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

```css
.tag[href="#Evergreen/Seedling"] {
  font-size: 0;
  padding: 0;
}
.tag[href="#Evergreen/Seedling"]:after {
  font-size: 8pt;
  font-weight: 700;
  background-color: rgb(165, 29, 42);
  color: rgb(232, 230, 227);
  padding: 3px 3px;
  border-radius: 3px;
  letter-spacing: -0.00ch;
  content: "🌱 SEEDLING";
}

.tag[href="#Evergreen/Sapling"] {
  font-size: 0;
  padding: 0;
}
.tag[href="#Evergreen/Sapling"]:after {
  font-size: 8pt;
  font-weight: 700;
  background-color: rgb(200, 150, 0);
  color: rgb(232, 230, 227);
  padding: 3px 3px;
  border-radius: 3px;
  content: "🪵 SAPLING";
}

.tag[href="#Evergreen/Evergreen"] {
  font-size: 0;
  padding: 0;
}
.tag[href="#Evergreen/Evergreen"]:after {
  font-size: 8pt;
  font-weight: 700;
  background-color: rgb(32, 134, 55);
  color: rgb(232, 230, 227);
  padding: 3px 3px;
  border-radius: 3px;
  content: "🌲 EVERGREEN";
}
```

Get a copy at [GitHub](https://gist.github.com/KingOfSpades/a7e38c6ebe8c291a559fbdc1418ef053) 

This will map the CSS to the tag's and will create the following output:
![Example Tags](/assets/images/20210421193541.png)

Feel free to play around with your own CSS hacks! Obsidian.md is a great tool that's easily customized.
