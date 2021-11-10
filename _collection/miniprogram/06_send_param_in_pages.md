---
title: 页面数据（参数）传输的方式
date: 2019-09-05
permalink: /collection/miniprogram/06_send_param_in_pages
---

上一篇，我们学会了如何制作简单的分享卡片，并保存在相册中。示例中，我们单独创建了 canvas 页面，卡片内的数据是直接在页面中写死的，当然这只是作为示例，实际情况中，我们的数据应该是从其他页面传输过来。所以，今天就来谈谈小程序页面之间传参的方式都有哪些？

下面就接着上一篇的例子，继续完善程序，我们在 pages 目录下，新建 days 目录，然后在 days 目录下，新建 Page index 用来显示日期列表，目录结构如下图：

![](/image/collections/miniprogram/2019-09-05-15-12-31.png)

index 页面的代码实现，就不多介绍了，它就是一个显示日期的列表，大概样子如下：

![](/image/collections/miniprogram/2019-09-04-10-43-41.png)

建议你能够照着上面这个样子，把代码写出来，不要认为看着简单，就不想动手，写出来才是自己的。

这里只贴出 wxml 的代码如下：

```html
<view class="list" bindtap='goto' data-title="新中国成立 70 周年" data-date="2019-10-01" data-isPast="{{ true }}" data-number="26">
  <view class="title">新中国成立 70 周年</view>
  <view class="danger">26</view>
  <view class="date-desc">还剩天数</view>
  <view class="desc">2019-10-01</view>
</view>
```

数据是写死在页面中的，这里先不用管，重点看数据的传输，这里的数据传输有两层：

第一层，是从视图层传输到逻辑层，采用的方法，前面已经介绍过，就是通过 `data-*` 属性的方式，例如上面例子中，`data-title`、`data-date`、`date-isPast`以及`date-number`，分别将 `tilte`、`date`、`isPast`、`number` 字段传输到逻辑层。

逻辑层如何获取呢？看 js 中 `goto` 函数的代码：

```js
goto: function(e){
  let date = e.currentTarget.dataset.date;
  let title = e.currentTarget.dataset.title;
  let isPast = e.currentTarget.dataset.ispast; // 注意这里的参数名
  let number = e.currentTarget.dataset.number;
  console.log(e.currentTarget.dataset); // 打印出来试试
  wx.navigateTo({
    url: '../canvas/canvas?title=' + title + '&date=' + date + '&isPast' + isPast + '&number=' + number
  })
},
```

上面这个函数做的事情，就是参数的「一收一发」，获取页面传过来的参数，然后转发出去。**需要注意的一点是**，`data-*` 参数名的一些规则，如果 `*` 中有大写字母，将自动转成小写；如果出现短横杠，会自动转成驼峰变量，例如：`data-is-past` 变成 `isPast`，记不住没关系，打印出来试试就知道了。

第二层，是从 A 页面传输到 B 页面，页面的跳转，官方提供了以下几种 API：

![](/image/collections/miniprogram/2019-09-05-15-58-12.png)

具体用法看[文档](https://developers.weixin.qq.com/miniprogram/dev/api/route/wx.switchTab.html)，这里我们使用的是 `wx.navigateTo`，在 URL 后面加上 `?key1=value1&key2=value` 的方式，就可以将参数传输到下一个页面。

紧接着的问题就是，下一个页面如何接收参数，答案是：通过页面的 onLoad 事件函数。

打开 canvas 页面的 js 文件，编辑 onLoad 函数如下：

```js
onLoad: function (options) {
  let title = options.title;
  let date = options.date;
  let isPast = options.isPast;
  let number = options.number;
  if(title && date){
    this.setData({
      title,
      date,
      isPast,
      number
    })
  }
},
```

看代码一目了然，参数直接保存在 options 对象中了。

写到这里，我们的 canvas 页面，可以算作是动态页面了，通过其他页面传输过来的参数，显示不一样的卡片。

### 总结
到这篇文章为止，我们已经知道了，小程序页面数据 or 参数传输的一些方式，总结一下就是：

1. 将数据从逻辑层传输到视图层，通过 `this.setData()` 函数；
2. 将数据从视图层传输到逻辑层，通过 `data-*` 的方式；
3. 将数据从 A 页面传输到 B 页面，通过页面跳转 url 参数的方式；

接收数据的方法，对应为：

1. 视图层使用 `{{ value }}` 语法；
2. 使用回调函数的 `e` 对象，参数保存在 `e.currentTarget.dataset` 里
3. 使用页面的 onLoad 事件函数，参数保存在 `options` 对象里

以上方式，应该能够满足绝大多数页面数据传输的需求，当然还有一些极端情况，例如：url 中保存参数值的长度是有限度的。如果出现类似情况，该如何解决？

其实，我们还有更多更灵活的方式，例如借助缓存的功能，再或者借助数据库。后续如果涉及到具体案例，会再进行介绍。