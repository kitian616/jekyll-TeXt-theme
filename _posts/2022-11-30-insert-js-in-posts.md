---
layout: article
title: 【Blog】在博文中插入JavaScript
permalink: /article/:title.html
key: insert-js-in-posts
tags: 
  - blog
  - javascript
  - Liquid
author: Yu Xiaoyuan
authors: 
  - Yu Xiaoyuan
  - Shao Mingyue
show_author_profile: true
license: WTFPL
---

<!-- abstract begin -->
博文本身是基于Markdown编写的，既然如此肯定可以在文中嵌入html，因而可以在其中嵌入Javascript。
<!-- abstract end -->

<!--more-->

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- {%- increment equation-h2-1 -%} -->
<!-- end private variable of Liquid -->

## 准备工作

笔者以嵌入一个现成的[开源pdf工具](https://github.com/smy1999/PDFTools)为例进行测试，该工具已在[GitHub-Pages](https://smy1999.github.io/PDFTools/)上部署。

由于需要对编辑好的静态界面进行修改，所以首先[fork](https://github.com/yuxiaoyuan0406/PDFTools)原作者的repo，并以submodule的形式植入到本博客repo的工作区中。

注意：要使用include嵌入的submodule应该放在`_posts`目录下。
{:.warning}

文件到位之后，由于原repo的设计是直接部署到网页上的，而笔者要将其嵌入到博文内，因此需要对原文件进行一定的修改。修改后的内容可以看笔者的[blog分支](https://github.com/yuxiaoyuan0406/PDFTools/tree/blog)。

主要修改有两点：

- 删除无用的标签
- 修改脚本的植入方式

然后在博文中就可以直接嵌入修改好的内容了。

{%- raw %}
```markdown
{%- include_relative PDFTools/split.html -%}
```
{% endraw %}

## 嵌入效果

{%- include_relative PDFTools/split.html -%}

可以点击这里的按钮测试一下。

## 参考

[smy1999/PDFTools](https://github.com/smy1999/PDFTools)

[jekyll Includes: Including files relative to another file](https://jekyllrb.com/docs/includes/#including-files-relative-to-another-file)
