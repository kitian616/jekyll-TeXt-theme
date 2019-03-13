---
tags: 技术            # 标签
title: 使用Github创建一个简单的博客
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
### 简介
使用GitHub创建一个简单的blog系统有很多好处，比如不用维护主机，自己完全掌握控制权，国内国外的访问速度都很好，可以绑定自己的域名，最重要的是这一切还免费。
<!--more-->
网上关于如何搭建的教程很多，也很全面。但一般都需要下载程序进行各种繁琐的配置，如果你只是要建立一个博客，不使用GitHub其它功能的话，其实完全可以一切都在线上操作。

以下是我使用 GitHub+Jekyll完全线上创建博客的过程。

### 建立账户
1. 注册GitHub账号，建立仓库 [教程](https://blog.csdn.net/p10010/article/details/51336332)
2. 注册过程中会设置一个账号ID，以后还会用到，为了别于叙述，假设是“gitID”
3. 按照教程还需要新建一个仓库，假设这个仓库名称是“blogID”

### 安装博客程序
1. 我使用的是jekyll-TeXt-theme程序
2. 浏览器打开[jekyll-TeXt-theme](https://github.com/kitian616/jekyll-TeXt-theme),点击右上角的"Ford".此时你就将程序复制到了你的账号里.
![image](/img/2019-03-12-github-fork.jpg)
3. 打开"Settings",将你的账号"gitID"如图所示改为"gitID.github.io"
![image](/img/2019-03-12-github-rename-repo.jpg)

---

过几分钟,浏览器打开gitID.github.io即可看到你的网站了.这是你博客默认的一个二级域名.已经可以访问,如果想绑定自己的域名,在以后的设置里会涉及.

---

### 网站的细节配置
#### 在线修改代码
1. 打开你的代码页
![image](/img/2019-03-12-blog.png)
2. 我们以 _config.yml 页面为例说明如何在线修改代码,点击右上角红圈所示"Edit this file"即可进入代码编辑页面.
![image](/img/2019-03-12-edit.png)
3. 修改完成后,点击页面最下方"Cmmit change"保持即可. 
#### 完善网站配置
