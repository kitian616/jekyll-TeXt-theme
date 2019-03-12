---
layout: article
title: Minecraft server starter
tags: script python
---

I have an old laptop running Linux that I'm using as a server for my modded Minecraft world. And because I don't want the laptop to be on all the time I'm trying to make some scripts to automate the turn on and start of the server. This scripts will run from my main Windows computer.


## How to use

To use this scripts you'll need python2.7, Wake On Lan and Plink on the windows side. And on the Linux server you'll need screen. You also need to enable wake on lan on the server.

1. Download the scripts from GitHub.
2. Update the config.ini with the correct data
3. Run start.py to power on the computer start the server
4. Run stop.py to stop the server and power it off

If the server is takes more than 30 seconds to power on you might need to increase the time it waits in the start.py. The wake on lan is only configured to work if the server is on the same network as you.


## Wake on lan

Wake on lan is a feature that almost all computers have, it allows the computer to turn on when a specify packet is recived, even if the computer is off. That packet is normally called Magic Packet.

I have search a bit and found a simple command line program that allows me to send the magic packet.

https://www.depicus.com/wake-on-lan/wake-on-lan-cmd

Here is a simple example on how to use the program on an internal network. I'll use that example since I only need to wake on lan from inside my own network.

``` 
wolcmd [macaddress] 255.255.255.255 255.255.255.255 4343
```


## Plink

[PuTTY Link](http://www.chiark.greenend.org.uk/~sgtatham/putty/download.html) is kind off a command line version on putty. This is how I'll send commands to the server, the problem with this method is that Plink only sends one command instead of having a season. This is a problem because the Minecraft server needs to be in a season to them be able to close the server safely.

Plink usage example:

```
plink [user]@[server] -pw [password] [command]
```


## Screen

To solve the season problem I'll be using screen. I search a little and found all the commands I needed to be able to have a season open without actually having it open.

* Run a command and detach in only one command:

  ```
  screen -S [name] -d -m [command]
  ```

* Run a command on a detached screen:

  ```
  screen -S [name] [command]
  ```

* Send text to the season instead of a normal command:

  ```
  screen -S [name] -X stuff [command]
  ```


## sudo

When I was trying to automate the shutdown of the server I came upon  it a little problem, the shutdown command needs to be run as super user and because Plink can only sends one command at a time I had to find a way to ```sudo shutdown now``` without it asking for the password. So I came up it this:

```
echo [password] | sudo -S [command]
```

This makes sudo read the password from the standard input.


## ConfigParser

ConfigParser is a python class that allows loading a simple configuration file. The configuration file consists of sections, led by a [section] header and followed by name: value entries. My configuration file locks like this:

```ini
[configs]
mac_address = 20:1D:12:38:9A:92
host = 192.168.1.100
username = notch
password = apple123
startServerCommand = ./server/ServerStart.sh
```

Example on how to read a value from the config file:

```python
import ConfigParser

config = ConfigParser.ConfigParser()
config.read('config.ini')

username = config.get('configs', 'username')
```

---

Check the source code at
[GitHubGists](https://gist.github.com/cyrillbrito/3327c7378248a51ee270156ac1d1cba7).
