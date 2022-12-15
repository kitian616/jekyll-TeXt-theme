---
title: Basic concept and Framework in Nature Lanugage Process
sidebar:
    nav: cs224n-eng
aside:
    toc: true
key: 20210713
tags: CS224N
---
**All contents is arranged from [CS224N](https://online.stanford.edu/artificial-intelligence/free-content?category=All&course=6097) contents. Please see the details to the [CS224N](https://online.stanford.edu/artificial-intelligence/free-content?category=All&course=6097)!**

### One Hot Vector
- Vector

$$
    \mathbb{R}^{|V| \times 1}
$$

- Example

$$
    w^{aardvar} = \begin{bmatrix}
                    1\\
                    0\\
                    0\\
                    \vdots\\
                    0
                \end{bmatrix}, 
    w^{a} = \begin{bmatrix}
            0\\
            1\\
            0\\
            \vdots\\
            0
            \end{bmatrix}, 
    \dots 
    w^{zebra}= \begin{bmatrix}
                0\\
                0\\
                0\\
                \vdots\\
                1
                \end{bmatrix}
$$

- This word represents does not give us directly any notion of similarity.
    
    $(w^{hotel})^Tw^{motel} = (w^{motel})^Tw^{hotel} = 0$
        
### SVD(Singular Value Decomposition) Base

##### What is the Singular Value Decomposition(SVD)?
- For reducing the dimensionality
- ${X = USV^T}$
- Then, use the rows of U as the word embeddings for all words in our dictionary.
- **Word-Document Matrix**
    - Loop over billions of documents and for each time word i appears in document j, we add one to entry $X_{ij}$
    - $\mathbb{R}^{ \lvert V \rvert\times M}$
    - M: the number of documents
- **Window-based Co-occurrence Matrix**
    - Counting the number of times each word appears inside a window of a particular size around the word of interest.
    - Example( Window size = 1 )
        1. I enjoy flying.
        2. I like NLP.
        3. I like deep learning.

        The resulting counts matrix            
        <p>
            <img src="/assets/images/cs224n/w1/cs224n-2019-notes01-wordvecs1-matrix.png" width="200" height="150" class="projects__article__img__center">
            <p align="center">
            <em class="projects__img__caption"> Reference. cs224n-2019-notes01-wordvecs1</em>
            </p>
        </p>         
               
##### Applying SVD to the co-occurrence matrix
- Cut SVD off at some index k based on the desired percentage variance captured(windows)
    
    $\dfrac { \textstyle\sum_{i=1}^k \sigma_i} { \textstyle\sum_{i=1}^{\lvert V \lvert}\sigma_i}$
    
- Then take the sub-matrix of $U_{1:\lvert V \lvert,1:k}$ to be our word embedding matrix
- Applying SVD to X:
    
    <p>
        <img src="/assets/images/cs224n/w1/cs224n-2019-notes01-wordvecs1-appySVD.png" width="400" height="100" class="projects__article__img__center">
        <p align="center">
        <em class="projects__img__caption"> Reference. cs224n-2019-notes01-wordvecs1</em>
        </p>
    </p>       
    
- Reducing dimensionality by selecting first k singular vectors:
    
    <p>
        <img src="/assets/images/cs224n/w1/cs224n-2019-notes01-wordvecs1-reduce-dim.png" width="400" height="100" class="projects__article__img__center">
        <p align="center">
        <em class="projects__img__caption"> Reference. cs224n-2019-notes01-wordvecs1</em>
        </p>
    </p>       
    
- This method can encode semantic and syntactic (part of speech) information

##### Problem
- The dimensions of the matrix change very often (new words are
added very frequently and corpus changes in size).
- The matrix is extremely sparse since most words do not co-occur.
- The matrix is very high dimensional in general (≈ $10^6 \times 10^6$)
- Quadratic cost to train (i.e. to perform SVD)
- Requires the incorporation of some hacks on *X* to account for the
drastic imbalance in word frequency

##### Some solution
- Ignore function words such as "the", "he", "has", etc.
- Apply a ramp window – i.e. weight the co-occurrence count based
on a distance between the words in the document.
- Use [Pearson correlation](https://en.wikipedia.org/wiki/Pearson_correlation_coefficient) and set negative counts to 0 instead of
using just raw count.

### Iteration Based Methods

##### Word2vec
- Probabilistic method

- #1 Language Models

    1. Unary Language model approach
        
        this probability by assuming the word occurrences are completely independent:
        
        $P(w_1,w_2,\dots, w_n) = \displaystyle\prod_{i=1}^{n}p(w_i)$
        
        - Issue
            - The next word is highly contingent upon the previous sequence of words.
            - The silly sentence example might actually score highly
    2. Bigram model
        
        $P(w_1,w_2,\dots, w_n) = \displaystyle\prod_{i=1}^{n}p(w_i\lvert w_{i-1})$
        
        - Issue: With pairs of neighboring words rather than evaluating a whole sentence.
        - In the Word-Word Matrix with a context of size 1, we basically can learn these pairwise probabilities.
<br><br>

- #2 Algorithm
    - [Continuous Bag-of-Words (CBOW)](/2021/09/27/continuous-back-of-words)  
    - [Skip-gram](/2021/09/27/skip-gram)

- #3 Training methods
    - [Negative sampling](/2021/09/27/negative-sampling)            
    - [[Brief] Hierarchical Softmax](/2021/09/27/hierarchical-softmax)

##### Evaluation and Training
<!-- [UNDERSTANDING] -->
- [#1 GloVe(Global Vectors for Word Representation)](/2021/09/27/global-vectors-for-word-representation)
- [#2 Evaluation of Word Vectors](/2021/09/27/evaluation-of-word-vectors)
 
### Reference
- <a href="https://web.stanford.edu/class/cs224n/">Course Main Page: Winter 2021</a><br>
- <a href="https://www.youtube.com/playlist?list=PLoROMvodv4rOSH4v6133s9LFPRHjEmbmJ">Lecture Videos</a><br>
- <a href="https://online.stanford.edu/artificial-intelligence/free-content?category=All&course=6097">Stanford Online - CS224n</a><br>
- [https://jiho-ml.com/weekly-nlp-5/](https://jiho-ml.com/weekly-nlp-5/)