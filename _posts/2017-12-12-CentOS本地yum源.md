---
layout: post
title: CetOS增加本地yum源
tags: CentOS
key: 20171212
---
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
***
## 修改yum源
1. 备份repo
将/etc/yum.repos.d下CentOS-Base.repo备份
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
