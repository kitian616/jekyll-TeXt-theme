---
layout: article
title: 【一点点Linux】在Linux上设置Proxy代理
permalink: /article/:title.html
key: proxy-on-linux
tags: 
  - 一点点Linux
  - Linux
  - git
  - proxy
  - bash
  - zsh
author: Yu Xiaoyuan
show_author_profile: true
license: CC-BY-NC-ND-4.0
---

<!-- abstract begin -->
在Linux上访问某些内容或者pull/push到GitHub时总会遇到代理设置的问题，系统的代理配置有时候不能反应到命令行中。
本文主要介绍了如何给命令行的指令、git和ssh配置代理，并且通过文件进行管理。
<!-- abstract end -->

<!--more-->

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- {%- increment equation-h2-1 -%} -->
<!-- end private variable of Liquid -->

## 网络环境

在之前的一篇文章中笔者在树莓派上配置了[局域网代理服务器]( {% post_url 2022-08-18-local-proxy-server %} )。  
另一篇文章中配置了树莓派的防火墙以[允许代理服务器端口通过]({% post_url 2022-12-08-firewall-on-pi %}) 。  
树莓派上的一些基础配置和网络环境配置如下表所示。

| 树莓派域名 | IPv4 | 子网掩码 | Proxy代理协议 | 监听端口 |
|:---:|:---:|:---:|:---:|:---:|
| `raspberrypi.local` | `192.168.1.2` | `255.255.254.0` | http | `7890` |

这些配置非常重要，在应用配置环节需要对应参数进行设置。
{:.warning}

## 命令行配置

### 临时配置

命令行进行临时配置非常简单，下面的指令即可指定http代理服务。

```bash
export http_proxy=http://192.168.1.2:7890
export https_proxy=http://192.168.1.2:7890
```

可以用`google.com`进行测试，如果代理配置成功则会输出下列内容。

```bash
$ curl google.com
<HTML><HEAD><meta http-equiv="content-type" content="text/html;charset=utf-8">
<TITLE>301 Moved</TITLE></HEAD><BODY>
<H1>301 Moved</H1>
The document has moved
<A HREF="http://www.google.com/">here</A>.
</BODY></HTML>
```

### 永久配置

想要每次打开命令行都自动配置好代理则需要对命令行启动时运行的脚本进行修改。  
在之前的一篇文章中我们知道了bash启动时[调用脚本的顺序]({% post_url 2023-02-07-linux-sudoer-hints %}#shell的启动调用顺序) ，bash在调用`$HOME/.profile`时会调用`$HOME/.bashrc`。  
笔者使用的是zsh，最后调用的则是`$HOME/.zshrc`。

<!-- 考虑到不是所有用户都有管理员权限，这里就以修改用户层面设置为例。   -->
为了方便管理和后期修改代理服务器地址，在`$HOME`目录下创建`.proxy`文件，并插入如下内容。

```bash
# filename: ~/.proxy
proxy_server="192.168.1.2"      # 代理服务器域名或者地址
proxy_port="7890"               # 代理服务器监听端口
proxy="http://${proxy_server}:${proxy_port}"
export http_proxy=$proxy
export https_proxy=$proxy
export no_proxy="localhost,127.0.0.1/8,::1,192.168.0.0/23,*.local"
```

最后一行的`no_proxy`需要根据自身网络环境进行配置，比如笔者的子网网段是`192.168.0.0/23`，则子网内的各设备不需要代理访问。

之后在`$HOME/.zshrc`中添加下列内容来在启动命令行时调用上的脚本，如果你使用的命令行是bash则需要修改`$HOME/.bashrc`。

```bash
if [ -f "$HOME/.proxy" ]; then
    source "$HOME/.proxy"
fi
```

这段脚本的含义是判断是否存在文件`$HOME/.zshrc`，如果存在则执行其中的内容。

配置完成之后可用重启命令行或者使用`source ~/.zshrc`指令让刚刚的配置生效，然后用`curl`指令测试。

## git配置proxy

如果你在使用git时设置的远程代码库用的是https地址，则下面的配置方式能让你的git通过代理访问远程仓库。  
如果想全局git配置，则可以编辑`$HOME/.gitconfig`添加如下内容

```config
[http]
    proxy = http://192.168.1.2:7890
[https]
    proxy = http://192.168.1.2:7890
```

如果特别指定GitHub代理，则使用下面的配置。

```config
[http "https://github.com/"]
    proxy = http://192.168.1.2:7890
    sslVerfy = false
```

但是用http访问GitHub牵扯到GitHub API的权限验证问题，不如ssh来的方便。
而这里配置的代理并不能约束ssh的行为。所以还要单独配置ssh的连接。

## ssh配置proxy

ssh配置在`$HOME/.ssh/config`中管理。
具体配置细节参看手册`man ssh_config`。  
这里配置GitHub的访问直接在文件中添加下面的内容即可。

```config
Host github.com
    Hostname github.com
    ProxyCommand    nc -X connect -x 192.168.1.2:7890 %h %p
```

其中的`nc`是Linux的一个工具，如果系统中没有则需要另外安装。

这样就能顺利通过ssh走proxy控制GitHub的repo。

### Windows

Win用户也可以使用`nc`作为代理指令，只不过系统一般不预装，需要去[官网](https://nmap.org/)手动下载安装。

```config
Host github.com
    HostName github.com
    ProxyCommand ncat.exe --verbose --proxy-type http --proxy 192.168.1.2:7890 %h %p
```

## 总结

命令行中走代理的操作大致上分为这三种，git、ssh和其他指令。分别从各自的配置文件中进行配置即可。  
以文件的形式管理也方便进行修改。

注意：本文中的一些IP地址和域名需要根据使用者的网络环境进行配置，切不可直接拷贝。
{:.warning}

## 参考

[OpenSSH config file on Windows - ProxyCommand not working](https://superuser.com/questions/1283560/openssh-config-file-on-windows-proxycommand-not-working)  
[Nmap.org](https://nmap.org/)
