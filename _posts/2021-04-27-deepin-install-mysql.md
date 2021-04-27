---
title: "Deepin 安装 MySQL"
subtitle: '装个数据库就不要再折腾了'
tags:
  - MySQL
---

Update:简单无脑的 MySQL 安装笔记

---

## Deepin 安装 MySQL
### 1. 添加软件源
&emsp;&emsp; Deepin 软件源里并没有包括 MySQL，需要手动添加，官方文档里也给出了[详细说明](https://dev.mysql.com/doc/mysql-apt-repo-quick-guide/en/)
-  去[下载页面](https://dev.mysql.com/downloads/repo/apt/)下载配置软件的安装包，选择合适的版本
-  安装上一步的安装包，假设版本为 `0.8.17-1`，则命令为 `sudo dpkg -i mysql-apt-config_0.8.17-1_all.deb`
-  随后会弹出GUI弹框，根据自己的系统选择合适的 MySQL 版本
-  更新软件源 `sudo apt-get update`

### 2. 安装
-  执行那个命令 `sudo apt-get install mysql-server` 安装
-  安装过程中会要求输入管理员root密码，需牢记
-  安装过程执行完以后，输入 `mysql -h 127.0.0.1 -uroot -p`，然后输入密码验证安装是否完成
-  开始愉快的使用 MySQL

### 3.TODO DOcker 安装

