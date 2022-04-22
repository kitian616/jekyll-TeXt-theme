---
title: "GIT批量删除远程分支"
tags: []
---
## 删除本地分支 
    
    git branch -D <branch_name>

## 删除远程分支
        
        git push origin :<branch_name>
        
## 批量删除本地分支

    git branch -a | grep 'pr-' | xargs git branch -D


 ```git branch -a```表示列出本地所有分支，```grep ‘pr-’```表示正则匹配本地所有分支中分支名有'pr-'的分支，然后将以上匹配结果作为参数传给```git branch -D```,执行删除本地分支命令，‘|’相当于一个管道符，将上一段的结果传给下一段

 有时候分支命名上没什么规则，只想保留某几个正在开发中的分支，删除所有其他的分支，就可以用下面命令：

    git branch -a | grep -v -E 'A|B' | xargs git branch -D

 ## 批量删除远程分支 

    git branch -r | grep  'pr-' | sed 's/origin\///g' | xargs -I {} git push origin :{}

```git branch -r```  查看远程分支
| ```sed ‘s/origin///g‘```  去掉origin(能够把接受到的分支都过滤掉开头的origin/得到实际的分支名)
```-I {} ``` 使用占位符来构造后面的命令
```git push origin :branchName```  删除远程分支

