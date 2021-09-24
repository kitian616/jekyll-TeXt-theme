# SDA Web Portal

The repository contains the codebase for the Swedish Sensitive Data Archive web portal. It provides information about the submission and retrieval of a dataset stored in the Swedish SDA.

## Jekill Theme

The portal is using the [TeXt-theme](https://github.com/kitian616/jekyll-TeXt-theme) template. Documentation about the template and different types of pages available can be found [here](https://tianqi.name/jekyll-TeXt-theme/docs/en/quick-start). Currently, all the page are created with `layout: article`. The navigation bar is configured in the file `_data/navigation.yml`.

## Publishing changes

According to the current setup, the code in the master branch is published. To change the web portal, edit and/or add a new `.md` file in the root folder and push the changes. Merging the changes to the master branch enables the action that updates the site.

