---
layout: article
title: 【树莓派学习笔记】在树莓派上播放音乐
key: play-music-on-pi
permalink: /article/:title.html
tags: 
  - 树莓派
  - bash
  - Linux
  - ALSA
  - man
author: Yu Xiaoyuan
show_author_profile: true
license: WTFPL
---

近期有个新的需求，定期在服务器上播放某段音频。所以研究了一下如何在linux上播放音乐、调整音量。

<!--more-->

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- end private variable of Liquid -->

## [ALSA](https://www.alsa-project.org/wiki/Main_Page)

[ALSA](https://www.alsa-project.org/wiki/Main_Page)(Advanced Linux Sound Architecture)为Linux提供了音频和MIDI功能，具有如下特点(笔者手动翻译欢迎勘误)。

- 支持各类音频接口，包括定制声卡和专业多通道音频接口；
- 完全模块化的音频驱动；
- SMP和线程安全设计；
- 用户态函数库(alsa-lib)简化二次开发；
- 支持OSS的大部分程序。

只是简单地播放音频，用不上二次开发，只需要两个指令`aplay`和`alsamixer`。

使用手册指令`man`来查看`aplay`的手册。

```bash
man aplay
```

>如果不清楚`man`是什么，可以用指令`man man`来查看`man`的手册。
>
>```
>MAN(1)                                            Manual pager utils                                           MAN(1)
>
>NAME
>       man - an interface to the system reference manuals
>
>SYNOPSIS
>       man [man options] [[section] page ...] ...
>       man -k [apropos options] regexp ...
>       man -K [man options] [section] term ...
>       man -f [whatis options] page ...
>       man -l [man options] file ...
>       man -w|-W [man options] page ...
>
>DESCRIPTION
>       man  is the system's manual pager.  Each page argument given to man is normally the name of a program, utility
>       or function.  The manual page associated with each of these arguments is then found and displayed.  A section,
>       if  provided,  will direct man to look only in that section of the manual.  The default action is to search in
>       all of the available sections following a pre-defined order (see DEFAULTS), and to show only  the  first  page
>       found, even if page exists in several sections.
>
>       The table below shows the section numbers of the manual followed by the types of pages they contain.
>
>       1   Executable programs or shell commands
>       2   System calls (functions provided by the kernel)
>       3   Library calls (functions within program libraries)
>       4   Special files (usually found in /dev)
>       5   File formats and conventions, e.g. /etc/passwd
>       6   Games
>       7   Miscellaneous (including macro packages and conventions), e.g. man(7), groff(7)
> Manual page man(1) line 1 (press h for help or q to quit)
>```

通过查阅`aplay`的手册我们得知：

- 该指令是一个音频播放指令；
- 与其对应的还有一个`arecord`录音指令；
- 该指令可以传入多个文件；
- 未指定文件的会默认使用`stdin`和`stdout`；
- 采样率等音频信息可以自动地从文件头读取。

并且该指令还能列出音频设备。

继续查阅`alsamixer`的手册。

```
man alsamixer
```

>```
>ALSAMIXER(1)                           General Commands Manual                           ALSAMIXER(1)
>
>NAME
>       alsamixer - soundcard mixer for ALSA soundcard driver, with ncurses interface
>
>SYNOPSIS
>       alsamixer [options]
>
>DESCRIPTION
>       alsamixer  is  an  ncurses  mixer program for use with the ALSA soundcard drivers. It supports
>       multiple soundcards with multiple devices.
>
>OPTIONS
>       -h, --help
>              Help: show available flags.
>
>       -c, --card <card number or identification>
>              Select the soundcard to use, if you have more than one. Cards are numbered from 0  (the
>              default).
>
>       -D, --device <device identification>
>              Select the mixer device to control.
>
>       -V, --view <mode>
>              Select the starting view mode, either playback, capture or all.
>
>       -g, --no-color
>              Toggle the using of colors.
>
>MIXER VIEWS
>       The top-left corner of alsamixer is the are to show some basic information: the card name, the
>       mixer chip name, the current view mode and the currently selected mixer item.  When the  mixer
>       item is switched off, [Off] is displayed in its name.
>
>       Volume  bars are located below the basic information area.  You can scroll left/right when all
>       controls can't be put in a single screen.  The name of each control is shown in the bottom be‐
>       low the volume bars.  The currently selected item is drawn in red and/of emphasized.
> Manual page alsamixer(1) line 1 (press h for help or q to quit)
>```

通过查阅`alsamixer`的手册我们得知，这是一个混音器，显然可以调整音量，并且是一个有命令行GUI的指令。  
该指令有一个问题，不能通过命令直接设置音量，必须经过UI去调整。笔者最终希望通过配置文件的方式来设置默认音量，所以这个工具并不满足要求。

好在`ALSA`有另一个混音器工具`amixer`，同样查看手册。

>```
>AMIXER(1)                              General Commands Manual                              AMIXER(1)
>
>NAME
>       amixer - command-line mixer for ALSA soundcard driver
>
>SYNOPSIS
>       amixer [-option] [cmd]
>
>DESCRIPTION
>       amixer  allows  command-line  control of the mixer for the ALSA soundcard driver.  amixer sup‐
>       ports multiple soundcards.
>
>       amixer with no arguments will display the current mixer settings for the default soundcard and
>       device. This is a good way to see a list of the simple mixer controls you can use.
>
>COMMANDS
>       help   Shows syntax.
>
>       info   Shows the information about a mixer device.
>
>       scontrols
>              Shows a complete list of simple mixer controls.
>
>       scontents
>              Shows a complete list of simple mixer controls with their contents.
>
>       set or sset <SCONTROL> <PARAMETER> ...
>              Sets  the  simple  mixer  control contents. The parameter can be the volume either as a
>              percentage from 0% to 100% with % suffix, a dB gain with dB suffix (like  -12.5dB),  or
>              an  exact  hardware  value.   The  dB gain can be used only for the mixer elements with
>              available dB information.  When plus(+) or minus(-) letter  is  appended  after  volume
>              value, the volume is incremented or decremented from the current value, respectively.
>
>              The  parameters cap, nocap, mute, unmute, toggle are used to change capture (recording)
>              and muting for the group specified.
>
>              The optional modifiers can be put as extra parameters to specify the  stream  direction
>              or  channels  to apply.  The modifiers playback and capture specify the stream, and the
>              modifiers front, rear, center, woofer are used to specify channels to be changed.
>
>              A simple mixer control must be specified. Only one device can be controlled at a time.
>
>       get or sget <SCONTROL>
>              Shows the simple mixer control contents.
>
>              A simple mixer control must be specified. Only one device can be controlled at a time.
>
>       controls
>              Shows a complete list of card controls.
>
>       contents
>              Shows a complete list of card controls with their contents.
> Manual page amixer(1) line 1 (press h for help or q to quit)
>```

注意到`set`指令可以设置音量，而且还有百分比和分贝两种单位可选，完美符合需求。

## 检视音频设备

使用指令`sudo aplay -l`来查看所有音频设备。这里以笔者的树莓派为例。

```bash
$ sudo aplay -l
**** List of PLAYBACK Hardware Devices ****
card 0: Headphones [bcm2835 Headphones], device 0: bcm2835 Headphones [bcm2835 Headphones]
  Subdevices: 8/8
  Subdevice #0: subdevice #0
  Subdevice #1: subdevice #1
  Subdevice #2: subdevice #2
  Subdevice #3: subdevice #3
  Subdevice #4: subdevice #4
  Subdevice #5: subdevice #5
  Subdevice #6: subdevice #6
  Subdevice #7: subdevice #7
card 1: vc4hdmi0 [vc4-hdmi-0], device 0: MAI PCM i2s-hifi-0 [MAI PCM i2s-hifi-0]
  Subdevices: 1/1
  Subdevice #0: subdevice #0
card 2: vc4hdmi1 [vc4-hdmi-1], device 0: MAI PCM i2s-hifi-0 [MAI PCM i2s-hifi-0]
  Subdevices: 1/1
  Subdevice #0: subdevice #0
```

一共有三个设备，其中`card1`和`card2`都是树莓派的HDMI接口，笔者的音响(其实是耳机)接在3.5mm音频接口上，所以应该是这里的`card0`，也就是`Headphones`设备。

使用指令`sudo aplay -L`查看所有PCM(Pulse-code modulation 脉冲编码调制)。

```bash
$ sudo aplay -L
null
    Discard all samples (playback) or generate zero samples (capture)
hw:CARD=Headphones,DEV=0
    bcm2835 Headphones, bcm2835 Headphones
    Direct hardware device without any conversions
plughw:CARD=Headphones,DEV=0
    bcm2835 Headphones, bcm2835 Headphones
    Hardware device with all software conversions
default:CARD=Headphones
    bcm2835 Headphones, bcm2835 Headphones
    Default Audio Device
sysdefault:CARD=Headphones
    bcm2835 Headphones, bcm2835 Headphones
    Default Audio Device
dmix:CARD=Headphones,DEV=0
    bcm2835 Headphones, bcm2835 Headphones
    Direct sample mixing device
hw:CARD=vc4hdmi0,DEV=0
    vc4-hdmi-0, MAI PCM i2s-hifi-0
    Direct hardware device without any conversions
plughw:CARD=vc4hdmi0,DEV=0
    vc4-hdmi-0, MAI PCM i2s-hifi-0
    Hardware device with all software conversions
default:CARD=vc4hdmi0
    vc4-hdmi-0, MAI PCM i2s-hifi-0
    Default Audio Device
sysdefault:CARD=vc4hdmi0
    vc4-hdmi-0, MAI PCM i2s-hifi-0
    Default Audio Device
hdmi:CARD=vc4hdmi0,DEV=0
    vc4-hdmi-0, MAI PCM i2s-hifi-0
    HDMI Audio Output
dmix:CARD=vc4hdmi0,DEV=0
    vc4-hdmi-0, MAI PCM i2s-hifi-0
    Direct sample mixing device
hw:CARD=vc4hdmi1,DEV=0
    vc4-hdmi-1, MAI PCM i2s-hifi-0
    Direct hardware device without any conversions
plughw:CARD=vc4hdmi1,DEV=0
    vc4-hdmi-1, MAI PCM i2s-hifi-0
    Hardware device with all software conversions
default:CARD=vc4hdmi1
    vc4-hdmi-1, MAI PCM i2s-hifi-0
    Default Audio Device
sysdefault:CARD=vc4hdmi1
    vc4-hdmi-1, MAI PCM i2s-hifi-0
    Default Audio Device
hdmi:CARD=vc4hdmi1,DEV=0
    vc4-hdmi-1, MAI PCM i2s-hifi-0
    HDMI Audio Output
dmix:CARD=vc4hdmi1,DEV=0
    vc4-hdmi-1, MAI PCM i2s-hifi-0
    Direct sample mixing device
```

注意其中的`bcm2835 Headphones`，这表明对应的设备是我们的耳机。

## 播放音频

了解了设备之后可以开始播放音频了。  
笔者的工作目录具有如下结构。

```bash
$ pwd
/home/pi/Musics
$ tree
.
└── 01 Love Never Felt So Good.wav

0 directories, 1 file
```

通过设备`Headphones`播放这首歌。

可以通过`Tab`{:.info}自动补全文件名。
{:.info}

```bash
$ aplay -D plughw:Headphones 01\ Love\ Never\ Felt\ So\ Good.wav
Playing WAVE '01 Love Never Felt So Good.wav' : Signed 16 bit Little Endian, Rate 44100 Hz, Stereo
```

注意到Michael悦耳的歌声从耳机中传出，这表明我们成功播放了音乐。

## 调整音量

使用`amixer`来调整音量。

```bash
$ amixer
Simple mixer control 'Headphone',0
  Capabilities: pvolume pvolume-joined pswitch pswitch-joined
  Playback channels: Mono
  Limits: Playback -10239 - 400
  Mono: Playback -664 [90%] [-6.64dB] [on]
```

记得刚才的`Headphones`，这里设置音量的指令需要这个参数。比如将音量设置为$90\%$。

```bash
$ amixer -c Headphones set Headphone 90%
Simple mixer control 'Headphone',0
  Capabilities: pvolume pvolume-joined pswitch pswitch-joined
  Playback channels: Mono
  Limits: Playback -10239 - 400
  Mono: Playback -664 [90%] [-6.64dB] [on]
```

`-c`后面跟的是刚才列出的PCM名称，而`set`后面的是`amixer`控制的名称，不要搞混，有些机器上可能不一样。

## 总结

手册是个好东西。

## 参考

[linux 播放音频](https://blog.csdn.net/qq_30519005/article/details/120308085)

[Advanced Linux Sound Architecture (ALSA) project homepage](https://www.alsa-project.org/wiki/Main_Page)

[Dynamic PCM](https://www.kernel.org/doc/html/v4.12/sound/soc/dpcm.html)
