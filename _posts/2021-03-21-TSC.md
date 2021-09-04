---
title: Topological invariants for the Fermi surface of a time-reversal-invariant superconductor
tags: Topology 
layout: article
license: true
toc: true
key: a20210320
pageview: true
header:
  theme: dark
  background: 'linear-gradient(135deg, rgb(34, 139, 87), rgb(139, 34, 139))'
article_header:
  type: overlay
  theme: dark
  background_color: '#123'
  background_image: false
aside:
    toc: true
sitemap: true
mathjax: true
author: YuXuan
show_author_profile: true
---
这篇博客主要是想整理一下一篇文献的内容，依据文章内容，可以仅仅通过费米面的拓扑性质和其上的超导电子配对符号，来直接判定这个超导系统是否为拓扑超导，而且结论可以适用于不同的维度，其中的公式表达也是非常漂亮，故而整理精华，也加深一下自己的理解。
<!--more-->
这个判据主要是来对时间反演不变的超导系统进行诊断，对于超导的哈密顿量而言，其还会具有粒子空穴对称性，在这两个对称性的保证下就会使得哈密顿量可以有一些不一样的性质。
{:.info}

这里就不翻译文章的内容了，只是将判据整理出来，再把符号含义解释一下。
# 3D判据
对于3D的拓扑超导体，可以利用一个整数的拓扑不变量来描述

$$N_w=\frac{1}{24\pi^2}\int d^3k\epsilon^{ijk}\text{Tr}\left[Q^\dagger_{\bf k}\partial_i Q_{\bf k}Q^\dagger_{\bf k}\partial_j Q_{\bf k}Q^\dagger_{\bf k}\partial_k Q_{\bf k}\right]$$

$$\delta_{nk}\equiv\langle n,{\bf k}\rvert\mathcal{T}\Delta^\dagger_{\bf k}\rvert n,{\bf k}]\rangle$$

这一项代表的是在某个能带上，电子配对的大小。文章中讨论的都是弱配对极限，也就是说电子配对仅仅是在费米面很小的范围内进行，所以自然就是忽略了费米面之间的电子配对，而通常情况下，费米面都是由一条独立的能带贡献的，所以这里也就仅仅考虑了带内的电子配对。

根据文章中的讨论，最后可以将这个$N_w$化简为

$$N_w=\frac{1}{2}\sum_s\text{sgn}(\delta_s)C_{1s}$$

$$\begin{equation}\begin{aligned}C_{1s}&=\frac{1}{2\pi}\int_{\text{FS}_s}d\Omega^{ij}\left[\partial_ia_{sj}({\bf k})-\partial_ja_{si}({\bf k})\right]\\
a_{si}&-i\langle s{\bf k}\rvert\partial/\partial k_i\rvert s{\bf k}\rangle\end{aligned}\end{equation}$$

这里的$s$代表的是费米面的索引，也就说需要对所有费米面的贡献求和。$\text{sng}(\delta_s$)代表的是$\delta_{nk}$在第$s$个费米面上的符号。如上面的讨论，费米面此时相互独立，每个费米面上都会存在电子配对。而具体电子配对的符号就取决于体系的费米面位置和电子配对形式。$C_{1s}$是最熟悉的Chern数，同样的我们需要对每个独立的费米面计算其Chern数。在3D体系中，费米面上的电子配对符号和费米面上的Chern数共同决定了提示是否为拓扑。这是一个非常简单的判据，只需要根据正常态的能带计算和超导态的电子配对分析就可以得到体系是否为拓扑的。
# 2D判据
对于2D体系，其拓扑不变量是$Z_2$，此时判据变得更加简单

$$N_{2D}=\Pi_s\left[\text{sgn}(\delta_s)\right]^{m_s}$$

这里的$m_s$就是第$s$个费米面包含的时间反演不变动量点的数目。这里因为时间反演不变的存在，所以对所有费米面贡献的Chern数进行求和之后一定为0。$\sum_sC_{1s}=0$

a 2D TRI superconductor is nontrivial (trivial) if there are an odd (even) number of Fermi surfaces each of which encloses one TRI point in the Brillouin zone and has negative pairing.
{:.success}

参考中的第二篇文章即为Rashba系统中，利用这个判据来判断系统是否为拓扑超导。
# 1D判据

$$N_{1D}=\Pi_s\left[\text{sgn}(\delta_s)\right]$$

Since in 1D each Fermi “surface”  which consists of two points at $k_f $and $−k_F$ always encloses one TRI invariant point. 

where $s$ is summed over all the Fermi points between 0 and $\pi$. In other words, a 1D TRI superconductor is nontrivial (trivial) if there are an odd number of Fermi points between 0 and $\pi$ with negative pairing.
{:.success}




# 参考
1. [Topological invariants for the Fermi surface of a time-reversal-invariant superconductor](https://journals.aps.org/prb/abstract/10.1103/PhysRevB.81.134508)
2. [Time-Reversal-Invariant Topological Superconductivity and Majorana Kramers Pairs](https://journals.aps.org/prl/abstract/10.1103/PhysRevLett.111.056402)
