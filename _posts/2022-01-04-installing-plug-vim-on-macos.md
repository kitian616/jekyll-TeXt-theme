---
title:  "Installing vim-plug on macOS"
toc: true
mermaid: true
categories: [Commandline]
tags: [vim, macos]
---

`vim-plug` is a VIM plug-in manager to install themes and other plug-ins in VIM. It's the most populair one at the moment.

# Installation
To install `vim-plug` you can go over to https://github.com/junegunn/vim-plug and run the following command:
```bash
$ curl -fLo ~/.vim/autoload/plug.vim --create-dirs \
    https://raw.githubusercontent.com/junegunn/vim-plug/master/plug.vim 
```

This will create a autoload directory and place `plug.vim` in it.

# Configuration
To enable the loading of plug-ins you need to add the loading code to your `vimrc`. If you already have one you can add it with:
```bash
$ vim ~/.vimrc 
```

Add:
```bash
# vimrc
call plug#begin()
Plug 'tpope/vim-sensible'
call plug#end()
```

# Installing a theme
I installed `vim-plug` to easily add a theme to my vim setup. I chose the theme https://github.com/sonph/onehalf/tree/master/vim

You can install the theme by adding the plug to you `vimrc`
```bash
# vimrc
call plug#begin()
Plug 'tpope/vim-sensible'
Plug 'sonph/onehalf', { 'rtp': 'vim' }
call plug#end()

colorscheme onehalfdark
let g:airline_theme='onehalfdark'
```

**Info**: When using and installing new plug's you might need to update plug from vim commando mode with: `:PlugUpdate`
{:.info}
