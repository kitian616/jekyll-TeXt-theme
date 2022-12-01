---
title: "Primer on YAML"
toc: true
mermaid: true
categories: [Commandline]
tags:
  - cli
  - yaml
---

It's always good to write down the basics, rehearse the syntax and go trough the motions. So let's do that with YAML.

YAML is a data serialisation language. It's used to store and display information. I always think of it as JSON 2.0 . You can create `value pairs` in YAML like:

- `lists`
- `values`
- `objects`

YAML files are identfied by the extenion `.yml` and `.yaml` . You can use `#` for comments and you want to start of with a `---` on top. YAML is used by a lot of software like Ansible.

# Creating object's in YAML

Let's start with an example or a car. We are going to create an object called `myCar` and fill it up with some information:

```yaml
myCar:
  name: "My cool car"
  function: "driving"
  wheels: 4
  gallonPerMile: 7.6
  apk: true
  buildDate: 2019-06-04 14:33:22
  broken: null
```

No we created a car object `myCar` . All child items that are indented (meaning they are 2 spaces to the right under `myCar` ) are part of the `myCar` object. You can use a lot of different datatype in YAML. In the `myCar` object we are using:

- `STRING` —> `name`
- `INT` —> `wheels`
- `BOOL` —> `APK`
- `DATE` —> `buildDate`
- `FLOAT` —> `gallonPerMile`

# Making a list, checking it once

Using YAML we can also use `lists`. Image we have 3 drivers that are registered to `myCar`. We can create a list of `drivers` using the following notations:

Driver list using indent:

```yaml
drivers:
  - Bob
  - Frank
  - Margret
```

We can also create a list on a single line using:

```yaml
drivers: ["Bob", "Frank", "Margret"]
```

You can even go one step further and an `object` in to a `list` using:

```yaml
drivers:
  - name: "Bob"
    age: 23
    canDrive: true
  - name: "Frank"
    age: 41
    canDrive: false
  - name: "Margret"
    age: 33
    canDrive: true
```

This can also be done using inline style:

```yaml
drivers:
  - {name: "Bob", age: 23, canDrive: true}
```

# Text rendering

YAML has been built to be readable and to take up a small space on you screen. This is primarily done so you can have multiple files open next to each-other (especially handy when using git and comparing different versions). To this extend there are few cool functions build in to YAML, like:

## Rendering text to a single line

Example. What if one of our drivers has a complicated backstory. One of lipsum's and lorems. In YAML you can break up this blok to make it more readable for you (human):

```yaml
- name: "Margret"
    age: 33
    canDrive: true
    backStory:
      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed 
      do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
      Ut enim ad minim veniam, quis nostrud exercitation ullamco 
      laboris nisi ut aliquip ex ea commodo consequat. Duis aute 
      irure dolor in reprehenderit in voluptate velit esse cillum 
      dolore eu fugiat nulla pariatur.
```

Now, for some reason you want to render this textblok to be displayed on one line? No problem, ad a `>` after `backStory` . Example:

```yaml
- name: "Margret"
    age: 33
    canDrive: true
    backStory: >
      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed 
      do eiusmod tempor incididunt ut labore et dolore magna aliqua.
```

Now, on rendering, this will be displayed as one long line.

## Rendering text while preserving formatting

Almost the opposite of the single line option, using `|` after `backStory` will render the text including all formatting (newlines and tabs included). Example:
```yaml
- name: "Margret"
    age: 33
    canDrive: true
    backStory: |
      Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed 
      do eiusmod tempor incididunt
            ut labore et 
                dolore magna 
                            aliqua. 
```

Will print out the example above with all the crazy spacing

# Anchors

You can use anchors `&` to recall information later. For example, we started giving Margret the age of 33. Maybe we want to reuse that information later. We can do so by using an anchor like:

```yaml
- name: "Margret"
    age: &age 33
    canDrive: true
```

Now we are going to calculate how long she had her driving licence. We going to add the age she got it and then calculate it:

```yaml
- name: "Margret"
    age: &age 33
    obtainedLicenceAt: &gotLicenceAt 19
    allowedToDriveSinceYears: *age - *gotLicenceAt
    canDrive: true
```

You can also anchor an entire object. So, if you have a car and want 2 cars of a different make you can do:
```yaml
baseCar: &base
  wheels: 4
  needsDriver: true

hondaCar:
  <<: *base
  manufacturer: "Honda"

teslaCar:
  <<: *base
  manufacturer: "Tesla"
  type: "⚡️"
```

This creates a base car with a few values. Then we use `<<: *base` to anchor it back to `baseCar`. You can also add extra values after that.

# Converting datatypes

YAML will do it's best to identify datatypes based on your input. If your don't want it to to that you can force a date type like:

```yaml
myCar:
  wheels: !!float 4
  gallonPerMile: !!str 7.6
```

Using these additions will force the `wheels` to be seen as a `float` (as `4.0`) and the `gallonPerMile` to be seen as a `string`. More conversion options over at: [https://github.com/yaml/YAML2/wiki/Type-casting](https://github.com/yaml/YAML2/wiki/Type-casting)