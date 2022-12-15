---
title: OS Setup for Machine Learning
sidebar:
    nav: os-setup-ko
aside:
    toc: true
key: 20220701
tags: SetUp
---
#### 1. OS 설치(Ubuntu 20.04 LTS)
[Ventoy](https://www.ventoy.net/en/index.html): OS image를 바꿔주는 서비스, 예를 들어 Linux 18.04를 설치했다가 20.04를 다시 설치해야할 때 OS image를 지우고 다시 까는 것이 아니라, Ventoy를 이용하면 OS 스위칭이 가능하다.

#### 2. Disk Partition and Mount<sub>[link](/2022/07/01/disk-partition-and-mount)</sub>

#### 3. GPU and Conda environment<sub>[link](/2022/07/01/gpu-conda-environment-for-tensorflow-pytorch)</sub>

#### 4 고정 아이피 설정
- Gateway 주소: (일반적으로) 192.168.1.1,
- ip 대역대: (일반적으로) 192.168.1.0/24비트, 필자의 경우 192.168.0.229
- `ifconfig`, `ip addr`: 현재 네트워크 상태 확인
- `route`, `netstat -r`, `ip route`: gateway 주소 확인
- 설정-네트워크에서 ipv4에서 변경 가능

#### 5 권한 나누기, 유저와 그룹, User and Group<sub>[link](/2022/07/01/user-and-group)</sub>

#### 6 Samba 설치 in Ubuntu 20.04 LTS<sub>[link](/2022/07/01/samba-installation-in-ubuntu)</sub>

#### [선택 사항] #7 Remote Control Method for Deep Learning

원격으로 일을 할 경우가 많고, 공용컴퓨터로 사용해서 원격으로 하는 방법이 필요했다. 터미널로 작업하는 경우는 모두 ssh 를 통해, ssh [user_name]@[ip_address] 로 연결해서 작업할 수 있다. 

그리고 화면의 경우는 MacOS에서는 XQuartz를 이용해서 ssh -X [user_name]@[ip_address]로 이용하면 화면까지 전송받을 수 있었다. 필자의 경우는 화면반응속도가 너무 느려서 패스! 

##### SSH+X11
- [https://velog.io/@spnova12/딥러닝-개발자를-위한-원격접속-방식-비교](https://velog.io/@spnova12/%EB%94%A5%EB%9F%AC%EB%8B%9D-%EA%B0%9C%EB%B0%9C%EC%9E%90%EB%A5%BC-%EC%9C%84%ED%95%9C-%EC%9B%90%EA%B2%A9%EC%A0%91%EC%86%8D-%EB%B0%A9%EC%8B%9D-%EB%B9%84%EA%B5%90)
- Mac, X11: XQuartz
- Window:  MobaXterm


#### #8 [선택 사항] 램오버클럭 → 안정화 작업필요, 현재에선 X


