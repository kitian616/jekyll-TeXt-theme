---
title: '使用近端梯度下降法求解L1正则化'
date: 2020-04-17 17:26:45
tags: [算法,学术研究]
mathjax: true
---
# 0. INTRODUCTION
学正则化的时候, 基础的教材都会告诉初学者, 一般来说有$\ell-1$ -Lasso正则化, 也有$\ell-2$ -Ridge岭回归. 然后会比较一大通两者的优缺点. 比较注重数学的教材可能还会讲一讲$\ell-2$正则化怎么求解, 但几乎没有地方涉及到过$\ell-1$正则化的求解方法.

<!--more-->

我倒是也很纳闷, 为什么我也一直没有注意到过没人去关注他这件事情. 
其实原因很简单, 因为$\ell-1$(1范数)项是不可微的, 所以无论是梯度下降还是矩阵求解, 都挺难的. 而教材嘛, 一般都是关注普遍性而非特殊性的, 所以避而不谈也无可厚非. 

解决L1正则化问题的方式之一是使用近端梯度下降法(proximal gradident descent). 
> 近端梯度下降法是众多梯度下降 (gradient descent) 方法中的一种，其英文名称为proximal gradident descent，其中，术语中的proximal一词比较耐人寻味，将proximal翻译成“近端”主要想表达"（物理上的）接近"。与经典的梯度下降法和随机梯度下降法相比，近端梯度下降法的适用范围相对狭窄。对于凸优化问题，当其目标函数存在不可微部分（例如目标函数中有 $\ell-1$-范数或迹范数）时，近端梯度下降法才会派上用场。

上述中的proximal是一个解剖学用语, 为此我还特地查了一下proximal的含义: 
> Proximal means nearer to the center (trunk of the body) or to the point of attachment to the body. If another reference point is given, such as the heart, the proximal point of another organ or extremity is the point closest to the heart, central rather than peripheral. Proximal is the opposite of distal.

# 1. RELATED WORK
## 1.1 GRADIENT DESCENT 梯度下降
如果没有正则化, 最小二乘法为以下形式: 
给定$\mathbf{A} \in \mathbb{R}^{m \times n}$, $\mathbf{b} \in \mathbb{R}^{m},$, 找到$\mathbf{x} \in \mathbb{R}^{n}$满足: 

$$\min_{\mathbf{x} \in \mathbf{R}^{n}} \frac{1}{2}\|\mathbf{A} \mathbf{x}-\mathbf{b}\|_{2}^{2}$$

梯度下降法的形式为: 

$$\mathbf{x}_{k+1}=\arg \min_{\mathbf{x}}\left\{\frac{1}{2 t_{k}}\left\|\mathbf{x}-\left(\mathbf{x}_{k} - t_{k} \nabla f\left(\mathbf{x}_{k}\right)\right)\right\|_{2}^{2}\right\}$$

其中: 
- $f(\mathbf{x})=\frac{1}{2}\|\mathbf{A} \mathbf{x}-\mathbf{b}\|_{2}^{2}$
- $t_k > 0$ 是合适的步长
- $\nabla f\left(\mathbf{x}_{k}\right)$ 是$f$在$x_k$的梯度
- $k$ 是迭代次数, $k = 1, 2, \cdot$

## 1.2 SUBDIFFERENTIAL 次微分
次微分是用于解决凸函数的问题的. 
> 设$f$:$l\mapsto\R$是一个实变量凸函数，定义在实数轴上的开区间内。这种函数不一定是处处可导的，例如绝对值函数$f(x)=|x|$。但是，从右面的图中可以看出（也可以严格地证明），对于定义域中的任何$x_0$，我们总可以作出一条直线，它通过点$(x_0, f(x_0))$，并且要么接触$f$的图像，要么在它的下方。这条直线的斜率称为函数的次导数。
![凸函数（蓝）和x0处的“次切线”（红）](https://xtopia-1258297046.cos.ap-shanghai.myqcloud.com/1587174425007.png)

定义: 凸函数$f$:$l\mapsto\R$在点x0的次导数，是实数c使得：
 $$f(x)-f(x_{0})\geq c(x-x_{0})$$
对于所有$l$内的$x$。可以证明，在点$x_0$的次导数的集合是一个非空闭区间$[a, b]$，其中$a$和$b$是单侧极限

$$a=\lim_{{x\to x_{0}^{-}}}{\frac  {f(x)-f(x_{0})}{x-x_{0}}}$$

$$b=\lim_{{x\to x_{0}^{+}}}{\frac  {f(x)-f(x_{0})}{x-x_{0}}}$$

它们一定存在，且满足$a \leq b$。
所有次导数的集合$[a, b]$称为函数$f$在$x_0$的次微分。
例如$f(x)=|x|$的次微分为: $[-1,1]$.

# 2. METHOLOGY
一般而言, proximal gradient descent一般是用于解决如下优化问题: 
> 假设目标函数$f(\omega)=g(\omega)+h(\omega)$ 是由$g(\omega)$和 $h(\omega)$ 叠加而成, 其中，限定$g(\omega)$是可微的凸函数, $h(\omega)$是不可微 (或局部不可微) 的凸函数。

近端梯度下降法的递推公式为: 
$$\begin{aligned} \boldsymbol{w}^{k} &=\operatorname{prox}_{t h(\cdot)}\left(\boldsymbol{w}^{k-1}-t \nabla g\left(\boldsymbol{w}^{k-1}\right)\right) \\ &=\arg \min _{z} h(\boldsymbol{z})+\frac{1}{2 t}\left\|\boldsymbol{z}-\left(\boldsymbol{w}^{k-1}-t \nabla g\left(\boldsymbol{w}^{k-1}\right)\right)\right\|_{2}^{2}\\
&=\arg \min _{z} h(z)+\frac{t}{2}\left\|\nabla g\left(\boldsymbol{w}^{k-1}\right)\right\|_{2}^{2}+\nabla g\left(\boldsymbol{w}^{k-1}\right)^{\top}\left(\boldsymbol{z}-\boldsymbol{w}^{k-1}\right)+\frac{1}{2 t}\left\|\boldsymbol{z}-\boldsymbol{w}^{k-1}\right\|_{2}^{2}\\
&=\arg \min _{z} h(z)+g\left(\boldsymbol{w}^{k-1}\right)+\nabla g\left(\boldsymbol{w}^{k-1}\right)^{\top}\left(\boldsymbol{z}-\boldsymbol{w}^{k-1}\right)+\frac{1}{2 t}\left\|\boldsymbol{z}-\boldsymbol{w}^{k-1}\right\|_{2}^{2}\\
&\approx \arg \min _{\bar{x}} h(z)+g(z) \end{aligned}$$

其中$\operatorname{prox}_{t h(\cdot)}(\boldsymbol{w})$为关于变量$\omega$和函数$h(\cdot)$的近端算子(proximal operator). 这条更新公式通常也被称为迭代软阈值算法 (iterative soft-thresholding algorithm, ISTA). 

## 2.1 PROXIMAL OPERATOR近端算子
$$\begin{aligned}
\operatorname{prox}_{t h(\cdot)}(\boldsymbol{w}) &=\arg \min _{z} \frac{1}{2 t}\|\boldsymbol{w}-\boldsymbol{z}\|_{2}^{2}+\lambda\|\boldsymbol{z}\|_{1} \\
&=\arg \min _{z} \frac{1}{2}\|\boldsymbol{w}-\boldsymbol{z}\|_{2}^{2}+\lambda t\|\boldsymbol{z}\|_{1} \\
&=\mathcal{S}_{\lambda t}(\boldsymbol{w})
\end{aligned}$$
其中, $\mathcal{S}_{\lambda t}(\boldsymbol{w}) \in \mathbb{R}^{n}$表示关于变量$\omega$的软阈值(soft-thresholding)函数. 

> 这条公式想表达的意思是：对于任意给定的$\boldsymbol{w} \in \mathbb{R}^{n}$，我们希望找到使得$\frac{1}{2}\|\boldsymbol{w}-\boldsymbol{z}\|_{2}^{2}+\lambda t\|\boldsymbol{z}\|_{1}$最小化的解$\boldsymbol{u}=\operatorname{prox}_{t h(\cdot)}(\boldsymbol{w})$，其中， $t$是一个新增参数，表示近端梯度下降的步长 (step size)。实际上，近端算子只和不可微的凸函数$h(\boldsymbol{w})$有关 (如的$\|\cdot\|_{1}$)。

## 2.2  SOFT-THREHOLDING 软阈值
当不可微的凸函数形式为$h(\boldsymbol{w})=\|\boldsymbol{w}\|_{1}$时, 对应的软阈值函数为: 
$$\left[\mathcal{S}_{t}(\boldsymbol{w})\right]_{i}=\left\{\begin{array}{ll}
w_{i}-t, & \text { if } w_{i}>t \\
0, & \text { if }\left|w_{i}\right| \leq t \\
w_{i}+t, & \text { if } w_{i}<-t
\end{array}\right.$$

若$h(\boldsymbol{w})=\lambda\|\boldsymbol{w}\|_{1}$, 软阈值函数为$\mathcal{S}_{\lambda t}(\cdot)$: 
$$\left[\mathcal{S}_{\lambda t}(\boldsymbol{w})\right]_{i}=\left\{\begin{array}{ll}
w_{i}-\lambda t, & \text { if } w_{i}>\lambda t \\
0, & \text { if }\left|w_{i}\right| \leq \lambda t \\
w_{i}+\lambda t, & \text { if } w_{i}<-\lambda t
\end{array}\right.$$

matlab代码为: `sign(omega).*(max(abs(omega)-lambda*t,0));`

# 3. EXPRIMENT
以线性回归问题为例, 给定$\boldsymbol{X} \in \mathbb{R}^{m \times n}$, $\boldsymbol{y} \in \mathbb{R}^{m}$,  若线性回归的表达式为$\boldsymbol{y}=\boldsymbol{X} \boldsymbol{w}$. 其中$\boldsymbol{w} \in \mathbb{R}^{n}$表示系数向量, 则含$\ell-1$正则项的优化目标函数可以写成如下形式. 
$$f(\boldsymbol{w})=\underbrace{\frac{1}{2}\|\boldsymbol{y}-\boldsymbol{X} \boldsymbol{w}\|_{2}^{2}}_{g(\boldsymbol{w})}+\underbrace{\lambda\|\boldsymbol{w}\|_{1}}_{h(\boldsymbol{w})}$$

在这个目标函数中, 正则项中的$\ell-1$范数是一个不可微的凸函数. 可以用近端梯度下降法对该类优化问题求解. 由于$\nabla g(\boldsymbol{w})=-\boldsymbol{X}^{\top}(\boldsymbol{y}-\boldsymbol{X} \boldsymbol{w})$, 根近端梯度下降的递推公式可以推导出变量$\boldsymbol{w}$的近端梯度更新公式: 
$\begin{aligned} \boldsymbol{w}^{k} &=\mathcal{S}_{\lambda t}\left(\boldsymbol{w}^{k-1}-t \nabla g\left(\boldsymbol{w}^{k-1}\right)\right) \\ &=\mathcal{S}_{\lambda t}\left(\boldsymbol{w}^{k-1}+t \boldsymbol{X}^{\top} \boldsymbol{y}-t \boldsymbol{X}^{\top} \boldsymbol{X} \boldsymbol{w}^{k-1}\right) \end{aligned}$


完整的代码实现可以参见: [Simple Matlab Solver for l1-regularized Least Squares Problems](https://web.stanford.edu/~boyd/l1_ls/)

参考资料: 
[1] [机器学习 | 近端梯度下降法 (proximal gradient descent)](https://zhuanlan.zhihu.com/p/82622940)
[2] [(Graphical) Derivation of the soft thresholding operator](https://angms.science/doc/CVX/ISTA0.pdf)
[3] [维基百科-次导数](https://zh.wikipedia.org/wiki/%E6%AC%A1%E5%AF%BC%E6%95%B0)
