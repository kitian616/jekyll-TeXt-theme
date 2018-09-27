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

# DON'T Swallow Exception

`DON'T Swallow Exception!`

`DON'T Swallow Exception!`

`DON'T Swallow Exception!`

If you are not sure how to handle the exception, re-throw it. 

# Checked Exception and Runtime Exception
## Runnable interface

# Interrupted Exception

# Should Exception be serialized?
Throwable implements Serializable.

RMI
RPC

# Exception message
## Exception code

# try with resources
https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html

Suppressed Exception

# special exception
## OOM and stackoverflow
EXITONOOM

# NullPointerException (null)

# Swallowed Exception
#Exception in Spring
  Checked Exception
  Exception handler


  \[1\] [Swallowed Exceptions: The Silent Killer of Java Applications] 
  
  [Swallowed Exceptions: The Silent Killer of Java Applications]:https://blog.takipi.com/swallowed-exceptions-the-silent-killer-of-java-applications/

  https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
