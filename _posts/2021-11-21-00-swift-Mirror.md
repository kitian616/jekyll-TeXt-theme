---
title: Swift Mirror
tags: Swift
---

## 前置知识介绍

### AnyObject

**AnyObject** ：代表任意类的实例、类的类型和仅类遵守的协议。

如下面几个就不正确：

#### 字面量

解释一下 Swift 中的字面量

所谓字面量，就是指像特定的数字，字符串或者是布尔值这样，能够直接了当地指出自己的类型并为变量进行赋值的值。比如在下面：

```swift
let aNumber = 3         //整型字面量
let aString = "Hello"   //字符串字面量
let aBool = true        //布尔值字面量
```

- 整型字面量

  整型字面量可以是一个十进制，二进制，八进制或十六进制常量。 二进制前缀为 0b，八进制前缀为 0o，十六进制前缀为 0x，十进制没有前缀：

  ```swift
  let decimalInteger = 17           // 17 - 十进制表示
  let binaryInteger = 0b10001       // 17 - 二进制表示
  let octalInteger = 0o21           // 17 - 八进制表示
  let hexadecimalInteger = 0x11     // 17 - 十六进制表示
  ```

- 浮点型字面量

  浮点型字面量有整数部分，小数点，小数部分及指数部分。

  除非特别指定，浮点型字面量的默认推导类型为 Swift 标准库类型中的 Double，表示64位浮点数。

  ```swift
  let decimalDouble = 12.1875       //十进制浮点型字面量
  let exponentDouble = 1.21875e1    //十进制浮点型字面量
  let hexadecimalDouble = 0xC.3p0   //十六进制浮点型字面量
  ```

- 字符串型字面量

  字符串型字面量由被包在双引号中的一串字符组成，形式如下：

  ```swift
  "characters"
  ```

- 布尔型字面量

  布尔型字面量的默认类型是 Bool。

  布尔值字面量有三个值，它们是 Swift 的保留关键字：

  ```
  true 表示真。
  
  false 表示假。
  
  nil 表示没有值。
  ```

回过头来继续来说 **AnyObject**，如下示例：

```swift
var array: [AnyObject] = [1, true, "aaa"]
```

上面的代码是无法编译过的，**AnyObject** 不能用来表示字面量。

#### 协议

如下示例代码：

```swift
protocol Person: AnyObject {
    func eat()
}

struct Teacher: Person {
    
}
// Non-class type 'Teacher' cannot conform to class protocol 'Person'
```

上面的代码无法编译过去，**AnyObject** 仅类遵守的协议，所以 **struct** 需要改写成 **class**。

### Any

**Any**：代表任意类型，包括 **function** 类型和 **Optional** 类型。

如下示例代码，并没有编译错误：

```swift
var array: [Any] = [1, true, "aaa"]
```

或者如下代码，也没有问题：

```swift
protocol Person: Any {
    func eat()
}

struct Teacher: Person {
    func eat(){}
}
```

### AnyClass

**AnyClass**：代表任意实例的类型。

如下示例代码是无法编译通过的：

```swift
var array: [AnyClass] = [Int.self, String.self]
```

**Int、String** 等等是字面量，如下代码能编译通过：

```swift
var array: [AnyClass] = [Dog.self, NSNumber.self]
```

### T.self

**T.self**：如果 T 是实例变量，那返回的就是它本身，如果 T 是类，那返回的就是 **metadata**。

### T.Type

**T.Type**：代表一种类型，**T.self 是 T.Type 的类型**。

### type(of:)

**type(of:)**： 用来获取一个值的动态类型。

如下示例：

```swift
protocol PersonProtocol: Any {
    func eat()
}

struct Teacher: PersonProtocol {
    func eat() {}
}


func valueType<T>(_ value: T) {
    let type = type(of: value)
    print(type)
}

var t1: PersonProtocol = Teacher()
var t2 = Teacher()
valueType(t1)
valueType(t2)
// print:
// PersonProtocol
// Teacher
```

你会发现 **type(of:)** 并没有正常的识别出 class，要改变一下写法：

```swift
let type = type(of: value as Any)
```

## Mirror 使用

如下示例代码：

```swift
class Dog {
    var age = 2
    var name = "naonao"
    var complete: (() -> ())?
    deinit {
        print("Dog release ...")
    }
}

var d = Dog()
let mirror = Mirror(reflecting: d.self)

for (label, value) in mirror.children {
    print("label: \(label) value: \(value)")
}
/*
label: Optional("age")      value: 2
label: Optional("name")     value: naonao
label: Optional("complete") value: nil
*/
```
