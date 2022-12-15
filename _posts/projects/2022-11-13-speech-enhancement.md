---
title: Speech Enhancement with ML for Edge devices
key: 20221113
tags: Projects
---
<div class="projects__article__right">
{% include image.html url="/assets/images/project/speech-enhancement.png"  
%}
</div>
This project focuses on speech enhancement with machine learning, and implementation to embedded devices, explicitly targeting STM32F746. And its repository will guide the sequence to make the tiny machine enhance streaming quality.
<br><br>
On an embedded device for generating speech from the microphone, the most important point is its clarity. The speech enhancement can be improved previously based on <a href="https://www.routledge.com/Speech-Enhancement-Theory-and-Practice-Second-Edition/Loizou/p/book/9781138075573">Speech Enhancement book</a> written by Philipos C. Loizou. Still, the improvement had a limitation whose method removed most noise and parts of speech. This method can use for a hearing-aid since the loudness is already too high, but the limitation is still the same. At this time, two studies indicate the effect of implementing machine learning on a tiny device. <a href="https://arxiv.org/abs/2005.11138">[1]</a>, <a href="https://dl.acm.org/doi/abs/10.1145/3498361.3538933?casa_token=-H4OyZuv9LMAAAAA:EAgY2h20z3T2QFuBhOdgsaocD2bjzwkpne16rPAxiWFxr7oIOvt_g0hguJ68plC3jdfLcYyE4Kcn">[2]</a>
<br><br> 
The first research, named tiny lstm, shows almost similar performance in the model, not in the constraint. And the second research, called Clear buds, shows the entire open source from PCB to iOS and tiny devices implementation. However, those research needs to be more fitting with the product having a real-time device using a microphone and its receiver, and bose's research shows only the result. So, I started to build the open source and the guide to implementing the ML model to the tiny device, especially for speech enhancement as a personal project.
<br><br>
This project is currently an ongoing project, which is on the modification of the training model, and will complete by 2022. The plan of this project is on the below lists.

**Applications**
- Dataset for clean and noisy sound
- **[On-going]** Trained Model
- Model Compression
- Pruning Model
- The guide to implementing the model into specifically STM32F746
<br><br>

**Code for Model**<br>
- <a href="https://github.com/ooshyun/Speech-evaluation-methods">Evaluation method for speech quality</a>
- <a href="https://github.com/ooshyun/LSTM-speech-enhancment-voicebank">Model with Tensorflow</a>
- [Not Opened] <a href="https://github.com/ooshyun/TinyLSTM-for-speech-enhancement">Tunable Model with Tensorflow inspiring from demuc made by facebook</a>
- [Not Opened] <a href=""> Related documents </a>

**Reference**<br>
[1] Fedorov, Igor, et al. "TinyLSTMs: Efficient neural speech enhancement for hearing aids." arXiv preprint arXiv:2005.11138 (2020).<br>
[2] Chatterjee, Ishan, et al. "ClearBuds: wireless binaural earbuds for learning-based speech enhancement." Proceedings of the 20th Annual International Conference on Mobile Systems, Applications and Services. 2022.<br>