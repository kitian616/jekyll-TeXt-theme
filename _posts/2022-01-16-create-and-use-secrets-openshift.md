---
title:  "Creating and Using Secrets in OpenShift"
toc: true
mermaid: true
categories: [EX280]
tags: [openshift, kubernetes, ex280]
---

Creating Secrets ([like creating ConfigMaps](https://blog.benstein.nl/posts/setting-config-maps-from-the-cli/)) is a vital part of Managing OpenShift workloads and is an exam objectives of the [EX280 exam](https://blog.benstein.nl/ex280/). Knowing how they work and how to configure them (from the command-line) will give you a better understanding of Secrets.

# Secrets, how do they work
Secrets provide information (like config files or credentials) to workloads in your cluster. You can use a secret to provide a username and password to your pod or to add a SSL certificate to it.

## No data on the node
When exposing secrets using volumes the data is stored in a `tmpfs` filesystem. This means that the data in de secret is not directly exposed to the node that the pod's are running on[^1].

[^1]: https://docs.openshift.com/container-platform/4.9/nodes/pods/nodes-pods-secrets.html#nodes-pods-secrets-about_nodes-pods-secrets

This provides a bit more security than that of ConfigMaps.


## Secret types
There are 3 secret types available for you to us. Don' worry, they are quite easy to keep apart:
- **docker-registry** secrets are used by the internal docker registry of your container platform
- **tls** secrets are used to provide tls certificates, keys or other supporting files to a pod
- **generic** secrets are used to store everything from an API key to a config file

I will be discussing **generic** secrets in this post.

## Encoding vs Encryption
An important factor to know when working with secrets are that they are not encrypted. The "secret" part of secrets is that they are not readable without decoding them. By default secrets are encodes in a `base64` encoding. 
When you create a secret with, for example, a password `key=value` pair; the value will be encoded in `base64`. 

We can simulate this in bash:

```bash
$ echo 'encoded_password' | base64
ZW5jb2RlZF9wYXNzd29yZAo=

$ echo 'ZW5jb2RlZF9wYXNzd29yZAo=' | base64 -d
encoded_password
```

In this case the `key=value` pair would be rendered in our secret like this (because only the value is encoded, and not the key):

```yaml
password=ZW5jb2RlZF9wYXNzd29yZAo=
```

When we create a secret from `yaml` we need to encode it first. But when we create a secret with `oc create` the cluster takes care of this encoding for us. If you would use `oc edit` to live edit a secret, don't forget to encode the string you are replacing.

# Creating a secret
Creating a secret can be done by creating a `yaml` file and applying it to the cluster or by using the `oc create secret` command.

## Creating a secret from yaml
Creating a secret form a `yaml` definition is quite easy when you know the layout. You always get an example from the [official documentation](https://docs.openshift.com/container-platform/4.6/nodes/pods/nodes-pods-secrets.html#nodes-pods-secrets-about-examples_nodes-pods-secrets) or use `oc explain secrets`

The following is a `yaml` secret with our own value filled in:
```yaml
# our-secret.yaml
apiVersion: v1
kind: Secret
metadata:
  name: test-secret
  namespace: project-of-secrets
type: Opaque 
data: 
  password: ZW5jb2RlZF9wYXNzd29yZAo=
```

If we wanted to create a secret from this we would do so with:
```bash
$ oc apply -f our-secret.yaml 
```

## Creating a secret from the cli
If your in a hurry (or don't want to create `yaml` files for all your secrets). You can create a secret without a definition file.

As a demo you can setup a special testing namespace. As you can see in my `yaml` example I called this one `project-of-secrets`:

```bash
$ oc new-project project-of-secrets 
```

### From Literal
You could create a secret from the command-line with the following command:
```bash
$ oc create secret generic \
  SECRET_NAME \
  --from-literal KEY1=VALUE1 \
  --from-literal KEY2=VALUE2
```

As an example:
```bash
$ oc create secret generic test-secret-literal \
--from-literal password=encoded_secret 
secret/test-secret-literal created
```

### From File
If you want your secret to be filled with an entire file:
```bash
$ oc create secret generic \
  SECRET_NAME \
  --from-file KEY1=/PATH/TO/FILE1 
```

As an example:
```bash
$ echo "Our verry secret file" > secret-file.txt
$ oc create secret generic test-secret-file --from-file secret-file.txt
secret/test-secret-file created
```

## Adding a secret to a pod
Secrets can be supplied to a pod much like ConfigMaps. For more info on those, I wrote an entire [blog regarding the creation and usage of them](https://blog.benstein.nl/posts/setting-config-maps-from-the-cli/).

Just like ConfigMaps we can provide secret data to pods as ENV variables or as a volume. We will use the `secret/test-secret-literal` and our `demoapp` as an example, you can create a demo app with:

```bash
$ oc new-app --name demoapp --docker-image bitnami/nginx
--> Creating resources ...
    imagestream.image.openshift.io "demoapp" created
    deployment.apps "demoapp" created
    service "demoapp" created
```

### Secret as ENV Variable
We use the `oc set env` command to set a secret as ENV Vars for our pod:
```bash
$ oc set env deployment/demoapp \
--from secret/test-secret-literal
deployment.extensions/demoapp updated 
```

We can now see the secret in action when we enter the pod:
```bash
$ oc get pods
NAME                       READY   STATUS    RESTARTS   AGE
demoapp-557f47dccb-2gzvp   1/1     Running   0          41s
$ oc rsh demoapp-557f47dccb-2gzvp bash
(pod) $ env | grep -i password
PASSWORD=encoded_secret
```

### Using a prefix
You can also prefix your secrets in the pod using the `--prefix` option. This will add a prefix like `mysql_` or `our_prefix_` to all the key's that are being added. Tis can be quite handy for example when you want to use the `mysql` app because that app expects all values to be prefixed. 

As an example, we will create a secret from-literal and then add it with a prefix:
```bash
$ oc create secret generic prefix-example \
  --from-literal key=a_value
secret/prefix-example created
```

And then we add it with the `--prefix` option:
```bash
$ oc set env deployment/demoapp \
  --from secret/prefix-example \
  --prefix our_prefix_ \
deployment.extensions/demoapp updated
```

Now, when we enter the pod and echo the ENV variables we will see the `key` being prefixed:
```bash
$ oc get pods -o name
pod/demoapp-6c9b77c-dw4c2
$ oc rsh pod/demoapp-6c9b77c-dw4c2 bash
(pod) $ env | grep -i 'key'
our_prefix_KEY=a_value
```

### Secret as file
<div markdown="span" class="alert alert-danger" role="alert"><i class="fa fa-exclamation-circle"></i> <b>Warning:</b> Mounting a volume on path that already exists on a pod  will make all files in that directory inaccessible
</div>

To create a volume that will hold the secret we use the `oc set volume` command:
```bash
$ oc set volume \
  deployment/demoapp \
  --add \
  --type secret \
  --mount-path /etc/secret \
  --secret-name test-secret-literal
info: Generated volume name: volume-zjmsq
deployment.extensions/demoapp volume updated 
```

We can see the mounted secret action from the pod in actions as well:

```bash
$ rsh demoapp-557f47dccb-2gzvp bash
(pod) $ cat /etc/secret/password
encoded_secret
```

As you can see a file is created with the name of the `key` containing the value of the `value` from our secret.

# Closing thoughts
Secrets might not be as secret as you would hope but they provide a fundamental way of exposing sensitive data to our pods on OpenShift and you will certainly encounter them on a daily basis if you manage any kind of Container Cluster.

See more of my [EX280 and OpenShift related posts here ](https://blog.benstein.nl/ex280/)