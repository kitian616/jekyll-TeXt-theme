---
layout: article
title: PINN reconstruct
mathjax: " true"
---
# Abstract
- **Supervised learning tasks**
- **Nonlinear-PDE.**
- **Two main classes of problems**:Data Driven Solution and data-driven discovery of partial differential equations.
- **Two Algorithms**:continuous time model, discrete time models.
  - Continuous Time Model:data-efficient spatio-temporal function approximators,
  - Discrete Time Models:arbitrarily accurate implicit Runge–Kutta time stepping schemes with unlimited number of stages.
# Intro
 *making decisions under partial information*.The data is hard to get.
 *physical laws that govern the time-dependent dynamics of a system*.The prior knowledge could be used to govern the ML practice.
 我们将这些先前已经知道的知识作为时间依赖的动力系统的监督准则.
Deepuniversal function approximators
(https://zhuanlan.zhihu.com/p/443284394)[万能逼近]


brew/miniforge
[PINN](https://github.com/maziarraissi/PINNs)
[AppleOfficial-Tensorflow](https://developer.apple.com/metal/tensorflow-plugin/)
[contribProblem](https://github.com/maziarraissi/PINNs/issues/45)
[LatexProblem](https://github.com/matplotlib/matplotlib/issues/26464)
[SomeImporovement need to run on tensorwlow v2](https://github.com/maziarraissi/PINNs/issues/48)


# Data-Driven Solution of Partial Differential 
问题是考虑如下形式的方程
$$
u_{t} + \mathscr{N}[u] = 0, x\in \Omega,t\in [0,T]
$$
- $\mathscr{N}$ 是一个非线性微分算子
- $u(t,x)$ 是潜在解
-  $\Omega$ 是 D 维实数空间的子集.

基于连续时间模型,本文构建等式如下

$$
f:= u_{t} +\mathscr{N}[u]
$$
然后利用 DNN 逼近得到 $u(t,x)$ . 由此我们推导得到了一个 PINN. 并且上述的神经网络和 $u(t,x)$ 具有相同的参数.
两组神经网络共同的参数可以通过对均方差的极小化问题得到
$$
MSE = MSE_{u}+MSE_{f}
$$
其中
$$
MSE_{u} = \frac{1}{N_{u}}\sum_{i=1}^{N_{u}}[u(t_{u}^i,x_{u}^i)-u^i]^2
$$
以及
$$
MSE_{f} = \frac{1}{N_{f}} \sum_{i=1}^{N_{f}}|f(t_{f}^i,x_{f}^i)|^2
$$
同时
- $\{ t_{u}^i,x_{u}^i,u^i \}^{N_{u}}_{i=1}$.是对于 $u(t,x)$ 的初始边界训练条件.
- $\{ f_{f}^i ,x_{f}^i\}^{N_{f}}_{i=1}$ 则是对于 $f$ 的对应点列
而 $MSE_{u}$ 是对应于初始的边界数据, $MSE_{f}$ 则是在有限的顺序点列中,满足等式
$$
u_{t} + \mathscr{N}[u] = 0,x \in \Omega , t \in [0,T]
$$
得到的.


## 具体问题
$$
\begin{align}
 & u_{t}(t,x)+ u u_{x} - \left( \frac{0.01}{\pi} \right)u_{xx}   = 0 , x \in[-1,1], t \in[0,1]\\
	 & u(0,x) = -\sin(\pi x) \\
 & u(t,-1) = u(t,1) = 0
\end{align}
$$
定义
$$
f:=u_{t} + uu_{x} -\left( \frac{0.01}{\pi} \right) u_{{xx}}
$$
利用 DNN 去逼近 $u(t,x)$ 
我们把 $u(t,x)$ 简化为

```python
def u(t,x):
	 u = neural_net(tf.concat([t,x],1),weights,biases)
	 return u
```

同样的,可以把神经网络搭建为
```python
def f(t,x):
	u = u(t,x)[0]
	u_t = tf.gradients(u,t)[0]
	u_x = tf.gradients(u,x)[0]
	u_xx = tf.gradients(u_x,x)[0]
	f = u_t+u*u_x - (0.01/tf.pi)*u_xx
```

神经网络之间的参数是一致的

运行结果
```python
/opt/homebrew/Caskroom/miniforge/base/envs/tf26/bin/python /Applications/PyCharm.app/Contents/plugins/python/helpers/pydev/pydevconsole.py --mode=client --host=127.0.0.1 --port=50858 
import sys; print('Python %s on %s' % (sys.version, sys.platform))
sys.path.extend(['/Users/harizuo/Downloads/PINNs-master', '/Users/harizuo/PycharmProjects/PMM'])
PyDev console: starting.
Python 3.9.0 | packaged by conda-forge | (default, Nov 26 2020, 07:55:15) 
[Clang 11.0.0 ] on darwin
runfile('/Users/harizuo/Downloads/PINNs-master/appendix/continuous_time_inference (Burgers)/Burgers.py', wdir='/Users/harizuo/Downloads/PINNs-master/appendix/continuous_time_inference (Burgers)')
WARNING:tensorflow:From /opt/homebrew/Caskroom/miniforge/base/envs/tf26/lib/python3.9/site-packages/tensorflow/python/compat/v2_compat.py:108: disable_resource_variables (from tensorflow.python.ops.variable_scope) is deprecated and will be removed in a future version.
Instructions for updating:
non-resource variables are not supported in the long term
2023-12-11 13:47:15.933902: I metal_plugin/src/device/metal_device.cc:1154] Metal device set to: Apple M1
2023-12-11 13:47:15.933935: I metal_plugin/src/device/metal_device.cc:296] systemMemory: 8.00 GB
2023-12-11 13:47:15.933946: I metal_plugin/src/device/metal_device.cc:313] maxCacheSize: 2.67 GB
2023-12-11 13:47:15.934009: I tensorflow/core/common_runtime/pluggable_device/pluggable_device_factory.cc:306] Could not identify NUMA node of platform GPU ID 0, defaulting to 0. Your kernel may not have been built with NUMA support.
2023-12-11 13:47:15.934053: I tensorflow/core/common_runtime/pluggable_device/pluggable_device_factory.cc:272] Created TensorFlow device (/job:localhost/replica:0/task:0/device:GPU:0 with 0 MB memory) -> physical PluggableDevice (device: 0, name: METAL, pci bus id: <undefined>)
2023-12-11 13:47:15.935583: I tensorflow/core/common_runtime/direct_session.cc:380] Device mapping:
/job:localhost/replica:0/task:0/device:GPU:0 -> device: 0, name: METAL, pci bus id: <undefined>
2023-12-11 13:47:16.132030: I tensorflow/compiler/mlir/mlir_graph_optimization_pass.cc:388] MLIR V1 optimization pass is not enabled
2023-12-11 13:47:16.136253: I tensorflow/core/common_runtime/placer.cc:125] truncated_normal/TruncatedNormal: (TruncatedNormal): /job:localhost/replica:0/task:0/device:GPU:0
2023-12-11 13:47:16.136262: I tensorflow/core/common_runtime/placer.cc:125] truncated_normal/mul: (Mul): /job:localhost/replica:0/task:0/device:GPU:0
2023-12-11 13:47:16.136265: I tensorflow/core/common_runtime/placer.cc:125] truncated_normal: (AddV2): /job:localhost/replica:0/task:0/device:GPU:0
2023-12-11 13:47:16.136268: I tensorflow/core/common_runtime/placer.cc:125] Variable: (VariableV2): /job:localhost/replica:0/task:0/device:CPU:0
2023-12-11 13:47:16.136280: I tensorflow/core/common_runtime/placer.cc:125] Variable/Assign: (Assign): /job:localhost/replica:0/task:0/device:CPU:0
2023-12-11 13:47:16.136283: I tensorflow/core/common_runtime/placer.cc:125] Variable/read: (Identity): /job:localhost/replica:0/task:0/device:CPU:0
2023-12-11 13:47:16.136288: I tensorflow/core/common_runtime/placer.cc:125] Variable_1: (VariableV2): /job:localhost/replica:0/task:0/device:CPU:0
2023-12-11 13:47:16.136291: I tensorflow/core/common_runtime/placer.cc:125] Variable_1/Assign: (Assign): /job:localhost/replica:0/task:0/device:CPU:0
2023-12-11 13:47:16.136293: I tensorflow/core/common_runtime/placer.cc:125] Variable_1/read: (Identity): /job:localhost/replica:0/task:0/device:CPU:0
2023-12-11 13:47:16.136297: I tensorflow/core/common_runtime/placer.cc:125] truncated_normal_1/TruncatedNormal: (TruncatedNormal): /job:localhost/replica:0/task:0/device:GPU:0
2023-12-11 13:47:16.136300: I tensorflow/core/common_runtime/placer.cc:125] truncated_normal_1/mul: (Mul): /job:localhost/replica:0/task:0/device:GPU:0
2023-12-11 13:47:16.136302: I tensorflow/core/common_runtime/placer.cc:125] truncated_normal_1: (AddV2): /job:localhost/replica:0/task:0/device:GPU:0
2023-12-11 13:47:16.136304: I tensorflow/core/common_runtime/placer.cc:125] Variable_2: (VariableV2): /job:localhost/replica:0/task:0/device:CPU:0
2023-12-11 13:47:16.136307: I tensorflow/core/common_runtime/placer.cc:125] Variable_2/Assign: 
...
gradients_1/Add_14_grad/Reshape: (Reshape): /job:localhost/replica:0/task:0/device:GPU:0
tensorflow/core/grappler/optimizers/custom_graph_optimizer_registry.cc:117] Plugin optimizer for device_type GPU is enabled.
Loss: 0.26729056
Loss: 4.186906
Loss: 0.24129552
Loss: 0.23253538
Loss: 0.22905155
Loss: 0.22709598
Loss: 0.2240366
Loss: 0.2153025
Loss: 0.20562463
Loss: 0.20127052
Loss: 0.19792041
...
Loss: 3.648233e-06
Loss: 3.648233e-06
Loss: 3.648233e-06
Training time: 290.4825
Error u: 6.723001e-03

```
将噪声加入为 0.01 后


```python
/opt/homebrew/Caskroom/miniforge/base/envs/tf26/bin/python /Applications/PyCharm.app/Contents/plugins/python/helpers/pydev/pydevconsole.py --mode=client --host=127.0.0.1 --port=51572 
import sys; print('Python %s on %s' % (sys.version, sys.platform))
sys.path.extend(['/Users/harizuo/Downloads/PINNs-master', '/Users/harizuo/PycharmProjects/PMM'])
PyDev console: starting.
Python 3.9.0 | packaged by conda-forge | (default, Nov 26 2020, 07:55:15) 
[Clang 11.0.0 ] on darwin
runfile('/Users/harizuo/Downloads/PINNs-master/appendix/continuous_time_inference (Burgers)/Burgers.py', wdir='/Users/harizuo/Downloads/PINNs-master/appendix/continuous_time_inference (Burgers)')
WARNING:tensorflow:From /opt/homebrew/Caskroom/miniforge/base/envs/tf26/lib/python3.9/site-packages/tensorflow/python/compat/v2_compat.py:108: disable_resource_variables (from tensorflow.python.ops.variable_scope) is deprecated and will be removed in a future version.
Instructions for updating:
non-resource variables are not supported in the long term
2023-12-11 14:00:13.397444: I metal_plugin/src/device/metal_device.cc:1154] Metal device set to: Apple M1
2023-12-11 14:00:13.397478: I metal_plugin/src/device/metal_device.cc:296] systemMemory: 8.00 GB
2023-12-11 14:00:13.397488: I metal_plugin/src/device/metal_device.cc:313] maxCacheSize: 2.67 GB
2023-12-11 14:00:13.397702: I tensorflow/core/common_runtime/pluggable_device/pluggable_device_factory.cc:306] Could not identify NUMA node of platform GPU ID 0, defaulting to 0. Your kernel may not have been built with NUMA support.
2023-12-11 14:00:13.398041: I tensorflow/core/common_runtime/pluggable_device/pluggable_device_factory.cc:272] Created TensorFlow device (/job:localhost/replica:0/task:0/device:GPU:0 with 0 MB memory) -> physical PluggableDevice (device: 0, name: METAL, pci bus id: <undefined>)
2023-12-11 14:00:13.400409: I tensorflow/core/common_runtime/direct_session.cc:380] Device mapping:
/job:localhost/replica:0/task:0/device:GPU:0 -> device: 0, name: METAL, pci bus id: <undefined>
...
Loss: 0.2672898
Loss: 4.16555
Loss: 0.24125421
Loss: 0.23243803
...
Loss: 2.4852043e-06
Loss: 2.4840672e-06
Loss: 2.482978e-06
Loss: 2.482191e-06
Training time: 94.4981
Error u: 1.006092e-01
```

神经网络层数参数选取为


```python
/opt/homebrew/Caskroom/miniforge/base/envs/tf26/bin/python /Applications/PyCharm.app/Contents/plugins/python/helpers/pydev/pydevconsole.py --mode=client --host=127.0.0.1 --port=51926 
import sys; print('Python %s on %s' % (sys.version, sys.platform))
sys.path.extend(['/Users/harizuo/Downloads/PINNs-master', '/Users/harizuo/PycharmProjects/PMM'])
PyDev console: starting.
Python 3.9.0 | packaged by conda-forge | (default, Nov 26 2020, 07:55:15) 
[Clang 11.0.0 ] on darwin
runfile('/Users/harizuo/Downloads/PINNs-master/appendix/continuous_time_inference (Burgers)/Burgers.py', wdir='/Users/harizuo/Downloads/PINNs-master/appendix/continuous_time_inference (Burgers)')
WARNING:tensorflow:From /opt/homebrew/Caskroom/miniforge/base/envs/tf26/lib/python3.9/site-packages/tensorflow/python/compat/v2_compat.py:108: disable_resource_variables (from tensorflow.python.ops.variable_scope) is deprecated and will be removed in a future version.
Instructions for updating:
non-resource variables are not supported in the long term
2023-12-11 14:09:02.084912: I metal_plugin/src/device/metal_device.cc:1154] Metal device set to: Apple M1
2023-12-11 14:09:02.084936: I metal_plugin/src/device/metal_device.cc:296] systemMemory: 8.00 GB
2023-12-11 14:09:02.304003: I tensorflow/core/grappler/optimizers/custom_graph_optimizer_registry.cc:117] Plugin optimizer for device_type GPU is enabled.
Loss: 0.2800508
Loss: 1.9304192
...
Loss: 1.7820725e-05
Loss: 1.7820725e-05
Training time: 170.5963
Error u: 1.379615e-02
```
```python
Loss: 1.2441325e-05
Loss: 1.2441325e-05
Training time: 279.9259
Error u: 6.665354e-03
```