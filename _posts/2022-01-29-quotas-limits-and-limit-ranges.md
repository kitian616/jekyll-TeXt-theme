---
title:  "Quotas, limits and limit-ranges"
date: 2022-01-29
toc: true
mermaid: true
categories: [EX280]
tags: [openshift, kubernetes, ex280] # TAG names should always be lowercase
---

In this post we will be diving in to quotas and limits. Quotas and limits can be used to allocate, limit en reserve resources within your cluster for one or multiple namepaces and can limit the usage of CPU and Memory for workloads. Limits can also be used to gurante (by reserving) resources for your workloads.
Understanding and using quotas and limits from day one can help plan the resource growth of your cluster and prevents unwanted resource depletion. Using these API objects helps your cluster to more effectively schedule workloads across your cluster while also making sure they get their piece of resources when they need it.

As always, these concepts apply to both Kubernetes and Openshift. We will try to do everything from the `oc` command line.

# Controlling resources allocation
We have options to control how many resources namespaces and pods are allowed to use or create in a cluster. This comes in handy to prevent unwanted growth of a namespace and prevent depletion of vital compute resources. 
Let's start with a overview:
- **Limits** (api:  can set the limits and request of a workload. With a limit we can limit a workload to use a maximum amount of CPU or request a minimum amount of CPU. You can find limits under `pods.spec.containers.resource`
  - **Limit** is the maximum amount of a resource that can be used. Found under `pods.spec.containers.resource.limits`
  - **Request** is the amount of a resource that is reserved. Requests are set under `pods.spec.containers.resource.requests`
- **Limit Ranges** are great because they assign default limits and requests to pods that don't have any and can set a threshold on how much every individual pod can use. This gives you finer control on resource allocation then a Quota because a quota looks at all requests in a namespace. Limit ranges use the API object `limitrange`
- **Quotas** are used to set how many resources a namespace can create and/or request. We can limit the amount of pods that can run in a single namespace. We can also use a quota to limit the amount of CPU all workloads can use by enforcing the usage of a CPU limit on each pod or the amount of memory the workloads can request. A quota is created of the type `resourcequota`
- **Cluster Quotas** can be used across the Openshift cluster and are not bound to a single namespace. A Cluster Qouta of the object `clusterresourcequota`

## What do the units mean?
  When we request or limit compute resources we do this on `Memory` and `CPU` . The amount that we want is expressed in units in different ways in `yaml`:
  - **CPU** A single virtual CPU is always a `1` in `yaml` or `1000m`. We can use the smaller unit to divide and share the CPU computing power between workloads. The actual clock speed of the CPU in this case is not relevant but could have an impact when you move to another cluster or CPU type [^CPUInKubernetes].
  - **Memory** Unlike CPU memory is expressed in bytes. You can use a fixed number like `1500M` or a power-of-two equivalent like `512Mi` [^MemoryInKubernetes]. We will use the later in our examples.

[^CPUInKubernetes]: https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/#meaning-of-cpu
[^MemoryInKubernets]: https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/#meaning-of-memory

# Understanding Limits
A limit can consist of an upper value (limit) and lower (request) threshold that you set on a pod or container. Limits on individual pods are usually baked in to the `yaml` by most applications (you'll see a lot of `cpu: 10m` requests in containers, see below for why). Most of the time we will set a limit for a complete deployment.

Limits on a pod can look like this on a deployment:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
....
spec:
....
  spec:
    containers:
      ....
      resources:
          limits:
            cpu: 500m
            memory: 512Mi
          requests:
            cpu: 10m
            memory: 128Mi       
```

## The request part
A part of a limit is that you can also set a request. This tells the scheduler that in order to run your workload you'll need a certain amount of CPU or Memory. The scheduler will place your pods on a node that has this amount free and the amount you request is held in reserver for you. A lot of pods have a CPU request of `10m`. So thats 1/100 of a vCPU. The reason for this is that they often don't use a lot of CPU but in the case of a high load on a node they would still receive *some* CPU from the node. 
Some things to keep in mind:
- If you set no requests on a pod and the node on which the pod is running is getting busy your pod might not receive any CPU or Memory. Why? Because you didn't tell the cluster you needed any
- If you set only a limit and no request of a resource type the cluster will take your limit and turn it in to the request. So if you set a high limit, be sure to set the right request so that your pod wont eat up all resources [^LimitButNoRequest].
- If you set a request but no limit the cluster will try to default to the values set in a `limitrange` or the cluster default setting [^RequestButNoLimit].

 [^LimitButNoRequest]: https://kubernetes.io/docs/tasks/administer-cluster/manage-resources/cpu-default-namespace/#what-if-you-specify-a-container-s-limit-but-not-its-request
 [^RequestButNoLimit]: https://kubernetes.io/docs/tasks/administer-cluster/manage-resources/cpu-default-namespace/#what-if-you-specify-a-container-s-request-but-not-its-limit

## Hitting a limit
So we set a limit. Everything is running but then al of sudden cHaOs. Pods go wild, workloads go crazy! Your whole namespace is on fire! 🔥 Resources are being eaten up by different pods. CPU and memory usage is climbing! What will happen now?

- **Hitting the CPU Limit** No worries, hitting a CPU limit is not cause for panic. Your pods will be throttled by the cluster. This might have some unforseen impact on perfomance but in most cases your fine
- **Hitting the Memory Limit** Now this is a bit more tricky. When you hit your memory limit in a cluster the cluster will kill the workout with the classic line `OOM` (Out Of Memory). Even if the node has `48Gi` of Memory and is not using half of it. If your pod hits your defined Memory limit it is killed [^3OOMAndMemoryUsage].

[^3OOMAndMemoryUsage]: https://kubernetes.io/docs/tasks/configure-pod-container/assign-memory-resource/#if-you-do-not-specify-a-memory-limit

## Understanding Limit Ranges
A `limit` applies a limit (or request) of resources on a pod. A `limitrange` can apply a minimum, maximum or default value for limits and requests of a pod or container in your namespace. If for example you want to have all pods in a namespace request a minimum of `128Mi` of Memory you can create a `limitrange` to do this.

## Limits on a whole Namespaces
As we said before. We set limits on pods (using the deployment) or on individual containers. But how do we control the total usage of CPU and Memory of a namespaces? But how do we do this? By using a quota!

# Understanding Quotas
Quotas can be used to limit the amount of resources that can be created, requested or used. Usually quotas are set on a namespace basis (so they apply to the sum of the resources in the namespace) but they can also be set on a group of namespaces or even on a per user basis.

Limits can for example:
- Control the maximum amount of pods that can run 
- Limit the amount of routes used by a namespace
- Control how many services can be created
- Set the maximum amount of CPU all pods can request
- Set a maximum amount of Memory that can be used at once

 In short "Quotas can be used to apply limits". 

 A quota can look like this
 ```yaml
apiVersion: v1
kind: ResourceQuota
metadata:
  name: my-quota
  namespace: a-quota-for-me
spec:
  hard:
    cpu: "1"
    memory: 1G
    persistentvolumeclaims: "10"
    pods: "2"
    replicationcontrollers: "2"
    resourcequotas: "1"
    secrets: "5"
    services: "3"
 ```

`cpu` and `memory` in this example relate to requests. To set a limit use `limits.cpu` and `limits.memory`. You can also go 100% correct and use `requests.cpu` and `requests.memory` and save yourself some troubleshooting time in the future. 

## Understanding quota ranges
A quota can be crated with two different scopes:
- `resourcequota` Is created with `oc create quota`
- `clusterresourcequota` Is created with `oc create clusterresourcequota`

So what are the differences? 

### Range of `resourcequota`
A quota of the type `resourcequota` is designed to apply a quota of resources to one project or namespace. You could add this resource to the default template of your Openshift projects creation to apply a default quota to all new projects that are created.
The quota is created on a project/namespace level.

### Range of `clusterresourcequota`
As the name suggets, cluster quotas (`clusterresourcequota`) are created and designed to work across you cluster. The way they are applied is by using selectors that matches to a label on a project or by using the `openshift.io/requester` annotation to link them to a project owner. This can come in handy when you are also using the cluster for developers to experiment on and don't want them to eat up all the resources. Be sure to update the `openshift.io/requester` value on other projects.

## Cumulative Quotas
You can create (or apply) multible quota's to a single namespace (but this is not recomendend). If you do, the effect is cummulative (meaning all quota's will be merged in to one). If you specify the quota for a specifiek resourcetype (like pods) in multible quota's the lowest value will be used. For example:

```bash
$ oc get resourcequotas
NAME      AGE    REQUEST                       LIMIT
quota-1   3m     pods: 5/10, secrets: 9/10
quota-2   3m     configmaps: 2/10, pods: 5/5
```

2 Quota's are set: `quota-1` and `quota-2`. One specifies a limit on secrets and the other on configmaps. This is added up, you an create 10 configmaps and secrets.
Both quota's specify a maximum number of running pods (`max: 5` and `max: 10`). The lowest one takes precedence. So a maximum of 5 pods can run.

## Failure due to quotas
When you set a quota and you try to start things up that exceed that quota some strange things might happen, like:
- When you set a quota of 1 pod per namespace and you try to do a rolling deployment you might encounter some issues. This is because for a rolling deployment to work two (or more depending on the amount of replicas) are required. This is not allowed by your quota so it will get stuck
- If you try to start up a pod with no requests or limits but you have set resource limits in your quota the pods will not start (because it has no limits set)
- When exceeding the amount of Memory that is allowed by your quota Openshift will give you some time to adjust in stead of killing your pod.
 
# Creating and viewing Limits and Requests
Lets get bussy on the command line and create some limits and request

## Limits on Pods
Lets create a namespace called `limit-the-sky` with a demo app called `airplane`:
```bash
$ oc new-project limit-the-sky
$ oc new-app --name airplane --image bitnami/nginx
```

Now lets do something crazy. Let's set a request thats way to high for our current cluster:

```bash
$ oc set resources deployment airplane \
  --requests cpu=12
deployment.apps/airplane resource requirements updated
```

What did we do? We set a request for our app for 12 whole vCPU's. Let's see if that flies:

```bash
$ oc get pods
NAME                        READY   STATUS    RESTARTS   AGE
airplane-5d8d87c8d5-lsqsd   0/1     Pending   0          58s
airplane-779789c8cc-2sfqz   1/1     Running   0          2m32s 
```
```bash
$ oc describe pod airplane-5d8d87c8d5-lsqsd
....
Events:
  Type     Reason            Age                From               Message
  ----     ------            ----               ----               -------
  Warning  FailedScheduling  24s (x2 over 91s)  default-scheduler  0/1 nodes are available: 1 Insufficient cpu.
```

As we can see that just won't fly in the current cluster. Now lets set something more realistlcy. We are going to ask our cluster for `10m` CPU and `128Mi` of memory and we are going to set a upper limit of a half vCPU (`500m` or `0.5`) and a maximum amount of Memory of `512Mi`:

```bash
$ oc set resources deployment airplane \
  --requests cpu=10m,memory=128Mi \
  --limits cpu=500m,memory=512Mi
deployment.apps/airplane resource requirements updated 
```

That flies:
```bash
$ oc get pods
NAME                        READY   STATUS    RESTARTS   AGE
airplane-59cbf468f7-w52xq   1/1     Running   0          18s 
```

We can always check the limit of a deployment using the `oc describe` command:
```bash
$ oc describe deployment airplane
  ....
  Pod Template:
  Labels:       deployment=airplane
  Annotations:  openshift.io/generated-by: OpenShiftNewApp
  Containers:
   airplane:
    Image:       bitnami/nginx@sha256:78cb209a82fca83aee2f2d71f7115165f911acf1fcc6ce48e1c8bddeb5191049
    Ports:       8080/TCP, 8443/TCP
    Host Ports:  0/TCP, 0/TCP
    Limits:
      cpu:     500m
      memory:  512Mi
    Requests:
      cpu:        10m
      memory:     128Mi
    Environment:  <none>
    Mounts:       <none>
  Volumes:        <none>
```

## Setting default, min and max with a limitrange
Now, you don't want to manually add limits and requests to everything. We can take care of this by creating a `limitrange`. A limitrange can't entirly be created from CLI but the setup is pretty easy (tip: use `oc explain limitrange` to see the available fields):
```yaml
# limit-range.yaml
apiVersion: v1
kind: LimitRange
metadata:
  name: a-limit-range
  namespace: limit-the-sky
spec:
  limits:
    - type: Container
      default:
        cpu: 50m
      max:
        cpu: 500m
      min:
        cpu: 10m
```

```bash
$ oc apply -f limit-range.yaml
limitrange/a-limit-range created
```

And let's have a look at it:
```bash
$ oc describe limitranges a-limit-range
Name:       a-limit-range
Namespace:  limit-the-sky
Type        Resource  Min  Max   Default Request  Default Limit  Max Limit/Request Ratio
----        --------  ---  ---   ---------------  -------------  -----------------------
Container   cpu       10m  500m  50m              50m
```

And now, when we crate a new deployment that has no CPU limits:
```bash
$ oc new-app --name zeplin --image bitnami/nginx
```

And let's have a look:
```bash
$ oc get pods zeplin-b79b4dcf6-jgtkh -o yaml | grep cpu
    kubernetes.io/limit-ranger: 'LimitRanger plugin set: cpu request for container
      zeplin; cpu limit for container zeplin'
        cpu: 50m
        cpu: 50m 
```

What happens if we try to go above (or below) the limit?
```bash
$ oc set resources deployment/zeplin --limits cpu=1000m
deployment.apps/zeplin resource requirements updated
```

That's strange? We set the mac allowed CPU to `500m` right? So, why no error? So, there is actually an error but it's hard to find. You can see the error using `oc get events` , by doing a `oc get replicasets` or by using `oc describe deployment zeplin`. You will see that the cluster is refusing to rollout the latest replicaset because of the limitrange:

```bash
$ oc get events | grep cpu
....
7m33s       Warning   FailedCreate        replicaset/zeplin-7dc7446698   Error creating: pods "zeplin-7dc7446698-cpqdh" is forbidden: maximum cpu usage per Container is 500m, but limit is 1k
7m32s       Warning   FailedCreate        replicaset/zeplin-7dc7446698   Error creating: pods "zeplin-7dc7446698-cdxdm" is forbidden: maximum cpu usage per Container is 500m, but limit is 1k
6m11s       Warning   FailedCreate        replicaset/zeplin-7dc7446698   (combined from similar events): Error creating: pods "zeplin-7dc7446698-95g7s" is forbidden: maximum cpu usage per Container is 500m, but limit is 1k
```

Or in the deployment:
```bash
$ oc describe deployment zeplin
....
Conditions:
  Type             Status  Reason
  ----             ------  ------
  ReplicaFailure   True    FailedCreate
  Available        True    MinimumReplicasAvailable
  Progressing      True    ReplicaSetUpdated
OldReplicaSets:    zeplin-b79b4dcf6 (1/1 replicas created)
NewReplicaSet:     zeplin-6f58fb66cd (0/1 replicas created)
```

# Creating and viewing Quotas
Now lets take a step back and look at a the bigger picture. It's great to set limits and requests on pods but this is a cluster we are using. So lets step it up. We are going to create a namespace `a-quota-for-me` with a our quota called `my-quota`:
```bash
$ oc new-project a-quota-for-me
$ oc create quota my-quota \
  --hard=cpu=1,memory=1G,pods=2,secrets=1
resourcequota/my-quota created
```

Let's have a look at our quota:
```bash
$ oc describe resourcequotas my-quota
Name:       my-quota
Namespace:  a-quota-for-me
Resource    Used  Hard
--------    ----  ----
cpu         0     1
memory      0     1G
pods        0     2
secrets     9     1
```

Thats strange! 9 secrets in use? But we set a limit to 1? This can happen because we set the quota after the creation of these secrets. A quota enforces its restrictions on new resources and not on existing. For example, when we try to create a secret now:

```bash
$ oc create secret generic just-try-it --from-literal key1=password
error: failed to create secret secrets "just-try-it" is forbidden: exceeded quota: my-quota, requested: secrets=1, used: secrets=9, limited: secrets=1 
```

## Applying quota to a deployment
Now lets run a demo application called `you-can-quota-me-on-that`:
```bash
$ oc new-app \
  --name you-can-quota-me-on-that \
  --image bitnami/nginx \
  --as-deployment-config
```

But our pod is nowhere to be found:
```bash
$ oc get pods
No resources found in a-quota-for-me namespace
``` 

Why? Because we set a resource limit in the quota (a CPU and Memory max) and our pod does not have one:
```bash
$ oc describe deploymentconfigs you-can-quota-me-on-that
  ....
  Events:
  Type		Reason			Age			From				Message
  ----		------			----			----				-------
  Normal	DeploymentCreated	4m20s			deploymentconfig-controller	Created new replication controller "you-can-quota-me-on-that-1" for version 1
  Warning	FailedRetry		2m42s			deployer-controller		Stop retrying: couldn't create deployer pod for "a-quota-for-me/you-can-quota-me-on-that-1": pods "you-can-quota-me-on-that-1-deploy" is forbidden: failed quota: my-quota: must specify cpu,memory
  Warning	FailedCreate		95s (x24 over 4m20s)	deployer-controller		Error creating deployer pod: pods "you-can-quota-me-on-that-1-deploy" is forbidden: failed quota: my-quota: must specify cpu,memory
```

Or:
```bash
$ oc get events
61s         Warning   FailedCreate                     deploymentconfig/you-can-quota-me-on-that   Error creating deployer pod: pods "you-can-quota-me-on-that-1-deploy" is forbidden: failed quota: my-quota: must specify cpu,memory
```

So lets set a request like in our previous example:
```bash
$ oc set resources deploymentconfig you-can-quota-me-on-that \
  --requests cpu=10m,memory=56Mi
deploymentconfig.apps.openshift.io/you-can-quota-me-on-that resource requirements updated
```

But that won't do the trick. Why? Because we set the requests on the pod and the error message is telling us the deployer pod is no allowed. So lets edit that by hand. To do that we add the requests under the deployment strategy:
````bash
$ oc edit deploymentconfigs you-can-quota-me-on-that
spec:
  replicas: 1
  revisionHistoryLimit: 10
  selector:
    deploymentconfig: you-can-quota-me-on-that
  strategy:
    activeDeadlineSeconds: 21600
    resources: {}
    rollingParams:
      intervalSeconds: 1
      maxSurge: 25%
      maxUnavailable: 25%
      timeoutSeconds: 600
      updatePeriodSeconds: 1
    type: Rolling
    resources:
      requests:
        cpu: 10m
        memory: 56Mi
````

Now lets rollout the latest version:
```bash
$ oc rollout latest you-can-quota-me-on-that
deploymentconfig.apps.openshift.io/you-can-quota-me-on-that rolled out

NAME                                READY   STATUS      RESTARTS   AGE
you-can-quota-me-on-that-3-deploy   0/1     Completed   0          24s
you-can-quota-me-on-that-3-k2pl7    1/1     Running     0          18s
```

## Hitting quota limits
Let's scale that up to 4 replicas:
```bash
$ oc scale deploymentconfig you-can-quota-me-on-that --replicas 4
deploymentconfig.apps.openshift.io/you-can-quota-me-on-that scaled
```
```bash
$ oc get pods
NAME                                READY   STATUS      RESTARTS   AGE
you-can-quota-me-on-that-3-deploy   0/1     Completed   0          70s
you-can-quota-me-on-that-3-k2pl7    1/1     Running     0          64s
you-can-quota-me-on-that-3-v5xnp    1/1     Running     0          10s 
```

That's stange. Where are the pods? As mentioned before. A quota denies deployment of resources that exceed the quota. We have requested four pods to be created but the cluster will only give use two.
We can see the error in the `replicationcontroller`
```bash
$ oc describe replicationcontrollers you-can-quota-me-on-that-3
....
Events:
  Type		Reason				Age			From				Message
  ----		------				----			----				-------
  Warning  FailedCreate      2m35s                replication-controller  Error creating: pods "you-can-quota-me-on-that-3-tcn57" is forbidden: exceeded quota: my-quota, requested: pods=1, used: pods=2, limited: pods=2
  Warning  FailedCreate      2m35s                replication-controller  Error creating: pods "you-can-quota-me-on-that-3-w9wp6" is forbidden: exceeded quota: my-quota, requested: pods=1, used: pods=2, limited: pods=2
  Warning  FailedCreate      25s (x8 over 2m34s)  replication-controller  (combined from similar events): Error creating: pods "you-can-quota-me-on-that-3-zmp82" is forbidden: exceeded quota: my-quota, requested: pods=1, used: pods=2,
```

## Creating a `clusterresourcequota`
We can create a cluster quota that wil target our user anna with the following command[^CreateClusterQuota]\:
```bash
$ oc create clusterquota for-user-anna \
     --project-annotation-selector openshift.io/requester=anna \
     --hard pods=10 \
     --hard secrets=20 
```

[^CreateClusterQuota]: https://docs.openshift.com/container-platform/4.8/applications/quotas/quotas-setting-across-multiple-projects.html


To see a resourcequota that is applied to the namespace use:
```bash
$ oc describe AppliedClusterResourceQuota
Name:		for-user-anna
Created:	2 minutes ago
Labels:		<none>
Annotations:	<none>
Namespace Selector: ["annas-project"]
Label Selector:
AnnotationSelector: map[openshift.io/requester:anna]
Resource	Used	Hard
--------	----	----
pods		10	10
secrets		9	20 
```

# Wrapping it up
This post turned out rather long! Lets review the information with some examples:
- We use `Limits` to set a limit (the maximum amount of a resource that a pod can use) and requests (the amount of resources a pod needs and is ensured) on containers and pods.
  - You can use a `limit` to set the maximum amount of CPU usage by a pod or container to 1 CPU
  - You can ensure a pod or container always gets `512Mi` of Memory using a `request`
- We use `LimitRanges` to set a default, minimum or maximum of the resources each pod can get.
  - We can use a `LimitRange` to set a default Memory request for all pods
  - A `LimitRange` can limit the maximum amount of CPU a pod can request to `200m`
- We use `ResourceQuota` to set the maximum number of objects in a namespace or to enforce the usage of a limit and the total amount of resources a namespace can use
  - A quota in a namespace can limit the sum of all CPU requests to 2 CPU's (`2000m` or `2`)
  - You can limit the amount of routes you can have in a namespace to 1
  - You can force pod's to have a set limit or request
- And we can use a `ClusterResourceQuota` to span a quota over multiple namespaces based on a field tag or the requester (owner) of a project.
  - With a cluster quota we can set a limit of 20 pods that can be running by a user
  - We can limit the amount of CPU used to 4 (`4000m`) of several namespaces that we have tagd as `production=true`

All in all, pretty usefull stuf. Be sure to practice with this and run in to strange situations! 

Do you want to see more of my EX280 post's? Checkout my [EX280 page](https://blog.benstein.nl/ex280/)