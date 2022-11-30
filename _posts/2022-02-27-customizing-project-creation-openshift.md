---
title:  "Configuring default Project creation in Openshift"
date: 2022-02-27
toc: true
mermaid: true
categories: [EX280]
tags: [openshift, kubernetes, ex280] # TAG names should always be lowercase
---

In this blog we will have a look at  "Configuring project creation" in an Openshift cluster. We will:
- Create a Project Template
- Add resources like a limit-range to the template
- Disable project self-provisioning

As always. We will be doing all the examples in a CRC ([Code Ready Containers](https://developers.redhat.com/products/codeready-containers/overview)) environment.

# Project template
When creating a new project in Openshift (a namespace) the API query's the default template that is in use by the cluster. We can however change this to better suit our needs.
By creating a project template and adding resources to it we can setup new Projects to be in line with our workflow and we can apply limits on creation.

# Creating a template
Templates are stored in the namespace `openshift-config` as `template.template.openshift.io` objects. To create a new template we can use the special `oc adm` command:

```bash
$ oc adm create-bootstrap-project-template -o yaml > our-template.yaml
```

This template will include some basic things like:
- Creating a `admin` role-binding to the user that creates the project
- Setting up parameters like `PROJECT_REQUESTING_USER`

## Customizing our template
Adding custom object is done by adding to the `-objects` array. Here we can add object's like `LimitRange`, `Quota` and other Openshift resources.

> **Tip:** When adding to the template validate the object's first by creating them. Otherwise you might get syntax error's when creating new projects 
{: .prompt-tip }

Let's change a few things in our template:

```yaml
# our-template.yam
apiVersion: template.openshift.io/v1
kind: Template
metadata:
  creationTimestamp: null
  name: our-template
objects:
- apiVersion: v1
  kind: LimitRange
  metadata:
    name: "${PROJECT_NAME}-resource-limits"
  spec:
    limits:
      - type: Container
        default:
          cpu: 50m
- apiVersion: project.openshift.io/v1
  kind: Project
  metadata:
    annotations:
      openshift.io/description: ${PROJECT_DESCRIPTION}
      openshift.io/display-name: ${PROJECT_DISPLAYNAME}
      openshift.io/requester: ${PROJECT_REQUESTING_USER}
    creationTimestamp: null
    name: ${PROJECT_NAME}-from-template
  spec: {}
  status: {}
parameters:
- name: PROJECT_NAME
- name: PROJECT_DISPLAYNAME
- name: PROJECT_DESCRIPTION
- name: PROJECT_ADMIN_USER
- name: PROJECT_REQUESTING_USER
```

In this `yaml` we have:
- added to the project name `-from-template`. Every new project that is created will now be called PROJECT-from-template
- Added a LimitRange with the name `${PROJECT_NAME}-resource-limits` to all new projects that sets a default cpu limit of `50m`
- Removed the default admin role binding

## Applying our custom template
Remember to create the template in `openshift-config`:

```bash
$ oc apply -f our-template.yaml -n openshift-config
template.template.openshift.io/our-template created 
```

To make this template the default we need to add it at the end of `project.config.openshift.io/cluster`:

```bash
$ oc edit project.config.openshift.io/cluster 
```

Change the last line from:
```yaml
spec: {}
```

To:

```yaml
spec:
  projectRequestTemplate:
    name: our-template
```

Now we can check out our template:

```bash
$ oc get templates.template.openshift.io -n openshift-config
NAME           DESCRIPTION   PARAMETERS    OBJECTS
our-template                 5 (5 blank)   2
```

No we need to wait a bit for the cluster to pick up the change. You can monitor this by checking the api pods:

```bash
$ oc get pods -n openshift-apiserver
AME                        READY   STATUS        RESTARTS   AGE
apiserver-b47db7bc4-x79sm   0/2     Pending       0          41s
apiserver-ccc6bf7b5-gbbq2   2/2     Terminating   0          125m
```

Once the new pods are up and running we can test out our new template.

## Creating a new project with our template
Ok check, new config is online? Let's create a project:

```bash
$ oc new-project template-test-project
Now using project "template-test-project-from-template" on server "https://api.crc.testing:6443".
```

```bash
$ oc get limitrange
NAME                                    CREATED AT
template-test-project-resource-limits   2022-02-27T19:38:12Z
```

# Disabling project self-provisioning
Letting users create new projects is a main principle of the DevOps setup of any cluster. There might however be situations where you don't want users to create their own projects. You could enforce project creation with a GitOps pipeline and ensure that no rouge projects are created from the CLI or web-interface.

## Patching the self-provisioner role
By default all authenticated uses are able to create new projects. To disable this we can patch this binding with:

```bash
$ oc patch clusterrolebinding.rbac self-provisioners -p '{"subjects": null}'
```

After this, users can no longer create projects:
```bash
$ oc new-project test-project
Error from server (Forbidden): You may not request a new project via this API.
```

> **Auto update:** This patching will work until the cluster is updated. To make this permanent follow the instructions in the [RedHat Openshift documentation](https://docs.openshift.com/container-platform/4.6/applications/projects/configuring-project-creation.html)
{: .prompt-tip }

## Creating a provisioning role
In certain scenarios you might still want some users to create projects. All users with the clusterolebinding `cluster-admin` can still create projects. For users with less privileges we will create a group `ProjectCreators`:

```bash
$ oc adm groups new ProjectCreators
$ oc adm policy add-cluster-role-to-group self-provisioner ProjectCreators
$ oc adm groups add-users ProjectCreators Jim
```

Now all members of the group can create projects.

# Wrapping up
This was a really simple demo of changing the default project template to something that fits our needs better.

I hope this post has helped you. Check out my other EX280 related content on my [EX280 page](https://blog.benstein.nl/ex280/)