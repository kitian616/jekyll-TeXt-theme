---
layout: post
title: An experiment using golang and python to execute command in docker
---

#### Description:

Using golang to execute python code, which can in turn execute docker command to control docker container.

<!--more-->

---

The overall idea is to execute some python script in golang environment using golang ```exec```, and this python script contain some docker commands (bash). So the overall flow of execution is:

> [bash terminal] ===> [golang.exec] ===> [python.subprocess] ===> [bash] ===> [docker command]

The issue occurs. 

> the input device is not a TTY.  If you are using mintty, try prefixing the command with 'winpty'

#### TEST COMMAND

```bash
docker run -it ubuntu bash
```

#####**Environment setting:**

- windows 10 home
- docker toolbox
- Git: git version 2.9.0.windows.1
- Python: Python 3.6.3, Anaconda custom (64-bit)
- golang: go version go1.9.2 windows/amd64

#####**Failed example**:

running under default **git-bash** environment in windows 10:

```bash
$ docker run -it ubuntu bash
```

> \>>> the input device is not a TTY.  If you are using mintty, try prefixing the command with 'winpty'

###### Similar issue has been mentioned in this [docker tutorial](https://bolcom.github.io/docker-for-testers/) and was explained with a proposed solution by [Will Anderson](http://willi.am/blog/2016/08/08/docker-for-windows-interactive-sessions-in-mintty-git-bash/) in his blog.

#####**Ideal successful case**

```
>>> go-command()
		# inside go-command()
			go.exec("python script")
				# inside python script
					python.subprocess("bash","docker run -it ubuntu bash")
```

The idea is by execute a command in a CLI created by golang, it will call a execution of a python script, and this python script can then subprocess execute the ```docker run -it ubuntu bash``` in the **bash** terminal.

####Experiment

1. the first experiment is to get rid of "is not a tty" error and make it work in python script.  Because this command(```docker run -it ubuntu bash```) is not working in the git-bash terminal, it will never (I think) work if you open idle or any python CLI for this git-bash. So the way you open the python CLI is important.

   The easiest solution is to use the **Docker Quickstart Terminal** to open the python CLI. In the working directory, you can create a simple python subprocess function and name this file ```pyCmd.py```.

   ```python
   from subprocess import Popen, PIPE, STDOUT

   def pyDockerCmd(command):
       bash_exec = 'bash.exe'
       return Popen([bash_exec, '-c', f'{command}'], \
               stdout = PIPE, stderr = PIPE, encoding='utf-8').communicate()
   ```

   in the command line interface (here I use Docker Quickstart terminal), you can test the result:

   ```bash
   $ python -c "import pyCmd; print(pyCmd.pyDockerCmd('docker run -it ubuntu bash'))"
   ```

   after execute, you will see something like this. 

   ![no response terminal](../../image/2017-11-27-pyCmd-0.png)

   If you have something like this, it means at least you get rid of the "tty" error, which is annoying. 

   This "non-response" situation is actually because of the "print" command print all the response after the docker bash session end. You can try type "ls", "pwd",and "exit". Since "exit" command will quit the bash terminal, you will see all the responses after you type "exit".
   ![response terminal](../../image/2017-11-27-pyCmd-1.png)

   Now we know at least the python can interact with the "docker" freely without the "TTY" error. It is not perfect, we can see the responsivity is crappy. But at least we know it's working.

   > [bash terminal(Docker quickstart terminal)] ~~===> [golang.exec]~~ ===> [python.subprocess] ===> [bash] ===> [docker command]

2. Now, if you wrap this python function into golang, and execute this in the **Docker quickstart terminal**, you will probably still see:

   > ('', "the input device is not a TTY.  If you are using mintty, try prefixing the command with 'winpty'\n")

   wtf?

   The golang function (```testGoPy.go```) is:

   ```go
   package main

   import (
   	"fmt"
   	"log"
   	"os/exec"
   )

   func main() {
   	out, err := exec.Command("python","-c","import pyCmd; print(pyCmd.pyDockerCmd('docker run -it ubuntu bash'))").Output()
   	
   	if err != nil {
   		log.Fatal(err)
   	}
   	fmt.Printf(string(out))
   }
   ```

   ![go terminal](../../image/2017-11-27-pyCmd-2.png)

   Why is this?

   I stuck here for a while, until I found [this](https://stackoverflow.com/questions/30207035/golang-exec-command-read-std-input) post which is kind related to my problem. If we think this way, the command we want to execute by golang is ```docker run -it ubuntu bash```, which is an interactive session, and it needs input and return output. However the golang ```exec.Command(...).Output()``` seems have no option of taking Stdin input. Hence, we need to modify the golang code so it can take stdin put.

   The modified code is shown as the following.

   ```go
   package main

   import (
   	"fmt"
   	"os"
   	"os/exec"
   )

   func main() {
   	runCommand("python","-c","import pyCmd; print(pyCmd.pyDockerCmd('docker run -it ubuntu bash'))")
   }

   func runCommand(cmdName string, arg ...string) {
       cmd := exec.Command(cmdName, arg...)
       cmd.Stdout = os.Stdout
       cmd.Stderr = os.Stderr
       cmd.Stdin = os.Stdin
       err := cmd.Run()
       if err != nil {
           fmt.Printf(err.Error())
           os.Exit(1)
       }
   }
   ```

    if you compile this and execute the this code in **Docker quickstart terminal**, it will work as before. (Similar to figure1 &2)

   ![go terminal](../../image/2017-11-27-pyCmd-3.png)

   Although it is not perfect, we have solved the problem of "is not a TTY".

#### To summarize the issue

      1. Try to use the "Docker quickstart terminal" instead of "git bash". 
      2. in the golang ```exec``` command, instead of using ```exec.Command().Output()``` directly execute the command, using ```exec.run()``` and set up the **Stdin** and Stdout before execute the command.

these two steps help me to solve my problem.

#### Further steps

I know this piece of code is imperfect and there are many things to be improved. But at least this annoying "is not a tty" problem is solved. I will further investigate the python subprocess package and try to get more understandings about it.




