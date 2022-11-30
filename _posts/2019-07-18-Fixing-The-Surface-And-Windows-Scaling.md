---
title: "Fixing Windows scaling issues on the Surface Pro"
toc: true
mermaid: true
categories: [Windows]
tags:
  - customization
---

We all know that scaling on Windows has ... some issues. If you use a Surface Device and Surface dock's a lot you'll run in to countless situations where you need the famous `CRTL+WIN+ALT+B` shortcut [thank me later](https://superuser.com/questions/1127463/what-does-ctrlwinshiftb-do-in-windows).

So, the biggest problem comes when you're using screens that are scaled differently. for example, if screen 1 is at 125% and screen 2 is at 200% windows will have to try it's best to keep them coherent. a way around this is of course to set all the screens to the same scaling. Wich in turn will A: make everything tiny, or B wont make the best use of your nice Surface device ...

Here is a small guide for the later
1. Install CRU tool to add a custom resloution to your Surface display [link](https://www.monitortests.com/forum/Thread-Custom-Resolution-Utility-CRU)

2. Add said resolution, in my case it is `1440x960`

![CRU](https://blog.benstein.nl/assets/images/CRU-Settings.png)

3. REBOOT

4. Select the custom resolution and set scaling to 100%

![SETTINGS](https://blog.benstein.nl/assets/images/Display-Settings.png)

Small side note, Windows will somethimes 'resset' your resoloution when connection to new displays.