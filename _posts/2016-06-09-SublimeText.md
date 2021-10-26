---
title: Sublime text - 最性感的编辑器
date: 2016-06-08
tags: [tool,sublime]
---

Vim被誉为『编辑器之神』，而emacs则被誉为『神的编辑器』，在程序猿世界中，从来都不乏编辑器的战争，而Vim和emacs是其中的佼佼者，争论则更加凶猛。当然真正的高手都是不屑于争论这些的，因为他们知道，适合自己的才是最好的。每一款编辑器都有其优势和劣势，发挥它的最大功效，提高自己的效率才是最重要的。这里要说到一款同样比较流行的编辑器--Sublime text。

对于Web开发者来说，应该对这款编辑器比较熟悉，个人觉得这款编辑器非常适合Web开发。针对页面的编写有非常好的体验。我个人就是因为Web的开发而接触的这款编辑器。可能是由于其漂亮的外观吧，被人称作是『最性感』的编辑器。

刚使用，还是有诸多的不便的，怎么装插件，怎么使用自定义？不过我愿意为它稍微改变一下使用习惯。下面简要介绍一下我刚开始使用遇到的一些问题。

### _1、设置代理服务器_
工作环境需要代理才能上网，联网第一步就要设置代理，打开`Setting-User(用户设定)`，添加`http_proxy`以及`https_proxy`属性值。

    "http_proxy": "http://10.20.1.1:8888"
    "https_proxy": "https://10.20.1.1:8888"

### _2、设置主题_
还是`Setting-User`，添加或者修改`font_face`属性值就可以了。

    {
      "color_scheme": "Packages/Color Scheme - Default/Dawn.tmTheme",
      "font_size": 16,
      "font_face": "Comic Sans MS"
    }

这是我个人比较喜欢的主题和字体，并不是默认的那种黑黑的编辑框。这里再推荐一款主题`flatland`,通过插件管理器很方便就能找到它。然后在`Setting-User`中添加下面两句就行了。

    {  
      "theme": "Flatland Dark.sublime-theme",  
      "color_scheme": "Packages/Theme - Flatland/Flatland Dark.tmTheme"  
    } 

### _3、安装插件管理器以及插件_
每一款优秀的编辑器应该都会有插件管理器，为了管理诸多的插件实现更强大的扩展功能。之前一篇文章里介绍了Vim的相关插件，用的插件管理器是Vundle。而Sublime Text的插件管理器叫做『Package Control』，安装方法不多说，网上很多教程。

[Sublime text 2/3 中 Package Control 的安装与使用方法](http://www.imjeff.cn/blog/62/)  
[Sublime Text 3 支持的热门插件推荐 ](http://www.imjeff.cn/blog/146/)  
[Sublime Text插件的升级和卸载？](http://www.zhugexiaojue.com/note/updatedrop-208.html)  

### _4、目录树_
不像Vim编辑器，目录树需要安装`NerdTree`插件,`Sublime text`编辑器是默认带有目录树功能的。打开一个目录就会自动在编辑器左侧显示该目录的结构，并且通过添加目录到项目的方式，可以将多个不同的目录在目录树中显示，同时可以保存项目，产生一个`.sublime-project`后缀的文件，保存现有项目的目录树结构。这样可以便于在不同的项目中切换目录树。

![](/image/tools/sublime01.png)

### _5、多行编辑技巧_

- 鼠标选中多行，按下 Ctrl Shift L (Command Shift L) 即可同时编辑这些行；
- 鼠标选中文本，反复按 CTRL D (Command D) 即可继续向下同时选中下一个相同的文本进行同时编辑；
- 鼠标选中文本，按下 Alt F3 (Win) 或 Ctrl Command G(Mac) 即可一次性选择全部的相同文本进行同时编辑；
- Shift 鼠标右键 (Win) 或 Option 鼠标左键 (Mac) 或使用鼠标中键可以用鼠标进行竖向多行选择；
- Ctrl 鼠标左键(Win) 或 Command 鼠标左键(Mac) 可以手动选择同时要编辑的多处文本