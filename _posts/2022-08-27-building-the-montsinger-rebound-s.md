---
title:  "Build-log: Montsinger Rebound-S"
date: 2022-08-27
toc: true
mermaid: true
categories: [Mechanical Keyboards]
tags:
  - zmk
  - keyboard
  - nice!nano
---

[The Rebound-S by Montsinger](https://store.montsinger.net/products/rebound-s) is a 60% case compatible ~40% keyboard ortholinear keyboard with an ergonomically-friendly 7° typing angle. It differs from the [Rebound](https://store.montsinger.net/products/rebound) because it has a  staggered contour. The Rebound's have some great customisation options like spot for a dedicated for an EC12 encoder and some extra keys located between the two halves of the keyboard. The PCB also allows you to build the keyboard with a choice of switches (Choc or MX style) and microcontrollers letting you choose which controller to use.
For my build I went with the [nice!nano](https://nicekeyboards.com/nice-nano/)for wireless support. I also decided not to install all keys (because I don't use them all) and to use M2 screws and bolts to get a very low profile keyboard.
This is more of a build log than a complete guide. You can use it to check if you'd like to build your own keyboard but it's by no means complete. The official buildguide is over at http://docs.montsinger.net

# Parts-list

To build this keyboard you will need the following, from the Montsinger website you will need:

- Montsinger Rebound S PCB
- Montsinger Rebound S Plate
- Montsinger Small adapter board

You will need to provide the following parts yourself:

- A nice!nano controller
- A small 100mah battery (or bigger)
- Choc or MX switches (builders choice, the Rebound supports both)
- Keycaps of choice

Optional parts:

- A EC12 encoder and knob
- Mill Max Low Profile Sockets, highly recommended, to make the nice!nano removable 

# Installing the nice!nano

I decided to start by installing the controller because this keyboard does not have native hot swappable switch support. By installing the controller first I could test the PCB first before adding switches save myself a lot of troubleshooting after installing the switches. Because I opted to use a nice!nano I had to use the Small adapter bord (provided by Montsinger) to install my controller on to the keyboard.
Many instructions have been written on how to socket your controller, so I'm just going to refer to the [How do I socket a microcontroller?](https://docs.splitkb.com/hc/en-us/articles/360011263059) by splitkb.com.

Setting up the pins on the sockets on the adapter board:

![](/assets/images/montsinger/20220901214744.jpg)
_Preparing the socket pins on the milmax_

Preparing the nice!nano for soldering. I put some isolation tape in between the nice!nano and the sockets to prevent the solder from leaking in to the socket. This way, the controller is easily removable. A lot of people on Discord let me know they don't do this and never had any issues, but the last two controllers I socketed did not want to come off after, so better safer than sorrow.

![](/assets/images/montsinger/20220901214243.jpg)
_Adding a piece of electrical tape prevents the solder from seeping trough_

Nice and soldered:

![](/assets/images/montsinger/20220901214446.jpg)

And un socketed:

![](/assets/images/montsinger/20220901214513.jpg)

Then just solder the sockets to the adapter board, put it on the PCB and connect the rest:

![](/assets/images/montsinger/20220901214919.jpg)
_The Nice!Nano on the Montsinger Adapter_

# Installing switches

I started by getting the EC12 in place that I wanted to use:

![](/assets/images/montsinger/20220901214956.jpg)
_The EC12 encoder on the center with the pins bent in_

I noticed the plate did not want to align nicely on the PCB after installing the EC12 encoder. To fix this I bent the pins of the encoder inwards after soldering it and I filed down the top plate a bit:

![](/assets/images/montsinger/IMG_6850.jpg)

Then I simply added the switches to the top plate:

![](/assets/images/montsinger/IMG_6845.jpg)

And aligned it on the PCB:

![](/assets/images/montsinger/IMG_6848.jpg)

Then just flip it over and solder on the switches.

# Adding the battery

Initially I thought about adding the battery between the plates. But after putting on all the switches I needed I saw that there was a nice space left on the left and right corners of my board. So I decided to but a battery there and route the cables troug a switch hole. I super glued a small on-off switch on the the underside of the PCB to act as a power-switch.

![](/assets/images/montsinger/IMG_7285.jpg)

With some cramming I got my powercables routed through two connectors on the PCB that are not in use (not sure how safe this is but it works)

![](/assets/images/montsinger/IMG_7274.jpg)

Once on the other side I connected them up to my nice!nano:

![](/assets/images/montsinger/IMG_7273.jpg)

![](/assets/images/montsinger/20220901220740.jpg)

Whith everyting in place I tugged away the cables:

![](/assets/images/montsinger/IMG_7277.jpg)

## Baseplate and keycaps

The last step was to add the baseplate. This is pretty straight foward. To minimize heigt I decided to use short M2 screws with M2 bolts in between as stand off

When everything is put together, it looks like this:

![](/assets/images/montsinger/IMG_7294.jpg)

# Software and layout

I went with a simpel layout. I cloned the repo from [https://github.com/Gorbataras/zmk-nn-rebound](https://github.com/Gorbataras/zmk-nn-rebound) and gave it my own spin over at [https://github.com/KingOfSpades/zmk-nn-rebound](https://github.com/KingOfSpades/zmk-nn-rebound).
Tip, to enable the use of the rotary encoder you will need to enable them in [`config/rebound_v4.conf`](https://github.com/KingOfSpades/zmk-nn-rebound/blob/master/config/rebound_v4.conf#L4):

```yaml
# Uncomment these two lines to add support for encoders
CONFIG_EC11=y
CONFIG_EC11_TRIGGER_GLOBAL_THREAD=y
```

You can add a binding to the encoder per layer on the keyboard like so:

```yaml
sensor-bindings = <&inc_dec_kp C_VOLUME_UP C_VOLUME_DOWN>;
```

An example can be found here: [https://github.com/KingOfSpades/zmk-nn-rebound/blob/master/config/rebound_v4.keymap#L81](https://github.com/KingOfSpades/zmk-nn-rebound/blob/master/config/rebound_v4.keymap#L81)

# Closing thoughts

The Montsinger Rebound S is a fun build with lot's of custom options. It's my first choc low profile keyboard and it works great! It's also pretty affordable to build! I decided to go with an EC12 encoder that was really low-profile. This does remove the "click" feature found on other encoders but I don't feel like I'm missing allot. 