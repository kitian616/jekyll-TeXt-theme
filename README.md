# SDA Web Portal

The repository contains the codebase for the Swedish Sensitive Data Archive web portal. It provides information about the submission and retrieval of a dataset stored in the Swedish SDA.

## Jekyll Theme

The portal is using the [TeXt-theme](https://github.com/kitian616/jekyll-TeXt-theme) template. Documentation about the template and the different types of available pages can be found [here](https://tianqi.name/jekyll-TeXt-theme/docs/en/quick-start). Currently, all the page are created with `layout: article`. The navigation bar is configured in the file `_data/navigation.yml`.

## Publishing changes

Currently the page is published from the `master` branch. To make changes, edit and/or add a new `.md` file in the root folder and push the changes. Once the changes are merged to the master branch, the page will update. Note that the actual update may take a few minutes as it needs to wait for the github to render the new changes.


# Executing SDA Web Locally

This guide explains how to execute the SDA web locally in you system.

## Prerequisites

In order to able to deploy the web locally, you need to have [jekyll](https://jekyllrb.com/docs/) installed in your machine. 

## Jekyll Execution

First, we start the rbenv in your shell. 

```bash
rbenv init
```

Install the bundler gems

```bash
gem install jekyll bundler
```

Build the site and make it available on a local server

```bash
bundle exec jekyll serve
```

Browse to http://localhost:4000 to see the SDA Web portal.