---
title: "Java Exception 201: NullPointerException"
date: 2018-09-28 16:18:32 +0800
tags: Java Exception
key: 2018-09-28-JavaException201
---

# What is NullPointerException (NPE)
It's worthy writing a long article to describe `NullPointerException`, the evil we hate. How to prevent `NPE` as much as possible?

Check whether object is null before each invocation? That's not good, as it makes code longer and harder to read.

**Notice following ways are not mutually exclusive.**

# Mitigations
## Check null in the Beginning
``` Java
public void myMethod(Object object) {
	if (object == null) {
		throw new IllegalArgumentException("useful message here.");
	}
}
```

## Comparing Known Object with Unknown Object

Instead of 
```Java
unknownObject.equals(knownObject) // BAD
```

Please use 

```Java
knownObject.equals("unknownObject") // GOOD
```

## Use `@Nullable` and `@NotNull`
[JSR 305]

## Avoid Returning `null` from method
## null and empty Collection are different

## Optional

## Null Object Pattern

## assert


[JSR 305]:https://jcp.org/en/jsr/detail?id=305
  https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html