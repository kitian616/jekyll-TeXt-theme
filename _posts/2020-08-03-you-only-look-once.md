---
title: Yolo - You only look once
date: 2020-04-26
tags: ['Machine Learning', 'Deep Learning', 'Tensorflow', 'Computer Vision']
---

![Cover](../../../assets/images/2020-08-03/image.png)

---

### Introduction

Recently, object detection has become one of the main applications in Computer Vision. You can easily find it in self-driving cars, diseases diagnosing and so on. In 2014, the performance of object detection was quite slow, there were no particularly architectures which can handle real-time process.

Understanding this problem, Yolo was invented in attempt to integrate concurrent performance to our daily life. It is also the backbone of other tasks like object tracking in preprocessing live thread. Today, we will take a good look at Yolo-v1, what problems can it solve and how is its performance. The articles about Yolo-v2 and v3 will come afterward.



### Network architecture

![Cover](../../../assets/images/2020-08-03/architecture.png)


#### Base network

As you can see from the picture, the base network of Yolo-v1 comprises 20 convolutional layers followed by Leaky ReLU activation functions and Pooling layers (the Fast Yolo only includes 9 layers). Besides, the 1x1 layers were added to reduce the features space from preceding layers. For the sake of visualization, this takes `448 x 448 x 3` image instead of `224 x 224 x 3` and results in `7 x 7 x 1024` tensor.

Leaky ReLU: $\phi(x) = \begin{cases}
                            0.1 x & x \leq 0 \\
                            x & x > 0
                          \end{cases} $

According to the paper, the base network was trained using Darknet framework and ImageNet classification dataset. As the result, the weights achieved 88% in ImageNet 2012 validation set.

(Remember that this architecture was not integrated Batch Normalization because it was written at 2014, 1 year earlier than the discovery of Batch Normalization).


#### Detection module

The last 4 layers in the image above consisting of 2 convolutional layers and 2 fully-connected layers are ulitized for object detection. Note that the first 3 use Leaky ReLU activation and the last one uses linear activation. Consequently, this returns `7 x 7 x 30` tensor.



### Output

Output of 
