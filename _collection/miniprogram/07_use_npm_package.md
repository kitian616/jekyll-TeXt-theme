---
title: 如何使用第三方 npm 扩展包
date: 2019-09-09
---

上一篇传参的示例中，不知道你是否注意到，像 `isPast`（判断日期是否为过去） 或者 `number`（距离当前的天数） 参数，根本就不需要传输嘛，这些应该是计算出来的。

然而，如果让你来写日期计算的 API，我相信绝大多数开发者，都不一定能够写出来，好在，这块有成熟的库可以使用，例如：`moment.js`，还有 `dayjs`，这篇文章就来介绍一下，如何在小程序中使用第三方 npm 扩展？

这块内容，官方文档已经写得比较清楚了，在[工具 — 开发辅助 — npm 支持](https://developers.weixin.qq.com/miniprogram/dev/devtools/npm.html)下，目录层次比较深，所以，在最开始的时候，建议通读一遍文档，还是非常有必要的。

### npm 支持的要求
小程序并非从一开始就支持 npm，而是在**基础库版本 2.2.1 或以上**、及**开发者工具 1.02.1808300 或以上**的时候，才开始支持。

所以，这两个条件一定要注意，当然，如果才开始学习小程序开发，用最新版的就可以了。

### 安装 dayjs
用命令行进入项目目录，使用下面命令安装 dayjs

```sh
npm init
npm i dayjs --production
```

第一条命令是初始化 npm，第二条命令是安装 dayjs 包。

然后打开开发者工具，在本地设置中，勾选**使用 npm 模块**，如下图：

![](/image/collections/miniprogram/2019-09-09-15-22-45.png)

最后，打开工具菜单，点击构建 npm 即可。

![](/image/collections/miniprogram/2019-09-09-15-28-33.png)

### 目录变化
构建完成后，项目目录中会多出几个文件：

- package.json
- package-lock.json
- node_modules
- miniprogram_npm

前面三个是 npm 初始化时，默认生成的，最后一个目录是开发者工具构建 npm 时生成的，可以理解为小程序版的 npm 包。

![](/image/collections/miniprogram/2019-09-09-15-30-18.png)

使用方法与 node.js 开发一致，下面就来完善上一篇中的例子。

### 页面改写
打开 days 的 index.wxml 文件，将页面静态内容替换成变量，代码如下：

```html
<view class="list" bindtap='goto' data-title="{{ day.title }}" data-date="{{ day.date }}" data-isPast="{{ day.isPast }}" data-number="{{ day.number }}">
  <view class="title">{{ day.title }}</view>
  <view class="date-number {{ day.isPast?'primary':'danger' }}">{{ day.number }}</view>
  <view class="date-desc">{{ day.isPast?"已过天数":"还剩天数" }}</view>
  <view class="desc">{{ day.date }}</view>
</view>
```

可以看出，从逻辑层传过来一个 day 的对象。

逻辑层添加 day 对象数据，打开 days 目录的 index.js 文件，编写代码如下：

```js
data: {
  day: {
    date: '2019-10-01',
    title: '新中国成立 70 周年'
  }
},
```

这里只有 date 和 title 属性值，并没有 isPast 和 number，因为 isPast 和 number 值应该是实时计算出来的，而无需储存。

当前的页面是不完整的，接下来就是 `dayjs` 上场的时候了。

### 引入 dayjs 初始化数据
接着编辑 js 代码，引入 dayjs 模块，编写初始化数据函数，如下代码所示：

```js
// 引入 dayjs
const dayjs = require('dayjs');
// 当天日期
const today = dayjs().format('YYYY-MM-DD');
// 初始化数据
const init_data = function(day){
  let date = day.date;
  day.isPast = today > date;
  day.number = dayjs(date).diff(dayjs(today), 'day');
  return day;
}
```

dayjs 的用法不多介绍了，可自行查看文档，`init_data` 函数在初始数据上，添加了两个参数 isPast 和 number，使用 dayjs 模块提供的 API 可以快速计算它们的值。

最后，在页面事件函数 `onShow` 中，调用初始化函数，代码如下：

```js
onShow: function () {
  this.setData({
    day: init_data(this.data.day)
  });
},
```

这样，整体代码就写完了，只需要一个 `title` 和 `date`，就可以实时计算出相隔的天数，引入 `dayjs` 模块，让这个计算的过程，简单了许多。

### 总结
这篇文章介绍了 npm 包的引入，安装以及使用。并实际演示了 dayjs 日期库的使用，进而完善了上一篇中的例子。

这里留一个作业题，看能不能结合前面的内容，完成一个完整的「计算日子」功能。

PS. 目前 demo 中只缺少添加日期，显示日期列表，以及存储日期的功能了，正好这些知识点，前面已经都介绍过了。

- - - - - 

写完这篇文章，距离国庆节还剩下 22 天。

![](/image/collections/miniprogram/wxf51bdbc02e495a2b.o6zAJs72N3pGGs-m3FtEnWYH4dV0.tTNlyGcwPgPS26c6ad682e91781e721148c3068d6ee2.jpg)