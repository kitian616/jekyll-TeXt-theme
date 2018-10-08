---
title: "Java Exception 201: NullPointerException and Its Mitigations"
date: 2018-09-28 16:18:32 +0800
tags: Java Exception NullPointerException
key: 2018-09-28-JavaException201
---

# What is NullPointerException (NPE)
`null` is a reserved value for indicating that the pointer does not refer to a valid object. In Java, access of `null` will trigger `NullPointerException`. 


The history of `null` dates back to 1965, Tony Hoare (known for `Quicksort`) introduced `null` references in an object oriented language. In 2009 at a conference, he apologized for inventing the `null` reference, which he called "my billion-dollar mistake". \[[1][Null References: The Billion Dollar Mistake]\]

<!--more-->

> At that time, I was designing the first comprehensive type system for references in an object oriented language (ALGOL W). My goal was to ensure that all use of references should be absolutely safe, with checking performed automatically by the compiler. But I couldn't resist the temptation to put in a null reference, simply because it was so easy to implement. This has led to `innumerable errors, vulnerabilities, and system crashes`, which have probably caused a billion dollars of pain and damage in the last forty years.


Removing `null` is not possible, instead we need some mitigations. Check whether object is `null` before each invocation? That's not good, as it makes code longer and harder to read.

**Notice following ways are not mutually exclusive.**

# Mitigations
## Check `null` in the Beginning

{% highlight JAVA %}
public void myMethod(Object object) {
	if (object == null) {
		throw new IllegalArgumentException("informative message here.");
	}
}
{% endhighlight %}

## Comparing Known Object with Unknown Object

{% highlight JAVA %}
// Bad Case
unknownObject.equals(knownObject);
unknownString.equals("mystring");
{% endhighlight %}

{% highlight JAVA %}
// Good Case
knownObject.equals(unknownObject);
"mystring".equals(unknownString);
{% endhighlight %}

## Use `@Nullable` and `@NonNull`/`NotNull`
If we can reduce the code to have potential `null` access in complication phase, we will have fewer NullPointerException in the runtime. 


[JSR 305] aims to develop standard annotations (such as @NonNull) that can be applied to Java programs to assist tools that detect software defects. 


But the standard is not decided yet, we can see `NonNull` or `NotNull` in different IDEs or checkers. Take Eclipse as an example. By using proper annotation, Eclipse will show errors when it detects potential mismatch. 


![Nullable annotation in Eclipse](/assets/NPE_nullable_annotation_Eclipse_sample.png)

By default, this is not enabled in Eclipse. See [Enable annotation-based null analysis in Eclipse](/assets/NPE_nullable_annotation_Eclipse_enable_setting.png)

## Optional
Java 8 introduces a new class called `java.util.Optional` that can alleviate some of `NullPointerException` problems. Following is a very good example from [Tired of Null Pointer Exceptions? Consider Using Java SE 8's Optional!]

{% highlight JAVA %}
// may have NullPointerException
String version = computer.getSoundcard().getUSB().getVersion();
{% endhighlight %}

{% highlight JAVA %}
// `Safe` version with manual null check.
String version = "UNKNOWN";
if(computer != null){
  Soundcard soundcard = computer.getSoundcard();
  if(soundcard != null){
    USB usb = soundcard.getUSB();
    if(usb != null){
      version = usb.getVersion();
    }
  }
}
{% endhighlight %}

{% highlight JAVA %}
// Better version with Java Optional
String version = computer.map(Computer::getSoundcard)
                  .map(Soundcard::getUSB)
                  .map(USB::getVersion)
                  .orElse("UNKNOWN");
{% endhighlight %}

## Avoid Returning `null`
### ~~Null Object Pattern~~ (Not recommended)
[Null object pattern] was introduced in book series. The idea behind is intuitive, use an 'empty' object which does nothing instead of `null` to avoid `NullPointerException`. 


`NullPointerException` is caused by coding bugs. By creating an object which does not exist in the real world doesn't help solving the problem. Instead, it delays the exposure of coding bugs. Therefore, `Null object pattern` should not be `encouraged`.

{% highlight JAVA %}
// Not recommended
interface Animal {
    void makeSound();
}

class DummyAnimal implements Animal {

	@Override
	public void makeSound() {
		// DO Nothing!
	}
}
{% endhighlight %}

### ~~Return `empty` Collection Instead of `null`~~ (Not recommended)
Starting from a real example in a web project. In a normal version up, there are 2 issues among hundreds of issues.

1. A method named `getList` in data access layer returns `null`, and some invokers do not check carefully so `NullPointerException` occurred. Developer fixed it by return `empty` Collection.
2. There are more than 1 version of data structure in the system, one on-fly migration script checks whether the return of `getList` is `null` or not to decide a routine in the migration. However, after the above fixing, `getList` never returns `null`.

Incident finally happened that end user can not open some pages. 


`null` and `empty` Collection represent different things logically. `null` means `unknown`/`unset`/`unconfigured`. 


For example, to answer multi-choice questions in a survey system, `empty` Collection means user's answer is empty, while `null` means the user hasn't answered the question yet. 


Another example, in a user management system which records the marital status, some users do not specify, therefore when the system reads marital status from database, it might be `null` instead of either `true` or `false`. 


You can also find `null` in JSON and MySQL.


Do not mix the logic of `null` or `empty` just for avoiding `NullPointerException`.

# Conclusion
`null` is not avoidable as it represents something logically. In Java, `Optional` and `NonNull`/`Nullable` etc. are relatively good ways to mitigate the `NullPointerException`.

# Reference
\[1\] [Null References: The Billion Dollar Mistake]

\[2\] [JSR 305]

\[3\] [Tired of Null Pointer Exceptions? Consider Using Java SE 8's Optional!]

\[4\] [Null object pattern]

[Null References: The Billion Dollar Mistake]:https://www.infoq.com/presentations/Null-References-The-Billion-Dollar-Mistake-Tony-Hoare

[JSR 305]:https://jcp.org/en/jsr/detail?id=305

[Tired of Null Pointer Exceptions? Consider Using Java SE 8's Optional!]:https://www.oracle.com/technetwork/articles/java/java8-optional-2175753.html

[Null object pattern]:[https://en.wikipedia.org/wiki/Null_object_pattern]