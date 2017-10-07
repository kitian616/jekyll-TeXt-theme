---
layout: post
title: 使用命令来编译、运行、打包 Java 程序（java, javac, jar）
key: 10005
tags: Java Linux bash
category: blog
date: 2015-11-10 23:03:00 +08:00
---

前一阵子需要把一个原本在 Windows 环境下的 Java Eclipse 项目放到 Linux 环境下测试，由于不想在 Linux 虚拟机中装 Eclipse，于是索性打算用命令来运行测试该项目。结果花了半小时，终于把项目跑起来了。 :sweat: :sweat: :sweat:
<!--more-->

事后实验总结了一下，于是有了这篇博文。

## 带 package 包名 Java 程序的编译和运行

首先我们来新建一个测试类 HelloJava。

{% highlight java %}
package com.thomastian.test;
public class HelloJava
{
    public static void main(String args[])
    {
        System.out.println("Hello, Java");
    }
}

{% endhighlight %}
直接编译（不推荐）

{% highlight console %}
$ javac HelloJava.java
{% endhighlight %}

带目录结构的编译

{% highlight console %}
$ javac -d . HelloJava.java
{% endhighlight %}

![带选项的 javac](https://ww2.sinaimg.cn/large/73bd9e13jw1exx0q5nnytj207402dt8m.jpg)

加上了 -d 选项后在当前目录下建立了对应包层次的目录结构（如上图）。如果没有加 -d，则在当前目录下生成 class 文件，需手动建立目录结构。

在运行时，需要加上主类对应的目录层次，可以采用包形式“.”，或者目录的形式“/”，这样 JVM 就会根据目录层次来查找对应的 class 文件并执行。**执行目录必须是包顶级层次的上级目录**（在这里就是 com 的上级目录“~”了）。

{% highlight console %}
$ java com.thomastian.test.HelloJava
$ java com/thomastian/test/HelloJava
{% endhighlight %}

## Java的归档（jar）

Java 使用 jar 命令打包（创建归档文件）。（jar 命令的选项跟 tar 命令是相同的，毕竟都是打包归档类的命令。关于 tar 命令可以看[这篇博客园文章](http://www.cnblogs.com/kitian616/p/4522456.html)）

这里前面的“HelloJava.jar”对应包的名称，后面的“com”对应的包的根目录。

{% highlight console %}
$ jar cvf HelloJava.jar com
{% endhighlight %}

运行 jar：

{% highlight console %}
$ java -jar HelloJava.jar
{% endhighlight %}

此时会提示错误：

![错误提示](https://ww1.sinaimg.cn/large/73bd9e13jw1exwbudlmsoj20bn014q2t.jpg)

用解压软件打开 jar 包文件，可以看到里面有一个 MAINFEST.MF 文件。

![MAINFEST.MF 文件](https://ww3.sinaimg.cn/large/73bd9e13jw1exwbx4yt7jj20cy02hjr9.jpg)

    Manifest-Version: 1.0
    Created-By: 1.8.0_65 (Oracle Corporation)

这是一个在归档时自动生成的文件，它是 JAR 档案文件中的特殊文件，用来定义扩展或档案打包相关数据。

如果 JAR 文件被用作可执行的应用程序，那么应用程序需要告诉 Java 虚拟机入口点位置，任意包含 `public static void main(String[] args)` 方法的类即为入口点。该信息由文件头 Main-Class 提供，基本格式如下：

    Main-Class: classname

以上内容摘自[维基百科条目：Manifest 资源配置文件](https://zh.wikipedia.org/wiki/Manifest%E8%B5%84%E6%BA%90%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6)，详细讲解了 Manifest 文件的用法以及相关资料。

接下来我们创建一个文本文件 Mainfest（随便什么名字都行），在文件中添加 Main-Class 值，注意冒号后面有空格，不然会报错，然后保存。

    Manifest-Version: 1.0
    Created-By: 1.8.0_65 (Oracle Corporation)
    Main-Class: com.thomastian.test.HelloJava

在 jar 命令加上 f 选项，即可指定自定义的 Mainfest 文件，在包名的后面加上刚才创建的文件名“mainfest”。在运行完命令时，mainfest 文件的内容就会赋给 jar 包中的 MAINFEST.MF 文件，完成了 MAINFEST 文件的自定义。

{% highlight console %}
$ jar cvfm HelloJava.jar mainfest com
{% endhighlight %}

![带 f 选项的 jar 命令](https://ww2.sinaimg.cn/large/73bd9e13jw1exwbuehk88j20km05j754.jpg)

生成 jar 包后，再执行 `java -jar HelloJava.jar` 命令即可成功运行。在设定了 jar 的文件关联后甚至可以双击运行，十分方便。

上面说过，被用作可执行的应用程序的 jar 包才需要指定 Main-Class，有些包是供其它程序调用的类库，并没有 main 方法，下文的 ImportClass 类就是这样。

## jar包的引用

上文讲的是如何创建 jar 包，接下来我们来看看包的引用。

首先我们创建一个 ImportClass 类，用于引用。注意待引用的类必须为 public 类型，否则对外是不可见的，这样也就无法引用了。

{% highlight java %}
package blog.thomastian.test;
public class ImportClass{
    public void print()
    {
        System.out.println("oh, my import class!");
    }
}
{% endhighlight %}

按照上文的方法将其编译为 jar 包。由于该文件没有 main 方法，所以也就无需指定 Main-Class 了。

修改前面的 HelloJava 类，引用并调用上面的 ImportClass 类。

{% highlight java %}
package com.thomastian.test;
import blog.thomastian.test.ImportClass;
public class HelloJava
{
    public static void main(String args[])
    {
        ImportClass ic = new ImportClass();
        ic.print();
    }
}
{% endhighlight %}

编译：

{% highlight console %}
$ javac -d . -cp lib/ImportClass.jar  HelloJava.java
{% endhighlight %}

运行：

{% highlight console %}
$ java -cp .:lib/ImportClass.jar com.thomastian.test.HelloJava
{% endhighlight %}

![运行结果](https://ww1.sinaimg.cn/large/73bd9e13jw1exwbufbia7j20eg012t8k.jpg)

多个路径之间用“:”（Linux） / “;”（Windows）隔开（跟系统环境变量的写法相同）。在编译的时候 -cp 路径可以不包含当前路劲，但是在运行的时候必须要加上代表当前路径的“.”，具体可参考[这篇stackoverflow问答](https://stackoverflow.com/questions/11459664/how-to-add-multiple-jar-files-in-classpath-in-linux?newreg=4df9dca55f4a4557a705a3fd90625f28)。