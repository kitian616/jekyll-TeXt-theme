---
layout: article
title: 树莓派学习笔记 - 额外的网卡和开机启动脚本
article_header:
  type: cover
  image:
    src: /assets/images/2021-05-27-wlan1-and-systemd/head.png
tags: 
- 树莓派
- bash
- Linux
- systemd
- 开机启动
- WiFi
---

最近为了把机器学习项目部署到终端设备，又把树莓派捡起来。这次的内容与机器学习关系不大，只是摸鱼的工作。  
之前是把树莓派板载的无线网卡作为基站使用了，然而办公室又没有多余的网线接口，恰好手头有个usb网卡，就直接插在树莓派上作为无线网接入使用。

<!--more-->

![usb无线网卡](/assets/images/2021-05-27-wlan1-and-systemd/wlan1.jpg){:.rounded}
网卡搞定了，我们希望自动接入无线网，下面就进入正题

## 无线网卡配置

在没有额外的无线网卡的时候，使用指令ifconfig应当是这个结果。


网络设备
eth0是以太网，就是插网线的；lo是回环；wlan0是板载无线网卡，之前被我们配置到create_ap作为基站了。

可以看到以太网是没有接入的，这里我们通过wlan0的ap连接到树莓派进行配置。接入无线网卡之后再运行ifconfig指令，看到这样的结果。


接入无线网卡
这时出现了一个新的设备wlan1，这就是我们的无线网卡。如果看不到，可能是被关闭了，可以用指令ifconfig wlan1查看。


down
与前面的图进行比较，可以发现flags字段后面的尖括号里少了个up，这就说明是被关闭了。使用指令sudo ifconfig wlan1 up可以启动它。启动之后再执行ifconfig wlan1可以看到变化。


注意到flags字段已经up了
下一步扫描你的wifi环境，执行指令sudo iwlist wlan1 scan。


我这边网络环境比较复杂，就截一点
从这里面找到你的WiFi，如果找不到，可能是你家的WiFi是5GHz，而你的网卡不支持，那就挺难受的。

现在普遍的WiFi加密方式是WPA-PSK，所以下一步配置wpa_supplicant。

新建文件/etc/my_wpa_supplicant.conf，输入如下内容。

ctrl_interface=/var/run/wpa_supplicant
network={
	ssid="your_ssid"
	psk="your_password"
}
其他配置都是默认值。下一步进行测试。执行指令sudo wpa_supplicant -B -i wlan1 -c /etc/my_wpa_supplicant.conf。


显示这个就是成功了，再次使用ifconfig查看。


注意到wlan1有了ip地址，说明接入成功。下一步把这个加入开机启动。这里介绍两种开机启动方法。

开机启动
各位如果有印象的话，在树莓派学习笔记1中，我们为了让create_ap开机启动，将一段bash指令添加到了/etc/rc.local。

Linux系统在启动的时候，各项服务都是由systemd进行管理的，rc.local只是其中的一片服务。对于比较简单的启动指令，可以放在这里面进行。比如create_ap和wpa_supplicant的两个指令都可以放在这个文件里自动执行。

把上面的指令sudo wpa_supplicant -B -i wlan1 -c /etc/my_wpa_supplicant.conf添加到/etc/rc.local中，放在exit 0之前即可。

当然这是简单的情况。如果我们的一些开机启动程序比较复杂，需要依赖其他的服务进行，这时就需要通过配置systemd服务文件来进行设置。

比如后面我要进行无线调试，我想让树莓派开机以后接入互联网并给我发邮件告诉我它的ip。写一个python脚本，放在/home/Program/email/send_ip.py。

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
使用指令sudo chmod a+x send_ip.py提升权限。注意到两点，首先你要在邮箱服务商那里开启smtp服务；其次，我们的邮箱服务是需要联网的，所以这个脚本必须在联网以后执行。这就要求这个脚本在rc.local后面执行。

使用systemd配置服务启动顺序，在/usr/lib/systemd/system下创建新文件sendip.service， 输入如下内容。

[Unit]
Description=a service to send ip through email
After=rc-local.service
[Service]
Type=idle
Restart=on-failure
ExecStart=/home/pi/Program/email/send_ip.py
[Install]
WantedBy=multi-user.target
[Unit]字段是写一些描述信息，关键是这里的After字段，表示在哪个服务后面启动。由于我们的邮箱需要联网，所以要在接入WiFi以后执行，也就是在rc-local.service服务后面启动。

[Service]字段是服务的启动信息。ExecStart是可执行文件的地址，就填脚本的地址。其他的字段参看后面的参考文献。

[Install]字段是服务的安装信息。 主要是提供一些依赖性。比如这里为了启动就被multi-user.target所依赖。

文件配置完成，使用指令sudo systemctl enable sendip.service来创建符号链接。然后可以进行测试了，测试指令sudo systemctl start sendip.service。成功之后就可以进行重启测试了。

总结
折腾了一天就在忙活这个开机启动了，无线网卡倒是好配置，但是服务的依赖关系确实要多看一些东西。直接照搬别人的服务文件信息是不好使的，一定要考虑好其中的逻辑关系和依赖关系。参考中的东西比我这里写得要多得多，有需要的可以看后面的参考文献。

参考文献
无线网卡配置：Linux 手动无线网卡 WiFi 配置

systemd简明教程：最简明扼要的 Systemd 教程，只需十分钟

systemd入门教程：Systemd 入门教程：命令篇

systemd入门教程：Systemd 入门教程：实战篇