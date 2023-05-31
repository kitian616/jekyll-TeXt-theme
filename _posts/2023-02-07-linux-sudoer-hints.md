---
layout: article
title: 【一点点Linux】管理员权限的更多命令行提示
permalink: /article/:title.html
key: linux-sudoer-hints
tags: 
  - 一点点Linux
  - Linux
  - 管理员
author: Yu Xiaoyuan
show_author_profile: true
license: WTFPL
---

<!-- abstract begin -->
在维护新服务器时，发现有些拥有管理员权限的用户偶尔会忘记自己拥有的超高权限，滥用`sudo`指令，给服务器维护带来一些麻烦。
想个办法让所有超级管理员权限的用户在登录命令行的时候看到一条额外信息。
<!-- abstract end -->

<!--more-->

<!-- begin include -->
{%- include general-variables.html -%}
<!-- end include -->

<!-- begin private variable of Liquid -->

<!-- {%- increment equation-h2-1 -%} -->
<!-- end private variable of Liquid -->

## shell的启动调用顺序

bash在启动时会调用一系列脚本，这些脚本一般设置系统变量、自动补全、插件等内容，增强shell的易用性。

使用指令`man bash`可以浏览bash手册，节选bash手册的内容如下。

>```
>INVOCATION
>       A login shell is one whose first character of argument zero is a  -,  or  one  started
>       with the --login option.
>
>       An  interactive shell is one started without non-option arguments (unless -s is speci‐
>       fied) and without the -c option whose standard input and error are both  connected  to
>       terminals (as determined by isatty(3)), or one started with the -i option.  PS1 is set
>       and $- includes i if bash is interactive, allowing a shell script or a startup file to
>       test this state.
>
>       The  following paragraphs describe how bash executes its startup files.  If any of the
>       files exist but cannot be read, bash reports an error.  Tildes are expanded  in  file‐
>       names as described below under Tilde Expansion in the EXPANSION section.
>
>       When bash is invoked as an interactive login shell, or as a non-interactive shell with
>       the --login option, it first reads and executes commands from the  file  /etc/profile,
>       if  that  file  exists.   After  reading  that  file,  it  looks  for ~/.bash_profile,
>       ~/.bash_login, and ~/.profile, in that order, and reads and executes commands from the
>       first  one  that  exists and is readable.  The --noprofile option may be used when the
>       shell is started to inhibit this behavior.
>
>       When an interactive login shell exits, or a non-interactive login shell  executes  the
>       exit  builtin  command, bash reads and executes commands from the file ~/.bash_logout,
>       if it exists.
>
>       When an interactive shell that is not a login shell is started, bash  reads  and  exe‐
>       cutes commands from /etc/bash.bashrc and ~/.bashrc, if these files exist.  This may be
>       inhibited by using the --norc option.  The --rcfile file option  will  force  bash  to
>       read and execute commands from file instead of /etc/bash.bashrc and ~/.bashrc.
>
>
>```

这里说当bash被当作一个交互式shell启动时会执行`/etc/profile`文件中的命令，然后到用户目录下找其他脚本。

大家的`/etc/profile`应该大同小异，这里不展示笔者的文件。大致上能看出来该文件又去执行了下面这些脚本：

- `/etc/bash.bashrc`
- `/etc/profile.d`目录下的所有脚本

其中`/etc/profile.d`目录下的脚本是一些设置常量等功能的脚本。后期如果在服务器上安装cuda啥的可以把环境变量的配置放在这个目录下。

另外一个`/etc/bash.bashrc`大有门道。

{% highlight bash linenos %}
$ cat /etc/bash.bashrc
# System-wide .bashrc file for interactive bash(1) shells.

# To enable the settings / commands in this file for login shells as well,
# this file has to be sourced in /etc/profile.

# If not running interactively, don't do anything
[ -z "$PS1" ] && return

# check the window size after each command and, if necessary,
# update the values of LINES and COLUMNS.
shopt -s checkwinsize

# set variable identifying the chroot you work in (used in the prompt below)
if [ -z "${debian_chroot:-}" ] && [ -r /etc/debian_chroot ]; then
    debian_chroot=$(cat /etc/debian_chroot)
fi

# set a fancy prompt (non-color, overwrite the one in /etc/profile)
# but only if not SUDOing and have SUDO_PS1 set; then assume smart user.
if ! [ -n "${SUDO_USER}" -a -n "${SUDO_PS1}" ]; then
  PS1='${debian_chroot:+($debian_chroot)}\u@\h:\w\$ '
fi

# Commented out, don't overwrite xterm -T "title" -n "icontitle" by default.
# If this is an xterm set the title to user@host:dir
#case "$TERM" in
#xterm*|rxvt*)
#    PROMPT_COMMAND='echo -ne "\033]0;${USER}@${HOSTNAME}: ${PWD}\007"'
#    ;;
#*)
#    ;;
#esac

# enable bash completion in interactive shells
#if ! shopt -oq posix; then
#  if [ -f /usr/share/bash-completion/bash_completion ]; then
#    . /usr/share/bash-completion/bash_completion
#  elif [ -f /etc/bash_completion ]; then
#    . /etc/bash_completion
#  fi
#fi
# sudo hint
if [ ! -e "$HOME/.sudo_as_admin_successful" ] && [ ! -e "$HOME/.hushlogin" ] ; then
    case " $(groups) " in *\ admin\ *|*\ sudo\ *)
    if [ -x /usr/bin/sudo ]; then
        cat <<-EOF
        To run a command as administrator (user "root"), use "sudo <command>".
        See "man sudo_root" for details.

        EOF
    fi
    esac
fi

# if the command-not-found package is installed, use it
if [ -x /usr/lib/command-not-found -o -x /usr/share/command-not-found/command-not-found ]; then
        function command_not_found_handle {
                # check because c-n-f could've been removed in the meantime
                if [ -x /usr/lib/command-not-found ]; then
                   /usr/lib/command-not-found -- "$1"
                   return $?
                elif [ -x /usr/share/command-not-found/command-not-found ]; then
                   /usr/share/command-not-found/command-not-found -- "$1"
                   return $?
                else
                   printf "%s: command not found\n" "$1" >&2
                   return 127
                fi
        }
fi
{% endhighlight %}

注意44行到54行的内容。这一段是给管理员的提示信息，在管理员首次登录时会显示，管理员执行一次`sudo`指令后就不再提示了。

参考这段脚本，我们可以给出我们的管理员提示信息。

## 更多的管理员提示信息

参考上门的例子，我们其实只需判断用户是否有超级管理员权限。而上面的脚本的原理是调用`groups`指令，并判断输出的内容里有没有`admin`或者`sudo`。

`groups`指令会在屏幕上显示调用者所在的所有用户组。

这样思路就有了。在系统的提示前面插入如下内容。

```bash
case " $(groups) " in *\ admin\ *|*\ sudo\ *)
    if [ -x /usr/bin/sudo ]; then
        cat /etc/sudo_hint.extra
    fi
esac
```

然后新建文件`/etc/sudo_hint.extra`，内容如下。

```text
   _____  _____  _____  _____  _____  _____  _____  _____
  |_____||_____||_____||_____||_____||_____||_____||_____|
  _  __        __ _     ____   _   _  ___  _   _   ____   _
 | | \ \      / // \   |  _ \ | \ | ||_ _|| \ | | / ___| | |
 | |  \ \ /\ / // _ \  | |_) ||  \| | | | |  \| || |  _  | |
 | |   \ V  V // ___ \ |  _ < | |\  | | | | |\  || |_| | | |
 | |    \_/\_//_/   \_\|_| \_\|_| \_||___||_| \_| \____| | |
 |_|                                                     |_|
   _____  _____  _____  _____  _____  _____  _____  _____
  |_____||_____||_____||_____||_____||_____||_____||_____|

        YOU ARE LOGGED IN AS A SUPER-USER
        BE EXTRA CAREFUL OF WHAT YOU ARE DOING
        THINK BEFORE YOU EXECUTE ANY SUDO COMMAND
   _____  _____  _____  _____  _____  _____  _____  _____
  |_____||_____||_____||_____||_____||_____||_____||_____|

```

这样在管理员账号登录的时候就总会显示一个这样的提示信息。

## 总结

Linux本就是一个多用户操作系统，在维护时要注意尽可能限制各用户的权限，又不影响他们的使用。  
原则上不应该给太多人管理员权限，这种设计也是与工业上防呆设计有相同的出发点。  
只是防呆不防傻，只能靠提示信息让用户手下留情吧。
