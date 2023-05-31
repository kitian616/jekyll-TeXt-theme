---
layout: article
title: 【树莓派学习笔记】给树莓派安装防火墙
permalink: /article/:title.html
key: firewall-on-pi
tags: 
  - 树莓派学习笔记
  - 树莓派
  - Linux
  - bash
  - 防火墙
author: Yu Xiaoyuan
show_author_profile: true
---

<!-- abstract begin -->
笔者前两天被隔离在宿舍，只能通过提前配置好的校园网内的一系列转发来连接服务器。为了安全考虑，还是给暴露端口的服务器安装防火墙。
由于笔者的树莓派上运行的服务较多，所以先拿它试试水。
<!-- abstract end -->

<!--more-->

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- {%- increment equation-s1 -%} -->
<!-- end private variable of Liquid -->

## UFW - Uncomplicated Firewall

[`ufw`][1]是Linux系统下简单好用的防火墙工具。不仅可以用来当防火墙限制出入站规则，还可以设置端口转发。

`ufw`的所有指令都必须以管理员身份执行。使用如下指令可以查看防火墙状态。

```bash
$ sudo ufw status
Status: inactive
```

## 安装

如果没有安装`ufw`可以使用包管理工具进行安装，以Debian为例可以使用如下指令。

```bash
sudo apt install ufw
```

## 允许ssh通过

由于笔者的树莓派没有显示器，所有操作都基于ssh。所以在打开防火墙之前先允许ssh通过。

```bash
sudo ufw allow OpenSSH
sudo ufw allow in ssh
```

启动防火墙。

```bash
sudo ufw enable
```

这时候再查看防火墙状态就会丰富一些了。

```bash
$ sudo ufw status
Status: active

To                         Action      From
--                         ------      ----
OpenSSH                    ALLOW       Anywhere
22/tcp                     ALLOW       Anywhere
OpenSSH (v6)               ALLOW       Anywhere (v6)
22/tcp (v6)                ALLOW       Anywhere (v6)
```

## 端口转发

端口转发需要编辑文件`/etc/ufw/before.rules`。在`*filter`字段前插入`*nat`字段。端口转发的模板如下。

```
*nat
:PREROUTING ACCEPT [0:0]
-A PREROUTING -p tcp --dport exposed_port -j REDIRECT --to-port effective_port
COMMIT
```

笔者这里将1998端口的数据全部转发到22端口，插入结果如下。

```bash
$ sudo head before.rules -n 20
#
# rules.before
#
# Rules that should be run before the ufw command line added rules. Custom
# rules should be added to one of these chains:
#   ufw-before-input
#   ufw-before-output
#   ufw-before-forward
#
*nat
:PREROUTING ACCEPT [0:0]
-A PREROUTING -p tcp --dport 1998 -j REDIRECT --to-port 22
COMMIT

# Don't delete these required lines, otherwise there will be errors
*filter
:ufw-before-input - [0:0]
:ufw-before-output - [0:0]
:ufw-before-forward - [0:0]
:ufw-not-local - [0:0]
```

然后重新加载防火墙。

```bash
sudo ufw reload
```

## 允许特定服务通过防火墙

`ufw`从`/etc/services`读取服务列表，这样可以直接从这里面允许或禁用服务。

```bash
less /etc/services
```

笔者在树莓派上搭建了一个网页服务器，需要允许http和https通过。

```bash
sudo ufw allow http
sudo ufw allow https
```

注意：不在`/etc/services`中的服务不能作为服务名传递给`ufw`。
{:.info}

## 允许特定端口通过防火墙

笔者在树莓派上搭建了代理服务，该服务要求开放7890端口。

```bash
sudo ufw allow from any to any port 7890
```

该指令允许任意ip到任意ip对7890端口的访问。

## 其他功能

`ufw`的其他功能包括阻止特定ip等可以查看[Ubuntu文档][1]，或者在命令行输入指令`man ufw`来查看本地手册。

## 总结

笔者最开始有动力去研究防火墙是为了端口转发功能，早就听说防火墙安全但是繁琐，深入了解过后发现确实是麻烦不少。
在安全的网络环境下没有必要考虑防火墙。但是没有绝对安全的网络环境，所以各自斟酌。

## 参考

[Ubuntu documentation: UFW][1]

[aruba cloud: How to manage and forward ports with UFW on Ubuntu 18.04][2]

<!-- begin reference links -->
[1]: https://help.ubuntu.com/community/UFW
[2]: https://www.arubacloud.com/tutorial/how-to-manage-and-forward-ports-with-ufw-on-ubuntu-18-04.aspx
<!-- end reference links -->
