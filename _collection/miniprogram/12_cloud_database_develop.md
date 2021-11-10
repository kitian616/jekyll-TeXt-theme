---
title: 初步使用云开发中数据库的能力
date: 2019-09-14
---

正如上篇所讲，数据库这块除了熟练使用几个 API，确实没啥可讲的，那么就来回答一下，在第 8 篇文章中，开发「历史上的今天」小工具时，留下来的思考题吧。题目如下：

> 由于类似的第三方数据接口，都是付费服务，这个接口虽然免费，但是限制调用次数。
> 如何通过改进代码，最低限度调用第三方接口？

当时给了两个思路：

1. 借助缓存功能，每天每个用户只需要 1 次请求；
2. 借助数据库功能，不管多少用户，每天只需要 1 次请求；

第一个思路，没啥好讲的，基本原理就是在启动页面的时候，检查缓存情况，如果有缓存，直接加载缓存；如果没有缓存，请求数据，并保存缓存。

但是，缓存是针对用户微信客户端的，有多少用户，就需要请求多少次，如果一个用户有 2 个微信客户端，还要另外再多请求一次。显然，这不是最佳的解决方案。

最佳的解决方案，当然是第二个思路，借助数据库。下面就以这个示例，直接进入开发实战，学会数据库的基本使用。

### 功能分析
代码先不着急写，我们先来分析一下具体功能，总体要求是：将请求的数据保存在云数据库中，用户打开页面，首先去云数据库中查找当天的数据，如果有，则在数据库中取；如果没有，再去接口请求。

这样就能保证，每天只需要请求一次数据，后面的用户直接在数据库中获取「历史上的今天」的数据。所以，云函数要实现的功能是：

1. 请求第三方接口数据。（之前的云函数已经写过了，这里只需调用即可）
2. 保存当天数据到数据库中。为什么是当天？这是业务的特殊性，「历史上的今天」每天的数据不一样

而页面逻辑层要实现的功能是：去云数据库中查找当天数据。

- 如果找到，返回数据，渲染页面即可；
- 如果没有，调用上述云函数；

### 数据库准备
打开「云开发」界面，选择「数据库」。

![](/image/collections/miniprogram/2019-09-12-21-07-51.png)

新建集合 `history`，设置集合权限为：所有人可读。如下图所示：

![](/image/collections/miniprogram/2019-09-12-21-09-19.png)

一人请求后，数据便写入云数据库，后续所有人都可以直接读取，而无需再次请求第三方接口。

### 代码实现：云函数
新建页面 `cloud_database` 用做演示，新建云函数 `history_save`，目录结构如下：

![](/image/collections/miniprogram/2019-09-12-20-46-11.png)

首先编写云函数 `history_save`，代码如下：

```js
// 引入模块
const cloud = require('wx-server-sdk')
// 初始化云开发
cloud.init()

// 云函数入口函数
exports.main = async (event, context) => {
  // 传入参数 today,env
  let today = event.today;
  let env = event.env;
  // 更新默认配置，将默认访问环境设为当前云函数所在环境
  cloud.updateConfig({
    env
  });
  // 初始化数据库
  const db = cloud.database();
  // 获取 history 集合对象
  const db_history = db.collection('history');

  const res = await cloud.callFunction({
    name: 'history'
  })
  console.log(res);
  let lists = res.result.showapi_res_body.list;
  await db_history.add({
    data: {
      date: today,
      lists: lists
    }
  }).then(res => {
    console.log(res);
  })
  return {
    date: today,
    lists: lists
  };
}
```

这个云函数的逻辑就是调用 `history` 云函数，获得数据，保存到数据库中，然后返回数据。其中有 4 处地方，需要提一下：

- 传入参数，通过 `event` 对象获取，例如上面的 today 以及 env
- 更新云函数的环境变量，手动使其与小程序端保持一致，避免默认选项
- 调用其他云函数，直接使用 `callFunction` API
- 插入数据库，使用 `add` API

这些其实都是最基础的用法，在官方文档中都能找到出处，如果发现不清楚的地方，一定要去看官方文档，这里仅作为参考，给你一个思路。

下图为本地测试结果：

![](/image/collections/miniprogram/2019-09-13-08-10-34.png)

图中我已经标注非常清楚了，就不多做解释了。**另外需要注意的一点是**，本地测试的云函数，其中调用的其他云函数是在云端的。所以，务必确保调用的那个云函数在云端是没有问题的。

最后看一下数据库中，是否已经有了数据，如下图：

![](/image/collections/miniprogram/2019-09-13-08-13-16.png)

跟预想的一样。云端测试这里就不演示了，接下来就是小程序端的功能实现了。

### 代码实现：小程序端
打开页面 js 文件，在 `onLoad` 事件函数中，编写代码如下：

```js
onLoad: function (options) {
  // 初始化数据库
  const db = wx.cloud.database();
  // 获得 history 集合对象
  const db_history = db.collection('history');
  // 引入 dayjs
  const dayjs = require('dayjs');
  const TODAY = dayjs().format('YYYY-MM-DD');
  db_history.where({
    date: TODAY
  }).get().then( res => {
    console.log(res);
    let result = res.data;
    if(result.length){
      this.setData({
        lists: result[0].lists
      });
    }else{
      wx.cloud.callFunction({
        name: 'history_save',
        data: {
          today: TODAY,
          env: getApp().globalData.env
        },
        complete: res => {
          console.log(res);
          this.setData({
            lists: res.result.lists
          });
        }
      })
    }
  })
},
```

简单说一下代码逻辑，首先根据当天日期，查找数据库，如果存在数据（即长度不为 0），则直接拿数据渲染页面；如果不存在数据，则调用 `history_save` 云函数，将返回的数据渲染页面。

### 总结
这篇文章有很多非常重要，却又很基础的知识点，然而，我并没有详细展开去讲，只是针对比较重要的点，单独都提出来了。例如：**更新云函数环境变量这块**。假如不更新的话，会出现什么结果？为什么要更新？还有没有其他的方式进行更新？

这些都需要自己去动手尝试，从结果出发，我只是给出了一条可行的道路，还有很多条不同的路，也可以获得同样的结果，但需要你去尝试。再例如：判断数据库是否存在数据的逻辑，是否可以一并放到云函数中实现？这样的话，页面逻辑就更简单了。

遇到不明白的地方，针对性的去查看官方文档，你将获得更多。结合基础的知识点，实际去解决问题，才是这个系列文章的初衷。希望能对你有一些帮助。

最后，还是留一个思考题：

![](/image/collections/miniprogram/2019-09-13-11-00-31.png)

如上图，云函数的调用也是有限额的，超了的话，也是需要付费的，能不能通过改进代码，实现最低限度的调用云函数？答案其实在之前的文章里，已经给过了。


