---
title: Hexo站点中添加文章目录以及归档
date: 2015-06-11
tags: [hexo,toc,archive]
---

通过前面的介绍，Hexo站点的布局应该了解得差不多了，这次主要介绍一下如何使用Hexo自带的[帮助函数](https://hexo.io/zh-cn/docs/helpers.html)在站点中添加归档以及文章目录。添加这两块功能都是使用了Hexo提供的帮助函数，创建对应局部模块之前，首先要想想这两块内容应该属于哪个布局？要添加到哪个局部模块下？考虑这些是为了整洁性，当你添加的东西越来越多的时候才不至于令自己混乱。
<!-- more -->
### 一、文章目录
文章目录肯定是添加到`post`布局上，这个毋庸置疑，因为只有看文章详情页的时候才需要目录。那么我们在目录`layout/_partial/post/`下创建`toc.ejs`文件，代码如下：

```
<div id="toc" class="toc-article">
	<div class="toc-title">目录</div>
	<%- toc(item.content, {list_number: false}) %>
</div>
```
这里使用了Hexo提供的`toc()`帮助函数，它的使用方法如下：

	<%- toc(str, [options]) %>
	
`str`就是文章内容，`options`有两个参数，一个是`class`，也就是html标签的class值，默认为toc；一个是`list_number`，是否显示列表编号，默认值是true。

接下考虑把这个局部模块放到哪呢，既然属于`post`布局，那么就看看`layout/post.ejs`代码如下:

	<%- partial('_partial/article', {item: page, index: false}) %>

很明显，我们要到`_partial/article.ejs`文件里添加`toc.ejs`，添加后`article.ejs`代码如下：

```
......
    <div class="entry">
      <% if (item.excerpt && index){ %>   <!--01-->
        <%- item.excerpt %>
      <% } else { if (item.toc !== false) {%>   <!--02-->
        <%- partial('post/toc') %>
      <% } %>
        <%- item.content %>
      <% } %>
    </div>
......

```

01、判断是否有摘要以及index值，显然`post.ejs`传过来的index值为false；
02、接下来判断`page.toc`是不是不等于`false`，这一块的主要作用是可以在文章的前置声明里设置`toc: false`来关闭目录功能。当没有设置`false`时，插入上面写的`toc.ejs`局部模块。

OK！完美嵌入进去，接下来就是设置样式了，进入`source/css/_partial/`目录下，创建`toc.styl`，代码这里就不贴出了，具体查看[github](https://github.com/pengloo53/light-ch)。最后别忘了在`source/css/style.styl`文件里加入这句了`@import '_partial/toc'`。显示如下图，样式可以自行调整。

![](/image/hexo/toc01.jpg)

### 二、归档sidebar
默认的`hexo-theme-light`主题没有添加归档的sidebar挂件，于是我将该widget添加到了主题[light-ch](https://github.com/pengloo53/light-ch)中。先看一下效果如下图：

![](/image/hexo/archive01.png)

sidebar中的局部模块都在`layout/_widget`中，于是在该文件夹下创建`archive.ejs`，代码如下：

```
<% if (site.posts.length){ %>
  <div class="widget tag">
    <h3 class="title">归档</h3>
	<%- list_archives({format: "YYYY年MM月"}) %>
  </div>
<% } %>
```

然后在主题的配置文件中`_config.yml`中加入该`widget`，如下所示：

	widgets:
		- category
		- archive
		- tagcloud
		- weibo

最后就是设置样式了，在`source/css/_partial/sidebar.styl`文件中加入了这么一段就能如上显示了。

```
.archive-list
	font-size 0.9em
	padding 15px 20px 
	.archive-list-count
		margin-left 8px
		font-size 0.8em
		color color-meta
		&:before
			content '('
		&:after
			content ')'
```
这段样式是为了和分类列表保持一致。详细代码参照[github](https://github.com/pengloo53/light-ch)来自定义样式。

### 总结
> 这里通过添加文章目录以及归档侧边栏的实战练习，大致了解了如何在Hexo站点中添加局部模块和widget，基本上也就这两种类型。  
诸如添加百度统计、多说评论以及微博小插件等等，都可以归属为上述两类，这里就不再赘述，详情可以参照[github](https://github.com/pengloo53/light-ch)上代码，有什么问题可以随时向我提issue。