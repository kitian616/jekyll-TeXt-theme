---
layout: article
title: 【信号与系统】齐次常微分方程与多项式
permalink: /article/:title.html
key: differential-equation-and-polynomial
tags: 
  - 数学
  - 信号与系统
  - 微分方程
  - 常微分方程
  - 多项式
author: Yu Xiaoyuan
show_author_profile: true
license: WTFPL
---

最近在做《信号与系统（第二版）》（奥本海默）的习题，这个习题解决了笔者多年的困扰。

<!--more-->

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- end private variable of Liquid -->

## 原题

原题摘录如下。

### English version

>**2.53. (a)** Consider the homogeneous differential equation
>
>$$
>\begin{equation}
>    \sum_{k=0}^N a_k \frac{d^k y(t)}{dt^k} = 0.
>\end{equation}\tag{P2.53-1}
>$$
>
>Show that if &s_0$ is a solution of the equation
>
>$$
>\begin{equation}
>    p(s) = \sum_{k=0}^N a_k s^k = 0,
>\end{equation}\tag{P2.53-2}
>$$
>
>then $Ae^{s_0t}$ is a solution of eq.$(P2.53-1)$, where $A$ is an arbitrary complex constant.
>
>**(b)** The polynomial $p(x)$ in eq.$(P2.53-2)$ can be factored in terms of its roots $s_1,\cdots,s_r$ as
>
>$$
>\begin{equation*}
>    p(s) = a_N(s-s_1)^{\sigma_{1}}(s-s_2)^{\sigma_{2}}\cdots(s-s_r)^{\sigma_{r}} ,
>\end{equation*}
>$$
>
>where the $s_i$ are the distinct solutions of eq.$(P2.53-2)$ and the $\sigma_i$ are their *multiplicities*
>--that is, the number of times each root appears as a solution of the equation. Note that
>
>$$
>\begin{equation*}
>    \sigma_1 + \sigma_2 + \cdots + \sigma_r = N.
>\end{equation*}
>$$
>
>In general, if $\sigma_i > 1$, then not only is $A^{s_i t}$ a solution of eq.$(P2.53-1)$,
>but so is $At^je^{s_it}$, as long as $j$ is an interger greater than or equal to zero and less than or equal to $\sigma_i-1$.
>To illustrate this, show that if $\sigma_i=2$, then $Ate^{s_it}$ is a solution of eq.$(P2.53-1)$.
>[*Hint*:Show that if $s$ is an arbitrary complex number, then
>
>$$
>\begin{equation*}
>    \sum_{k=0}^N \frac{d^k(Ate^{st})}{dt^k} = Ap(s)te^{st} + A \frac{dp(s)}{ds}e^{st}.]
>\end{equation*}
>$$
>
>Thus, the most general solution of eq.$(P2.53-1)$ is
>
>$$
>\begin{equation*}
>    \sum_{i=1}^N \sum_{j=0}^{\sigma_i-1} A_{ij}t^j e^{s_i t},
>\end{equation*}
>$$
>
>where the $A_{ij}$ are arbitrary complex constants.

### 汉语翻译

>**2.53. (a)** 有齐次微分方程
>
>$$
>\begin{equation}
>    \sum_{k=0}^N a_k \frac{d^k y(t)}{dt^k} = 0.
>\end{equation}\tag{P2.53-1}
>$$
>
>证明：若 $s_0$ 是方程
>
>$$
>\begin{equation}
>    p(s) = \sum_{k=0}^N a_k s^k = 0,
>\end{equation}\tag{P2.53-2}
>$$
>
>的一个解，则 $Ae^{s_0t}$ 是方程$(P2.53-1)$的一个解, 其中 $A$ 是任意复常数.
>
>**(b)** 式$(P2.53-2)$ 的多项式 $p(x)$ 可以根据方程的根 $s_1,\cdots,s_r$ 进行因式分解为
>
>$$
>\begin{equation*}
>    p(s) = a_N(s-s_1)^{\sigma_{1}}(s-s_2)^{\sigma_{2}}\cdots(s-s_r)^{\sigma_{r}} ,
>\end{equation*}
>$$
>
>其中 $s_i$ 是方程$(P2.53-2)$ 的互异根, 而 $\sigma_i$ 是 重根数(即在解中每个根出现的次数). 应该有
>
>$$
>\begin{equation*}
>    \sigma_1 + \sigma_2 + \cdots + \sigma_r = N.
>\end{equation*}
>$$
>
>一般来说, 若 $\sigma_i > 1$, 那么不仅 $A^{s_i t}$ 是方程 $(P2.53-1)$ 的一个解,
>而且 $At^je^{s_it}$ 也是它的解, 只要 $j$ 是整数且 $0 \leq j \leq \sigma_i-1$.
>为了说明这一点, 证明: 若 $\sigma_i=2$, 则 $Ate^{s_it}$ 就是方程$(P2.53-1)$的一个解.
>[*提示*：证明, 若 $s$ 是某任意复数, 则
>
>$$
>\begin{equation*}
>    \sum_{k=0}^N \frac{d^k(Ate^{st})}{dt^k} = Ap(s)te^{st} + A \frac{dp(s)}{ds}e^{st}.]
>\end{equation*}
>$$
>
>因此, 方程$(P2.53-1)$ 的最一般解是
>
>$$
>\begin{equation*}
>    \sum_{i=1}^N \sum_{j=0}^{\sigma_i-1} A_{ij}t^j e^{s_i t},
>\end{equation*}
>$$
>
>其中 $A_{ij}$ 是任意复常数.

## 题解

### **(a)** 多项式的单根

只需将函数$A e^{s_0t}$代入到微分方程左侧，并证明左侧为$0$即可完成证明。那么证明关键就是函数的连续求导。

注意到对 $k\geq 0$ 有

$$
\begin{equation*}
    \frac{d^k}{dt^k} e^{s_0t} = s_0^k e^{s_0t}
\end{equation*}
$$

则

<!-- $$
\begin{equation*}
    \sum_{k=0}^N a_k \frac{d^k}{dt^k} \left[ A e^{s_0t} \right] = A\sum_{k=0}^N a_k s_0^k e^{s_0t}
\end{equation*}
$$ -->

$$
\begin{aligned}
    \sum_{k=0}^N a_k \frac{d^k}{dt^k} \left[ A e^{s_0t} \right] & = A\sum_{k=0}^N a_k s_0^k e^{s_0t} \\
    & = A e^{s_0t} \sum_{k=0}^N a_k s_0^k \\
    & = A e^{s_0t} p(s_0) = 0
\end{aligned}
$$

所以函数$A e^{s_0t}$是方程$(P2.53-1)$的一个解。

### **(b)** 多项式的重根

与[题解a](#a-多项式的单根)类似，关键仍是函数的连续求导

为了方便描述，设对 $n \geq 1$ ，

$$
\begin{equation*}
    x[n] = \frac{d}{dt} x[n-1]
\end{equation*}
$$

其中$x[0] = te^{st}$。

注意到

$$
\begin{aligned}
    x[0] &= te^{st} \\
    x[1] &= e^{st} + ste^{st} \\
    x[2] &= 2se^{st} + s^2te^{st} \\
    x[3] &= 3s^2e^{st} + s^3te^{st}
\end{aligned}
$$

数学归纳法，易知 $x[n] = ns^{n-1}e^{st} + s^nte^{st}$对$n=0,1,2,3$ 成立 。
假设 $x[n] = ns^{n-1}e^{st} + s^nte^{st}$对$n=k(k\geq 0)$ 成立即 $x[k] = ks^{k-1}e^{st} + s^kte^{st}$，只需证明 $n=k+1$ 亦成立。

$$
\begin{aligned}
    x[k+1] &= \frac{d}{dt} x[k] \\
    &= \frac{d}{dt} \left[ ks^{k-1}e^{st} + s^kte^{st} \right] \\
    &= ks^{k}e^{st} + s^ke^{st} + s^{k+1}te^{st} \\
    &= (k+1)s^{(k+1)-1}e^{st} + s^{k+1}te^{st}
\end{aligned}
$$

由此可以推知， $x[n] = ns^{n-1}e^{st} + s^nte^{st}$对$n \geq 0$ 成立 。

$$
\begin{aligned}
    \sum_{k=0}^N a_k \frac{d^k}{dt^k} \left[ A te^{st} \right] & = A\sum_{k=0}^N a_k x[k] \\
    & = A \sum_{k=0}^N a_k \left( ks^{k-1}e^{st} + s^kte^{st} \right) \\
    & = A \left( e^{st} \sum_{k=0}^N a_k ks^{k-1} + te^{st} \sum_{k=0}^N a_k s^k \right) \\
    & = A \left( e^{st}\frac{d}{ds}p(s) + te^{st} p(s) \right)
\end{aligned}
$$

其中注意到

$$
\begin{aligned}
    p'(s) &= \frac{d}{ds}p(s) \\
    &= \frac{d}{ds} \left[ a_N(s-s_1)^{\sigma_{1}}(s-s_2)^{\sigma_{2}}\cdots(s-s_r)^{\sigma_{r}} \right] \\
    &= \sigma_i a_N(s-s_1)^{\sigma_{1}}(s-s_2)^{\sigma_{2}} \cdots (s-s_i)^{\sigma_i - 1} \cdots (s-s_r)^{\sigma_{r}} \\
    & \quad + a_N (s-s_i)^{\sigma_i} \frac{d}{ds} \left[ (s-s_1)^{\sigma_{1}}(s-s_2)^{\sigma_{2}} \cdots (s-s_{i-1})^{\sigma_{i-1}}(s-s_{i+1})^{\sigma_{i+1}} \cdots (s-s_r)^{\sigma_{r}} \right]
\end{aligned}
$$

当$\sigma_i=2$时，$p(s_i) = 0, p'(s_i) = 0$。因此

$$
\begin{equation*}
    \sum_{k=0}^N a_k \frac{d^k}{dt^k} \left[ A te^{s_it} \right] = 0
\end{equation*}
$$

因此$A te^{s_it}$满足微分方程，是微分方程的一个解。

## 总结

不难发现方程$p(s)=0$是方程$(P2.53-1)$的特征根方程，这与高数课上学的内容是类似的，而$s_i$就是特征根。
当特征根为重根时，需要在指数函数前乘一个多项式来保证分解基底的完备性。本题恰好证明了重根乘多项式的合理性。
