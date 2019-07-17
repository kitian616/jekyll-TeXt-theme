---
layout: article
title: Confusion matrix
tags: Theory
sidebar:
  nav: docs-en
aside:
  toc: true
---

분류기의 성능을 평가하기 위해 간단히 accuracy (정확도)만을 사용할 수도 있지만, 더 정확하게 평가하기 위해서 **confusion matrix (오차 행렬)** 를 조사하는 것이 필수적입니다. <br>

<img align='left' src="https://raw.githubusercontent.com/djy-git/djy-git.github.io/master/_posts/assets/confusion_matrix.png">[^1]
<br>
### 1. Accuracy = $\frac{\text{TP + TN}}{\text{All}}$
### 2. Precision = $\frac{\text{TP}}{\text{TP + FP}}$
### 3. Recall = TPR = $\frac{\text{TP}}{\text{TP + FN}}$
### 4. F$\bf{_1}$ score = $\frac{2}{\frac{1}{Precision} + \frac{1}{Recall}}$
### 5. FPR = $\frac{\text{FP}}{\text{FP + TN}}$
{:.success}


[^1]: 출처: https://tex.stackexchange.com/questions/20267/how-to-construct-a-confusion-matrix-in-latex
