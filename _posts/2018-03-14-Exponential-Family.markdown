---
layout: post
title:  "Exponential Family"
date:   2018-03-14 12:00:00 +0800
---

The exponential family distributions are very important in graphical models and Bayesian learning. They have nice properties, like conjugacy and finite sufficient statistics, which enable the convenience and efficiency of the inference and learning process. Yet, I only almost know the details of exponential family distribution. The minor gap between "almost know" and "know" prevents me from understanding it completely. Here I try to close the gap.

## Basics

The canonical/natural form of exponential family distribution is of the form

$$p(x|\theta) = \frac{1}{Z(\theta)}h(x)\exp[\theta^T \phi(x)] = h(x)\exp[\theta^T \phi(x) - A(\theta)]$$

where $\theta$ is the natural parameter, $\phi(x)$ is sufficient statistics, $Z(\theta)$ is the partition function, $A(\theta)$ is the log partition function. Let's write the univariate Gaussian distribution in  exponential family form

$$\mathcal{N}(x|\mu, \sigma^2) = \frac{1}{\sqrt{2\pi \sigma^2}}\exp[-\frac{1}{2\sigma^2}(x-\mu)^2] = \frac{1}{\sqrt{2\pi \sigma^2}}\exp[-\frac{1}{2\sigma^2}\mu^2]\exp[-\frac{1}{2\sigma^2}x^2 + \frac{\mu}{\sigma^2}x]$$

Therefore, it is easy to tell that

$$\theta = \begin{pmatrix} \frac{\mu}{\sigma^2} \\ -\frac{1}{2\sigma^2}\end{pmatrix} \\
\phi(x) = \begin{pmatrix}x \\ x^2\end{pmatrix}\\
Z(\theta) = \sqrt{2\pi \sigma^2}\exp[-\frac{1}{2\sigma^2}\mu^2]$$

There is one beautiful property of the log partition function $\nabla_{\theta}A(\theta) = \mathbb{E}_{p(x)}[\phi(x)]$.

## MLE Estimation

Given dataset $\mathcal{D}=\{x_1, x_2,\cdots,x_N\}$, the data likelihood of an exponential family model has the form

$$p(\mathcal{D}|\theta) = [\prod_{i=1}^N h(x_i)] \frac{1}{Z(\theta)^N} \exp[\theta^T (\sum_{i=1}^N \phi(x_i))] $$

We see that the sufficient statistics are $N$ and $\sum_{i=1}^N \phi(x_i)$. The loglikelihood of the data $\log p(\mathcal{D}\|\theta) = \theta^T \phi(\mathcal{D}) - N A(\theta)$ is concave since $-A(\theta)$ is concave in $\theta$, and $\theta^T \phi(\mathcal{D})$ is linear in $\theta$. To compute MLE for the model, we can compute the derivative of the log-likelihood:

$$\nabla_{\theta} \log p(\mathcal{D}|\theta) = \phi(\mathcal{D}) - N \nabla_{\theta}A(\theta) = \phi(\mathcal{D}) - N \mathbb{E}_{p(X|\theta)}[\phi(X)]$$

Setting this gradient to zero, we see that the empirical average of the sufficient statistics must equal the model's theoretical expected sufficient statistics, i.e. $\hat{\theta}$ must satiesfy

$$\mathbb{E}_{p(X|\hat{\theta})}[\phi(X)] = \frac{1}{N}\sum_{i=1}^N \phi(x_i)$$

We need to solve this equation for specific type of distribution, which I will give some examples.

## MLE for Gaussian distribution

For Gaussian distribution, following above, the MLE corresponds to solve 

$$\mathbb{E}[\begin{pmatrix} X \\ X^2 \end{pmatrix}] = \begin{pmatrix} \mu \\ \sigma^2 + \mu^2 \end{pmatrix} = \begin{pmatrix} \frac{1}{N}\sum_{i=1}^N x_i \\ \frac{1}{N}\sum_{i=1}^N x_i^2 \end{pmatrix}$$

Solving this, we have

$$\mu = \frac{1}{N}\sum_{i=1}^N x_i, \quad \sigma^2 = \frac{1}{N}\sum_{i=1}^N x_i^2 - \mu^2$$

## EM for Gaussian Mixture

For Gaussian mixture, we can use EM to perform MLE. But first, we should identify the MLE under complete data situation. We know that $p(x\|\theta) = \sum_k p(z_k, x) = \sum_k p(z^k=1\|\pi) p(x\|z^k=1,\mu,\Sigma) = \sum_k \pi_k\mathcal{N}(x\|\mu_k, \Sigma_k))$. If we assume that all variables are observed, we can learn the parameters simply by using MLE. The data likelihood is

$$p(x,z) = p(x|z,\mu, \sigma)p(z|\pi) = \prod_k \pi_k^{z^k} {\mathcal{N}(x|\mu_k,\sigma_k)}^{z^k} \\
\log p(x,z) = \sum_k z^k \log \pi_k - z^k \frac{1}{2\sigma_k^2}(x-\mu_k)^2 + C$$

Obviously, the sufficient statistics and natural parameter can be identified:

$$\phi(x, z) = \begin{pmatrix} z^k \\ z^kx \\ z^k x^2 \end{pmatrix}\\
\theta_k = \begin{pmatrix} \log\pi_k \\ \frac{\mu_k}{\sigma_k^2} \\ -\frac{1}{2\sigma^2}\end{pmatrix}$$

Here we are also able to compute the expected sufficient statistics of the random variable $X$ and $Z$

$$\mathbb{E}_{p(x,z)}[z^k] = \pi_k, \quad \mathbb{E}_{p(x,z)}[z^k x] = \pi_k\mu_k,\quad \mathbb{E}_{p(x,z)}[z^k x^2] = \pi_k(\sigma^2 + \mu_k^2)$$

Using the MLE rule, given the dataset $\mathcal{D}=\{x_1, x_2,\cdots,x_N\}$, we need to set the $\mathbb{E}[\phi(X)] = \frac{1}{N}\sum_{i=1}^N\phi(x_i)$. This leads to

$$\pi_k = \frac{1}{N}\sum_i z_i^k, \quad \pi_k\mu_k = \frac{1}{N}\sum_i z_i^k x_i, \quad \pi_k(\sigma_k^2 + \mu_k^2) = \frac{1}{N}\sum_i z_i^k x_i^2 \\
\pi_k = \frac{1}{N}\sum_i z_i^k, \quad \mu_k = \frac{\sum_i z_i^k x_i}{\sum_i z_i^k}, \quad \sigma_k^2 = \frac{\sum_i z_i^k x_i^2}{\sum_i z_i^k} - \mu_k^2$$

Note that the derivation is different from P351 in Murphy's book, where one has to enforce the constraints to derive the maximization of $\pi$. Here since we use the MLE property in exponential family, it simplifies the derivation a lot. The results are exactly the same. Now that we have identified the rule for converting the sufficient statistics to the MLE of the parameters, we can now use the EM. In the E-step, we complete the data by computing the posterior distribution of $z$ under current parameter setting $p(z_i^k=1\|x_i,\theta^t)$

$$r_{ik} = p(z_i^k=1|x_i,\theta^t) = \frac{\pi_k p(x_i|\theta_k^{t})}{\sum_{k'}\pi_{k'}p(x_i|\theta_{k'}^t)}$$

In the M-step, we compute optimize the expected complete data loglikelihood, i.e. MLE. This corresponds to take the expectation of the sufficient statistics over $p(z_i^k=1\|x_i,\theta^t)$. Then we follow the MLE estimation using the expected sufficient statistics, i.e.

$$\pi_k = \frac{1}{N}\sum_i r_{ik}, \quad \mu_k = \frac{\sum_i r_{ik} x_i}{\sum_i r_{ik}}, \quad \sigma_k^2 = \frac{\sum_i r_{ik} x_i^2}{\sum_i r_{ik}} - \mu_k^2$$

## Stepwise EM for Gaussian Mixture

The reason why I tried to figure out the derivation of Gaussian mixture using sufficient statistics, instead of the one presented in the book, is that using sufficient statistics will lead to more general optimization method, such as stepwise EM and stochastic variational inference. Stepwise EM is computed as follows

$$\text{While not converged} \\\text{for each example $i=1:N$ in a random order do}\\
s_i = \sum_z p(z|x_i, \theta(\mu))\phi(x_i,z);\\
\mu = (1-\eta_k)\mu + \eta_k s_i;\\
t = t+1$$

Since we are able to convert sufficient statistics into the MLE of the parameters as above, we only need to update the sufficient statistics $\mu$. Therefore, the Stepwise EM for Gaussian Mixture is as follows

$$
\text{While not converged} \\
\text{For each example $x_i$ and component $k$ do}\\
\text{E-step:}\quad r_{ik} = p(z_i^k=1|x_i,\theta^t) \\
\text{M-step:}\quad \hat{\omega}_k = \hat{\omega}_k + \eta_t (r_{ik} - \hat{\omega}_k) \\
\hat{\mu}_k = \hat{\mu}_k + \eta_t (r_{ik} x_i - \hat{\mu}_k) \\
\hat{S}_k = \hat{S}_k + \eta_t (r_{ik} x_ix_i^T - \hat{S}_k) \\
\text{Update parameter}\quad \pi_k^{t+1} = \hat{\omega}_k, \quad \mu_k^{t+1} = \frac{\hat{\mu}_k}{\omega_k}, \quad \Sigma^{t+1} = \frac{\hat{S}_k}{\omega_k} - {\mu_k^{t+1}}^2 \\
t = t+1
$$

For Stepwise EM with minibatch of size $m$, the algorithm is as follows:

$$
\text{While not converged} \\
\text{For each minibatch $X$ of size $M$ and component $k$ do}\\
\text{E-step:}\quad r_{ik} = p(z_i^k=1|x_i,\theta^t) \\
\text{M-step:}\quad \hat{\omega}_k = \hat{\omega}_k + \eta_t (\sum_{i=1}^M r_{ik} - \hat{\omega}_k) \\
\hat{\mu}_k = \hat{\mu}_k + \eta_t (\sum_{i=1}^M r_{ik} x_i - \hat{\mu}_k) \\
\hat{S}_k = \hat{S}_k + \eta_t (\sum_{i=1}^M r_{ik} x_ix_i^T - \hat{S}_k) \\
\text{Update parameter}\quad \pi_k^{t+1} = \frac{1}{M}\hat{\omega}_k, \quad \mu_k^{t+1} = \frac{\hat{\mu}_k}{\hat{\omega}_k}, \quad \Sigma_k^{t+1} = \frac{\hat{S}_k}{\hat{\omega}_k} - \mu_k^{t+1}{\mu_k^{t+1}}^T \\
t = t+1
$$
