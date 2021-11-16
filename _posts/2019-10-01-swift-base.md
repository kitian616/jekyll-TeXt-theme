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
我们可以通过如下命令来查看 SIL 码：

```shell
swiftc -emit-sil main.swift | xcrun swift-demangle >> main.sil
# xcrun swift-demangle 将混淆的名称还原
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

### lazy
测试源码如下：

```swift
class Dog {
   lazy var age:Int = 2
}

var d = Dog()
print(d.age);
```

可以通过内存查看访问前后值的变化：

![Image]({{ site.baseurl }}/assets/images/swift/02-swift-value-type00.png){:width="200px" height="200px"}

为了更确切的了解底层实现，我们生成的SIL码：

```swift
class Dog {
  lazy var age: Int { get set }
  @_hasStorage @_hasInitialValue final var $__lazy_storage_$_age: Int? { get set }
  @objc deinit
  init()
}
```

**从 SIL 码可以看出，lazy 修饰的属性其实会变为可选类型**

当我们用 **get** 方法第一次去访问的时候：

![Image]({{ site.baseurl }}/assets/images/swift/02-swift-value-type01.png){:width="200px" height="200px"}

所以：

````swift
class Dog {
  // 16 + 9 = 25  ---> 32
   lazy var age:Int = 2 // Optional
}
````

所以我们可以用：

```swift
print(class_getInstanceSize(Dog.self))
```

来查看 **lazy** 对类内存大小的影响：当设置为 lazy 修饰的时候类的大小为：**32**  

非 lazy 修饰的时候大小为：   **24**

可以查看一下 **Optional<Int>** 的大小：


```swift
print(MemoryLayout<Optional<Int>>.size);
print(MemoryLayout<Optional<Int>>.stride);
```

![Image]({{ site.baseurl }}/assets/images/swift/02-swift-value-type02.png){:width="200px" height="200px"}


所以总结一下，用 lazy 修饰的属性：

- 延迟存储属性必须有一个默认的初始值；
- 延迟存储在被第一次访问的时候才被赋值；
- 延迟存储属性并不能保证线程安全；
- 延迟存储属性对实例的大小有影响；

## 类属性
如下代码：

```swift
class Dog {
    static var age:Int = 15
}
```

生成 SIL 码来查看具体的内容：

```swift
class Dog {
  @_hasStorage @_hasInitialValue static var age: Int { get set }
  @objc deinit
  init()
}

// static Dog.age
sil_global hidden @static main.Dog.age : Swift.Int : $Int
```

由此可以看出，类属性是一个全局的静态变量。

我们分析一下访问的过程：

```swift
// 创建一个变量 d
alloc_global @main.d : Swift.Int                     // id: %2
// 将 %3 = d
%3 = global_addr @main.d : Swift.Int : $*Int         // users: %17, %9
%4 = metatype $@thick Dog.Type
// function_ref Dog.age.unsafeMutableAddressor
// 去调用 函数 Dog.age.unsafeMutableAddressor
%5 = function_ref @main.Dog.age.unsafeMutableAddressor : Swift.Int : $@convention(thin) () -> Builtin.RawPointer // user: %6
%6 = apply %5() : $@convention(thin) () -> Builtin.RawPointer // user: %7
%7 = pointer_to_address %6 : $Builtin.RawPointer to [strict] $*Int // user: %8
%8 = begin_access [read] [dynamic] %7 : $*Int   // users: %10, %9
// 将返回的值赋值给 %3
copy_addr %8 to [initialization] %3 : $*Int     // id: %9
```

来看一下函数 **Dog.age.unsafeMutableAddressor** 的实现：

```swift
// one-time initialization function for age
// 这个函数的功能主要是赋值 15
sil private [global_init_once_fn] @one-time initialization function for age : $@convention(c) () -> () {
bb0:
  alloc_global @static main.Dog.age : Swift.Int             // id: %0
  %1 = global_addr @static main.Dog.age : Swift.Int : $*Int // user: %4
  %2 = integer_literal $Builtin.Int64, 15         // user: %3
  %3 = struct $Int (%2 : $Builtin.Int64)          // user: %4
  store %3 to %1 : $*Int                          // id: %4
  %5 = tuple ()                                   // user: %6
  return %5 : $()                                 // id: %6
} // end sil function 'one-time initialization function for age'


// Dog.age.unsafeMutableAddressor
sil hidden [global_init] @main.Dog.age.unsafeMutableAddressor : Swift.Int : $@convention(thin) () -> Builtin.RawPointer {
bb0:
  %0 = global_addr @one-time initialization token for age : $*Builtin.Word // user: %1
  %1 = address_to_pointer %0 : $*Builtin.Word to $Builtin.RawPointer // user: %3
  // function_ref one-time initialization function for age
  // 调用 @one-time 函数进行初始化值 15
  %2 = function_ref @one-time initialization function for age : $@convention(c) () -> () // user: %3
  // 这里调用了 once
  %3 = builtin "once"(%1 : $Builtin.RawPointer, %2 : $@convention(c) () -> ()) : $()
  %4 = global_addr @static main.Dog.age : Swift.Int : $*Int // user: %5
  %5 = address_to_pointer %4 : $*Int to $Builtin.RawPointer // user: %6
  return %5 : $Builtin.RawPointer                 // id: %6
} // end sil function 'main.Dog.age.unsafeMutableAddressor : Swift.Int'
```

我们来看看 **once** ，实际上是调用了 **swift_once**

```c++
void swift::swift_once(swift_once_t *predicate, void (*fn)(void *),
                       void *context) {
#if defined(__APPLE__)
  dispatch_once_f(predicate, context, fn);
#elif defined(__CYGWIN__)
  _swift_once_f(predicate, context, fn);
#else
  std::call_once(*predicate, [fn, context]() { fn(context); });
#endif
}
```

所以它很像是 objc 中的 **dispatch_once** 函数。

最终，类属性是线程安全的。
{:.success}
