---
layout     : post
comments   : true
title      : How to output Markdown file with Rmd
---

YML Setup
---------

Add the code below to the header in Rmd files.
<!--more-->

``` r
---
title: "Title"
output: github_document
---
```

Set Image Path
--------------

Use the code chunk below to set image output path

``` r
{r setup, include=FALSE}
knitr::opts_chunk$set(fig.path = "folder_for_image/")
```

Common Code Chunk Commands
--------------------------

`eval=FALSE` shows code only.

`echo=FALSE` displays output(image or console output) only.

`include=FALSE` runs code but doesn't show anything.

``` r
ggplot(data=iris, mapping = aes(x=Sepal.Length, fill=Species))+
    geom_histogram(binwidth = 0.5)
```

![image](https://github.com/liao961120/liao961120.github.io/blob/master/images/markdown1-1.png)

## Latex

$$\frac{\lambda}{3}$$
?
