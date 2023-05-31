---
layout: article
title: 【树莓派学习笔记】局域网代理服务器
permalink: /article/:title.html
key: local-proxy-server
tags: 
  - 树莓派学习笔记
  - 树莓派
  - bash
  - Linux
  - clash
  - proxy
  - static ip
author: Yu Xiaoyuan
show_author_profile: true
---

由于笔者经常需要连接GitHub获取代码，而没有代理同步会非常慢甚至完全不可用。所以笔者用树莓派搭建一个局域网的代理服务器，并设置静态IP，给局域网内所有设备提供代理转发服务。

<!--more-->

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- end private variable of Liquid -->

## [clash](https://github.com/Dreamacro/clash)

> A rule-based tunnel in Go.

### 安装

首先到clash作者的[Releases页面](https://github.com/Dreamacro/clash/releases)下载最新的版本，注意匹配自己的系统架构。  
下载完成之后获得一个`.gz`格式的压缩包，使用命令解压。

比如对于32位的树莓派系统，需要下载armv7版本。

```bash
# 下载
wget https://github.com/Dreamacro/clash/releases/download/v1.11.4/clash-linux-armv7-v1.11.4.gz
# 解压
gzip -d clash-linux-armv7-v1.11.4.gz
```

解压之后获得一个`clash-linux-armv7-v1.11.4`文件，使用`file`指令查看文件格式。

```bash
$ file clash-linux-armv7-v1.11.4
clash-linux-armv7-v1.11.4: ELF 32-bit LSB executable, ARM, EABI5 version 1 (SYSV), statically linked, stripped
```

可以看到这是一个32位ARM架构可执行文件。

为了能在系统指令内直接调用`clash`，将上面的可执行文件拷贝(或移动)到系统的`PATH`中，并赋予执行权限。

```bash
# 执行权限
chmod a+x clash-linux-armv7-v1.11.4
# 拷贝
sudo cp clash-linux-armv7-v1.11.4 /usr/local/bin/clash
```

然后尝试调用一下。

```bash
$ clash -v
Clash v1.11.4 linux arm with go1.18.3 Thu Jul  7 14:16:39 UTC 2022
```

在`/etc`下创建一个目录用于存放`clash`的配置文件。并且将你的代理服务商提供的`config.yaml`放到该目录下。

```bash
sudo mkdir /etc/clash
sudo touch /etc/clash/config.yaml
```

### 开机启动

使用之前提到的[`systemd`方式]({% post_url 2021-05-27-wlan1-and-systemd %}#开机启动)执行一个系统服务守护。  
但是这里使用了稍微复杂一点但是可定制空间更大配置方式，不仅设置了`systemd`的守护配置，还分别提供了服务启动和结束的脚本。

首先创建各文件。

```bash
sudo touch /usr/lib/systemd/system/clash.service
sudo touch /etc/clash/start-clash.sh
sudo touch /etc/clash/stop-clash.sh
```

`systemd`守护配置。

```systemd
[Unit]
Description=Clash daemon, A rule-based proxy in Go.
After=network.target

[Service]
User=root
Type=simple
Restart=always
RuntimeMaxSec=259200
ExecStart=/etc/clash/start-clash.sh
ExecStop=/etc/clash/stop-clash.sh
Environment="CLASH_URL=https://your.clash-config.url"

[Install]
WantedBy=multi-user.target
```

上面的配置定义了一个`root`用户的简单类型守护，并且通过设置最长运行时间来自动重启。

注意：请在[`Enviroment`]({% post_url 2022-08-18-local-proxy-server %}#:~:text=Environment%3D%22CLASH_URL%3Dhttps%3A//your.clash%2Dconfig.url%22)行插入你的代理服务商提供的clash配置更新链接。
{:.warning}

启动脚本。

{% highlight bash linenos %}
#!/bin/bash
# save this file to ${HOME}/.config/clash/start-clash.sh

# save pid file
echo $$ > /etc/clash/clash.pid
config=/etc/clash/config.yaml
diff $config <(curl -s ${CLASH_URL})
if [ $? != 0 ]; then
    curl -L -o $config ${CLASH_URL}
fi

allow_lan=`sed -n '/allow-lan/=' ${config}`
sed -i "${allow_lan}a allow-lan: true" ${config}
sed -i "${allow_lan}d" ${config}

log_level=`sed -n '/log-level/=' ${config}`
sed -i "${log_level}a log-level: debug" ${config}
sed -i "${log_level}d" ${config}

/usr/local/bin/clash -d /etc/clash/
{% endhighlight %}

上面的启动脚本的主要功能是记录当前进程PID用于后面的结束脚本杀死进程。  
辅助功能包括下面几个方面：

* 通过链接更新clash配置
* 修改clash配置文件的某些字段

其中第7行是对比本地配置和链接指向的配置，若不一致则在后面三行进行更新。  
这里进行判断其实是冗余的，因为如果每次更新配置文件后都进行修改，那不管链接指向的是否更新了，本地与云端对比总是不一样的。

后面12到14行和16到18行都是通过`sed`指令匹配某个字符串找到目标行所在位置，然后在目标行后插入修改后的内容，最后删除之前找到的目标行。  
这里修改了`allow-lan`字段允许LAN口其他设备访问，修改了`log-level`字段使其输入debug级别的日志。

结束脚本。

```bash
#!/bin/bash
# file: /etc/clash/stop-clash.sh

# read pid file
PID=`cat /etc/clash/clash.pid`
kill -9 ${PID}
rm /etc/clash/clash.pid
```

使用上述的设置，服务启动时会调用`start-clash.sh`脚本，将进程的PID保存到文本文件中，并根据服务配置中的url进行`config.yaml`的更新；
结束时调用`stop-clash.sh`，使用刚才的PID杀死进程。

之后给各脚本赋予执行权限。

```bash
sudo chmod a+x /etc/clash/st*.sh
```

这中间缺少一个`mmdb`文件，需要手动下载。

```bash
wget http://www.ideame.top/mmdb/lite/Country.mmdb
sudo mv Country.mmdb /etc/clash/Country.mmdb
```

完成之后设置启动。

```bash
sudo systemctl enable clash.service
sudo systemctl start clash.service
```

## 静态IP

路由器有时候会给设备重新分配IP，这给我们服务器的设置带来了很大的不便利。  
为了能让局域网内的各设备更方便地访问服务器，给服务器设置静态IP。  
笔者这边的路由器dhcp服务设置IP池是`192.168.1.100-200`，子网掩码为23位。只要不冲突，在路由器IP池之外的IP都可以随意设置静态。

编辑文件`/etc/dhcpcd.conf`来设置静态IP。  
找到其中一段注释内容为`# Example static IP configuration:`，这一段注释给出了一个静态IP的配置样例，参考这一段编写你自己的配置。

```conf
# Example static IP configuration:
#interface eth0
#static ip_address=192.168.0.10/24
#static ip6_address=fd51:42f8:caae:d92e::ff/64
#static routers=192.168.0.1
#static domain_name_servers=192.168.0.1 8.8.8.8 fd51:42f8:caae:d92e::1
interface eth0
static ip_address=192.168.1.2/23
static routers=192.168.1.1
static domain_name_servers=192.168.1.1
```

## 拓展阅读

clash：[clash](https://github.com/Dreamacro/clash)

静态IP：[树莓派设置静态IP地址](https://zhuanlan.zhihu.com/p/435714438)

树莓派防火墙：[【树莓派学习笔记】给树莓派安装防火墙]( {% post_url 2022-12-08-firewall-on-pi %})
