---
title: Vim插件入门
date: 2014-10-19 19:39:20
tags: [tool,vim]
---

之前一直觉得vim插件好复杂的，太多了，也不知道装哪些好，怎么使用，在这里我简单整理了一下vim的一些常用的插件安装及基本使用，给自己做一个备份，同时给大家做一个参考。

### 1、SuperTab
SuperTab使键入Tab键时具有上下文提示及补全功能。如下图    
![SuperTab_1](/image/tools/SuperTab_1.png)

这个插件的安装比较简单，按以下步骤：

1. 下载插件，进官网下载，请点击[这里](http://www.vim.org/scripts/script.php?script_id=1643)
2. 用Vim打开下载的supertab.vmb文件，比如我下载到了Home目录下Downloads文件夹里，于是使用命令`vim ~/Downloads/supertab.vmb`打开文件
3. 打开后，直接敲命令`:UseVimball ~/.vim`，出现下图所示  
    ![SuperTab_2](/image/tools/SuperTab_2.png)
4. 打开vim配置文件，`vim ~/.vimrc`，在最后加上一行内容
    > let g:SuperTabDefaultCompletionType="context"
5. OK了，可以拿tab键去尝试了

### 2、[Vundle](https://github.com/gmarik/Vundle.vim)
插件管理器，方便安装Vim众多插件。   
![](/image/tools/Vundle_1.png)

插件管理器的安装方法官方README文档都有，建议直接看文档安装，点击标题进入。鉴于是英文的，这里还是简单的说明一下安装步骤：

1. 下载Vundle

		mkdir -p ~/.vim/bundle
		cd ~/.vim/bundle
		git clone https://github.com/gmarik/Vundle.vim.git
没有.vim目录的先创建目录，git下载克隆插件到.vim的bundle目录下  
![Vundle_2](/image/tools/Vundle_2.png)

2. 配置各种插件，根据说明文档，敲入下面代码到`.vimrc`配置文件中就OK了  
    ![Vundle_3](/image/tools/Vundle_3.png)  
其实官方说明中，默认给装了很多插件，它是为了教你如何装各种来源的插件，故代码有些长，如下图。  

    ![Vundle_4](/image/tools/Vundle_4.png)

这样Vundle这个插件管理器就安装完了。下面紧接着介绍如何用它来安装插件。


### 3、[Syntastic](https://github.com/scrooloose/syntastic)
根据名字，也能大概猜出它是一个语法检测的插件，变量类型写错了、句末分号忘加了(针对需要加分号的语言)等等语法错误都能自动检测出来。如下图所示。   
![Syntastic_1](/image/tools/Syntastic_1.png)

这个插件代码是托管在Github上的，点击上面标题进入github地址，按照它的安装说明是需要装一个叫[pathogen](https://github.com/tpope/vim-pathogen)的插件管理器。但是这里我不这么装，因为个人觉得Vundle更加方便，再说插件管理器之前已经装过了，先入为主嘛。有了插件管理器，下面安装就简单了。

1. 在`.vimrc`文件中加入一句
    > Plugin 'scrooloose/syntastic'    

	![Syntastic_3](/image/tools/Syntastic_3.png)
	
2. 保存退出，进入vim，输入`:PluginInstall`，等待下载安装完成
	![Syntastic_4](/image/tools/Syntastic_4.png)

OK，安装插件就是这么简单，保存文件时就会出现错误语法提示，如图有3处错误，光标定位到哪行，下面就显示该行错误提示。  
![Syntastic_2](/image/tools/Syntastic_2.png)

### 4、[Auto Pairs](https://github.com/jiangmiao/auto-pairs)
一款简单的括号匹配插件。一件让程序员们抓狂的事是：我是不是少加了最后一个括号？为了处理这个问题，Auto Pairs 这个插件会自动插入和格式化方括号和圆括号。

有了上面的插件管理器Vundle，安装这个插件那就太简单了。将下面这行代码加入.vimrc文件中。
> Plugin 'jiangmiao/auto-pairs'

如图所示  
![Auto-pairs_1](/image/tools/Auto-pairs_1.png)  
最后在vim界面上，运行`:PluginInstall`，等待，大功告成。使用的话，自己去实际中体验吧。也可以点击标题查看说明文档

### 5、[NERD Commenter](https://github.com/scrooloose/nerdcommenter)
如果你在找一个可以支持多种程序语言的注释代码的快捷键，你可以试试 NERD Commenter。即使你不是程序员，也非常非常推荐这款插件，因为它会让你在注释bash脚本或者其他任何东西的时候都会变得非常高效。

使用Vundle插件安装器，安装方法简单到我都不想介绍了，老规矩，在.vimrc中加入下面一句。
> Plugin 'scrooloose/nerdcommenter'

OK了，运行vim，`:PluginInstall`，真正的轻松拥有。

![nerdcommenter_1](/image/tools/nerdcommenter_1.png)    
使用很简单，但是也得看看官方Usage，不过一般只用到几个就行了，比如

- `\cc` 注释当前行
- `\cu` 撤销注释行
- `\cs` sexily注释，C语言注释效果如上，不过我没看出哪里性感了
- `\cA` 行尾注释，切换成输入模式 

别看这个插件功能比较简单，但是强大之处就是可以用相同的方法给不同的语言添加注释，比如，bash是`#`，java是`\\`等等，很方便，居家旅行必备品。

还有一个插件叫[Nerdtree](https://github.com/scrooloose/nerdtree)，从名字上可以看出它俩应该是兄弟，同一个开发者开发的。下面紧接着就介绍它。

### 6、[Nerdtree](https://github.com/scrooloose/nerdtree)
![nerdtree_1](/image/tools/nerdtree_1.png)    
它是一个文件浏览器，管理项目的时候，需要在不同的文件夹中编辑不同的文件代码，那么有了这个插件，你就方便了，不用来回的切换目录了，这样的Vim看起来有点像IDE了。

安装就不多说了，再讲就显得啰嗦了。Github地址请点击标题。它的使用可能需要介绍下，有些插件装完就能用，但是有些还是需要记点快捷键的，至少这个就不会像`SuperTab`一样简单使用。

进入vim输入`:NERDTree`默认打开当前目录，当然可以打开指定目录，如`:NERDTree /home/`打开home目录，完事后敲`?`，直接显示帮助。下面列出常用的快捷键：

1. `j`、`k`分别下、上移动光标
2. `o`或者回车打开文件或是文件夹，如果是文件的话，光标直接定位到文件中，想回到目录结构中，按住`Ctrl`，然后点两下`w`就回来了
3. `go`打开文件，但是光标不动，仍然在目录结构中
4. `i`、`s`分别是水平、垂直打开文件，就像vim命令的`:vs`、`:sp`一样
5. `gi`、`gs`水平、垂直打开文件，光标不动
6. `p`快速定位到上层目录
7. `P`快速定位到根目录
8. `K`、`J` 快速定位到同层目录第一个、最后一个节点
9. `q` 关闭

### 总结
在这里介绍了6个插件的安装及用法，可以发现一些共性

1. 下载。大部分插件都是在Github上下载的
2. 安装。通过插件管理器，安装插件的方式都是一样的
3. 用法。或看Usage，或`:h PluginName`查看帮助文档都可以了解它的用法

知道以上三点，差不多就掌握了vim插件的安装与使用了，接下来就是练习了。还有一些插件，诸如Snipmate、Tag List、undotree、gdbmgr、Ctags等，可以自行搜索学习。