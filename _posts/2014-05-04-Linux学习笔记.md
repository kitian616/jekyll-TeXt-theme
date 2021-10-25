---
title: Linux学习笔记
date: 2014-05-04 19:51:16
tags: linux
---

对前一段时间学习Linux做一个总结，主要是整理笔记，写的可能比较乱，但是内容比较丰富，还是值得一看的。内容总共分为：用户账户、进程管理、计划任务、文件系统构成、磁盘管理、备份、Shell编程。内容都是比较基础，比较常用的，过一遍会加深你的印象。

## 1. 用户账户
Linux用户基本上分为下面三类：超级用户（root ， UID=0）、普通用户（UID=[500,60000]）、伪用户（UID=[1,499]）。

用户配置文件储存在`/etc/passwd`文件下，文件中每一行代表一个用户，每一列代表用户的基本信息，第一列代表用户的用户名，第二列代表用户的密码，密码使用*代替。因为密码是通过加密后存储在`/etc/shadow`文件下了，这个文件的权限只有root用户才能访问。

`pwunconv`命令可以将密码回写到`/etc/passwd`文件中，执行了这个密码后，再次进入`/etc/passwd`文件中，就会发现第二列的*被一串字符串所替代，这就是加密后的密码。`pwconv`命令正好与上面的命令意思相反，它将密码从passwd中写入到shadow中，这个命令一般是在我们创建用户后就会自动执行。

- 用户组文件：`/etc/group`；
- 用户组密码文件：`/etc/gshadow`；
- 用户配置文件：`/etc/login.defs`、`/etc/default/useradd`；
- 新用户信息文件：`/etc/skel`，在创建新用户后，会自动将此文件夹中的内容拷贝到新用户的HOME目录下，作为新用户的配置文件；

登录显示信息：`/etc/motd`成功后、`/etc/issue`登录时。

### 1.1 普通用户为什么可以改密码？
上面说过密码存放在`/etc/passwd`以及`/etc/shadow`中，而这两个文件对其他用户均没有写权限。
普通用户用`passwd`命令改密码，该命令的全路径是`/usr/bin/passwd`，你可以通过`ls -l /usr/bin/passwd`查看到该命令的权限，为`-rwsr-xr-x`，可以看到有个s权限位。  
这个s权限位的意思就是当其他用户在使用这个命令的时候，将暂时获得这个命令的所属用户的权限。换句话就是说当普通用户在使用`passwd`命令的时候，会暂时获取root权限，从而可以改变`/etc/passwd`里的密码值，达到改密码的效果。

### 1.2 特殊权限位
那么，像上面那样的特殊权限位有哪些呢？又如何设置呢？

- setUID：就是上面的s权限位，可以通过以下命令进行设置
	- `chmod u+s filename`
	- `chmod 4755 filename`
- setGID: 大概意思同setUID，就是执行行默认获取命令所属组的权限，同样，可以通过下面命令进行设置
	- `chmod g+s filename`
	- `chmod 2755 filename` 
- 粘着位：用`t`表示，你可以通过查看`/tmp`目录的权限就知道了，那么，它的作用是什么呢？

`/tmp`目录的权限代表什么意思？粘着位只是针对目录进行设置，大家知道`/tmp`目录的权限为`drwxrwxrwt`，意思就是什么人都具有读、写、执行的权限，那么，如果我建的文件或者目录被别人删了怎么办？所以，粘着位的作用就是：每个人在目录里都有读、写、执行的权限，但是只有自己可以删除自己创建的文件。

很神奇是不是？来看看怎么设置？两种方式，`chmod o+t dir`&`chmod 1777 dir`，dir代表目录。

### 1.3 添加用户
`useradd -D` 其实就是根据`/etc/default/useradd`默认配置来创建的  
![default](/image/linux/default.png)

### 1.4 用户组管理命令
- `usermod -G 组名 用户名` 将用户加入用户组
- `gpasswd -a 用户名 组名` 将用户加入用户组
- `groups`查看用户隶属那些用户组
- `newgrp` 切换用户组
- `grpck` 用户组配置文件检查
- `chgrp`修改文件所属用户组
- `vigr` 编辑`/etc/group`文件

### 1.5 用户管理命令
- `pwck` 检测`/etc/passwd`文件
- `vipw` 编辑`/etc/passwd`文件
- `id` 查看用户详细信息
- `finger` 查看用户详细信息，有些Linux发行版默认不带此命令
- `passwd -S` 查看用户密码状态
- `who` or `w`查看当前登录用户信息 

### 1.6 实例
- 添加用户组：`groupadd -g 888 webadmin`
- 删除用户组：`groupdel webadmin`
- 修改用户信息：`groupmod -n apache webadmin`(将webadmin组名修改为apache)
- 检测用户：`passwd -S username`
- 锁定用户：`passwd -l username`

### 1.7 sudo
使普通用户以root的方式执行命令。

- **配置文件**：`/etc/sudoers`；
- **格式**：用户名(组名) 主体地址=命令(绝对路径)；
- **实例**：sudoer文件中有编写规则说明，具体参照下图。

![sudoer](/image/linux/sudoers.png)

## 2. 进程管理
进程管理不得不介绍的四个命令，`ps`、`kill`、`nice/renice`、`top`。
### 2.1 PS
查看进程的命令，非常强大哦。它有三种表达方式，具体可以参照man文档的description部分。  
![ps_des](/image/linux/ps_des.png)  

- `a` 显示所有的用户
- `u` 显示用户名和启动时间
- `x` 显示没有控制终端进程
- `-e` 显示所有进程
- `-f` 显示完整格式

**常用命令**

- `ps aux` or `ps -ef`都是显示所有用户进程，只是不一样的表达方式
- `ps -uU username`查看系统中指定用户执行的进程

很有必要看一下ps命令的man说明文档，下面图片贴出文档中example部分内容。  
![ps_example](/image/linux/ps_example.png)

### 2.2 Kill
这个命令也很常用的，杀死进程.

- `kill PID`根据进程号，直接终止进程
- `kill -9 PID`强行关闭
- `kill -1 PID`重启进程
- `xkill`关闭图形程序
- `killall 进程名`结束该进程

### 2.3 nice/renice
改变进程的优先级，每一个进程都有一个优先级（也称nice值），其范围为[-20,19]，最高到最低，注意-20是最高的哦，默认情况下，进程的优先级都是0，优先级高的进程会比较频繁的被调用运行。

`nice`设定进程的优先级。格式：`nice -n command`，eg. `nice -n 19 dd if=/dev/cdrom of=~/mdk.iso`利用最低优先级创建一张cd的镜像，防止复制操作阻碍其他进程。

`renice`更改用户的优先级。格式：`renice -n pid` ， eg. `renice -5 777`提高777进程的优先级。

### 2.4 top
知道Windows下的任务管理器吧，这个命令就是命令行下的任务管理器。  
![top](/image/linux/top.png)

很强大的命令，介绍下简要用法。

- `d`:指定刷新间隔
- `c`:显示整个命令行
- `u`:查看指定用户进程
- `k`:终止执行进程
- `h`:帮助

## 3. 计划任务
计划任务最常用的两个命令就是`at`和`crontab`，`at`命令是一次性执行任务；`crontab`是周期性运行作业。
### 3.1 at
格式：`at [-f filename] time`，这里的time也就是时间分为两类：

- 绝对计时
	- `hh:mm MM/DD/YY`
- 相对计时
	- `now + n minutes`
	- `now + n hours`
	- `now + n days`

**实例**

- `at 5:30pm`
- `at now + 18minutes`
- `at 17:30 1/11/11`

接下来输入命令，输入完后ctrl+D保存任务。**注意**：命令一定要是绝对路径。

- `at -l`列举任务
- `at -d`删除任务

**配置文件**

- `/etc/at.allow`允许用户列表
- `/etc/at.deny`不允许用户列表

### 3.2 crontab
作用：用于生成cron进程所需要的crontab文件；  
格式：`crontab {-l|-r|-e}`

- `-l`显示当前的crontab
- `-r`删除
- `-e`编辑

编辑规则，将知道的具体时间填上，不知道的填*

```table
分钟 | 小时 | 天 | 月 | 星期 | 命令/脚本 |
 30 | 17 | \* | \* | 1-5 | /usr/bin/wall < x.txt |
45 | 17 | \* | \* | 1-5 | /sbin/shutdown -r now |
\*/2 | 12-14 | \* | 3-6,9-12 | 1-5 | 检测脚本 |
```

上面第一例，每个周一到周五的17:30进行广播操作；第二例，每周一到周五17:45进行重启操作；第三例，3-6月和9-12月，每周一到周五，每隔2分钟进行脚本检测。

## 4. 文件系统构成
- `/usr/bin & /bin` 可执行的命令
- `/usr/sbin & /sbin` root可执行的的命令
- `/proc` 虚拟文件系统，存放当前内容镜像
- `/dev` 存放设备文件
- `/lib` 存放系统程序运行所需的共享库
- `/lost+found` 存放一些系统出错的检查结果
- `/tmp` 临时文件
- `/etc` 系统配置文件
- `/var` 存放经常发生变动的文件，比如邮件、日志等
- `/usr` 存放所有的命令、库、手册页等
- `/mnt` 临时文件系统的安装点
- `/boot` 内核文件及自带程序文件保存位置
- `/usr/local` 安装第三方软件默认路劲

## 5. 磁盘管理
关于`du`和`df`之类的命令这里就不介绍了，这里主要介绍的是如何添加磁盘和分区、如何添加swap分区以及磁盘配额的内容。
### 5.1 添加磁盘或分区
总共分为4大步。

1. `fdisk`，划分分区。
	- `fdisk -l /dev/sdb`，查看磁盘分区情况
	- `fdisk /dev/sdb`，对磁盘进行操作，见下面基本操作
		- `m` 帮助
		- `p` 打印分区表
		- `n` 增加新的分区
		- `t` 改变分区文件系统ID
		- `L` 查看文件系统ID
		- `d` 删除分区
		- `w` 保存分区表
		- `q` 退出（不保存）
2. `mkfs`，创建文件系统。
	- `mkfs.ext3 /dev/sdb` 将分区格式化为ext3文件系统类型
3. `mount`，挂载分区。
	- `mount /dev/sdb /mnt` 将sdb分区挂载到mnt目录下
4. `df -h` 查看系统磁盘，可以看到mnt分区，注意：我这是在虚拟机中测试的
    ![fdisk_sdb](/image/linux/fdisk_sdb.png)  
5. 写入配置文件，编辑`/etc/fstab`。文件的内容信息格式如下；

```table
物理分区名 | 挂载点 | 文件系统 | 缺省设置 | 是否检测（1:检测；2:检测）| 检测顺序（0：是否检测；1：优先检测；2:期后检测）
LABEL=1 | / | ext3 | default | 1 | 2 
/dev/sda1 | / | ext3 | default | 0 | 0
/dev/sdb1 | /web | ext3 | default | 1 | 1	
```

- `e2label /dev/sdb1` 检测是否有卷标
- `e2label /dev/sdb1 name`添加卷标

### 5.2 添加Swap分区
两种方法扩大swap分区

- 新建磁盘，分swap分区
- 在已有磁盘上使用swapfile文件增大swap分区

第一种方法可以根据上面增加分区的步骤一样进行操作，这里主要记录一下使用swapfile文件增大swap分区的步骤。

1. `mkdir /var/swap` 新建swap目录
2. `chmod 700 /var/swap` 设置目录权限
3. `dd if=/dev/zero of=/var/swap/file.swp bs=1024 count=3000` 创建swp文件，大小为3MB
4. `mkswap /var/swap/file.swp` 使文件可用
5. `vim /etc/fstab` 写入配置文件
6. `free` 查看分区
7. `swapon /var/swap/file.swp` 启用swap分区
8. `free` 再次查看分区，如图显示，之前和之后的swap分区大小。

![swap](/image/linux/swap.png)  

### 5.3 磁盘配额（针对分区）
磁盘配额就是管理员可以为用户所能使用的磁盘空间进行配额限制，每一用户只能使用最大配额范围内的磁盘空间。可分为三类限制：

- 软限制（Soft limit）：定义用户可以占用的磁盘空间数。当用户超过该限制后会收到已超过配额的警告。
- 硬限制（Hard limit）：当用户试图将文件存放在其已经超过该限制目录时，报告文件系统错误。
- 宽限制（Grace period）：定义用户在软限制下可以使用其文件系统的期限。

**操作步骤**

1. 开启分区配额功能，`vim /etc/fstab`，编辑配置文件，在挂载属性上加上标志userquota或grpquota，然后重新挂载`mount -o remount /home`，或重新启动系统`sudo init 6`；
2. 建立配额数据库，`quotacheck -Cvuga`，会生成aquota.user、aquota.group两文件。
3. 启动配额功能，`quotaon 分区名称`；关闭配额功能，`quotaoff 分区名称`；
4. 编辑配额
	- `edquota username` 编辑用户配额
	- `edquota -g groupname` 编辑用户组配额
	- `edquota -t 设置宽期限` 设置宽期限
	- `edquota -p 模版用户 复制用户1 复制用户2`
	- `quota username` 查看用户的配额使用情况
	- `repquota -a` 管理员查看配额信息

## 6. 备份
备份几乎是Linux系统运维最频繁的工作了，不过大部分情况下，都是通过自动化脚本进行备份。
### 6.1 备份策略

- 完全备份
- 增量备份：通常是这种情况

### 6.2 备份分类

- 系统备份：`/etc` ,  `/boot` , `/usr/local` , `/var` , `/log`...
- 用户备份：`/home`

### 6.3 备份相关命令
其实主要就是通过`cp`和`tar`命令
 
 - `cp -Rpu` 复制文件
 	- `-p` 保持文件原本属性
 	- `-u` 增量备份
 	- `-R` 循环复制
 - `scp` 远程备份，类似cp命令
 - `tar -zcf /backup/sys.tar.gz /etc /boot` 备份/etc , /boot
 - `tar -ztf /backup/sys.tar.gz` 查看备份包中的文件（不解包）
 - `tar -zxf /backup/sys.tar.gz` 还原备份目录，其实就是解包
 - `tar -zxf /backup/sys.tar.gz -C ./backup` 解压到指定目录`backup`下
 - `tar -zxf /backup/sys.tar.gz etc/group` 恢复指定文件
 - `tar -rf /backup/sys.tar /etc/file1 /etc/file2` 追加文件到备份包中
 - `tar -uf /backup/sys.tar /etc/file` 将修改过的文件做备份

## 7. Shell脚本编程实例
下面的例子基本上都是上面的知识点，比较扩展一点的就是`awk`命令的用法，下面代码__仅作参考__，写的不好，请见谅；错误的地方烦请指出，不胜感激！

### 7.1 测试Apache服务是否启动，若没有启动，则启动Apache服务

```bash
#! /bin/sh
# 测试Apache服务是否启动，若没有启动，则启动Apache服务
web=`/usr/bin/pgrep httpd`
if [ "ABC$web" = "ABC" ] 
then
	/etc/init.d/httpd start
fi
```

### 7.2 判断某个文件属于什么类型的文件

```bash
#! /bin/sh
# 判断一个文件属于什么类型
f [ -d $1 ]
then
	echo "这是一个目录";
elif [ -f $1 ]
then
	echo "这是个普通文件"
elif [ -c $1 ]
then
	echo "这是字符特殊文件"
elif [ -b $1 ]
then
	echo "这是块特殊文件"
else
	echo "我也不知道这是啥玩意"
fi
```

### 7.3 踢出用户的脚本

```bash
#! /bin/sh
# 判断用户是否在线，存在的话踢出用户
username=$1
/usr/bin/w > whoIsOnline
userString=`/usr/bin/awk -v user=$username '{if($1 == user) print $1}' ./whoIsOnline`
if [ "ABC$userString" = "ABC" ]
then
	echo "$1 is not online"
else
	killId=`/usr/bin/ps aux | /usr/bin/grep $username | /usr/bin/eprep -v grep |/usr/bin/awk {print $2}`
	kill $killId
fi
```

### 7.4 显示用户的信息

```bash
#! /bin/sh
# 显示用户的相关信息
user=$1
if [ "ABC$user" = "ABC" ]
then
	echo "请在命令行后面输入要查询的用户名！"
else
	/usr/bin/grep ^$1: /etc/passwd | /usr/bin/awk -F ":" '{print "用户ID为"$3 ; print"用户组ID为"$4 ; print "你其实是"$5 ; print "你的主目录为"$6 ; print"你的默认Shell为"$7}'
fi
```

### 7.5 加减乘除的脚本
```bash
#! /bin/sh
# 加减乘除的小脚本，主要练习case语句
case $2 in
+)
	echo "$1 + $3 = `expr $1 + $3` " 
	;;
-)
	echo "$1 - $3 = `expr $1 - $3`"
	;;
\*)
	echo "$1 * $3 = `expr $1 \* $3`"
	;;
/)
	if [ $3 = 0 ]
	then
		echo "除数不能为0"
		exit
	else
		echo "$1 / $3 = `expr $1 / $3`"   
	fi
	;;
*)
	echo "您的输入不符合规范，请重新输入"
esac
```

### 7.6 打印出1到10之间的平方数
```bash
#! /bin/sh
# 打印1-10之间的平方数，主要练习while语句
#赋值的时候注意不要加空格
i=1
while [ $i -le 10 ]
do
	echo "$i 的平方为：`expr $i \* $i`"
	i=`expr $i + 1`
done
```



```bash
#! /bin/sh
# 打印1-10之间的平方数，主要练习for语句
for i in {1..10}
do
	echo "$i 的平方为：`expr $i \* $i`"
done
```

### 7.7 累加求和（使用shift命令）

```bash
#! /bin/sh
# 使用shift命令累加求和
sum=0
while [ $# -gt 0 ]
do
	sum=`expr $sum + $1`
	shift
done
echo "总和为：$sum"
```