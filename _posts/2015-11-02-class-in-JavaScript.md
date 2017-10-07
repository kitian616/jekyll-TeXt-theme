---
layout: post
title: JavaScript 的 prototype（原型）和类
key: 10004
tags: JavaScrip
category: blog
date: 2015-11-02 23:00:00 +08:00
modify_date: 2017-09-11 21:00:00 +08:00
---

最近在学习 JavaScript。它是一个让我倍感惊喜的语言，它不仅打开我函数式语言学习的大门，而且它关于原型的机制也是让我眼前一亮——原来面向对象还可以这么弄。

<!--more-->

JavaScript 中的所有事物都是对象：字符串、数值、数组、函数。这一点跟 Java、Python 这些面向对象的语言没有太大区别。但是 JavaScript 基于原型（prototype）的继承机制，使得 JavaScript 在实现面向对象编程时与 Java 这样的语言有很大的不同。

一般的面向对象语言通常是使用 Class 关键字来定义类的，但是很遗憾，JavaScript（ES5） 并没有像 Class 这样的关键字。那么 JavaScript 是怎样定义类和对象的呢?

## Javascript的对象

前面我们说过 JavaScript 中的所有事物都是对象，那么我们可以这样创建对象。

{% highlight javascript %}
var lilei = {};
document.write(typeof(lilei));    //object
{% endhighlight %}

我们定义了一个叫 lilei 的字典，我们可以看到他确实是一个对象。

接下来，我们给 lilei 添加属性 name 和 sex，表示一个叫李雷的男性。再添加 speak 方法来表示李雷说话的动作。

{% highlight javascript %}
var lilei = {};
lilei.name = "李雷";
lilei.sex = "M";
lilei.speak = function(){ document.write("Hi, I'm Li Lei!"); }

//_MAIN
document.write(lilei.name); //李雷
lilei.speak(); //Hi, I'm Li Lei!
{% endhighlight %}

这样，我们的 lilei 对象既有了名字，也有了性别，还学会了说话。

接下来我们还想添加一个对象来代表韩梅梅，但是问题来了，我们不得不像 lilei 对象一样添加属性，方法，这些代码基本都是相同的。很明显，按照面向对象的思想，我们应该创建一个 People 类来代表抽象的人，再来通过人这个类来生成具体的对象，JavaScript 又是如何创建类的呢？

## Javascript的类

首先从 Prototype（原型）说起。

下面的示例代码中，我创建了一个 Person 函数来充当构造函数，并且向函数的 prototype 中加入了 name 和 sex 属性以及一个 speak 函数。最后使用 new 关键字创建了 hanmeimei 对象。其实这个代码还不完美，这里仅仅用来说明原型，后面还会改进。

{% highlight javascript %}
var Person = function(){}
Person.prototype.name = "韩梅梅";
Person.prototype.sex = "F";
Person.prototype.speak = function(words) { document.write(words); }

//_MAIN
var hanmeimei = new Person();
document.write(hanmeimei.name); //韩梅梅
hanmeimei.speak("Hi, I'm Han Meimei!"); //Hi, I'm Han Meimei!
{% endhighlight %}

这种使用 new 关键字创建对象的方式在 JavaScript 中使用非常广泛，说来也怪，在一个没有 Class 关键字的语言中居然有创建对象的 new 关键字。

### prototype 和 \_\_proto\_\_ 属性

看到这里大家可能会很困惑，这个 prototype 究竟是如何将属性和函数传递给对象的呢？多说无益，直接上图。

**图中用椭圆表示普通对象，用圆角矩形来表示函数对象**。

![模型图](https://ww2.sinaimg.cn/large/73bd9e13jw1expb5r0bn4j20eo0bedg9.jpg)

之前说过 JavaScript 中的所有事物都是对象，Person 函数也是对象，是普通对象的扩充。JavaScript 的函数对象有一个特殊的 prototype 属性，它指向了一个普通对象。一般的普通对象有一个 \_\_proto\_\_ 属性，当然函数对象也由该属性。

当使用 new 关键字创建新对象的时候，发生了三件事情：

- 一个叫 hanmeimei 的空对象被创建，首先 hanmeimei 与{}一致；
- 将 hanmeimei 的 \_\_proto\_\_ 属性指向 Person 的原型属性，constructor 属性指向 Person 函数对象；
- Person 方法被执行。当然此时 Person 函数的方法是空的。

这样通过构造函数生成的对象就通过 \_\_proto\_\_ 属性与构造函数的原型关联了起来。当我们调用 hanmeimei 的 speak 函数时，浏览器会首先在 hanmeimei 对象中查找 speak 函数，找不到，然后再到 \_\_proto\_\_ 所指向的对象中查找，找到了，然后调用函数。

 \_\_proto\_\_ 可以看成一种继承关系， \_\_proto\_\_ 所指向的对象可以看成是该对象的父对象。或者说， \_\_proto\_\_ 所指向的对象相当于是一个模板，而该对象是对这个模板的修改和扩充。

## Javascript 中的 this 变量

前面说过，上面示例代码是不完美的。我们想一下，在上一个示例中，我们每创建一个对象，对象的名称都叫“韩梅梅”，这显然是不合适的。

我们可以这么写。

{% highlight javascript %}
var Person = function(name, sex){}
Person.prototype.name = name;
Person.prototype.sex = sex;
……
{% endhighlight %}

但是仔细思考，这也有问题，每次使用 new 构造对象时，都会对原型中的属性赋值。在我们构建“李雷”时，“人类”的名字是“李雷”，而在我们构建“韩梅梅”时，“人类”的名字又变成了“韩梅梅”。人类（原型）的名字不断的改变，这显然也是不太对的。

{% highlight javascript %}
var lilei = new Person("李雷", "M");
var hanmeimei = new Person("韩梅梅", "F");
{% endhighlight %}

我们可以使用 this 变量来解决这个问题。

{% highlight javascript %}
var Person = function(name, sex){
    this.name = name;
    this.sex = sex;
}
Person.prototype.speak = function(words) { document.write(words); }

//_MAIN
var lilei = new Person("李雷", "M");
document.write(lilei.name); //李雷
lilei.speak("Hi, I'm Li Lei!"); //Hi, I'm Li Lei!
var hanmeimei = new Person("韩梅梅", "F");
document.write(hanmeimei.name); //韩梅梅
hanmeimei.speak("Hi, I'm Han Meimei!"); //Hi, I'm Han Meimei!
{% endhighlight %}


在这个示例中，当我们使用 new 关键字创建新对象的最后，Person 方法执行，将新对象 lilei 作为 this 引用传递，于是该对象就添加上了 name 和 sex 属性。

![改进的模型图](https://ww2.sinaimg.cn/large/73bd9e13jw1expb5qrg0ej20df0c7t95.jpg)

此时，name 和 sex 属性并不在 Person.prototype 中，而是 Person 构造函数为每个生成的对象添加了 name 和 sex 属性。这些属性是完全属于生成的对象的，构造函数只是执行了“添加属性”的指令。

这样，每个人都有名字和性别属性（为了简单起见，就姑且这么认为吧），然而每个人的名字的值和性别的值是不一样的。人的名字和性别属于他/她自己，人类只负责给每个人起名和确定性别。

这样这个 Person 类就比较合乎逻辑了，当然还有其他的方法来构建类，这里也可以看出 JavaScript 的灵活性。

## 附加参考

上面的示意图，为了突出重点，其实省略了不少的细节。这里附上完整的结构示意图，作为参考。

![总示意图](https://ww1.sinaimg.cn/large/73bd9e13jw1expb5rhnvgj20j00n93zx.jpg)

其实，name、sex 和 speak 函数也是对象，它们同样有 prototype 和 constructor 属性，图中并没有画出。lilei 对象跟 hanmeimei 相同，故省略。

本博文的示意图都是我经过测试得到的，网上的资料都有一些问题和矛盾，所以就自己动手了。这个过程虽然费时但确实很有意义，有时间的朋友可以[下载测试代码](https://github.com/kitian616/practice_workspace/blob/master/javascript/js/proto_test.js)研究一下 :-)

我使用的是 Google Chrome（version: 46），不同的浏览器结果可能会有所不同。