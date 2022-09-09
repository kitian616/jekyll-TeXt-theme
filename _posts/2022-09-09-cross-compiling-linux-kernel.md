---
layout: article
title: 【树莓派学习笔记】编译Linux内核
permalink: /article/cross-compiling-linux-kernel.html
key: cross-compiling-linux-kernel
tags: 
  - 树莓派
  - bash
  - Linux
  - kernel
  - aarch64
  - cross compiling
author: Yu Xiaoyuan
show_author_profile: true
license: WTFPL
---

今天尝试给树莓派交叉编译Linux内核，大部分人没有这方面的需要，所以只是折腾。

<!--more-->

## 准备工作

> 工欲善其事，必先利其器

需要准备的硬件设备有

- 一台安装了Linux系统的电脑
- 树莓派
- sd卡

笔者在电脑上交叉编译Linux内核，首先安装一些必要组件。

```bash
sudo apt install git bc bison flex libssl-dev make libc6-dev libncurses5-dev
```

安装交叉编译器。

```bash
sudo apt install crossbuild-essential-arm64
```

克隆内核源码仓库。

```bash
git clone --depth=1 https://github.com/raspberrypi/linux
```

这里可以他嗯国`--branch`选项来明确需要拷贝的分支，笔者克隆时默认的分支是`rpi-5.15.y`。

## 交叉编译

笔者的树莓派4B之前安装了64位内核，这里按照64位的要求首先生成编译配置文件

```bash
cd linux
KERNEL=kernel8
make ARCH=arm64 CROSS_COMPILE=aarch64-linux-gnu- bcm2711_defconfig
```

完成之后配置会保存到`.config`文件内。然后开始编译。

```bash
make ARCH=arm64 CROSS_COMPILE=aarch64-linux-gnu- Image modules dtbs -j`nproc`
```

最后的`-j`选项是多线程编译。

## 安装

在正式安装之前查看一下树莓派当前的内核版本。

```bash
$ uname -r
5.15.56-v8+
```

更新内核之前务必备份你的sd卡。
{:.warning}

然后关机拔下sd卡，把sd卡插到刚编译内核的电脑上，挂载。笔者的sd卡在电脑上的设备是`/dev/sda`，通过如下指令查看挂载情况。

```bash
$ lsblk /dev/sda
NAME   MAJ:MIN RM  SIZE RO TYPE MOUNTPOINT
sda      8:0    1 59.5G  0 disk 
├─sda1   8:1    1  256M  0 part /media/dennis/boot
└─sda2   8:2    1 59.2G  0 part /media/dennis/rootfs
```

注意到系统已经帮我们自动挂载了，查看以下分区情况。

```bash
$ sudo fdisk /dev/sda -l
Disk /dev/sda: 59.49 GiB, 63864569856 bytes, 124735488 sectors
Disk model: Storage Device  
Units: sectors of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
Disklabel type: dos
Disk identifier: 0x44cc6dad

Device     Boot  Start       End   Sectors  Size Id Type
/dev/sda1         8192    532479    524288  256M  c W95 FAT32 (LBA)
/dev/sda2       532480 124735487 124203008 59.2G 83 Linux
```

这里根据分区的类型可以知道，`sda1`是`boot`分区，而`sda2`是`root`分区。

根据树莓派官方教程，推荐将设备挂载到当前目录。
使用如下指令创建文件夹并挂载。

```bash
mkdir mnt
mkdir mnt/fat32
mkdir mnt/ext4
sudo mount /dev/sda1 mnt/fat32
sudo mount /dev/sda2 mnt/ext4
```

更新内核之前务必备份你的sd卡。
{:.warning}

开始安装。

```bash
sudo env PATH=$PATH make ARCH=arm64 CROSS_COMPILE=aarch64-linux-gnu- INSTALL_MOD_PATH=mnt/ext4 modules_install
```

最后拷贝内核镜像文件。

```bash
sudo cp mnt/fat32/$KERNEL.img mnt/fat32/$KERNEL-backup.img
sudo cp arch/arm64/boot/Image mnt/fat32/$KERNEL.img
sudo cp arch/arm64/boot/dts/broadcom/*.dtb mnt/fat32/
sudo cp arch/arm64/boot/dts/overlays/*.dtb* mnt/fat32/overlays/
sudo cp arch/arm64/boot/dts/overlays/README mnt/fat32/overlays/
sudo umount mnt/fat32
sudo umount mnt/ext4
```

至此就完成了内核的编译和安装，重新插入sd卡，启动树莓派。看一下内核版本。

```bash
$ uname -r
5.15.65-v8+
```

哈哈哈没变化，白折腾了。

## 拓展阅读

[The Linux kernel - Raspberry Pi](https://www.raspberrypi.com/documentation/computers/linux_kernel.html)
