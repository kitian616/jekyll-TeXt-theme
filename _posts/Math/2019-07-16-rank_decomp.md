---
layout: article
title: Rank factorization
tags: Math
sidebar:
  nav: docs-en
aside:
  toc: true
---

이번에 살펴볼 내용은 neural network를 구축할 때 기본적으로 머릿속에 담고 있어야하는 내용인 ***rank factorization*** 에 대해서 알아보겠습니다.  
자세한 내용은 [https://en.wikipedia.org/wiki/Rank_factorization](https://en.wikipedia.org/wiki/Rank_factorization ) 를 참조하세요.

$ A = BC $ <br> such that $A \in \mathbb{R^{m \times n}}$, *rank $A$ = $r$*, $B \in \mathbb{R^{m \times r}}$, *rank $B$ = $r$*, $C \in \mathbb{R^{r \times n}}$, *rank $C$ = $r$*
{:.success}

1. Let $\mathbf{b}_i \doteq$ i'th basis of $\mathbb{C}(A)$  
$\mathbf{a}_j \doteq$ j'th column vector of $A$

2. $\mathbf{a}_j = \sum_{i=1}^r c_{ij} \mathbf{b}_i$  

3. $
\begin{pmatrix}  
  \\  
  \mathbf{a}_i\\  
  \\  
\end{pmatrix} = \begin{pmatrix}  
  \\  
  \mathbf{b}_1 & \cdots & \mathbf{b}_r\\  
  \\  
\end{pmatrix}  
\begin{pmatrix}  
  c_{11}\\  
  \vdots\\  
  c_{r1}\\  
\end{pmatrix}  
$

4. $
\begin{pmatrix}  
  \\  
  \mathbf{a}_i & \cdots & \mathbf{a}_n\\  
  \\  
\end{pmatrix}  
=
\begin{pmatrix}  
  \\  
  \mathbf{b}_1 & \cdots & \mathbf{b}_r\\  
  \\  
\end{pmatrix}  
\begin{pmatrix}  
  c_{11} & \cdots & c_{1n}\\  
  \vdots & \ddots & \vdots\\  
  c_{r1} & \cdots & c_{rn}\\  
\end{pmatrix}  
$

5. $A = BC$
