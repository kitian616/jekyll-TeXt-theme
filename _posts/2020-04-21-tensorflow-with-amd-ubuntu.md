---
title: Use Tensorflow with AMD GPU on Ubuntu
date: 2020-04-21
tags: ['Machine Learning', 'Tensorflow', 'ROCm']
---

![Cover](../../../assets/images/2020-04-21/cover.png)

# Use Tensorflow with AMD graphic card on Ubuntu

After I received my graphic card (Radeon RX 570 Series) from my cousin. So I started using Tensorflow and found out my training process was also extremely slow, like when training with my MacOS.

After reading some articles and being through many tedious days, I successfully set up my Tensorflow with Docker based on it. So in this article, I would like to share with you how to set it up in the most convenient way.

No more further instruction, let’s get started!



### Install ROCm on Ubuntu

1. Run the following code to ensure that your system is up to date:
```bash
sudo apt update
sudo apt dist-upgrade
sudo apt install libnuma-dev
sudo reboot
```

2. Add the ROCm apt repository:
```bash
wget -q -O - http://repo.radeon.com/rocm/apt/debian/rocm.gpg.key | sudo apt-key add -
echo 'deb [arch=amd64] http://repo.radeon.com/rocm/apt/debian/ xenial main' | sudo tee /etc/apt/sources.list.d/rocm.list
```

3. Install the ROCm meta-package:
```bash
sudo apt update
sudo apt install rocm-dkms
```
4. Set permissions:
```bash
groups
sudo usermod -a -G video $LOGNAME
echo 'ADD_EXTRA_GROUPS=1' | sudo tee -a /etc/adduser.conf
echo 'EXTRA_GROUPS=video' | sudo tee -a /etc/adduser.conf
```

5. Restart the system

6. Verify ROCm installation is successful:
```bash
/opt/rocm/bin/rocminfo
/opt/rocm/opencl/bin/x86_64/clinfo
```

![/opt/rocm/bin/rocminfo](../../../assets/images/2020-04-21/command-1.png)

![/opt/rocm/opencl/bin/x86_64/clinfo](../../../assets/images/2020-04-21/command-2.png)

__Note:__ To run the ROCm programs more efficiently, add the ROCm binaries in your PATH.
```bash
echo 'export PATH=$PATH:/opt/rocm/bin:/opt/rocm/profiler/bin:/opt/rocm/opencl/bin/x86_64' | sudo tee -a /etc/profile.d/rocm.sh
```



### Install Docker

There are a ton of instruction in the Internet, but I recommend you to follow the steps which were written in this [article](https://do.co/2zcd8NI).



### Setup Tensorflow with ROCm

1. Pull Tensorflow image (make sure your Docker is running):
```bash
sudo docker pull rocm/tensorflow:latest
```

2. Run container with image:
```bash
docker run -i -t \
    --name=tensorflow \
    --network=host \
    --device=/dev/kfd \
    --device=/dev/dri \
    --group-add video \
    --cap-add=SYS_PTRACE \
    --security-opt seccomp=unconfined \
    --workdir=/docker \
    -v $HOME/your-working-directory:/docker rocm/tensorflow:latest /bin/bash
```

__Note:__ If you restart your computer, you could exec to your container using these 2 following commands:
```bash
sudo docker container start tensorflow # container name
sudo docker exec -it tensorflow /bin/bash
```

3. Run Jupyter notebook:
```bash
jupyter notebook --allow-root
```
![Run notebook](../../../assets/images/2020-04-21/image-1.png)

4. Create a new notebook:

![Create notebook](../../../assets/images/2020-04-21/image-2.png)

### Run Tensorflow

In this tutorial, we will use Python to test our ROCm. (For someone who doens't know Python, I encourage you to take some crash courses on the Internet).

We will test our ROCm with a basic neural network and MNIST dataset:

```python
# Install required packages
!pip install cv2 numpy matplotlib
```

```python
# Load libraries
from tensorflow.keras.datasets import mnist
from tensorflow.keras.utils import to_categorical
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, MaxPooling2D, Conv2D, Flatten

import os
import cv2
import numpy as np
import matplotlib.image as mpimg
```

```python
# Load dataset by Keras mnist
(X_train, y_train), (X_test, y_test) = mnist.load_data()
```

```python
# Categorize target
y_cat_train = to_categorical(y_train)
y_cat_test = to_categorical(y_test)
```

```python
# Standardize features
X_train = X_train / 255
X_test = X_test / 255
X_train = X_train.reshape(len(X_train), 28, 28, 1)
X_test = X_test.reshape(len(X_test), 28, 28, 1)
```

```python
# Define model
model = Sequential([
    Conv2D(filters=32, kernel_size=(5,5), input_shape=(28,28,1), activation='relu'),
    MaxPooling2D(),
    Conv2D(filters=32, kernel_size=(5,5), input_shape=(28,28,1), activation='relu'),
    MaxPooling2D(),
    Flatten(),
    Dense(128, activation='relu'),
    Dense(10, activation='softmax')
])
model.compile(loss='categorical_crossentropy',
             optimizer='rmsprop',
             metrics=['accuracy'])
```

```python
model.summary()
```

![Model summary](../../../assets/images/2020-04-21/image-3.png)

```python
model.fit(X_train, y_cat_train, epochs=3)
```

![Training result](../../../assets/images/2020-04-21/image-4.png)

```python
model.evaluate(X_test, y_cat_test)
```

![Evaluation](../../../assets/images/2020-04-21/image-5.png)

```python
# Save model
model.save('model.h5')
```

### Summary

After going through various steps, we have our running Tensorflow in AMD GPU. It is worth for spending our time on doing this setup instead of wasting a ton of hours training without ROCm.

So please don't hesitate any further, step your foots into Machine Learning world.

### References:
- [My source code](https://github.com/tailtq/ml-learning/blob/master/handmade-products/digit-recognition/model.ipynb)
- [Set up ROCm](https://rocm-documentation.readthedocs.io/en/latest/Installation_Guide/Installation-Guide.html)
- [Cover and also useful article](https://towardsdatascience.com/train-neural-networks-using-amd-gpus-and-keras-37189c453878)
