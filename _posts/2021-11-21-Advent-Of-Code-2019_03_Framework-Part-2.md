---
layout: article
title: "Advent Of Code 2019: Framework - Part 2 :santa:"
key: "Advent Of Code 2019: Framework - Part 2"
tags: aoc2019 rust yew
aside:
    toc: true
mermaid: true
---

## Continuing the work on my AoC framework

<!--more-->

I left the previous post with some features that still need to be implemented

- Upload an input file
- Add amount of time that it took to calculate the solution

### Day 4: Refactoring the project

After implementing the event bus logger in [Part 1](/2021/10/23/Advent-Of-Code-2019_02_Framework.html), I decided it was necessary to do some refactoring. I decided that the advent of code library should be in its own project, with a public facing `get_solution` function.

```rust
pub fn get_solution(day: &Days, part: &Part) -> String
```

In the future, this should be expanded with the logger that was created in the previous post.

I have also written some `docTests` for this public facing function. This is done by adding a comment on top of the function, and then use backticks.

```rust
/// Gets the solution of the day and part speficied
/// ```
/// use advent_of_code::get_solution;
/// use advent_of_code::solutions::general::{part::Part, day::Days};
/// let result = get_solution(Days::Day1, Part::First);
/// assert_eq!("Sum of fuel requirements: 3416712".to_string(), result)
/// ```
///
```

The cool thing is that these docTests are run every time you use `cargo test`. Also, when generating docs for you library (run `rustdoc src/lib.rs` when in root directory of your library), the tests are included in the docs.
