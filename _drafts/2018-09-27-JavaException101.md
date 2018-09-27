---
title: "Java Exception 101"
date: 2018-09-27 16:18:32 +0800
tags: Java Exception
key: 2018-09-27-JavaException101
---

# Overview
The most fantastic usage of Exception in Java I saw around 3 years ago is, a guy throws Exception in a inner loop in order to exit the outer loop.

Exception is fundamental knowledge in Java, but someone analyzed half a million Java projects in Github, the result is not so good.\[[1][Swallowed Exceptions: The Silent Killer of Java Applications]\]

![What do developers do in exception catch blocks?](https://384uqqh5pka2ma24ild282mv-wpengine.netdna-ssl.com/wp-content/uploads/2018/02/instances.png)

It's worthy to emphasize the usage of Exceptions.

# Kinds of Exception

[Exceptions in Java Language Specification] describes kinds of Exceptions. 

![Exceptions in JLS](/assets/Exception_in_JLS.png)

# DON'T Swallow Exception

`DON'T Swallow Exception!`

`DON'T Swallow Exception!`

`DON'T Swallow Exception!`

If you are not sure how to handle the exception, re-throw it. 

# Exception message
## Exception code

# NullPointerException (null)

# try with resources
https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html




\[1\] [Swallowed Exceptions: The Silent Killer of Java Applications] 
\[2\] [Exceptions in Java Language Specification]
[Swallowed Exceptions: The Silent Killer of Java Applications]:https://blog.takipi.com/swallowed-exceptions-the-silent-killer-of-java-applications/

[Exceptions in Java Language Specification]:https://docs.oracle.com/javase/specs/jls/se7/html/jls-11.html

  https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
