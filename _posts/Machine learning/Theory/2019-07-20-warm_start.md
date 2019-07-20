---
layout: article
title: Warm start
tags: Theory
sidebar:
  nav: docs-en
---

# Remarks
[https://scikit-learn.org/stable/glossary.html#term-warm-start](https://scikit-learn.org/stable/glossary.html#term-warm-start)를 듬뿍 의역 및 수정한 글입니다.

<!--more-->

---

**warm_start**
동일한 dataset으로 estimator(model)를 반복적으로 학습시키고자 하는 경우에 학습시간을 줄이기 위해 기존의 학습된 parameter(attribute)에서부터 학습을 시작하는 방법입니다. <br>

`warm_start=True` option을 사용하게되면, 다음 번 `fit()`을 통해 학습되는 새로운 estimator의 초깃값으로 기존의 학습된 parameter가 사용됩니다. <br>

이 기능은 몇몇 개의 estimator와 parameter에 대해서만 사용할 수 있고 일부 parameter의 순서에도 적용할 수 있습니다.
