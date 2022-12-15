---
title: Olive Pro and Olive Max, Earbuds for hearing aid
key: 20210413
tags: Projects
---
<div class="projects__article__right">
{% include image.html url="/assets/images/project/img_olivepro.jpeg"  
%}
</div>

Joining Olive Union, which design, manufacture, and service the application for hearing aid, I contributed to the product <a href="https://www.indiegogo.com/projects/olive-pro-2-in-1-hearing-aids-bluetooth-earbuds#/">Olive Pro</a> and <a href="https://www.indiegogo.com/projects/olivemax-3-in-1-hearing-aid-earbud-tinnitus-app#/">Olive Max</a> as digital signal processing engineer in embedded systems. 
<br><br>
In the hearing aid open-source platform, <a href="https://github.com/claritychallenge/clarity">the clarity challenge</a> is continuing to research for enhancing speech clarity even if it amplifies most of the sounds. Referring to this platform, I was trying to contribute to exploring the products and algorithms of hearing aid.
<br><br>

Specifically, we designed the system from a microphone, analog to digital converter, digital signal processing block in an embedded system, digital to analog converter, and speaker.
<br>

{% include image.html 
url="/assets/images/project/dsp-system-block-diagram.png" 
custom__conf="projects__img__center"
%}

Based on the form of hearing aid, In-the-ear(ITE), and true wireless earbuds(TWS), I set up the dynamic range of the sound based on each of the specifications for dual-microphone and speaker. After getting from the sound as scaling data in the system, overlap and add operation is used for seamless sound as natural. Based on sound generation, I contributed the application on this device for clear speech amplification, as below. 

**Applications**
- Microphone and Speaker calibration
- Sound Compression with multi-frequency band
- Equalizer for Hearing aid and Music
- Digital Filter design for Low-pass and High-pass filter
- Tests with Python
