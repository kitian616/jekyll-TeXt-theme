---
layout: article
title: Confusion matrix
tags: Theory
sidebar:
  nav: docs-en
aside:
  toc: true
---

분류기의 성능을 평가하기 위해 간단히 accuracy (정확도)만을 사용할 수도 있지만 더 정확하게 평가하기 위해서 **confusion matrix (오차 행렬)** 를 조사하는 것이 필수적입니다. <br><br>

<img align='left' src="https://raw.githubusercontent.com/djy-git/djy-git.github.io/master/_posts/assets/confusion_matrix.png">[^1]
#### 1. Accuracy = $\frac{\text{TP + TN}}{\text{All}}$
#### 2. Precision = $\frac{\text{TP}}{\text{TP + FP}}$
#### 3. Recall = TPR = $\frac{\text{TP}}{\text{TP + FN}}$
#### 4. F$\bf{_1}$ score = $\frac{2}{\frac{1}{Precision} + \frac{1}{Recall}}$
#### 5. FPR = $\frac{\text{FP}}{\text{FP + TN}}$
<br><br><br><br><br><br>
{:.success}

<!--more-->

---

# 1. Accuracy
**Accuracy (정확도)** 는 가장 대표적인 분류기의 평가지표로 전체 sample들 중 정확하게 분류한 것들의 비율을 의미합니다. <br>
Accuracy 뿐만 아니라 여타 지표들도 마찬가지로 각각 제한된 정보만을 제공하기 때문에 여러가지 조건에 따른 지표들을 함께 고려하여 총체적으로 분류기를 평가하는 것이 바람직합니다.
<br>

# 2. Precision
**Precision (정밀도)** 은 true라고 예측$\color{green}{\textbf{(predictive true)}}$한 것들 중에서 실제로 true$\color{blue}{\textbf{(actual true)}}$인 sample들의 비율을 의미합니다. 양성 예측의 정확도라고 할 수 있습니다. <br><br>
$$ \textbf{Prediction} = P(\color{blue}{\textbf{actual true }} | \color{green}{\textbf{ predictive true}}) $$
<br>

# 3. Recall
**Recall (재현율, sensitivity, 민감도, TPR, true positive ratio)** 은 실제로 true$\color{blue}{\textbf{(actual true)}}$인 것들 중 true라고 예측$\color{green}{\textbf{(predictive true)}}$한 sample들의 비율입니다. <br><br>
$$ \textbf{Recall} = P(\color{green}{\textbf{predictive true }} | \color{blue}{\textbf{ actual true}}) $$
<br>

# 4. Precision / recall tradeoff
Precision은 다른 모든 양성 sample들(FN)을 무시하기 때문에 이들을 반영하는 recall과 반드시 함께 평가되어야 합니다. 안타깝게도 두 지표를 동시에 올릴 수는 없기 때문에 주어진 문제에 따라 가장 적절한 임곗값(threshold, decision function)을 정해야 합니다. 이를 **precision / recall tradeoff** 라고 부릅니다.

![Image](https://raw.githubusercontent.com/djy-git/djy-git.github.io/master/_posts/assets/threshold.png){:.border}
<br>

# 5. F$\bf{_1}$ score
**F$\bf{_1}$ score** 는 precision과 recall의 조화평균으로 precision과 recall이 비슷한 분류기에서 큰 값을 나타냅니다. <br>
특히, 두 분류기를 비교할 때 precision과 recall을 하나의 지표로 만든 F$_1$ score를 편리하게 사용할 수 있습니다.
<br>

# 6. PR curve
Precision-recall graph를 말합니다. <br>
일반적으로 precision이 급격하게 줄어드는 하강점 직전을 threshold로 정하는 것이 좋습니다. 어떤 precision이 주어지더라도 만족시키는 분류기를 만들 순 있지만, recall이 너무 낮다면 사용할 수 없기 때문에 생성된 분류기의 성능을 고려해야 합니다.

![Image](https://raw.githubusercontent.com/djy-git/djy-git.github.io/master/_posts/assets/prcurve.png){:.border}
<br>

# 7. ROC curve (Receiver Operating Characteristic curve)
또다른 평가곡선으로 FPR-TPR graph를 **ROC curve**라 부릅니다. <br>
**FPR (False Positive Ratio)** 은 true라고 예측$\color{green}{\textbf{(predictive true)}}$한 sample들 중 실제로 false$\color{red}{\textbf{(actual false)}}$인 것들의 비율을 의미하며, **TPR (True Positive Ratio)** 은 recall과 동일한 값을 나타냅니다. 여기서도 TPR(recall)이 높을수록 FPR이 증가하는 tradeoff가 발생합니다. <br>
점선은 완전한 random 분류기를 의미하며 성능이 좋은 분류기는 이 점선으로부터 최대한 많이 떨어져 있는 (0, 1.0)에 근접한 모양이 됩니다. Curve 아래의 면적인 **AUC(Area Under the Curve)** 를 통해 분류기들을 비교할 수 있는데 완벽한 분류기는 AUC=1 이고, random 분류기의 AUC=0.5 가 됩니다.

![Image](https://raw.githubusercontent.com/djy-git/djy-git.github.io/master/_posts/assets/roccurve.png){:.border}

일반적으로 true class가 적거나 FN보다 FP가 더 중요할 때 PR curve를 사용하고, 그 밖의 경우엔 ROC curve를 사용합니다.


[^1]: 출처: [https://tex.stackexchange.com/questions/20267/how-to-construct-a-confusion-matrix-in-latex](https://tex.stackexchange.com/questions/20267/how-to-construct-a-confusion-matrix-in-latex)
