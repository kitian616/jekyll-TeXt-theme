---
layout: article
title: 【树莓派学习笔记】树莓派安装Dronekit并连接Pixhawk飞控
permalink: /article/:title.html
key: dronekit-on-pi-and-pixhawk
article_header:
  type: cover
  image:
    src: /assets/images/posts/dronekit-on-pi-and-pixhawk/head.png
tags: 
  - 树莓派学习笔记
  - 树莓派
  - 无人机
  - bash
  - DroneKit
  - Pixhawk
author: Li Tianyan
show_author_profile: true
---

<!-- abstract begin -->

[上一节](https://zhuanlan.zhihu.com/p/663071221)中我们在树莓派上配置了基本的开发环境，可以用PC上的Ubuntu系统远程ssh访问树莓派。  
接下来需要配置Dronekit库，这是一个控制无人机和获取无人机参数的Python库。

<!-- abstract end -->

<!--more-->

[*原文链接*](https://zhuanlan.zhihu.com/p/663141193)

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- {%- increment equation-h2-1 -%} -->
<!-- end private variable of Liquid -->

## 0\. 连接登陆树莓派

在Ubuntu系统命令行窗口输入：（切记此时PC与树莓派必须连在同一个路由器下，注意变更路由器后IP也会改变）。


**树莓派的IP地址有可能是动态分配的，所以你必须每次都连好wifi后都确定树莓派IP后再ssh**

```bash
ssh "树莓派上用户名"@"树莓派IP"
```

## 1\. 此时需要确保在根目录下，输入"cd"

![确保在根目录下]({{ image_dir }}/to-home.jpg)  
*确保在根目录下*

## 2\. 安装pip和python-dev

```bash
sudo apt-get install python-pip python-dev
```

这一步中可能是树莓派中安装但是最新系统，提示需要安装python3版本对应的两个软件

![]({{ image_dir }}/install-pip.jpg )  
*提示安装python3版本库*

因此改为：

```bash
sudo apt-get install python3-pip python-dev-is-python3
```

## 3\. 接下来安装DroneKit库

```bash
sudo pip install dronekit
```

可是结果externally-managed-environment报错如下

![]({{ image_dir }}/dronekit-error.jpg)

BD后发现了此问题的 [三个解法](https://link.zhihu.com/?target=https%3A//www.yaolong.net/article/pip-externally-managed-environment/%23%25E6%2596%25B9%25E6%25A1%2588%25E4%25B8%2580%25E7%25B2%2597%25E6%259A%25B4-%25E5%258E%25BB%25E6%258E%2589%25E8%25BF%2599%25E4%25B8%25AA%25E6%258F%2590%25E7%25A4%25BA)，选择了其中最麻烦但是最稳妥的方法——使用pipx代替pip

先安装pipx，这一步会花点时间

```bash
sudo apt install pipx
```

再把pipx加入路径中

```bash
pipx ensurepath
```

然后需要重启树莓派，重新ssh登陆

```bash
### 一些主要的sudo命令
sudo halt                                   #清除进程并关机
sudo reboot                                 #重启
sudo shut down -h 14:30                     #定时关机
clear                                       #清屏
cd /folder1/folder2/...                     #打开路径
ls                                          #显示当前文件夹的目录
sudo find / -name opps.txt                  #寻找opps.txt文件
sudo mv ~/file /folder1/folder2/            #把folder1里的file移动到folder2
sudo raspi-config                           #打开配置页面
sudo ifconfig -a                            #树莓派网络配置信息
```

重新启动后输入

```bash
sudo pipx install dronekit
```

但是还是没法安装，可能是公众号上的方法因为版本更新有些不一致，所以接下来使用了 [官方Dronekit的方法](https://link.zhihu.com/?target=https%3A//dronekit-python.readthedocs.io/en/latest/guide/quick_start.html)

### 3.1 官方方法安装DroneKit-Python

```bash
sudo apt-get install python-pip python-dev
```

上一行命令无法运行，于是在提示下换用下面的命令行

```bash
sudo apt-get install python3-pip python-dev-is-python3
```

可是安装了无数次，试了无数办法，依旧无法正常安装Dronekit，只要按照教程不用树莓派的官方镜像系统，因此需要重刷系统镜像

（1）下载安装最新的 [Ubuntu-mate树莓派镜像](https://link.zhihu.com/?target=https%3A//ubuntu-mate.org/download/armhf/)

步骤后之前刷树莓派官方的方法一样，只不过这次我们需要安装的镜像文件换成了链接里的文件，balenaEtcherh或者树莓派官方的镜像安装其均可，开机后完成基本的初始设置


（2） 更新系统

```bash
sudo apt-get update
sudo apt-get upgrade
```

这一步很重要，后一步一般会花费很长时间，我就是这一步没有完成好导致后面安装时频频出错

（3）安装必要的软件和开启ssh服务

```bash
sudo apt-get install vim
sudo apt-get install xrdp

sudo apt-get install openssh-server
sudo ps -e | grep ssh
sudo /etc/init.d/ssh start
sudo systemctl enable ssh
```

然后你可以使用ifconfig查看树莓派的IP，就可以远程ssh了

（4）安装Dronekit

```bash
sudo apt-get install python2-dev
sudo apt-get install python3-pip

sudo apt-get install libxml2-dev
sudo apt-get install libxslt1-dev
sudo apt-get install zlib1g-dev

sudo pip install dronekit
```

最后的安装成功结果：

![]({{ image_dir }}/install-dronekit.jpg)

## 4\. 测试DroneKit

回到根目录，创建一个名为“test”的文件夹

```bash
mkdir -p ~/test
```

然后在test里新建connect.py脚本测试树莓派是否能连接到Pixhawk飞控板

```bash
cd test
touch connect.py
```

再使用vim来编写这个connect.py脚本（我选择直接在树莓派里编辑）

```bash
sudo vim connect.py
```

脚本内容如下

```python
from dronekit import connect

# Connect to the Vehicle (in this case a UDP endpoint)
vehicle = connect('/dev/ttyUSB0', wait_ready=True, baud=921600)

# vehicle is an instance of the Vehicle class
print "Autopilot Firmware version: %s" % vehicle.version   #飞控版本
print "Autopilot capabilities (supports ftp): %s" % vehicle.capabilities.ftp
print "Global Location: %s" % vehicle.location.global_frame    #全球定位信息（经纬度和高度）
print "Global Location (relative altitude): %s" % vehicle.location.global_relative_frame  #相对坐标
print "Local Location: %s" % vehicle.location.local_frame    #NED相对位置信息，东南西北
print "Attitude: %s" % vehicle.attitude    #无人机朝向：roll,pitch,yaw单位弧度（-π~π）
print "Velocity: %s" % vehicle.velocity    #三维速度（m/s）
print "GPS: %s" % vehicle.gps_0            #GPS信息
print "Groundspeed: %s" % vehicle.groundspeed         #地速（m/s）
print "Airspeed: %s" % vehicle.airspeed               #空速（m/s）
print "Gimbal status: %s" % vehicle.gimbal            #云台信息（单位度）
print "Battery: %s" % vehicle.battery                 #电池信息
print "EKF OK?: %s" % vehicle.ekf_ok                  #扩展卡尔曼滤波器信息
print "Last Heartbeat: %s" % vehicle.last_heartbeat
print "Rangefinder: %s" % vehicle.rangefinder         #超声波或激光雷达模块状态
print "Rangefinder distance: %s" % vehicle.rangefinder.distance     #超声波或激光雷达模块的传感距离
print "Rangefinder voltage: %s" % vehicle.rangefinder.voltage       #超声波或激光雷达模块电压
print "Heading: %s" % vehicle.heading                               #无人机朝向（单位度）
print "Is Armable?: %s" % vehicle.is_armable                        #是否可以解锁
print "System status: %s" % vehicle.system_status.state             #系统状态
print "Mode: %s" % vehicle.mode.name                                # settable飞行模式
print "Armed: %s" % vehicle.armed                                   # settable解锁状态
```

## 5\. 定义USB口

到这里就涉及到树莓派和飞控的通信问题，树莓派使用USB口与飞控通信，这里需要给端口权限,首先需要确认树莓派的USB与飞控的telem2口相连了

查看端口

```bash
ls /dev/ttyUSB*
```

在执行上一步时，系统提示找不到对应的ttyUSB0，后来 [查询资料](https://link.zhihu.com/?target=https%3A//blog.csdn.net/linklzqq/article/details/130213658) 发现是这个端口被brltty占用，于是直接卸载了这个软件

```bash
sudo apt remove brltty
```

重启后可以找到ttyUSB0了

![]({{ image_dir }}/ttyusb0.png)

接下来，给这个USB口赋予权限：

```bash
sudo chmod 666 /dev/ttyUSB0
```

以上只是暂时的，下面来永久地改写USB口读写权限的方法

```bash
ls usb -vvv
```

可是系统提示上一步中的usb无法确认，所以找了一个 [别的方法](https://link.zhihu.com/?target=https%3A//blog.csdn.net/Sunnyxbl/article/details/108241589)

首先获取dmesg权限

```bash
sudo sysctl kernel.dmesg_restrict=0
```

然后系统会返回ttyUSB0对应的usb序号，如下图中的1-1.3

![]({{ image_dir }}/dmesg-ttyusb0.png)

然后我们需要获取对应USB的idVendor和idProduct，输入

```bash
udevadm info --name=/dev/ttyUSB0 --attribute-walk
```

命令行会输出许多文本，需要获取的两个值在1-1.3下如图，记录idVendor和idProduct对应的编码


![]({{ image_dir }}/udevadm.jpg)

创建新的udev规则

```bash
sudo vim /etc/udev/rules.d/myusb0.rules
```

在新建的文件中输入:

```bash
SUBSYSTEMS=="usb",ATTRS{idVendor}=="1a86",
ATTRS{idProduct}=="7523",GROUP="users", MODE="0666"
```

其中的idVendor和idProduct用上图中对应的编码代替，接着重新加载udev规则

```bash
sudo udevadm control --reload
```

## 6\. 测试连接

连接好树莓派和飞控后，运行`connect.py`

```bash
cd test
python3 connect.py
```

这时候一般会有个问题，在脚本的`__init__.py`的2689行，把这一行改为

```python
class Parameter(collections.abc.MutuableMapping,HasObservers):
```





