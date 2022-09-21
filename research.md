---
layout: page
permalink: /research/
title: Research
pubs:
    
    - title:   "Learning Latent Superstructures in Variational Autoencoders for Deep Multidimensional Clustering"
      author:  "Xiaopeng Li, Zhourong Chen and Nevin L. Zhang"
      journal: "International Conference on Learning Representations"
      year:    "2019"
      media:
        - name: "arXiv"
          url:  "https://arxiv.org/abs/1803.05206"

    - title:   "Building Sparse Deep Feedforward Networks using Tree Receptive Fields"
      author:  "Xiaopeng Li, Zhourong Chen and Nevin L. Zhang"
      journal: "International Joint Conference on Artificial Intelligence"
      year:    "2018"
      media:
        - name: "arXiv"
          url:  "https://arxiv.org/abs/1803.05209"
        - name: "github"
          url:  "https://github.com/eelxpeng/TreeReceptiveFields"

    - title:   "Learning Sparse Deep Feedforward Networks via Tree Skeleton Expansion"
      author:  "Zhourong Chen, Xiaopeng Li and Nevin L. Zhang"
      journal: "arXiv"
      year:    "2018"
      media:
        - name: "arXiv"
          url:  "http://arxiv.org/abs/1803.06120"    

    - title:   "Relational Variational Autoencoder for Link Prediction with Multimedia Data"
      author:  "X. Li and J. She"
      journal: "ACM SIGMM International Conference on Multimedia Thematic Workshop"
      year:    "2017"
      url:     ""
      media:
        - name: "paper"
          url:  ""
        - name: "github"
          url:  "https://github.com/eelxpeng/RVAE"

    - title:   "Collaborative Variational Autoencoder for Recommender Systems"
      author:  "X. Li and J. She"
      journal: "ACM SIGKDD International Conference on Knowledge Discovery and Data Mining"
      year:    "2017"
      url:     ""
      media:
        - name: "paper"
          url:  "/assets/paper/Collaborative_Variational_Autoencoder.pdf"
        - name: "github"
          url:  "https://github.com/eelxpeng/CollaborativeVAE"

    - title:   "A Bayesian Neural Network for Deep Learning in Mobile Multimedia using Small Data"
      author:  "X. Li, J. She and M. Cheung"
      journal: "Submitted to ACM Trans. Multimedia Comput. Commun. Appl. (Under Review)"
      year:    "2016"
      url:     ""
      media:
        - name: "paper"
          url:  ""

    - title:   "Connection Discovery using Shared Images by Gaussian Relational Topic Model"
      author:  "X. Li, M. Cheung and J. She"
      journal: "IEEE International Conference on Big Data"
      year:    "2016"
      url:     ""
      media:
        - name: "paper"
          url:  "/assets/paper/GRTM.pdf"
        - name: "github"
          url:  "https://github.com/eelxpeng/GRTM"

    - title:   "A Distributed Streaming Framework for Connection Discovery Using Shared Videos"
      author:  "X. Li, M. Cheung and J. She"
      journal: "ACM Trans. Multimedia Comput. Commun. Appl."
      year:    "Sep. 18, 2017"
      url:     ""
      media:
        - name: "paper"
          url:  ""

    - title:   "An Efficient Computation Framework for Connection Discovery using Shared Images"
      author:  "M. Cheung, X. Li and J. She"
      journal: "ACM Trans. Multimedia Comput. Commun. Appl."
      year:    "Aug. 29, 2017"
      url:     ""
      media:
        - name: "paper"
          url:  ""

    - title:   "Dance Background Image Recommendation with Deep Matrix Factorization"
      author:  "J. Wen, J. She, X. Li and H. Mao"
      journal: "ACM Trans. Multimedia Comput. Commun. Appl."
      year:    "2018"
      url:     ""
      media:
        - name: "paper"
          url:  ""

    - title:   "Visual Background Recommendation for Dance Performances Using Dancer-Shared Images"
      author:  "J. Wen, X. Li, J. She, S. Park and M. Cheung"
      journal: "IEEE International Conference on Cyber Physical and Social Computing"
      year:    "2016"
      url:     ""
      media:
        - name: "paper"
          url:  "/assets/paper/Visual_Background_Recommendation_for_Dance_Performances_Using_Dancer-Shared_Images.pdf"

    - title:   "Non-user Generated Annotation on User Shared Images for Connection Discovery"
      author:  "M. Cheung, J. She and X. Li"
      journal: "IEEE International Conference on Cyber Physical and Social Computing"
      year:    "2015"
      url:     ""
      media:
        - name: "paper"
          url:  "http://ieeexplore.ieee.org/document/7396504/?arnumber=7396504&tag=1"

    
---

## Publications

{% assign thumbnail="left" %}

{% for pub in page.pubs %}
{% if pub.image %}
{% include image.html url=pub.image caption="" height="100px" align=thumbnail %}
{% endif %}
[**{{pub.title}}**]({% if pub.internal %}{{pub.url | prepend: site.baseurl}}{% else %}{{pub.url}}{% endif %})<br />
{{pub.author}}<br />
*{{pub.journal}}*
{% if pub.note %} *({{pub.note}})*
{% endif %} *{{pub.year}}* {% if pub.doi %}[[doi]({{pub.doi}})]{% endif %}
{% if pub.media %}<br />Media: {% for article in pub.media %}[[{{article.name}}]({{article.url}})]{% endfor %}{% endif %}

{% endfor %}
