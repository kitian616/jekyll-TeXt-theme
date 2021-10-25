---
title: Node连接mysql数据库方法
date: 2016-12-29
tags: [mysql,node]
---

使用Node做Web开发，基本上都是使用`NoSQL`数据库，最频繁的就是使用[MongoDB](https://www.mongodb.com/)了，自己做了一些简单的Web开发，为了降低学习门槛，一直使用`MySQL`来做数据库。这里简单介绍一下连接`MySQL`数据库的方式，希望能帮助到其他人。

```
npm install --save mysql
```

使用上述命令安装完`MySQL`的模块后，就可以直接使用了，官网的DOCS里一个简单的例子如下就可以入门了。

```js
var mysql = require('mysql');
var connection = mysql.createConnection({
  host: 'localhost',
  user: 'me',
  password : 'secret',
  database : 'my_db'
});
connection.connect();
connection.query('SELECT 1 + 1 AS solution', function(err, rows, fields) {
  if (err) throw err;
  console.log('The solution is: ', rows[0].solution);
});
connection.end();
```

很简单的一个例子，从上面的例子可以得出：使用`createConnection(option)`方法创建一个连接对象，然后连接对象的`connect()`方法创建连接，最后使用`query()`方法执行SQL语句，返回结果作为回调函数的参数`rows`返回，`rows`为数组类型。

### 1. 连接
创建连接对象，需要传入连接数据库的一些连接参数，也就是`createConnection(option)`里的`option`，`option`是一个对象，以键值对的形式传入`createConnection()`方法里。上例列举出了最基本的参数：

- `host` 主机名
- `user` 连接数据库的用户
- `password` 密码
- `database` 数据库名称

还有其他的参数，可以查询下官方DOCS，这里不一一列举了，初期学习上面这些参数就足以。

### 2. 关闭
关闭一个连接使用`end()`方法，`end()`方法提供一个回调函数，如下：

```js
connect.end(function(err){
    console.log('End a connection');
});
```

这是建议使用的方法，`end()`方法会等待连接回调完成后才关闭连接。官方还提供了另外一种方法`destroy()`方法，这个方法直接关闭连接，不会等待回调完成。

举个简单的例子：

```js
var mysql = require('mysql');
var option = require('./connect.js').option;
var conn = mysql.createConnection(option);
conn.query('select * from message',function(err,rows,fields){
  if(!err){
    console.log(rows);
  }
});
conn.end(function(err){
  console.log('end a connection');
});
```

最终结果会是：先打印完`SELECT`数据表结果后，再打印`end a connection`。而如果你将关闭方法换成`conn.destroy();`，那么你就别想返回任何结果了，因为还没等回调结束就已经终止连接了。

### 3. 连接池
连接池的原理是一开始就给你创建多个连接对象放在一个“池子”里，用的时候取一个，用完了放回“池子”里，在一定程度上是有利于节省系统开销的，因为连接对象是在最开始的时候就创建好了，使用的时候不再需要系统开销去创建数据库连接对象。官方DOCS介绍了连接方法：

```js
var mysql = require('mysql');
var pool  = mysql.createPool({
  connectionLimit : 10,
  host            : 'example.org',
  user            : 'bob',
  password        : 'secret',
  database        : 'my_db'
});
pool.query('SELECT 1 + 1 AS solution', function(err, rows, fields) {
  if (err) throw err;
  console.log('The solution is: ', rows[0].solution);
});
```

创建连接池的方法是`createPool(option)`，`option`里多了一个参数`connectionLimit`指的是一次性在连接池里创建多少个连接对象，默认10个。如果你想共享一个连接对象，可以使用下面方法进行连接；

```js
var mysql = require('mysql');
var pool  = mysql.createPool({
  host     : 'example.org',
  user     : 'bob',
  password : 'secret',
  database : 'my_db'
});
pool.getConnection(function(err, connection) {
  // Use the connection
  connection.query( 'SELECT something FROM sometable', function(err, rows) {
    // And done with the connection.
    connection.release();
    // Don't use the connection here, it has been returned to the pool.
  });

// Use the connection
  connection.query( 'SELECT something2 FROM sometable2', function(err, rows) {
    // And done with the connection.
    connection.release();
    // Don't use the connection here, it has been returned to the pool.
  });
});
```

使用一个连接对象执行两次`query()`函数。

### 4. 示例1
使用基本的连接方式来连接数据库，分别定义数据连接以及关闭的`function`，如下示例：

```js
// connect.js 数据库连接与关闭
var mysql = require('mysql');
var config = require('./config.json'); // 将数据库连接参数写入mysql对象，即config.mysql
var connCount = 0; // 统计目前未关闭的连接
exports.getConn = function(){
  connCount ++;
  console.log('............................OPEN a connection, has '+ connCount + ' connection.');
  return mysql.createConnection(config.mysql);
};
exports.endConn = function(conn){
  conn.end(function(err){
    if(!err){
      connCount --;
      console.log('.........................CLOSE a connection, has '+ connCount + ' connection.');
    }
  });
};
```

然后给个使用数据库的示例，

```js
// db.js 查询用户信息
var connect = require('./connect.js'); // 引入数据连接方法
exports.getUser = function(username, callback){
    var connection = connect.getConn();
    var sql = 'select * from user where username = "' + username + '"';
    connection.query(sql,function(err,rows,fields){
        callback(err,rows,fields);    
    });
    connect.endConn(connection);
}
```

### 5. 示例2
使用数据库连接池，同样先创建数据库连接池的方法，如下两种方式：

```js
// connect.js 直接使用
var mysql = require('mysql');
var config = require('./config.json');
var pool = mysql.createPool(config.mysql);

exports.querySQL = function(sql,callback){
    pool.query(sql, function(err,rows,fields){
        callback(err,rows,fields);
    });
}
```

```js
// connect.js 使用getConnection方法
var mysql = require('mysql');
var config = require('./config.json');
var pool = mysql.createPool(config.mysql);

exports.querySQL = function(sql, callback){
    pool.getConnection(function(err,conn){
        conn.query(sql,function(err,rows,fields){
            callback(err,rows,fields); 
            conn.release();   // 不要忘了释放
        });        
    });
}
```

使用的时候，直接使用`querySQL`方法即可，如下：

```js
// db.js 查询用户信息
var connect = require('./connect.js');
exports.getUser = function(username,callback){
    var sql = 'select * from user where username = "' + username + '"';
    connect.querySQL(sql,function(err,rows,fields){
        callback(err,rows,fields);        
    });
};
```

官方是推荐使用连接池的方式进行连接的，但是，是直接使用`pool.query()`连接还是`pool.getConnection()`的方法来连接，官方并没有介绍其优劣，我简单做了个测试，貌似这两种方式并没有多大的区别，也就没再研究，有知道的烦请告知，谢了~