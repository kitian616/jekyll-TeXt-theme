---
title:  "Taints and Tolerations - Can't Pods and Nodes just get along?"
toc: true
mermaid: true
categories: [EX280]
tags: [openshift, kubernetes, ex280]
---

During my study for [EX280](https://www.redhat.com/en/services/training/ex280-red-hat-certified-specialist-in-openshift-administration-exam) I found Taints and Tolerations very hard to understand. This is because Taints and Tolerations flip the scheduling of pods the other way around. What I mean by this is the following. Traditionally you would specify on a server what the server would run and what not. But in OpenShift this is flipped around. Instead of creating a list on a Server that allows or disallows apps to run we "taint" the node wit a `key=value` pair and a `effect` and let the scheduler sort out which pods are assigned to the node. How do we get a specifiek pod to run on a "tainted" node? We do this by "tolerating" the taint. For example; If we would taint a node with the following `location:westus` we could let a pod tolerate that this node is running at that location.

> ⚠️ FYI; this blog is written for Openshift but the theory is the same and most of the `oc` commands can be replaces with `kubectl`

# But why?
Using taints is a great way to dynamically schedule workloads across different nodes with different `key:value` pairs. A few examples could be:
  - Nodes that are in a certain datacenter: `location=datacenter01:PreferNoSchedule`
  - Nodes that have a special CPU: `cpu=special:NoSchedule`
  - Nodes that we have a special place for in our heart: `specialNode=true:NoExecute`

We can also combine different taints to get a specifiek selection of nodes that we want for our workload. Using taints can make scheduling your workloads a lot easier!

## Understanding the effects
A taint consist of `key=value:effect`. The effect are how the node will handle the taint[^OpenshiftTaints], if:
- `NoSchedule` is selected, Openshift wil not schedule pods on that node that do not tolerate the taint. Existing pods will keep running.
- `PreferNoSchedule` is selected, Openshift wil try not to schedule pods on the node that do not tolerate the taint. This is a great way to use taints without ending up with underutilized nodes.
- `NoExecute` is selected, Openshift will nog schedule pods that do not tolerate the taint. Openshift will also evict existing pods from the node. When you `cordon` a node in Openshift it basically gets this taint and asks all pods to get out of there. If there are pods that tolerate this taint then the additional paramater `tolerationSeconds` in the toleration can be parsed to allow the pod some time to shut down. 

[^OpenshiftTaints]: https://docs.openshift.com/container-platform/4.9/nodes/scheduling/nodes-scheduler-taints-tolerations.html

# In practice
So now we know why, on to the how. You can follow along with these commands on a [CRC](https://developers.redhat.com/products/codeready-containers/overview) instance or on a local [K3s](https://k3s.io/) cluster. Don't forget to change the command's from `oc` to `kube` or `kubectl`

## Tainting a node
Tainting a node is pretty easy. It' done using the `oc adm taint` command:
```bash
$ oc adm taint node crc-hsl9k-master-0 linux=good:NoSchedule
node/crc-hsl9k-master-0 tainted
```

So, now only pods that "tolerate" that `linux=good` will be scheduled on this node.

## Removing a taint
Untainting a node can easily be done by adding a `-` after our taint command:
```bash
$ oc adm taint node crc-hsl9k-master-0 linux=good:NoSchedule-
node/crc-hsl9k-master-0 untainted 
```

## The taint in action

Let's spin up a pod and see the effect of our taint:
```bash
$ oc new-project tainted-love
$ oc new-app --name i-like-linux --docker-image bitnami/nginx
```

In my case this results in a pending pod because CRC is a one (1) node cluster. Great for this example. If you run multiple nodes and want to replicate the effect you should taint them all. Be aware that this will prevent other pods from running (even pods used by operators).

The reason my pod is in pending?
```bash
$ oc describe pod i-like-linux-968b9cdbc-n5lb6
...
Events:
  Type     Reason            Age                 From               Message
  ----     ------            ----                ----               -------
  Warning  FailedScheduling  35s (x2 over 114s)  default-scheduler  0/1 nodes are available: 1 node(s) had taint {linux: good}, that the pod didn't tolerate.
```

## Adding a toleration to a pod
At the time of writing there is no easy CLI command to add a toleration to a pod or deployment. That means we will have to edit the `yaml` of the deployment. You can do this live by using `oc edit deployment DEPLOYMENT_NAME` or we can export the current config, edit it and send it back to the Cluster. Yeah, lets do that.

### Exporting the deployment yaml
We will export the deployment using `oc get` and the `-o yaml` flag. In the past there was an easy way to get the `yaml` without extra data but in this case we have to do it ourself:
```bash
$ oc get deployment i-like-linux -o yaml > tolerate_app.yaml
```

We can now edit the file and add our toleration. Look for the `spec` of our container:
```yaml
spec:
....
  template:
....
    spec:
    progressDeadlineSeconds: 600
    replicas: 1
    revisionHistoryLimit: 10
    selector:
      matchLabels:
        deployment: i-like-linux
    strategy:
      rollingUpdate:
        maxSurge: 25%
        maxUnavailable: 25%
      type: RollingUpdate
    template:
      metadata:
        annotations:
          openshift.io/generated-by: OpenShiftNewApp
        creationTimestamp: null
        labels:
          deployment: i-like-linux
      spec:
        containers:
        - image: bitnami/nginx@sha256:8f5062e816099c770d98613b95c86b4e1ac8d369712237a579fc3121225e55e2
          imagePullPolicy: IfNotPresent
          name: i-like-linux
          ports:
          - containerPort: 8443
            protocol: TCP
          - containerPort: 8080
            protocol: TCP
          resources: {}
          terminationMessagePath: /dev/termination-log
          terminationMessagePolicy: File
        dnsPolicy: ClusterFirst
        restartPolicy: Always
        schedulerName: default-scheduler
        securityContext: {}
        terminationGracePeriodSeconds: 30
....
```

And add a toleration like (in this case I will be adding it under the `spec.spec.dnsPolicy` field):
```yaml
spec:
....
  template:
....
    spec:
    dnsPolicy: ClusterFirst
    tolerations:
    - key: linux
      value: good
      operator: Equal
      effect: NoSchedule
....
```

And patch that back to the cluster:
```bash
$ oc apply -f tolerate_app.yaml
deployment.apps/i-like-linux configured
```

> ⚠️ FYI; If you want to edit the yaml again you will need to re-export it

After this our pod should be running
```bash
$ NAME                            READY   STATUS    RESTARTS   AGE
i-like-linux-54b9b5fb7c-sndtx   1/1     Running   0          59s 
```

## How does this work
Taints and tolerations are matched by the scheduler of the cluster. In this case we gave the toleration of "Equal" (`operator: Equal`) to match with a node that has the same taint (`key=linux` and `value=good`). The effect (`NoSchedule`) must also match.

### Wildcards
You can also create a wild card toleration on a pod. This is done with the following values:
```yaml
spec:
....
  template:
....
    spec:
      tolerations:
      - effect: NoSchedule
        operator: Exists
```

Or
```yaml
spec:
....
  template:
....
    spec:
      tolerations:
      - key: linux
        operator: Exists
```

In this case the `Exits` operator will only check if the taint or effect existst on the node [^ExistsToleration]

[^ExistsToleration]: https://docs.openshift.com/container-platform/4.9/nodes/scheduling/nodes-scheduler-taints-tolerations.html#nodes-scheduler-taints-tolerations-about_nodes-scheduler-taints-tolerations

There is even a way to tolerate all taints[^ToleratingAllTaints] :
```yaml
spec:
....
  template:
....
    spec:
      tolerations:
      - operator: "Exists"
```

[^ToleratingAllTaints]: https://docs.openshift.com/container-platform/4.9/nodes/scheduling/nodes-scheduler-taints-tolerations.html#nodes-scheduler-taints-tolerations-all_nodes-scheduler-taints-tolerations

# Wraping up
Taints are a great way to make the scheduling of your pods more predicable. It allows you designate specifiek nodes for specifiek workloads based on your usecase. There is a lot more to do with taints like combining them or applying them dynamically to nodes but I will not cover that in this blog.