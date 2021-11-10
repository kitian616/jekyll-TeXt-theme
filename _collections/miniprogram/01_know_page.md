---
title: 了解小程序的页面逻辑
date: 2019-08-29
---

不同于 Web 页面的开发，小程序页面的开发有很多定制化的规定。例如，它不用像传统网页开发那样，需要在代码中引入样式文件（CSS）以及脚本文件（JS）,这些规则都直接封装在小程序的框架中了。

结合微信开发者工具，页面注册以及页面创建等操作，都实现了自动化。建议直接使用微信开发者工具进行小程序开发，一方面可以实时调试，另一方面避免造成莫名的错误。

### 目录结构
每一个页面对应一个目录，目录里有 4 个文件：wxml、wxss、js 以及 json，它们分别代表页面结构、样式、逻辑以及配置，4 个文件的命名，必须是一样的，否则会报错。如下图所示：

![](/image/collections/miniprogram/2019-08-29-20-19-34.png)

在微信开发者工具中，右键可以很方便的创建一个页面，选择「新建 Page」，会自动生成这 4 个文件。

![](/image/collections/miniprogram/2019-08-29-20-24-09.png)

### 事件函数
自动生成 js 文件中，会写入默认的代码，如下所示：

```js
Page({
  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
```

一个 Page 对象包含了 Data 属性，以及 8 个页面监听函数，分别是：

- onLoad：页面创建时执行
- onShow：页面显示到前台时执行
- onReady: 页面首次渲染完毕时执行
- onHide：页面从前台转到后台时执行
- onUnload：页面销毁时执行
- onPullDownRefresh：下拉刷新时执行
- onReachBottom：页面触底时执行
- onShareAppMessage：页面被用户分享时执行

除了上面 8 个监听函数，官方文档中，还列举了如下 3 个：

- onPageScroll：页面滚动时执行
- onResize：页面尺寸变化时执行
- onTabItemTap：tab 点击时执行

微信小程序以及开发工具还在高速发展阶段，很多内容还在更新中，所以，实时关注官方文档是最重要的。

### 生命周期
关于页面的生命周期，官方给了一张图，现在不太懂，没关系，随着开发的进行，会慢慢了解的。

![](/image/collections/miniprogram/2019-09-02-14-12-42.jpg)


### 页面路由
小程序的页面路由由框架进行管理，我们真正需要了解的是，**什么情况下，会触发什么事件函数？**看下面这张图：

![](/image/collections/miniprogram/2019-09-02-14-20-32.png)

Tab 切换

![](/image/collections/miniprogram/2019-09-02-14-22-47.png)

这里特别需要提醒一下的是，虽然触发的事件函数，有一定的先后顺序，但是，函数里代码的执行顺序可就不一定了。

例如：初次进入小程序，会先触发小程序的 onLaunch，然后 onShow，紧接着触发首页的 onLoad，最后触发首页的 onShow。

如果小程序的 onLaunch 里，使用 `wx.getSystemInfo` API 要获取屏幕的宽度 windowWidth，要在首页里使用，就不一定能够生效，因为 `wx.getSystemInfo` 是异步函数，可能还没有获取到 windowWidth，首页的 onShow 就已经执行完了。

### 小结
本篇主要是介绍了小程序的**页面构成**和**生命周期**。

虽然还不涉及到一行代码的编写，但是内容非常重要，建议再过一遍官方文档：[框架 | 微信开放文档](https://developers.weixin.qq.com/miniprogram/dev/framework/MINA.html)，它会给后续代码的调试带来很大的帮助。