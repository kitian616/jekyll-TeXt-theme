---
title:  "Bye bye Caps Lock - Re-mapping Caps to CTRL"
categories: [Windows]
toc: true
mermaid: true
tags:
  - customization
---

HOW OFTEN DO YOU USE CAPS LOCK? Not a lot right. So let's re-map that key. In my setup I like to use the Caps Lock as 2nd CTRL key. This way I don't have to fold up my pinky to use that ow so important CTRL+C & CTRL+V.

That being said and done. Let's get going. Changing the mapping requires you to edit some REG's. Using the following lines this will be done automatically:

~~~~ powershell
$hexified = "00,00,00,00,00,00,00,00,02,00,00,00,1d,00,3a,00,00,00,00,00".Split(',') | % { "0x$_"};

$kbLayout = 'HKLM:\System\CurrentControlSet\Control\Keyboard Layout';

New-ItemProperty -Path $kbLayout -Name "Scancode Map" -PropertyType Binary -Value ([byte[]]$hexified);
~~~~

After that a quick reboot loads up the new defaults.

And hey, if you still need some text in ALL CAPS. Use [Shoutcloud](http://shoutcloud.io/).

