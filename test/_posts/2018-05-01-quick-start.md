---
layout: post
title: 'Document - Quick Start'
key: 20180501
category: Document
tags:
- Document
- English
---

TeXt Theme is 100% compatible with GitHub Pages and it has been developed as a [gem-based themes](https://jekyllrb.com/docs/themes/) for easier use.

In this document, you will learn how to **install the theme**, **setup your site**, **local preview** for development, **build and publish**.

<!--more-->

## Installing the Theme

### Common Method

To install it with common method, all you need to do is copying all the theme files to your project. There are several ways to do so:

1. Clone [jekyll-TeXt-theme](https://github.com/kitian616/jekyll-TeXt-theme) from github:

    ```bash
    git clone git@github.com:kitian616/jekyll-TeXt-theme.git
    ```

2. Download and unzip the file to your Jekyll site directory.

    [==> Download TeXt Theme <==](https://github.com/kitian616/jekyll-TeXt-theme/archive/master.zip)

3. If you host your site on GitHub Pages, you can just fork [jekyll-TeXt-theme](https://github.com/kitian616/jekyll-TeXt-theme), then rename the repository to **USERNAME.github.io** — replacing **USERNAME** with your GitHub username.

    ![Fork](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/test/assets/images/fork.png)

    ![Rename](https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master/test/assets/images/rename.png)

### Ruby Gem Method

Add this line to your Jekyll site’s `Gemfile`:

```ruby
gem "jekyll-text-theme"
```

Add this line to your Jekyll site’s `_config.yml` file:

```yaml
theme: jekyll-text-theme
```

Then run Bundler to install the theme gem and dependencies:

```bash
bundle install
```

## Setup Your Site

If you install the theme with common method, you can go straight to the next step. But **if you install the theme with ruby gem method, you have to do some extra jobs**.

With gem-based themes, some of the site’s directories (such as the assets, _layouts, _includes, and _sass directories) are stored in the theme’s gem, hidden from your immediate view. You need add some files in your Jekyll site directory:

```bash
├── 404.html
├── Gemfile
├── _config.yml
├── _data
│   └── locale.yml
├── _posts
│   └── ...
├── about.md
├── all.html
└── index.html
```

You can reffer to the [/test folder](https://github.com/kitian616/jekyll-TeXt-theme/tree/master/test), this is a example with gem-based themes.

## Local Preview

Run `bundle exec jekyll serve` to start the development server, then you can visit [http://localhost:4000/](http://localhost:4000/) to preview your site.

## Build and Publish

If you host your site on GitHub Pages, just push the source to the master branch of your USERNAME.github.io repository, GitHub would build automatically. You can visit your site on **https://USERNAME.github.io** several minutes later.

If you host your site on your server, you need first run `JEKYLL_ENV=production bundle exec jekyll build` to generated your site, then update the files in _site folder to your server.

---

## Further Reading

- [Configuration](https://tianqi.name/jekyll-TeXt-theme/test/document/2018/04/20/configuration.html)

- [Writing Posts](https://tianqi.name/jekyll-TeXt-theme/test/document/2018/04/10/writing-posts.html)