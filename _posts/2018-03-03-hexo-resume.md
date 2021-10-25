---
title: 在Hexo博客中加入自定义简历页
date: 2018-03-03
tags: [hexo,简历]
---

![](/image/hexo/2018-03-03-11-16-09.jpg)

很早就在博客里加入了简历页，那会很多信息是写死在了页面中，今天正好有空，就把页面重新调整里一下，将简历信息自定义化，相当于在此主题中正式加入简历功能。

### 使用方法
首要当然是将[本主题](https://github.com/pengloo53/Hexo-theme-light_cn)clone到你的Hexo博客themes目录下，并且将博客配置文件`_config.yml`中的主题改成`Hexo-theme-light_cn`，

```
theme: Hexo-theme-light_cn
```

然后新建page页，在`source`目录下新建`resume`目录，里面再新建`index.md`文件，应用`resume`这个布局，内容如下：

```
---
title: Hey, It's me
layout: resume
---

> 思想、创作、灵感都源于阅读。
...
...
```

访问该页面`https://localhost:4000/resume`，正常就能显示出来，最后修改一下该简历中左侧个人信息，这块信息，我通过`theme`主题里的变量自定义化了，所以你在`theme`中`_config.yml`中可以看到如下信息：

```
# resume
resume_photo: /assets/img/resume/resume.jpg
resume:
  name: 鲁 鹏
  birth: 1989年5月
  nation: 汉族
  political_status: 中共党员
  birth_place: 湖北省武汉市
  domicile_place: 北京市
  address: 北京市房山区北京理工大学良乡校区
  postcode: 102488
  email: decadent_prince@foxmail.com
  phone: 18511xxxxxx
```

将这些数据改成你自己的即可，同时你也可以添加其他信息。例如加入一列：`性别： 男`，简历页左侧就会显示出来，大体功能就这样，早就该把这块功能自定义化的，一直懒...

### 实现方法
在主题中新建了`resume`布局，见`resume.ejs`文件，内容如下：

```html
<%- partial('_partial/head') %>
<body>
    <header id="header">
        <%- partial('_partial/header') %>
    </header>
    <div class="resume">
        <h1 class="resume-title">
            <%- page.title %>
        </h1>
        <div class="resume-left">
            <img src="<%= theme.resume_photo %>" alt="photo">
            <% for(var i in theme.resume){ if(theme.resume[i]){%>
                <div class="clearfix"></div>
                <div class="resume-txt">
                    <div class="resume-little"><%- __(i) %></div>
                    <div class="resume-detail"><%= theme.resume[i] %></div>
                </div>
            <% }} %>
        </div>
        <div class='resume-content'>
            <div class="resume-entry">
                <%- page.content %>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>
    <footer id="footer">
        <%- partial('_partial/footer') %>
    </footer>
    <%- partial('_partial/after_footer') %>
```

该布局分为两个部分，`resume-left`部分为可自定义部分，`resume-content`为简历主体内容，该部分可以自由发挥，写工作经历等等。

- [Demo](https://pengloo53.coding.me/about)
- [Hexo主题](https://github.com/pengloo53/Hexo-theme-light_cn)