---
title: Swift 指针
tags: Swift
---

## Swift 指针

Swift 中的指针分为两类：

- typed pointer： 指定数据类型指针，在 Swift 表示 **UnsafePointer<T>**；
- raw pointer: 未指定数据类型的指针，在 Swift 表示 **UnsafeRawPointer**；

Swift 中的指针和 OC 中的对应如下：

| Swift                   | Objc          | 说明                         |
| ----------------------- | ------------- | ---------------------------- |
| unsafePointer<T>        | const T *     | 指针和所指向的内容都不可变   |
| unsafeMutablePointer    | T *           | 指针以及其所指向的内容均可变 |
| unsafeRawPointer        | const  void * | 指针指向未知类型             |
| unsafeMutableRawPointer | void *        | 指针指向未知类型             |



### MemoryLayout

在讲指针之前，必须先得了解一下`Swift`的内存布局`MemoryLayout`，不同的基础类型、结构体、枚举、类等等，他们在内存中占有的大小，步长，对齐长度都不一样。一旦指针的偏移量、获取值的长度错误，轻则获取的值错误，重则程序崩溃。

基础类型的`MemoryLayout`：

```swift
MemoryLayout<Int>.size          // returns 8 (on 64-bit)
MemoryLayout<Int>.alignment     // returns 8 (on 64-bit)
MemoryLayout<Int>.stride        // returns 8 (on 64-bit)

MemoryLayout<Int16>.size        // returns 2
MemoryLayout<Int16>.alignment   // returns 2
MemoryLayout<Int16>.stride      // returns 2

MemoryLayout<Bool>.size         // returns 1
MemoryLayout<Bool>.alignment    // returns 1
MemoryLayout<Bool>.stride       // returns 1

MemoryLayout<Float>.size        // returns 4
MemoryLayout<Float>.alignment   // returns 4
MemoryLayout<Float>.stride      // returns 4

MemoryLayout<Double>.size       // returns 8
MemoryLayout<Double>.alignment  // returns 8
MemoryLayout<Double>.stride     // returns 8
// ...
```

MemoryLayout是一个在编译时评估的泛型类型。它确定每个指定类型的大小、对齐方式和步长，并返回一个以字节为单位的数字。

我们可以靠如上的方式获取数据类型的`MemoryLayout`：

- `size`：数据类型的长度，就是数据在内存中占据的大小；
- `alignment`：数据类型的对齐方式，在某些结构中（比如在`Raw Pointer`），数据在内存的首地址必须是`alignment`的倍数，否则将会崩溃，例如`Int16`的`alignment`是2，那么`Int16`在内存的首地址必须是偶数，否则崩溃；
- `stride`：数据类型的步长，应该是`alignment`的倍数，如果是一串紧挨的数据，那么下一个数据会在大于等于`stride`的地址之后；

关于数据类型的对齐方式举个例子：

```swift
//向堆申请开辟空间
let p = UnsafeMutableRawPointer.allocate(byteCount: 4 * 8, alignment: 8)
//结束时释放空间
defer {
    p.deallocate()
}
//在这块连续的内存空间内，放上0，1，2，3
for i in 0..<4 {
  	// 存放数据 storeBytes
    p.advanced(by: i * 8).storeBytes(of: i, as: Int.self)
}
let offset = 8
// load 取数据
print(p.advanced(by: offset).load(as: Int.self))
// 这里要释放的
p.deallocate()
```

不过注意存和取都必须是 **alignment** 的倍数，否则会崩溃。

### 其他类型的`MemoryLayout`

`Swift`中还有许多其他的类型：结构体，枚举，类等

我们可以用同样的方式来查看他们的`MemoryLayout`：

````swift
struct EmptyStruct {}

MemoryLayout<EmptyStruct>.size      // returns 0
MemoryLayout<EmptyStruct>.alignment // returns 1
MemoryLayout<EmptyStruct>.stride    // returns 1

struct SampleStruct {
  let number: UInt32
  let flag: Bool
}

MemoryLayout<SampleStruct>.size       // returns 5
MemoryLayout<SampleStruct>.alignment  // returns 4
MemoryLayout<SampleStruct>.stride     // returns 8

class EmptyClass {}

MemoryLayout<EmptyClass>.size      // returns 8 (on 64-bit)
MemoryLayout<EmptyClass>.stride    // returns 8 (on 64-bit)
MemoryLayout<EmptyClass>.alignment // returns 8 (on 64-bit)

class SampleClass {
  let number: Int64 = 0
  let flag = false
}

MemoryLayout<SampleClass>.size      // returns 8 (on 64-bit)
MemoryLayout<SampleClass>.stride    // returns 8 (on 64-bit)
MemoryLayout<SampleClass>.alignment // returns 8 (on 64-bit)

````

我们可以发现：

`EmptyStruct`空结构体的大小为零。对齐大小是1，所有数字都可以被1整除，所以它可以存放于任何地址。步长也为1，虽然它的大小为零，但是必须有一个唯一的内存地址。

`SampleStruct`的大小为5，等于`UInt32`和`Bool`大小的相加。对齐大小是4，等于`UInt32`的对齐大小，属性中最大的对齐方式会决定结构体的对齐方式。步长为8，比5大的，最小的4的倍数为8，所以步长为8。

`EmptyClass`和`SampleClass`，不管是否包含属性，他们的大小，对齐方式，步长都为8，因为类是引用类型，本质上就是一个指针，在64位的系统下，指针长度就是为8.

当然，还有枚举、数组、字典等等的`MemoryLayout`，以及结构体属性内部顺序不一样，会造成结构体的大小也不一样。

### 指针类型

在`Swift`中，因为指针可以直接操作内存，所以用上了`Unsafe`的开头修饰，虽然每次申明指针的时候，要多写这个修饰词，但是可以时刻提醒着你在访问编译器没有检查的内存，如果操作不当，你会遇到一些奇怪的问题，甚至奔溃。

`Swift`不像`C`的`char *`那样只提供一种非结构化方式访问内存的非安全指针类型。Swift包含近12种指针类型，每种类型都有不同的功能和用途。

我们看下常用的8个指针类型：

| 指针类型                        | Editable | Collection | Strideable | Typed |
| ------------------------------- | -------- | ---------- | ---------- | ----- |
| `UnsafeMutablePointer<T>`       | YES      | NO         | YES        | YES   |
| `UnsafePointer<T>`              | NO       | NO         | YES        | YES   |
| `UnsafeMutableBufferPointer<T>` | YES      | YES        | NO         | YES   |
| `UnsafeBufferPointer<T>`        | NO       | YES        | NO         | YES   |
| `UnsafeMutableRawPointer`       | YES      | NO         | YES        | NO    |
| `UnsafeRawPointer`              | NO       | NO         | YES        | NO    |
| `UnsafeMutableRawBufferPointer` | YES      | YES        | NO         | NO    |
| `UnsafeRawBufferPointer`        | NO       | YES        | NO         | NO    |

- `Editable`: 类型名中带有`Mutable`的，说明都是可以写入更改数据的，其余的只能读取，并不能修改，可以保护数据安全。根据自己的需求看，是否需要`Mutable`;
- Collection`: 类型名中带有`Buffer`的，都具有`Collection`的特性，如果查看他们的声明文件，发现他们都遵守`Collection`协议`;
- Strideable`: 不带有`Buffer`的可以调用`advanced`方法，`Typed`类型的默认步长为`T`的步长，而`Raw`类型的默认步长为1`;
- Typed`: 有范型的是`Typed Pointer`，带有`Raw`字段的是`Raw Pointer；

#### 如何获取某个变量的地址？

如下示例：

```swift
var age = 16
let p = withUnsafePointer(to: &age) { ptr in
    return ptr
}
print(p.pointee);
// 尾随闭包简写
var a = withUnsafePointer(to: &age){$0}
// a.pointee 对应的值
print(a.pointee)
// 这里 a p 的类型为 UnsafePointer

// 因为返回的值的 UnsafePointer  故不能修改值，
withUnsafePointer(to: &age) { ptr in
	// 会报出如下错误
	// Left side of mutating operator isn't mutable: 'pointee' is a get-only property
	ptr.pointee += 1
}
```

如果我们想要修改对应的值怎么办？

```swift
withUnsafeMutablePointer(to: &age) { ptr in
    ptr.pointee += 1
}
```

另一种创建方式：

```swift
var age = 16
// capacity: 大小为 1 * 8 字节
let p = UnsafeMutablePointer<Int>.allocate(capacity: 1)
// 初始化当前的指针
p.initialize(to: age)

print(p.pointee)

// 成对调用，管理内存
p.deinitialize(count: 1)

// 释放内存
p.deallocate()
```

#### 存放结构体和类

```swift
struct Person {
    var age = 16
    var name = "小明"
}


let ptr = UnsafeMutablePointer<Person>.allocate(capacity: 2)

ptr.initialize(to: Person())
ptr.advanced(by: 1).initialize(to: Person(age: 25, name: "liming"))

print(ptr[0])
print(ptr[1])
print((ptr + 1).pointee)
print(ptr.successor().pointee)
ptr.deinitialize(count:2)
ptr.deallocate()

class Dog {
    var age = 2
    var name = "naonao"
    init() {}
    init(age: Int, name: String) {
        self.age = age
        self.name = name
    }
}


let ptrCls = UnsafeMutablePointer<Dog>.allocate(capacity: 2)
ptrCls.initialize(to: Dog())
ptrCls.advanced(by: 1).initialize(to: Dog(age: 1, name: "小白"))

print(ptrCls[0].name)
print(ptrCls[1].name)

ptrCls.deinitialize(count: 2)
ptrCls.deallocate()

```

#### Unmanaged

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

struct HeapObject {
    var kind: UnsafeRawPointer
    var strongRef: Int32
    var unownedRef: Int32
}

struct SwiftClass {
    var kind: UnsafeRawPointer
    var superClass: UnsafeRawPointer
    var cacheData1: UnsafeRawPointer
    var cacheData2: UnsafeRawPointer
    var data: UnsafeRawPointer
    var flags: UInt32
    var instanceAddressOffset: UInt32
    var instanceSize: UInt32
    var flinstanceAlignMask: UInt16
    var reserved: UInt16
    var classSize: UInt32
    var classAddressOffset: UInt32
    var description: UnsafeRawPointer
}

let d = Dog()

// 不会增加引用计数, passRetained 会增加引用计数
let ptr = Unmanaged.passUnretained(d).toOpaque()
let heapObject = ptr.bindMemory(to: HeapObject.self, capacity: 1)
let metaPtr = heapObject.pointee.kind.bindMemory(to: SwiftClass.self, capacity: 1)

print(metaPtr.pointee)
```


## 参考

[Swift中的指针](https://juejin.cn/post/6914147790197096456)
