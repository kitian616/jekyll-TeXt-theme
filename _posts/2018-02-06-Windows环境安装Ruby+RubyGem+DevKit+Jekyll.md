---
title: Windows环境安装Ruby+RubyGem+DevKit+Jekyll
key: 20180206_ruby_rubygem_devkit_jekyll
tags: Ruby+RubyGem+DevKit+Jekyll安装
---

## 1.安装Ruby

1. 下载Rubyinstall安装包https://rubyinstaller.org/downloads/，选择对应版本安装

<!--more-->

![mark](http://p3pla9n1t.bkt.clouddn.com/blog/180206/eDaHbhG9kb.png-Kuradeon)

​

1. 配置环境变量

   1. 添加环境变量：RUBY_HOME

   ![mark](http://p3pla9n1t.bkt.clouddn.com/blog/180206/A4hC4K69m1.png-Kuradeon)

   2. 修改path环境变量，添加地址：

      ![mark](http://p3pla9n1t.bkt.clouddn.com/blog/180206/1eDeF43cI0.png-Kuradeon)

   3. 测试ruby是否安装成功

      ![mark](http://p3pla9n1t.bkt.clouddn.com/blog/180206/Kgc767922C.png-Kuradeon)

## 2.安装RubyGem

一般情况下，安装完RubyInstall后，gem已经安装完毕。

![mark](http://p3pla9n1t.bkt.clouddn.com/blog/180206/G5J6B9chBb.png-Kuradeon)

如果安装失败，可以下载rubygem自行安装https://rubygems.org/pages/download

Or, to upgrade to the latest RubyGems:

```
$ gem update --system          # may need to be administrator or root
```

**NOTE:** RubyGems 1.1 and 1.2 have problems upgrading when there is no rubygems-update installed. You will need to use the following instructions if you see `Nothing to update`. If you have an older version of RubyGems installed, then you can still do it in two steps:

```
$ gem install rubygems-update  # again, might need to be admin/root
$ update_rubygems              # ... here too
```

If you don't have any RubyGems installed, there is still the pre-gem approach to getting software, doing it manually:

1. [Download from above](https://rubygems.org/pages/download#formats)
2. Unpack into a directory and `cd` there
3. Install with: `ruby setup.rb` (you may need admin/root privilege)

For more details and other options, see:

```
ruby setup.rb --help
```

## 3.安装DevKit

1. 于Ruby网站下载Devkit

   ![mark](http://p3pla9n1t.bkt.clouddn.com/blog/180206/DdC0hhHE9E.png-Kuradeon)

2. 运行解压后，安装DevKit：

   ```powershell
   D:\DevKit>ruby dk.rb init
   [INFO] found RubyInstaller v2.5.0 at D:/Ruby25-x64

   Initialization complete! Please review and modify the auto-generated
   'config.yml' file to ensure it contains the root directories to all
   of the installed Rubies you want enhanced by the DevKit.
   ```

   根据提示可能需要修改config.yml里面ruby的根目录

   修改完毕后可以回顾安装信息

   ```powershell
   D:\DevKit>ruby dk.rb review
   Based upon the settings in the 'config.yml' file generated
   from running 'ruby dk.rb init' and any of your customizations,
   DevKit functionality will be injected into the following Rubies
   when you run 'ruby dk.rb install'.
   ```

   最后安装DevKit

   ```powershell
   D:\DevKit>ruby dk.rb install
   [INFO] Skipping existing gem override for 'D:/Ruby25-x64'
   [WARN] Skipping existing DevKit helper library for 'D:/Ruby25-x64'
   ```

3. 配置DevKit环境变量

   1. 配置DEVKIT_HOME

   ![mark](http://p3pla9n1t.bkt.clouddn.com/blog/180206/aKL3dFeCEB.png-Kuradeon)

   2. 添加Path路径

      ![mark](http://p3pla9n1t.bkt.clouddn.com/blog/180206/0hJH7ei0FG.png-Kuradeon)

   ​

## 4.安装Jekyll

```powershell
D:\DevKit>gem install jekyll
Temporarily enhancing PATH for MSYS/MINGW...
Building native extensions. This could take a while...
Successfully installed http_parser.rb-0.6.0
Fetching: eventmachine-1.2.5-x64-mingw32.gem (100%)
Successfully installed eventmachine-1.2.5-x64-mingw32
Fetching: em-websocket-0.5.1.gem (100%)
Successfully installed em-websocket-0.5.1
Fetching: concurrent-ruby-1.0.5.gem (100%)
Successfully installed concurrent-ruby-1.0.5
Fetching: i18n-0.9.3.gem (100%)
Successfully installed i18n-0.9.3
Fetching: rb-fsevent-0.10.2.gem (100%)
Successfully installed rb-fsevent-0.10.2
Fetching: ffi-1.9.18-x64-mingw32.gem (100%)
ERROR:  Error installing jekyll:
```
安装Jekyll报错，发现不支持Ruby2.5，于是笔者便更换了Ruby 2.4.3来继续安装Jekyll

```powershell
D:\DevKit>gem install jekyll
Fetching: public_suffix-3.0.1.gem (100%)
Successfully installed public_suffix-3.0.1
Fetching: addressable-2.5.2.gem (100%)
Successfully installed addressable-2.5.2
Fetching: colorator-1.1.0.gem (100%)
Successfully installed colorator-1.1.0
Fetching: http_parser.rb-0.6.0.gem (100%)
Temporarily enhancing PATH for MSYS/MINGW...
Building native extensions.  This could take a while...
Successfully installed http_parser.rb-0.6.0
Fetching: eventmachine-1.2.5-x64-mingw32.gem (100%)
Successfully installed eventmachine-1.2.5-x64-mingw32
Fetching: em-websocket-0.5.1.gem (100%)
Successfully installed em-websocket-0.5.1
Fetching: concurrent-ruby-1.0.5.gem (100%)
Successfully installed concurrent-ruby-1.0.5
Fetching: i18n-0.9.3.gem (100%)
Successfully installed i18n-0.9.3
Fetching: rb-fsevent-0.10.2.gem (100%)
Successfully installed rb-fsevent-0.10.2
Fetching: ffi-1.9.18-x64-mingw32.gem (100%)
Successfully installed ffi-1.9.18-x64-mingw32
Fetching: rb-inotify-0.9.10.gem (100%)
Successfully installed rb-inotify-0.9.10
Fetching: sass-listen-4.0.0.gem (100%)
Successfully installed sass-listen-4.0.0
Fetching: sass-3.5.5.gem (100%)
Successfully installed sass-3.5.5
Fetching: jekyll-sass-converter-1.5.2.gem (100%)
Successfully installed jekyll-sass-converter-1.5.2
Fetching: ruby_dep-1.5.0.gem (100%)
Successfully installed ruby_dep-1.5.0
Fetching: listen-3.1.5.gem (100%)
Successfully installed listen-3.1.5
Fetching: jekyll-watch-2.0.0.gem (100%)
Successfully installed jekyll-watch-2.0.0
Fetching: kramdown-1.16.2.gem (100%)
Successfully installed kramdown-1.16.2
Fetching: liquid-4.0.0.gem (100%)
Successfully installed liquid-4.0.0
Fetching: mercenary-0.3.6.gem (100%)
Successfully installed mercenary-0.3.6
Fetching: forwardable-extended-2.6.0.gem (100%)
Successfully installed forwardable-extended-2.6.0
Fetching: pathutil-0.16.1.gem (100%)
Successfully installed pathutil-0.16.1
Fetching: rouge-3.1.1.gem (100%)
Successfully installed rouge-3.1.1
Fetching: safe_yaml-1.0.4.gem (100%)
Successfully installed safe_yaml-1.0.4
Fetching: jekyll-3.7.2.gem (100%)
Successfully installed jekyll-3.7.2
Parsing documentation for public_suffix-3.0.1
Installing ri documentation for public_suffix-3.0.1
Parsing documentation for addressable-2.5.2
Installing ri documentation for addressable-2.5.2
Parsing documentation for colorator-1.1.0
Installing ri documentation for colorator-1.1.0
Parsing documentation for http_parser.rb-0.6.0
Installing ri documentation for http_parser.rb-0.6.0
Parsing documentation for eventmachine-1.2.5-x64-mingw32
Installing ri documentation for eventmachine-1.2.5-x64-mingw32
Parsing documentation for em-websocket-0.5.1
Installing ri documentation for em-websocket-0.5.1
Parsing documentation for concurrent-ruby-1.0.5
Installing ri documentation for concurrent-ruby-1.0.5
Parsing documentation for i18n-0.9.3
Installing ri documentation for i18n-0.9.3
Parsing documentation for rb-fsevent-0.10.2
Installing ri documentation for rb-fsevent-0.10.2
Parsing documentation for ffi-1.9.18-x64-mingw32
Installing ri documentation for ffi-1.9.18-x64-mingw32
Parsing documentation for rb-inotify-0.9.10
Installing ri documentation for rb-inotify-0.9.10
Parsing documentation for sass-listen-4.0.0
Installing ri documentation for sass-listen-4.0.0
Parsing documentation for sass-3.5.5
Installing ri documentation for sass-3.5.5
Parsing documentation for jekyll-sass-converter-1.5.2
Installing ri documentation for jekyll-sass-converter-1.5.2
Parsing documentation for ruby_dep-1.5.0
Installing ri documentation for ruby_dep-1.5.0
Parsing documentation for listen-3.1.5
Installing ri documentation for listen-3.1.5
Parsing documentation for jekyll-watch-2.0.0
Installing ri documentation for jekyll-watch-2.0.0
Parsing documentation for kramdown-1.16.2
Installing ri documentation for kramdown-1.16.2
Parsing documentation for liquid-4.0.0
Installing ri documentation for liquid-4.0.0
Parsing documentation for mercenary-0.3.6
Installing ri documentation for mercenary-0.3.6
Parsing documentation for forwardable-extended-2.6.0
Installing ri documentation for forwardable-extended-2.6.0
Parsing documentation for pathutil-0.16.1
Installing ri documentation for pathutil-0.16.1
Parsing documentation for rouge-3.1.1
Installing ri documentation for rouge-3.1.1
Parsing documentation for safe_yaml-1.0.4
Installing ri documentation for safe_yaml-1.0.4
Parsing documentation for jekyll-3.7.2
Installing ri documentation for jekyll-3.7.2
Done installing documentation for public_suffix, addressable, colorator, http_parser.rb, eventmachine, em-websocket, concurrent-ruby, i18n, rb-fsevent, ffi, rb-inotify, sass-listen, sass, jekyll-sass-converter, ruby_dep, listen, jekyll-watch, kramdown, liquid, mercenary, forwardable-extended, pathutil, rouge, safe_yaml, jekyll after 40 seconds
25 gems installed
```

到此安装完毕