---
layout: article
title: Regular matrix and singular matrix
tags: Math
sidebar:
  nav: docs-en
aside:
  toc: true
---

**Regular matrix / Singular matrix** <br>
Square matrix (정방행렬) 중에서 역행렬이 존재하는 행렬을 **regular matrix (정칙행렬)**라고 하고 역행렬이 존재하지 않는 행렬을 **singular matrix (특이행렬)**라고 합니다.
{:.success}

<!--more-->

---

행렬 $A \in \mathbb{R^{n \times n}}: X \in \mathbb{R^n} → Y \in \mathbb{R^n}$가 **정칙행렬**이라는 말에는 여러가지 중요한 의미들이 포함되어 있습니다.

# $A$ is a *regular matrix* means..
### 1. $A^{-1}$가 존재한다.

### 2. $A$는 injective mapping (단사 사상)이다.
1) 서로 다른 $\mathbf{x_1}$과 $\mathbf{x_2}$가 동일한 $\mathbf{y}$에 mapping되지 않는다. <br>
2) *Ker A* = $\mathbb{0}$ <br>
3) *dim Ker A* = $0$

### 3. $A$는 surjective mapping (전사 사상)이다.
1) $A$의 사상에 의해 이동된 $X$는 $Y$공간을 전부 포함한다. <br>
2) *Im A* = $\mathbb{R^n}$ <br>
3) *dim Im A = rank A* = $n$

### 4. $A$의 column (row) vector들이 linearly independent하다.

### 5. *det A $\neq 0$*

### 6. $A$의 eigenvalue 중 $0$이 존재하지 않는다.
