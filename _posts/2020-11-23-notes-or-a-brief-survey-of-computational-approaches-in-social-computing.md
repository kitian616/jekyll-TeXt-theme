---
title: 'NOTES | A Brief Survey of Computational Approaches in Social Computing'
date: 2020-11-23 20:57:35
tags: [社会计算,论文阅读,学术研究]
---
![](https://xtopia-1258297046.cos.ap-shanghai.myqcloud.com/social%20computing.png)

*A Brief Survey of Computational Approaches in Social Computing*是2009年发表在IJCNN上的文章。是一篇关于社会计算的综述。

* AUTHOR：Irwin King; Jiexing Li; Kam Tong Chan
* DOI: [10.1109/IJCNN.2009.5178967](https://doi.org/10.1109/IJCNN.2009.5178967)
* citations: 110

<!--more-->

---
# 作者 (AUTHORS)

## Irwin King

![Irwin King](https://xtopia-1258297046.cos.ap-shanghai.myqcloud.com/20201123210552.png) 

Chairperson and Professor at the Department of Computer Science and Engineering and the former Associate Dean (Education), Faculty of Engineering, The Chinese University of Hong Kong

Major areas: machine learning, social computing, AI, Big Data, data mining, web intelligence, multimedia information processing

Linkedin: [https://www.linkedin.com/in/irwinking/?originalSubdomain=hk](https://www.linkedin.com/in/irwinking/?originalSubdomain=hk)

homepage: [https://www.cse.cuhk.edu.hk/irwin.king/home](https://www.cse.cuhk.edu.hk/irwin.king/home)

## Jiexing Li

Software Engineer at Google

homepage: [http://pages.cs.wisc.edu/~jxli/](http://pages.cs.wisc.edu/~jxli/)

linkedin: [https://www.linkedin.com/in/jiexingli/](https://www.linkedin.com/in/jiexingli/)

# 引言 (INTRODUCTION)

Web 2.0的发展给人们在社会网络上的连接带来了全新的方式，当流量增大的同时，社会计算就成了一种无论从社会层面，还是文化层面都无法忽视的computing phenomenon，值得人们更详细的研究。

## What is Social Computing?

社会计算的其中一种定义是：

> Computational facilitation of social studies and human social dynamics as well as the design and use of information and communication technologies that consider social context. [1]
社会科学和人类社会动态的计算以及考虑社会背景的信息和通信技术的设计和使用。~~（这翻译不太行啊）~~

社会计算涉及到跨学科分析，通过对不同媒体和平台上的社交行为进行建模，来产生一些不同的intelligent applications。一般包括了计算、社会学、社会心理学、组织理论、传播理论、人机交互等多学科。

社会计算需要关注的三种类型的features：

- **Connectivity**: relations among people within the group.
    - eg: phone, email, instant messaging, SMS, chats, blogs,forums, social network services, and other emerging media.
- **Collaboration**: collaborative (positive) / adversarial or competitive(negative) relations
    - eg: collaborative filtering, trust and reputation systems, online auctions,verification games, social choices, knowledge sharing, etc.
- **Community**: grouping or clustering of people
    - eg: blogs, wikis, social networks, social tagging, collaborative filtering, collaborative bookmarking, pod-casts, etc

# 社交平台 (SOCIAL PLATFORMS)

## 社交网络 (Social Networks)

例如 Facebook, QQ, Blogger, LinkedIn等

## 社交媒体 (Social Media)

Flickr, YouTube

## 游戏 (Social Games/Human Computation)

Social games or Games with A Purpose (GWAP)希望利用人力来解决问题。

这种问题有两个相似点：

1. 这些问题都不太方便用计算机解决。they are problems that computers are not good at solving. 
2. 对人类来说很简单。they are trivial for humans. 

例如：image annotation (图像标注问题) （os：😅）

需要机制来确保答案是正确的，符合标准的，因此引入了interaction models（交互模型），常见的interaction models有三种：

- Output-Agreement Games：所有的用户都扮演同样的角色。用户都是随机选择的，为了保证他们的anonymity(匿名性?)（os：感觉是出于安全的考虑，为了让数据集更可靠）。给用户相同的输入，并被要求生成输出。如果用户的输出一致，则可以验证答案的正确性。例如：ESPGame[2]、reCAPTCHA[3]。
- Inversion-Problem Games：把用户分成两个角色。一位用户扮演"describer"，充当input；一位扮演"guesser"，充当output。如果guesser可以成功猜测到input，我们称之为describer给出的hint是正确的描述。例如：Peekaboom(a system for locating objects in images)[4]、Phetch(an image description generator)[5]
- Input-Agreement Games：为所有的玩家分配同样的角色。但每个用户的input可能不同，他们需要猜测他们是否和其它用户具有同样的input，可以分享input的描述信息。例如：Tagatune[6]

## 书签/标签 (Social Bookmarking/Tagging)

Social Bookmarking是用户在网络上存储，搜索和管理网页的书签。

标签的站点可以通过分析用户对站点的inputs，帮助搜索引擎来更快地索引站点，并提供更高质量的结果，例如Del.icio.us，StumbleUpon，Ma.gnolia等。

但是不同的用户对于相同的概念可能有不一样的描述方式。系统中可能存在大量的同义词，这也常常导致检索精度较低。

## 新闻和知识共享 (Social News and Social Knowledge Sharing)

Social news是指用户提交信息的网站，用户也可以对链接进行投票，这可以用于显示哪些链接。

比如wikipedia，每天有数百万人正在使用它，也有成千上万的人都在编辑它。而在QA系统（AnswerBus，Webclopedia，Yahoo's babelfish，etc.）中，每个用户都有助于集体知识库的实现。

# 主要的任务和方法 (TASKS AND TECHNIQUES)

## 社交网络理论、建模和分析(Social Network Theory, Modeling, and Analysis)

目标：表征用户的社交团体与其个人行为之间存在的关系。

## 排名算法 (Ranking)

在任何信息检索系统中，对检索结果的排名都是至关重要的关键任务。一个好的排名方案将给出高度相关且准确的结果，而所需的计算资源最少。

根据排序目标分类，一般有这四种算法：

- graph ranking
- mediators
- Pagerank
- or objects and for data lying in the Euclidean space: such as text or image data

## Query Log Processing

搜索引擎以及社交网站从他们的用户那里收集大量的查询日志或点击数据。 这是一个信息的金矿，可用于改善检索结果。

## 网络垃圾检测 (Web Spam Detection)

检测网络上的垃圾邮件主机或网页。

## Graph/Link Analysis and Mining

因为可以使用图来建模社会关系，link analysis and modeling便是处理这些图的最直观的方法。

理想的图数据挖掘算法有以下特征：

- Efficiency：时间和空间复杂度小
- Scalability：web信息增多时，算法是否可用
- Stability：网络的不稳定因素是否会干扰算法
- Robustness：抵抗常见的web spam

## 协同过滤 (Collaborative Filtering)

协作过滤是基于其他类似用户提供的信息来识别特定用户的信息兴趣的过程。一般的算法有：

- Memory-based collaborative filtering：也称为nearest-neighbor collaborative filtering，最常见的形式是user-based model。[7] 中提出了协同过滤的框架。
- Model-based Collaborative Filtering Algorithms. 通过定义的用户评分模型来训练模型。
- Other Related Approaches. Hybrid框架

CF的关键问题在于解决数据的稀疏性。(data sparsity)

## 情感分析和观点挖掘 (Sentiment Analysis and Opinion Mining)

观点挖掘(Opinion Mining)的重点是从网络上提取人们的意见。

观点挖掘(opinion mining)会分析的点有：

- 哪些部分是表达观点的部分。which part is opinion expressing
- 观点的作者是谁。who wrote the opinion
- 正在被评价的是什么。what is being commented on
- 作者的观点是什么。what is the opinion of the writer

为了解决观点挖掘中的问题，已经有许多现有的工作，例如：

- Feature Extraction 特征提取
- Sentiment Analysis 情感分析


---

[1] Wang, Fengyu & Carley, Kathleen & Zeng, Daniel Dajun & Mao, Wenji. (2007). Social Computing: From Social Informatics to Social Intelligence. Intelligent Systems, IEEE. 22. 79 - 83. 10.1109/MIS.2007.41.

[2] ——, “Labeling images with a computer game,” inCHI, E. Dykstra-Erickson and M. Tscheligi, Eds. ACM, 2004, pp. 319–326.

[3] L. von Ahn, B. Maurer, C. McMillen, D. Abraham, and M. Blum,“reCAPTCHA: Human-Based Character Recognition via Web Secu-rity Measures,”Science, vol. 321, no. 5895, p. 1465, 2008.

[4] L. von Ahn, R. Liu, and M. Blum, “Peekaboom: a game forlocating objects in images,” inCHI ’06: Proceedings of the SIGCHIconference on Human Factors in computing systems.New York,NY, USA: ACM, 2006, pp. 55–64.

[5] L. von Ahn, S. Ginosar, M. Kedia, and M. Blum, “Improving ImageSearch with PHETCH,” inAcoustics, Speech and Signal Processing,2007. ICASSP 2007. IEEE International Conference on, vol. 4, 2007.

[6] R. B. D. M. C. Edith L. M. Law, Luis von Ahn, “Tagatune: A gamefor music and sound annotation,”ISMIP, 2007.

[7] J. L. Herlocker, J. A. Konstan, A. Borchers, and J. Riedl, “Analgorithmic framework for performing collaborative filtering,” inSIGIR. ACM, 1999, pp. 230–237.