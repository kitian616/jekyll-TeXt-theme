---
layout: article
title: Error analysis
tags: Python
sidebar:
  nav: docs-en
aside:
  toc: true
---

전처리 단계를 마치고 문제에 가장 적절한 모델을 찾은 후 해당 모델의 성능을 향상시키기 위한 방법을 찾기 위해 생겨난 에러의 종류를 분석할 수 있습니다. 이 과정을 **에러 분석 (Error analysis)** 라고 합니다.
{:.success}

<!--more-->

---

0에서 9까지의 숫자 이미지를 분류하는 작업에서 linear SGDClassifier를 사용한 경우를 살펴봅시다. <br>

1. 먼저 confusion matrix를 구하고 `matshow()`를 사용하여 시각적으로 문제가 되는 지점을 분석합니다.

```p
y_train_pred = cross_val_predict(sgd_clf, X_train_scaled, y_train, cv=3)
conf_mx = confusion_matrix(y_train, y_train_pred)

plt.matshow(conf_mx, cmap=plt.cm.gray)
plt.show()
```

![Image](https://raw.githubusercontent.com/djy-git/djy-git.github.io/master/_posts/assets/all_conf_mat.png){:.border} <br>
