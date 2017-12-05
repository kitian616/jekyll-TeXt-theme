# [TeXt Theme](https://github.com/kitian616/jekyll-TeXt-theme)

[![Gem Version](https://img.shields.io/gem/v/jekyll-text-theme.svg)](https://github.com/kitian616/jekyll-TeXt-theme/releases)
[![license](https://img.shields.io/github/license/kitian616/jekyll-TeXt-theme.svg)](https://github.com/kitian616/jekyll-TeXt-theme/blob/master/LICENSE)
[![Travis](https://img.shields.io/travis/kitian616/jekyll-TeXt-theme.svg)](https://travis-ci.org/kitian616/jekyll-TeXt-theme)

![TeXt Theme](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/TeXt-home.png)

![TeXt Theme Details](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/TeXt-details.png)

TeXt is a succinct theme for blogging. Similar to iOS 11 style, it has large and prominent titles and round buttons & cards.

## Features

- Responsive
- Paginate ([jekyll-paginate](https://github.com/jekyll/jekyll-paginate))
- Table of contents ([TOC](http://projects.jga.me/toc/))
- Tag
- Reading quantity ([LeanCloud](https://leancloud.cn/))
- Emoji ([Jemoji](https://github.com/jekyll/jemoji))
- Comment ([Disqus](https://disqus.com/))
- Google Analytics
- Contact information (Email, Facebook, Twitter, Linkedin, Weibo, Zhihu, etc)
- Semantic HTML
- Icon automation tool ([gulp-svg2png](https://www.npmjs.com/package/gulp-svg2png), [gulp-to-ico](https://www.npmjs.com/package/gulp-to-ico))
- Color Theme
- Mathematical formula ([MathJax](https://www.mathjax.org/))
- RSS（[jekyll-feed](https://github.com/jekyll/jekyll-feed))
- Multi-language support(English | Simplified Chinese | Traditional Chinese)

## How To Use

The easiest way is making a fork of the repository and rename it to `<username>.github.io`, then you can clone it to local, commit your changes and push.

And also, you can get the source code of the theme at the [release page](https://github.com/kitian616/jekyll-TeXt-theme/releases).

### Configuration

Add your information in the ./_config.yml file, such as your name, contact, title of the site and so on.

Write your brief introduction in the ./about.md file.

### Writing Posts

To create a new post, all you need to do is create a file in the ./_posts directory. the blog post files need to be named as the format `YEAR-MONTH-DAY-title.MARKUP`, for example `2011-12-31-new-years-eve-is-awesome.md`. You can find more info at [Jekyll: Writing Posts](https://jekyllrb.com/docs/posts/).

### Excerpt

There are two excerpt types: TEXT type and HTML type. You can change it by setting the value of `excerpt_type` in the ./\_config.yml file.

| excerpt_type | type | description |
| --- | --- | --- |
| text | TEXT | the excerpt are plain text that filters out all non-text elements (such as title, link, list, table, picture, etc.) and only show 350 characters most. |
| html | HTML | the excerpt are HTML document just like the content of the article, This will show all the content by default, except adding `<!--more-->` in the article Markdown file, You can find more info at [Jekyll: Post#post-excerpts](https://jekyllrb.com/docs/posts/#post-excerpts).  |

### Installation Development Environment (not Necessary)

Find more info at [Jekyll: Installation](https://jekyllrb.com/docs/installation/).

### Development Server (not Necessary)

if you Installed Node.js, you can start a development server by running `npm run dev` at the root path of the repository.

if not, just run:

```console
bundle exec jekyll serve -H 0.0.0.0
```
after the command finished, you can visit [http://localhost:4000/](http://localhost:4000/) to see the page.

You can find more info at [Jekyll: Usage](https://jekyllrb.com/docs/usage/).

## Advanced

### Color Theme

| `default` | `dark` | `forest` |
| --- |  --- | --- |
| ![default](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_default.png) | ![dark](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_dark.png) | ![forest](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_forest.png) |

| `ocean` | `chocolate` | `orange` |
| --- |  --- | --- |
| ![ocean](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_ocean.png) | ![chocolate](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_chocolate.png) | ![orange](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_orange.png) |

### Icons

This theme comes with a "ginkgo leaf" icon, you can replace it with your own icon. The site's icons are located in the ./favicon.ico and ./assets/images/logo directories. You can see the logo directory has a lot of png files and a svg vector file. Those png images are actually images of different sizes generated from svg vector images that may used in some scenes, such as pinned to the screen in OS and Android and tiles in Windows 10.

This theme provides an automated script for automatically generating favicon and png files from svg vector graphics. What you have to do is:

1. Install Node.js

2. Run `npm i` command at the root directory.

3. Replacing the logo.svg file in the ./assets/images/logo directory.

4. Run `npm run artwork` command. after it finished, the favicon and png files will be replaced by the new logo.svg generated files.

### Comment

### Reading Quantity

### Google Analytics

### Front Matter Enhancement

In addition to Jekyll's front matters, the theme adds some unique front matters.

| variable      | option values | description |
| ---           | ---           | ---         |
| key           | | |
| picture_frame | shadow        | |
| modify_date   | | |
| comment       | true/false    | |
| mathjax       | true/false    | |

### Other Resource

## Examples

- [Demo](https://tianqi.name/jekyll-TeXt-theme/)
- [Qi's blog](https://tianqi.name/blog/)