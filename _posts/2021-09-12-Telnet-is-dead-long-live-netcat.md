---
title: Using netcat to replace telnet
toc: true
mermaid: true
categories: [Commandline]
tags:
  - cli
  - linux
---

Any longtime computer user might be familiarity with the (ab)use of the `telnet` command to check connections and open ports. It is certainly still the first program that pops in to my mind when I want to check a network adres or port. But `telnet` was removed from Windows a long time ago and should not be installed on any \*nix system. Luckily there is a much older and beter program available.

# Enter netcat
`netcat` is an old school Unix util that (just like whiskey) gets better with the years. It's a great way to check open ports and troubleshoot connections

## Netcat syntax
The `netcat` syntax is pretty straight forward:
```bash
nc OPTIONS TARGET PORT_RANGE 
```

Example:
```bash
$ nc -v google.com 80 
Connection to google.com port 80 [tcp/http] succeeded!
```

## Netcat options
`netcat` has some options that are quite handy:
- `-v` & `-vv` give you a verbose and more verbose output. Hint, without a `-v` flag you will not see the succeeded message
- `-u` toggles from TCP to UDP
- `-w n` changes the wait time to *n*. So `-w 1` will set a 1 second timeout
- `-n` gives you the option to scan a IP

## How to use
Here a some examples of how to use `nc`:
### Scanning a port on a host
```bash
$ nc -vv google.nl 80
Connection to google.com port 80 [tcp/http] succeeded!
```

### Scanning a port range on a local host
```bash
$ nc -vv -n -z -w 1 10.1.1.1 50-60
nc: connectx to 10.1.1.1 port 50 (tcp) failed: Connection refused
nc: connectx to 10.1.1.1 port 51 (tcp) failed: Connection refused
nc: connectx to 10.1.1.1 port 52 (tcp) failed: Connection refused
Connection to 10.1.1.1 port 53 [tcp/*] succeeded!
nc: connectx to 10.1.1.1 port 54 (tcp) failed: Connection refused
nc: connectx to 10.1.1.1 port 55 (tcp) failed: Connection refused
nc: connectx to 10.1.1.1 port 56 (tcp) failed: Connection refused
nc: connectx to 10.1.1.1 port 57 (tcp) failed: Connection refused
nc: connectx to 10.1.1.1 port 58 (tcp) failed: Connection refused
nc: connectx to 10.1.1.1 port 59 (tcp) failed: Connection refused
nc: connectx to 10.1.1.1 port 60 (tcp) failed: Connection refused
```