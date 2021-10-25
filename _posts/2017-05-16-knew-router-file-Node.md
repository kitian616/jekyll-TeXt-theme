---
title: 了解express的路由方法 - Node实战
date: 2017-06-23
tags: [node,express,路由]
---

一直使用Express脚手架（express-generator模块）直接生成项目目录，使用命令`express -e ./test`，生成目录后会创建一些项目目录，如下：

![](/image/node/2017-06-23-10-21-26.jpg)

路由相关逻辑都是写在了`routes/`目录里，如果想创建一些新的路径，需要新建路由文件，然后在`app.js`里定义这个路由文件，并添加中间件。`app.js`代码如下：

```js
// ...省略
var routes = require('./routes/index');
var users = require('./routes/users');
var admins = require('./routes/admins');
var systems = require('./routes/systems');

var app = express();
// ...省略

app.use('/', routes);
app.use('/user', users);
app.use('/admin', admins);
app.use('/sys', systems);
// ...省略
```

路由越多，`app.js`里的路由相关代码就越多，显得有点乱。能不能把这块代码转移出去，最好转移到`routes/`目录下，学习[N-blog](https://github.com/nswbmw/N-blog)项目的时候，发现项目中路由注册相关代码正好写进了`routes/`。

每一个路由对象都是中间件的一个实例，上面的代码可以先写成如下：

```js
// ...省略
var app = express();
// ...省略
app.use('/', require('./routes/index'));
app.use('/user', require('./routes/users'));
app.use('/admin', require('./routes/admins'));
app.use('/sys', require('./routes/systems'));
// ...省略
```

我们将`routes/`目录下文件`index.js`改成一个普通对象如下：

```js
module.exports = function(app){
  app.use('/', require('./routes/home'));
  app.use('/user', require('./routes/users'));
  app.use('/admin', require('./routes/admins'));
  app.use('/sys', require('./routes/systems'));
};
```

将路由分别放进`routes/home.js`、`routes/user.js`、`routes/admins.js`、`routes/systems.js`四个文件中，来对应4个路径：`/`、`/user`、`admin`、`/sys`。

最后在`app.js`里引入`routes/index.js`模块就行了，如下代码：

```js
var routes = require('../routes');  // require一个目录会默认寻找该目录下的index.js文件
var app = express();
routes(app);
```

这样的话，再新添加一个路由，直接在`routes/index.js`里添加相关代码即可，不必再动`app.js`了，这样整体看起来会整齐一些。