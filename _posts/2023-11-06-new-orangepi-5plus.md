---
layout: article
title: 【树莓派学习笔记】新入手的香橙派5 Plus
permalink: /article/:title.html
key: new-orangepi-5plus
tags: 
  - 树莓派学习笔记
  - 香橙派
  - bash
  - Linux
author: Yu Xiaoyuan
show_author_profile: true
license: WTFPL
---

<!-- abstract begin -->
新办公室没有原来的服务器，但梯子和代码托管还是要有。
本人的树莓派现在作为个人使用放在宿舍了，办公室这边新买了个香橙派做服务器。
需要运行的服务包括局域网代理服务器、代码托管服务器、本地部署ChatGPT前端。
<!-- abstract end -->

<!--more-->

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- {%- increment equation-h2-1 -%} -->
<!-- end private variable of Liquid -->

## 需求分析

根据需要运行的服务需求，在挑选开发板时选择了香橙派5 Plus，性能优于树莓派。  
在官方淘宝店同时购入了配套的合金外壳和风扇，**到货之后发现风扇并不能和合金外壳同时使用**。  
由于要运行代码托管服务，所以存储要大。  
香橙派5P支持PCIe3.0x4协议的NVMe m.2固态硬盘，这里选用希捷酷鱼Q5作为主要硬盘。  
考虑到偶尔的无线网络接入需求，购置了官方的Wi-Fi模块。  

运行服务不需要图形界面，因此在[官方网站](http://www.orangepi.cn/html/hardWare/computerAndMicrocontrollers/service-and-support/Orange-Pi-5-plus.html)下载了最新的Debian服务器镜像。  

## 系统安装

根据官方手册描述，安装系统到SSD需要烧录引导启动到SPI Flash，而这一步需要在香橙派的操作系统上完成。  
因此首先需要在一张TF卡上烧录一个可用的系统。  

### 烧录到TF卡

用系统烧录工具（[balenaEtcher](https://etcher.balena.io/)或者[Win32DiskImager](https://win32diskimager.org/)）将下载好的镜像写入到TF卡。  
之后在香橙派上启动。

Server版系统没有图形界面，在连接了显示器之后会看到一个文本界面。  
默认用户名orangepi，密码与用户名相同。

### 基础测试

系统运行起来之后测试一下Wi-Fi模块是否正常运行。  
这一步骤参考官方手册即可。

### 烧录到SSD

烧录之前首先将镜像拷贝到香橙派上。

官方手册的方法是通过网络（SFTP）将文件发送到香橙派。  
这里笔者使用U盘拷贝。

首先将U盘格式化为exFAT文件系统，拷贝刚下载的系统镜像到U盘上。  
将U盘插到香橙派上，准备挂载。

首先在`home`下创建挂载点，比如笔者使用的`orangepi`用户，使用如下指令创建挂载点目录。

```bash
mkdir ~/disk
```

首先查看U盘是否正常识别。

```bash
sudo fsidk -l
```

应该能正常看到U盘被识别为`sda`。

使用`mount`指令挂载U盘到挂载点。

```bash
sudo mount -o uid=orangepi /dev/sda1 /home/orangepi/disk
```

然后拷贝镜像文件到香橙派本地存储。

```bash
cp image_file_name.img ~/
```

之后弹出U盘。

```bash
sudo umount disk
```

然后就可以拔掉U盘了。

### 基础设置

安装好系统之后配置一下Wi-Fi，自动连接并且设置为静态IP。  
这一步骤参考官方手册。

这些都搞定之后就可以把整个香橙派和合金外壳安装起来了。  
上电，启动，ssh接入，添加用户这些不赘述。

## 代理服务器

笔者在Linux上习惯使用的软件配置都托管到了笔者GitHub，由于特殊原因访问这些内容需要代理。

在其他电脑上下载好clash并拷贝到香橙派。

下载时注意CPU架构和操作系统。
{:.warning}

在之前的文章中详细介绍过如何[配置Clash]({% post_url 2022-08-18-local-proxy-server %})和如何[配置局域网代理]({% post_url 2023-02-10-proxy-on-linux%})，这里不赘述。

记得配置好用户级别的代理和Git代理。
{:.info}

## zsh

笔者习惯使用`zsh`，因此这里安装一下`zsh`的插件。  
安装`oh-my-zsh`的过程不赘述，另外安装的插件包括自动补全和语法高亮等。

```bash
git clone https://github.com/zsh-users/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting
git clone https://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions
git clone https://github.com/zsh-users/zsh-completions ${ZSH_CUSTOM:=~/.oh-my-zsh/custom}/plugins/zsh-completions
```

完成后记得修改`~/.zshrc`。

## Gitea

为了方便迁移和维护，这里选用docker来部署Gitea。  
这里需要额外安装docker-compose。

安装好依赖之后参考官网的教程编写如下的`docker-compose.yml`，放在`/etc/gitea`目录下。

{% highlight yml linenos %}
version: "3"

networks:
  gitea:
    external: false

services:
  server:
    image: gitea/gitea:1.20.5
    container_name: gitea
    environment:
      - USER_UID=1000
      - USER_GID=1000
      - GITEA__database__DB_TYPE=mysql
      - GITEA__database__HOST=db:3306
      - GITEA__database__NAME=gitea
      - GITEA__database__USER=gitea
      - GITEA__database__PASSWD=gitea
    restart: always
    networks:
      - gitea
    volumes:
      - ./gitea:/data
      - /etc/timezone:/etc/timezone:ro
      - /etc/localtime:/etc/localtime:ro
    ports:
      - "8080:3000"
      - "2222:22"
    depends_on:
      - db

  db:
    image: mysql:8
    restart: always
    environment:
      - MYSQL_ROOT_PASSWORD=gitea
      - MYSQL_USER=gitea
      - MYSQL_PASSWORD=gitea
      - MYSQL_DATABASE=gitea
    networks:
      - gitea
    volumes:
      - ./mysql:/var/lib/mysql

{% endhighlight %}

其中关键点如下：

1. 使用MYSQL数据库作为Gitea的数据库实现，所以在32行定义了`db`字段，并在30行设置为`server`实例的依赖；
2. 转发主机端口`8080`到docker端口`3000`、`2222`到`22`，这样从主机的`8080`端口可以访问Gitea的页面，并在`2222`端口通过ssh访问；
3. `docker-compose.yml`文件所在目录下的`gitea`目录被映射到容器内的`/data`目录，docker数据可以从主机的该目录下直接访问。

为了该服务能够开机启动，编写systemd service文件。

```systemd
# /etc/systemd/system/gitea.service

[Unit]
Description=Docker Compose Application Service for Gitea
Requires=docker.service
After=docker.service
StartLimitIntervalSec=60

[Service]
WorkingDirectory=/etc/gitea
ExecStart=/usr/bin/docker compose up
ExecStop=/usr/bin/docker compose down
TimeoutStartSec=30
Restart=on-failure
StartLimitBurst=3

[Install]
WantedBy=multi-user.target
```

逻辑上gitea依赖于docker服务，工作目录位于`/etc/gitea`，这些内容都体现在service文件中了。

保存后设置服务为开机启动。

```bash
sudo systemctl enable gitea.service
sudo systemctl start 
```

## 参考

Orange Pi 5 Plus官方介绍界面：[Orange Pi 5 Plus](http://www.orangepi.cn/html/hardWare/computerAndMicrocontrollers/service-and-support/Orange-Pi-5-plus.html)

安装omz和插件：[Raspberry Pi 安装 oh-my-zsh](https://www.likecs.com/show-307328694.html#sc=900)

使用 Docker 部署Gitea：[Gitea-使用 Docker 安装](https://docs.gitea.com/zh-cn/installation/install-with-docker)

docker-compose开机启动：[How to run docker-compose up -d at system start up?](https://stackoverflow.com/questions/43671482/how-to-run-docker-compose-up-d-at-system-start-up)

<!-- ## section 1

{{ image_dir }}

### link example 1

[link to first post]({% link _posts/2019-11-02-new-pi-4b.md %})

### link example 2

[link to first post]({% post_url 2019-11-02-new-pi-4b %})

### image example

![image]({{ image_dir }}/404.jpg "comments"){:.rounded}

### code block example

code block with line number

{% highlight c linenos %}
#include <stdio.h>

int main() {
    printf("hello world\n");

    return 0;
}
{% endhighlight %}

### embeded BiliBili video

## section 2

{% for shit in page %}
{{ shit }}
{% endfor %}

## reference

[Liquid: Safe, customer-facing template language for flexible web apps.](https://shopify.github.io/liquid/) -->
