---
title: 不同环境下配置文件使用 - Node实战
date: 2017-07-02
tags: [node,配置文件]
---

在实际的项目会存在多个不同的环境，不同的环境下，一些配置是不相同的，如何在不同的环境下调用不同的配置，提高开发效率？
<!-- more -->
### 1. `config-lite`模块
首先引入一个配置模块[config-lite](https://www.npmjs.com/package/config-lite)，使用命令`npm i config-lite --save`安装。

通常我们会针对不同的环境，将配置写入不同的配置文件中，在Node项目下新建`config`目录，里面新建不同环境的配置文件，这里我以『开发』以及『生产』两个环境作为例子来讲解如何操作。

开发环境中，我们在`config`目录下新建两个文件：`test.js`和`default.js`，你可能会问，为啥没有生产机配置文件，因为生产机配置要在生产环境下再创建嘛。

在`test.js`配置文件中写入代码如下（PS. 这里以mysql的配置为例）：

```js
// test.js
module.exports = {
  mysql : {
    host: "localhost",
    user: "lupeng",
    password: "080910",
    database: "b1imd"
  }
};
```

`default.js`里写入一些默认的配置文件，例如`session`的配置等。

```js
// default.js
module.exports = {
  mysql : {
    host: "10.20.141.220",
    user: "lupeng",
    password: "123456",
    database: "b1imd"
  },
  session: {
    secret: 'keyboard cat',
    resave: false,
    saveUninitialized: true,
    cookie: {
      maxAge: 1000*60*60
    }
  }
};
```

好了，配置文件写好了，如何使用呢？这里我们使用的 `config-lite`模块，这个模块是依据环境变量来选择不同的配置文件的，所以在使用之前我们需要修改`package.json`里的启动命令：

```
"scripts": {
    "start": "NODE_ENV=production supervisor --harmony -i views/ ./bin/www",
    "test": "NODE_ENV=test supervisor --harmony -i views/ ./bin/www"
  },
```

可以看到，上面有两条启动命令，一条是针对生产机，设置了`NODE_ENV=production`，一条是针对测试机，设置了`NODE_ENV=test`，当我们使用`npm test`启动项目的时候，`config-lite`会去抓取`test.js`配置，并且会与`default.js`里配置去合并，如果有相同的对象，会覆盖`default.js`里的配置。**如上例子都有mysql的对象，那么这里会以`test.js`里的对象为准。**

好了，下面来介绍一下如何在项目中使用`config-lite`模块，在`app.js`里代码如下：

```js
// 省略...
var config = require('config-lite')(__dirname);
// 省略...
app.use(session(config.session));
console.log("mysql服务器：" + config.mysql.host);    // display mysql-config
// 省略...
```

引入之后，可以直接使用配置文件中的配置对象。

### 2. 生产环境
那么在生产环境中，如何使用呢？上面已经介绍了`config-lite`基本原理以及用法，在生产环境的时候，我们只需要在生产机环境中`config`目录下新建`production.js`文件，然后使用启动命令`npm start`即可。

为了避免测试环境以及生产坏境配置文件混淆，可以通过`.gitignore`文件忽略配置文件，添加如下：

```
# config
config/*
!config/default.*
```

这样，git会忽略除了`default.js`之外的配置文件，在本地开发环境中，可以创建多个配置文件测试使用，只需设置对应的环境变量即可。**需要注意的是环境变量名需同配置文件名一样。**

### 3. windows环境
也许你是一个多系统环境开发者，可能同时在`Linux`和`windows`环境下开发，由于`windows`下设置环境的变量的语法不太一样，所以可以在`package.json`启动命令中再加上两句，如下：

```
"scripts": {
    "start": "NODE_ENV=production supervisor --harmony -i views/ ./bin/www",
    "test": "NODE_ENV=test supervisor --harmony -i views/ ./bin/www",
    "winStart": "SET NODE_ENV=production&&supervisor --harmony -i views/ ./bin/www",
    "winTest": "SET NODE_ENV=test&&supervisor --harmony -i views/ ./bin/www"    
  }
```

这样，如果部署在`windows`环境下，在`config`目录下新建`production.js`配置文件，启动命令`npm winStart`；如果部署在`Linux`或`类Unix`环境下，同样是创建`production.js`，启动命令`npm start`即可。