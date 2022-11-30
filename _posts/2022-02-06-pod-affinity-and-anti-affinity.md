---
title:  "Pod Affinity and Anti-affinity - Spreading out workloads"
date: 2022-02-06
toc: true
mermaid: true
categories: [EX280]
tags: [openshift, kubernetes, ex280] # TAG names should always be lowercase
---

I have written about using [Taints and Tolerations](https://blog.benstein.nl/posts/tolerating-tolerations-openshift/) to prevent pods from running on certain (tainted) nodes and there is some influence on scheduling that we can exert using [Limits and Requests](https://blog.benstein.nl/posts/quotas-limits-and-limit-ranges/). 
But if we really want to control pod placement we have to look no further than Node/Pod Affinity and Anti-Affinity. This allows you to specify nodes that your pod can run on (Pod Affinity) and can be used to spread out your pods at runtime to different nodes using Anti-Affinity. Why? Because it's great to have a container cluster, but if all your pods are landing on a single note your not gonna have a great (up)time. Let's get started!

As always, these concepts apply to both Kubernetes and Openshift. We will try to do everything from the `oc` command line.

# Understanding  Affinity
Affinity means to have a natural liking to something. In Openshift it means that there is a connection (a preferred grouping) of resources. Naturally Anti-Affinity inverses this. With Affinity you can group workloads together on a single host or ensure that pods land on the same server. This can be useful if your workload gains performance by being scheduled together. The inverse is also true. By using Anti-Affinity rules we can make sure not all of our frontend pods are being run on the same node so that when it might go down or get busy our application pods won't go down all at once.

Affinity is specified in your pod spec, `pod.spec.affinity`. Tip! Use `oc explain pod.spec.affinity` for some helpful info:

```yaml
$ oc explain pod.spec.affinity
KIND:     Pod
VERSION:  v1

RESOURCE: affinity <Object>

DESCRIPTION:
     If specified, the pod's scheduling constraints

     Affinity is a group of affinity scheduling rules.

FIELDS:
   nodeAffinity	<Object>
     Describes node affinity scheduling rules for the pod.

   podAffinity	<Object>
     Describes pod affinity scheduling rules (e.g. co-locate this pod in the
     same node, zone, etc. as some other pod(s)).

   podAntiAffinity	<Object>
     Describes pod anti-affinity scheduling rules (e.g. avoid putting this pod
     in the same node, zone, etc. as some other pod(s)).
```

## Node Affinity
With `nodeAffinity` we can ask the pod to be scheduled (or not to be scheduled) on a node with a certain label. This works a lot like a `toleration` (`pod.spec.tolerations`)

## Pod Affinity
- `podAffinity` is used to tell our pod to schedule our pod with other pods based on affinity rules
- `podAntiAffinity` enables us to separate pods based on affinity rules

## Required fiels
When using a Affinity rule you also need to specify the `topologyKey: kubernetes.io/hostname` in the `yaml`. Also, when using a Preferred rule you need to set a `weight` so that the scheduler knows (on a scale from 1-100) how strongly it should weigh the preference. 

## Why not taint?
At this point you might be asking, why not use a toleration or the nodeSelector found in the pods spec? This is a good question. Using these techniques gives us controll on where to place a pod but it does based on static information on the node. Using Affinity rules we can schedule dynamilcy based on where other pods are located.

# Affinity Rules
So, how does this work? Affinity Rules use matchExpressions based on `key=value` pairs to match. We will take the following `yaml` as an example:
```yaml
kind: Pod
metadata:
  name: looking-for-a-green-pod
spec:
  affinity:
    podAffinity: 
      requiredDuringSchedulingIgnoredDuringExecution:
      - labelSelector:
          matchExpressions:
          - key: color
            operator: In
            values:
            - green
            - darkgreen
            - lightgreen
        topologyKey: kubernetes.io/hostname
  containers:
  - name: looking-for-a-green-pod
    image: docker.io/ocpqe/hello-pod
```

This will create a pod called `looking-for-a-green-pod` that looks for another pod that has the key `color` with one of three values `green`, `darkgreen` and `lightgreen`.

We could easily create a pod called `black-and-white` that just wont schedule on the same node as a pod with color defined using the following affinity rule:

```yaml
spec:
  affinity:
    podAffinity: 
      requiredDuringSchedulingIgnoredDuringExecution: 
        labelSelector:
        - matchExpressions:
          - key: color
            operator: Exists
```


## Understanding operators
The operators we can use are:
- **In** Meaning one of the `values` in the `key` matches our `value`
- **NotIn** Meaning the `value` is not in the `value` of the `key`
- **Exists** Meaning the `value` exists in the `key`
- **DoesNotExist** The `value` should not exist in the `key`
- **Lt** Lesser then
- **Gt** Greater then

## Required and Preferred
We can set up our Affinity rules in two modes, "Required" and "Preferred". Let me explain:
- **Required** Affinity rules have to be met before a pod is scheduled on a node
- **Preferred** Affinity rules are, well, preferred. We would like these rules to be met but we can be a bit more flexible

# Creating pods with Affinity rules
Lets spin up two pods that want to be scheduled together, `green-pod` and `looking-for-a-green-pod`:

```yaml
# green-pod.yaml
apiVersion: v1
kind: Pod
metadata:
  name: green-pod
  labels:
    color: green
spec:
  containers:
  - name: green-pod
    image: docker.io/ocpqe/hello-pod
```

```yaml
# looking-for-a-green-pod
apiVersion: v1
kind: Pod
metadata:
  name: looking-for-a-green-pod
spec:
  affinity:
    podAffinity: 
      requiredDuringSchedulingIgnoredDuringExecution:
      - labelSelector:
          matchExpressions:
          - key: color
            operator: In
            values:
            - green
            - darkgreen
            - lightgreen
        topologyKey: kubernetes.io/hostname
  containers:
  - name: looking-for-a-green-pod
    image: docker.io/ocpqe/hello-pod
```

You can save both these definitions to a `yaml` file and use `oc apply -f FILE` to create the. When this is done they should both be running on the same node:

```bash
$ oc get pods -o wide
NAME                      READY   STATUS    RESTARTS   AGE   IP             NODE                 NOMINATED NODE   READINESS GATES
green-pod                 1/1     Running   0          11m   10.217.0.98    crc-ktfxm-master-0   <none>           <none>
looking-for-a-green-pod   1/1     Running   0          64s   10.217.0.101   crc-ktfxm-master-0   <none>           <none>
```

Let's create our pod that does not like any color:
```yaml
# black-and-white.yaml
apiVersion: v1
kind: Pod
metadata:
  name: black-and-white
spec:
  affinity:
    podAntiAffinity: 
      requiredDuringSchedulingIgnoredDuringExecution: 
      - labelSelector:
          matchExpressions:
          - key: color
            operator: Exists
      topologyKey: kubernetes.io/hostname
  containers:
  - name: black-and-white
    image: docker.io/ocpqe/hello-pod
```

Now when we have a look at our pods we will see that our newest one does not like to run with the other pods:

```bash
$ oc get pods -o wide
NAME                      READY   STATUS    RESTARTS   AGE     IP             NODE                 NOMINATED NODE   READINESS GATES
black-and-white           0/1     Pending   0          14s     <none>         <none>               <none>           <none>
green-pod                 1/1     Running   0          16m     10.217.0.98    crc-ktfxm-master-0   <none>           <none>
looking-for-a-green-pod   1/1     Running   0          6m34s   10.217.0.101   crc-ktfxm-master-0   <none>           <none> 
```

**Note** Because this is running on CRC which is single node cluster the pod will *not* start because there are no other nodes available.

And we can see the effect with `oc describe`:

```bash
$ oc describe pod black-and-white
....
Events:
  Type     Reason            Age                 From               Message
  ----     ------            ----                ----               -------
  Warning  FailedScheduling  36s (x2 over 101s)  default-scheduler  0/1 nodes are available: 1 node(s) didn't match pod anti-affinity rules.
```

Not lets change the pod from requiring the affinity rule from being met to a preffered rule. This is not as simple as swapping out  `requiredDuringSchedulingIgnoredDuringExecution` because a preferred rules needs some extra information to work with, we will update our `yaml` to:

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: black-and-white
spec:
  affinity:
    podAffinity: 
      preferredDuringSchedulingIgnoredDuringExecution:
      - weight: 50
        podAffinityTerm:
          labelSelector:
            matchExpressions:
            - key: color
              operator: Exists
          topologyKey: kubernetes.io/hostname
  containers:
  - name: black-and-white
    image: docker.io/ocpqe/hello-pod
```

As we can see in the events now all pods are scheduled on the same node. Even the `black-and-white` pod because despite its preference there is simply no other node to run on.

```bash
$ oc get events
LAST SEEN   TYPE      REASON             OBJECT                        MESSAGE
1m         Normal    Scheduled          pod/black-and-white           Successfully assigned all-together-now/black-and-white to crc-ktfxm-master-0 
```

# Wrapping up
Using Affinity Rules can help us dynamically select where our pods are scheduled based on node labels and other pods. This makes it easy to spread out a workload across a cluster or keep pods together for maximum performance.

I hope this post has helped you. Check out my other EX280 related content on my [EX280 page](https://blog.benstein.nl/ex280/)