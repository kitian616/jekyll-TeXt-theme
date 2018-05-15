---
layout: post
title: 撰写博客
permalink: /docs/zh/writing-posts
key: 20180410-ch
lang: zh
category: Document
tags:
- Document
- Simplified Chinese | 简
---

在[目录结构](http://jekyllcn.com/docs/structure/)介绍中说明过，所有的文章都在 _posts 文件夹中。这些文件可以用 Markdown 或 HTML 编写。只要文件中有 YAML 头信息，它们就会从源格式转化成 HTML 页面，从而成为你的静态网站的一部分。

<!--more-->

## 创建文章

发表一篇新文章，你所需要做的就是在 `_posts` 文件夹中创建一个新的文件。文件名的命名非常重要。Jekyll 要求一篇文章的文件名遵循下面的格式：

    年-月-日-标题.MARKUP

下面是一些合法的文件名的例子：

    2011-12-31-new-years-eve-is-awesome.md
    2012-09-12-how-to-write-a-blog.markdown

## 内容相关

所有博客文章顶部必须有一段 YAML 头信息(YAML front-matter)。

为了提高文章的阅读和书写体验，TeXt 在 Markdown 原有的基础上做了一些增强。

### YAML 头信息

    ---
    layout: post
    title: Document - Writing Posts
    mathjax: true
    ---

在 `---` 之间你可以设置属性的值，可以把它们看作页面的配置，这些配置会覆盖在 `_config.yml` 文件中设置的全局配置。

除去 Jekyll 自定义的变量外，TeXt 也定义了一些额外的变量：

| 变量名             | 可选值                 | 描述 |
| ---               | ---                   | --- |
| **key**           | -                     | 文章或页面的唯一标识符，供评给评论系统和点击量统计使用。必须以字母（`[A-Za-z]`）开头，其后可以接若干字母、数字（`[0-9]`）、连字符（`-`）、下划线（`_`）、冒号（`:`）和小数点（`.`） |
| **modify_date**   | -                     | 该文章的最后修改时间, 其格式为 `YYYY-MM-DD HH:MM:SS +/-TTTT` 和 `date` 的格式相同 |
| **comment**       | true(default), false  | 该文章或页面是否开启评论支持，默认开启，设置为 `false` 关闭 |
| **mathjax**       | true, false(default)  | 该文章或页面是否开启 Mathjax 公式支持，默认关闭，设置为 `true` 开启 |
| **mathjax_autoNumber** | true, false(default)  | 该文章或页面的 Mathjax 公式是否自动编号，默认关闭，设置为 `true` 开启 |
| **mermaid**       | true, false(default)  | 该文章或页面是否开启 Mermaid 流程图支持，默认关闭，设置为 `true` 开启 |
| **chart**         | true, false(default)  | 该文章或页面是否开启 Chart 图表支持，默认关闭，设置为 `true` 开启 |

### Markdown 增强

| 增强项 | 描述 |
| --------------- | ----------- |
| **Mathjax** | 在文章中方便的加入数学公式，使用 MathML、LaTeX 和 ASCIIMathML 语法 | [示例](https://tianqi.name/jekyll-TeXt-theme/test/2017/07/07/mathjax.html) |
| **Mermaid** | 在文章中方便的加入流程图 | [示例](https://tianqi.name/jekyll-TeXt-theme/test/2017/06/06/mermaid.html) |
| **Chart**   | 在文章中方便的加入可交互的图表 | [示例](https://tianqi.name/jekyll-TeXt-theme/test/2017/05/05/chart.html) |

正如上文所说，你需要在 `_config.yml` 或 YAML 头信息中设置相关的属性为 `true` 来开启对应的功能。

### 附加样式

Jekyll 使用 kramdown 作为默认 Markdown 解释器。kramdown 可以通过 ALDs[^ALDs] 来设置块级元素或行内元素的属性。例如，可以通过 `{:.class-name1 .class-name-2}` 来给元素定义样式类。

TeXt 定义了一些样式类，你可以在文章和页面的方便的使用，可以在[这里](https://tianqi.name/jekyll-TeXt-theme/test/2017/08/08/additional-styles.html)看到示例和详细的说明.

[^ALDs]: [Attribute List Definitions](https://kramdown.gettalong.org/syntax.html#attribute-list-definitions)