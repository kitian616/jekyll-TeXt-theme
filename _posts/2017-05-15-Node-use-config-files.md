---
title: 学会使用配置文件 - Node实战
date: 2017-05-16
tags: [node]
---

在开发项目中，一些配置文件不便直接在代码中显示出来，通常我们会创建一个配置文件用来保存本地开发过程中的一些配置参数，例如数据库连接参数，session参数等等；有时，我们可能需要创建多个参数文件来对应不同的开发环境。下面简单介绍一下Node项目中参数文件的使用方法。
<!-- more -->
Node开发使用`javascript`语言，参数文件可以是`js`文件，也可以是`json`文件，因为Node原生支持的缘故，这两种文件最为方便。

### 1. `js`文件
在项目根目录下创建config文件夹用来保存配置文件，例如创建文件`default.js`，编写如下配置：

```js
module.exports = {
  mysql : {
    host: "localhost",
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

上述是`mysql`以及`session`的基本配置，使用的方式也很简单，例如编写`mysql`的连接模板时，这样使用：

```js
var mysql_config = require('../config/default').mysql;
var mysql = require('mysql');

var pool = mysql.createPool(mysql_config);
```

就是这么简单，`require`之后，直接当做一个`js`对象就可以使用了。

### 2. `json`文件
使用`json`文件当做配置文件也是一样的，在config目录下创建一个`default.json`文件，写入内容：

```
{
  "mysql" : {
    "host": "localhost",
    "user": "lupeng",
    "password": "123456",
    "database": "b1imd"
  }
}
```

使用的方式也是一样的，Node原生支持就是方便，同样是`require`引入，然后当做`js`对象使用即可。如下：

```js
var config = require('../config.json');
var mysql = require('mysql');

var pool = mysql.createPool(config.mysql);
```

Node开发过程中配置文件的使用方法就说到这里，当然还是推荐直接使用`js`文件，风格更加的统一。