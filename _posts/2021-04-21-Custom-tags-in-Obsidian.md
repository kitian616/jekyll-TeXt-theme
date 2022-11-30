---
title: How to customize the appearance of tags in Obsidian
toc: true
mermaid: true
categories: [Obsidian]
tags:
  - customization
---

In this note I wil show and tell how to change the appearance of certain Tag's based on the content of the Tag using CSS

# Customizing

Now you can create custom tag's that match on you existing tag's. I'm using a simple [evergreen](https://notes.andymatuschak.org/Evergreen_notes) system to show the status of my notes. In this instance I'm going to map the following tag's:

- `#Evergreen/Seedling` → `🌱 SEEDLING`
- `#Evergreen/Sapling` → `🪵 SAPLING`
- `#Evergreen/Evergreen` → `🌲 EVERGREEN`

I'm also going to change the background color of the tag, just to let it stand out a bit more.

# Creating a custom `css` file

You can easily add custom CSS to Obsidian using CSS Snippets. These are located in your vault located in the `vault/.obsidian/snippets/`. Create a custom file (for example `customTags.css`) and enable the snippet in Obsidian settings. See the official documentation over at <https://help.obsidian.md/Advanced+topics/Customizing+CSS> .

# Creating custom tag's

Using the following CSS code we can easily create the custom Tag's:

<script src="https://gist.github.com/KingOfSpades/a7e38c6ebe8c291a559fbdc1418ef053.js"></script>

This will map the CSS to the tag's and will create the following output:
![Example Tags](/assets/images/20210421193541.png)

Feel free to play around with your own CSS hacks! Obsidian.md is a great tool that's easily customized.

Found this help-full? Checkout my [GitHub](https://github.com/KingOfSpades) or buy me a Coffee over at [BuyMeACoffee](https://www.buymeacoffee.com/cabenstein)

[<img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="BuyMeACoffee" width="120">](https://www.buymeacoffee.com/cabenstein)