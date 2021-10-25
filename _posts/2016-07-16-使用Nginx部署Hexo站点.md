---
title: 使用Nginx部署Hexo站点
date: 2016-07-15
tags: [nginx,hexo]
---

在公司内网环境中，搭建一个静态站点，用于保存一些相关知识文档，并通过页面的形式展现出来。静态站点生成器这里我选择的是[Hexo](https://hexo.io)（一个基于Node实现的静态博客框架），而Web服务器首选当然是[Nginx](http://nginx.org)，简单高效。

### 1. 安装Nginx
这里我以Windows Server为例，安装非常简单，从[官方站点页面](http://nginx.org/en/download.html)下载Nginx软件包。下载完成后，直接解压到本地目录就行了。

打开`cmd`，进入Nginx目录，执行命令`start nginx`，然后你就可以在任务管理器中看到nginx的进程了。直接访问浏览器`http://127.0.0.1`,正常情况下，就能看到Nginx的欢迎界面了。如果不对，90%的可能是因为80端口占用问题，打开配置Nginx配置文件，修改一下默认端口就行了。

### 2. 配置Nginx
Nginx目录下打开`conf/nginx.conf`文件，部分内容如下：

```
...
server {
        listen       8080;
        server_name  127.0.0.1;
        #charset koi8-r;
        #access_log  logs/host.access.log  main;
        location / {
            root   html;
            index  index.html;
        }
...
```

如上，将默认`80`改成`8080`，然后在访问`http://127.0.0.1:8080`就OK了。下面`location`就是欢迎页面的访问路径，进入Nginx目录下`html`目录，可以看到有个`index.html`文件，这就是欢迎页面。

那么如何将Hexo静态站点部署在Nginx服务器下呢？

### 3. 部署静态站点
通过`Hexo g`命令生成的静态站点，默认就是Hexo站点目录中的`public`文件夹。

![](http://ww2.sinaimg.cn/large/006tNc79jw1f5vgu55i8aj306005baaa.jpg)

将生成好的静态站点（也就是`public/`目录），拷贝至Nginx目录下的`html`文件夹中。然后修改Nginx配置文件。

```
...
server {
        listen       8080;
        server_name  127.0.0.1;
        location / {
            root   html/public;
            index  index.html;
        }
...
```

只是修改了`root`字段为`public`目录，其他地方都不变，然后重新加载Nginx，打开`cmd`，在Nginx目录下执行`nginx -s reload`，重新访问`http://127.0.0.1:8080`，就可以看到Hexo静态站点了。__这里要注意浏览器缓存的问题__

### 问题
1. Hexo其实是提供`hexo s`命令来实时查看访问页面，如果在服务器端启动`hexo s`，在浏览器中同样也可以通过服务器IP来访问站点，并且能实时显示当前状态。但是，通过我尝试后发现，`hexo s`效率非常低，只能适合在线调试，不适合作为站点访问。
2. 更新的时候，需要使用`hexo g`重新生成站点，然后将`public`目录拷贝到Nginx目录中`html`文件夹下即可。