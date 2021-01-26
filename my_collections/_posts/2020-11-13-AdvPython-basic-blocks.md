---
layout: article
show_title: true
tags:
- Python
- tutorial
- advanced
title: "Advanced Python: The basic blocks of everything."
---

Hey! I really think this tutorial is good stuff for you. Maybe you've been years using Python or you are just a beginner, but you get struggled when you try to understand how things work in Python, you don't understand docs and you get lost very quickly when you try to advance in Python. This guide is for you. Also, I recommend reading this even if you are a very advanced Python programmer, as well as basic user, you will understand why very quickly.

Without more preables, let's tackle it.

## The basic blocks: Classes and Functions.

"Classes and Functions", "Classes and Functions"... repeat 10 times more. You get it. Because all things in Python are objects, and those objects are divided (in a general way), into classes and functions.

### Classes
Classes have the following characteristics:

- Store objects in them. These could be other instance of classes (called **attributes**) or functions (called **methods**).
- Serve as templates. They instantiate objects belonging to the original class by calling it's **constructor** ().
- Can heredate from other classes. You can call "Parent" or "Child" class attending this hierarchy. Child classes heredate all attributes and methods from parent classes.

Classes have the following components:

- **Methods:** Functions inside the class. They can be:
    1. **Dunder methods:** They have a special meaning for Python. They are methods that interact's with Python internal built-in system, and implements some general functionality. I.e.: __init__(),__add__(),__exit__(). They have double underscores surrounding a verb.
    2. **Routine methods:** They .They can be called as you want, but by conventions, it is a good practice to call them as **verbs**. I.e.: get_something(), get_name, get_attribute(), etc. By the way, the "get_something" methods are calling "getters". You will find more of these practices by a little of thinking process about them.
- **Attributes:** Attributes are objects attached to the class. They are usually instances, but you could attach subclasses (i.e. 'self.num = 1' defines an attribute called "num").

Classes are defined by this syntax done by pseudo-code:

```
class my_Class(<parent_class (opt)>):
    def __init__(self,<other_arguments (opt)>):
        self.<some_attribute_name> = <object_got_by_calling_constructor> 
        <return_sentence (opt)>
    
    # attribute <some_attribute_name>
    <some_attribute_name> = <object>
    
    # method <some_function_name>
    def <some_function_name>(self, <other_arguments (opt)>):
        self.<attribute1> = <object>
        self.<attribute2> = <function_call>(<arguments>)
        return self.<attribute>    

# Instantiation:
obj = my_Class()

# Reference to methods/attributes:
obj.<method>
obj.<attribute>
```

These explanations can be extended much more, and made more rigurous, but we assume you know already how to code a class, define the __init__() method and instantiate an object. You can find this out in almost every rounded Python tutorial, as [this one.](https://github.com/Asabeneh/30-Days-Of-Python).

### Functions

Functions have the following characteristics:

Functions have the following components:

- **Name.** The name is only an alias to access the object, as any object in Python. You call the function like `function(arguments)`.
- **Input.** Inputs define a map between the *GLOBAL NAMESPACE* and the *LOCAL NAMESPACE*. This defines a level of abstraction and accesibility (in classes we do not have this, except from methods, which are functions attached) and you can take advantage of this. You can vary the objects to pass to the function at execution time (when you call `function(argument=any_object)`). Also, it's optional to have input, you can just gather a set of instructions and put them together in a function and not care all about how it was done. 

    Input is the collection of arguments. There are different types of arguments that should be placed in order:
        1. default arguments: "args". def f(arg1=1, arg2=2). They are defined in the definition of the function, and although you change their value, the remain being that value if you don't call them explicitly.
        2. keyword arguments: or "kwargs". f(arg1=1, arg2=2). They don't need to respect position because they are called through mapping (=) 
        3. positional arguments: "args". f(1, 2, 3). They are placed in index-order into the function local namespace. 
        4. arbitrary positional arguments. def f(*args). They are packed into the local namespace (as a tuple), when the function is called. * is splat operator.
        5. arbitrary keyword arguments. def f(**kwargs). They take the mappings defined in a dictionary and adopt that namespace when the function is called. ** is double_splat operator.

- **Internal structure.** You can place transformations, scripts, and whatsoever, inside a function. Your goal is to abstract a series of instructions and get the output of them done by simply calling the function. For the internal structure, you use a **LOCAL NAMESPACE**, which you can't access out of the function. You only can access the output of a function by calling the function outside (i.e. '\<out\> = \<function_name\>(\<arguments\>)' ). You can also set "global \<object\>"
    inside a function to declare it globally.
- **Output.** There is always an `Output`, although it can be "None". 'return <something>' will return the output and exit the function. Outputs are generally stored as alias with the assign operator = (i.e. `my_output =  my_function(arguments)`).


```
def my_Function(a,b=2):  # Input, arguments
    out = a + b         # Internal structure/instructions
    return out          # Output

my_Function(1,1) # Outputs 2, placed by order into local namespace

my_Function(1,b=1) # Also outputs 2
my_Function(a=1,b=1)

my_Function(1) # Outputs 3, b was default argument

args=[10,10]
my_Function(*args) #Outputs 20

kwargs = { "a":10, 
           "b":10,
           }
my_Function(**kwargs)  # Outputs 20
```

## Once you understand this...

Ok, for an intermediate/advanced Python guy, what I've said until now is just obvious. 

A real advanced Pythoner guy is, of course, much more than this. But internally, he doesn't know much more than what I've just told before. The difference resides in details: advanced Python users **DO KNOW** the details of the basic python types, while intermediate users **DON'T**.

A typical intermediate user will find "3rd party/some external" library doing what they want to do in a simple way and exclaim "Hey!, I've just done it, I'm pro.". Another example is that they try to make an overcomplicated function by themselves that often works magically.

A advanced user would try to do the job by simply using **methods** and **classes** from *built-in* Python. If they find it difficult or need more help, they could be looking for some libraries in the [Python Standard Library](https://docs.python.org/3/library/). This is because these libraries have full integration with each other, and you will not be exhausting your possibilities or reaching incompatibles within your code.

There are some few external libraries that are **CORE** for Python. This is because they extend their functionality far away. A few of them are:

- NumPy: numpy, numpy as np
- Matplotlib: matplotlib , matplotlib.pyplot as plt
- Pandas: pandas, pandas as pd
- SciPy : scipy, scipy as sp
- Statsmodels: statsmodels, statsmodels as sm
- Scikit-learn: sklearn, from sklearn import *
- Tensorflow: tensorflow, tensorflow as tf

It is totally ok to use these core libraries as they are far well documented, supported and developed.

## Reading a documentation:

What an advanced Python user does when learning a new library is:

1. Going to the official documentation of that library.
2. They read the **Overview**. 
3. They try to make a little understanding of the main classes of that library. These classes are usually super-classes for other classes defined by hereditance, and they are usually enough to achieve most purposes.
4. They figure out basic methods of the super-classes. They know the "get", "set", "from", "to" and other verbs defining methods in relation to attributes.
5. They get hands on it!

This is the super-power I hope you develop by reading this guide. The power of Python is simplified by the interface of "Classes/Functions" it has. You just can reach a module or a topic you didn't even know nothing about and end up having good understanding of it.

Best of luck!
