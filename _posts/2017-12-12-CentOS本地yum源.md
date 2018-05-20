---
layout: post
title: CetOS增加本地yum源，修改默认yum源为阿里云
tags: CentOS
key: 20171212
---
# 配置本地yum源
在没有网络的环境下需要使用镜像中的yum源,以下介绍如何配置本地yum源
## 挂载iso镜像
1. 新建文件夹用于挂载目录
```
    mkdir /mnt/cdrom
```
2. 挂载CentOS镜像
```
  mount /dev/cdrom /mnt/cdrom
```

<!--more-->

## 修改yum源
1. 备份repo
将/etc/yum.repos.d下默认yum源CentOS-Base.repo重命名备份，这样默认yum源就失效了
```
  cd /etc/yum.repos.d
  mv  CentOS-Base.repo     CentOS-Base.repo.bak
```
2. 修改CentOS-Media.repo
原文件
```
  [c7-media]
  name=CentOS-$releasever - Media
  baseurl=file:///media/CentOS/
          file:///media/cdrom/
          file:///media/cdrecorder/
  gpgcheck=1
  enabled=0
  gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7
```
修改后
```
  [c7-media]
  name=CentOS-$releasever - Media
  baseurl=file:///mnt/cdrom/  #挂载iso的目录
          file:///media/cdrom/
          file:///media/cdrecorder/
  gpgcheck=1
  enabled=1    #开启
  gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7
```
3. clean一下yum缓存
```
  yum clean
```
4. 检查yum源是否修改成功
```
  yum list
```

# 修改默认yum源地址为阿里云
## 备份系统自带yum源
备份系统自带yum源配置文件/etc/yum.repos.d/CentOS-Base.repo

```
  mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup
```

## 下载阿里云的yum源
下载到/etc/yum.repos.d/下，并命名为CentOS-Base.repo

```
  wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
```

## 运行yum makecache生成缓存

```
  yum makecache
```

## 更新yum源
会看到mirrors.aliyun.com信息

```
  yum -y update

  已加载插件：fastestmirror, refresh-packagekit, security
  设置更新进程Loading mirror speeds from cached hostfile
  * base: mirrors.aliyun.com
  * extras: mirrors.aliyun.com
  * updates: mirrors.aliyun.com
```
