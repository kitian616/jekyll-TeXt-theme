---
layout: article
title: 【树莓派学习笔记】额外的网卡和开机启动脚本
key: wlan1-and-systemd
permalink: /article/:title.html
article_header:
  type: cover
  image:
    src: /assets/images/posts/wlan1-and-systemd/head.png
tags: 
  - 树莓派
  - bash
  - Linux
  - systemd
  - 开机启动
  - WiFi
author: Yu Xiaoyuan
show_author_profile: true
---

最近为了把机器学习项目部署到终端设备，又把树莓派捡起来。这次的内容与机器学习关系不大，只是摸鱼的工作。  
之前是把树莓派板载的无线网卡作为基站使用了，然而办公室又没有多余的网线接口，恰好手头有个usb网卡，就直接插在树莓派上作为无线网接入使用。

<!--more-->

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- end private variable of Liquid -->

![usb无线网卡]({{ image_dir }}/wlan1.jpg){:.rounded}
网卡搞定了，我们希望自动接入无线网，下面就进入正题

## 无线网卡配置

在没有额外的无线网卡的时候，使用指令`ifconfig`应当是这个结果。

![网络设备]({{ image_dir }}/no_wlan1.png){:.border}

`eth0`是以太网，就是插网线的；`lo`是回环；`wlan0`是板载无线网卡，[之前]({% post_url 2019-11-02-new-pi-4b %}#ap模式-wifi基站)被我们配置到`create_ap`作为基站了。

可以看到以太网是没有接入的，这里我们通过`wlan0`的ap连接到树莓派进行配置。
接入新的无线网卡之后再运行`ifconfig`指令，看到这样的结果。

![接入无线网卡]({{ image_dir }}/with_wlan1.png){:.border}

这时出现了一个新的设备`wlan1`，这就是我们的无线网卡。
如果看不到，可能是被关闭了，可以用指令`ifconfig wlan1`查看。

![down]({{ image_dir }}/wlan1_down.png){:.border}

与前面的图进行比较，可以发现flags字段后面的尖括号里少了个up，这就说明是被关闭了。
使用指令`sudo ifconfig wlan1 up`可以启动它。启动之后再执行`ifconfig wlan1`可以看到变化。

![up]({{ image_dir }}/wlan1_up.png){:.border}

下一步扫描你的wifi环境，执行指令`sudo iwlist wlan1 scan`。

![scan]({{ image_dir }}/scan.jpg){:.border}

从这里面找到你的WiFi，如果找不到，可能是你家的WiFi是5GHz，而你的网卡不支持，那就挺难受的。

现在普遍的WiFi加密方式是WPA-PSK，所以下一步配置wpa_supplicant。

新建文件`/etc/my_wpa_supplicant.conf`，输入如下内容。

```conf
ctrl_interface=/var/run/wpa_supplicant
network={
	ssid="your_ssid"
	psk="your_password"
}
```

其他配置都是默认值。下一步进行测试。
执行指令`sudo wpa_supplicant -B -i wlan1 -c /etc/my_wpa_supplicant.conf`。

![wpa_supplicant]({{ image_dir }}/wpa_supplicant.png){:.border}

显示这个就是成功了，再次使用`ifconfig`查看。

![connected]({{ image_dir }}/successfully_connected.png){:.border}

注意到`wlan1`有了ip地址，说明接入成功。下一步把这个加入开机启动。这里介绍两种开机启动方法。

## 开机启动

在[之前的笔记]({% post_url 2019-11-02-new-pi-4b %}#ap模式-wifi基站)中，我们为了让`create_ap`开机启动，将一段bash指令添加到了`/etc/rc.local`。

Linux系统在启动的时候，各项服务都是由`systemd`进行管理的，`rc.local`只是其中的一片服务。
对于比较简单的启动指令，可以放在这里面进行。
比如`create_ap`和`wpa_supplicant`的两个指令都可以放在这个文件里自动执行。

把上面的指令`sudo wpa_supplicant -B -i wlan1 -c /etc/my_wpa_supplicant.conf`添加到`/etc/rc.local`中，放在`exit 0`之前即可。

当然这是简单的情况。如果我们的一些开机启动程序比较复杂，需要依赖其他的服务进行，这时就需要通过配置`systemd`服务文件来进行设置。

比如后面我要进行无线调试，我想让树莓派开机以后接入互联网并给我发邮件告诉我它的ip。写一个python脚本，放在`/home/pi/Program/email/send_ip.py`。

``` python
#!/usr/bin/env python3
# -*- coding: utf-8 -*-
from email import encoders
from email.header import Header
from email.mime.text import MIMEText
from email.utils import parseaddr, formataddr
import smtplib
import os
from time import sleep

sleep(20)

def _format_addr(s):
    name, addr = parseaddr(s)
    return formataddr((Header(name, 'utf-8').encode(), addr))

# use your own email here
from_addr = '**********@163.com'
password = '**************'
to_addr = '***************@qq.com'
smtp_server = 'smtp.163.com'
ip_file = '/home/pi/.hostname'

os.system("hostname -I > "+ip_file)
ip_file = open(ip_file)

server = smtplib.SMTP(smtp_server, 25)
server.set_debuglevel(1)
server.login(from_addr, password)

msg = MIMEText(ip_file.readline().replace(' ', '\n'), 'plain', 'utf-8')
msg['From'] = _format_addr(from_addr)
msg['To'] = _format_addr(to_addr)
msg['Subject'] = Header('rpi system ip', 'utf-8').encode()
server.sendmail(from_addr, [to_addr], msg.as_string())

server.quit()
ip_file.close()
```

使用指令`sudo chmod a+x send_ip.py`提升权限。

`注意`{:.warning}  
首先，你可能需要在邮箱服务商那里开启smtp服务；  
其次，我们的邮箱服务是需要联网的，所以这个脚本必须在联网以后执行（这就要求这个脚本在rc.local后面执行）。

使用`systemd`配置服务启动顺序，在`/usr/lib/systemd/system`下创建新文件`sendip.service`， 输入如下内容。

```systemd
[Unit]
Description=a service to send ip through email
After=rc-local.service
[Service]
Type=idle
Restart=on-failure
ExecStart=/home/pi/Program/email/send_ip.py
[Install]
WantedBy=multi-user.target
```

其中：

- `[Unit]`字段是写一些描述信息，关键是这里的After字段，表示在哪个服务后面启动。由于我们的邮箱需要联网，所以要在接入WiFi以后执行，也就是在rc-local.service服务后面启动。

- `[Service]`字段是服务的启动信息。ExecStart是可执行文件的地址，就填脚本的地址。其他的字段参看后面的参考文献。

- `[Install]`字段是服务的安装信息。 主要是提供一些依赖性。比如这里为了启动就被multi-user.target所依赖。

文件配置完成，使用指令`sudo systemctl enable sendip.service`来创建符号链接。
然后可以进行测试了，测试指令`sudo systemctl start sendip.service`。
成功之后就可以进行重启测试了。

## 总结

折腾了一天就在忙活这个开机启动了，无线网卡倒是好配置，但是服务的依赖关系确实要多看一些东西。  
直接照搬别人的服务文件信息是不好使的，一定要考虑好其中的逻辑关系和依赖关系。  
参考中的东西比我这里写得要多得多，有需要的可以看后面的参考文献。

## 参考文献

无线网卡配置：[Linux 手动无线网卡 WiFi 配置](https://blog.csdn.net/vic_qxz/article/details/88658802)

systemd简明教程：[最简明扼要的 Systemd 教程，只需十分钟](https://blog.csdn.net/weixin_37766296/article/details/80192633)

systemd入门教程：[Systemd 入门教程：命令篇](http://www.ruanyifeng.com/blog/2016/03/systemd-tutorial-commands.html)

systemd入门教程：[Systemd 入门教程：实战篇](https://www.ruanyifeng.com/blog/2016/03/systemd-tutorial-part-two.html)