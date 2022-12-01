---
title: "Fixing broken YUM REPO's on CentOS"
toc: true
mermaid: true
categories: [Linux]
tags:
  - cli
---

Imagine this

```bash
# yum update
https://mirrors.lug.mtu.edu/epel/7/x86_64/repodata/13b91b1efe2a1db71aa132d76383fdb5311887958a910548546d58a5856e2c5d-primary.sqlite.xz: [Errno 14] HTTPS Error 404 - Not Found
Trying other mirror.
http://mirror.oss.ou.edu/epel/7/x86_64/repodata/13b91b1efe2a1db71aa132d76383fdb5311887958a910548546d58a5856e2c5d-primary.sqlite.xz: [Errno 14] HTTP Error 404 - Not Found
Trying other mirror.
https://mirror.csclub.uwaterloo.ca/fedora/epel/7/x86_64/repodata/13b91b1efe2a1db71aa132d76383fdb5311887958a910548546d58a5856e2c5d-primary.sqlite.xz: [Errno 14] HTTPS Error 404 - Not Found
Trying other mirror.
http://mirror.sfo12.us.leaseweb.net/epel/7/x86_64/repodata/13b91b1efe2a1db71aa132d76383fdb5311887958a910548546d58a5856e2c5d-primary.sqlite.xz: [Errno 14] HTTP Error 404 - Not Found
Trying other mirror.
```

And this
```bash
# yum update
rpmdb: page 6849: illegal page type or format
rpmdb: PANIC: Invalid argument
rpmdb: Packages: pgin failed for page 6758
error: db4 error(-9494) from dbcursor->c_get: DB_RUNRECOVERY: Fatal error, run database recovery
```

I know right  ¯\\_(ツ)_/¯

So it seems your mirror's are broken, the package DB is out of date and you're not that happy. Let's go trough the steps of fixing this. We gonna do this in two steps:

1. Cleaning the current repo's & Cleaning the Package DB;
2. Finding and installing the appropriate repo's

### Cleaning the current Repo's
First we will move the old package DB and clean the cache with these 3 command's

```bash
mv /var/lib/rpm/__db* /tmp/
rpm --rebuilddb
yum clean all
```

Now remove the old Repo's
```bash
rm -r /etc/yum.repos.d/*
rm /etc/yum.repos.d/*.repo
```

### Finding and installing the appropriate repo's
Now the next part is abit manual. You'll need to find the appropriate `RPM` package to fix our repo's. You can get them at the CentOS vault page: http://vault.centos.org . I'm working with CentOS build 1611 here so I used: http://vault.centos.org/7.3.1611/os/x86_64/Packages/centos-release-7-3.1611.el7.centos.x86_64.rpm

If you're unsure on what version you are use `cat /etc/centos-release` to find out.

Now install the repo using:

```bash
rpm -Uvh --force http://vault.centos.org/7.3.1611/os/x86_64/Packages/centos-release-7-3.1611.el7.centos.x86_64.rpm 
```

And now you can run the command's to verrify:
```bash   
yum makecache fast
yum update
```