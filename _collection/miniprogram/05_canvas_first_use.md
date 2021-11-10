---
title: 生成分享卡片 | Canvas 初使用
date: 2019-09-04
---

这是微信小程序开发频率最高的功能，绝大部分的小程序都会做这个，因为小程序页面不可以分享到朋友圈。所以，为了达到分享的目的，大家几乎都是通过生成分享卡片的方式实现。

其本质就是将页面元素，生成一张图片，并且保存到相册中。这块功能的实现需要借助 canvas 画布的 API。这也是「计算日子」这个小工具，一个关键的功能点，最终生成卡片效果如下图所示：

![](/image/collections/miniprogram/IMG_3386.JPG)

下面将以它的实现代码为例进行介绍。首先在 pages 目录下，创建 canvas 目录，新建 Page，命名 canvas，目录如下图所示：

![](/image/collections/miniprogram/2019-09-04-13-36-06.png)

编写 canvas.wxml 文件，结构很简单，一个画布元素，一个按钮，代码如下：

```html
<canvas style="width: 100%; height: 200px;" canvas-id="firstCanvas"></canvas>
<view class="bottom_btn">
  <button type="warn" bindtap="saveToImg" loading='{{ isLoading }}' disabled='{{ isDisable }}'>保存卡片</button>
</view>
```

接下来，就是最主要的部分了，使用 js 在画布上画画了，在写代码之前，先来分析一下画布上显示的内容，我们从示例图中，可以发现，有以下几个元素：

- 矩形白色卡片，带阴影
- 4 行文本：标题（新中国成立）、数字（25540）、描述（已过天数）以及目标日
- 一张二维码图片（已经放在 canvas 目录下了）

我们分别将这些元素的绘制方法，放在单独的函数中，打开 canvas.js 文件，在 Page() 函数之前，定义如下几个函数：

```js
const app = getApp();
// 获取屏幕宽度
const windowWidth = wx.getSystemInfoSync().windowWidth;
// 使用 wx.createContext 获取绘图上下文 context
let context = wx.createCanvasContext('firstCanvas');
function setTitle(title) {
  // 绘制标题
  context.setFontSize(20);
  context.setFillStyle('#333');
  context.fillText(title, 40, 60);
}
function setLabel(numberLabel) {
  // 绘制 desc
  context.setFontSize(10);
  context.setFillStyle('#999')
  context.fillText(numberLabel, 43, 95);
}
function setNumber(number, numberColor) {
  // 绘制数字
  context.setFontSize(40);
  context.setFillStyle(numberColor)
  context.fillText(number, 40, 135);
}
function setDate(date) {
  // 绘制目标日
  context.setFontSize(10);
  context.setFillStyle('#999')
  context.fillText('目标日：' + date, 43, 165);
}
function setRectAndQrcode() {
  // 绘制背景
  context.setFillStyle('#fff');
  context.setShadow(5, 5, 12, '#999');
  context.fillRect(20, 20, windowWidth - 40, 160);
  context.setShadow(0, 0, 0, '#fff');
  // 绘制二维码
  context.drawImage('./bytefactory.jpg', windowWidth - 145, 55, 100, 100);
}
``` 

画布定义高度为 200 px，宽度为屏幕宽度（通过 `wx.getSystemInfoSync().windowWidth` API 获取），使用 `wx.createCanvasContext` 创建画布对象，然后就是使用具体的 API 进行绘制了，具体 API 的使用，请打开官方文档，对照查看。

上面的几个函数，只是 context 对象的一些设置，接下来再定义一个函数 `drawCard()` 用来绘制图形，代码如下：

```js
function drawCard(title, date, number, isPast) {
  var numberLabel = isPast === true ? '已过天数' : '剩余天数';
  var numberColor = isPast === true ? '#3cc51f' : '#e64340';
  setRectAndQrcode();
  setTitle(title);
  setLabel(numberLabel);
  setNumber(number, numberColor);
  setDate(date);
  // 绘制
  context.draw();
}
```

将一些变量提取出来，通过参数形式传入。

最后，在 `onShow` 页面监听函数中，加一行代码就搞定了，当显示页面的时候，执行 `drawCard` 函数，绘制图形。

```js
onShow: function () {
  drawCard(this.data.title, this.data.date, this.data.number, this.data.isPast);
},
```

绘制完卡片后，canvas 有一个导出图片的 API，然后调用保存图片到相册的 API，就可以了。视图中有一个按钮，绑定了 saveToImg 的函数，代码如下：

```js
saveToImg: function () {
  this.setData({
    isLoading: true
  });
  wx.canvasToTempFilePath({
    canvasId: 'firstCanvas',
    fileType: 'jpg',
    quality: 1,
    success: res => {
      wx.saveImageToPhotosAlbum({
        filePath: res.tempFilePath,
        success: res => {
          this.setData({
            isLoading: false
          });
          wx.showToast({
            title: '保存成功'
          })
        }
      });
    }
}
```

- 导出文件 API：[wx.canvasToTempFilePath](https://developers.weixin.qq.com/miniprogram/dev/api/canvas/wx.canvasToTempFilePath.html)
- 保存相册 API：[wx.saveImageToPhotosAlbum](https://developers.weixin.qq.com/miniprogram/dev/api/media/image/wx.saveImageToPhotosAlbum.html)

具体使用方法，请点击查看官方文档，学会查看文档尤其重要。

最后，留一个思考题，假设，我不想在卡片上显示目标日，该如何实现定制卡片的需求？

给个提示：尝试使用 [clearRect](https://developers.weixin.qq.com/miniprogram/dev/api/canvas/CanvasContext.clearRect.html)，当然页面也要有对应的修改。最终效果如下图：

![](/image/collections/miniprogram/2019-09-04-15-20-19.png)

![](/image/collections/miniprogram/IMG_3390.JPG)

假设尝试过了，还是不行，[点这里](https://github.com/pengloo53/miniprogram-demos)查看源代码。
