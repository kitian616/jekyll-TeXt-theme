---
title: Quick open Things3 with Alfred
toc: true
mermaid: true
categories: [Productivity]
tags:
  - things3
  - alfred
  - macos
---
In my daily workflow I have found that when using different tools and systems with each other removing friction can be key. This is not a original insight or even a leap of the imagination but I do find myself eliminating friction in workflows in new ways. One of those ways I recently Implemented is to use [Alfred](https://www.alfredapp.com) on the Mac to quickly jump to lists or filter in my preferred ToDo app [Things3](https://culturedcode.com/things/). 

This is how it works

# Things3 URL Scheme

You can easily create custom URL schemes using the [Things3 URL Scheme Creator](https://culturedcode.com/things/support/articles/2803573/) . Using the `things///` will tell you macOS or iOS device to use the Things app to open the action. Some examples of Query's I use on the daily:

- `things:///show?id=inbox` To show the Inbox
- `things:///show?query=WORK%20-%20JIRA` To show the list `WORK - JIRA` from Things
- `things:///show?id=today&filter=WORK` To show ToDo's that are scheduled for `Today` with the tag `WORK`

You can add these URL's to any place that allows links so you can even add links from `.md` documents or text documents to refer to projects which can be a great workflow.

# Alfred open URL Action

Opening URL's with Alfred is easy using a custom workflow. To focus the app and make sure it's running I include the open app action in my workflow. 

![Screenshot 2021-09-27 at 10.47.16](/assets/images/Screenshot 2021-09-27 at 10.47.16.png)

I use the `-` sign as a prefix to trigger the workflow. Using the list filter you can add Query's to the workflow:

![image-20210928120122517](/assets/images/image-20210928120122517.png)

# Putting it together

A simple workflow get's a simple demo, this is how mine works. When the Keyword `-` is activated it pop's up a list of all my saved search query's. Choosing one of these will open Things3 to that list

![Sep-27-2021 11-06-05](/assets/images/Sep-27-2021 11-06-05.gif)