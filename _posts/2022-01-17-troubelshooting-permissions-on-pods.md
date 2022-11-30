---
title:  "ServiceAccounts and SCCs - Running pods with more permission's"
toc: true
mermaid: true
categories: [EX280]
tags: [openshift, kubernetes, ex280]
---

In this blog we will take a look at SCC permissions that are needed to run a pod with escalated permissions and how to use a serviceaccount (`sa`) to run such a pod or deployment.

Small thing: I use the term pod and container in this post but I'm referring to the same idea. In Openshift and Kubernetes we talk about pods being the smallest unit of measurement. Each pod usually runs one container but there are pods that run multiple containers.

# Understanding Rootless
I won't explain fully why and what rootless means in general but will focus on the parts that are importent to know in a Openshift environment and according to the [EX280 exam objectives](https://www.redhat.com/en/services/training/ex280-red-hat-certified-specialist-in-openshift-administration-exam?section=Objectives). If you do wanna go deep on rootless, namespaces and more I highly recommend the following video's by RedHat:
- [Overview of Rootless Podman: Part 1 - Understanding Root Inside and Outside a Container](https://www.youtube.com/watch?v=ZgXpWKgQclc)
- [Overview of Rootless Podman: Part 2 - How User Namespaces Work in Rootless Containers](https://www.youtube.com/watch?v=Ac2boGEz2ww)

## UIDs and Namespaces
So what are Namespaces? A namespace is a feature of the Linux kernel that allows you to segment resources [^Wiki2022]. All things that make up a running system or process are at home in a Namespace. You'll find Process ID's (PID) in there as well as mounts and user ID's (UID).
Simply said, a Namespace is a organizational container to partition resources in. Now, your Linux base system has a great way of orchestrating all these different resources based on their unique ID's and maps process ID's that are spawned inside a container back to the host system so that the stuff that needs running (the processes that make up the containers) can actually get resources and stay separated from each other. 

[^Wiki2022]: https://en.wikipedia.org/wiki/Linux_namespaces

So, when we are talking about container technology and containers being "contained" we are actually talking about this awesome namespace feature.

## So, what are the risks?
There are different risks associated with running containers that default to a root account. The obvious risk is the same as on normal Linux server. Imagine all accounts that are available on a Linux host having root privileges. Now imagine someone gaining access to that system. When you run a pod that defaults to the root account (aka a root container) you're basically exposing the same risk. You can have all the security of a cluster, but if someone manages to drop a executable in a pod or gain access to it they could do a lot of damage.

If you want to go deeper on rootless containers, the risks and how to avoid them, [check out this video by Synk](https://www.youtube.com/watch?v=YrN49thJREY).

## Openshift to the rescue(?)
One of the things that Openshift takes care of (in comparison with the out-of-the-box Kubernetes cluster) is that it won't allow root containers to run by default. This can lead to some issues as some containers need more access to the underling host or special resources or that the images are not setup for rootless usage. For these cases Openshift provides Security Context Constraints to manage elevated rights for pods.

# SCC's and SA's
SCC? SA? What do you mean? Let me (try) to explain:
- **SCC:** Security Context Constrains. A SCC is used to provide access to a certain resource like: `anyuid` (which allows the pod to run under any UID), `hostaccess` (which gives the pod host access) and `hostnetwork` (which, well I'll let you guess that one yourself)
  
  To see all SCCs that are available in you cluster you can use:
    ```bash
    $ oc get scc -o name
    securitycontextconstraints.security.openshift.io/anyuid
    securitycontextconstraints.security.openshift.io/hostaccess
    securitycontextconstraints.security.openshift.io/hostmount-anyuid
    securitycontextconstraints.security.openshift.io/hostnetwork
    securitycontextconstraints.security.openshift.io/machine-api-termination-handler
    securitycontextconstraints.security.openshift.io/nonroot
    securitycontextconstraints.security.openshift.io/privileged
    securitycontextconstraints.security.openshift.io/restricted 
    ```
    (to see more details, use `oc get scc`)
- **SA:** A Service Account is used to run a pod with a special SCC. In Openshift you create Service Accounts and bind them to a deployment to hand out permissions. You can see 

# Running a container with elevated permissions
A great container image to use as an example for this post is the `nginx` container which is available in both a root a rootless version.
For our example we will create two apps based on the two different images. One is the official `nginx` container image and the second one is the `bitnami/nginx` rootless version:

## Creating the deployments
We will create a new namespace and two apps:
```bash
$ oc new-project root-more-or-less
$ oc new-app --name root-container --image docker.io/nginx
$ oc new-app --name rootless-container --image docker.io/bitnami/nginx
``` 

Strait away we can see some issues with our root pod:
```bash
$ oc get pods
NAME                                  READY   STATUS    RESTARTS      AGE
root-container-67c49b777d-wdzn5       0/1     Error     3 (28s ago)   58s
rootless-container-59c496f955-zh7qd   1/1     Running   0             76s 
```

## Troubleshooting
Let's have a look at whats going on here. We will start with a simple `oc status` command:
```bash
$ oc status --suggest
....
Errors:
  * pod/root-container-67c49b777d-wdzn5 is crash-looping

The container is starting and exiting repeatedly. This usually means the container is unable to start, misconfigured, or limited by security restrictions. Check the container logs with

      oc logs root-container-67c49b777d-wdzn5 -c root-container

Current security policy prevents your containers from being run as the root user. Some images may fail expecting to be able to change ownership or permissions on directories. Your admin can grant you access to run containers that need to run as the root user with this command:

      oc adm policy add-scc-to-user anyuid -n root-or-less -z default
....
```

Of the bat we get two hints of fixing this. `oc status` is telling us to check the logs of the pod and that the pod might not be able to run due to a SCC. Let's not blindly follow the suggestion of adding `anyuid` to this pod and explore further with the logs:
```bash
$ oc logs root-container-67c49b777d-wdzn5
/docker-entrypoint.sh: /docker-entrypoint.d/ is not empty, will attempt to perform configuration
/docker-entrypoint.sh: Looking for shell scripts in /docker-entrypoint.d/
/docker-entrypoint.sh: Launching /docker-entrypoint.d/10-listen-on-ipv6-by-default.sh
10-listen-on-ipv6-by-default.sh: info: can not modify /etc/nginx/conf.d/default.conf (read-only file system?)
/docker-entrypoint.sh: Launching /docker-entrypoint.d/20-envsubst-on-templates.sh
/docker-entrypoint.sh: Launching /docker-entrypoint.d/30-tune-worker-processes.sh
/docker-entrypoint.sh: Configuration complete; ready for start up
2022/01/22 15:51:47 [warn] 1#1: the "user" directive makes sense only if the master process runs with super-user privileges, ignored in /etc/nginx/nginx.conf:2
nginx: [warn] the "user" directive makes sense only if the master process runs with super-user privileges, ignored in /etc/nginx/nginx.conf:2
2022/01/22 15:51:47 [emerg] 1#1: mkdir() "/var/cache/nginx/client_temp" failed (13: Permission denied)
nginx: [emerg] mkdir() "/var/cache/nginx/client_temp" failed (13: Permission denied)
```
As we can see, the pod is expecting to be run as a root user:
```bash
 [warn] 1#1: the "user" directive makes sense only if the master process runs with super-user privileges, ignored in /etc/nginx/nginx.conf:2
```

Before we fix this, let's check out the SCC's the pod's are currently using:
```bash
$ oc describe pod | grep -i SCC
    openshift.io/scc: restricted
    openshift.io/scc: restricted
```

Now lets use this amazing policy command to find out what SCC our root container wants to user:
```bash
$ oc get pod root-container-67c49b777d-wdzn5 -o yaml \
  | oc adm policy scc-subject-review -f -
RESOURCE                              ALLOWED BY
Pod/root-container-67c49b777d-wdzn5   anyuid
```

Looks like our pod wants to use `anyuid` just as suggested when we ran the `oc status` command.

## Creating a ServiceAccount
As mentioned before, if you want to run a pod with a different SCC you'll need to create a ServiceAccount to run the pod. The SA will provide the runtime with the elevated permissions that we add to the SA

We will create a SA called `ok-go-for-it` in our namespace with:
```bash
$ oc create sa ok-go-for-it \
  --namespace root-more-or-less
  serviceaccount/ok-go-for-it created
``` 

A small tip, always create the SA with a `--namespace` (or `-n`) flag to ensure the SA is bound to the namespace.

### Giving special permissions to a ServiceAccount
Now let's add some permissions to our brand spanking new SA. We use `oc adm policy` to add permissions to users, groups and ServiceAccounts. When we add permissions to a SA we use the `-z` flag:
```bash
$ oc adm policy add-scc-to-user -h
Add a security context constraint to users or a service account.

Usage:
  oc adm policy add-scc-to-user SCC (USER | -z SERVICEACCOUNT) [USER ...] [flags 
```

```bash
$ oc adm policy add-scc-to-user anyuid \
  -z ok-go-for-it \
  --namespace root-more-or-less
clusterrole.rbac.authorization.k8s.io/system:openshift:scc:anyuid added: "ok-go-for-it"
```

### Adding a ServiceAccount to a Deployment
Now to update our app. We got our SA ready and our permissions permitted. Let's not waist time with editing `yaml` and set the serviceaccount for our app using `oc set`:
```bash
$ oc set serviceaccount deployment root-container ok-go-for-it
deployment.apps/root-container serviceaccount updated
```

And just to verify:
```bash
$ oc get pods
NAME                                  READY   STATUS    RESTARTS   AGE
root-container-5d6c7cc66b-sqhhs       1/1     Running   0          19s
rootless-container-59c496f955-zh7qd   1/1     Running   0          20m 
```
```bash
$ oc describe pod | grep -i SCC
              openshift.io/scc: anyuid
              openshift.io/scc: restricted
```

# Wrapping up
As you can see it's pretty easy to use SAs to apply special permissions to deployments. That doesn't mean you should do it without thought. There are use cases in which it's necessary that a deployment might need to run with elevated permissions but there are a lot of other solution's than simply handing out the permissions. So stay sharp!

Do you want to see more of my EX280 post's? Checkout my [EX280 page](https://blog.benstein.nl/ex280/)