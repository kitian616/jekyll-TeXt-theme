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

当调用 **method3** 的时候，直接调用的是确切的地址，而不是去 V-table 中去调用。
{:.success}

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



### dynamic

### hook



