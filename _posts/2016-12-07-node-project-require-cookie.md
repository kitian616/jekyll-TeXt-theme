---
title: Node项目之需求收集平台（三）- 使用cookie实现点赞功能
date: 2016-12-07
tags: [Node项目,需求收集系统]
---

又是一个临时 YY 出来想要添加的功能，需求收集平台旨在收集用户的需求，然后给出基本的答复以及更新需求状态，但是针对那些重复的需求，也就没有必要要求不同的用户重复的去提交，但是又为了让收集者知道哪些需求是用户频繁提出的，这样就要求有个类似于点赞的功能，如果看到相同的需求，不需要重新添加一条需求，只需要在该需求上点个赞即可。

首先从用户的角度简单分析一下这个功能：

- 需要给每个需求条目添加点赞按钮来触发点赞动作
- 点过赞的条目与没有点过赞的条目样式要不一样
- 不允许重复点赞
- 点赞可以取消

再来分析一下系统如何实现：

- 数据库：后台表需要有记录每个需求条目点赞数量的字段，添加完成后初始值为1，点赞+1，取消点赞-1
- 重复点赞：这个问题实现的方法其实挺多，比较灵活。比较常见的一种实现方法应该是通过用户名来查重，该用户针对一个需求条目只能点赞一次，如果点过赞再次点击则为取消点赞。

但是这个项目刚开始并没有考虑设计用户登陆功能，因为需求收集可能就是一个开放的平台，在公司内网环境下，都可以通过需求收集平台来提交用户的问题或者建议，并不需要登陆。于是这里我想到是否可以通过 cookie 的方式来实现这个功能，正好最近学习 jQuery 看到 cookie 那块。

大体思路：

页面加载后，检查需求条目是否有对应的 cookie，如果没有即没有点过赞，设置样式 A；如果找到对应的 cookie，证明已经点过赞，设置样式 B。点击动作同理，同样是判断是否有对应条目的 cookie，有的话，点击即为-1；没有的话，点击即为+1。

想到就动手实践了，首先下载carhartl/jquery-cookie插件，并在项目中引入以备后用。

前台样式如下动画：

![](/image/node/GIF.gif)

第一次点赞+1，背景变成浅红色；再次点赞-1，样式恢复；并且点赞后，刷新页面后依然是点赞状态。

接下来看看js是怎么实现的：

```js
/* 已经赞过的message 样式设置，防止刷新页面后样式恢复原样 */
$('.message-list-item').each(function () {
    var mid = $(this).attr('mid');
    var cookie = $.cookie('haveUp' + mid);
    if (cookie && cookie == 2) { // 2代表赞过，1代表没有赞过
        $(this).find('div.up').addClass('up-yes'); // up-yes为红色背景样式
    }
});
/* up a message 赞一个需求 */
$('.qa-rank .up').click(function () {
    var messageId = $(this).attr('data-messageId');
    var $plus = $('<span id="plus"><strong>+1</strong></span>');
    var $minus = $('<span id="minus"><strong>-1</strong></span>');
    var $this = $(this);
    var bool = $.cookie('haveUp' + messageId); // 是否Up
    if (!bool || bool == 1) { // 赞一个需求
        $plus.insertAfter($this).css({
            'position': 'relative',
            'z-index': '1',
            'color': '#C30'
        }).animate({
            top: -30 + 'px',
            left: +30 + 'px'
        }, 'slow', function () {
            $(this).fadeIn('slow').remove();
        });
        $.ajax({
            url: '/ajax/up/' + messageId,
            method: 'POST',
            global: false,
            success: function (result) {
                $this.addClass('up-yes');
                $.cookie('haveUp' + messageId, 2, { path: '/', expires: 1 });
            }
        });
        return false;
    } else {
        $minus.insertAfter($this).css({  // 取消赞
            'position': 'relative',
            'z-index': '1',
            'color': '#5cb85c'
        }).animate({
            top: -30 + 'px',
            left: +30 + 'px'
        }, 'slow', function () {
            $(this).fadeIn('slow').remove();
        });
        $.ajax({
            url: '/ajax/cancel/' + messageId,
            method: 'POST',
            global: false,
            success: function (result) {
                $this.removeClass('up-yes');
                $.cookie('haveUp' + messageId, 1, { path: '/' });
            }
        });
        return false;
    }
});
```

代码逻辑很简单，主要就是判断是否点赞，如果点赞了，那么创建 id 为 minus 的 span 节点插入到 DOM 中，然后给个动画效果；如果没有点赞，那么创建 id 为 plus 的 span 节点插入到 DOM 中，同样给个动画效果；同时，通过 ajax 异步请求数据到后台更新数据库中的点赞数量。

好了，这个小功能算是基本实现了，思路是不是对的暂不清楚，如果不对，请指正，学习就是不断尝试的过程。后面再继续介绍该项目的一些内容。