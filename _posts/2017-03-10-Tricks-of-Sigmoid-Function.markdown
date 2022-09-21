---
layout: post
title:  "Tricks of Sigmoid Function"
date:   2017-03-10 12:00:00 +0800
---

During my research of Bayesian Deep Models (integration of Bayesian graphical models with deep learning models), I found several handy tricks when dealing with sigmoid functions. Here, I summarize several for future use and also for other researchers who might find it useful.

### Variational Lower Bound on Sigmoid $\sigma(x)$


### Expectation of Sigmoid function with Normal distribution
Consider the following logistic-normal integral:

$$g=\int_{-\infty}^{\infty} \sigma(x)\mathcal{N}(x|\mu, \sigma^2) dx = \int_{-\infty}^{\infty} \frac{1}{1+e^{-x}} \frac{1}{\sigma \sqrt{2\pi}}e^{-\frac{(x-\mu)^2}{2\sigma^2}} dx.$$

The logistic-normal integral does not have analytic expression. However, for mathmatical simplicity, we can approximate the expectation. In the end, we will demonstrate that the integral is approximately a reparameterized logistic function.

First, we can approximate the sigmoid function with a probit function.

$$\sigma(x)\approx \Phi(\xi x), \text{where } \Phi(x)=\int_{-\infty}^x \mathcal{N}(\theta|0,1)d\theta, \text{and } \xi^2=\frac{\pi}{8}$$

A little fact is that the probit-normal integral is just another probit function:

$$\int \Phi(x) \mathcal{N}(x|\mu,\sigma^2) dx = \Phi(\frac{\mu}{\sqrt{1+\sigma^2}})$$

Therefore,

$$g\approx \int_{-\infty}^{\infty} \Phi(\xi x)\mathcal{N}(\mu, \sigma^2) dx = \Phi(\frac{\xi \mu}{\sqrt{1+\xi^2\sigma^2}})\approx \sigma(\frac{\mu}{\sqrt{1+\xi^2\sigma^2}}) = \sigma(\frac{\mu}{\sqrt{1+\pi\sigma^2/8}})$$

It actually means, given a normally distributed random variable $x$, the sigmoid of $x$ is approximately the sigmoid of $\mathbb{E}[x]$ with some adjustment by the variance.

### Some others
$$\tanh(x)=2\sigma(2x)-1$$