---
layout: article
title: Warm start
tags: Theory
sidebar:
  nav: docs-en
---

# Remarks
[https://scikit-learn.org/stable/glossary.html#term-warm-start](https://scikit-learn.org/stable/glossary.html#term-warm-start)를 참고하였습니다.

<!--more-->

---

**warm_start**
동일한 dataset으로 estimator(model)를 반복적으로 학습시키고자 하는 경우에 학습시간을 줄이기 위해 기존의 학습된 parameter(attribute)에서부터 학습을 시작하는 방법입니다. <br>

`warm_start=True` option을 사용하게되면, `fit()`을 통해 학습되는 새로운 estimator의 초깃값으로 따뜻하게 데워진(?) 기존의 학습된 parameter가 사용됩니다. <br>

이 기능은 몇몇 개의 estimator와 parameter에 대해서만 사용할 수 있고 일부 parameter의 순서에도 적용할 수 있습니다. <br>
예를 들어 `RandomForestClassifier`, `SGDClassifier`와 같은 estimator를 생성할 때 `warm_start` 여부를 생성 시 정할 수 있지만, 대부분의 estimator들은 `fit()`를 실행할 때마다 기존의 parameter를 초기화시키고 주어진 데이터를 학습하는 `cold_start`를 합니다.
