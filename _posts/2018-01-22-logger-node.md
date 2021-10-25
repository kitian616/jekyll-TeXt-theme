---
title: 日志 - Node实战
date: 2018-01-22
tags: [logger,node]
---

`express`模块简单集成了一个日志模块`morgan`，可以将请求的一些消息打印在后台终端上，然而在实际的生产系统中，我们通常需要更完备的日志功能，以便供运维人员定期查看。

这里简单介绍`winston`模块的引入，实现最简单的日志写入文件功能，首先项目中安装模块，如下命令:

```
npm install --save express-winston
npm install --save winston
```

然后在项目`app.js`文件中引入对应的模块，如下代码所示: 

```js
var winston = require('winston');
var expressWinston = require('express-winston');
// 正常请求的日志
app.use(expressWinston.logger({
  transports: [
    new (winston.transports.Console)({
        json: true,
        colorize: true
    }),
    new winston.transports.File({
      filename: 'logs/success.log'
    })
  ]
}));
// 正常访问路由
routes(app);
// 错误请求的日志
app.use(expressWinston.errorLogger({
  transports: [
    new winston.transports.Console({
        json: true,
        colorize: true
    }),
    new winston.transports.File({
      filename: 'logs/error.log'
    })
  ]
}));
```

日志文件分为`success.log`和`error.log`，放在正常路由之前则为成功日志，放在正常访问路由之后，则为错误日志。如不需过多功能，这样放进项目里即可，详细了解可查看官方文档: [winstonjs/winston: A logger for just about everything.](https://github.com/winstonjs/winston)