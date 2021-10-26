---
title: 页面渲染传参的方式 - Node实战
date: 2017-06-06
tags: [node,flash]
---

动态页面的开发，基本会涉及一个比较关键的问题，那就是传参。后台把不同的参数传递给前台，前台页面根据不同的参数显示不同的页面效果，这叫做渲染。

不同的后台开发环境均有不同的渲染方式，Node开发过程中，传递参数的方式非常简单直观。直接就是渲染模版即可，如下代码：

```js
res.render('error', {
  message: err.message,
  error: err
});
```

渲染页面模版`error`，给它传递了2个参数。这样前台页面模版就可以使用这两个参数了。如下`error`页面代码：

```html
<h1><%= message %></h1>
<h2><%= error.status %></h2>
<pre><%= error.stack %></pre>
```

当然页面上不仅仅只可以使用render方法传递的那几个参数，如果你使用的是`express`框架的话，你还可以通过`app.locals`以及`res.locals`对象赋值的方式传递其他的参数到页面模版引擎。例如：

```js
router.use(function (req, res, next) {
  res.locals.period = moment().day(5).format('YYYYMMDD');  // 得到本周五的日期
  res.locals.prePeriod = moment().day(5 - 7).format('YYYYMMDD'); // 得到上周五的日期
});
```

这个路由方法，会将`period`以及`prePeriod`两个变量传递到该路由下所有路径的模版引擎上。在该路由下的所有页面模版上均可以使用这两个变量。

**但是，存在另外一种情况，页面跳转的时候，参数该什么传递呢？**

页面的跳转，例如：`res.redirect('/login')`，这条语句是把请求跳转到另外一个路径上；或者，`res.redirect('back')`返回上一页。页面的跳转相当于重新访问一遍目标路径的路由。这种情况下，我们想传递参数，通过`redirect`方法貌似不可以实现。可能有人会想到使用`session`对象来作为一个中介进行传参，参数展现后就自动删除该条`session`，没错，这就是`connect-flash`模版所实现的功能。

具体可参考：
1. [Express框架之connect-flash详解 - 高山上的鱼 - 博客频道 - CSDN.NET](http://blog.csdn.net/liangklfang/article/details/51086607)。
2. [connect-flash 用法详解 - 云库网](http://yunkus.com/connect-flash-usage/)

原理和使用方法都已经有了，我就不赘述了，我的使用场景一般都是当进入一个没有权限访问的页面时，跳转到登陆界面所提示信息的展现。好多场景其实是可以通过前台Ajax来取代的，例如：登陆界面的验证等等。

### 总结
Node Web开发中，后台三种传参渲染页面的方式：

1. 普通页面渲染使用`res.render()`方法；
2. 还有别忘了`express`提供的`app.locals`以及`res.locals`对象；
3. 针对跳转页面的场景可以尝试使用`connect-flash`模块