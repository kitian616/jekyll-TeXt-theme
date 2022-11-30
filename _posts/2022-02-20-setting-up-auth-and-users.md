---
title:  "Manage users and policies, groups and permissions"
date: 2022-02-20
toc: true
mermaid: true
categories: [EX280]
tags: [openshift, kubernetes, ex280] # TAG names should always be lowercase
---

This blog will cover the ["Manage users and policies"](https://www.redhat.com/en/services/training/ex280-red-hat-certified-specialist-in-openshift-administration-exam?section=Objectives) objective of the [EX280]() exam from RedHat. In this post we will:

- Configure the HTPasswd identity provider for authentication
- Create and delete users
- Modify user passwords
- Modify user and group permissions
- Create and manage groups

Will also sprinkle in a little bit from "Manage users and policies" with:

- Define role-based access controls
- Apply permissions to users

The most easy way to try OpenShift is by using CRC. In this case this gives as an advantage because HTPasswd is already set up. We will however be configuring it from the ground up using the example provided by the OpenShift documentation [^ConfiguringHTPasswd]. 

[^ConfiguringHTPasswd]: https://docs.openshift.com/container-platform/4.6/authentication/identity_providers/configuring-htpasswd-identity-provider.html

# Setting up HTPasswd Authentication
One of the simplest way to authenticate users to a cluster is by using the HTPasswd provider. This is based on a text based secret containing a `username:password` key-pair. It's easy to set up and understand.

## Creating the HTPasswd file
Creating a new HTPasswd file can be done in a single command from the command-line:

```bash
$ htpasswd -c -B -b FILENAME USERNAME PASSWORD
```

Let create our first user with username admin and password ... dunder:

```bash
$ htpasswd -c -B -b htpasswd-file Michael dunder
Adding password for user Michael
```

### Adding users
After creating the `htpasswd` file we can drop the `-c` (create) flag and add some more users:

```bash
$ htpasswd -B -b htpasswd-file Pam secret01
  Adding password for user Pam
$ htpasswd -B -b htpasswd-file Dwight secret02
$ htpasswd -B -b htpasswd-file Jim secret03
```
<div markdown="span" class="alert alert-danger" role="alert"><i class="fa fa-exclamation-circle"></i> <b>Important:</b> Usernames and passwords are case sensitive</div>

And let's have a look at our file:

```bash
$ cat htpasswd-file
Michael:$2y$05$f2SD9CoUnLzqkA.AgTVToOb6fSKhmN.5xwHqq/Cz/zUZ4ZSXqsyze
Pam:$2y$05$fRR.5EmSaDGd1rMtAygWxexpuiMGnZJOgk0Oo.kuocyKtKoin5Z0e
Dwight:$2y$05$EKp1MLW/anaJ1R2wKpAkju6oPZvxV47tTtTq8KKp7x.cjFeOTke5u
Jim:$2y$05$k1Nlh3ZhqY8E6wK.v1exfOuieLkmZT2MRwFFxVuYZ8KQZ3xcLHWg.
```

### Applying secret to the cluster
In order to use our `htpasswd` file we need to make it available in the cluster as a secret. [I wrote about creating secrets before here](https://blog.benstein.nl/posts/create-and-use-secrets-openshift/). 

We will create a secret called `htpasswd-source` in the cluster with the content of our `htpasswd` file. This has to be done in the project `openshift-config`:

```bash
$ oc create secret generic htpasswd-source \
  --from-file htpasswd=htpasswd-file \
  --namespace openshift-config
secret/htpasswd-source created
```

## Setting up the identity provider
Now that we have the secret in place we can create our identity provider. We will do this with the template thats available from the OpenShift Documentation [^ConfiguringHTPasswd] :

```yaml
apiVersion: config.openshift.io/v1
kind: OAuth
metadata:
  name: cluster
spec:
  identityProviders:
  - name: custom_htpasswd_provider 
    mappingMethod: claim 
    type: HTPasswd
    htpasswd:
      fileData:
        name: htpasswd-source
```

By applying this `yaml` to the cluster we will:
- Create a OAuth Identity provider called `custom_htpasswd_provider`
- With the source secret `htpasswd-source`

```bash
$ oc apply -f custom_htpasswd_provider.yaml \
  --namespace openshift-config
oauth.config.openshift.io/cluster configured
```

It might take a while but after some syncing in you cluster your new auth provider should be online. You can check this with `oc get oauth`:

```bash
$ oc get oauth -o yaml
apiVersion: v1
items:
- apiVersion: config.openshift.io/v1
....
  spec:
    identityProviders:
    - htpasswd:
        fileData:
          name: htpasswd-source
      mappingMethod: claim
      name: custom_htpasswd_provider
      type: HTPasswd
....
```

## Viewing users and logging in
After getting our new authentication provider up and running we can test it by logging in with one of our new users. But first, lets take a look at the users we have:

```bash
$ oc get users
NAME        UID                                    FULL NAME   IDENTITIES
developer   623cd251-b25b-44b5-a00e-67f311029588               developer:developer
kubeadmin   06238f0b-4f58-45f2-9b61-94b482bb4b74               developer:kubeadmin 
```

As you can see our new users are not yet listed. This is because they will be created after they log in. For example:

```bash
$ oc login -u Pam https://api.crc.testing:6443
Authentication required for https://api.crc.testing:6443 (openshift)
Username: Pam
Password:
Login successful.
```

Switch back to a user with admin rights and check te users again:
```bash
$ oc login -u kubeadmin
Logged into "https://api.crc.testing:6443" as "kubeadmin" using existing credentials.

$ oc get users
NAME        UID                                    FULL NAME   IDENTITIES
Pam         cfe773a0-e0e2-40e1-bb5f-799db1ceaeb7               custom_htpasswd_provider:Pam
developer   623cd251-b25b-44b5-a00e-67f311029588               developer:developer
kubeadmin   06238f0b-4f58-45f2-9b61-94b482bb4b74               developer:kubeadmin
```

We can see that Pam was created and that her identity is provided by `custom_htpasswd_provider:Pam`. At this point we can see that everything works. I would however advise you to test all users that you have created. This will prevent errors from showing when we want to add users to a group.

# Creating groups
Creating groups is actually quite strait forward with the `oc adm groups` command. First off, lets create a group called managers:

```bash
$ oc adm groups new managers
group.user.openshift.io/managers created
```

And let's add Micheal to that group:

```bash
$ oc adm groups add-users managers Michael
group.user.openshift.io/managers added: "Michael"
```

```bash
$ oc get groups
NAME       USERS
managers   Michael 
```

It's that easy. We can also create a group and add users at the same time:

```bash
$ oc adm groups new sales Jim Dwight 
```

```bash
$ oc adm groups new sales Jim Dwight
group.user.openshift.io/sales created 
$ oc adm groups new reception Pam
```

```bash
oc get groups
NAME        USERS
managers    Michael
reception   Pam
sales       Jim, Dwight
```

It's that easy!

# Assigning Permissions
Now it's time to assign some permissions. We do this by using the `oc adm policy` command. By default a user can create projects and manage objects in that project. We can assign permissions to a user or a group. We are going to give Michael full permissions to the cluster by giving him `cluster-admin` cluster role:

```bash
oc adm policy add-cluster-role-to-user cluster-admin Michael
clusterrole.rbac.authorization.k8s.io/cluster-admin added: "Michael"
```

Now we will create a project as Micheal and give the sales group permissions to it:

```bash
$ oc new-project sales
$ oc adm policy add-role-to-group edit sales \
  --namespace sales
clusterrole.rbac.authorization.k8s.io/edit added: "sales"
```

Take note of the `--namespace` flag in this command. This will bind the permissions to the namespace sales.

We also want to give the reception groups view permissions to the namespace sales:

```bash
$ oc adm policy add-role-to-group view reception --namespace sales
clusterrole.rbac.authorization.k8s.io/view added: "reception"
```

To view all bound permissions to a namespace we can use:

```bash
$ oc describe rolebinding.rbac -n sale 
```

For example:

```bash
$ oc describe rolebinding.rbac -n sales | grep reception -B 9
Name:         view
Labels:       <none>
Annotations:  <none>
Role:
  Kind:  ClusterRole
  Name:  view
Subjects:
  Kind   Name       Namespace
  ----   ----       ---------
  Group  reception
```

For a full list of roles and permissions check out the [RedHat OpenShift documentation on Roles](https://docs.openshift.com/container-platform/4.6/authentication/using-rbac.html).

## Optional: Removing the default kubeadmin
A great best practice is to remove the default kubeadmin user. However, we should only do this after assigning the cluster-role `cluster-admin` to a user that we have testes. To make sure of this let's check the binding:

```bash
$ oc describe rolebinding.rbac | grep ClusterRole -A 5
  Kind:  ClusterRole
  Name:  admin
Subjects:
  Kind  Name     Namespace
  ----  ----     ---------
  User  Michael
....
```

Check! Now we can delete the default kubeadmin user [^DeletingTheKubeadminUser] :

[^DeletingTheKubeadminUser]: https://docs.openshift.com/container-platform/4.6/authentication/remove-kubeadmin.html#removing-kubeadmin_removing-kubeadmin

```bash
$ oc delete secrets kubeadmin -n kube-system
secret "kubeadmin" deleted
```

# Wrapping up
Understanding how user creation, authentication and permissions works is a basic principle of working with cluster. Is is however often overlooked because there are so many pre configured ways out there to get this working (like integration with Azure or a direct LDPA sync). It's important have a firm understanding of Users, Groups and Permissions in order to be proficient OpenShift Admin.

I hope this post has helped you. Check out my other EX280 related content on my [EX280 page](https://blog.benstein.nl/ex280/)