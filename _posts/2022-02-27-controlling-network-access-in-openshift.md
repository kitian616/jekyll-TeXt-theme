---
title:  "Controlling Ingress with Openshift Network Policy's"
date: 2022-02-27
toc: true
mermaid: true
categories: [EX280]
tags: [openshift, kubernetes, ex280] # TAG names should always be lowercase
---

This blog will go in to the "software defined networking" of ["Configure networking components"](https://www.redhat.com/en/services/training/ex280-red-hat-certified-specialist-in-openshift-administration-exam?section=Objectives) objective of the [EX280](https://www.redhat.com/en/services/training/ex280-red-hat-certified-specialist-in-openshift-administration-exam?section=Overview) exam from RedHat. In this post we will:

- Traffic to pods
- The types of Network Policy's we can create
- Create a Network Policy based on a application label

> This post focuses on Ingress (incoming traffic). You can also create Egress policy's to manage outgoing traffic
{: .prompt-tip }


As always. We will be doing all the examples in a CRC ([Code Ready Containers](https://developers.redhat.com/products/codeready-containers/overview)) environment.

# Traffic to pods
As explained in my post about [Services and Routes]() pods are accessed in the cluster by using services. We can filter traffic to these services using Network Policy's. These can allow traffic based on different 'keys' called identifiers. By default no traffic is blocked to a service and you can not block traffic from a pod to itself. When you add a Network Policy all traffic is blocked by default.
Also, good to keep in mind is that Network Policy's are cumulative. Meaning they won't cancel each other out.

## Types of Network Policy Identifiers
You can use three (3) ways to block or allow traffic to your Service, you can filter:
1. **Pods:** by using a label (podSelector). You can allow traffic from certain pods in your cluster.
2. **Namespace:** by using the label (namespaceSelector) you can allow access from a given namespace in the cluster
3. **IP Blocks:** by using the IP (ipBlock) you can block or allow IPv4 addresses to access a service

## Example of a Network Policy
A `{networkPolicy}` can look like this:

```yaml
kind: NetworkPolicy
apiVersion: networking.k8s.io/v1
metadata:
  name: allow-from-label
spec:
  podSelector: 
    matchLabels:
      app: nginx
  ingress:
  - from:
    - podSelector: 
        matchLabels:
          access-to-service: "true"
```

In this example:
- We create a network policy called `allow-from-label`
- It will work on the pods that are labeled as `app: nginx`
- It will allow access to our service if the pod that access our service has a label `access-to-service` which is set to `true`

To create a IPBlock type Network Policy we would use:

```yaml
kind: NetworkPolicy
apiVersion: networking.k8s.io/v1
....
  ingress:
  - from:
    - ipBlock:
        cidr: 172.17.0.0/16
        except:
        - 172.17.1.0/24
```

To create a namespace type Network Policy we would use:

```yaml
kind: NetworkPolicy
apiVersion: networking.k8s.io/v1
....
  ingress:
  - from:
    - namespaceSelector:
        matchLabels:
          project: myproject
```

# Creating a policy
To test if we can block traffic to a pod using a Network Policy we will spin up two (2) apps called `server` and `client` in our namespace called `restricted-network`. We will secure access to our `server` service by creating a Network Policy called `access-policy`:

Creating the project and apps:
```bash
$ oc new-project restricted-network
$ oc new-app --name client --image bitnami/nginx
$ oc new-app --name server --image bitnami/nginx
```

Now that we have created the server app we can run a `curl` command using `oc exec` with our client pod and check if we can connect to it. But first we have to `expose` the server:

```bash
$ oc expose service/server
$ oc get route
NAME     HOST/PORT                                    PATH   SERVICES   PORT   TERMINATION   WILDCARD
server   server-restricted-network.apps-crc.testing          server     8080    None
```

Check, our target will be the service: `server`. Lets `curl` it:

```bash
$ oc exec -it client-76ccdb697d-n2xqp -- curl -v server:8080 | grep HTTP
> GET / HTTP/1.1
< HTTP/1.1 200 OK
```

Great! We can set up a connection. Now lets see what happens when we create the following network policy:

```yaml
# allow-from-label.yaml
kind: NetworkPolicy
apiVersion: networking.k8s.io/v1
metadata:
  name: allow-from-label
spec:
  podSelector:
    matchLabels:
      policy: "true"
  ingress:
  - from:
    - podSelector:
        matchLabels:
          access-to-service: "true"
```

And let's apply it:

```bash
$ oc apply -f allow-from-label.yaml
networkpolicy.networking.k8s.io/allow-from-label configured 
```
```bash
$ oc get networkpolicies.networking.k8s.io
NAME               POD-SELECTOR        AGE
allow-from-label   deployment=server   5s 
```

Now we only need to add a label to our server to link this Network Policy. We will apply the label: `policy: "true"`:

```bash
$ oc label pod server-68ff6d4bfd-prd4w policy=true
pod/server-68ff6d4bfd-prd4w labeled
```

Let's test our access again with the `-m` flag (`--max-time`), otherwise we will be waiting a long time:

```bash
$ oc exec -it client-76ccdb697d-n2xqp -- curl -v -m 3 server:8080 | grep HTTP
command terminated with exit code 28 
```

No we will add the label `access-to-service: "true"` to our pod client and try again:

```bash
$ oc label pod client-76ccdb697d-n2xqp access-to-service=true
pod/client-76ccdb697d-n2xqp labeled
$ oc exec -it client-76ccdb697d-n2xqp -- curl -v server:8080 | grep HTTP
> GET / HTTP/1.1
< HTTP/1.1 200 OK
```

And thats a simple demo of adding a Network Policy, applying it to a pod and granting access to it by adding a label to a pod. 

# Wrapping up
Controlling ingress traffic to services and pods gives you (and your developers) a great way to increase security in the cluster. By segmenting access based on labels or namespaces you can easily isolate important services from the rest of the cluster.

I hope this post has helped you. Check out my other EX280 related content on my [EX280 page](https://blog.benstein.nl/ex280/)