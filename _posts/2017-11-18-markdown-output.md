---
layout     : post
comments   : true
mathjax    : true
title      : How to output Markdown file with Rmd
---
<script type="text/javascript" async src="//cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-MML-AM_CHTML">
</script>

YML Setup
---------

Add the code below to the header in Rmd files.
<!--more-->

```r
---
title: "Title"
output: github_document
---
```

Set Image Path
--------------

Use the code chunk below to set image output path

```r
{r setup, include=FALSE}
knitr::opts_chunk$set(fig.path = "folder_for_image/")
```

Common Code Chunk Commands
--------------------------

`eval=FALSE` shows code only.

`echo=FALSE` displays output(image or console output) only.

`include=FALSE` runs code but doesn't show anything.

```r
ggplot(data=iris, mapping = aes(x=Sepal.Length, fill=Species))+
    geom_histogram(binwidth = 0.5)
```


![]({{ "/assets/images/markdown1-1.png" | absolute_url }})

## Latex
$$\frac{\lambda}{3}$$

## R code
```r
if (TRUE) {
   print("a = b") 
} else {
   print("I'm not sure!")
}
```

## R code 2
``` r
if (TRUE) {
   print("a = b") 
} else {
   print("I'm not sure!")
}
```

## python code
```python
if a == b:
   print "a = b"
else:
   print "I'm not sure!"
```

