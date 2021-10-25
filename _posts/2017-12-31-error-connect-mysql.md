---
title: Access denied for user 'xxx'@'localhost' 报错
date: 2017-12-31
tags: node
---

```shell
Unhandled rejection SequelizeAccessDeniedError: Access denied for user 'lupeng'@'localhost' (using password: YES)
```

这是`Node`在使用`Sequlize`连接`Mysql`数据时报的错，关键看冒号后面的错误：访问拒绝，关键是访问拒绝的错误，说明数据库连接这里有问题，数据库连接访问拒绝，要么是没有相应的操作权限，要么是账号密码错误。

这样就把问题定位在访问权限以及账号密码错误两点上了，千万不要往其他方面去找问题了，那样只会是浪费时间。

**1. 用户权限的问题**

权限问题从数据库着手，确认用户授权后，是否刷新的权限列表。也就是在使用`Grant`命令授权用户后，应该要使用`flush privileges`命令，这个是很多人会忽略的问题。

如果用户授权没有问题，那么尝试重启mysql服务器。使用命令`/etc/init.d/mysql restart`重启mysql服务器，不同Linux版本重启命令可能不一样，我这里是Debain系。

如果重启了问题还没有解决，那么可能就不是数据库用户权限的问题了。

**2. 账号密码的问题**

账号密码错误，这个问题听起来很扯，但是开发过程中，很多人会忽略掉。为什么这么说？我使用的是`config-lite`模块来配置数据库参数，因为会在多个系统环境中切换开发，使用`config-lite`模块可以通过简单的环境变量配置，来加载不同的参数文件。具体用法参照：[不同环境下配置文件使用 - Node实战 - 技术人生 - SegmentFault](https://segmentfault.com/a/1190000010099383)。

我的问题就出现在这里，打开一个终端运行项目`npm run test`，`package.json`文件里配置着`test`的运行脚本`NODE_ENV=test supervisor --harmony -i views/ ./bin/www`。另打开一个终端，运行数据库同步的命令，同步命令是单独写在一个`js`脚本中，脚本里引用了数据连接方法（通用的），连接方法如下：

```javascript
var Sequelize = require('sequelize');
// 引入数据库配置文件
var sqlConfig = require('config-lite')(__dirname).mysql;
var sequelize = new Sequelize(sqlConfig.database, sqlConfig.user, sqlConfig.password, {
    host: sqlConfig.host,
    dialect: 'mysql',
    pool: {
        max: 10,
        min: 0,
        idle: 10000
    }
});
module.exports = sequelize;
```

于是在同步数据库的时候，总是如标题报错，然而觉得没错啊，**最后检查才发现问题所在：运行`test`脚本里的`NODE_ENV`环境变量只在当前终端下才有效，如果要另开一个终端来同步数据库，那么需要在另开的终端里再设置一下环境变量。**不然加载的数据库参数是不一致的，也就是说连接数据库的用户名密码是不对的。