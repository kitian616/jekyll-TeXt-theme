---
layout: post
title: Install GMP on windows 10 machine
tags: GMP
category: blog
date: 2014-03-03
---

Recently, I was trying to install [GMP library](https://gmplib.org/#DOWNLOAD) on my windows 10 machine.

The first problem that I was facing is 

>  'gcc' is not recognized as an internal or external command, operable program or batch file."

Then I did some searches and I found [this page](https://suchideas.com/journal/2007/7/installing-gmp-on-windows) which showed how to install GMP on windows. However, it is a bit outdated, I still encounter some problems in following its steps. Luckily I figured out how to solve the problems and installed the GMP successfully. 

<!--more-->

In the guide, they recommended to use Dev-C++ to get gcc compiler. I had tried this method, however it didn't work. It returned some error when I was trying to run ```./configure```. 

Here I will suggest a different approach, instead of using the Dec-C++, I recommend to install MinGW following the guild from [MinGW website](http://www.mingw.org/wiki/Getting_Started). You need to install both **MinGW** and **MSYS**. 

After installation, if you open window cmd terminal, you may still not find "gcc". The trick is to go to the directory ("C:\MinGW\msys\1.0\") (if you install MinGW in its default directory) and double click "msys.bat" then a terminal is popped out. In this terminal, you will be able to find gcc command.

The next step is to use "cd" command to go to the GMP directory. (My path is :"C:\c++\gmp\gmp-6.1.2\"). In this directory, run "./configure" and it should work correctly. I did followed the [guide](https://suchideas.com/journal/2007/7/installing-gmp-on-windows) to modify the make files, but I suspect it can work without changing.

> Update **fix**
>
> : You must  fix an error which currently exists in the Makefiles. You must do one of the following:
>
> 1. **Before **running ./configure (step 4), go to C:\c++\GMP\GMP-4.2.1\mpn\**Makeasm.am** and go to the last line of the file. If you find **--m4="$(M4)"** in the middle of it, change it to **--m4=$(M4)**. That is, remove the double quote marks,
> 2. **After** running ./configure (step 4), go to C:\c++\Includes\GMP\GMP-4.2.1\mpn\**Makefile** and also C:\c++\Includes\GMP\GMP-4.2.1\tests\**Makefile** and make the same replacements described just above, although not quite at the last line. Change **--m4="$(M4)**" to **--m4=$(M4)**.

Instead of run "./configure", I did run "./configure --enable-cxx". However, in order to configure successfully with "enable cxx", you need to install not only gcc, but also g++ compiler as well. To install g++ compiler, you can simply open "**MinGW installation manager**" and find mingw gcc g++ compiler and install it. 

After configure run successfully, you can type

```bash 
make
```

and 

```bash
make install
```

then finally

```bash
make check
```

I didn't face any new issues. All the tests have been passed. So the gmp is successfully installed.

#### Summary

In summary, the trick is to install mingw and msys properly. If you follow [mingw page](http://www.mingw.org/wiki/Getting_Started) properly, you should not experience any issue. (At least I didn't). SO this basically the guild for installing GMP on windows 10. 