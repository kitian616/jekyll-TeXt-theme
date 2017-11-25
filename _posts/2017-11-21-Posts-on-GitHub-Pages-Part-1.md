---
layout     : post
mathjax    : true
title      : Create Posts on GitHub Pages with R Studio(Part 1)
key        : post
comment    : true
---
<script type="text/x-mathjax-config">
MathJax.Hub.Config({
  tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}
});
</script>
<script type="text/javascript" async src="//cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-MML-AM_CHTML">
</script>

## Getting Started with R markdown
First open a new R markdown file in R studio.

## YML Setup

By default, Rmd files will output as html files. If you want to post on GitHub pages, you will need markdown(.md) files.
Add the code below to the header in Rmd files to get a file output as `.md`.
<!--more-->
```r
---
title: "Title"
output: github_document
---
```


## Features of Rmd files
Writing posts on R studio has some great benefits. I think the most appealing one is that you can easily put together both code and outputs of code in the Rmd file(How tedious would it be if you're working with MS word, you'll need a lot of cut and paste).

### Code Chunks
You could insert a code chunk by clicking the **insert** button on upper part of the source pane(where R scripts are shown). After inserting the code chunks, you can change the properties of the code chunk with the arguments below:

* `eval=FALSE` shows code only.

* `echo=FALSE` displays output(image or console output) only.

* `include=FALSE` runs code but doesn't show anything.

How a code chunk and its output look like in a markdown file:
```r
ggplot(data=iris, mapping = aes(x=Sepal.Length, fill=Species))+
    geom_histogram(binwidth = 0.5)
```
![]({{ "/assets/images/markdown1-1.png" | absolute_url }})

,and the original appearance in Rmd file:
```r
## ```{r , include=FALSE}
## library(ggplot2)
## ```

## ```{r markdown1, echo=TRUE}
## ggplot(data=iris, mapping = aes(x=Sepal.Length, fill=Species))+
##    geom_histogram(binwidth = 0.5)
## ```
```
Note that the first code chunk above didn't appear in the md file.

### LaTeX
LaTeX is used for creating neat mathematic notations, and Rmarkdown supports it.
If you type: `$$\frac{\lambda}{3}$$` in Rmd files(not in code chunks, where the LaTeX syntax is between the two `$$`, you will have the output below.

$$\frac{\lambda}{3}$$

If you put `\frac{\lambda}{3}` between two `$`s, it would be displayed inline,like this $\frac{\lambda}{3}$.


## Set Image Path
When your output of code contains images, R studio would create a folder and save them in it. You can set where you want to save the images in advance(you migth want to do this if you're using Git and GitHub together) by inserting a code chunk after the YML:
```r
{r setup, include=FALSE}
knitr::opts_chunk$set(fig.path = "folder_for_image/")
```
The code above will save the output images in a folder called `folder_for_image` in the working directory.



<div id="disqus_thread"></div>
<script>
    /**
     *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT 
     *  THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR 
     *  PLATFORM OR CMS.
     *  
     *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: 
     *  https://disqus.com/admin/universalcode/#configuration-variables
     */
    /*
    var disqus_config = function () {
        // Replace PAGE_URL with your page's canonical URL variable
        this.page.url = PAGE_URL;  
        
        // Replace PAGE_IDENTIFIER with your page's unique identifier variable
        this.page.identifier = PAGE_IDENTIFIER; 
    };
    */
    
    (function() {  // REQUIRED CONFIGURATION VARIABLE: EDIT THE SHORTNAME BELOW
        var d = document, s = d.createElement('script');
        
        // IMPORTANT: Replace EXAMPLE with your forum shortname!
        s.src = 'https://y-f-liao-s-page.disqus.com/embed.js';
        
        s.setAttribute('data-timestamp', +new Date());
        (d.head || d.body).appendChild(s);
    })();
</script>
<noscript>
    Please enable JavaScript to view the 
    <a href="https://disqus.com/?ref_noscript" rel="nofollow">
        comments powered by Disqus.
    </a>
</noscript>
