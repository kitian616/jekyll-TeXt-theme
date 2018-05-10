---
layout: post
title: ssh 配置文件 (ssh config) 介绍
key: 20180510
tags:
  - Web
  - ssh
---

ssh 配置文件 (ssh config) 介绍

<!--more-->

## 简介
利用ssh能够安全地登录远程服务器。但是当服务器数量增加时，要记住每个服务器的配置信息是十分耗时耗力的。一般而言，你可能需要提供用户名，主机名，端口以及私钥等信息登录服务器。比如：
    
```
  ssh -i yourPrivatekey.pem user@hostname -p port
```

所以ssh 提供了配置文件（ssh config）解决这个问题。利用ssh config, 用户能够简单地利用别名登录服务器：

```
  ssh yourAlias
```

## 格式与配置项
用户的配置文件是写在 `~/.ssh/config` 中的。

ssh config 的格式比较简单, 通过模式匹配的方式匹配相应的配置，每个配置项都是一对键值对。


配置示例1：

``` ssh config
Host 192.168.*
    IdentityFile ~/myPrivatekey.pem
    LogLevel QUIET
    User ubuntu

Host hello
    HostName 172.26.1.1
    ...
```

匹配的关键配置项是`Host`, `Host`的值是别名。只要匹配到对应的别名就采用相应的配置。因此，`Host` 将配置文件分为若干段，每一段是从一个`Host`到下一个`Host`之前的所有配置项。

`Host`的值能夠使用通配符，如示例1， `Host 192.168.*` 能够匹配到以`192.168.` 开头的所有字符串，并采用后面定义的配置项。

以下是常见的配置项：

    Host 别名
    HostName 主机名
    Port 端口
    User 用户名
    IdentityFile 密钥文件的路径
    IdentitiesOnly 只接受SSH key 登录
    PreferredAuthentications 强制使用Public Key验证

更多配置项请参考[官方文档](https://linux.die.net/man/5/ssh_config) 或者执行 `ssh_config` 命令

## 配置优先级
使用 ssh config 后，配置数据根据下列优先级依次获得：
1.   命令行选项
2.   用户配置文件 (~/.ssh/config)
3.   系统级配置文件 (/etc/ssh/ssh_config)

另外，由于存在通配符，一个别名可能匹配到多个`Host`段。ssh 命令会使用先获得的配置参数。也就是说，在配置文件中越靠前的就越先被获得。

配置示例2：

``` ssh config
Host 192.*
    IdentityFile ~/myPrivatekey.pem
    User ubuntu
Host 192.168.*
    LogLevel QUIET
    User root
```

如示例2，执行`ssh 192.168.1.1`会匹配上两个`Host`段。所以能夠获得`IdentityFile`，`User`，`LogLevel` 三项配置。同时，由于`User` 的`ubuntu`值在`root`值之前，所以最终`User` 的取值为`ubuntu`。
