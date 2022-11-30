---
title: Using Alfred and qlmarkdown to Quicklook .md files in macOS
toc: true
mermaid: true
categories: [Productivity] 
tags:
  - macos
  - alfred
---
I have been using [Alfred](https://www.alfredapp.com/) more and more during the past months and I've been looking for a quick way to view little snippets of information with it. I use a few 'cheat sheets' for commands and application I use where I'm not always sure of the right command or syntax.

# Cheat sheets
I have been creating very simple `.md` markdown sheets for a few command's I use a lot. We will use the Atlassin text syntax as an example. I created the following markdown file:
```markdown
- `*strong*`	→ 	Makes text strong.
- `_emphasis_`	→ 	Makes text emphasis..
- `??citation??`	→	Makes text in citation.
- `-deleted-`	→ 	Makes text as deleted.
- `+inserted+`	→ 	Makes text as inserted.
- `^superscript^`	→ 	Makes text in superscript.
- `~subscript~`	→ 	Makes text in subscript.
- `{{monospaced}}` →	Makes text as monospaced.

Putting text in red
{color:red}
    look ma, red text!
{color}
```

# Previewing `.md` files on macOS
To preview a markdown file on macOS with rendering you'll need to install [qlmarkdown](https://github.com/toland/qlmarkdown). When installed this will enable the build in macOS Quicklook to render markdown files in the preview window.

# Calling Cheat Sheets using Alfred
Using an Alfred 'File Filter' + 'Run Script' action you can easily list files in a directory. The Run Script action will then perform the command-line version of Quicklook on the file using the following command:
```bash
qlmanage -p $query 
```

Setting up the workflow in Alfred will look like this:

1. The Workflow in Alfred\
![Alfred Workflow](/assets/images/20201127162256.png)

2. Settings for the File Filter, drag the folder you want to look in into the option `scope`\
![File Filter](/assets/images/20201127162314.png)

3. Settings for the Run Script\
![Run Script Settings](/assets/images/20201127162335.png)

# Putting it all together
Once this is all set-up you can easily create simple markdown cheat sheets and call them using Alfred\
![](/assets/images/CheatExample.gif)