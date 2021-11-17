---
title: Swift 对象方法
tags: Swift
---

类声明中的方法是通过 V-table 来进行调度的
{:.success}

**V-table** 在 SIL 中是如下表示的：

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

查看 **Swift-source** 源码：

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



