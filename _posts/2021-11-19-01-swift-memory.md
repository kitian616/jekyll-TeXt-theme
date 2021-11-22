---
title: Swift 内存管理
tags: Swift
---

## 引用计数结构

之前我们分析过了 **Swift** 实例的内存结构如下：

![]({{ site.baseurl }}/assets/images/swift/04-swift-memory.png)

现在我们继续分析 **refCounts**

### InlineRefCounts

HeapObject 结构如下：

```c++
struct HeapObject {
  /// This is always a valid pointer to a metadata object.
  HeapMetadata const *metadata;

  SWIFT_HEAPOBJECT_NON_OBJC_MEMBERS;

#ifndef __swift__
  HeapObject() = default;

  // Initialize a HeapObject header as appropriate for a newly-allocated object.
  constexpr HeapObject(HeapMetadata const *newMetadata) 
    : metadata(newMetadata)
    , refCounts(InlineRefCounts::Initialized)
  { }
  
  // Initialize a HeapObject header for an immortal object
  constexpr HeapObject(HeapMetadata const *newMetadata,
                       InlineRefCounts::Immortal_t immortal)
  : metadata(newMetadata)
  , refCounts(InlineRefCounts::Immortal)
  { }
};
```

**SWIFT_HEAPOBJECT_NON_OBJC_MEMBERS** 宏如下：

```c++
#define SWIFT_HEAPOBJECT_NON_OBJC_MEMBERS       \
  InlineRefCounts refCounts

typedef RefCounts<InlineRefCountBits> InlineRefCounts;
typedef RefCounts<SideTableRefCountBits> SideTableRefCounts;
```

看到`InlineRefCounts`是`RefCounts<InlineRefCountBits>`的别名，我们点进`RefCounts`看下：

```c++
template <typename RefCountBits>
class RefCounts {
  std::atomic<RefCountBits> refCounts;
  // ...
}
```

`RefCounts`是一个抽象出来的模板类，所以得看传进来的`InlineRefCountBits`：

```c++
typedef RefCountBitsT<RefCountIsInline> InlineRefCountBits;
```

又是一个别名，和上面操作一样，`RefCountBitsT`是一个模版类，但`RefCountIsInline`只是一个枚举，所以我们深入看下`RefCountBitsT`:

```c++
// RefCountIsInline: refcount stored in an object
// RefCountNotInline: refcount stored in an object's side table entry
enum RefCountInlinedness { RefCountNotInline = false, RefCountIsInline = true };

template <RefCountInlinedness refcountIsInline>
class RefCountBitsT {

  friend class RefCountBitsT<RefCountIsInline>;
  friend class RefCountBitsT<RefCountNotInline>;
  
  static const RefCountInlinedness Inlinedness = refcountIsInline;

  typedef typename RefCountBitsInt<refcountIsInline, sizeof(void*)>::Type
    BitsType;
  typedef typename RefCountBitsInt<refcountIsInline, sizeof(void*)>::SignedType
    SignedBitsType;
  typedef RefCountBitOffsets<sizeof(BitsType)>
    Offsets;

  BitsType bits;
  // ...
 }
```

`BitsType`又是`RefCountBitsInt`的别名，点击`RefCountBitsInt`的时候，我们发现有多个选项，不过 **sizeof(void*)**

的大小为 8，故：

```c++
// 64-bit inline
// 64-bit out of line
template <RefCountInlinedness refcountIsInline>
struct RefCountBitsInt<refcountIsInline, 8> {
  typedef uint64_t Type;
  typedef int64_t SignedType;
};
```

看了这么多，`InlineRefCounts`目前就是一个`uint64_t`，被`RefCountBitsT`操作着。

### InlineRefCounts 结构

**RefCountBitsT** 的几个初始化函数：

```c++
  LLVM_ATTRIBUTE_ALWAYS_INLINE
  constexpr
  RefCountBitsT(uint32_t strongExtraCount, uint32_t unownedCount)
    : bits((BitsType(strongExtraCount) << Offsets::StrongExtraRefCountShift) |
           (BitsType(unownedCount)     << Offsets::UnownedRefCountShift))
  { }
  
  LLVM_ATTRIBUTE_ALWAYS_INLINE
  constexpr
  RefCountBitsT(Immortal_t immortal)
    : bits((BitsType(2) << Offsets::StrongExtraRefCountShift) |
           (BitsType(2) << Offsets::UnownedRefCountShift) |
           (BitsType(1) << Offsets::IsImmortalShift) |
           (BitsType(1) << Offsets::UseSlowRCShift))
  { }

  LLVM_ATTRIBUTE_ALWAYS_INLINE
  RefCountBitsT(HeapObjectSideTableEntry* side)
    : bits((reinterpret_cast<BitsType>(side) >> Offsets::SideTableUnusedLowBits)
           | (BitsType(1) << Offsets::UseSlowRCShift)
           | (BitsType(1) << Offsets::SideTableMarkShift))
  {
    assert(refcountIsInline);
  }

```

我们点击`Offsets`后看到：

```c++
  typedef RefCountBitOffsets<sizeof(BitsType)>    Offsets;
  
  // 64-bit inline
// 64-bit out of line
// 32-bit out of line
template <>
struct RefCountBitOffsets<8> {
  static const size_t IsImmortalShift = 0;
  static const size_t IsImmortalBitCount = 1;
  static const uint64_t IsImmortalMask = maskForField(IsImmortal);

  static const size_t UnownedRefCountShift = shiftAfterField(IsImmortal);
  static const size_t UnownedRefCountBitCount = 31;
  static const uint64_t UnownedRefCountMask = maskForField(UnownedRefCount);

  static const size_t IsDeinitingShift = shiftAfterField(UnownedRefCount);
  static const size_t IsDeinitingBitCount = 1;
  static const uint64_t IsDeinitingMask = maskForField(IsDeiniting);

  static const size_t StrongExtraRefCountShift = shiftAfterField(IsDeiniting);
  static const size_t StrongExtraRefCountBitCount = 30;
  static const uint64_t StrongExtraRefCountMask = maskForField(StrongExtraRefCount);
  
  static const size_t UseSlowRCShift = shiftAfterField(StrongExtraRefCount);
  static const size_t UseSlowRCBitCount = 1;
  static const uint64_t UseSlowRCMask = maskForField(UseSlowRC);

  static const size_t SideTableShift = 0;
  static const size_t SideTableBitCount = 62;
  static const uint64_t SideTableMask = maskForField(SideTable);
  static const size_t SideTableUnusedLowBits = 3;

  static const size_t SideTableMarkShift = SideTableBitCount;
  static const size_t SideTableMarkBitCount = 1;
  static const uint64_t SideTableMarkMask = maskForField(SideTableMark);
};
```

这里已经能看到所有的偏移值了，我们整理成一个表格：

| 名字                    | 范围（起始位置，长度 ） | 作用                                      |
| ----------------------- | ----------------------- | ----------------------------------------- |
| PureSwiftDeallocMask    | (0, 1)                  | 对象是否需要调用ObjC运行时来解除分配      |
| UnownedRefCountMask     | (1, 31)                 | 无主引用计数                              |
| IsImmortalMask          | (0, 32)                 | 所有bit设置后，对象不会释放或具有refcount |
| IsDeinitingMask         | (32, 1)                 | 是否在进行反初始化                        |
| StrongExtraRefCountMask | (33, 30)                | 强引用计数                                |
| UseSlowRCMask           | (63, 1)                 | 使用慢RC                                  |
| SideTableMask           | (0, 62)                 | 存放SideTable地址                         |
| SideTableUnusedLowBits  |                         | SideTable没有用到的低字节位数             |
| SideTableMarkMask       | (62, 1)                 | 是否存放是SideTable                       |

具体结构如下：

![]({{ site.baseurl }}/assets/images/swift/04-swift-refconts.png)

下面的代码是 **swift_retain** 的过程：

```c++
static HeapObject *_swift_retain_(HeapObject *object) {
  SWIFT_RT_TRACK_INVOCATION(object, swift_retain);
  if (isValidPointerForNativeRetain(object))
    object->refCounts.increment(1);
  return object;
}

HeapObject *swift::swift_retain(HeapObject *object) {
  CALL_IMPL(swift_retain, (object));
}

// Increment the reference count.
void increment(uint32_t inc = 1) {
  auto oldbits = refCounts.load(SWIFT_MEMORY_ORDER_CONSUME);
  RefCountBits newbits;
  do {
    newbits = oldbits;
    // 这里去增加计数
    bool fast = newbits.incrementStrongExtraRefCount(inc);
    // fast == true 引用计数没有溢出， fast == false 引用计数溢出
    if (SWIFT_UNLIKELY(!fast)) {
      if (oldbits.isImmortal())
        return;
      return incrementSlow(oldbits, inc);
    }
  } while (!refCounts.compare_exchange_weak(oldbits, newbits,
                                            std::memory_order_relaxed));
}

bool incrementStrongExtraRefCount(uint32_t inc) {
  // This deliberately overflows into the UseSlowRC field.
  bits += BitsType(inc) << Offsets::StrongExtraRefCountShift;
  return (SignedBitsType(bits) >= 0);
}
```

## 无主引用（unowned）

如下示例代码：

```swift
class Dog {
    var age = 2
    var name = "naonao"
    init() {}
    init(age: Int, name: String) {
        self.age = age
        self.name = name
    }
}
var d = Dog()
unowned var t = d
```

- 无主引用和弱引用类似，不会 retain 当前实例的引用计数；
- 非可选类型，当前 t 被 **unowned** 修饰之后，假定永远有值，不会为空；
- 既然是非可选类型，也就意味着存在访问 **Crash** 的风险；

### swift_unownedRetain

```swift
HeapObject *swift::swift_unownedRetain(HeapObject *object) {
  SWIFT_RT_TRACK_INVOCATION(object, swift_unownedRetain);
  if (!isValidPointerForNativeRetain(object))
    return object;

  object->refCounts.incrementUnowned(1);
  return object;
}

void incrementUnowned(uint32_t inc) {
  auto oldbits = refCounts.load(SWIFT_MEMORY_ORDER_CONSUME);
  if (oldbits.isImmortal())
    return;
  RefCountBits newbits;
  do {
    if (oldbits.hasSideTable())
      return oldbits.getSideTable()->incrementUnowned(inc);

    newbits = oldbits;
    assert(newbits.getUnownedRefCount() != 0);
    uint32_t oldValue = newbits.incrementUnownedRefCount(inc);

    // Check overflow and use the side table on overflow.
    if (newbits.getUnownedRefCount() != oldValue + inc)
      return incrementUnownedSlow(inc);

  } while (!refCounts.compare_exchange_weak(oldbits, newbits,
                                            std::memory_order_relaxed));
}

uint32_t incrementUnownedRefCount(uint32_t inc) {
  uint32_t old = getUnownedRefCount();
  setUnownedRefCount(old + inc);
  return old;
}
```

## 弱引用

### swift_weakInit

```c++
WeakReference *swift::swift_weakInit(WeakReference *ref, HeapObject *value) {
  ref->nativeInit(value);
  return ref;
}

void nativeAssign(HeapObject *newObject) {
  if (newObject) {
    assert(objectUsesNativeSwiftReferenceCounting(newObject) &&
           "weak assign native with non-native new object");
  }

  auto newSide =
    newObject ? newObject->refCounts.formWeakReference() : nullptr;
  auto newBits = WeakReferenceBits(newSide);

  auto oldBits = nativeValue.load(std::memory_order_relaxed);
  nativeValue.store(newBits, std::memory_order_relaxed);

  assert(oldBits.isNativeOrNull() &&
         "weak assign native with non-native old object");
  destroyOldNativeBits(oldBits);
}

HeapObjectSideTableEntry* RefCounts<InlineRefCountBits>::formWeakReference()
{
  auto side = allocateSideTable(true);
  if (side)
    return side->incrementWeak();
  else
    return nullptr;
}
```

我们可以看到，用weak的时候，会创建一张`SideTable`，我们详细看一下`allocateSideTable`的实现：

```c++
HeapObjectSideTableEntry* RefCounts<InlineRefCountBits>::allocateSideTable(bool failIfDeiniting) {
  // 获取当前的引用计数
  auto oldbits = refCounts.load(SWIFT_MEMORY_ORDER_CONSUME);
  
  // 如果 oldbits 已经创建过 SideTable，就返回
  if (oldbits.hasSideTable()) {
    // Already have a side table. Return it.
    return oldbits.getSideTable();
  } 
  else if (failIfDeiniting && oldbits.getIsDeiniting()) {
		// 如果正在释放，do noting
    return nullptr;
  }

  // Preflight passed. Allocate a side table.
  
  // 生成 SideTable
  HeapObjectSideTableEntry *side = new HeapObjectSideTableEntry(getHeapObject());
  
  auto newbits = InlineRefCountBits(side);
  
  do {
    // 这里又做了一次判断
    if (oldbits.hasSideTable()) {
      // Already have a side table. Return it and delete ours.
      // Read before delete to streamline barriers.
      auto result = oldbits.getSideTable();
      delete side;
      return result;
    }
    else if (failIfDeiniting && oldbits.getIsDeiniting()) {
      // Already past the start of deinit. Do nothing.
      return nullptr;
    }
    
    side->initRefCounts(oldbits);
    
  } while (! refCounts.compare_exchange_weak(oldbits, newbits,
                                             std::memory_order_release,
                                             std::memory_order_relaxed));
  return side;
}
```

这里注意 **InlineRefCountBits** 的初始化参数为 ：**SideTable**

```c++
RefCountBitsT(HeapObjectSideTableEntry* side)
  : bits((reinterpret_cast<BitsType>(side) >> Offsets::SideTableUnusedLowBits)
         | (BitsType(1) << Offsets::UseSlowRCShift)
         | (BitsType(1) << Offsets::SideTableMarkShift))
{
  assert(refcountIsInline);
}
```

也就是说现在的 **refConts** 变成了如下：

![]({{ site.baseurl }}/assets/images/swift/04-swift-refconts-01.png)

继续看 **incrementWeak** 函数：

```c++
void incrementWeak() {
  auto oldbits = refCounts.load(SWIFT_MEMORY_ORDER_CONSUME);
  RefCountBits newbits;
  do {
    newbits = oldbits;
    assert(newbits.getWeakRefCount() != 0);
    newbits.incrementWeakRefCount();

    if (newbits.getWeakRefCount() < oldbits.getWeakRefCount())
      swift_abortWeakRetainOverflow();
  } while (!refCounts.compare_exchange_weak(oldbits, newbits,
                                            std::memory_order_relaxed));
}
```

这样，我们整个weak的操作流程已经完成了，我们总结下

当使用弱引用的时候，我们会查看当前对象的`SideTable`是否已经创建了，如果创建了，`SideTable`中弱引用计数加一，如果没有创建，那么先创建，把当前对象的引用计数存在`SideTable`中，在把弱引用计数加一。操作完后，我们把`SideTable`处理过的地址赋给当前对象的引用计数。

换句话说，一旦我们使用了weak修饰词，那么对象引用计数的内存里存放的不在是强引用和无主引用的个数，而是对应`SideTable`的地址，真正的强引用和无主引用的个数存在了`SideTable`中。

### HeapObjectSideTableEntry

我们刚才从代码可以看到`SideTable`用的是`HeapObjectSideTableEntry`，这样可以看下`SideTable`的大概结构

```c++
class HeapObjectSideTableEntry {
  // FIXME: does object need to be atomic?
  std::atomic<HeapObject*> object;
  SideTableRefCounts refCounts;
  ...
}
```

我们可以看到里面有个`HeapObject`的指针，然后还有个`SideTableRefCounts`，我们点击`SideTableRefCounts`，又会来到这边:

```c++
typedef RefCounts<InlineRefCountBits> InlineRefCounts;
typedef RefCounts<SideTableRefCountBits> SideTableRefCounts;
```

这次我们看的是`SideTableRefCountBits`:

```c++
class alignas(sizeof(void*) * 2) SideTableRefCountBits : public RefCountBitsT<RefCountNotInline>
{
  uint32_t weakBits;
  ...
}
```

`SideTableRefCountBits`继承与`RefCountBitsT`，所以也会有一个`bits`属性，存放的就是对应对象的强引用和无主引用的个数，这个`weakBits`存放的就是弱引用的个数，我们等会到项目里验证一下。

### 验证

如下示例：

```swift
class Dog {
    var age = 2
    var name = "naonao"
    init() {}
    init(age: Int, name: String) {
        self.age = age
        self.name = name
    }
}

var d = Dog()

weak var a = d
weak var b = d
weak var c = d

var e = d
print(d)
```

整个变化如下：

![]({{ site.baseurl }}/assets/images/swift/04-swift-sidetable.png)

## 循环引用

如下示例代码，会发生循环引用：

```swift
class Dog {
    var age = 2
    var name = "naonao"
    var complete: (() -> ())?
    deinit {
        print("Dog release ...")
    }
}

func test() {
    let d = Dog()
    d.complete = {
        d.age += 1
    }
}

test()
```

如何解决呢，两种方法：

- unowned

```swift
let d = Dog()
d.complete = { [unowned d] in
    d.age += 1
}
```

- weak

```swift
let d = Dog()
d.complete = { [weak d] in
    d?.age += 1
}
```

### 捕获列表

如下示例代码的大括号就是捕获列表的语法：

```swift
d.complete = { [weak d] in
    d?.age += 1
}
```

如下示例代码：

```swift
let d = Dog()
var height = 0
var width  = 0
d.complete = { [weak d, height] in
    d?.age += 1
    print("height: \(height) width: \(width)")
}
width = 10
height = 5
d.complete?()
// height: 0 width: 10
```

你会发现 block 中的 **height** 的值并没有变化，很像一个 block 内部的常量，不可被改变，而 **width** 可以被外界影响，这是捕获列表的特性。

## 参考

[Swift引用计数的底层分析](https://juejin.cn/post/6906438952895709197)