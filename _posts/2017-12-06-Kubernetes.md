---
layout: post
title: Kubernete's tutorial notes
tags: Kubernetes Cluster docker 
category: blog
date: 2017-12-06
---

#Some concepts of Kubernetes Clusters

- **Kubernetes cluster**(KC) can be analogy to the conventional computational cluster, which includes many computers and to work as a single unit. 

- application runs on the KC needs to be **containerized**.

- the **program** organizing  the KC, namely **Kubernete**, manage the deployment and make containerized application run in KC in a more efficient way.

## Kubernetes cluster structure
- Master ( act as a manager): coordinate the cluster
- Nodes (act as worker, the job of work is to run the containerize application)

![KC diagram](https://d33wubrfki0l68.cloudfront.net/99d9808dcbf2880a996ed50d308a186b5900cec9/40b94/docs/tutorials/kubernetes-basics/public/images/module_01_cluster.svg)

### Master responsibilities : coordinate activities in cluster
- scheduling application
- maintaining application states
- scaling application
- rolling new updates

### Node(worker)
- **definition of Node**:
  A node can be a VM or a physical computer that serves as a worker machine in a KC. So, you can think node is just a physical laptop/desktop.
- What node have?
| name       | description                              | Analogy |
| ---------- | ---------------------------------------- | ------- |
| Kubelet    | this guy does two thing: 1. managing the node; 2. communicate with master |         |
| Docker/rkt | tool to handle containerized application(containers) |         |

### Kubernetes application deployment
1. Kubernetes deployment process
  - tell the master to start application containers. 
  - then master schedules the containers to run on the cluster's nodes.
  - master exposes the Kubernetes' API. The node also communicate with this API
  - end user can use Kubernete's API to interact with cluster
  - Kubernetes can be deployed on either physical or vertial machines
2. Kubernetes development,**Minikube**
  - one simple Kubernetes implementation(simple cluster only contains one node) 
  - Minikube CLI provide basic bootstrapping operations for cluster (start, stop, status and delete)

## Kubernetes interactive tutorial
### Module 1: Creating a cluster
1. check the minikube. Type the commands in terminal

```bash 
minikube version
```

2. start a cluster

```bash
minikube start
```
3. to interact with Kubernetes, we will use the command line interface, **kubectl**. To check whether Kubectl is installed, you can run kubectl version check.

```bash
kubectl version
```
4. to see the cluster details, we can type the command

```bash
kubectl cluster-info
```
5. to view the cluster's nodes, we can type the command

```bash
kubectl get nodes
```
###Module 2. Deploy an Application using Kubectl
#### Kubernetes application deployments
1. running your Kubernetes cluster
2. deploy containerized application on top of it. This requires you to create a  Kubernetes _Deployment_ configuration.
3. Deployment configuration instruct Kubernetes how to create and update instances of your application.
4. Once the deployment is created by you. the Kubernetes master loads the mentioned application instances into individual nodes.
#### After Kubernetes applications are deployed
1. once the application instances are created, a** Kubernetes Deployment Controller** continuously monitors those instances. 
2. if a node goes down or get deleted, the **Deployment Controller** replaces it. _this provides a self-healing mechanism to address machine failure or maintainance._
#### Now, let's try to deploy your first app on Kubernetes
Never the less, here's a diagram of showing the relationships between different components of Kubernetes cluster.
![KC application deployment](https://d33wubrfki0l68.cloudfront.net/152c845f25df8e69dd24dd7b0836a289747e258a/4a1d2/docs/tutorials/kubernetes-basics/public/images/module_02_first_app.svg)
1. you can create and manage a deployment by using kubernetes command line interface, **kubectl**. 
2. when you create a deployment, you need to specify the container image for your application and how many replicas that you want to run. 
#### First deployment example, Module 2
- ** Node.js** application packaged in a Docker container. [source code](https://github.com/kubernetes/kubernetes-bootcamp)
- goal: deploy your first app on Kubernetes using **kubectl**.
1. Kubectl basic commands


| command                 | description                |
| :---------------------- | :------------------------- |
| ```kubectl version```   | get the version of kubectl |
| ```kubectl get nodes``` | view the nodes in cluster  |

2. run an app using Kubectl

```bash
kubectl run <command>
```

example:

```bash
kubectl run kubernetes-bootcamp --image=docker.io/jocatalin/kubernetes-bootcamp:v1 --port=8080
```

What this previous command did?

- **find** the available nodes that can be used to run the application
- **schedule** the application to run on that node
- **configure** the **cluster** to reschedule the instance on a new Node when needed

3. to list your deployments

```bash
kubectl get deployments
```
4. View our apps
- **pods**: running inside Kubernetes are running on a private, isolated network. (**What the fuck is this pods?**). By default, they are visible from other pods and services within the same kubernetes cluster, but not outside that network. 

- ```kubectl``` can create a proxy that will forward communications into cluster-wide, private network. (**But how? Show me the code**). ```kubectl``` interacting through an API endpoint(**Wtf?**) to communicate with application.

- This is how, we use a second terminal to open the proxy:
```bash
kubectl proxy
```
So, this is the understanding, ```kubectl``` is a piece of program that is run in the bash terminal(host pc). 
After the proxy execution, we now have a connection between our host(terminal) and the **Kubernetes cluster**. The proxy enables direct access to the API through terminals.
- Once this proxy is set, it means we have set up the communications between host and Kubernetes cluster. But how do we actually interact with the pod?
- what it actually happens, is that the proxy will automatically set up proxy endpoints (similar like ```http://proxy/endpoints```) for each pods and we can actually query the APIs for individual pods by using internet protocols with ```curl``` command, for example.
  - You can see all those APIs hosted through the proxy endpoint, now available at through http://localhost:8001. For example, we can query the version directly through the API using the curl command:
    ```
    curl http://localhost:8001/version
    ```
  - The API server will automatically create an endpoint for each pod, based on the pod name, that is also accessible through the proxy.
  - we can get all the pods name and store it in an environment variable POD_NAME (on the host)
    ```
    export POD_NAME=$(kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}')
    ```


### Summary of this section

#### terms and concepts

| Terms                    | Concepts                                 |
| ------------------------ | ---------------------------------------- |
| Kubernetes Cluster (KC)  | The actual cluster that contains many nodes(VMs or physical PCs) |
| Kubernetes               | Sometimes indicate the program that organize the KC, managing the application deployment and scheduling. |
| Containerize             | Application is packed in a way that is independent from its environment |
| Master node              | A special node in the KC. Its main tasks are scheduling and controlling the applications that are running in the normal KC nodes. So, in this sense, the master node controls the normal nodes. In analogy, this nodes is like a manager |
| Nodes                    | The actual PCs or VMs that are used for running the applications. In analogy, these nodes are like workers. |
| Kubelet                  | A piece of software (called "agent" in Kubernetes official document) that taking control of an actual node, and managing the communication with master. Every node has a Kubelet. |
| Docker/rkt               | This is a tool to handle containerized applications. Since every node needs to run containerize application, node needs a tool that can be used to manage these applications. |
| minikube                 | A simple Kubernetes cluster implementation. It only contains 1 Node. |
| kubectl                  | The command line interface that is used to interact with the Kubernetes program. So this "**kubectl**" is actually independent of Kubernetes cluster and nodes. |
| Deployment configuration | A configuration file that tells the Kubernetes Cluster how to configure and deploy your applications. Then the master node will then load this file and deploy the applications to nodes. The deployment can also be done by using the "kubectl" command line interface. |
| Deployment controller    | Once the application is deployed on nodes, a Deployment controller will take in charge to monitor all these applications. If nodes goes down or deleted, the deployment controller will replace it with new one. |
| pods(?)                  | The concept of this is not quite clear yet. To me, it is more like a running instance of an application. There may be more than one instance of applications. In analogy, the pods is like an instance of a class, there are maybe several instances of same class. (```dog = Animal(); cat = Animal()```, ```dog``` and ```cat``` are similar to pods) |
| Proxy(?)                 | The KC is more like a network, where as each pod is act like an user in the KC network. In the KC,  a proxy build up a communication channel that allows the pods to be able to communicate with each other. |
| endpoint(?)              | Every pods can be linked to the proxy with an address. In my understanding, this address can be referred as endpoint. |