---
layout: post
title: 我的软件清单
key: 10009
tags: Tools
category: blog
date: 2016-03-05 21:48:00 +08:00
modify_date: 2017-09-16 14:30:00 +08:00
picture-frame: shadow
picture-max-width: 480px
---

整理了下自己喜欢的软件，列出了这个清单。

我的原则是：尽量使用免费开源软件，不使用破解软件。

**本文长期更新，欢迎推荐。**
<!--more-->

## Windows

### PicPick

瑞士军刀一样的软件。图像编辑器，颜色选择器，颜色调色板，像素标尺，量角器，瞄准线和白板等等，堪称全能的设计工具。更重要的是，它对个人用户是完全免费的。

![PicPick](https://wx1.sinaimg.cn/large/73bd9e13ly1fjle6dsbhsj204q08g0so.jpg)

[官网链接](http://ngwin.com/picpick)

### PuTTY

Putty 是一个免费的，Windows 32 平台下的 telnet、rlogin 和 ssh 客户端。

![PuTTY](https://wx1.sinaimg.cn/large/73bd9e13ly1fjle6dbwcrj20kt0dkq38.jpg)

[官网链接](http://www.putty.org/)

### Advanced ip Scanner

是可以在快速扫描局域网计算机信息的网络 IP 扫描工具，对于寻找一些没有显示的设备（比如没接屏幕的树莓派）IP 特别有用。

![Advanced ip Scanner](https://wx3.sinaimg.cn/large/73bd9e13ly1fjle6bprd4j20jg0erq35.jpg)

### Oracle VM VirtualBox

VMware Workstation 的绝佳开源替代品，十分适合在 Windows 中虚拟 Linux 系统环境。

![VirtualBox](https://wx4.sinaimg.cn/large/73bd9e13ly1fjle6c6dm4j20l80fhwfe.jpg)

[官网链接](https://www.virtualbox.org/)

### Cmder

cmd 替代品，能最大化，标签页以及非常不错的定制性。

![Cmder](https://wx1.sinaimg.cn/large/73bd9e13ly1fjle6cxdlqj20qy0g0dmm.jpg)

[官网链接](http://cmder.net/)

### typora

Windows 下非常好用的 Markdown 编辑器，支持多种主题，更棒的是还支持 YAML 头文件。

[官网链接](https://www.typora.io/)

### mp3Tag

MP3 文件 ID3-Tag 信息修改器。可以修改 MP3 文件中的曲名、演唱者、专集、年月、流派、注释等信息，歌曲收藏者的利器。

[官网链接](http://www.mp3tag.de/)

## Linux

### zsh (with Oh My Zsh)

比 bash 更好用的 shell。更强的可配置性，更强的 tab 补全，还附带 git 支持。再加上[Oh My Zsh](https://github.com/robbyrussell/oh-my-zsh)的存在，zsh 已经相当易用了。

![zsh](https://wx2.sinaimg.cn/large/73bd9e13ly1fjle6b9zi3j20nm0h0q5k.jpg)

[Oh My Zsh 项目链接](https://github.com/robbyrussell/oh-my-zsh)

### Tmux

SSH 最佳伴侣。

[官网链接](https://tmux.github.io/)

### Graphviz

“所想即所得”的画图工具，由大名鼎鼎的贝尔实验室开发。简单的来讲就是一款使用脚本语言来进行绘图的工具。

![Graphviz](https://wx3.sinaimg.cn/large/73bd9e13ly1fjle6aocfqj20sg0g8tdu.jpg)

[官网链接](http://www.graphviz.org/)

[DOT脚本语言](https://zh.wikipedia.org/wiki/DOT语言)

### Graph::Easy

跟 Graphviz 类似的软件。用它可以很方便的绘制出字符版的流程图，很适合代码注释。当然，它的功能远不止这些。

{% highlight text%}
+------+     +--------+      .............     +---------+
| Bonn | --> | Berlin |  --> : Frankfurt : --> | Dresden |
+------+     +--------+      .............     +---------+
               :
               :
               v
             +---------+     +---------+
             | Potsdam | ==> | Cottbus |
             +---------+     +---------+
{% endhighlight %}

[GitHub 项目页](https://github.com/ironcamel/Graph-Easy)

## Coding

### Visual Studio Code

微软出品，和 atom 一样基于 electron，但比 atom 流畅。页面非常酷，插件也已经非常全面了，越来越多的前端工程师开始转向它了。

[官网链接](https://code.visualstudio.com/)