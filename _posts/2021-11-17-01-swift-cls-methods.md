---
title: Swift 对象方法
tags: Swift
---

类声明中的方法是通过 V-table 来进行调度的
{:.success}

`V-table`{:.info} 在 SIL 中是如下表示的：

```swift
decl ::= sil-vtable
sil-vtable ::= 'sil_vtable' identifier '{' sil-vtable-entry* '}'
sil-vtable-entry ::= sil-decl-ref ':' sil-linkage? sil-function-na
me
```

如下示例代码

```swift
class Subject {
    var age: Int = 15
    var name: String = "小明"
    func method0() -> Void { print("method0")}
    func method1() -> Void { print("method1")}
    func method2() -> Void { print("method2")}
}

var t = Subject()
t.method0()
t.method1()
t.method2()
print(t)
```

来看下生成的 SIL 码：

```swift
sil_vtable Subject {
  #Subject.age!getter: (Subject) -> () -> Int : @main.Subject.age.getter : Swift.Int	// Subject.age.getter
  #Subject.age!setter: (Subject) -> (Int) -> () : @main.Subject.age.setter : Swift.Int	// Subject.age.setter
  #Subject.age!modify: (Subject) -> () -> () : @main.Subject.age.modify : Swift.Int	// Subject.age.modify
  #Subject.name!getter: (Subject) -> () -> String : @main.Subject.name.getter : Swift.String	// Subject.name.getter
  #Subject.name!setter: (Subject) -> (String) -> () : @main.Subject.name.setter : Swift.String	// Subject.name.setter
  #Subject.name!modify: (Subject) -> () -> () : @main.Subject.name.modify : Swift.String	// Subject.name.modify
  #Subject.method0: (Subject) -> () -> () : @main.Subject.method0() -> ()	// Subject.method0()
  #Subject.method1: (Subject) -> () -> () : @main.Subject.method1() -> ()	// Subject.method1()
  #Subject.method2: (Subject) -> () -> () : @main.Subject.method2() -> ()	// Subject.method2()
  #Subject.init!allocator: (Subject.Type) -> () -> Subject : @main.Subject.__allocating_init() -> main.Subject	// Subject.__allocating_init()
  #Subject.deinit!deallocator: @main.Subject.__deallocating_deinit	// Subject.__deallocating_deinit
}
```

可以看到方法按照顺序排列在 V-table 中。

我们调试一下调用过程：

![]({{ site.baseurl }}/assets/images/swift/03-swift-method-00.png)

我画了如下图来加强理解：

![]({{ site.baseurl }}/assets/images/swift/03-swift-method-01.png)

也可以在 **Swift-source** 中看到初始化的时候，按顺序取加载方法：

```c++
static void initClassVTable(ClassMetadata *self) {
  const auto *description = self->getDescription();
  auto *classWords = reinterpret_cast<void **>(self);

  if (description->hasVTable()) {
    auto *vtable = description->getVTableDescriptor();
    auto vtableOffset = vtable->getVTableOffset(description);
    for (unsigned i = 0, e = vtable->VTableSize; i < e; ++i)
      classWords[vtableOffset + i] = description->getMethod(i);
  }
  // ......
 }
```

### extension

如下示例代码，增加了 Subject 对象的扩展方法：

```swift
extension Subject {
    func method3() -> Void { print("method3")}
}
```

查看  SIL 码：

```swift
extension Subject {
  func method3()
}

sil_vtable Subject {
	// ...
  #Subject.method0: (Subject) -> () -> () : @main.Subject.method0() -> ()	// Subject.method0()
  #Subject.method1: (Subject) -> () -> () : @main.Subject.method1() -> ()	// Subject.method1()
  #Subject.method2: (Subject) -> () -> () : @main.Subject.method2() -> ()	// Subject.method2()
// ...
}
```

可以看到 V-table 中并没有方法 **method3**。

我们可以调试一下：

![]({{ site.baseurl }}/assets/images/swift/03-swift-method-02.png)

当调用 **method3** 的时候，直接调用的是确切的地址，而不是使用 V-table 去调度，这说明 method3 是静态方法。
{:.warning}

### final 关键字

final关键字在大多数的编程语言中都存在，表示不允许对其修饰的内容进行继承或者重新操作。Swift中，final关键字可以在class、func和var前修饰。

通常大家都认为使用final可以更好地对代码进行版本控制，发挥更佳的性能，同时使代码更安全。下面对这些说法做个总结：

1. 想通过使用final提升程序性能 - 效果有限
通常认为final能改成性能，因为编译器能从final中获取额外的信息，因此可以对类或者方法调用进行额外的优化处理。但这中优化对程序性能的提升极其有限。
所以如果抱着提升性能的想法，就算把所有不需要继承的方法、类都加上final关键字，也没多大作用。还不如花时间去优化下程序算法。

2. final正确的使用场景 - 权限控制
也就是说这个类或方法不希望被继承和重写，具体情况如下：

（1）类或者方法的功能确实已经完备了
这种通常是一些辅助性质的工具类或者方法，特别那种只包含类方法而没有实例方法的类。比如MD5加密类这种，算法都十分固定，我们基本不会再继承和重写。

（2）避免子类继承和修改造成危险
有些方法如果被子类继承重写会造成破坏性的后果，导致无法正常工作，则需要将其标为final加以保护。

（3）为了让父类中某些代码一定会执行

父类的方法如果想要其中一些关键代码在继承重写后仍必须执行（比如状态配置、认证等）。我们可以把父类的方法定义成final，同时将内部可以继承的部分剥离出来，供子类继承重写。

### @objc
如下示例代码：

```swift
class Person: NSObject {
    func method0() -> Void {
        print("method0");
    }
    @objc func method1() -> Void {
        print("method1");
    }
}
```

如果只是添加 **@objc** ，OC 中并不能使用 **Person** 类，头文件中也没有对应的类声明

![]({{ site.baseurl }}/assets/images/swift/03-swift-method-03.png)

```objc
SWIFT_CLASS("_TtC8ObjcTest6Person")
@interface Person : NSObject
- (void)method1;
- (nonnull instancetype)init OBJC_DESIGNATED_INITIALIZER;
@end
```

如上图所示，只有被标记 **@objc** 的方法才能被 OC 调用。

我们可以看下生成的 SIL 码：

```swift
@objc @_inheritsConvenienceInitializers class Person : NSObject {
  func method0()
  @objc func method1()
  @objc deinit
  override dynamic init()
}
```

以及对应的 **V-table**：

```swift
sil_vtable Person {
  #Person.method0: (Person) -> () -> () : @Test.Person.method0() -> ()	// Person.method0()
  #Person.method1: (Person) -> () -> () : @Test.Person.method1() -> ()	// Person.method1()
  #Person.deinit!deallocator: @Test.Person.__deallocating_deinit	// Person.__deallocating_deinit
}
```

在分析 SIL 的时候发现一个问题：

```swift
// Person.method1()
sil hidden @Test.Person.method1() -> () : $@convention(method) (@guaranteed Person) -> () {
// %0 "self"                                      // user: %1
bb0(%0 : $Person):
  debug_value %0 : $Person, let, name "self", argno 1 // id: %1
  // ...
  return %25 : $()                                // id: %26
} // end sil function 'Test.Person.method1() -> ()'

// @objc Person.method1()
sil hidden [thunk] @@objc Test.Person.method1() -> () : $@convention(objc_method) (Person) -> () {
// %0                                             // users: %4, %3, %1
bb0(%0 : $Person):
  strong_retain %0 : $Person                      // id: %1
  // function_ref Person.method1()
  %2 = function_ref @Test.Person.method1() -> () : $@convention(method) (@guaranteed Person) -> () // user: %3
  %3 = apply %2(%0) : $@convention(method) (@guaranteed Person) -> () // user: %5
  strong_release %0 : $Person                     // id: %4
  return %3 : $()                                 // id: %5
} // end sil function '@objc Test.Person.method1() -> ()'

```

在 OC 中并没有直接调用 **Person.method1( )** 方法，而是调用了 **@objc Person.method1( )** 方法，其包装了 **Person.method1( )** 方法。

但在 Swift 中是走 **V-table** 调用的 **Person.method1( )** 方法。

目前尚不知道这种机制的目的，后续待分析。

如果在扩展中标记 **@objc**呢？

```swift
extension Person {
    @objc func method5() -> Void {
        print("method5");
    }
}
```

结果是走消息发送。


### dynamic
首先一点，被标记 **dynamic** 的方法仍然走 **V-table**，但是它可被替换，如下示例：

```swift
class Person {
    dynamic func method0() -> Void {
        print("method0");
    }
    @objc func method1() -> Void {
        print("method1");
    }
}

extension Person {
    @_dynamicReplacement(for: method0)
    func method2() -> Void {
        print("method2");
    }
}

let p = Person()
p.method0()

// method2
```

被标记 **dynamic** 的方法，OC 端仍然不能调用。

### @objc 和 dynamic

如果一个方法被 **@objc 和 dynamic** 同时标记：

```swift
class Person {
    dynamic func method0() -> Void {
        print("method0");
    }
    @objc dynamic func method1() -> Void {
        print("method1");
    }
}
```

你会发现它会走消息发送的流程：

```swift
->  0x100002750 <+48>: movq   0x5bc9(%rip), %rax        ; SwiftDemo.p : SwiftDemo.Person
    0x100002757 <+55>: movq   0x5a2a(%rip), %rsi        ; "method1"
    0x10000275e <+62>: movq   %rax, %rdi
    0x100002761 <+65>: callq  0x100003c04               ; symbol stub for: objc_msgSend
```

我们看下它的 **V-table** ：

```swift
sil_vtable Person {
  #Person.method0: (Person) -> () -> () : @main.Person.method0() -> ()	// Person.method0()
  #Person.init!allocator: (Person.Type) -> () -> Person : @main.Person.__allocating_init() -> main.Person	// Person.__allocating_init()
  #Person.deinit!deallocator: @main.Person.__deallocating_deinit	// Person.__deallocating_deinit
}
```

只剩下一个 **method0** 了。

## 关于方法派发

### 一些概念

#### 静态派发

- 有时也被称为*直接调用/派发*。
- 如果一个方法是静态派发的，编译器就可以在编译时找到指令所在的位置。这样，当调用这种函数时，系统将直接跳转到此函数的内存地址以执行操作。这种直接行为导致执行速度非常快，并且还允许编译器执行各种优化，例如*内联*。实际上，由于性能的巨大提高，编译管道中存在一个阶段，在此阶段，编译器将在适用的情况下尝试使函数静态化。这种优化称为**去虚拟化**[[1\]](#note1)。

#### 动态派发

- 使用这种方法，程序直到运行时才知道要选择哪种实现。
- 尽管静态派发是极为轻量的，但它限制了灵活性，特别是在多态方面。这也是为什么动态派发被 OOP 语言广泛支持的原因
- 每种语言都有其自己的机制来支持动态调度。 Swift提供了两种实现动态性的方法：*table 派发（表派发）\*和*message 派发（消息派发）*。

##### **Table 派发** （表派发）

- 这是编译语言中最常见的选择。通过这种方法，一个类与一个所谓的 **virtual table** 相关联，虚拟表包含了指向对应于该类的实际实现的函数指针数组。
- 请注意，vtable 是在编译时构造的。因此，与静态派发相比，真多了两个附加指令（`read`和`jump`）。从理论上讲，表派发应该也很快。

##### **Message 派发**（消息派发）

- 实际上，正是由 Objective-C 提供的这种机制（有时这被称作消息传递[[2\]](#note2)），Swift 代码仅使用了 Objective-C 运行库。每次调用 Objective-C 方法时，调用都会传递给 `objc_msgSend` ，由后者负责处理查找工作。从技术上讲，运行时从给定类开始，抓取类的层次结构以便确认调用哪个方法。
- 与表派发不同的是，*message passing dictionary* 在运行时可以发生修改，从而使我们能够在运行时调整程序的行为。利用这一特点，*Method swizzling* 成为最流行的技术。
- 消息派发是三种派发（实际上是 4 种）中最具动态性的。作为交换，尽管系统通过实现缓存机制来保障查找的性能，但是其解决实现的成本可能会稍微高一些。
- 这种机制是 Cocoa 框架的基石。查看代码 Swift 的源码，你会发现 KVO 就是利用 swizzling 实现的。

### 不同方法的派发方式

如下示例列举了一些派发的方式：

```swift
protocol Other {
    func eat() // static
    func work() // table
    func study() // table
}

extension Other {
    func eat() { print("eat") } // static
    func sleep() { print("sleep") } // static
    func study() { print("Other study") } // static
}

class Person: Other {
    dynamic func method0() { print("method0"); } // table
    @objc func method2() { print("method2"); } // table
    @objc dynamic func method3() { print("method3"); } // message
    func work() { print("work") } // table
    func study() { print("Person study") } // table
}

extension Person {
    @_dynamicReplacement(for: method0)
    func method4() { print("method4"); } // static
    @objc func method5() { print("method5"); } // message
    
    dynamic func method6() { print("method6"); } // static
    @objc dynamic func method7() { print("method7"); } // message
}
```

#### 原则是什么？

- 优先考虑直接调用（静态派发）。
- 如果需要覆盖，则表派发是下一个候选。
- 需要对 Objective-C 进行覆盖和可见性吗？然后发送消息。

## 参考

[Swift 中的方法派发](https://juejin.cn/post/6968799729853399053)





