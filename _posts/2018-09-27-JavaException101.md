---
title: "Java Exception 101"
date: 2018-09-27 16:18:32 +0800
tags: Java Exception
key: 2018-09-27-JavaException101
---

# Overview
The most fantastic usage of exception in Java I saw around 3 years ago is, a guy throws Exception in an inner loop in order to exit the outer loop.

Exception is fundamental knowledge in Java. However, someone analyzed half a million Java projects in GitHub, the result shows the usage is not good.\[[1][Swallowed Exceptions: The Silent Killer of Java Applications]\]

![What do developers do in exception catch blocks?](https://384uqqh5pka2ma24ild282mv-wpengine.netdna-ssl.com/wp-content/uploads/2018/02/instances.png)

It's worthy to emphasize the usage of exceptions.

<!--more-->

# Kinds of Exception

[Exceptions in Java Language Specification] describes kinds of exceptions. We can see the figure to see the kinds of Exception. 

![Exceptions in JLS](/assets/Exception_in_JLS.png)

1. `Exception` is the superclass of all the exceptions from which ordinary programs may `wish to recover`.
2. `Error` is the superclass of all the exceptions from which ordinary programs are `not ordinarily expected to recover`.
3. `Checked Exception` must be handled, while `Unchecked Exception` is not.

For example, when we invoke a method which may throw `Checked Exception`, we must surround with `try/catch` or add throw declaration. 

{% highlight JAVA %}
try {
  // some routines which may throw CheckedException
} catch (CheckedException ex) {
  // must do something here
}
{% endhighlight %}

{% highlight JAVA %}
public void invoker() throws CheckedException {
  // some routines which may throw CheckedException
}
{% endhighlight %}

# Exception 101
## DON'T Swallow Exception

`DON'T Swallow Exception!`

`DON'T Swallow Exception!`

`DON'T Swallow Exception!`

This is `extremely important`. When the system goes online, finding swallowed exception is harder than looking for a needle in the haystack. 

If you are not sure how to handle the exception, re-throw it. 

## Write Informative and Insensitive Exception Message, and Keep the Cause

{% highlight JAVA %}
// Bad Case
try {
  // some routines which may throw IOException
} catch (IOException ex) {
  throw new MyException("Unknown issue.");
}
{% endhighlight %}

{% highlight JAVA %}
// Bad Case, user object might include sensitive information.
try {
  // some routines which may throw UserNotFoundException
} catch (UserNotFoundException ex) {
  throw new MyException("Can not find the user: " + user.toString(), ex);
}
{% endhighlight %}

{% highlight JAVA %}
// Good Case
try {
  // some routines which may throw IOException
} catch (IOException ex) {
  throw new MyException("Informative message here.", ex);
}
{% endhighlight %}

## Throw/Catch Specific Exception Instead of Its Superclass

**Don't Throw/Catch `Exception` and `Throwable`.**

 {% highlight JAVA %}
// Bad Case
public void useSpecificException() throws Exception {
  // some routines which may throw IOException and TimeoutException ;
}
{% endhighlight %}

 {% highlight JAVA %}
// Good Case
public void useSpecificException() throws IOException, TimeoutException { 
  // some routines which may throw IOException and TimeoutException ;
}
{% endhighlight %}

 {% highlight JAVA %}
// Bad Case, as the Exception might be RuntimeException, which should have other ways to handle.
public void useSpecificTryCatch() { 
  try {
    // some routines which may throw IOException and TimeoutException ;
  } catch (Exception ex) {
    // Do something here for Exception;
  } 
}
{% endhighlight %}

 {% highlight JAVA %}
// Good Case. This might be controversial. In real cases, developers just log each exception, therefore 
//     `try {} catch (IOException | TimeoutException ex) {}`
// might be used.
public void useSpecificTryCatch() { 
  try {
    // some routines which may throw IOException and TimeoutException ;
  } catch (IOException ex) {
    // Do something here for IOException;
  } catch (TimeoutException ex) {
    // Do something else here for TimeoutException;
  }
}
{% endhighlight %}

## Log Only When Exception is Handled
{% highlight JAVA %}
// Bad Case, because same exception might be logged many times (log here and the outer invokers),
// which messes up the log and monitoring tool.
try {
  // some routines which may throw CheckedException
} catch (CheckedException ex) {
  LOGGER.error("Informative message in log", ex);
  throw ex;
}
{% endhighlight %}

{% highlight JAVA %}
// Good Case
try {
  // some routines which may throw CheckedException
} catch (CheckedException ex) {
  LOGGER.error("Informative message in log", ex);
  // Do something to recover if needed. 
}
{% endhighlight %}

## Release Resources Finally

From Java 7, we should use `try-with-resources` for `InputStream` etc. 
{% highlight JAVA %}
try (InputStream is = new FileInputStream("file")) {
  is.read();
}
{% endhighlight %}

Prior to Java 7, or for those resources which do not implement `java.lang.AutoCloseable`, remember to release resources in `finally` block. [IOUtils.closeQuietly in Apache IOUtils] is used by many developers prior to Java 7, but it swallows `IOException` which might be harmful in many cases especially closing a writer. It's also discussed in `Google guava`, [95% of all usage of Closeable.closeQuietly is broken].

{% highlight JAVA %}
InputStream is = null;
try {
  is = new FileInputStream("file");
  is.read();
} finally {
  if (is != null) {
    is.close();
  }
}
{% endhighlight %}

# Next
Some notorious exceptions (NullPointerException, OutOfMemoryError etc.) and corresponding mitigation will be described.

# Reference

\[1\] [Swallowed Exceptions: The Silent Killer of Java Applications] 

\[2\] [Exceptions in Java Language Specification]

\[3\] [The try-with-resources Statement]

\[4\] [IOUtils.closeQuietly in Apache IOUtils]

\[5\] [95% of all usage of Closeable.closeQuietly is broken]

[Swallowed Exceptions: The Silent Killer of Java Applications]:https://blog.takipi.com/swallowed-exceptions-the-silent-killer-of-java-applications/

[Exceptions in Java Language Specification]:https://docs.oracle.com/javase/specs/jls/se7/html/jls-11.html

[The try-with-resources Statement]:https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html

[IOUtils.closeQuietly in Apache IOUtils]:https://github.com/apache/commons-io/blob/58b0f795b31482daa6bb5473a8b2c398e029f5fb/src/main/java/org/apache/commons/io/IOUtils.java#L359

[95% of all usage of Closeable.closeQuietly is broken]:https://github.com/google/guava/issues/1118