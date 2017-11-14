# [TeXt Theme](https://github.com/kitian616/jekyll-TeXt-theme)

[![Gem Version](https://img.shields.io/gem/v/jekyll-text-theme.svg)](https://github.com/kitian616/jekyll-TeXt-theme/releases)
[![license](https://img.shields.io/github/license/kitian616/jekyll-TeXt-theme.svg)](https://github.com/kitian616/jekyll-TeXt-theme/blob/master/LICENSE)

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

## How To Use

The easiest way is making a fork of the repository and rename it to `<username>.github.io`, then you can clone it to local, commit your changes and push.

And also, you can get the source code of the theme at the [release page](https://github.com/kitian616/jekyll-TeXt-theme/releases).

### Configuration

Add your information in the ./_config.yml file, such as your name, contact, title of the site and so on.

Write your brief introduction in the ./about.md file.

### Writing Posts

To create a new post, all you need to do is create a file in the ./_posts directory. the blog post files need to be named as the format `YEAR-MONTH-DAY-title.MARKUP`, for example `2011-12-31-new-years-eve-is-awesome.md`. You can find more info at [Jekyll: Writing Posts](https://jekyllrb.com/docs/posts/).

### Excerpt

### Installation Development Environment (not Necessary)

Find more info at [Jekyll: Installation](https://jekyllrb.com/docs/installation/).

### Development Server (not Necessary)

if you Installed Node.js, you can start a development server by running `npm run dev` at the root path of the repository.

if you don't, just run

```console
bundle exec jekyll serve -H 0.0.0.0
```
after the command finished, you can visit [http://localhost:4000/](http://localhost:4000/) to see the page.

You can find more info at [Jekyll: Usage](https://jekyllrb.com/docs/usage/).

## Advanced

### Color Theme

| `default` | `dark` | `forest` | `ocean` |
| --- |  --- | --- | --- |
| ![default](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_default.jpg) | ![dark](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_dark.jpg) | ![forest](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_forest.jpg) | ![ocean](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_ocean.jpg) |

### Icons

### Comment

### Reading Quantity

### Google Analytics

### Front Matter Enhancement

### Other Resource

## Example

Demo: [Qi's blog](https://tianqi.name/blog/)

Source Code: [https://github.com/kitian616/kitian616.github.io/](https://github.com/kitian616/kitian616.github.io/)