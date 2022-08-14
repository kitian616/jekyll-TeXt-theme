---
layout: article
title: 【杰森指南】Jetson Nano使用说明
permalink: /article/new-jetson-nano.html
key: new-jetson-nano
tags: 
  - Jetson Nano
  - Nvidia
  - bash
  - OpenCV
  - Linux
  - pytorch
  - conda
author: Yu Xiaoyuan
show_author_profile: true

---

简要的Jetson Nano使用说明，包括安装环境等。

<!--more-->

## 系统烧录和SSD启动

前往[英伟达开发者网站](https://developer.nvidia.com/embedded/downloads)下载系统镜像。  
下载完成后使用[balenaEtcher](https://www.balena.io/etcher/)烧录到sd卡或者ssd。

注意，Jetson Nano B01虽然有m.2接口但该接口并不支持硬盘，ssd只能从usb启动。  
> 这一段参考[知乎文章-Nvidia Jetson Nano USB SSD Boot 配置](https://zhuanlan.zhihu.com/p/346736716)  

只需要将写好系统的ssd在一台linux机器上挂载APP分区，修改文件`/boot/extlinux/extlinux.conf`  

```conf
LABEL primary
      MENU LABEL primary kernel
      LINUX /boot/Image
      INITRD /boot/initrd
      APPEND ${cbootargs} quiet root=/dev/mmcblk0p1 rw rootwait rootfstype=ext4 console=ttyS0,115200n8 console=tty0 fbcon=map:0 net.ifnames=0
```

将`/dev/mmcblk0p1`修改为`/dev/sda1`即可

## conda

常用linux写代码的比较熟悉[Anaconda](https://www.anaconda.com/products/individual)。  
~~Anaconda官方之前只提供了x86的版本，最近刚更新了ARM64平台的支持，但本人没有尝试过，这里不推荐也不反对~~

~~笔者使用的是[Archiconda](https://github.com/Archiconda/build-tools)~~  
~~直接下载脚本安装~~  

[Archiconda](https://github.com/Archiconda/build-tools)已经停止维护，转用[Miniforge](https://github.com/conda-forge/miniforge)。  
在[清华大学Tuna镜像站](https://mirrors.tuna.tsinghua.edu.cn/github-release/conda-forge/miniforge/LatestRelease/)可以找到最新的Miniforge发行版。

## pytorch和torchvision

英伟达很良心得为大家准备了官方编译的[PyTorch](https://forums.developer.nvidia.com/t/pytorch-for-jetson-version-1-9-0-now-available/72048)，
也提供了官方的安装说明，直接去官网查看。

torchvision安装参考[Qengineering的教程](https://qengineering.eu/install-pytorch-on-jetson-nano.html)

验证安装

```python
import torch
print(torch.__version__)
print(torch.cuda.is_available())
import torchvision
print(torchvision.__version__)
```

可能会报错

```python
>>> import torch
Illegal instruction (core dumped)
```

这是numpy 1.19.5 的[issue](https://github.com/numpy/numpy/issues/18131)，使用指令`pip install numpy==1.19.4`降级可以解决

## libtorch

上一步安装的torch包含了C++的库文件即libtorch。  
使用指令`python -c 'import torch;print(torch.utils.cmake_prefix_path)'`可以查看目录。  
`要在安装了torch的conda环境中执行这个指令👆`{:.info}  
然后将输出的结果设置为CMake脚本的参数`CMAKE_PREFIX_PATH`。  

这里给出一个[示例工程](https://github.com/yuxiaoyuan0406/JetsonInstruction/tree/main/example/libtorch)作为参考。  

工程文件结构

```bash
$ tree
.
├── CMakeLists.txt
├── include
│   └── network.h
├── README.md
└── src
    └── main.cpp
```

CMakeLists.txt

```cmake
cmake_minimum_required(VERSION 3.2)

project(torch_example)

set(CMAKE_PREFIX_PATH /your/libtorch/dir/here)

find_package(PythonInterp REQUIRED)
find_package(Torch REQUIRED)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} ${TORCH_CXX_FLAGS}")

add_executable(${PROJECT_NAME} src/main.cpp include/network.h)
include_directories(./include)

target_link_libraries(${PROJECT_NAME} ${TORCH_LIBRARIES})

set_property(TARGET ${PROJECT_NAME} PROPERTY CXX_STANDARD 14)
```

include/network.h

```cpp
#pragma once

#include <iostream>
#include <torch/torch.h>

// 创建Net类的实现
struct NetImpl: torch::nn::Module
{
    NetImpl(int fc1_dims, int fc2_dims):
    fc1(fc1_dims, fc1_dims), fc2(fc1_dims, fc2_dims), out(fc2_dims, 1)
    {
        register_module("fc1", fc1);
        register_module("fc2", fc2);
        register_module("out", out);
    }

    torch::Tensor forward(torch::Tensor x){
        x = torch::relu(fc1(x));
        x = torch::relu(fc2(x));
        return out(x);
    }

    torch::nn::Linear fc1, fc2, out;
};

// 调用宏模板创建一个类
TORCH_MODULE(Net);
```

src/main.cpp

```cpp
#include <torch/torch.h>
#include <iostream>
#include "network.h"

using namespace torch;

int main()
{
    Net network(50, 10);
    auto x = torch::randn({2, 50});
    
    // torch::Device device(torch::kCPU);
    // if (torch::cuda::is_available())
    // {
    //     std::cout << "CUDA is available" << std::endl;
    //     device = torch::kCUDA;
    // }
    
    // x.to(device);
    // network->to(device);
    // network->out->to(device);

    std::cout << network << std::endl;
    std::cout << x << std::endl;

    auto output = network->forward(x);
    std::cout << output << std::endl;

    return 0;
}
```

## OpenCV

OpenCV如果只需要在python上使用可以用conda安装，如果要用C++而且需要最新版本，只能从源码编译。  

直接参考Qengineering给出的[OpenCV教程](https://github.com/Qengineering/Install-OpenCV-Jetson-Nano)。

这里给出一个libtorch+OpenCV的[示例工程](https://github.com/yuxiaoyuan0406/JetsonInstruction/tree/main/example/torchcv)作为参考。

## 拓展阅读

[原repo](https://github.com/yuxiaoyuan0406/JetsonInstruction)

[更多配置问题](https://github.com/yuxiaoyuan0406/JetsonInstruction/issues)
