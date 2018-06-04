# [TeXt Theme](https://github.com/kitian616/jekyll-TeXt-theme)

[![Gem Version](https://img.shields.io/gem/v/jekyll-text-theme.svg)](https://github.com/kitian616/jekyll-TeXt-theme/releases)
[![license](https://img.shields.io/github/license/kitian616/jekyll-TeXt-theme.svg)](https://github.com/kitian616/jekyll-TeXt-theme/blob/master/LICENSE)
[![Travis](https://img.shields.io/travis/kitian616/jekyll-TeXt-theme.svg)](https://travis-ci.org/kitian616/jekyll-TeXt-theme)

![TeXt Theme](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/TeXt-home.png)

![TeXt Theme Details](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/TeXt-details.png)

TeXt 是针对博客的一款简洁的主题，它虽然简洁但并不简单。它参考了 iOS 11 的风格，有大而突出的标题和圆润的按钮及卡片。

[English Documentation](https://github.com/kitian616/jekyll-TeXt-theme/blob/master/README.md)

## Features

- 响应式
- 分页（[jekyll-paginate](https://github.com/jekyll/jekyll-paginate)）
- 文章目录
- 文章标签
- 搜索（标题）
- 阅读次数统计（[LeanCloud](https://leancloud.cn/)）
- Emoji（[Jemoji](https://github.com/jekyll/jemoji)）
- 评论（[Disqus](https://disqus.com/), [gitalk](https://gitalk.github.io/)）
- Google Analytics
- 联系方式设置（Email, Facebook, Twitter, 微博, 知乎……）
- Web 语意化
- 网站图标的自动化工具（[gulp-svg2png](https://www.npmjs.com/package/gulp-svg2png), [gulp-to-ico](https://www.npmjs.com/package/gulp-to-ico)）
- Color Theme
- 数学公式（[MathJax](https://www.mathjax.org/)）
- 流程图， 序列图，甘特图（[mermaid](https://mermaidjs.github.io/)）
- 柱状图，折线图，饼图，雷达图（[chartjs](http://www.chartjs.org/)）
- RSS（[jekyll-feed](https://github.com/jekyll/jekyll-feed)）
- 多语言支持（English, 简体中文, 繁體中文）

## Color Theme

颜色主题位于文件夹 ./\_sass/colors 中，修改 ./\_config.yml 中的 text_color_theme 项为以下值即可更换颜色主题，默认主题为 default。

| `default` | `dark` | `forest` |
| --- |  --- | --- |
| ![default](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_default.png) | ![dark](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_dark.png) | ![forest](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_forest.png) |

| `ocean` | `chocolate` | `orange` |
| --- |  --- | --- |
| ![ocean](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_ocean.png) | ![chocolate](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_chocolate.png) | ![orange](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/screenshots/colors_orange.png) |

## 文档

### 开始

- [快速开始](https://tianqi.name/jekyll-TeXt-theme/docs/zh/quick-start)

### 定制

- [配置](https://tianqi.name/jekyll-TeXt-theme/docs/zh/configuration)
- [导航栏](https://tianqi.name/jekyll-TeXt-theme/docs/zh/navigation)
- [布局](https://tianqi.name/jekyll-TeXt-theme/docs/zh/layouts)

### 内容

- [撰写博客](https://tianqi.name/jekyll-TeXt-theme/docs/zh/writing-posts)
- [Markdown 增强](https://tianqi.name/jekyll-TeXt-theme/docs/zh/markdown-enhancements)
- [附加样式](https://tianqi.name/jekyll-TeXt-theme/docs/zh/additional-styles)

## 网站图标

该主题自带了一个“银杏叶”图标，你可以把它替换为自己的图标。网站的图标位于 ./favicon.ico 和 ./assets/images/logo 目录下。你会看到 logo 目录中有很多的 png 文件和一个 svg 矢量图文件。那些 png 图片实际上就是根据 svg 矢量图生成的不同大小的图片，这些图片是一些场景可能会用到的大图标，像 iOS 和 Android 的固定到屏幕和 Windows 10 的磁贴。

该主题提供了一个自动化脚本能将 svg 矢量图自动生成 favicon 和 png 文件。你所要做的是：

1. 安装 Node.js 环境

2. 在项目根目录执行 `npm i` 命令

3. 替换 ./assets/images/logo 目录下的 logo.svg 文件

4. 执行 `npm run artwork` 命令，此时 favicon 和 png 便会替换为新 logo.svg 生成的文件

当然如果要追求各个尺寸下图标的显示效果，那还得对不同尺寸的图片进行修改和优化。

## 其他资源

在 ./\_includes/icon/social 目录下有很多的社交产品图标，例如 Behance、Flickr、QQ、微信等，方便修改和使用。

## 示例

| Name | Description |
| --- | --- |
| [Home](https://tianqi.name/jekyll-TeXt-theme/) | 文章列表页 |
| [Archive](https://tianqi.name/jekyll-TeXt-theme/archive.html) | 过滤标签和查询 |

## 协议

TeXt Theme 遵循 [MIT 协议](https://github.com/kitian616/jekyll-TeXt-theme/blob/master/LICENSE)。