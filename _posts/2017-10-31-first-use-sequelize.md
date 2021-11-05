---
title: 初步使用sequelize模块 - Node实战
date: 2017-10-31
tags: [node]
description: Node Web开发中，换台机器开发就要检查数据库表结构是否一致，直接操作SQL来写代码实在是太Low，太不方便了，有没有替代方案？我怎么这么晚才发现sequelize模块呢
---

![](/image/node/sequelize.png)

在Node Web开发过程中，后台数据库我一直使用的都是Mysql，没有别的什么原因，就觉得熟悉，图形界面操作起来比较简单，简化了好多关于数据库方面的操作。

最开始的时候，我一直都是提前将我要的数据表在Mysql里用图形界面创建好，然后再开始我的项目代码编写，当然这个过程一直穿插在整个项目的开发中，有时候某个功能最开始想得不太全，于是就重新修改数据表，再根据数据表结构进行代码的修改。刚开始学习的时候，一直关注前台后台逻辑，数据库方面关注得比较少，而且开发基本都是自己一个人一台机器上完成，所以也没觉得这种操作特别麻烦。

后来换台机器继续写代码的时候，就会发现，这种方式真的是太不方便了，当我在A电脑上修改了代码，并且修改了数据表结构之后，去B电脑上通过GIt同步代码，但是数据表结构还得copy一份A电脑上修改过的数据表结构，想想也是醉了。这种实现方式不仅比较Low，并且维护起来也非常的不方便，一方面要写交互逻辑代码，一方面要拼接sql语句来获取数据。

从理论上讲应该在后台业务逻辑以及数据库取数逻辑中间再架一层，将数据库操作全部封装起来，调用对象方法而取消拼接SQL来实现数据的获取，没错，这就是[ORM（Object Relational Mapping）](https://baike.baidu.com/item/ORM/3583252?fr=aladdin)框架。J2EE开发三剑客`SSH`中的`Hibernate`就是ORM的一种实现方式。Node Web开发中，我找到了[Sequelize](http://docs.sequelizejs.com/)，看看文档，用起来感觉还是不错，最起码解决了我的上述的困扰。

引入模块等等准备工作我就不多废话了，直接上手实战。

### 1. 创建连接对象，并模块化
新建数据库连接模块`dbConnect.js`，单独提出连接数据库的对象`sequelize`，如下代码：

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

上面代码使用了配置模块`config-lite`，具体使用可以参考[这篇文章](https://segmentfault.com/a/1190000010099383)。然后根据数据库的一些参数，创建了`sequelize`的数据库连接模块。

### 2. 定义数据表结构，将表结构写进代码里
目前我使用的方式是：每个表对应一个文档，放入Node项目中`models`目录中，这里拿我创建的一个`todolist`表来做示例，在`models`目录中创建`todolist.js`文件，代码如下：

```javascript
var Sequelize = require('sequelize');
var sequelize = require('./dbConnect.js');

var todolist = sequelize.define('todolist',{
    id: {
        type: Sequelize.BIGINT(11),
        primaryKey: true,
        allowNull: false,
        unique: true,
        autoIncrement: true
    },
    title: Sequelize.STRING(100),  // 标题
    content: Sequelize.STRING(500),  // 详细内容
    priority: Sequelize.INTEGER,  // 级别
    owner: Sequelize.STRING,  // 承接人
    officer: Sequelize.STRING, // 负责人
    startDate: Sequelize.STRING, // 开始时间
    planFinishDate: Sequelize.STRING, // 计划完成时间
    realFinishDate: Sequelize.STRING, // 实际完成时间
    bz: Sequelize.STRING(500), // 备注
    state: Sequelize.INTEGER,  // 状态
    createdAt: Sequelize.BIGINT,
    updatedAt: Sequelize.BIGINT,
    version: Sequelize.BIGINT
},{
    timestamps: false   // 不要默认时间戳
});

module.exports = todolist;
```

以上代码，直接引入之前创建的`sequelize`对象，然后使用`defind`方法定义数据表结构。其他的所有数据表都可以通过这种方式来定义，保存在每一个独立的文件中，引出数据模块即可。

### 3. 同步数据表结构
这个就简单了，单独创建一个工具类，我的做法是在项目中新建`libs/util`目录，然后新建`syncTable.js`，代码如下：

```javascript
var todolist = require('../../models/todolist.js');

// 同步表结构
todolist.sync({
    force: true  // 强制同步，先删除表，然后新建
});
```

这样就OK了，当每次我换电脑继续项目的时候，不用单独操作数据库以确保数据库结构一致了，只需要手动执行一下该方法就可以了。

### 4. 创建一些初始数据
通过这种方法，我们同样可以创建一个工具类，用来初始化一些基础数据，如下`addMasterData.js`代码：

```javascript
var priority = require('../../models/priority.js');
// 创建u_priority表的基础数据
priority.create({
    title: '重要 紧急'
}).then(function (p) {
    console.log('created. ' + JSON.stringify(p));
}).catch(function (err) {
    console.log('failed: ' + err);
});

priority.create({
    title: '重要 不紧急'
}).then(function (p) {
    console.log('created. ' + JSON.stringify(p));
}).catch(function (err) {
    console.log('failed: ' + err);
});

priority.create({
    title: '不重要 紧急'
}).then(function (p) {
    console.log('created. ' + JSON.stringify(p));
}).catch(function (err) {
    console.log('failed: ' + err);
});

priority.create({
    title: '不重要 不紧急'
}).then(function (p) {
    console.log('created. ' + JSON.stringify(p));
}).catch(function (err) {
    console.log('failed: ' + err);
});
```

给`prioritys`表里创建一些初始数据，默认表名会添加s，定义表的时候可以通过`tableName`属性值来定义对应的表名，如下示例将表名定义为`u_priority`：

```javascript
var priority = sequelize.define('priority',{
    id: {
        type: Sequelize.BIGINT(11),
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
    },
    title: Sequelize.STRING,
},{
    timestamps: false,
    tableName: 'u_priority'  // 数据表名为u_priority
});
```

好了，本次就到这里，深入学习后再做分享。