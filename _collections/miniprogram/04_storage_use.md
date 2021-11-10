---
title: 数据持久化保存 | Storage API 的使用
date: 2019-09-03
---

上一篇，我们已经实现了添加地址的功能，并且能够在列表中，及时显示添加的地址。

但是，当我们重新进入小程序的时候，数据就会消失，因为这里的数据只是保存在程序的变量中，当页面重新载入的时候，数据就会被重置，所以，这里我们需要借助缓存功能，来实现数据持久化保存。

### 数据存储
使用 `wx.setStorage()` 将列表数据保存在缓存中，具体用法参照文档。我们打开 `address.js` 文件，修改 add 函数中，如下：

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
      wx.setStorage({
        key: "address_lists",
        data: this.data.lists
      })
    }
  })
},
```

再次添加数据的时候，我们可以在控制台 Storage 中，看到保存的数据。

![](/image/collections/miniprogram/2019-09-03-07-43-53.png)

PS. 上面添加逻辑，把默认的两条地址数据一块保存了，但是点击它们的时候，却无法打开地址，可以想一下为什么？

### 数据获取
数据可以保存在缓存中了，下次进入页面的时候，需要先读取缓存，这里就要用到页面的事件函数。

我们接着编辑 address.js 文件，在 `onShow` 函数中添加 `wx.getStorage` 调用方法，如下：

```js
onShow: function () {
  wx.getStorage({
    key: 'address_lists',
    success: res => {
      this.setData({
        lists: res.data
      })
    },
  })
},
```

这样处理之后，数据就会一直保存在缓存中，下次进入页面的时候，会优先读取缓存数据，然后给页面传值。

### 同步与异步
关于数据存储这块，官方同时提供了同步以及异步的方法，具体查看[这里](https://developers.weixin.qq.com/miniprogram/dev/api/storage/wx.setStorageSync.html)。

我们是使用同步还是使用异步，得看具体程序逻辑而定，如果不是特殊情况，一般使用异步方法。

在上述示例中，我们就通过 `setData` 的方法，就已经动态更新了页面数据，保存到缓存就不需要同步处理了。

### 数据删除
数据能够添加保存了，同时可以查看了，再加上删除的功能，「收藏地址」这个小工具，就差不多完整了。

数据是以数组的形式，保存在了缓存中，所以，删除功能其实就是要实现删除数组中某一个对象。

在上一篇「页面传参」中，不知你是否注意到，除了 `wx.openLocation` 所需要的 4 个地址参数，我们还设置了一个 `data-index` 的属性，用来传输地址在数组中的序号。另外，还添加了一个长按事件 ` bindlongpress="del"`，下面打开 address.js 文件，添加 del 函数代码如下：

```js
del: function(e){
  let index = e.currentTarget.dataset.index;
  wx.showModal({
    title: '确认删除？',
    content: '删除后，将不可恢复',
    success: res => {
      if(res.confirm){
        this.data.lists.splice(index, 1);
        this.setData({
          lists: this.data.lists
        });
        wx.setStorage({
          key: 'address_lists',
          data: this.data.lists,
        })
      }
    }
  })
},
```

这里使用了 `wx.showModal` 的确认框 API，用法自行查询官方文档。另外，使用数组的 `splice` 函数，通过 index 删除对应的对象，最后，更新页面数据，并保存到缓存中。

### 总结
这篇文章主要介绍了数据缓存的使用方法，调用 Storage API 还是非常方便的。

最后，留一个思考，如果想要实现修改的功能，如何实现？又或者，它是否有必要实现修改功能？

- - - - -
到这里，我们已经完整的做完了第一个小工具「收藏地址」，它几乎就是一个完整的小产品，如果可以，你完全可以将其上线。

接下来，我会继续介绍其他小工具的开发实战，而当涉及到类似的技术点时，将快速跳过，也不再贴实现代码。