---
layout: article
title: "New features in C# 10 and 11"
key: "New features in C# 10 and 11"
tags: dotnet csharp net6.0
aside:
    toc: true
---

After a VISUG talk regarding new features in the latest c# language versions, these are my favourites.

<!--more-->

## The Record datatype

The [Record](https://docs.microsoft.com/en-us/dotnet/csharp/language-reference/builtin-types/record) datatype is a short notation for a **class** or a **struct**. By design, its properties are immutable. Examples of usages can be found in the code snippet below.

<iframe width="100%" height="400" src="https://dotnetfiddle.net/Widget?Languages=CSharp&CSharp_FiddleId=DTYkGd" frameborder="0"></iframe>

### Pattern matching with record datatypes

Some advanced pattern matching can be performed on record datatypes.

```csharp
// Pattern matching
Console.WriteLine(person is Person { name: "Willem" });

// More complex Example for pattern matching: match properties that are part of type
var personWithAddress = new PersonWithAddress("Willem", new Address("Waregem"));

// Match on a property of the type
// C# 11 allows the "." notation to match on the property of a type
Console.WriteLine(personWithAddress is PersonWithAddress { address.city: "Waregem" });
```

The examples, as shown above, are also safe against `NullReference Exceptions`. When we set `personWithAddress` to `null`, it matches as `false`.

```csharp
// Safe against nullReference exceptions
personWithAddress = null;

// Will print "false"
Console.WriteLine(personWithAddress is PersonWithAddress { address.city: "Waregem" });
```

## String interpolation improvements

### Usage of double quotes in a string

With C# 11, it will be possible to use strings with double quotes as literals, without escaping them with a backslash. Consider the following example

```csharp
var testing = ""
    This is a "test" string
    "";
// Will print "This is a "test" string"
Console.WriteLine(testing);
```

The beginning and the ending of the string should be marked with the amount of double quotes, one more than the sequences, used in the string. In the example above, 2 double quotes are sufficient to mark the beginning and the ending of the string.

If 2 double quotes are used in a sequence, 3 double quotes are used to begin and end the string declaration. This can go on to about 17 double quotes. Below, a more extreme example

```csharp
var testing = """""
    This is a "test" string with unnecessarily many double quotes """"
    """"";
// Will print "This is a "test" string with unnecessarily many double quotes """""
Console.WriteLine(testing);
```

### Use curly braces in a string, alongside string interpolation

The same principle as shown in [the previous section](#usage-of-double-quotes-in-a-string) can also be found in string interpolation

```csharp
var i = 0;
var testing = $$"
    {i} is {{i}}
    ";

// Will print "{i} is 0"
Console.WriteLine(testing);
```
