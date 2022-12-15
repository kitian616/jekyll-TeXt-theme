---
title: Design Equalizer for music
key: 20211220
tags: Projects
---
This project is starting to implement an equalizer in the embedded device. Since the limitation of the edge device has a small computation capacity, it needs the filter application for adjusting the low-frequency band significantly below 200Hz. As a personal project for research and testing in a python environment, the inspiration is from a simple parallel or cascaded Infinite Impulse Response(IIR) filter <a href="https://www.aes.org/e-lib/browse.cfm?elib=19355">[1]</a>, <a href="https://ieeexplore.ieee.org/abstract/document/6891289/">[2]</a>. The process using equalizer simulates in filtfilt and filt function in scipy.signal library also for implementing those functions.
<br>
{% include image.html 
url="/assets/images/project/cascade-parellel-iir.png" 
custom__conf="projects__img__center"
%}

Specifically, I designed a biquid filter for high-pass, low-pass, band-pass, shelf, peak, and notch forms. And also it tested parallel IIR filter application, which used mainly wrapped fixed pole design and minimum phase through Hilbert Transform. The code for those designs and test is on the <a href="https://github.com/ooshyun/FilterDesign">github</a>. The code includes not only the implementation of the filter but also filt function in c and several digital signal processing examples such as <a href="https://github.com/ooshyun/FilterDesign/tree/master/study/fft_scratch">vanilia fft</a>. The list of details is as below.

**Measurement**
{% include image.html 
url="/assets/images/project/graphical-eq.png" 
custom__conf="projects__img__center"
%}

**Applications**
- Biquid filter
- Cascaded IIR filter
- Parallel IIR filter
    - Wrapped fixed poled frequency weighting
    - Cubic Hermite and spline interpolation
    - Minimum phase system using Hilbert Transform
    - Least square solution

**Code**<br>
- <a href="https://github.com/ooshyun/FilterDesign">Digital Filter design</a>


**Reference** <br>
[1] Bank, Balázs, Jose A. Belloch, and Vesa Välimäki. "Efficient design of a parallel graphic equalizer." Journal of the Audio Engineering Society 65.10 (2017): 817-825.<br>
[2] Rämö, Jussi, Vesa Välimäki, and Balázs Bank. "High-precision parallel graphic equalizer." IEEE/ACM Transactions on Audio, Speech, and Language Processing 22.12 (2014): 1894-1904.<br>