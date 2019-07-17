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

$$
\begin{tabular}{l|l|c|c|c}
\multicolumn{2}{c}{}&\multicolumn{2}{c}{True diagnosis}&\\
\cline{3-4}
\multicolumn{2}{c|}{}&Positive&Negative&\multicolumn{1}{c}{Total}\\
\cline{2-4}
\multirow{2}{*}{Screening test}& Positive & $a$ & $b$ & $a+b$\\
\cline{2-4}
& Negative & $c$ & $d$ & $c+d$\\
\cline{2-4}
\multicolumn{1}{c}{} & \multicolumn{1}{c}{Total} & \multicolumn{1}{c}{$a+c$} & \multicolumn{    1}{c}{$b+d$} & \multicolumn{1}{c}{$N$}\\
\end{tabular}
$$
