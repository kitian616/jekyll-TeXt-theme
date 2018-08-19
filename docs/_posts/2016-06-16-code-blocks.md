---
title: Jekyll - Code Blocks
key: 20160616
tags:
  - Jekyll
  - English
---

## Code Spans

Use `<html>` tags for this.

Here is a literal `` ` `` backtick.
And here is ``  `some`  `` text (note the two spaces so that one is left
in the output!).

<!--more-->

**markdown:**

    Here is a literal `` ` `` backtick.
    And here is ``  `some`  `` text (note the two spaces so that one is left
    in the output!).

## Standard Code Blocks

    Here comes some code

    This text belongs to the same code block.

^
    This one is separate.

**markdown:**

```
    Here comes some code

    This text belongs to the same code block.

^
    This one is separate.
```

---

```
(() => console.log('hello, world! hello, world! hello, world! hello, world! hello, world! hello, world! hello, world! hello, world!'))();
```

**markdown:**

    ```
    (() => console.log('hello, world! hello, world! hello, world! hello, world! hello, world! hello, world! hello, world! hello, world!'))();
    ```

---

```javascript
(() => console.log('hello, world!'))();
```

**markdown:**

    ```javascript
    (() => console.log('hello, world!'))();
    ```

---

```none
(() => console.log('hello, world! hello, world! hello, world! hello, world! hello, world! hello, world! hello, world! hello, world!'))();
```

## Highlighting Code Snippets

{% highlight javascript %}
(() => console.log('hello, world!'))();
{% endhighlight %}

**markdown:**

```
{%- raw -%}
{% highlight javascript %}
(() => console.log('hello, world!'))();
{% endhighlight %}
{% endraw %}
```

### Line Numbers

{% highlight javascript linenos %}
var hello = 'hello';
var world = 'world';
var space = ' ';
(() => console.log(hello + space + world + space + hello + space + world + space + hello + space + world + space + hello + space + world))();
{% endhighlight %}

**markdown:**

```
{%- raw -%}
{% highlight javascript linenos %}
var hello = 'hello';
var world = 'world';
var space = ' ';
(() => console.log(hello + space + world + space + hello + space + world + space + hello + space + world + space + hello + space + world))();
{% endhighlight %}
{% endraw %}
```

---

{% highlight javascript %}
(() => console.log('hello, world! hello, world! hello, world! hello, world! hello, world! hello, world! hello, world! hello, world!'))();
{% endhighlight %}

{% highlight none %}
(() => console.log('hello, world! hello, world! hello, world! hello, world! hello, world! hello, world! hello, world! hello, world!'))();
{% endhighlight %}

## Fenced Code Blocks

~~~
Here comes some code.
~~~

**markdown:**

    ~~~
    Here comes some code.
    ~~~

---

~~~~~~~~~~~~
~~~~~~~
code with tildes
~~~~~~~~
~~~~~~~~~~~~~~~~~~

**markdown:**

    ~~~~~~~~~~~~
    ~~~~~~~
    code with tildes
    ~~~~~~~~
    ~~~~~~~~~~~~~~~~~~

## Language of Code Blocks

~~~
def what?
  42
end
~~~
{: .language-ruby}

**markdown:**

    ~~~
    def what?
      42
    end
    ~~~
    {: .language-ruby}

---

~~~ ruby
def what?
  42
end
~~~

**markdown:**

    ~~~ ruby
    def what?
      42
    end
    ~~~