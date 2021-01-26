---
layout: page
title: <u style='color:orange'>Programming</u> 
subtitle: "Proofs of programming skills and some interesting code."
header:
  theme: dark
  background: 'linear-gradient(135deg, rgb(250, 139, 87), rgb(100, 34, 139))'
article_header:
    type: overlay
    align: center
    theme: dark 
    background_image:
        src: /assets/images/portfolio/programming.jpg
picture: /assets/images/portfolio/programming.jpg
aside:
    toc: true
display_order: 2
show_title: true
hero_subtitle: "Here are posted code chunks regarding some of the common languages that developers usually need in their affairs, showing up my actual knowledge of those languages. Some code is posted in Pastebin, but I will try to show you notebooks if I could spend some time."
---
<!--more-->
## <a class="button button--info button--rounded button--lg" style="background:blue; color:yellow"><img src='https://svgshare.com/i/Csy.svg' style="width:20px; height=20px"/> Python </a>
{% highlight python %}
def hello_world(s1,s2="World!"):
    # out = s1 + " " + s2 # str.__add__() or +
    # out = " ".join([s1,s2])  # str.join()
    # out = "%s %s"%(s1,s2)  # % formatting
    # out = "{0} {1}".format(s1,s2)  # str.format()
    out = f"{s1} {s2}"   # f-strings
    return out

print(hello_world(s1="Hello"))

{% endhighlight %}

- Checkout [Python tags!](/archive/?tag=Python)

## <a class="button button--info button--rounded button--lg" style="background:magenta"><img src='/assets/svg/r-project.svg' style="width:28px; height=28px"/> R </a>
{% highlight R %}
hello_world <- function(s1,s2="World!"){
    # output <- paste(s1,s2, sep=" ")  # paste (separator for multiple values), kinde of "concat"
    output <- paste(c(s1,s2), collapse=" ") # paste (collapse for atomic vector), kind of "join"
    return(output)
}
hello_world(s1="Hello")

{% endhighlight %}

- Checkout [R tags!](/archive/?tag=R)

## <a class="button button--info button--rounded button--lg" style="background:orange; color:red"><img src='https://svgshare.com/i/PkX.svg' style="width:20px; height:20px"/> JavaScript </a>
{% highlight javascript %}
var string2 = String("World!")
function hello_world(s1,s2=string2){
    // out = s1 + " " + s2   // str.concat()
    out = [s1,s2].join(" ")  // str.join()
    return(out)
}
console.log(hello_world(s1="Hello"))

{% endhighlight %}

## <a class="button button--info button--rounded button--lg" style="background:grey"><img src="/assets/svg/fortran.svg" style="width:20px; height:20px">  Fortran </a>
{% highlight fortran %}
PROGRAM hello_world
    IMPLICIT none
    CHARACTER(10) :: s1="Hello"
    CHARACTER(10) :: s2="World!"
    CHARACTER(20) :: hello_world

    PRINT *,hello_world(s1,s2)
END PROGRAM

CHARACTER(20) FUNCTION hello_world(s1,s2)
    IMPLICIT none
    CHARACTER(10) :: s1,s2
    ! This is a line comentary inside the function
    hello_world = TRIM(s1) // " " // TRIM(s2)
    RETURN
END FUNCTION

{% endhighlight %}

## <a class="button button--primary button--success button--pill button--lg"><u>My Projects:</u></a>
- [ML_POCs](https://github.com/arioboo/ML_POCs): My <u style="color:grey">Proof Of Concepts</u> for ML and DL algorithms.
- [ML_Christmas](https://github.com/arioboo/ML_Christmas): A <u style="color:grey">Christmas Tree</u> related to my Kaggle account, proofs with Tensorboard and so on..
- [ML_Library](https://github.com/arioboo/ML_Library): My collected library for Machine Learning, builded from open sources found on Internet. It includes books/notebooks/etc. I have read and appreciated a lot these books. This repository does not include my own work
- [my_Cheatsheets](https://github.com/arioboo/my_Cheatsheets): Useful and personal collection of cheat-sheets for a wide range of topics.
- **Demanding APIs in Jupyter Notebooks**: I'm working on solutions demanding APIs for multiple purposes. Some which I work on are: 
    * Chess: [Lichess.org API](https://lichess.org/api)
    * Job networking: [Linkedin API](https://docs.microsoft.com/en-us/linkedin/)
    * Food and recipes: [Logmeal API](https://api.logmeal.es/docs/)
    * Room rental: [Housing Anywhere](https://developers.housinganywhere.com/)
- [LorDB](https://github.com/LorDB-company/LorDB)(1!): LorDB is a personal project that intends to be a **database sharing service** and **website**. Collaboration is welcomed, currently trying to find front-end developers for the project.
- [Chess-Analyzer](https://github.com/Chess-Improvers/chess-analyzer)(1!): This is a personal attempt to build an **automatic chess analysis** with clear parameters and automatic trascript of games. **AI** will be developed alongside the project. Collaboration is welcomed, specially for people related to chess programming.



## <a class="button button--secondary button--rounded" style="background:red"><u>Notes:</u></a>
*(1!): better included in the <a href="/portfolio/projects">Projects</a> section, but can be also categorized on <a href="#">Programming</a>.*
* I use <u>Devdocs.io</u>() for my personal guidance at most of topics. I strongly recommend using it online, or build a local server (accesible through http://127.0.0.1/) if you want to use any other source documentation offline. You can define a set of enabled-documentation via importing/exporting a simple JSON file.
##### last_updated: 2021-01-24
##### (I!) This a personal webpage with some information of interest in the topic selected. This is not intended to be a complete guide, but to provide useful guide for those interested in the topic and my personal trajectory on it.
##### Powered by [Jekyll](https://github.com/jekyll/jekyll): [TeXT Theme](https://github.com/kitian616/jekyll-TeXt-theme)
