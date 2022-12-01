---
title:  "Switch iTerm profile when running Vim"
date: 2022-02-08
toc: true
mermaid: true
categories: [Commandline]
tags: [vim, iterm2, macos] # TAG names should always be lowercase
---

I love using [iTerm2](https://iterm2.com) and Vim on my Mac. Today we are going to have a look at Profile Switching feature that iTerm2 has and how we can combine this with Vim. 
Recently [I wrote about using `vim-plug` to easily install vim plugins](https://blog.benstein.nl/posts/installing-plug-vim-on-macos/). Using `vim-plug` I installed the theme "onehalfdark" ([Theme link](https://github.com/sonph/onehalf)):

```yaml
# .vimrc
"# Custom VIM File for CABenstein
"## Loading the plugins using vim-plug
call plug#begin()
Plug 'tpope/vim-sensible'
Plug 'sonph/onehalf', { 'rtp': 'vim' }
Plug 'itchyny/lightline.vim'
call plug#end()

"## Theme
colorscheme onehalfdark
let g:airline_theme='onehalfdark'
```

Now, this is a great basis theme but it uses a custom background color which does not match the color of my iTerm using "Solarized":

![Vim with blue border](/assets/images/iterm-vim-20220208171856.png)

Notice the blue bar around the edges? Let's take care of this.

# Automatic profile switching
iTerm2 has some amazing features. If you're running a Mac check it out! I will use the the automatic switching that is enabled by installing the shell integrations. Read the docs over at https://iterm2.com/documentation-automatic-profile-switching.html .

## Creating a profile
I'm gonna create a custom profile called "VIM" and set the background color to the same color used by onehalfdark. That's `48,48,48`:

![Create iTerm profile](/assets/images/iterm-vim-20220208172444.png)

## Auto switching
Now, remember, for this part the shell integrations will need to be installed. We will add a auto-switch condition on the *Advanced* tab. Hint: These settings seem to be applied when you closed the preferences screen. So, if you're testing keep this in mind.

To switch create the trigger `&Vim` (the capital is important!!!):
![Setting the switch trigger](/assets/images/iterm-vim-20220208172740.png)

Now, when you enter Vim you should see the switching taking place:

 ![Automatic switching](/assets/images/iterm-vim-20220208172839.png)

 And that's it! To be honest I googled my way up and down because I was not able to get this to work! The issue in the end was that I was using the trigger `&vim` instead of `&Vim`. I really hope someone out there that runs in to the same issue will find this post!