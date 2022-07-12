---
layout: article
title: 如何使用GitHub发表博客
---

#### 步骤一：注册GitHub并安装Git

具体方法详见https://zhuanlan.zhihu.com/p/499364238

如已有GitHub账号并已安装Git，跳过此步骤。

#### 步骤二：创建markdown文件

撰写格式参照https://tianqi.name/jekyll-TeXt-theme/docs/zh/writing-posts

#### 步骤三：Pull Request

##### 方法一：使用Git及SSH免密码登录（也可以使用账户密码登录，这里用SSH举例）

###### 一、SSH配置（仅首次使用需要）

1. 在Git Bash中输入以下指令，配置用户名和邮箱。

`git config --global user.name 'your name'`

`git config --global user.email 'your email' `

2. 生成SSH密钥

`ssh-keygen -t rsa -C "your email" `

3. 查看公钥

`cat ~/.ssh/id_rsa.pub`

4. 添加SSH keys

   在GitHub的settings中，找到New SSH key，并将第3步查看的公钥复制并添加到Key中，Title自己命名

###### 二、分支并克隆

1. 进入https://github.com/HGPART/HGPART.github.io，点击Fork创建一个新的分支

2. 打开自己新创建的分支，点击Code-Clone-SSH，并点击复制

3. 克隆文件

   打开Git Bash，输入 `git clone 第2步复制内容`

4. 将md文件放入_post文件夹中

5. 在项目中打开Git Bash，输入`git add .`（可以用`git status`查看是否添加成功 ）

6. 输入`git commit -m "填写发布的内容"`

7. 输入`git push`

8. 进入https://github.com/HGPART/HGPART.github.io，点击Pull requests-New pull request

   base repository选择HGPART/HGPART.github.io，head repository选择自己的分支项目，提交request

##### 方法二：使用GitHub Desktop

###### 一、下载GitHub Desktop并登录账号

###### 二、分支并克隆

1. 进入https://github.com/HGPART/HGPART.github.io，点击Fork创建一个新的分支
2. 打开自己新创建的分支，点击Code-Open with GitHub Desktop，将项目克隆到本地
3. 将md文件放入_post文件夹中
4. 

<!--more-->