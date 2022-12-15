---
title: GloVe(Global Vectors for Word Representation)
key: 20210713
tags: CS224N
---
**All contents is arranged from [CS224N](https://online.stanford.edu/artificial-intelligence/free-content?category=All&course=6097) contents. Please see the details to the [CS224N](https://online.stanford.edu/artificial-intelligence/free-content?category=All&course=6097)!**

### Intro
- Jeffrey Pennington, Richard Socher, and Christopher D. Manning. 2014

### 1.1 Previous Method
1. Count-based and rely on matrix factorization (e.g. Latent Semantic Analysis (LSA), Hyperspace Analogue to Language (HAL))
    - Effectively leverage global statistical information, they are primarily used to capture word similarities
    - Poorly do on tasks such as word analogy, indicating a sub-optimal vector space structure.
    
2. Shallow window-based (e.g. the skip-gram and the CBOW models), which learn word embeddings by making predictions in local context windows.
    - The capacity to capture complex linguistic patterns beyond word similarity
    - Fail to make use of the global co-occurrence statistics.

- GloVe consists of a weighted least squares model that trains on global word-word co-occurrence counts and thus makes efficient use of statistics.

- Using global statistics to predict the probability of word j appearing in the context of word i with a least squares objective

### 1.2 Co-occurrence Matrix

- $X$: word-word co-occurrence matrix
- $X_{ij}$: number of times word $j$ occur in the context of word $i$
- $X_i = \sum_k X_{ik}$: the number of times any word $k$ appears in the context of word $i$
- $P_{ij} = P(w_j \lvert w_i) = \dfrac{X_{ij}}{X_i}$: the probability of $j$ appearing in the context of word $i$
- $V \in R^{n×\lvert V\lvert}$ and $U \in R^{\lvert V\lvert×n}$, Where n is an arbitrary size which defines the size of our embedding space. $V$ is the input word matrix such that the $i$-th column of $V$ is the n-dimensional embedded vector for word wi when it is an input to this model. We denote this $n × 1$ vector as $v_i$. Similarly, $U$ is the output word matrix. The $j$-th row of $U$ is an n-dimensional embedded vector for word $w_j$ when it is an output of the model. We denote this row of U as $u_j$. Note that we do in fact learn two vectors for every word $w_i$ (i.e. input word vector $v_i$ and output word vector $u_i$).

### 1.3 Least Squares Objective
#### 1.3.1 Explaination in CS224N
- Softmax: To compute the probability of word $j$ appears in the context of word $i$
    
    $$
        Q_{ij} = \dfrac{exp(\overrightarrow{u_j}^T \overrightarrow{v_i})}{\displaystyle\sum_{w=1}^W exp(\overrightarrow{u_w}^T \overrightarrow{v_i})}
    $$
    
- Global cross-entropy loss
    
    $$
        J = -\displaystyle\sum_{i \in corpus} \displaystyle\sum_{j \in context(i)} log\ Q_{ij}
    $$
    
    As the same words i and j can appear multiple times in the corpus, it is more efficient to the first group together the same values for i and j:
    
    $$
        J = -\displaystyle\sum_{i=1}^W \displaystyle\sum_{j=1}^W X_{ij}log\ Q_{ij}
    $$
    
    where the value of co-occurring frequency is given by the co-occurrence matrix X.
    
    > Drawback: Cross-entropy loss requires the distribution Q to be properly normalized, which involves the expensive summation over the entire vocabulary.
    
    Instead, we use a least square objective in which the normalization factors in P and Q are discarded:
    
    $$
        \hat J = \displaystyle\sum_{i=1}^W \displaystyle\sum_{j=1}^W X_{i}(\hat P_{ij}-\hat Q_{ij})^2
    $$

    where,
    
    $$
        \hat{P}_{ij} = X_{ij}\ \ and\ \ \hat{Q}_{ij}=exp(\overrightarrow{u_j}^T \overrightarrow{v_i})
    $$
    
    are the unnormalized distributions.
    
    > Problem: $-X_{ij}$ often takes on very large values and makes the optimization difficult
    
    Effective change: To minimize the squared error of the logarithms of $\hat{P} \ and \ \hat{Q}$
        
    $$
        \hat{J} 
        
        = \displaystyle\sum_{i=1}^W\displaystyle\sum_{j=1}^W X_i(log(\hat{P}_{ij}) - log(\hat{Q}_{ij}))^2
        
        = \displaystyle\sum_{i=1}^W\displaystyle\sum_{j=1}^W X_i(\overrightarrow{u_j}^T \overrightarrow{v_i}-log(X_{ij}))^2
    $$
        
    - Another observation
        
        The weighting factor $X_i$ is not guaranteed to be optimal. Instead, we introduce a more general weighting function, which we are free to take to depend on the context word as well:
        
    $$
        \hat{J} 
        
        = \displaystyle\sum_{i=1}^W\displaystyle\sum_{j=1}^W f(X_{ij})(\overrightarrow{u_j}^T \overrightarrow{v_i}-log(X_{ij}))^2
    $$
            
        
#### 1.3.2 Explaination in Korean
- Link to [https://wikidocs.net/22885](https://wikidocs.net/22885)
    
### 1.4 Conclusion
1. It consistently outperforms word2vec on the word analogy task, given the same corpus, vocabulary, window size, and training time. 
2. It achieves better results faster, and also obtains the best results irrespective of speed.
