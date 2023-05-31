---
layout: article
title: 【信号与系统】连续时间周期信号的傅里叶级数
permalink: /article/:title.html
key: continuous-time-periodic-signal-fourier-series
tags: 
  - 信号与系统
  - 数学
  - 傅里叶级数
author: Yu Xiaoyuan
show_author_profile: true
license: WTFPL
---

傅里叶级数是傅里叶变换的基础, 深入理解傅里叶级数能帮助读者更全面得建立对频域的理解.

<!--more-->

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- end private variable of Liquid -->

## 原题

原题摘录如下。

### English version

>**3.41.** Suppose we are given the following information about a continuous-time periodic signal with period $3$ and Fourier coefficients $a_k$:
>
>1. $ a_k = a_{k+2} $.
>2. $ a_k = a_{-k} $.
>3. $ \int_{-0.5}^{0.5} x(t) dt = 1 $.
>4. $ \int_1^2 x(t) dt = 2 $.
>
>Determine $x(t)$.

### 汉语翻译

>**3.41.** 关于一个周期为$3$和傅里叶系数为$a_k$的连续时间周期信号, 给出下列信息: 
>
>1. $ a_k = a_{k+2} $.
>2. $ a_k = a_{-k} $.
>3. $ \int_{-0.5}^{0.5} x(t) dt = 1 $.
>4. $ \int_1^2 x(t) dt = 2 $.
>
>试确定$x(t)$.

## 题解

根据题设条件, 对任意整数$k$,

$$
\begin{gather*}
  a_{2k} = a_0, \\
  a_{2k-1} = a_1.
\end{gather*}
$$

据此展开$x(t)$的傅里叶级数.

$$
\begin{aligned}
  x(t) & = \sum_{k = - \infty}^{+\infty} a_k e^{j \;k\; \omega_0 t} \\
  & = \sum_{k = - \infty}^{+\infty} a_{2k} e^{j \; 2k \; \omega_0 t} + \sum_{k = - \infty}^{+\infty} a_{2k-1} e^{j \; (2k-1) \; \omega_0 t} \\
  & = a_{0} \sum_{k = - \infty}^{+\infty}  e^{j \; k \; 2\omega_0 t} + a_{1} e^{j \omega_0 t} \sum_{k = - \infty}^{+\infty}  e^{j \; k \; 2\omega_0 t} \\
  & = \left( a_{0} + a_{1} e^{j \omega_0 t} \right) \sum_{k = - \infty}^{+\infty}  e^{j \; k \; 2\omega_0 t}
\end{aligned}
$$

其中$ \omega_0 = \frac{2\pi}{3} $.

注意到该展开式的形式, 求和式内每一项的系数相等, 有这个性质的周期信号只有周期冲激串(梳状函数)即

$$
\begin{equation*}
   \sum_{n = -\infty}^{+\infty} \delta(t - \frac{\pi}{\omega_0} n) = \sum_{k = -\infty}^{+\infty} \frac{\omega_0}{\pi} e^{j \; k \; 2\omega_0 t}.
\end{equation*}
$$

因而

$$
\begin{aligned}
  x(t) & = \left( a_{0} + a_{1} e^{j \omega_0 t} \right) \frac{\pi}{\omega_0} \sum_{n = -\infty}^{+\infty} \delta(t - \frac{\pi}{\omega_0} n) \\
  & = \frac{3}{2} \sum_{n = -\infty}^{+\infty} \left( a_{0} + a_{1} e^{j \frac{2\pi}{3} t} \right) \delta(t - \frac{3}{2} n) \\
  & = \frac{3}{2} \sum_{n = -\infty}^{+\infty} \left( a_{0} + a_{1} e^{j n \pi} \right) \delta(t - \frac{3}{2} n) \\
  & = \frac{3}{2} \sum_{n = -\infty}^{+\infty} \left( a_{0} + (-1)^n a_{1} \right) \delta(t - \frac{3}{2} n)
\end{aligned}
$$

根据题设条件,

$$
\begin{aligned}
  \int_{-0.5}^{0.5} x(t) dt & = \frac{3}{2} \left( a_{0} + a_{1} \right) = 1 \\
  \int_{1}^{2} x(t) dt & = \frac{3}{2} \left( a_{0} - a_{1} \right) = 2
\end{aligned}
$$

解得

$$
\begin{aligned}
  a_0 & = 1 \\
  a_1 & = -\frac{1}{3}
\end{aligned}
$$

因而

$$
\begin{aligned}
  x(t) & = \sum_{n = -\infty}^{+\infty} \left( \frac{3}{2} - \frac{(-1)^n}{2} \right) \delta(t - \frac{3}{2} n) \\
  & = \sum_{k = -\infty}^{+\infty} \left[ \delta(t - 3k) + 2 \delta( t - 3k - \frac{3}{2} ) \right]
\end{aligned}
$$

## 总结

从结果观察不难发现原信号是离散的, 考虑到傅里叶系数的周期性($a_k = a_{k+2}$), 这一点是符合预期的, 因为离散时间周期信号的傅里叶系数是周期的.  
与本题对应的离散周期信号为$ y[n] = \frac{3}{2} - \frac{(-1)^n}{2} $, 基频周期$N = 2$, 若设置相邻采样点$n$之间的时间间隔为$\frac{3}{2}$就可以得到$x(t)$的表达式.  
此时考虑到采样周期和离散基频周期, $x(t)$的周期$ T = 3 $.
