---
title: LNMP环境搭建（CentOS+meiupic）
tags: [linux,nginx,mysql]
date: 2015-2-19
---

### 背景
CentOS上搭建meiupic图床（一个PHP写的开源相册），之前也搭建过PHP的LNMP环境，但是那是很长时间的事了，并且当时使用的是Ubuntu Server版本服务器。本以为很快就搭建OK，却还是折腾了一晚上，真是惭愧...

![](/image/linux/Linux_zt01.jpg)

### 一、CentOS配置网络

本是一件多简单的事，不就是在配置文件`/etc/sysconfig/network-scripts/ifcfg-eth0`里写入下面这几行配置嘛。

	DEVICE=eth0
	BOOTPROTO=static
	ONBOOT=yes
	
	ipaddr=192.168.1.123
	netmask=255.255.255.0
	gateway=192.168.1.1

可是还真是奇了怪了，一重启，IP就自动变了，随机分配了。明明配置的是静态IP啊。耗了我老长时间找原因了，最后照着书上一个一个校对，最后发现把`ipaddr`、`netmask`、`gateway`都改成大写的就行了。。（PS. 记得在Ubuntu上没有区分啊，难道我记错了~郁闷）

### 二、Nginx
#### 1、安装
教程很多，贴一条备用，没准哪天又忘了怎么编译安装了。以前在Ubuntu上都是直接`sudo apt-get install nginx`，可是在CentOS里貌似默认没有，学会编译安装nginx还有必要的。而且编译安装的版本使用起来有一些差异。

[Nginx安装](http://www.nginx.cn/install)

注意点：

- pcre不要选择最新版pcre2，选择pcre2会报错，有能力解决者可以自行选择；
- 源码全部下载在`/usr/local/src/`目录下，便于管理。

#### 2、使用

重新启动`service nginx restart`？No，No，No，别被自动安装的软件惯坏了，nginx应该这么用。

- `/usr/local/nginx/nginx`  启动主程序，安装目录下运行nginx，我的版本是1.7，其他版本nginx执行文件也许不在nginx目录下
- `/usr/local/nginx/nginx -s reload` 重新加载
- `/usr/local/nginx/nginx -s stop` 停止

当然不想输入那么长的路径也行，创建一个快捷方式，如果还是想使用service命令也行，在/etc/init.d目录下创建快捷方式。命令如下：

`cp -s /usr/local/nginx/nginx /etc/init.d/`

### 三、MySQL设置root密码

安装MySQL的方法倒是跟之前没两样，偷懒的方法就这样`yum install -y mysql*`，OK，一股脑全装上了，装完设置root密码方法如下：

方法1： 用SET PASSWORD命令

	mysql -u root
	mysql> SET PASSWORD FOR 'root'@'localhost' = PASSWORD('newpass');

方法2：用mysqladmin

```
mysqladmin -u root password "newpass"，
如果root已经设置过密码，采用如下方法：
mysqladmin -u root password oldpass "newpass"
```

方法3： 用UPDATE直接编辑user表

	mysql -u root
	mysql> use mysql;
	mysql> UPDATE user SET Password = PASSWORD('newpass') WHERE user = 'root';
	mysql> FLUSH PRIVILEGES;

在丢失root密码的时候，可以这样

	mysqld_safe --skip-grant-tables&
	mysql -u root mysql
	mysql> UPDATE user SET password=PASSWORD("new password") WHERE user='root';
	mysql> FLUSH PRIVILEGES;


瞅一眼密码啥样...     
![](/image/linux/Linux_zt02.jpg)

### 四、php+nginx配置

这次花费最长的时间就是这块了，之前基本没怎么接触过PHP开发，php的开发环境更是半知不解。其实这次搭建完也是半知不解。

想要nginx解析php文件，那么需要安装php-fpm，它是个啥？

> FastCGI Process Manager：FastCGI进程管理器，fpm能根据访问的压力动态的唤起cgi进程和销毁以到达动态的调整cgi数量，这样可以有效的使用内存。

跟着`yum install php*`命令，全安装上了。

接下来就是配置nginx了，使其能够解析并转发meiupic开源相册，我将相册源代码解压到了`/usr/www/`目录下了，然后就是配置nginx了，打开nginx配置文件，`vim /usr/local/nginx/nginx.conf`，找到http下的server区域，改成如下：

	...此处省略
	http {
		....
		server{
			listen	8080;
			server_name	192.168.1.123;
			location / {
				root	/usr/www;
				index	index.php;
			}
			location ~ \.php$ {
				root	/usr/www;
				fastcgi_pass	127.0.0.1:9000;
				fastcgi_index	index.php;
				fastcgi_param	SCRIPT_FILENAME	/scripts$fastcgi_script_name;
				include	fastcgi_params;		
			}
		}
	}
	...省略若干


listen端口，我将默认的80改成了8080，server_name改成本机的IP，root改成php开源项目的源代码目录，然后最重要的那段php的配置当然不是我写的，只是将默认对PHP的配置注释给去掉了，改一下root就完事了。

好了，迫不及待的想试一试了。依次检查各种服务是否正常，nginx、mysql、php-fpm....启动了的重启一下，没有启动的启动一下。

	service mysqld restart  
	service php-fpm restart  
	/usr/local/nginx/nginx -s reload  

OK ~浏览器运行`http://192.168.1.123:8080`，第一次嘛，必然是没有反应的。出现问题并不可怕，主要是要知道怎么找原因。

1. 首先想到的就是网络是否通，主机`ping 192.168.1.123`，OK！
2. 然后测试页面是否从服务器返回了，很显然在主机上测试是没有返回页面，而不是返回404。那么测试一下在虚拟机中是否返回页面了，没有浏览器怎么玩？没事，`curl 192.168.1.123:8080`，抓取页面代码。返回了。

好了，原因找到了，网络是通的，而页面无法访问，可是本地可以访问，这么一讲，就知道是防火墙的问题了。

### 五、设置防火墙

防火墙的配置是Linux运维的重点内容，CentOS默认只开通了22端口的访问配置。这里我们需要开启端口8080，亦或是关闭了它（当然不建议这么做）。

教程很多，或是直接看`man iptables`，你会学到很多。关于防火墙的配置文件是`/etc/sysconfig/iptables`，学习就靠自己了。

关闭的方法：`service iptables stop`。

OK，设置好防火墙后，又是一系列重启服务操作，然后浏览器访问，这次不报错了，可是`File Not Find`，what's the fuck!

### 六、fastcgi_params

最后找到就是fastcgi_params配置的问题，中间寻找答案的艰辛就不多说了，这里记下，给自己提个醒，同时给新人指路。我也是从前人那里找到的答案。

[nginx调用php-fpm出错解决方法和nginx配置详解](http://www.jb51.net/article/47953.htm)

问题就在上面nginx配置文件中的的这两句：

```
fastcgi_param	SCRIPT_FILENAME	/scripts$fastcgi_script_name;
include	fastcgi_params;		
```

解决方法有两种（其实是一个意思）：

1. 把第一句改成如下：
	`fastcgi_param  SCRIPT_FILENAME  $document_root$fastcgi_script_name;`
2. 或者把第二句改成如下：    
	`include fastcgi.conf`
	
为什么说一样，可以自行查看一下，nginx安装目录下，`fastcgi_param`以及`fastcgi.conf`这两个文件的内容差异。

` vim -O /usr/local/nginx/fastcgi_params /usr/local/nginx/fastcgi.conf`

![](/image/linux/Linux_zt03.jpg)

解决完这个问题后，又是一堆服务的重启，然后浏览器打开`http://192.168.1.123:8080`，OK，运行安装程序了。

### 七、安装meiupic

![](/image/linux/Linux_zt04.jpg)   

目录权限问题，到`/usr/www`目录下，设置一下，各个文件目录的访问权限。如果嫌麻烦，直接`chmod 777 /usr/www/*`，到这里基本就完成了。