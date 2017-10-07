---
layout: post
title: 用 HTML5 实现星战的开篇字幕
key: 10007
tags: FrontEnd
category: blog
date: 2016-02-17 21:30:00 +08:00
---

![the-force-awake](https://wx3.sinaimg.cn/large/73bd9e13ly1fjldo8obm7j21kw16oaj8.jpg)

去年慕名去电影院看了星战的最新作[《原力觉醒》](https://movie.douban.com/subject/20326665/)。故事简单，不过里面的场景和人物（非常喜欢汉·索洛，可惜了）特别有趣。光剑的吱吱声，武士的服饰，海岛上的拜师（卢克终于出现了）……和中土世界（You walk a lonely road, Oh! How far you are from home...）不同，星战世界处处展现出了一种神秘的东方哲学。

看完了电影后，我就把星战的整个系列看了一遍。星战，在每部电影的开头都会有一段经典的字幕动画，黄色的文字呈梯形向前方流动，渐渐消失，背景则是漆黑的宇宙星空。简单，但令人印象深刻。

<!--more-->

昨天下午在查找资料的时候，无意中看到了一篇[CSS3 3D 转换的介绍](http://www.w3school.com.cn/css3/css3_3dtransform.asp)，胡乱看到了这张图：

![css3_3dtransform](https://wx1.sinaimg.cn/large/73bd9e13ly1fjldo82ostj207c06wglf.jpg)

等等，这不是星战的字幕吗？于是抱着好玩的心态，写出了[这个 H5 动画](/blog/projects/star-war.html)。

## 准备工作

- 文本编辑器
- 支持 H5 的浏览器
- JQuery
- 乐事薯片

首先，我们来构建一个跟浏览器窗口大小一样的黑色画布 `div.paper`。

{% highlight html %}
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <script src="https://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
    <div class="paper">
    </div>
</body>
</html>
{% endhighlight %}

充满整个窗口……

{% highlight css %}
.paper {
    height: 100%;
    width: 100%;
    background-color: black;
}
{% endhighlight %}

查看一下效果，整个界面惨白一片……

![惨白的界面](https://wx3.sinaimg.cn/large/73bd9e13ly1fjldo7inddj20l10hp74p.jpg)

![AreYouKiddingMe](https://wx3.sinaimg.cn/large/73bd9e13ly1fjldgnth3cj206404ja9y.jpg)

查看界面元素可以发现，此时 `div.paper` 的 hight 为 0，并没有像我们写的 `height: 100%` 那样等于窗口的高度。为什么呢？

我们知道这里的 100% 指的是相对父级元素高度的 100%，`div.paper` 的父级是 `body`，而此时 `body` 的高度也为 0，于是我们加上

{% highlight css %}
body {
    height: 100%;
}
{% endhighlight %}

查看一下效果，整个界面依旧惨白一片……

![fuuuuuuuu](https://wx1.sinaimg.cn/large/73bd9e13ly1fjldgotoe6j208706oglt.jpg)

查看界面元素可以发现，此时 `body` 的高度依然为 0，再来看上面的 HTML 代码，原来 `body` 上面还有 `html` 元素，于是我们加上：

{% highlight html %}
html {
   height: 100%;
}
{% endhighlight %}

啧啧，大功造成。然后去除掉 `body` 的内边距（padding），再来看一下效果。

![完美的画布](https://wx3.sinaimg.cn/large/73bd9e13ly1fjldo77qxgj20ki0e2q37.jpg)

PERFECT

![完美](https://wx3.sinaimg.cn/large/73bd9e13ly1fjldgn3ycej208y08cwez.jpg)

## 类容文字元素

我们加上一个 `div#content` 元素作为文字层。

{% highlight html %}
<body>
    <div class="paper">
        <div id="content">
            <p>A long time ago, in a galaxy far, far away...</p>
            <br><br>
            <p>Episode IV</p>
            <p>A NEW HOPE</p>
            <p>It is a period of civil war. Rebel spaceships, striking from a hidden base, have won their first victory against the evil Galactic Empire.</p>
            <p>During the battle, Rebel spies managed to steal secret plans to the Empire's ultimate weapon,the DEATH STAR,an armored space station with enough power to destroy an entire planet.</p>
            <p>Pursued by the Empire's sinister agents,Princess Leia races home aboard her starship,custodian of the stolen plans that can save her people and restore freedom galaxy...</p>
        </div>
    </div>
</body>
{% endhighlight %}

设置字体、字号、颜色和文字居中。

{% highlight css %}
body {
    height: 100%;
    padding: 0;
    margin: 0;
    font-family: "lucida grande", "lucida sans unicode", lucida, helvetica, "Hiragino Sans GB", "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif;
    color: #ffda00;
    font-size: 4em;
    text-align: center;
}
{% endhighlight %}

来看看效果。

![带白框的文字](https://wx2.sinaimg.cn/large/73bd9e13ly1fjldo6rdlwj20ki0e2q3h.jpg)

噗……怎么上面出现了一个白框啊。

![我需要冷静](https://wx1.sinaimg.cn/large/73bd9e13ly1fjldgmnkamj20c809574j.jpg)

冷静下来，查看元素，原来上面之所以会出现一个白框，是因为“A long time ago”这一个段落 `p` 的外边距（margin）穿过了 `div.paper`，到了 `div.paper` 的上方, 这种规则称为**[外边距叠加](http://www.cnblogs.com/snowinmay/archive/2013/04/28/3048997.html)**，由于外边距是透明的，因此它呈现出了父元素 `body` 的白色。

解决方法很简单。

1. 设置 `div.paper` 内边框，使得子元素的外边距（margin）无法穿透。

2. 设置 `body` 的背景色。

这里，我使用方法二。

### 添加滚动条

设置 `content` 的样式、高度、宽度、居中当然还有滚动条。

{% highlight css %}
#content {
    box-sizing: border-box;
    margin: 0 auto;
    width: 80%;
    height: 100%;
    overflow: scroll;
}
{% endhighlight %}

看看效果。

![平面初步效果](https://wx4.sinaimg.cn/large/73bd9e13ly1fjldo64ekqj211y0ijjrn.jpg)

哎哟，不错哦。

![good](https://wx4.sinaimg.cn/large/73bd9e13ly1fjldvedp06j207p0693zd.jpg)

## CSS3的3D转换

最关键的部分到了，根据 w3school 的[这个示例](http://www.w3school.com.cn/tiy/t.asp?f=css3_perspective1)，实现星战那样的 3D 字幕涉及到 perspective 和 rotateX 属性。

照葫芦画瓢, 我们给 `body` 和 `div.paper` 添加 3D 变换：

{% highlight css %}
body {
    /*省略*/
    perspective: 500;
    -webkit-perspective: 500;
}
{% endhighlight %}

{% highlight css %}
.paper {
    /*省略*/
    transform: rotateX(60deg);
    -webkit-transform: rotateX(60deg);
}
{% endhighlight %}

看看效果。

![3D变换1](https://wx4.sinaimg.cn/large/73bd9e13ly1fjldo5mkrsj20kv0e3wf3.jpg)

good。

## jQuery动画

下面我们来一起加上动画，让字幕从开始滚动到结尾。

虽然 CSS3 有动画的功能，但是我还是选择用 jQuery 来做动画。（懒……）

jQuery 动画是用 `animate` 函数来完成的。只需要给出动画结束后**某属性**的值和动画时间（非必需）等参数，即可创建针对该参数的动画，参考[w3school 关于 JQuery 动画的介绍](http://www.w3school.com.cn/jquery/jquery_animate.asp)。

这里涉及到两个属性，[scrollTop](https://developer.mozilla.org/en-US/docs/Web/API/Element/scrollTop)和[scrollHeight](https://developer.mozilla.org/en-US/docs/Web/API/Element/scrollHeight)。

scrollTop 可以控制滚动条的滚动高度，scrollHeight 可以得到滚动内容的实际高度。具体可以参考上面的链接，讲的很详细。

知道了 `animate` 函数和必要时属性后，很快的写下了代码。`scrollTop` 属性从开始的 0，经过 `scrollHeight * 20` 毫秒，匀速的变为了 `scrollHeight`。

{% highlight js %}
$(document).ready(function(){
    var scrollHeight = $("#content")[0].scrollHeight;
    var scrollTop = $("#content")[0].scrollTop;
    $("#content").animate({scrollTop: scrollHeight}, scrollHeight * 20, "linear");
}());
{% endhighlight %}

最后，隐藏滚动条。

{% highlight css %}
#content {
    /*省略*/
    overflow: hidden;
}
{% endhighlight %}

![3D变换2](https://wx3.sinaimg.cn/large/73bd9e13ly1fjldo56n7jj211y0ijtc8.jpg)

嗯，大功告成。不过还是有些问题，一开始应该是没有文字的。我们可以在 `div#content` 开头加上一个空白区。

{% highlight html %}
<div class="content">
    <div class="empty-content top"></div>
    <p>A long time ago, in a galaxy far, far away...</p>
    ...
{% endhighlight %}

{% highlight css %}
.empty-content {
    box-sizing: border-box;
    width: 100%;
    height: 100%;
}
{% endhighlight %}

这样就差不多了。当然我们还可以加图片背景，加背景音乐。这里就不做了……

[动画演示](/blog/projects/star-war.html) |
[源码](https://github.com/kitian616/blog/blob/gh-pages/projects/star-war.html)

![Yoda](https://wx1.sinaimg.cn/large/73bd9e13ly1fjldo4fv1nj20b404qglq.jpg)