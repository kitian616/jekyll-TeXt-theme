---
layout: post
title: 'Document - Writing Posts'
key: 20180410
category: Document
tags:
- Document
- English
---

As explained on the [directory structure](https://jekyllrb.com/docs/structure/) page, the _posts folder is where your blog posts will live. These files are generally Markdown or HTML. All posts must have YAML Front Matter, and they will be converted from their source format into an HTML page that is part of your static site.

<!--more-->

## Creating Post Files

To create a new post, all you need to do is create a file in the `_posts` directory. Jekyll requires blog post files to be named like these:

    2011-12-31-new-years-eve-is-awesome.md
    2012-09-12-how-to-write-a-blog.markdown

## Content

All blog post files must begin with YAML Front Matter.

To improve the user experience for both reading and writing posts, TeXt made some enhancements for markdown and some additional styles.

### YAML Front Matter

    ---
    layout: post
    title: Document - Writing Posts
    mathjax: true
    ---

Between these triple-dashed lines you can set variables. you can consider it as page configuration, these would overrides the global configuration in `_config.yml`.

Beside Jekyll's predefined variables, TeXt define some new variables:

| Variable      | Option Values         | Description |
| ---           | ---                   | ---         |
| key           | -                     | Unique key for the post |
| modify_date   | -                     | The last modified date of this post, the date is modified in the format `YYYY-MM-DD HH:MM:SS +/-TTTT`; hours, minutes, seconds, and timezone offset are optional. just like `date` variable |
| comment       | true(default), false  | Set as `false` to disable comment on the post |
| mathjax       | true, false(default)  | Set as `true` to enable Mathjax on the post |
| mathjax_autoNumber | true, false(default)  | Set as `true` to enable Mathjax autoNumber on the post |
| mermaid       | true, false(default)  | Set as `true` to enable Mermaid on the post |
| chart         | true, false(default)  | Set as `true` to enable Chart on the post |

### Markdown Enhancements

| Enhancemen Name | Description |
| --------------- | ----------- |
| **Mathjax** | Make it easy to add mathematics in articles | [EXAMPLES](https://tianqi.name/jekyll-TeXt-theme/test/2017/07/07/mathjax.html) |
| **Mermaid** | Bring diagrams and flowcharts in articles | [EXAMPLES](https://tianqi.name/jekyll-TeXt-theme/test/2017/06/06/mermaid.html) |
| **Chart**   | Bring charts in articles | [EXAMPLES](https://tianqi.name/jekyll-TeXt-theme/test/2017/05/05/chart.html) |

As mentioned above, you need set the variable true in the _config.yml or in the YAML Front Matter to enable markdown enhancement.

### Additional styles

Jekyll use kramdown as the default markdown processor. kramdown can adding attributes to block and span-level elements throw ALDs[^ALDs] feature. with the help of ALDs, we can defined class names to an element by `{:.class-name1 .class-name-2}`.

TeXt offer some class names that you can use in the post, refer to [HERE](https://tianqi.name/jekyll-TeXt-theme/test/2017/08/08/additional-styles.html) for details.

[^ALDs]: [Attribute List Definitions](https://kramdown.gettalong.org/syntax.html#attribute-list-definitions)