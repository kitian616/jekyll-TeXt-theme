---
title: 使用脚手架工具，创建一个 Element UI 的 Vue 项目
date: 2020-08-06
tags: [字节加工厂,记支出,Vue,ElementUI]
---

### 前端项目
初步接触 Element UI，创建第一个 test 页面

### 安装 vue-cli

```bash
npm i @vue/cli -g
```

### 安装 Element 插件

> 我们为新版的 vue-cli 准备了相应的 [Element 插件](https://github.com/ElementUI/vue-cli-plugin-element)，你可以用它们快速地搭建一个基于 Element 的项目。

地址：https://github.com/ElementUI/vue-cli-plugin-element

```bash
vue create my-app
cd my-app
```

![image-20200806222952203](./_image/image-20200806222952203.png)

```bash
vue add element
```

![image-20200806223646729](./_image/image-20200806223646729.png)

就这样，一个基于 Element UI 的 Vue 项目就准备好了。

项目目录如下所示：

![image-20200806224332462](./_image/image-20200806224332462.png)

这个项目是可以跑起来的，运行如下命令：

```
npm run serve
```

浏览器打开 `http://localhost:8080`，显示如下界面：

![image-20200806224603201](./_image/image-20200806224603201.png)

其实，即便到这里，依然不知道如何下手，因为对于新手来说，其实不适合一开始就使用`脚手架`工具来创建项目。

所以，我们还是回到最开始的手动创建的那个项目中，待时机成熟之时，再来这里。

### 总结

今天了解了使用脚手架工具，创建一个使用 Element UI 的 Vue 项目。