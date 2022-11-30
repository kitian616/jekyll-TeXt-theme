---
title:  "Scaling applications - Rollout, Scale and Auto-Scale Deployments"
date: 2022-01-23
toc: true
mermaid: true
categories: [EX280]
tags: [openshift, kubernetes, ex280] # TAG names should always be lowercase
---

This post is gonna be a bit bigger broader then what is requested on the [EX280 exam objectives](https://www.redhat.com/en/services/training/ex280-red-hat-certified-specialist-in-openshift-administration-exam?section=Objectives). In this post we will have a look at:
- Controlling roll-out of pods (manually rolling out or back a deployment)
- Scaling Deployments on the command-line
- Creating a auto scaler for a deployment on the command-line

Now, apart from the scaling being an exam objective it's really handy to know how to scale pods and how to control the rollout of deployments. This is certainly something I use almost on a daily basis when operating a container cluster. So, let's go!

# What even is a "Deployment"

When we use `oc new-app` we are creating a `Deployment` that sets up a `ReplicaSet` which in turn starts te pods. 

```mermaid
graph LR
    Deployment --> |Manages| ReplicaSet--> |Manages| Pods
```

Let's have a look at the options.

## Deployment or DeploymentConfig
So, I found this really confusing. In Openshift there are resource to control your deployment (the app that you want to run). In short, this is how it is:
- **Deployment** is an API resource that controls ScaleSets and Pods. The `deployment` resource is kubernetes native and is the default that is being used when using `oc new-app` [^DeploymentConfigsOpenshift]
- **DeplymentConfig** is almost the same as a `deployment`. It used to be included in Kubernetes but has been removed from the API. In Openshift we can still use the resource when we use the `oc new-app` command with the flag `--as-deployment-config` [^DeploymentConfigsOpenshift]

[^DeploymentConfigsOpenshift]: https://docs.openshift.com/container-platform/4.9/applications/deployments/what-deployments-are.html

To create a DeploymentConfig from the command line use:
```bash
$ oc new-app --as-deployment-config ... 
```

`Deployment` and `DeploymentConfigs` are API object in your cluster that manage the pods that are running. They do this (most of the time) by using ScaleSets. When u update a Deployment (with a ConfigMap for example) the Deployment will trigger a new rollout of the pod(s). 

## Viewing Deployments and DeploymentConfigs
We can view `Deployments` and `DeploymentConfigs` with the basic `oc get RESOURCE_TYPE` command. To get some data we will setup a namespace and deploy two app's to it. We will  create the app `deployment-app` as an example of a `Deployment` and a second app called `deploymentconfig-app` as an example of a `DeploymentConfig`: 

```bash
$ oc new-project deploying-can-be-fun
$ oc new-app --name deployment-app --image bitnami/nginx
$ oc new-app --as-deployment-config --name deploymentconfig-app --image bitnami/nginx
```

Now lets `get` them:
```bash
$ oc get deployment
NAME             READY   UP-TO-DATE   AVAILABLE   AGE
deployment-app   1/1     1            1           51
```

```bash
$ oc get deploymentconfig
AME                   REVISION   DESIRED   CURRENT   TRIGGERED BY
deploymentconfig-app   1          1         1         config,image(deploymentconfig-app:latest)
```

Straight away we can see a difference between the two. The "Triggered by" field is shown on the the `DeploymentConfig`. As we can see the app will be rolled out when the `config` changes and it's currently on the `latest` status

## Rolling out a new Deployment
What if you want to rollout a new version of your app? Just for fun without changing it. We can do that

### DeploymentConfig style
Running a new deployment with a `DeploymentConfig` is really easy, use [^DeploymentOpenshift] :

```bash
$ oc rollout latest deploymentconfig/deploymentconfig-app
deploymentconfig.apps.openshift.io/deploymentconfig-app rolled out
```

[^DeploymentOpenshift]: https://docs.openshift.com/container-platform/4.9/applications/deployments/managing-deployment-processes.html

And your done. To verify your work you can use `oc rollout history` to see that the cluster has rolled out a new version:

```bash
$ oc rollout history deploymentconfig/deploymentconfig-app
deploymentconfig.apps.openshift.io/deploymentconfig-app
REVISION	STATUS		CAUSE
1		Complete	config change
2		Complete	manual change 
```

### Deployment style
To trigger a new rollout of a `Deployment` we are going to change something in the `Deployment`. We will trigger a new rollout by updating the "last restart" of the container using `oc patch`:

```bash
$ oc patch deployment/deployment-app --patch \
"{\"spec\":{\"template\":{\"metadata\":{\"annotations\":{\"last-restart\":\"`date +'%s'`\"}}}}}"
deployment.apps/deployment-app patched
```

## Rolling back
A great feature of rollouts is that a history of your rollouts is kept. This makes it easy to recover from configuration mistakes or to abort and rollback the release of a new rollout. 

To rollback a rollout you can use `oc rollout undo`:

```bash
$ oc rollout undo deploymentconfig deploymentconfig-app
deploymentconfig.apps.openshift.io/deploymentconfig-app rolled back 
```
```bash
$ oc rollout undo deployment deployment-app
deployment.apps/deployment-app rolled back 
```

### Deployment history
As seen in a previous example we can see the history of rollouts. This also gives us the ability to rollback to a specific version, simply use the `--to-revision` flag:

```bash
$ oc rollout history deploymentconfig/deploymentconfig-app
deploymentconfig.apps.openshift.io/deploymentconfig-app
REVISION	STATUS		CAUSE
1		Complete	config change
2		Complete	manual change
3		Complete	manual change
```

```bash
$ oc rollout undo --to-revision 1 deploymentconfig/deploymentconfig-app
deploymentconfig.apps.openshift.io/deploymentconfig-app rolled back
```

## Further options
There are allot of other options you can use with `oc rollout`. Have a look at the help prompt:
```yaml
$ oc rollout -h
Start a new rollout, view its status or history, rollback to a previous revision of your app.

....

Usage:
  oc rollout SUBCOMMAND [flags]

Available Commands:
  cancel      Cancel the in-progress deployment
  history     View rollout history
  latest      Start a new rollout for a deployment config with the latest state from its triggers
  pause       Mark the provided resource as paused
  restart     Restart a resource
  resume      Resume a paused resource
  retry       Retry the latest failed rollout
  status      Show the status of the rollout
  undo        Undo a previous rollout 
```

# Scaling it up
One thing that you hear allot when talking about container platforms is the ability to "scale up" applications. This is done by increasing the number of pods that run you application. Scaling can be done manually or automatically by your cluster. Let's have a look.

## Scaling from the command line
We will use a demo app called `scale-me` for this demo in the namespace `the-sky-is-the-limit`:

```bash
$ oc new-project the-sky-is-the-limit
$ oc new-app --name scale-me --image bitnami/nginx
```

When we take a look at the running pods we will see that our `new-app` command created a single pod:

```bash
$ oc get pods
NAME                        READY   STATUS    RESTARTS   AGE
scale-me-85b69877f9-xxvbp   1/1     Running   0          92s
``` 

Let's scale it up using the `oc scale` command:

```bash
$ oc scale deployment/scale-me --replicas 10
deployment.apps/scale-me scaled
``` 
```bash
$ oc get pods
NAME                        READY   STATUS    RESTARTS   AGE
scale-me-85b69877f9-czjvm   1/1     Running   0          13s
scale-me-85b69877f9-jdcwp   1/1     Running   0          13s
scale-me-85b69877f9-k6v45   1/1     Running   0          13s
scale-me-85b69877f9-k75c4   1/1     Running   0          13s
scale-me-85b69877f9-ldsv4   1/1     Running   0          13s
scale-me-85b69877f9-p2pq2   1/1     Running   0          13s
scale-me-85b69877f9-pspnn   1/1     Running   0          13s
scale-me-85b69877f9-sl78f   1/1     Running   0          13s
scale-me-85b69877f9-t62b8   1/1     Running   0          13s
scale-me-85b69877f9-xxvbp   1/1     Running   0          2m24s
```

And well, that's it. It's that easy.

# Scaling on auto
As I wrote before. Scaling can also be done automatically. Normally this is done by letting the cluster keep an eye on the CPU usage of the pods [^OpenshiftAutoScaling]. We do this by creating a `autoscaler`. An autoscaler can automatically increase or decrease number of pods deployed within the deployment as needed.

[^OpenshiftAutoScaling]: https://docs.openshift.com/container-platform/4.9/nodes/pods/nodes-pods-autoscaling.html

<div markdown="span" class="alert alert-info" role="alert"><i class="fa fa-exclamation-circle"></i> <b>Info:</b> The `autoscaler` uses the Openshift metric server to check the CPU usage of a pod. If you do not have a running metric server (like when using CRC) you can create autoscalers but they wont do anything
</div>

## Creating a auto-scaler
To create the autoscale we will use `oc autoscaler`. We will set it to scale to minimum of 5 pods and a max of 10. We will use 60% as the target CPU usage:

```bash
$ oc autoscale deployment/scale-me \
  --min 5 \
  --max 10 \
  --cpu-percent 60
horizontalpodautoscaler.autoscaling/scale-me autoscaled
``` 

And thats it... Ow wait! There is more. If you been following along with this last example and you do a `oc get pods` you might notice something strange:
```bash
$ oc get pods
NAME                        READY   STATUS    RESTARTS   AGE
scale-me-85b69877f9-czjvm   1/1     Running   0          10m
scale-me-85b69877f9-jdcwp   1/1     Running   0          10m
scale-me-85b69877f9-k6v45   1/1     Running   0          10m
scale-me-85b69877f9-k75c4   1/1     Running   0          10m
scale-me-85b69877f9-ldsv4   1/1     Running   0          10m
scale-me-85b69877f9-p2pq2   1/1     Running   0          10m
scale-me-85b69877f9-pspnn   1/1     Running   0          10m
scale-me-85b69877f9-sl78f   1/1     Running   0          10m
scale-me-85b69877f9-t62b8   1/1     Running   0          10m
scale-me-85b69877f9-xxvbp   1/1     Running   0          12m 
```

That's strange? We set the `--min` to 5 pods right?

```bash
$ oc get horizontalpodautoscaler.autoscaling/scale-me
NAME       REFERENCE             TARGETS         MINPODS   MAXPODS   REPLICAS   AGE
scale-me   Deployment/scale-me   <unknown>/60%   5         10        10         7m38 
```

So why are there still 10 pods running? This is because the `autoscale` resource uses the requested CPU of a pod to scale. When we take a look at our `Deployment`:

```bash
$ oc get deployment/scale-me -o yaml | grep request
```

We will see that our `deployment` has no requested CPU. I haven't talked about about requests yet but here is a simple way to set one:

```bash
$ oc set resources deployment/scale-me --requests cpu=200m
deployment.apps/scale-me resource requirements updated 
```

After this our pod's should be rolled out once again (because of a config change) and the cluster should scale them down to 5.

# Wrapping up
I hope you found this useful. I really struggled with the difference between `Deployment` and `DeploymentConfig` at the start. Let's hope this has helped you.

Do you want to see more of my EX280 post's? Checkout my [EX280 page](https://blog.benstein.nl/ex280/)