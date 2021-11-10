---
title: Hello, World | 写一个完整的页面
date: 2019-09-02
permalink: /collection/miniprogram/02_first_page
---

上一篇，大致了解了小程序的页面，这篇就可以直接上手开发了，先写一个页面试试。当然不是真的让你写个 **Hello, World** 的页面，因为那个没有任何意义。

从这一篇开始，我带你直接写一个**收藏地址**的小工具，有页面，有交互，有储存，实战写一个完整的工具，作为小程序开发的 **Hello, World**。

这篇文章，先带你写一个完整的页面出来。

### 新建项目
打开微信开发者工具，新建项目，如下图：

![](/image/collections/miniprogram/2019-09-02-15-18-34.png)

你也可以在 GitHub 上，下载我写的 [demo](https://github.com/pengloo53/miniprogram-demos)，然后导入项目。（新手建议自己去创建）

![](/image/collections/miniprogram/2019-09-02-15-22-43.png)

如上图是默认的项目目录，所有的页面都保存在 pages 里，其他代码先不用管，我们来创建一个页面。

### 新建页面
在 pages 目录下，新建一个目录叫 address，然后在 address 目录下，新建 page，同样命名为 address。最后结果如下：

![](/image/collections/miniprogram/2019-09-02-15-25-49.png)

你会发现，在新建 page 的时候，会自动创建同名的 js/json/wxml/wxss 4 个文件。前面提到过，这是微信开发者工具提供的自动化功能。如果你在使用其他 IDE 或 编辑器，可能就没有这种便利性了。

当然，这种便利性一定程度上，会造成冗余，因为大部分的页面，几乎用不上 json 文件。

### 写个列表样式
列表可能是小程序里最常用的组件了，并且微信几乎都是列表组成了，聊天页是一个一个对话列表组成，通讯录、发现页以及设置页，几乎都是列表组成的。

打开 `address.wxml` 文件，敲入代码如下：

```html
<scroll-view scroll-y>
<block wx:for="{{ lists }}" wx:key="{{ index }}" wx:for-index="index">
  <view class="list">
    <view class="title">{{ item.title }}</view>
    <view class="access"></view>
    <view class="desc">{{ item.address }}</view>
  </view>
</block>
</scroll-view>
```

组件以及框架的基础使用方法，请自行查看官方文档，这里不多赘述，它的使用方法与 Vue 非常类似。

上述代码中，用到一个 for 循环，循环变量为 lists，打开对应的 js 文件，也就是 address.js，在 data 初添加 lists 变量数据：

```js
data: {
  lists: [
    {
      title: '我的家乡',
      address: '湖北武汉'
    },{
      title: '我的学校',
      address: '河北工业大学'
    }
  ]
},
```

然后简单写一下样式代码如下：

```css
.list{
  background-color: #ffffff;
  position: relative;
  padding: 20rpx;
  border-bottom: 1px solid #eeeeee;
  margin-left: 30rpx;
}

.list:first-child{
  margin-top: 20rpx;
  border-top: 1px solid #eeeeee;
}

.list .title{
  /* font-size: 1.1em; */
  color: #333;
}

.list .access{
  content: " ";
  position: absolute;
  height: 6px;
  width: 6px;
  border-width: 2px 2px 0 0;
  border-color: #aaa;
  border-style: solid;
  -webkit-transform: matrix(.71,.71,-.71,.71,0,0);
  transform: matrix(.71,.71,-.71,.71,0,0);
  top: 45%;
  right: 30rpx;
}

.list .desc{
  margin-top: 10rpx;
  font-size: 0.8em;
  color: #aaa;
}
```

到这里，这个页面就写完了，接下来，在首页添加一个入口，跳转到这个页面。

### 页面显示
打开 index 目录下的 index.wxml 文件，添加如下代码：

```html
<view style="margin-top: 30rpx; padding: 10rpx 20rpx; background-color:#eeeeee">
  <navigator url="../address/address">收藏地址</navigator>
</view>
```

这里可以看出，写法跟 HTML 几乎一样，在标签中使用 `style` 属性，直接定义样式，最后显示效果如下：

![](/image/collections/miniprogram/2019-09-02-21-02-28.png)

点击「收藏地址」链接，如果不出意外，将会跳转到，刚完成的 address 页面，显示效果如下：

![](/image/collections/miniprogram/2019-09-02-21-05-09.png)

小程序的第一个页面，我们就写完了。

### 总结
这篇文章，从新建项目开始，到完整写完第一个页面。

每一个页面是一个目录，里面有 4 个同名文件，js 控制页面逻辑，wxss 负责页面样式，wxml 定义页面结构，还有 json 配置文件。

最后使用 `navigator` 基础组件，实现页面的跳转。