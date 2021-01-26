---
layout: article
title: About
key: page-about
permalink: /about/
#->
about: 
    description: "`Astrophysicist` & `Data Scientist`. But I really wanted to become a `Deep Learning` expert, so there I am. I hope you enjoy checking out my activity. Explore my web and feel free to **contact me**."
    lemma: "*Everyday work can change everything in this life*"
show_title: false
quote: 
    text: "I choose a lazy person to do a hard job. Because a lazy person will find an easy way to do it."
    author: "Bill Gates"
---



# About me!

<div class="about" font_size="30px" >
{{ page.about.description | markdownify }}
{{ page.about.lemma | markdownify }}
</div>

{% include my_home/about_github_arioboo.md  %}
--------------------------------------------------------------------------------------------
<div class="quote">
<h4 style="color:black"><b style="color: red">"{{ page.quote.text }}"</b> - {{ page.quote.author | color_modify: red }}</h4>
</div>
