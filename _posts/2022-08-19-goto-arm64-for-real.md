---
layout: article
title: 【树莓派学习笔记】给树莓派刷64位系统
permalink: /article/:title.html
key: goto-arm64-for-real
tags: 
  - 树莓派
  - bash
  - Linux
  - aarch64
  - rpi-imager
author: Yu Xiaoyuan
show_author_profile: true
license: WTFPL
---

[之前]({% post_url 2022-08-17-goto-arm64 %}#切换)从32位迁移到64位的实践失败了。为了能升级，只能重刷系统。

<!--more-->

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- end private variable of Liquid -->

## rpi-imager

`rpi-imager`是[树莓派官方](https://www.raspberrypi.com/software/)推出的镜像烧写工具, 自带汉语界面, 下载烧写一条龙服务.

![imager-main]({{ image_dir }}/imager-main.png "imager主界面"){:.rounded}

选择好系统和烧录介质之后可以点击右下角的齿轮进行一些其他的配置.
{:.info}

![imager-main-selected]({{ image_dir }}/imager-main-2.png "点击右下角的齿轮进行额外配置"){:.rounded}

注意这里可以选择打开ssh服务.
{:.warning}

![imager-settings]({{ image_dir }}/imager-settings.png "高级设置界面"){:.rounded}

都设置完成之后开始烧录.

![imager-downloading]({{ image_dir }}/imager-downloading.png "烧录中"){:.rounded}

一切正常的话烧录完成之后就可以将sd卡插到树莓派上, 然后启动.

## 基础配置

笔者的树莓派接入了局域网路由器, 想要获得IP可以使用[Advanced IP Scanner](https://www.advanced-ip-scanner.com/)来扫描子网.  
获得IP后就可以ssh接入.

首先进行系统架构测试.

```bash
$ uname -a
Linux raspberrypi 5.15.32-v8+ #1538 SMP PREEMPT Thu Mar 31 19:40:39 BST 2022 aarch64 GNU/Linux
$ getconf LONG_BIT
64
```

可以看到架构和长整型数的位宽都正确.

### 替换软件源

常规步骤更换软件源不再赘述, 参考[清华大学开源软件镜像站](https://mirrors.tuna.tsinghua.edu.cn/)的[Raspbian 镜像使用帮助](https://mirrors.tuna.tsinghua.edu.cn/)

### 安装必要组件

```bash
sudo apt install build-essential cmake tmux vim exfat-fuse
```

- `build-essential` 基础构建工具
- `cmake`
- `tmux` 会话管理工具
- `vim` 文本编辑器
- `exfat-fuse` exfat文件系统支持

### conda

树莓派上只能安装[Miniforge](https://github.com/conda-forge/miniforge)。

```bash
wget https://github.com/conda-forge/miniforge/releases/download/4.13.0-1/Miniforge3-4.13.0-1-Linux-aarch64.sh
sh ./Miniforge3-4.13.0-1-Linux-aarch64.sh
```

## 拓展阅读

树莓派操作系统: [Raspberry Pi OS](https://www.raspberrypi.com/software/)

清华大学开源软件镜像站: [Raspbian 镜像使用帮助](https://mirrors.tuna.tsinghua.edu.cn/)
