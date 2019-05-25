---
tags: 生活            # 标签
title: 下载电影美剧等并实现边下边播
author: yanbook       # 作者
# published: false    # 标记为草稿不发布
# excerpt_separator: <!--more-->   # 输出摘要长度
aside:
  toc: true           # 侧边栏

# article_header:
#   type: cover
#   image:
#     src: /screenshot.jpg         # 题图图片
---

看视频一般通过在线视频网站观看，或者下载到本地观看。本文主要说说下载视频并且能够边下边播观看的方法。
<!--more-->

边下边播大致分为两大类：一种是播放软件同时带有视频聚合功能，可以即点即播；一种是找到视频的种子资源，通过下载工具边下边播。
## 1. 聚合视频，即点即播
Stremio和 PopcornTime两个软件功能差不多。
![Stremio](/img/stremio.jpg "Stremio")
![PopcornTime](/img/popcorn-time.jpg "PopcornTime")

优点：
- 及时聚合最新电影和剧集
- 有各种分类方式，方便查看
- 能够选择清晰度
- 美剧等剧集能够按照季和集排列

缺点：
- 几乎都是欧美内容，亚洲很少
- 热门视频速度快，冷门速度慢或者没有速度。
- 推荐Stremio，分类和界面更合理，还可以安装各种插件。

## 2. 寻找资源，边下边播
### 使用迅雷边下边播
迅雷本身就带有边下边播的功能，但是最新的迅雷软件几乎是个广告流氓软件了，如果想有个干净的操作系统，不建议使用。
建议安装迅雷极速版作为下载工具。安装PotPlayer作为播放软件，这是款开源软件，功能强大，日常视频播放必备软件。
将迅雷的“边下边播”和PotPlayer关联起来，方法如下：
- 找到迅雷的安装位置，可以通过 迅雷快捷方式右击--打开文件位置；
- 找到PotPlayer安装位置，可以通过PotPlayer快捷方式右击--打开文件；
- 在迅雷安装程序文件夹位置新建一个xmp.ini文件，通过记事本编辑内容如下
```
[global]
Path=您的Potplayer安装路径\PotPlayerMini64.exe
//注意:  PotPlayerMini64.exe 或 PotPlayerMini.exe 需要您看下Potplayer安装路径下的该文件命名.
```
这样，当迅雷下载文件的时候，右键打开边下边播就可以调用PotPlayer播放了。

### 安装WebTorrent或者Soda Player
这两款软件都支持直接打开种子文件边下边播。
这两个软件不需要像迅雷那样改造软件，方便快捷。但是和Stremio和 PopcornTime一样，下载速度取决于种子的快慢。
以上各种方法，迅雷唯一的优点是对付国内资源，或者稍微冷门的资源有较大优势。如果资源比较热门，使用其它软件则更省心更舒服。

## 3. 寻找资源推荐
- 中文资源：

-- 美剧类：

[人人影视](http://www.zmz2019.com)

[天天美剧](http://www.ttzmz.vip)

各大字幕组

-- 电影类：

[电影天堂](https://www.dy2018.com)（非种子，只能迅雷） 

[高清MP4吧](http://www.mp4ba.com)  

[五号站](http://www.wuhaozhan.net/movie/list)

- 英文资源：

[RARBG](https://rarbgprx.org/torrents.php) （推荐，最新最全最及时） 

EZTV ,The Pirate Bay, YIFY  

- 字幕资源：

[subHD](http:/subhd.com/)(中文） 

[人人影视字幕](http://www.zmz2019.com/subtitle)（中文）  

[opensubtitles 1](https://www.opensubtitles.org)（英文）  或者[opensubtitles 2](https://www.opensubtitles.com)

各大字幕组

---------
参考

[http://xiaolai.co/books/xiaolai-xuexi/1fb749afb52ed507ecd0d34e4d62d6e9.html](http://xiaolai.co/books/xiaolai-xuexi/1fb749afb52ed507ecd0d34e4d62d6e9.html)

[https://digitalimmigrant.org/652](https://digitalimmigrant.org/652)
