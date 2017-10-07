---
layout: post
title: 如何通过 SSH 来连接 VirtualBox 虚拟机
key: 10002
tags: Linux VirtualBox SSH
category: blog
date: 2015-10-24 23:00:00 +08:00
---

说起来使用 Linux 也快有 4 年了。从最开始的 Ubuntu 到 CentOS 再到现在的 Arch Linux。

不得不说 Linux 确实是一个非常适合开发的系统，Linux 的软件包管理系统使得软件的安装，升级都非常的方便。而在 Windows 下，你得先去官网下载安装程序，安装路径，下一步，下一步，完成……升级也是相当的麻烦（别跟我提什么软件管家之类的，我已深恶痛绝）。总之，**Linux 大法好，奈何软件少。**很多时候我还是离不开 Windows，因此我的 Linux 都是安装在 VirtualBox 虚拟机（因为它是免费开源的）中的。

有时为了共享虚拟机资源，我们需要把 VirtualBox 里的 Arch Linux 虚拟机作为“服务器”，其他的终端便可以连接登陆“服务器”，实现对服务器的操作。
<!--more-->

## 环境

- SSH 服务端：虚拟机上的 Arch Linux（实体机系统为：Windows 10）

- SSH 客户端：Windows / Android

## SSH 协议

上面所说的就是远程登录，在 Linux 世界中，远程登陆最常用的就是 SSH 协议。

SSH 是由客户端和服务端的软件组成的。

服务端是一个守护进程（daemon），他在后台运行并响应来自客户端的连接请求。服务端一般是 sshd 进程，提供了对远程连接的处理，一般包括公共密钥认证、密钥交换、 对称密钥加密和非安全连接。

客户端包含 SSH 程序以及像 scp（远程拷贝）、slogin（远程登陆）、 sftp（安全文件传输）等其他的应用程序。

详情见[维基百科](https://zh.wikipedia.org/wiki/Secure_Shell)（可能需要爬梯）

## 主机端（ArchLinux）

OpenSSH 是 SSH 的开源实现，它包含了服务端和客户端。要想连接到 VBox 中的 Arch Linux，首先我们需要在 Arch Linux 上安装并启动 sshd 服务（即 OpenSSH 的主机端服务）。

安装 OpenSSH：

{% highlight console %}
# pacman -Syy openssh
{% endhighlight %}

启动 sshd 服务：

{% highlight console %}
# systemctl start sshd
{% endhighlight %}

要让 sshd 服务自启动可以：

{% highlight console %}
# systemctl enable sshd
{% endhighlight %}

这样服务端的 sshd 守护进程已经启动了，它将一直等待连接请求并对请求进行相应。

因为我们是无法直接连接到虚拟机上的，必须要通过实体机来间接的连接到虚拟机（如下图所示）。如果是直接把实体机作为 sshd 服务端就不需要进行次步骤。

![虚拟机连接示意图](https://ww2.sinaimg.cn/large/73bd9e13jw1exdn5mgqedj20d609z0ss.jpg)

在 VBox 网络设置里选择端口转发，将虚拟机的 SSH 服务端口映射到实体机上。

![VBox网络设置/端口转发](https://ww3.sinaimg.cn/large/73bd9e13jw1ex0xxg0sqnj20hc099t8p.jpg)

这里我把 VirtualBox 虚拟机的 22 端口（sshd 服务端口）转发到实体机的 2201 端口，这样就可以通过访问实体机的 2201 端口来访问虚拟机的 sshd 服务端口。

## 客户端（Windows）

Windows 下的 SSH 软件有很多，这里使用 [PuTTY](http://www.putty.org/)（官网打不开可以使用快照系统）。

装好之后，我们可以看到好几个快捷方式，其中 PuTTY 是主程序，进行 SSH 连接。PuTTYgen 负责公私密钥对生成，这在后面会用到。

![PuTTY](https://ww4.sinaimg.cn/large/73bd9e13jw1exdp9flr7lj204k072aa8.jpg)

打开 PuTTY 填写 ip（Windows 下输入 `ipconfig` 查看 ip 地址）和端口，就是实体机的 ip（注意不是虚拟机的!）和上一步映射的端口，设置保存。

![填写ip和端口](https://ww4.sinaimg.cn/large/73bd9e13jw1ex0xxh007qj20ck0c5aag.jpg)

由于我是在实体机本地连接的，所以 ip 地址用的是 localhost，如果是实体机之外的设备（比如后面的安卓），就必须要输入实体机的 ip 地址，并且该设备必须跟实体机在同一个局域网中。

填写好了 ip 和端口之后就可以点击 Open 按钮登陆了。

![使用密码方式登陆](https://ww3.sinaimg.cn/large/73bd9e13jw1ex0xxl9bj9j20id0bmgli.jpg)

为了方便，我们可以填写自动登陆的用户名，这里我的用户名为 chi，这样每次连接后就不用再输入用户名了。

![填写用户民](https://ww3.sinaimg.cn/large/73bd9e13jw1ex0xxhp0zpj20ck0c5glx.jpg)

### 生成密钥对

要使用更加安全可靠的身份验证，我们需要设置公私密钥对。一个密钥对包含私钥和对应的公钥，私钥顾名思义就是自己保存的密钥，公钥就是对外公开的密钥。

打开 PuTTYgen，首先生成密钥对，当鼠标在空白区域滑动时可以加快密钥对的生成，挺有趣。

![生成密钥对](https://ww4.sinaimg.cn/large/73bd9e13jw1ex0xxjphp5j20db0cygls.jpg)

生成好密钥对后，我们将公钥复制放到 ssh 服务器（虚拟机）~/.ssh/authorized_keys 文件中，然后点击 Save private key 保存私钥到本地。

![保存私钥](https://ww2.sinaimg.cn/large/73bd9e13jw1ex0xxk1dexj20db0cy0tc.jpg)

然后到 PuTTY 主程序设置刚才保存的私钥文件，这样每次连接时 PuTTY 就会读取私钥文件跟服务端的公钥进行配对。这种身份验证方式比密码更为安全。因为私钥是保存在客户端上的，不会在网络上传输。

![设置私钥](https://ww2.sinaimg.cn/large/73bd9e13jw1ex0xxiw0wlj20ck0c50t4.jpg)

建议在保存私钥到本地时设置私钥文件密码，这样安全性更高。

在登陆时直接输入上面设置的密钥文件密码（注意不是用户密码！）即可登录。如果没有设置密钥文件密码则会自动登陆。

![使用密钥对登陆](https://ww3.sinaimg.cn/large/73bd9e13jw1exdos2mzglj20id0bmgli.jpg)

## 客户端（Android）

Android 下的 SSH 软件有很多，Play 上一搜一大把，这里使用 [ConnectBot](https://play.google.com/store/apps/details?id=org.connectbot&hl=zh-CN)，排名靠前，评分也不错。

使用方法更上面的 PuTTY 使用差不多，首先设置 ip 和端口，也可以设置公私密钥对。

![ConnectBot 设置](https://ww4.sinaimg.cn/large/73bd9e13jw1exdrnkq13tj218g1z447u.jpg)

登陆后就可以进行操作了，蓝牙键盘是必要的，触摸屏打字太慢。

![登录成功](https://ww3.sinaimg.cn/large/73bd9e13jw1exdrnjix2uj218g1z4qfh.jpg)

我的 [vim 配置文件](https://github.com/kitian616/vimrc)

![vimrc](https://ww2.sinaimg.cn/large/73bd9e13jw1exdrnih755j218g1z47n8.jpg)
