---
layout: article
title: 【树莓派学习笔记】局域网代理服务器
permalink: /article/local-proxy-server
key: local-proxy-server
tags: 
  - 树莓派
  - bash
  - Linux
  - clash
  - proxy
  - static ip
author: Yu Xiaoyuan
show_author_profile: true
---

由于笔者经常需要连接GitHub获取代码，而没有代理同步会非常慢。所以笔者用树莓派搭建一个局域网的代理服务器，并设置静态IP，给局域网内所有设备提供代理转发服务。

<!--more-->

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

为了能在系统指令内直接调用`clash`，将上面的可执行文件拷贝(或移动)到系统的`PATH`中，并赋予执行权限。

```bash
# 拷贝
sudo cp clash-linux-armv7-v1.11.4 /usr/local/bin/clash
# 执行权限
sudo chmod a+x /usr/local/bin/clash
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

使用之前提到的[`systemd`方式](/article/wlan1-and-systemd#开机启动)执行一个系统服务守护。  
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
Type=simple
User=root
# Restart=always
# ExecStart=/usr/local/bin/clash -d /etc/clash
ExecStart=/etc/clash/start-clash.sh
ExecStop=/etc/clash/stop-clash.sh
Environment="CLASH_URL=https://your.clash-config.url"

[Install]
WantedBy=multi-user.target
```

启动脚本。

```bash
#!/bin/bash
# file: /etc/clash/start-clash.sh

# save pid file
echo $$ > /etc/clash/clash.pid

diff /etc/clash/config.yaml <(curl -s ${CLASH_URL})
if [ "$?" == 0 ]
then
    echo "nothing changed"
else
    echo "updating config.yaml"
    # uncomment next line if you wish to update config.yaml on each start up
    # curl -L -o /etc/clash/config.yaml ${CLASH_URL}
fi
/usr/local/bin/clash -d /etc/clash/
```

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

赋予执行权限。

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
