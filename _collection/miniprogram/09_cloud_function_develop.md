---
title: 初步使用云函数开发
date: 2019-09-11
permalink: /collection/miniprogram/09_cloud_function_develop
---

上一篇内容，我们初次接触了网络开发，使用第三方的接口数据，由于微信公众账号的限制与管控，在线使用第三方的服务器，都必须要添加合法域名信息，如下图所示：

![](/image/collection/miniprogram/2019-09-10-21-57-45.png)

能找到第三方数据接口已经很不容易了，还必须是 `https` 以及域名备案的，这个就有点过分了。

那能不能有什么办法，绕过这个限制呢？答案是肯定的。绕过这个限制的办法，正是官方自己提供的方法，使用云开发呀。

### 云函数介绍
云函数开发是微信小程序云开发的一个重要能力，它其实就是微信小程序在腾讯云上，给你分配的一个 Node 环境的服务器。

什么是 Node 环境？简单来讲，它是 JavaScript 在服务器端的运行环境， 后台的服务功能，它都能实现，至于怎么跟**微信小程序**以及**腾讯云**衔接起来的，这个你就不需要管了，会用就可以了。

它有很多的开发优势，具体看[文档](https://developers.weixin.qq.com/miniprogram/dev/wxcloud/basis/getting-started.html)。

>  云函数是一段运行在云端的代码，无需管理服务器，在开发工具内编写、一键上传部署即可运行后端代码。
> 小程序内提供了专门用于云函数调用的 API。开发者可以在云函数内使用 wx-server-sdk 提供的 getWXContext 方法获取到每次调用的上下文（appid、openid 等），无需维护复杂的鉴权机制，即可获取天然可信任的用户登录态（openid）。

基础内容不多介绍了，直接进入实战。

### 云函数使用
在项目根目录中，新建 cloudfunctions 目录，用来保存云函数的相关代码。然后在 `project.config.json` 加上如下一条配置：

```json
"cloudfunctionRoot": "cloudfunctions"
```

右键该目录，就可以创建云函数了，如下图：

![](/image/collection/miniprogram/2019-09-11-18-01-55.png)

这里我们创建一个名为 `history` 的云函数，会给我们新建两个默认文件 `index.js` 和 `package.json`，其实这就是一个简化版的 Node 服务，其中 `index.js` 代码如下：

```js
// 云函数入口文件
const cloud = require('wx-server-sdk')

cloud.init()

// 云函数入口函数
exports.main = async (event, context) => {
  const wxContext = cloud.getWXContext()

  return {
    event,
    openid: wxContext.OPENID,
    appid: wxContext.APPID,
    unionid: wxContext.UNIONID,
  }
}
```

截止到这里，我们已经完成了几乎一半的工作，然而一句代码还没有写，高度定制化的开发就是这样，也许你只需要简单的操作，就能完成编程的很多工作。

当然，这种方式各有利弊，傻瓜式的操作虽然使编程工作简单化了，但是，很有可能也会让你真的「傻瓜」化。

### 异步请求数据
下面我们就来把上一篇中请求数据的方法，写到云函数中。这样一来，就不需要再去设置合法域名信息了。因为我们并不是在小程序端发起请求，而是在云函数中（腾讯云提供的定制服务器）。

由于腾讯云提供的这个定制服务器里，跑的是 Node 服务，于是，编写服务端代码，可以直接使用原生 Node 的一些 API，例如这里的请求数据，我们就可以直接使用 `http` 或 `https` 模块。这里不多废话，看完代码再解释，改写上述 `index.js` 代码：

```js
// 引入 https，querystring 模块
const https = require('https');
const querystring = require('querystring');
// 引入自定义配置文件
const { showapi_appid, showapi_sign } = require('./self.config.js');
// showapi url
const URL = 'https://route.showapi.com/119-42';
// 序列化请求参数
const params = querystring.stringify({
  showapi_appid,
  showapi_sign
});
// 请求 URL
const requestUrl = URL + '?' + params;

// 云函数入口函数
exports.main = async (event, context) => {
  return new Promise((resolve, reject) => {
    const req = https.request(requestUrl, function (res) {
      let resData = '';
      res.setEncoding('utf8');
      res.on('data', (chunk) => {
        resData += chunk;
      });
      res.on('end', () => {
        resolve(resData);
      });
    });
    req.end();
  })
}
```

异步请求数据必须返回一个 `Promise` 对象，具体参考[这里的文档说明](https://developers.weixin.qq.com/miniprogram/dev/wxcloud/guide/functions/async.html)，除了 `exports.main` 部分，其他代码就是 `https` 模块最基本的使用了，如果看起来有些难度，可以去看看 Node 的[官方文档](http://nodejs.cn/api/)，这里不多赘述。

或者，你也可以看看，我写的另外一个课程：https://github.com/pengloo53/node-fullstack-course，正在更新中。

### 云函数部署
本地的云函数写完之后，最好在本地测试一下，再上传到云端，关于云函数的测试，下一篇文章单独再介绍。

上传部署之前，要确保已经开通了云开发能力，点击「云开发」，然后跟着流程走就可以了，一个小程序，可以创建两个环境，一个用来测试，一个用于生产。

![](/image/collection/miniprogram/2019-09-11-15-37-41.png)

由于这里我早已创建好了，就不再演示了。

选择要上传的环境，然后右键 history 目录，创建并部署，就可以了。

![](/image/collection/miniprogram/2019-09-11-15-42-48.png)

### 云函数调用
假设云函数都搞定了，功能在本地和云端都测试通过了。（如果没有通过，先不着急，下一篇详解本地和云端的测试）

现在再回到小程序的文件夹中，我们新建一个页面用作示例，如下图：

![](/image/collection/miniprogram/2019-09-11-17-09-04.png)

页面代码可以 copy 一下上一篇中的 request 页面，然后编辑 `index.js` 代码如下：

```js
onLoad: function (options) {
  wx.showLoading({
    title: '加载中',
  });
  // 云函数的调用
  wx.cloud.callFunction({
    name: 'history',
    complete: res => {
      wx.hideLoading();
      let result = JSON.parse(res.result);
      this.setData({
        lists: result.showapi_res_body.list
      });
    }
  });
},
```

在 onLoad 事件函数中，使用 `wx.cloud.callFunction` 调用云函数，它的具体使用方法参照官方文档就可以了，没什么可讲的。其中，返回的的数据又包裹了一层，在 result 参数下，记不住没关系，可以随时打印出来。

最后，在首页中加入一个导航链接，进入 cloudfunction 页面，发现，果然没有什么事情是一帆风顺的。控制台报错如下：

![](/image/collection/miniprogram/2019-09-11-17-37-02.png)

上述错误的提示已经很明显了，就是没有初始化，具体查看这里的[文档](https://developers.weixin.qq.com/miniprogram/dev/wxcloud/guide/init.html)。

我们打开小程序的 `app.js` 文件，在启动事件函数中，进行初始化，一劳永逸。在 `onLaunch` 的事件函数中，加上如下代码即可。

```js
// 初始化云开发
wx.cloud.init({
  env: 'byteco***'
});
```

把环境变量替换成自己的，环境变量 ID 在这里查看。

![](/image/collection/miniprogram/2019-09-11-17-42-45.png)

### 总结
这篇文章介绍了云函数的基本使用，并带你完成了，云函数异步请求第三方数据的功能，通过这种方式，可以不使用 `wx.request` API 来获得第三方接口的数据，从而避免要设置合法域名信息的限制。