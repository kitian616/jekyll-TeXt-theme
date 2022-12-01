---
title: "Creating my EX280 page on Jekyll"
toc: true
mermaid: true
categories: [Jekyll]
tags: [ex280, study]
---

As you might have notices I use Jekyll for this blog hosted on GitHub. I might do a post later on how I use it and how I set it up. For now, all you need to know is that Jekyll turns `.md`(markdown) file in to a blog. This happens using GitHub actions and builds my blog from source when I commit to main. 
Jekyll support some dynamic code to generate content and in this blog I will show you how I used that to generate an overview of all blog posted in a certain category.

# Why
As part of my ongoing study's I decided to give [EX280 (Red Hat Certified Specialist in OpenShift Administration)](https://www.redhat.com/en/services/training/ex280-red-hat-certified-specialist-in-openshift-administration-exam?section=Overview) a try. I wrapped up EX200 earlier to get familiar with the way RedHat does Remote testing and to get a feel for the style of question's that are asked. I passed EX200 on the first try but wasn't lucky on EX280 (getting 150 of 300 points on the first try, 210 are needed to pass).
It was then that I thought back on something I red recently about learning:

> "Writing is thinking" (Ahrens2017)[^Ahrens2017]

So I decided that I would do some write ups on the core concepts that you need to know for EX280. I also thought It might warrant a special page on my blog to find it easily. 

# How
I started by creating a new category in Jekyll. This is simply done by using setting a new category in the [front matter](https://assemble.io/docs/YAML-front-matter.html) of a markdown file:

As an example, this is the front matter of this post:
```yaml
title: "Creating my EX280 page on Jekyll"
toc: true
mermaid: true
categories: [Jekyll]
tags: [EX280, Study]
```

## Creating a tab
Next up I created a tab in my Jekyll locals file, you can see that over here. The locals file for this blog is located at [`_data/locales`](https://github.com/KingOfSpades/KingOfSpades/blob/main/_data/locales/en.yml).

```yaml
....
# The tabs of sidebar
tabs:
  # format: <filename_without_extension>: <value>
  home: Home
  categories: Categories
  tags: Tags
  archives: Archives
  about: About
....
```

## Creating the page
Next up I created a page in `_tabs` with some content explaining what the page was about. It's the page you are now viewing and it's located at [`_tabs`](https://github.com/KingOfSpades/KingOfSpades/blob/main/_tabs/ex280.md)

### Dynamically filling the page
Last up was creating the overview of my [EX280](/categories/ex280/) posts on this new page. 
I did this using and modifying the following code:

```js
<div id="page-category">
  <h1 class="pl-lg-2">
    <i class="far fa-folder-open fa-fw text-muted"></i>
      All EX280 Related post's
    <span class="lead text-muted pl-2">{{ site.categories["EX280"] | size }}</span>
  </h1>

  <ul class="post-content pl-0">
    {% assign post_df = site.data.locales[lang].date_format.post.long %}

    {% for post in site.categories["EX280"] %}
    <li class="d-flex justify-content-between pl-md-3 pr-md-3">
      <a href="{{ post.url | relative_url }}">{{ post.title }}</a>
      <span class="dash flex-grow-1"></span>
      <span class="text-muted small">{{ post.date | date: post_df }}</span>
    </li>
    {% endfor %}
  </ul>
</div>
```

The most important this is the selector of the category: `site.categories["EX280"]`. if you wanted to simply list a blog of you custom category (let's call the example category `foo`) you could use:

{% raw %}
```js
  {% for post in site.categories["foo"] %}
  <li">
    <a href="{{ post.url | relative_url }}">{{ post.title }}</a>
  </li>
  {% endfor %}
```
{% endraw %}

# Wrapping up
So that's it. At the time of writing I have the first two EX280 post's up but I have planed a few more. Check out the page at [https://blog.benstein.nl/ex280/](https://blog.benstein.nl/ex280/)

**Update:** It paid of. I passed the exam on my second try with 262/300 points.

[^Ahrens2017]: Ahrens, Sönke. _How to Take Smart Notes: One Simple Technique to Boost Writing, Learning and Thinking: For Students, Academics and Nonfiction Book Writers_. North Charleston, SC: CreateSpace, 2017.