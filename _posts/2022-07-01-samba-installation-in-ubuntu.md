---
title: Installation Samba in Ubuntu 20.04 LTS
sidebar:
    nav: os-setup-ko
aside:
    toc: true
key: 20220701
tags: SetUp
---
#### 왜 FTP(File Transfer Protocol)를 사용 안하는가?

FTP(File Transfer Protocol) 는 파일 송수신을 위한 목적으로 만들어진 프로토콜로서 많은 데이터를 빠르게 주고받을 수 있고 그 원리와 사용법 또한 비교적 간단하여 누구나 편리하게 이용 가능하다. 

하지만 전송 과정에서 데이터가 암호화되지 않고 텍스트 그대로 노출된다는 점에서 보안에 취약하다는 단점이 잇다. 해커의 포트 스캐닝과 사용자 대입을 통한 무작위 공격으로 사용자의 계정이 탈취될 수 있으며, 악성코드를 업로드해 다수의 이용자 피해가 발생할 수 있다. 또한 웹을 공격한 해커는 서버를 통제할 수 있게 되고 더 나아가 기업의 데이터베이스까지 타깃으로 삼을 수 있다.

### 1. 준비물

Ubuntu 16.04 LTS 이상, Local Area Network(LAN)

### 2. samba로 이용할 디렉토리 만들기
    
    `mkdir /home/<username>/sambashare`
    
### 3. samba configuration 수정
    
`sudo vim /etc/samba/smb.conf`

#### 1) 파일 가장 밑에 아래 항목을 입력한다.
    
```
[sambashare]
    comment = Samba on Ubuntu
    path = /home/username/sambashare
    read only = no
    browsable = yes
```

- Guset로 접속할 수 있도록 설정하기
    
    ```
    [sambashare]
        comment = Samba on Ubuntu
        path = /home/username/sambashare
        read only = no
        browsable = yes
            guest only = yes
            guest ok = yes
    ```
    
- User에 맞는 권한 할당하기
    
    ```bash
    [sambashare]
    comment = Samba on Ubuntu
    path = /home/username/sambashare
    valid users = id1,id2
    writeable = yes
    read only = no
    create mode = 0777
    directory mode = 0777
    ```
    
- 더 많은 디테일 은 이 [링크](https://help.ubuntu.com/community/Samba/SambaServerGuide?_ga=2.39460963.1347981673.1656592978-926832432.1656313274)로!

#### 2) 새로운 configuration을 적용/저장 그리고 restart samba
    
`sudo service smbd restart`

*이후 Configuration을 변경할 때마다 reload해줘야 한다.

`sudo /etc/init.d/samba reload`

위에 명령어가 안되면, `sudo smbd reload`
    
#### 3) 방화벽 해제

`sudo ufw allow samba`

### 4. samba에 user account 세팅과 로컬에서 연결

#### 1) Samba Passward, User 설정

 `sudo smbpasswd -a username`

username의 password와 samba password는 다르므로 주의 하자. 
    
#### 2) 로컬에서 \\ip-address\sambashare 로 연결한 후, 세팅한 user account와 samba password로 연결한다.

- MacOS는 Finder에 Go 탭에서, Window는 실행창(window+shift)에서 연결할 수 있다.

- Guest 허용시, 폴더 권한을 anyone에게 허용해줘야 한다.

### 기타 명령어

pdbedit — manage the SAM database (Database of Samba Users)

ex. `sudo pdbedit -L -v`

### Reference

- 본문: [https://ubuntu.com/tutorials/install-and-configure-samba#1-overview](https://ubuntu.com/tutorials/install-and-configure-samba#1-overview)

- window에서 연결 [https://velog.io/@krystal_95/Ubuntu-Samba-설치-및-설정](https://velog.io/@krystal_95/Ubuntu-Samba-%EC%84%A4%EC%B9%98-%EB%B0%8F-%EC%84%A4%EC%A0%95)

