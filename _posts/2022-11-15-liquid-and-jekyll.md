---
layout: article
title: 【Blog】Liquid和Jekyll
permalink: /article/:title.html
key: liquid-and-jekyll
tags: 
  - blog
  - Liquid
  - jekyll
author: Yu Xiaoyuan
show_author_profile: true
render_with_liquid: false
---

<!-- abstract begin -->
为了更高效方便得写博文，博客框架的一些内容需要深挖。Liquid是利用模板来显示动态内容的语言，使用起来感觉有点像$\LaTeX$或者说是宏。
Jekyll是本博客使用框架的基础框架。
<!-- abstract end -->

<!--more-->

<!-- begin include -->

<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- end private variable of Liquid -->

## 基础语法

编程语言总体来说有一些通用的基本的语法元素。包括但不限于变量、分支、循环等等内容。

### 基础的基础

创建变量的语法非常简单，在md或html文件中插入如下内容来创建创建一个变量。

```liquid
{%- assign _assets_root_dir = "/assets" -%}
```

用同样的语法可以修改变量，在编译博文时按照顺序执行替换。想要在博文中输出一个Liquid变量可以通过如下语法实现。

```liquid
{{ _assets_root_dir }}
```

这样在编译博文时会将输出语句之前的最后一个assign的值替换。例如上述结果将在md或html中留下如下内容。

```markdown
/assets
```

其他基础内容包括操作符、类型、逻辑表达式、空行等内容参看[Liquid官方文档][1]。

### 分支控制

Liquid支持if、unless、case三种条件分支。

```liquid
{%- if include.source1 == nil -%}
  {%- if include.source0 == nil -%}
    {%- assign __return = include.target -%}
  {%- else -%}
    {%- assign __return = include.source0 -%}
  {%- endif -%}
{%- else -%}
  {%- assign __return = include.source1 -%}
{%- endif -%}
```

上面这段代码截取自[TeXt Theme][2]的文件`/_includes/snippets/assign.html`。整体上实现了一个判断变量是否为空并按照先后顺序进行返回的操作。  
优先级最高的变量是`include.source1`，只要该变量非空，则返回值为该变量的值。  
次之的是`include.source0`，最末是`include.target`。即只有当另外两个都为空，返回值才是`include.target`的值。

### 循环

最基础的for循环，举例如下。

```liquid
{% for shit in page %}
{{ shit }}
{% endfor %}
```

这段代码是笔者在没看文档的时候自己摸索出来的土方法，能在最终的文档里输出`page`字典的所有键。

### 渲染禁用

从上面的例子可以看出，Liquid语法包含很多花括号，如果博文中的代码块和Liquid冲突了，那么就需要暂时关闭liquid渲染。

比如在md文件中插入如下内容。

````markdown
{%- raw -%}
```liquid
{%- assign _assets_root_dir = "/assets" -%}
{{ _assets_root_dir }}
```
{%- endraw -%}
````

这样编译博文时md就会被替换为如下形式。

````markdown
```liquid
{%- assign _assets_root_dir = "/assets" -%}
{{ _assets_root_dir }}
```
````

如果没有两行`{%- raw -%}`和`{%- endraw -%}`，代码块内的内容将会被替换为如下形式。

````markdown
```liquid
/assets
```
````

在Jekyll框架下，如果需要在谋篇博文中整篇关闭Liquid渲染，可以在文件开头的配置块中插入如下语句来实现。

```markdown
---
render_with_liquid: false
---
```

## 拓展

### 函数

有时候一段代码可能反复调用，比如上面的一个例子。甚至一段代码可能跨文件调用。这时模板的力量就体现出来了。  
Liquid的include功能，我倾向于理解为函数。不过这种函数是以文件为基础的，一个文件只能包装成一个函数。

本代码库的前几次提交包含了一个基于这个功能的更新。某些博文包含的图片都放在一个有规律的目录下，这个目录只跟博文的`key`有关。  
笔者考虑在每一篇博文中都自动生成一个变量，自动生成图片的链接目录。这样在插入图片时就不用输入完整的路径，只要替换即可。

于是笔者在`_includes`目录下新建了一个[general-variables.html](https://github.com/yuxiaoyuan0406/yuxiaoyuan0406.github.io/blob/b1fa31876c18c735773452f7762647c13803f7a1/_includes/general-variables.html)文件，内容如下。

```liquid
{%- assign _assets_root_dir = "/assets" -%}
{%- assign _assets_img_dir = _assets_root_dir | append: "/images" -%}
{%- assign _post_img_dir = _assets_img_dir | append: "/posts" -%}

{%- if page.key -%}
    {%- assign image_dir = _post_img_dir | append: '/' | append: page.key -%}
{%- else -%}
    {%- assign image_dir = _post_img_dir -%}
{%- endif -%}
```

功能上生成了一个图片链接目录的变量。如果文章开头没有设置`key`，这个路径为`/assets/images/posts`，如果设置了`key`，则路径为`/assets/images/posts/{{ page.key }}`。  
这样，如果需要插入图片，就只需在md中插入如下内容。

```markdown
![usb无线网卡]({{ image_dir }}/wlan1.jpg){:.rounded}
```

### Filters和Tags

过滤器(Filters)和标签(Tags)是Liquid官方定义的概念，可以去官网看[文档][1]进行详细区分。要注意，Jekyll定义了很多新的可用标签，具体参看[官网文档](https://jekyllrb.com/docs/liquid/tags/)。

## 参考

[Liquid: Safe, customer-facing template language for flexible web apps.][1]

[TeXt Theme: 💎 🐳 A super customizable Jekyll theme for personal site, team site, blog, project, documentation, etc.][2]

[jekyll: Transform your plain text into static websites and blogs.][3]

<!-- begin reference links -->
[1]: https://shopify.github.io/liquid/
[2]: https://github.com/kitian616/jekyll-TeXt-theme
[3]: https://jekyllrb.com/
<!-- end reference links -->
