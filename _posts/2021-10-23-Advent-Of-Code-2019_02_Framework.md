---
layout: article
title: "Advent Of Code 2019: Framework :santa:"
key: "Advent Of Code 2019: Framework"
tags: aoc2019 rust yew
aside:
    toc: true
mermaid: true
---

To satisfy the requirements for my AoC target (see [previous post](/2021/10/23/Advent-Of-Code-2019_01_Intro.html)), I started looking into [Yew](https://yew.rs/){:target="_blank"}, a front-end framework similar to **React**, but based on Rust instead of javascript/typescript.

<!--more-->

## Getting started with Yew

### Day 1: Getting started

I followed the introduction on their website, and started creating a front end app which will become the base for my Advent Of Code 2019.
After some hours trying, I have made a small example of how I want it to be.

![My results with yew after some hours trying](/assets/images/yew_day_1.png)

It is still far off from what I want to achieve, however, I was already able to send messages to the web page. I needed this for my code output during the code run.

The main question I had after day 1, was if there was any way to put breakpoints in the code. - To be continued...

### Day 2: Continue work on front end

I searched for a way to debug my web application. Unfortunately, this does not seem possible yet, as there is currently no support for source maps in Rust/Wasm web apps [^1]. The only way to have some interaction between my source code and the web app was to use some sort of console logger. I chose to use the `ConsoleService`, as it is included within `yew`.

```rust
use yew::services::ConsoleService;
// usage
ConsoleService::info(format!("Update: {:?}", msg).as_ref());
```

The second issue I had was about using the component system. I have 1 parent component, which uses a child component. This child component needs to be updated when a property on the parent component changes, but I haven't found yet how to do it.

By re-reading the introduction, I discovered that [the yew repository](https://github.com/yewstack/yew/tree/master/examples) is filled with examples. Probably worth it to take a look there if there is a similar case to mine.

After looking into the samples repository, I found the `Pub_Sub` sample [^2], which lets 2 components talk to each other via an `event_bus`.
I will use the same architecture in my example: first I had the intention to build a `main` component, that contains a list with links to all the days, and a `day` component, which changes when a day is clicked. The current build up is a `main` component, with a `list` component and a `day` component in it. Communication between the `list` and `day` components happens using the `event_bus`, similar as the example app [^2].

After again some tinkering, I have now a front end app where I can select days from a list (= a list component). Via the event bus, the days are sent to a second component, which will display the UI for running my AoC code. Following topics are the ones I had to research a bit more to get my app working

- Rust modules and how to import them
- Converting an Enum to a string and vice versa
- The workings of the event bus

Next up, adding the logic of the AoC code to web app. My plan is to create a separate library project for the AoC logic. The public accessors for this library will be the following

- An enum which contains all the days that have already been implemented
- The `solution` struct, which can be initialized with an enum value containing the day, and 2 functions: part 1 and part 2

### Day 3: Library for the Advent of Code logic

Today, my plan is to implement the library for actual logic of the AoC challenge. The plan is to create a new library project inside the currently existing yew app. I want this library to be able to push some logging to the front end. Maybe the best solution here would be to set up a web socket connection?

After exploring the different options, I came to the conclusion that it would be the simplest to create a `Logger` trait in the advent of code library. I can then implement this trait on an `event_bus_logger` struct, which can send the messages to the `day_view` component. I will expand on this in another blogpost, but in short, this is what I came up with

```rust
// The Logger trait
pub trait Logger
{
    fn log(&mut self, message : &str);
}
```

```rust
// A simple console logger
pub struct ConsoleLogger
{

}

impl Logger for ConsoleLogger
{
    fn log(&mut self, msg: &str) {
        println!("Testing ConsoleLogger: {}", msg);
     }
}
```

```rust
// A logger, based on the yew event bus
pub struct EventBusLogger {
    pub event_bus: Dispatcher<EventBus>
}

impl Logger for EventBusLogger
{
    fn log(&mut self, msg: &str) {
        // Because different kinds of messages can be sent to the event bus, 
        // custom logic was implemented to serialize/deserialize different kinds of messages
        let message = Message::to_log_msg(String::from(msg));
        self.event_bus.send(Request::EventBusMsg(message.to_string()));
    }
}
```

### Conclusion

With the logging system implemented, the first part of my framework is finished. I now have a simple web app around a library for advent of code, which contains the following features

- Select a day of the calendar
- Send logging from the advent of code logic to a console window on the site

The following features are not yet implemented

- Upload an input file
- Add amount of time that it took to calculate the solution

To be continued... :fire::fire::fire:

[^1]: [Yew website, debugging section](https://yew.rs/more/debugging){:target="_blank"}
[^2]: [Pub sub sample from yew example repository](https://github.com/yewstack/yew/tree/master/examples/pub_sub)
