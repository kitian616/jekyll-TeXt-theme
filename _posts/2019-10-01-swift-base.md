---
title: Swift 对象、类和属性
tags: Swift
---

## 实例对象的结构

```swift
class Teacher {
    var age: Int = 18;
    var name: String = "erge";
}

var t = Teacher();
```

初始化的时候汇编调用如下：

```swift
SwiftDemo`Teacher.__allocating_init():
    0x100003c90 <+0>:  pushq  %rbp
    0x100003c91 <+1>:  movq   %rsp, %rbp
    0x100003c94 <+4>:  pushq  %r13
    0x100003c96 <+6>:  pushq  %rax
    0x100003c97 <+7>:  movq   %r13, %rdi
    0x100003c9a <+10>: movl   $0x28, %esi
    0x100003c9f <+15>: movl   $0x7, %edx
->  0x100003ca4 <+20>: callq  0x100003d7e               ; symbol stub for: swift_allocObject
    0x100003ca9 <+25>: movq   %rax, %r13
    0x100003cac <+28>: callq  0x100003ce0               ; SwiftDemo.Teacher.init() -> SwiftDemo.Teacher at main.swift:10
    0x100003cb1 <+33>: addq   $0x8, %rsp
    0x100003cb5 <+37>: popq   %r13
    0x100003cb7 <+39>: popq   %rbp
    0x100003cb8 <+40>: retq 
```

这里比较重要的是 **swift_allocObject** 的过程，下面是 **Swift-source 的源码**：

```c++
static HeapObject *_swift_allocObject_(HeapMetadata const *metadata,
                                       size_t requiredSize,
                                       size_t requiredAlignmentMask) {
  assert(isAlignmentMask(requiredAlignmentMask));
  // 创建内存大小，字节对齐
  auto object = reinterpret_cast<HeapObject *>(
      swift_slowAlloc(requiredSize, requiredAlignmentMask));

  // NOTE: this relies on the C++17 guaranteed semantics of no null-pointer
  // check on the placement new allocator which we have observed on Windows,
  // Linux, and macOS.
  new (object) HeapObject(metadata);

  // If leak tracking is enabled, start tracking this object.
  SWIFT_LEAKS_START_TRACKING_OBJECT(object);

  SWIFT_RT_TRACK_INVOCATION(object, swift_allocObject);

  return object;
}
```

**HeapObject** 的结构如下：

```c++
struct HeapObject {
  /// This is always a valid pointer to a metadata object.
  HeapMetadata const *metadata; // 元类型 8字节
	// InlineRefCounts refCounts 
  SWIFT_HEAPOBJECT_NON_OBJC_MEMBERS; // 引用计数 8 字节

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

#ifndef NDEBUG
  void dump() const LLVM_ATTRIBUTE_USED;
#endif

#endif // __swift__
};
```



**我们可以得出一些简单的结论**：

* Swift 内存分配的过程：**\_\_allocating_init ---> swift_allocObject ---> \_\_swift_allocObject\_\_ ---> swift_slowAlloc ---> malloc**;
* Swift 对象的内存结构 **HeapObject** 有两属性：**metadata(8字节)、refCounts(8字节)**，默认占用 **16字节**大小；
* init 主要是初始化变量；

查看类的大小：

````swift
print(class_getInstanceSize(Teacher.self))
// 40

print(MemoryLayout<String>.stride);
// 16
````

所以 **Teacher** 类的大小如下计算：

```swift
class Teacher {
  	// 16
    var age: Int = 18; // 8
    var name: String = "erge"; // 16
}

var t = Teacher();
```

## 类的结构

```c++
template <typename Runtime>
struct TargetHeapMetadata : TargetMetadata<Runtime> {
  using HeaderType = TargetHeapMetadataHeader<Runtime>;

  TargetHeapMetadata() = default;
  // 如果是单纯的 Swift 类就走这个
  constexpr TargetHeapMetadata(MetadataKind kind)
    : TargetMetadata<Runtime>(kind) {}
#if SWIFT_OBJC_INTEROP
  // 如果是继承 NSObject @objc 走这个
  constexpr TargetHeapMetadata(TargetAnyClassMetadata<Runtime> *isa)
    : TargetMetadata<Runtime>(isa) {}
#endif
};
```

**MetadataKind** 的类型如下：

继承关系

```
TargetClassMetadata(所有属性)
	---> TargetAnyClassMetadata(kind, superClass, cacheData)
		---> TargetHeapMetadata
			---> TargetMetadata(kind)
```

**metaData的数据结构如下：**

```c++
struct swift_class_t: NSObject {
	void *kind; //isa, kind(unsigned long)
	void *superClass;
	void *cacheData
  void *data
  uint32_t flags; //4
  uint32_t instanceAddressOffset; //4
  uint32_t instanceSize;//4
  uint16_t instanceAlignMask; //2
  uint16_t reserved; //2
  
  uint32_t classSize; //4
  uint32_t classAddressOffset; //4
  void *description;
  // ...
};
```

## Swift 属性

```swift
class Teacher {
    var age: Int = 18;
    var age2: Int = 20;
}
```

### 内存结构

可以看到如下的数据结构：
![Image]({{ site.baseurl }}/assets/images/swift/01-swift-struct.png){:.border}

存储属性要么是常量存储(let 修饰)属性，要么是变量存储(var 修饰)属性：
{:.warning}

```swift
class Teacher {
    let age: Int = 18;
    var name: String = "erge";
}
```

**SIL 码如下：**

```swift
class Teacher {
  @_hasStorage @_hasInitialValue final let age: Int { get }
  @_hasStorage @_hasInitialValue var name: String { get set }
  @objc deinit
  init()
}
```

### **计算属性是不占用空间的**

```swift
// Square 大小是 24
class Square {// 16
    var width: Double = 8; // 8
    // 计算属性：不占用内存空间 / 本质上就是 get 和 set 方法
    var area: Double {
        get { width * width}
        set {
            width = sqrt(newValue)
        }
    }
}
```

**SIL 码如下：**

```swift
class Square {
  @_hasStorage @_hasInitialValue var width: Double { get set }
  var area: Double { get set }
  @objc deinit
  init()
}
```

### 属性观察者

```swift
class Teacher {
    var age: Int;
    // 属性观察者
    var name: String = "erge" {
        // 新值存储之前
        willSet {
            print("willSet: \(newValue)");
        }
        // 新值存储之后
        didSet {
            print("didSet: \(oldValue)")
        }
    }
    // 初始化期间不会调用 属性观察者
    init() {
        self.name = "haha";
        self.age = 18;
    }
}
```

注意：属性观察者在初始化期间是不会被调用的，原因在于初始化的时候，比如上面的代码，如果 name 的属性观察者中调用了 age 属性，那么得到的值是未知的，这不安全。
{:.warning}

**属性观察者继承**

如下代码：

```swift
class Person {
    var age: Int = 18;
    // 属性观察者
    var name: String {
        // 新值存储之前
        willSet {
            print("willSet: \(newValue)");
        }
        // 新值存储之后
        didSet {
            print("didSet: \(oldValue)")
        }
    }
    // 初始化期间不会调用 属性观察者
    init() {
        self.name = "haha";
        self.age = 18;
    }
}

class Student: Person {
    override
    var name: String {
        willSet {
            print("override: \(newValue)")
        }
        didSet {
            print("override: \(oldValue)")
        }
    }
    override init() {
        super.init()
        self.name = "xioaming"
    }
}
```
结果如下：

```
override: xiaoming
willSet: xiaoming
didSet: haha
override: haha
```

注意：在继承的属性观察者中，初始化期间却被调用了，因为之前已经被初始化过了。
{:.warning}

