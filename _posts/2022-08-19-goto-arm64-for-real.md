---
layout: article
title: 【树莓派学习笔记】给树莓派刷64位系统
permalink: /article/goto-arm64-for-real
key: goto-arm64-for-real
tags: 
  - 树莓派
  - bash
  - Linux
  - aarch64
  - rpi-imager
author: Yu Xiaoyuan
show_author_profile: true
---

[之前](/article/goto-arm64#切换)从32位迁移到64位的实践失败了。为了能升级，只能重刷系统。

<!--more-->

## rpi-imager

`rpi-imager`是[树莓派官方](https://www.raspberrypi.com/software/)推出的镜像烧写工具, 自带汉语界面, 下载烧写一条龙服务.

![imager-main](/assets/images/2022-08-19-goto-arm64-for-real/imager-main.png "imager主界面"){:.rounded}

选择好系统和烧录介质之后可以点击右下角的齿轮进行一些其他的配置.
{:.info}

![imager-main-selected](/assets/images/2022-08-19-goto-arm64-for-real/imager-main-2.png "点击右下角的齿轮进行额外配置"){:.rounded}

注意这里可以选择打开ssh服务.
{:.warning}

![imager-settings](/assets/images/2022-08-19-goto-arm64-for-real/imager-settings.png "高级设置界面"){:.rounded}

## 拓展阅读

树莓派操作系统: [Raspberry Pi OS](https://www.raspberrypi.com/software/)
