---
title: Git进阶学习笔记
date: 2015-12-14
tags: [tool,git]
---

好久好久之前，入门学习了一下`Git`，基本上可以使用`Git`在`Github`上`push`自己的代码，并且`clone`一下别人的项目。本以为这就能够满足我的日常需求了，但是随着学习以及使用的复杂性，发现那些[基础的命令](/post/tools/2014-05-16-git-common)不能再满足我的日常使用了，于是最近在看[《pro Git》](http://git-scm.com/book/zh/v2)一书， 希望能更深入的学习一下`Git`这个工具，工欲善其事，必先利其器。

### 1. `git config`配置
刚开始使用Git的时候，很多人肯定都忽略配置的相关内容。也许你还记的在第一次`push`内容的时候，总会有提示让你设置你的用户名以及邮箱。其实这就是最基本的一个配置。

`git config --list`可以查看目前git仓库中有哪些配置。那么另外一个问题来了，Linux中有个观点是：『一切皆文件』，这些配置从哪里来的？共有3个地方设置，分别是系统级的、用户级的以及仓库级的。

1. `/etc/gitconfig` 系统级的配置，系统中所有的`git`仓库都普遍适用的配置。使用`git config --system`来写入；如：  
`git config --system user.name pengloo53`

	> 注意：配置文件不一定是`/etc/gitconfig`，去你的git安装的目录下去找。怎么找？Linux和Mac用户可以使用`which git`找到`git`的所在地，然后就好找了，比如我的`git`的系统配置文件在`/usr/local/etc/gitconfig`下。我的Mac是通过`homebrew`安装的`git`。如果是Windows系统，去安装目录下找就OK了。

2. `~/.gitconifg`用户级的配置，该用户下所有的`git`仓库都普遍适用的配置。使用`git config --globe`来写入；如：  
`git config --globe user.email aa@xxx.com`

3. `.git/config`仓库级的配置，只针对某个仓库而言的配置。在仓库目录下直接`git config`来写入配置。

如果我很无聊，分别使用`git config --system user.name name_1`、`git config --globe user.name name_2`以及`git config user.name name_3`设置了我的用户名，那么推送消息的时候到底记录的是哪个呢？这里不用担心，`git`会一级一级覆盖的，也就是会显示最小范围的那个级别。如果有相同的配置存在，最终会显示仓库级的那个配置。

 
### 2. `git commit`提交
这个命令可真是熟悉得不能再熟悉了，每次`git add --all`都要习惯性的`git commit -m "message"`一下，`message`是一定要写的，Git默认也是要你写的，这是个好习惯，一定要坚持下去。反正我都习惯了，不写`-m`都觉得奇怪了。

当然保留好的习惯的时候，该偷懒也得偷懒一下。可以尝试`git commit -a -m "message"`将`git add --all`和`git commit -m 'message"`合并一块写，我相信你会更爽的。

如果一不小心，`message`写成了`meaasge`，虽然不是啥大事，但是对于一个『强迫症』来说，那叫个难受。没事，试一下`git  commit --amend`修改提交文本。

再如果`commit`之前忘了`add`某些文件了，想一块`commit`进来，没关系。

```
git add --all
git commit --amend 
```

这样就不用产生两条提交信息了。

### 3. `git show`查看
在入门的[常用命令](http://lupeng.me/2014/05/16/Git%E5%B8%B8%E7%94%A8%E5%91%BD%E4%BB%A4.html)的笔记里，通过`git show v1.0`可以查看标签信息，然而它还可以查看每次的提交信息，可能这并不是什么进阶知识，但是对我来说还是比较新鲜的，就记在这里吧。

当你想查看之前某个的提交改了些啥玩意的时候，那么`git show`就大有用处了。先通过`git log --graph --pretty=oneline --abbrev-commit`查看一下自己的分支信息，然后`git show commitID`就可以了。当然你不用写下全部的`commitID`，写前4位就行了。

如：`git show 0fce`，就可以查看`0fce*`那次都提交了哪些内容。

### 4. `git remote`远程
在之前，这个命令我基本上没有怎么使用过，因为创建的仓库，除了第一次使用关联到`github`上之后，就再也没咋用了。把本地的仓库上传至远程，基本上会用到如下两条命令。

```
git remote add origin https://github.com/XXX/xxx.git
git push -u origin
```

如果是你一个人玩`Git`，那么上面两条命令真的够了，很长一段时间我就是一个人玩。但是使用`Git`版本控制器就注定了你不能再一个人玩下去了，协同处理项目才是版本控制器的真正意义。

`git remote -v`查看一下远程的仓库，通常情况下，你会看到一个`origin`这个远程仓库，它是默认的远程仓库。基本上就是你第一次`push` or `clone`的那个仓库。

> 情景

假设你在`github`上`fork`了一个别人的项目，并且`clone`下到本地，而且玩了老长时间了，也许你还`pull request`一下别人的项目，运气好，别人还`merge`了你的`pull request`，然后过了好长时间，你都放弃了这个项目，然而别人还在一直更新，你想看看最新的项目状态。那么你可以通过下面几条命令，再把别人仓库的内容抓取下来。

```
git reomte add others https://github.com/xxx/xxx.git
git fetch others master
git checkout -b tmp others/master
```

1. 首先添加一下别人的远程仓库，命名为`others`；
2. 然而使用`git fetch`命令抓取一下远程仓库的内容，通过`git branch -r`，你就可以看到`others/master`远程分支了；
3. 再在本地创建一个`tmp`分支跟踪远程`others/master`分支。
4. 最后`tmp`的处理权就交到你的手里了，删除它或者`merge`到本地`master`上就看你的心情了。
5. 最最后别忘了，再把它推送到你的远程分支上`origin/master`，使用`git push origin master`就行了。


### 5. `gitk`图形界面

要是让我记那么多`git log`的参数实在是太让人为难了，基本上是用的时候现查命令参数，自从知道了`gitk`，现在连`git log`都快忘了，这个图形工具真是太方便了。一目了然各个分支都哪个地方。纵使我再崇拜命令行，我还是为了它妥协了。

![](/image/tools/gitk02.png)

- Windows上就不用安装了，安装完[git for windows](http://git-scm.com/download/win)就可以直接使用了。
- Mac上如果无法使用`gitk`命令启动图形界面，赶紧[点击这里安装](http://stackoverflow.com/questions/17582685/install-gitk-on-ma)，不要再犹豫。
- Linux，啊，能把Linux当桌面系统使用的用户都是高手，我这里就不班门弄斧了。


> 以上是这段时间对Git学习的一些内容，当作是Git学习的一个进阶阶段吧。Git博大精深，想深入了解还需更多的学习。当然它毕竟只是一个工具，工具只有在使用的时候才能体会到它是否满足自己的需求，期待下一次进阶学习。