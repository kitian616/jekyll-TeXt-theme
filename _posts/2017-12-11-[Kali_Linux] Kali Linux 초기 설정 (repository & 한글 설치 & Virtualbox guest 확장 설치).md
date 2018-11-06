---
title: [Kali_Linux] Kali Linux 초기 설정 (repository & 한글 설치 & Virtualbox guest 확장 설치)
key: 20171211
tags: Kali_Linux
---


## 1. Repository 추가
```
gedit /etc/apt/source.list
deb http://http.kali.org/kali kali-rolling main contrib non-free 추가 후 저장
```
![Alt text](https://t1.daumcdn.net/cfile/tistory/995940465A2E40C42A)

Kali sources.list Repositories 참고
(https://docs.kali.org/general-use/kali-linux-sources-list-repositories)



## 2. apt 최신 업데이트
```
apt-get update && apt-get dist-upgrade -y

init 6
```


## 3. 한글 설치
```
apt-get install fonts-nanum* -y

init 6
```


## 4. 한글 입력기 설치
```
apt-get -y install nabi im-switch

im-config -s nabi

im-config -c   #설정에서 hangul 선택 후 재부팅
```

## 5. guest 확장 설치 (Virtualbox)
```
apt-get install virtualbox-guest-x11
```
