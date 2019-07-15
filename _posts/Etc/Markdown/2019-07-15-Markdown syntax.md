---
layout: article
title: Markdown / LaTex notes
tags: Markdown
sidebar:
  nav: docs-en
aside:
  toc: true
---

1. Code block
다음과 같이 적어주면 예쁜 syntax highlight를 볼 수 있다.

**markdown:**

    {% highlight python linenos %}
    # Code here
    {% endhighlight %}


2. LaTex symbols
Bold체에도 몇 가지 종류가 있어서 목적에 맞는 문법을 사용하면 원하는 모양을 볼 수 있다.  

**markdown:**

    \textbf{}: Text
    \mathbf{}: Vector
    \mathbb{}: Special set (Real set)
    \mathcal{}: Laplace, Fourier transform의 그것  


3. Jekyll(kramdown) styles
highlight theme을 tomorrow로 사용할 떄만 사용할 수 있는 것 같다.
- Alert
Class: success, info, warning, error

**markdown:**

    Success Text.
    {:.success}

- Tag
Class: success, info, warning, error

**markdown:**

    `_`{:.success}
