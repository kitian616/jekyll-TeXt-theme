---
layout: post
title: python subprocess
tages: python, subprocess, CLI
date: 2017-11-28
---

## Python subprocess examples

### Description

This is a collection of python subprocess examples for easy usage. Although I found the python documents are very comprehensive, I found there is not much  examples of showing how to use. This document act as a note of how to use python subprocess package.

#### Environment settings

| OS     | Windows 10 home        |
| ------ | ---------------------- |
| python | Anaconda, python 3.6.3 |

#### Main functions

- ##### subprocess.run()

  the code is executed in IDLE command line window.

  ```python 
  >>> import subprocess as sb
  >>> bash_exec = "C:\\Program Files\\Git\\bin\\bash.exe"
  >>> sb.run([bash_exe, '-c','pwd'], stdout=sb.PIPE)
  ```

  > CompletedProcess(args=['C:\\Program Files\\Git\\bin\\bash.exe', '-c', 'pwd'], returncode=0, stdout=b'/c/Users/xxx/Anaconda3/Scripts\n')

  NOTE:

  * need to point to bash exe
  * bash needs a **"-c"** condition in order to run it, otherwise a **returncode=126** will return. Cannot using **-i** tag for bash command execution.
  * return result will not be shown if ```stdout``` is not set. 


- ##### subprocess.Popen(*)

  However, I personally feel that the Popen is the most important tool of this subprocess package.

  **Basic syntex** 

  ```python
  >>> p = sb.Popen([bash_exe, '-c', 'pwd'], stdout=sb.PIPE, stderr=sb.PIPE)
  >>> p.communicate()
  ```

  > (b'/c/Users/xxx/Anaconda3/Scripts\n', b'')

  this is working. 

  The other one I thought it would work but actually not

  ```python 
  >>> p = sb.Popen(['-c','pwd'],executable=bash_exe, stdout=sb.PIPE, stderr=sb.PIPE)
  >>> p.communicate()
  ```

  > (b'', b'/usr/bin/pwd: /usr/bin/pwd: cannot execute binary file\n')

  The error turn out to be a bit wired. For now, I am not sure how is this happened.

  ```env``` might be one very useful attributes when some info needs to be preloaded before executing the command.

  ```python 
  >>> p = sb.Popen([bash_exe, '-c', 'echo $NAME'], stdout=sb.PIPE, stderr=sb.PIPE)
  >>> p.communicate()
  ```

  > (b'\n', b'')


  ```python 
  >>> p = sb.Popen([bash_exe, '-c', 'echo $NAME'], stdout=sb.PIPE, stderr=sb.PIPE, env = {'NAME':'superman'})
  >>> p.communicate()
  ```

  > (b'superman\n', b'')

  **context manager**

  ```python
  with sb.Popen([bash_exe, '-c', 'echo $NAME'], stdout=sb.PIPE, stderr=sb.PIPE, env = {'NAME':'superman'}) as proc:
  	proc.communicate()
  ```

  > (b'superman\n', b'')

  Not working:

  ```python
  >>>with sb.Popen([bash_exe, '-c',], stdout=sb.PIPE, stderr=sb.PIPE, stdin = sb.PIPE, env = {'NAME':'superman'}) as proc:
  	proc.communicate(input=input())

  	
  pwd
  ```

  > (b'', b'/usr/bin/bash: -c: option requires an argument\n')

  Eh, looks I can write a basic bash CLI?

  ```python
  import subprocess as sb

  command = ''
  kernal = "C:\\Program Files\\Git\\bin\\bash.exe"

  while command!="exit":
      command = input('%: ')
      proc = sb.Popen([kernal,'-c',command], stdout=sb.PIPE, stderr=sb.PIPE, encoding = 'utf-8')
      out,err = proc.communicate()
      if err =='':
          print(out)
      else:
          print(err)
  ```

  However, if you input "cd" command, it actually doesn't change its directory. SO, this CLI sucks.

  One easy solution is to use some libraries such as [Pespect on Windows](http://pexpect.readthedocs.io/en/stable/overview.html#windows). 

  NOTE:

  (TODO)

  [this](http://code.activestate.com/recipes/440554-module-to-allow-asynchronous-subprocess-use-on-win/) seems a good solution for building an interactive shell. Will implement later on. 

  ​