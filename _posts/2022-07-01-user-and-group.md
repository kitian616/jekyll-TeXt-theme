---
title: Authorization to User and Group
sidebar:
    nav: os-setup-ko
aside:
    toc: true
key: 20220701
tags: SetUp
---
- **Terminal에서 SSH를 통해서 user_name@ip_address 로 접속할 수 있다.**
- 예시: `ssh daniel@192.168.0.229`
- 같은 공유기에 연결된 경우에만 가능, 외부에서 접속은 VPN을 통해 Port를 할당받아야하지만 현재는 그럴 루트 공유기 PW를 알 수 없는 상태로 패스!
    - [TODO] Port, VPN 세팅
- Mac은 터미널 사용, 윈도우는 MobaXterm사용

### 1. 유저 추가 `adduser [name]` , `passewd [username]`

#### 1) **TL;DR**
- 새로운 패키지를 설치하면 관리자가 된 사람이 더 쉽게 사용자 계정을 만들 수 있다. Debian/Ubuntu 기준, 설치 명령어는 다음과 같다
    
    ```bash
    apt-get install adduser
    ```
    
    `adduser name`
        
    명령어를 입력하면 연속적인 프롬프트를 받을 것이다. 이름과 패스워드는 필수.
    Unix passoword, Name, Room number, Work Phone, Other
        
- useradd [-option] [name]
    
    써보면 알겠지만, 하위 configuration이 설정돼있지 않다.
    
- 유저 리스트 확인
    
    다음 경로(`/etc/passwd`) 로 가면(Ubuntu, MacOS에서 테스트) 유저, 속한 그룹, 쉡타입, home directory와 같은 정보를 볼 수 있다.
    
- 유저 아이디 확인: `id -u username`
- 유저 그룹아이디 확인: `id -gn username`
- 유저 지우기:  `userdel [name]`
    
    위 명령어는 사용자의 계정을 지우기만 한다. 사용자의 파일과 홈 디렉토리는 지워지지 않는다. 사용자와 홈 폴더와 파일을 지우려면 하단의 명령어를 사용하면 된다.
    
    `userdel -r [name]`
    
- 유저 디테일 변경하기, `usermod [option] [name]`
- 유저 계정 만료 날짜 확인: `chage -l username`
- 비밀번호, password: passwd [username]
- 기본 옵션
    - 보기: `useradd -D`
    - shell 변경하기: `sudo useradd -D -s /bin/bash`
    - /etc/default/useradd 에서 기본 설정을 확인 가능

#### 2) **Description of the option**

| Option | Description | Example |
| --- | --- | --- |
| -m | 사용자 홈 디렉토리를 /home/name으로 생성 | useradd -m [username] |
| -d [home_dir] | home_dir은 사용자의 로그인 디렉토리 값으로 사용 | useradd -m -d /home/[user's home] [user_name]  |
| -e [date] | 계정이 expire되는 시간 | useradd -e <yyyy-mm-dd> [user_name] |
| -f [inactive] | 계정 만료 전까지의 날짜 수 | useradd -f [0 or -1] [user_name] |
| -s [shell] | 디폴트 쉘 shell 타입 설정 | useradd -s /bin/[shell] [user_name] |
| -u(--uid) | 특정 UID를 가진 사용자를 생성 | useradd -u [uid] [user_name] |
| -g(--gid) | 특정 초기 로그인 그룹을 사용하여 사용자를 생성, 그룹 이름 또는 GID가 이미 존재해야 함 | useradd -g [group_name] [user_name] |
| -G | 사용할 보조 그룹 목록을 지정 | useradd -g [group_name] -G wheel,developers [user_name] |
| -c(--comment) | 새 사용자에 대한 간단한 설명을 추가, 설명 필드는 GECOS | useradd -c "Explanation" [user_name] |
| -r(--system) | 옵션을 사용하여 시스템 사용자 계정 생성, 시스템 사용자는 만료 날짜 없이 생성된다. 해당 UID는 login.defs 파일에 지정된 시스템 사용자 ID 범위에서 선택되며, 일반 사용자에게 사용되는 범위와는 다르다. | useradd -r [user_name] |
| -D | /etc/default/useradd 파일의 값을 수동으로 편집하여 기본 사용자 추가 옵션을 보고 변경 | useradd -D [-option] [change_condition] |

### 1-1. 유저 바꾸기, `su [name]`

- `su -`, `su root` : 루트 권한으로 들어간다.
    - 초기에 비밀번호가 설정돼 있지 않으므로, sudo passwd를 통해 관리자 권한이 있는 계정으로 수정해야한다.
- su [user_name]

### 1-2. sudo, 권력자 만들기 😈 

루트 root 는 슈퍼 사용자, root로 로그인하지 않고도 관리자 특권을 가질 수 있다.

한 사용자에게 sudo 능력을 제공하려면, 사용자 이름을 sudoers 파일에 추가하여야 한다. 이 파일은 텍스트 에디터에서 직접 편집하지 않아야 하며, sudoers 파일을 정확하게 편집하지 않으면 시스템에 접근할 수 없게 될 것이다.

그러므로 sudoers 파일을 편집하려면 `visudo` 명령어를 사용해야한다. 커맨드 라인에서 시스템에 로그인 하고 `visudo` 명령어를 입력하면 된다.

아래는 sudoers 파일에서 sudo 접근을 할 수 있는 사용자를 보여주는 부분이다. “root”를 찾으면 모든 권한을 받는 것을 볼 수 있고, 그 부분에 사용자를 추가하면 된다.

자신의 사용자 계정에 sudo 특권을 준 다음, sudoers 파일 `edit /etc/sudoers` 을 저장하고 루트에서 로그아웃한다. 이제 자신의 사용자 계정으로 로그인하여 sudo 접근할 수 있는 특권을 테스트하면 된다. 새 사용자가 sudo 접근이 필요하다면, 이제 자신의 계정으로 아래 명령으로 sudoers 파일을 편집할 수 있다.

`sudo visudo`
    
### 2. 그룹 추가

그룹에 대한 정보는 /etc/group 경로로 들어가면 group ID를 볼 수 있다.

- 추가: `groupadd [group_name]`
- 수정: `groupmod [option] [group_name]`

    | Option | Description | Example |
    | --- | --- | --- |
    | -n(--new-name) | 그룹 이름 변경 | groupmod -n [new_group_name] [old_group_name] |
    | -g | gid 변경 | groupmod -g [new_gid] [group_name] |

- 삭제: `groupdel [group_name]`
- 자기가 속한 그룹 보기: `groups`
- (root 권한)다른 사용자가 속한 그룹 보기: `groups USER`
- 사용자를 그룹에 추가 삭제: `gpasswd [option] GROUP`

    | Option | Description | Example |
    | --- | --- | --- |
    | -a(--add) | 사용자를 그룹에 추가 | (TODO) |
    | -d(--deletet) | 사용자를 그룹에서 삭제 | (TODO) |
    | -M(--members) | 여러명의 사용자를 그룹에 추가, 콤마(,)로 구분 | (TODO) |
        
### 3. 파일, 권한, 디렉토리 소유권, 권한 보기: `ls -al`
    
-a : (all) 숨겨진 파일 혹은 모든 파일을 보여주기

-l  : (long listing) 긴 설명의 목록

<p>
    <img src="/assets/images/post/2022-07-01-os-setup/group_0.png">
    <p align="center">
    <em> 그룹을 나눈 후 리스트</em>
    </p>
</p>

첫 번째 파일인 `drwxr-xr-x 7 seunghyun staff 224 Jun 13 14:01 .` 을 설명하면 다음과 같다.

- drwxr-xr-r-x: 권한
    - d(파일 타입), rwx(사용자), r-x(그룹), r-x(다른 사용자) 파트로 나뉜다.
- 7: 파일/디렉토리의 수
- seunghyun: 소유자
- staff: 그룹
- 224: 크기
- Jun 13 14:01: 최종 접근 날짜/시간
- . : 디렉토리

### 4. 권한/소유자 바꾸기, `chmod` `chown`
    
#### 1) TL;DR, chmod, chown and chgrp

`chmod`는 모드 변경(change)의 줄임말이다. chmod는 파일과 디렉토리의 권한을 변경할 때 사용한다. chmod 명령어는 권한을 설정하기 위해 아래와 같은 (팔진법으로 알려진) 문자나 숫자와 함께 사용된다. 
권한: r, w, x, s, t
대상: u, g, o
방식: -, +
예시: `chmod u+r, g+x [filename]`
    
`chown`, 기본적으로 모든 파일은 그것을 만든 사용자와 그 사용자의 디폴트 그룹에 의해 “소유된다”. 파일의 소유권을 변경하려면 chown를 아래와 같이 이용해서 바꿔줘야 한다. 
`chown user:group /path/to/file`
- 예시, 파일 소유자 바꾸기: `chown [user_name] [file_path]`
- 예시, 모든 파일 소유자 바꾸기: `chown -R [user_name] [file_path]`
    
`chgrp` own은 소유자, grp는 그룹

#### 2) Detail

| Letter w/ Permission | Meaning |
| --- | --- |
| r | read, 읽기  |
| w | write, 쓰기  |
| x | exectue, 실행  |
| X | (파일이 디렉토리인 경우)execute, 실행  |
| s | (setuid bit) 실행할 때 사용자와 그룹 ID 설정 |
| t | (sticky bit) swap 장치에 프로그램 텍스트 저장 |
| Letter w/ 대상 | Meaning |
| u | (user) 소유자의 파일에 대한 현재 권한 |
| g | (group) 같은 그룹에 있는 사용자의 파일에 대한 현재 권한 |
| o | (others) 그룹에 있지 않은 사용자의 파일에 대한 현재 권한 |
| - | 권한이 제거된 것 |
| + | 권한을 주는 것 |

권한을 주는 방법으로 **예시**를 들면 다음과 같다. `chmod u+r, g+x [filename]`이는 filename을 u: user, r: read, g: group, x: execute 로 + 하겠다는 말이며, 파일에 대해서 사용자에게 읽기 권한을 주고 그룹에게는 실행권한을 준다는 말이다. **한 번의 설정에서 여러 권한을 줄 때는 설정 사이에 쉼표(,)를 넣어야 한다.**

#### 3) 그럼 t(sticky bit) 와 s(setuid bit)는 어떤 용도로 사용할까?
    
**sticky bit(+t)**를 부여하는 경우 소유자(혹은 root)만 파일을 지울 수 있다는 것을 뜻한다. 사용자가 그룹 멤버십이나 오너십으로 이 파일에 쓰기 접근을 가지고 있음에도 불구하고 많은 사용자가 파일들에 쓰기 접근을 공유하고 있는 그룹이 소유하고 있는 파일이나 디렉토리의 경우에 유용하다.

`/root/sticky.txt`라는 파일에 sticky bit을 설정하려면 다음 명령을 내린다:

`chmod +t /root/sticky.txt`

파일에서 sticky bit을 제거하려면, `chmod -t` 명령어를 사용하면 된다. sticky bit을 변경하려면 root 거나 파일 소유자이어야 한다는 것에 주의해라. root 사용자는 sticky bit의 상태와 관계없이 파일을 지울 수 있다.

**setuid(+s)** 사용자들이 소유자 권한으로 파일을 실행할 수 있도록 설정하는 경우이다. 예를 들면, root 사용자와 marketing 그룹이 work 파일을 소유하고 있다면, marketing 그룹은 마치 그들이 root 사용자인 것처럼 work 프로그램을 실행할 수 있다. 이것은 어떤 경우에는 잠재적인 보안 위험을 일으킬 수 있으며, 실행자들은 `+s` 플래그를 주기 전에 적절하게 평가해야 한다. `/usr/bin/work`라는 파일에 `+s` bit을 설정하는 명령어는 다음과 같다:

`chmod g+s /usr/bin/work`

파일의 오너십에 대한 *+s* 모드와 대비하여, 디렉토리에 대한 *+s* 모드의 효과는 약간 다르다. *+s* 디렉토리에 생성된 파일은 (파일을 생성한 사용자와 그들의 디폴트 그룹의 오너십이 아닌) 디렉토리 사용자와 그룹의 오너십을 받는다. 디렉토리에 *setguid* (group id) 옵션을 설정하려면 다음 명령어를 사용한다:

`chmod g+s /var/doc-store/`

`/var/doc-store`라는 디렉토리에 *setuid* (user id)를 설정하려면 다음 명령을 내린다:

`chmod o+s /var/doc-store/`
    
#### 4) 파일 소유권/그룹 바꾸기, Changing File Ownership 
`chown user /path/to/file` and `chgrp [option] [group_name] /path/to/file`

기본적으로 모든 파일은 그것을 만든 사용자와 그 사용자의 디폴트 그룹에 의해 “소유된다”. 파일의 소유권을 변경하려면 `chown user:group /path/to/file` 형식으로 `chown` 명령어를 사용한다. 다음 예제에서는, "text.txt” 파일의 소유권이 daniel로 변경된다.

`chown daniel test.txt`

디렉토리와 그 안의 모든 파일의 오너십을 변경하려면, `-R` 플래그로 recursive 옵션을 사용해라. 다음 예제에서는, `/home/user/hello/` 의 소유권을 "daniel”로 변경한다.

`chown -R daniel /home/user/hello/`

그룹을 바꾸는 것은 소유권과 동일한 방식이라고 이해하면 된다. 옵션의 종류는 아래를 참고하자.

| OPTION | DESCRIPTION |
| --- | --- |
| -f, --silent, --quiet | Executes the command without displaying any error messages. |
| -v, --verbose | Outputs the action details for every file processed. |
| -c, --changes | Similar to --verbose, but reports only when making a change. |
| --dereference | Affects the referent of each symbolic link, rather than the symbolic link itself. |
| -h, --no-dereference | Affects symbolic links instead of any referenced files. Use this option only on systems that can change the ownership of a symlink. |
| --no-preserve-root | Does not treat '/' specially (default setting). |
| --preserve-root | Fails to operate recursively on '/'. |
| --reference=RFILE | Changes a file's group name to the group name of the referenced file. |
| -R, --recursive | Operates on files and directories recursively. |
| -H | If a command line argument is a symbolic link to a directory, traverses it. Used in combination with the -R option. |
| -L | In a recursive traversal, traverses every symbolic link to a directory that is encountered. Used in combination with the -R option. |
| -P | Does not traverse any symbolic links. This is the default if -H, -L, or -P aren't specified. Used in combination with the -R option. |
| --help | Displays the help file and exits. |
| --version | Outputs version information and exits. |

#### 5) 숫자를 이용해서 사용하는  Chmod Octal Format
`chmod [octal or letters] [file/directory name]`

팔진법 형식을 사용하려면 파일이나 디렉토리 각 부분에 대한 권한을 계산해야 한다. 위에서 언급 첫 10문자는 팔진법으로 네 개의 숫자에 해당한다. 실행 권한은 숫자 (1)과 같고, 쓰기 권한은 숫자 (2)와 같고, 읽기 권한은 숫자 (4)와 같다. 그러므로 팔진법 형식을 사용할 때는 0과 7 사이의 숫자를 계산할 필요가 있다.

| Octal Value | Read | Write | Execute |
| --- | --- | --- | --- |
| 7 | r | w | x |
| 6 | r | w | - |
| 5 | r | - | x |
| 4 | r | - | - |
| 3 | - | w | x |
| 2 | - | w | - |
| 1 | - | - | x |
| 0 | - | - | - |

아래는 권한을 설정할 때 문자와 팔진법 형식을 사용하는 예제들이다.

Sample syntax: `chmod [octal or letters] [file/directory name]`

Letter format: `chmod go-rwx Work` (그룹과 others에게 rwx 권한을 취소)

위의 chmod 명령어 이후의 `ls -al`의 출력은 다음과 같이 보일 것이다:

`dr-------- 2 user user 4096 Dec 17 14:38 Work`

팔진법 형식: `chmod 444 Work`

chmod 명령어 이후의 `ls -al`의 출력은 다음과 같이 보일 것이다:

`dr--r--r-- 2 user user 4096 Dec 17 14:38 Work`

아래 팔진법 테이블은 권한에 해당하는 숫자를 보여준다.

| Permission String | Octal Code |
| --- | --- |
| rwxrwxrwx | 777 |
| rwxr-x--- | 400 |
| … | … |

### 5. 이외 명령어

디렉토리 생성: `mkdir [directory_name]`

권한이 설정된 디렉토리 생성: `mkdir -m a=rws [directory_name]`

파일을 제거: `rm [file]`

디렉토리를 제거: `rm -r [directory_name]`
    
### Reference

- [https://www.commandlinux.com/man-page/man8/addgroup.8.html](https://www.commandlinux.com/man-page/man8/addgroup.8.html)
- [https://nolboo.kim/blog/2015/08/18/linux-users-groups/](https://nolboo.kim/blog/2015/08/18/linux-users-groups/)
- 사용자 추가: [https://jjeongil.tistory.com/1449](https://jjeongil.tistory.com/1449)
