---
layout: article
title: "Make code blocks copyable"
clipboard: true
---

## A code block wrapped in a `snippet` class

<div class="snippet" markdown="1">

	<div class="snippet" markdown="1">

	```
	def hello():
	    print("Hello world!")
	```
	{: .language-python}
	</div>
</div>

which looks like this:  

<div class="snippet" markdown="1">

```
def hello():
    print("Hello world!")
```
{: .language-python}
</div>

## Original style without copyable button

<div class="snippet" markdown="1">

	```
	def hello():
	    print("Hello world!")
	```
	{: .language-python}

</div>

which is given as follows:

```
def hello():
    print("Hello world!")
```
{: .language-python}

## Inline codes are not applied

Some inline code: `python --version`.