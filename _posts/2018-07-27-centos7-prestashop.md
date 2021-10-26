---
title: CentOS 7 搭建开源商城 prestashop
date: 2018-07-21 14:15
tags: linux
---

隔一段时间，就得折腾一次，不然记的那些Linux命令就差不多忘光了。

### 1. 安装mysql
CentOS 7 有点麻烦，需要先添加[官方源](https://dev.mysql.com/downloads/repo/yum/)，然后通过yum安装。

![](/image/linux/centos7/mysql1.png)
![](/image/linux/centos7/mysql2.png)

```
wget -i -c https://dev.mysql.com/get/mysql80-community-release-el7-1.noarch.rpm
yum -y install mysql80-community-release-el7-1.noarch.rpm
yum -y install mysql-community-server
```

通过上述命令安装mysql，会自动覆盖掉CentOS 7 自带的mariadb数据库。

![](/image/linux/centos7/mysql3.png)

安装完成之后，比较搞笑的一步竟然是找密码，因为默认安装直接给设置了一个密码，需要找到它，然后修改初始密码，接下来才能操作。

```
grep "password" /var/log/mysqld.log
2018-07-18T04:38:24.043225Z 5 [Note] [MY-010454] [Server] A temporary password is generated for root@localhost: Gua<TuiB6ty:
```

修改密码的命令
```
ALTER USER 'root'@'localhost' IDENTIFIED BY 'NewPassWord!';
```

*注意：因为在服务器端，密码强度要高，大小写字母，还要有特殊字符，不然会报错。*

### 2. 下载prestashop
[官网链接](https://www.prestashop.com/en)，选择下图右边自定义模式下载源码。

![prestashop](/image/linux/centos7/prestashop.png)

下载完后，解压，应该是如下3个文件，这里是1.7.4.1 版本。

![files](/image/linux/centos7/files.png)

### 3. 配置Web服务器
Web服务器选择nginx，由于prestashop是php的开源平台，所以要安装php。安装命令如下

```
sudo yum install nginx php php-fpm    // 安装nginx，php，php-fpm（配合nginx）
```

启动php-fpm，打开nginx配置文件，添加如下配置

```
location ~ \.php$ {
        root           /var/www/html;
        fastcgi_pass   127.0.0.1:9000;
        fastcgi_index  index.php;
        fastcgi_param  SCRIPT_FILENAME  $document_root$fastcgi_script_name;
        include        fastcgi_params;
    }
```

在目录`/var/www/html`目录中添加测试文件`index.php`，编辑如下信息：

```php
// index.php
<?php
phpinfo()
?>
```

启动nginx，`nginx`，或重启nginx服务，`nginx -s reload`，在浏览器端访问：http://your_ip/index.php，显示如下：

![php](/image/linux/centos7/php.png)

### 4. 升级php版本

这里学了几个关于yum的命令。

```
yum list installed   // 列出安装的软件
yum list installed | grep php  // 找出PHP相关的软件包
yum provides php  // 查找源中php软件包
yum remove php-common  // 卸载php-common软件包，这里注意要卸载干净 
yum install php70w-common  // 安装php7.0版本
```

升级就是卸载旧版本，安装新版本即可。这里建议直接安装7.0版本，list结果如下图所示。

![php70w](/image/linux/centos7/php70w.png)


### 5. 安装prestashop
第一步，把该启动的服务全部启动，这里以CentOS 7 为例。如下命令：

```
systemctl start mysqld.serivce   // 启动mysql服务
systemctl start php-fpm.service  // 启动php-fpm服务
nginx  // 启动nginx服务

systemctl restart mysqld.serivce   // 重启mysql服务
systemctl restart php-fpm.service  // 重启php-fpm服务
nginx -s reload // 重启nginx服务
```

第二步，创建prestashop数据库

第三步，拷贝prestashop项目，将index.php和prestashop.zip拷贝到web目录中`/var/www/html/`

第四步，浏览器端输入`http://your_ip/index.php`，自动开始安装，如下界面为解压缩。

![解压文件中](/image/linux/centos7/unzip.png)

第五步，随即启动安装，如下图

![系统兼容检查](/image/linux/centos7/check.png)

![店铺信息填写](/image/linux/centos7/info.png)

![数据库配置](/image/linux/centos7/database.png)

![系统配置](/image/linux/centos7/module.png)

第六步，删除服务器端安装目录，`/var/www/html/install/`

第七步，如果一切顺利，那么就能正常进入首页，以及后台页面。

![前台](/image/linux/centos7/index.png)

![后台](/image/linux/centos7/backend.png)

### 6. 各种BUG情况
Linux的世界里，从来没有一帆风顺，期间需要根据报错信息，不断的更正，才能一步一步走下去。这里记录一个让我耗时最长的问题。

![image.png](/image/linux/centos7/bug1.png)

如上图，店铺安装到12%的时候，就过不去了，创建数据表失败，检查数据库的操作没有问题，而且奇怪的是，查看数据库，所有的表都已经建好了。那为什么会显示504 Gateway Time-out呢？于是，把问题定位在Web服务器的配置上，修改相关配置，延长响应时间，加大缓存等等。但每次重新来过都是这样。（清空`/var/www/html`里的文件，重新把安装包拷贝进去就可以重来）

后来，检查服务器的时候，发现，每次到这一步，mysql就极其卡顿，偶尔还会down掉，于是隐约的感觉到，会不会是服务器资源不够了。于是，再来一遍的时候，我就通过`free`命令，观察内存使用情况，发现果然是内存不足。（我使用的是腾讯云的最基础版的云服务器，1G+1Mbps）

内存不够，创建交换分区，暂时把硬盘充当内存使用，避免安装过程mysql直接down掉。

```
dd if=/dev/zero of=/swapfile bs=4096 count=1024K    // 创建文件
mkswap /swapfile  // 设置交换文件
swapon /swapfile  // 启用
```

这样就可以了，`free`看一下，就会发现swap分区的使用情况。可怜的1G，如果没有swap，服务器就处于崩溃的边缘。

![swap](/image/linux/centos7/bug1-swap.png)

参考文章：    
- [CentOS7安装MySQL - 一张对二 - 博客园](https://www.cnblogs.com/bigbrotherer/p/7241845.html)
- [centos7升级自带的php5.4版本到php5.6 - 标配的小号 - 博客园](https://www.cnblogs.com/biaopei/p/7730464.html)
- [centos7开启交换内存](http://zixuephp.net/article-335.html)
- [CentOS 7 yum nginx MySQL PHP 简易环境搭建 - Evai - 博客园](https://www.cnblogs.com/evai/p/5991525.html)


![image.png](/image/linux/centos7/bug2.png)

可能你非常不幸，到了最后一步，还是失败了。不用担心，到这一步失败，其实没什么大碍了，它只是在安装模块。不用管它，退出后，删除服务器端`install/`目录，然后浏览器直接进入后台`http://your_ip/admin*****`，后台是可以手动安装这些模块的。

![install modules](/image/linux/centos7/bug2-install-modules.png)

好了，先到这里，有问题再补充。