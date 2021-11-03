

### SSH 密钥登陆服务器

#### 生成公钥

在本地终端，输入 `ssh-keygen` 命令后，一路回车就可以了。

Windows 电脑建议提前安装 [Git 客户端](https://gitforwindows.org/)，千万别用 cmd 来操作。

![](D:/Git/pengloo53.github.io/image/2021-11-02-git-auto-deploy/image-20211102174256261.png)

然后就可以在用户根目录下找到 `.ssh` 目录了，里面有一个 `id_rsa.pub` 的文件，就是本机的公钥了。

#### 复制公钥

复制的话，用下面这个命令把它拷贝一份到 D 盘根目录上。

`cat ~/.ssh/id_rsa.pub > /d/1.txt`

复制内容，贴到 `Github-Setting-SSH and GPG keys` 里面，就可以不用密码 push 代码了。

如果把它放到服务器上，就可以不用密码登陆服务器了，怎么把公钥上传到服务器上？很简单，只需要下面这条命令就可以了。

`ssh-copy-id -i ~/.ssh/id_rsa.pub root@xxx.xxx.xxx.xxx`

原理就是在把公钥内容，复制到了服务器上 root 用户 `.ssh` 目录下的  `authorized_keys` 文件里。

好了，可以尝试 `ssh root@xxx.xxx.xxx.xxx` 登陆服务器了。

### Git 分支基础




### Git 裸库（服务端中心库）

裸仓库一般情况下是作为远端的中心仓库而存在的，它不包含**工作区**，不能在这个目录下执行 Git 命令。其他非裸仓库 可以 push 代码到裸仓库，可以从裸仓库 pull 代码到本地。

#### 创建裸库
生成一个裸仓库。

```
cd /home/repo
git init --bare xxx.git
```

以上命令会在 `/home/repo` 目录下生成 `xxx.git` 的目录，即为裸仓库。

####  切换裸库的分支
在裸库中执行 `git checkout <branch-name>` 会报错如下：

```
fatal: this operation must be run in a work tree
```

因为裸库是不存在工作区的，可使用命令 `git symbolic-ref HEAD refs/heads/<branch-name>` 替代。


#### 与其他远程仓库镜像

`git push --mirror http://github.com/pengloo53/xxx`


### Git 钩子自动化部署

然后编辑 `xxx.git` 目录下的 `hooks/post-receive`  文件，该文件的作用：在提交代码之后，`git` 用户会自动执行里面的脚本，实现自动化部署。

```bash
git --work-tree=/home/www/xxx --git-dir=/home/repo/xxx.git checkout -f
```

#### 部署静态页面

```bash
# 指定我的代码检出目录（服务访问目录）
DIR=/home/www/xxx
git --work-tree=${DIR} clean -fd
# 直接强制检出
git --work-tree=${DIR} checkout --force
```

```bash
#!/bin/sh
unset GIT_DIR  #很关键
NowPath=`pwd`
DeployPath="/usr/local/nginx/html/student" #存放项目的文件夹位置
cd $DeployPath
git add . -A && git stash
git pull origin master
composer install --ignore-platform-reqs
cd $NowPath
echo "同步完成"
exit 0
```

#### 部署 Jekyll 服务

```bash
#!/bin/bash
unset GIT_DIR
GIT_REPO=/home/repo/xxx.git
TMP_GIT_CLONE=/home/git/tmp/blog-site
PUBLIC_WWW=/home/www/xxx
git clone $GIT_REPO $TMP_GIT_CLONE
cd $TMP_GIT_CLONE
bundle install
bundle exec jekyll build -s $TMP_GIT_CLONE -d $PUBLIC_WWW -q
rm -rf $TMP_GIT_CLONE
exit
```


### Git 本地仓库

#### 初始化操作

```bash
git init #初始化
git remote add origin root@xxx.xxx.xxx.xxx:/www/repo/xxx.git #关联远程仓库
```

#### 分支查看与创建

`git branch` 查看本地分支
`git branch -r` 查看远程分支
`git branch -a` 查看所有分支
`git branch xxx` 创建分支 xxx

`git checkout xxx` 切换到分支 xxx
`git checkout -b xxx` 创建分支 xxx，并切换到 xxx 上（基于当前 HEAD 指向的分支，创建新分支）

`git checkout -b new-branch existing-branch`  基于 existing-branch 创建 new-branch，new-branch 和 existing-branch 可以是远程分支，例如：origin/xxx

`git fetch --all` 拉取所有远程分支

#### 分支删除与更改

`git branch -d xxx`
`git branch -D xxx`
`git branch -m <new-branch-name>` 将当前分支重命名


#### 删除远程分支

`git push origin --delete branch_name` 
`git push origin :branch_name`



### 注意事项

1. git repo 建议创建单独的 git 用户来操作，repo 的用户权限改为 git
2. 本地仓库想要顺利（无需输入密码） push 到服务端的 git 仓库，建议[配置 ssh 的密钥登陆]()

