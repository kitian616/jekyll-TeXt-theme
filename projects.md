---
layout: article
titles:
  # @start locale config
  en      : &EN       About
  en-GB   : *EN
  en-US   : *EN
  en-CA   : *EN
  en-AU   : *EN
  zh-Hans : &ZH_HANS  关于
  zh      : *ZH_HANS
  zh-CN   : *ZH_HANS
  zh-SG   : *ZH_HANS
  zh-Hant : &ZH_HANT  關於
  zh-TW   : *ZH_HANT
  zh-HK   : *ZH_HANT
  ko      : &KO       소개
  ko-KR   : *KO
  fr      : &FR       À propos
  fr-BE   : *FR
  fr-CA   : *FR
  fr-CH   : *FR
  fr-FR   : *FR
  fr-LU   : *FR
  # @end locale config
show_title: false
key: page-about
---
## Projects
<div class="projects__block__left">
{% include image.html url="/assets/images/project/img_olivepro.jpeg" 
      title="Olive Pro and Olive Max, Earbuds for hearing aid" 
      description="Shipping product for real-time and speech amplifcation"
      date="Apr 13, 2020, Audio, Programming, Embedded System"
      link="_posts/projects/2021-04-13-olive-pro-max-tws.md"
%}
</div>

<div class="projects__block__right">
{% include image.html url="/assets/images/project/speech-enhancement.png" 
      title="Speech Enhancement with ML for Edge devices" 
      description="Implementation to STM32F746"
      date="Nov 13, 2020, Audio, Programming, Embedded System"
      link="_posts/projects/2022-11-13-speech-enhancement.md"
%}
</div>

<div class="projects__block__left">
{% include image.html url="/assets/images/project/img_eq_parellel_filter_32_band.png" 
      title="Digital Filter for Music Equalizer" 
      description="Equalizer with Parallel and Cascades form and examples"
      date="Dec 20, 2021, Audio, Programming, Embedded System"
      link="_posts/projects/2021-12-20-equalizer.md"
%}
</div>

<div class="projects__block__right">
{% include image.html url="/assets/images/project/delay-locked-loop-result.png" 
      title="Delay Locked Loop for DDR3 and LPDDR3" 
      description="Design the analog circuit for the sychronization between CPU and Memory"
      date="Sep 19, 2019, Analog Circuit Design"
      link="_posts/projects/2019-09-19-delay-locked-loop.md"
%}
</div>


<div class="projects__block__left">
{% include image.html url="/assets/images/project/PHY-interface.png" 
      title="PHY Interface for DDR3 and LPDDR3" 
      description="Design the analog circuit for the interface between CPU and Memory"
      date="Sep 10, 2018, Analog Circuit Design"
      link="_posts/projects/2018-09-10-phy-interface.md"
%}
</div>


<!-- [Ongoing project] Speech Enhancement using LSTM, 2022, [Code]  

[Ongoing project] Olive Max & Olive Pro, 2020-2022

Design Equalizer with cascasde and parallel biquid filter, Fall 2021, [Code]
  least square solution
  minimum phase system using Hilbert Transform
  cubic Hermite and spline interpolation
  wrapped fixed poled frequency weighting

Transceiver and Receiver for single-ended PAM2 with differential sensing, Fall 2019
  Explanation
  Picture, Design -> Measurement

Delay Locked Loop for DDR3 and LPDDR3, 2019
  Explanation
  Picture, Block -> Logic -> Layout -> Measurement

PHY Interface for DDR3 and LPDDR3, 2018
  Explanation
  Picture, Block -> Logic -> Layout -> Measurement -->
