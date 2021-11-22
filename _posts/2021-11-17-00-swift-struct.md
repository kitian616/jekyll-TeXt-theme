---
title: Swift 结构体
tags: Swift
---

### 结构体的一些特性

如下示例代码：

```swift
struct Teacher {
    var age: Int
    var name: String
}
```

如下：
![Image]({{ site.baseurl }}/assets/images/swift/02-swift-struct-00.png)

查看 SIL 码：

```swift
struct Teacher {
  @_hasStorage var age: Int { get set }
  @_hasStorage var name: String { get set }
  init(age: Int, name: String)
}
```

自动帮你实现了带有初始化参数的方法。

假如都有初始值的情况：

```swift
struct Teacher {
    var age: Int = 18
    var name: String = "liming"
}

let t = Teacher()
```

查看 SIL 码：

```swift
struct Teacher {
  @_hasStorage @_hasInitialValue var age: Int { get set }
  @_hasStorage @_hasInitialValue var name: String { get set }
  init()
  init(age: Int = 18, name: String = "liming")
}
```

你会发现不仅实现了带有默认值的初始化方法，而且还增加了没有参数的初始化方法。

 如果我们手动实现了初始化方法：

```swift
struct Teacher {
    var age: Int = 18
    var name: String = "liming"
    init(age: Int) {
        self.age = age;
    }
}
```

查看 SIL 码：

```swift
struct Teacher {
  @_hasStorage @_hasInitialValue var age: Int { get set }
  @_hasStorage @_hasInitialValue var name: String { get set }
  init(age: Int)
}
```

嗯，它就不会自动生成了。


### 结构体是值类型

如下测试代码：

```swift
struct Teacher {
    var age: Int = 18  // 8
    var age2: Int = 16 // 8
}

func test() {
    var t = Teacher();
    let t1 = t;
    withUnsafeMutableBytes(of: &t){print($0)}
    print(t1)
}

test()
```

打印结果：

```swift
UnsafeMutableRawBufferPointer(start: 0x00007ff7bfeff2b0, count: 16)
(lldb) x/8g 0x00007ff7bfeff2b0
0x7ff7bfeff2b0: 0x0000000000000012 0x0000000000000010
0x7ff7bfeff2c0: 0x000000010008c3a0 0x00007ff7bfeff398
0x7ff7bfeff2d0: 0x00007ff7bfeff2e0 0x0000000100003809
0x7ff7bfeff2e0: 0x00007ff7bfeff3f0 0x00000001000194fe
(lldb) x/8g 0x00007ff7bfeff2a0
0x7ff7bfeff2a0: 0x0000000000000012 0x0000000000000010
0x7ff7bfeff2b0: 0x0000000000000012 0x0000000000000010
0x7ff7bfeff2c0: 0x000000010008c3a0 0x00007ff7bfeff398
0x7ff7bfeff2d0: 0x00007ff7bfeff2e0 0x0000000100003809
(lldb) 
```

可以看到结构体存在于栈区。**t1 完全复制了 t**.

那么 **Teacher** 的大小：

```swift
print(MemoryLayout<Teacher>.size);
// 16
```

### 结构体引用类

如下示例代码：

```swift
class Subject {
    var name: String = "小明"
}

struct Teacher {
    var age: Int = 18
    var age2: Int = 16
    var sub: Subject
}

let sub = Subject()
var t = Teacher(sub: sub);
```

关于引用计数，将在内存管理部分分析。

 ### mutating

如下示例代码：

```swift
struct Teacher {
    var items = [Int]();
    func push(item: Int) -> Void {
        print(item)
    }
}

let t = Teacher()
t.items.append(1)
```

会出现如下的错误：

![Image]({{ site.baseurl }}/assets/images/swift/02-swift-struct-01.png)


如果将 **let** 改换成 **var**，那就没有问题了。

但情况是这样的：

```swift
struct Teacher {
    var items = [Int]();
    func push(item: Int) -> Void {
        self.items.append(item)
    }
}
```

错误如下：

![Image]({{ site.baseurl }}/assets/images/swift/02-swift-struct-02.png)

为什么会出现这样的错误呢？我们来看看 SIL 码：

![Image]({{ site.baseurl }}/assets/images/swift/02-swift-struct-03.png)

和我们上面的例子一模一样，这里的 **let 要改换成 var 才行**。那我们来变通一下：

```swift
struct Teacher {
    var items = [Int]();
    func push(item: Int) -> Void {
        var s = self
        s.items.append(item)
    }
}

var t = Teacher()
t.push(item: 1)

print(t.items)
// 结果： []
```

为啥是空呢？因为这是值传递，**变量 s 和 t  已经不是同一个了**。

所以 **mutating** 来解决这个问题：

```swift
struct Teacher {
    var items = [Int]();
    mutating func push(item: Int) -> Void {
        self.items.append(item)
    }
}

var t = Teacher()
t.push(item: 1)

print(t.items)
```

没有发现任何错误，为什么，查看一下 SIL 码：

![Image]({{ site.baseurl }}/assets/images/swift/02-swift-struct-04.png)

我们发现了几处细微的变化，其中 **$*Teacher** 可以确定不是传值，而是传递的指针。

我们来看看 **inout**
### 引用 inout

如下示例代码：

![Image]({{ site.baseurl }}/assets/images/swift/02-swift-inout-00.png)

为什么会报错，**其实函数的参数，默认是不可变的， let**。所以要使用 **inout**：

```swift
// 这里传递的就是地址了
func swap(_ a: inout Int, _ b: inout Int) {
    let temp = a
    a = b
    b = temp
}

var a = 10
var b = 20

swap(&a, &b)

print("a: \(a), b: \(b)")
// a: 20, b: 10
```

我们查看一下 SIL 码：

```swift
// swap(_:_:)
sil hidden @main.swap(inout Swift.Int, inout Swift.Int) -> () : $@convention(thin) (@inout Int, @inout Int) -> () {
// %0 "a"                                         // users: %11, %4, %2
// %1 "b"                                         // users: %14, %8, %3
bb0(%0 : $*Int, %1 : $*Int):
  //                    使用的是地址
  debug_value_addr %0 : $*Int, var, name "a", argno 1 // id: %2
  debug_value_addr %1 : $*Int, var, name "b", argno 2 // id: %3
// ...
}
```

### 结构体方法

示例如下：

```swift
struct Teacher {
    var items = [Int]();
    mutating func push(item: Int) -> Void {
        self.items.append(item)
    }
}

var t = Teacher()
t.push(item: 1)
```

我们来看一下汇编代码：

![Image]({{ site.baseurl }}/assets/images/swift/02-swift-struct-05.png)

可以无论调用的 **Teacher.init() 方法 和 Teacher.push(item: Swift.Int) -> () 方法 都是 call 一个明确的地址，没有什么方法查找什么的。**

这说明 **push** 函数就是一个静态函数，所以它并不占用结构体的空间,所以结构体方法的调用是静态方法调用。
{:.success}


