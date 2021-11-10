---
title: 实现页面交互 | 地图 API 的使用
date: 2019-09-02
permalink: /collection/miniprogram/03_map_api_use
---

在前面一篇，我们已经写完了 address 页面，页面中显示的，并非是地址数据，那是直接写在 data 中的测试数据，并没有实际意义。

接下来，我们将调用微信小程序提供的地图 API 获取地址数据。

### 添加一个按钮
在 `address.wxml` 中，加入一个按钮，代码如下：

```html
<view class="bottom_btn">
  <button type="primary" bindtap='add'>添加常用地址</button>
</view>
```

在 `address.wxss` 中，添加样式代码如下：

```css
.bottom_btn{
  position: absolute;
  bottom: 30rpx;
  width: 90%;
  left: 5%;
}
```

重新进入 address 页面，显示效果如下图：

![](/image/collection/miniprogram/2019-09-02-21-25-30.png)

按钮中，绑定了一个 add 的事件函数，当我们点击按钮的时候，将调用 add 函数。

### API 调用：选择地址
打开 `address.js` 文件，编写 add 函数，函数与页面事件函数在同一层级，代码如下：

```js
add: function () {
  wx.chooseLocation({
    success: res => {
      var address = {
        title: res.name,
        address: res.address,
        latitude: res.latitude,
        longitude: res.longitude
      }
      this.data.lists.push(address);
      this.setData({
        lists: this.data.lists
      });
    }
  })
},
```

这里主要调用了微信小程序的地图 API，[wx.chooseLocation(Object object)](https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.chooseLocation.html)，选择地址成功，将会返回地址的名称(name)、详细地址(address)、纬度(latitude)以及经度(longitude)，四个属性值。

返回的数据，我们直接添加 lists 列表中，页面将会即时显示出新增加的地址。如下图：

![](/image/collection/miniprogram/2019-09-02-21-40-05.png)

### 页面传参
从上面的 API 中，我们获取到了一个地址的 4 个参数值：name、address、latitude 以及 longitude，我们需要将这些参数值渲染到页面中。

当我们点击列表中某一个地址的时候，将参数值传到逻辑层，调用 API，打开地图，显示导航。于是，修改 `address.wxml` 代码如下：

```html
<scroll-view scroll-y>
<block wx:for="{{ lists }}" wx:key="{{ index }}" wx:for-index="index">
  <view class="list" bindtap='gotoLocation' data-index="{{ index }}" data-latitude="{{ item.latitude }}" data-longitude="{{ item.longitude }}" data-title="{{ item.title }}" data-address="{{ item.address }}" bindlongpress="del">
    <view class="title">{{ item.title }}</view>
    <view class="access"></view>
    <view class="desc">{{ item.address }}</view>
  </view>
</block>
</scroll-view>
<view class="bottom_btn">
  <button type="primary" bindtap='add'>添加常用地址</button>
</view>
```

可以看出，在列表中，我们绑定了一个 `gotoLoacation` 的事件，同时，将那 4 个参数值，通过 `data-*` 属性的方式，附在了页面中。

### API 调用：打开地址
当点击列表的时候，将触发 `gotoLocation` 事件函数。

```js
gotoLocation: function(e){
  var latitude = e.currentTarget.dataset.latitude;
  var longitude = e.currentTarget.dataset.longitude;
  var name = e.currentTarget.dataset.title;
  var address = e.currentTarget.dataset.address;
  wx.openLocation({
    latitude,
    longitude,
    name,
    address,
    scale: 15
  })
},
```

这里主要调用了打开地址 API  [wx.openLocation](https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.openLocation.html)，传入页面中的 4 个参数值，即可实现如下图所示功能页面，而无需再写页面代码。

![](/image/collection/miniprogram/2019-09-02-21-54-57.png)

通过两个地图 API 的调用，使得我们这个页面多了一些交互，能够选择并添加地址，还能够打开地址，显示导航。

### 总结
这一篇，我们初步接触了微信小程序 API 的使用方法，并使用了两个地图 API：选择地址以及打开地址。

另外，还接触了从视图层传参到逻辑层的方式。
