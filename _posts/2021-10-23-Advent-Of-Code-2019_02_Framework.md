---
layout: article
title: "Advent Of Code 2019: Framework"
key: "Advent Of Code 2019: Framework"
tags: aoc2019
aside:
    toc: true
mermaid: true
---

To satisfy the requirements for my AoC target (see previous post), I started looking into [Yew](https://yew.rs/){:target="_blank"}, a front-end framework similar to **React**, but based on Rust instead of javascript/typescript.

<!--more-->

## Getting started with Yew

### Day 1

I followed the introduction on their website, and started creating a front end app which will become the base for my Advent Of Code 2019.
After some hours trying, I have made a small example of how I want it to be.

//TODO: fix image
![My results with yew after some hours trying](../assets/images/yew_day_1.png)

It is still far off from what I want to achieve, however, I was already able to send messages to the web page. I needed this for my code output during the code run.

I still had some issues while developing

- Did not yet find how to put breakpoints in the code - is this possible?

### Day 2

I searched for a way to debug my web application. Unfortunately, this does not seem possible yet, as there is currently no support for source maps in Rust/Wasm web apps [^1]. The only way to have some interaction between my source code and the web app was to use some sort of console logger. I chose to use the `ConsoleService`, as it is included within `yew`.

// TODO use a gist

```rust
use yew::services::ConsoleService;
// usage
ConsoleService::info(format!("Update: {:?}", msg).as_ref());
```

The second issue I had was about using the component system. I have 1 parent component, which uses a child component. This child component needs to be updated when a property on the parent component changes, but I haven't found yet how to do it.

By re-reading the introduction, I discovered that [the yew repository](https://github.com/yewstack/yew/tree/master/examples) is filled with examples. Probably worth it to take a look there if there is a similar case to mine.

After looking into the samples repository, I found the `Pub_Sub` sample, which lets 2 components talk to each other via an `event_bus`.
I will use the same architecture in my example.

After again a few hours of tinkering, I have now a front end app where I can select days from a list (= a list component). Via the event bus, the days are sent to a second component, which will display the UI for running my AoC code. Following topics are the ones I had to research a bit more to get my app working

- Rust modules and how to import them
- Converting an Enum to a string and vice versa
- The workings of the event bus

[^1]: [Yew website, debugging section](https://yew.rs/more/debugging){:target="_blank"}
