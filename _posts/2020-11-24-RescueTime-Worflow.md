---
title: Using RescueTime and Alfred for easy focus sessions
toc: true
mermaid: true
categories: [Productivity]
tags:
  - rescuetime
  - alfred
  - macos
---

During the last weeks of working from home I have been using the FocusTime feature of RescueTime a lot more. I use these timeblocks (20 minutes for example) to focus in on specific tasks or to create a timebox where I can work trough a small set of problems. For this I have been using 
- RescueTime (Premium)
- Alfred
- IFTTT
- A TimeTimer

# Overview
The workflow is quite simple. I set a TimeTime to the time I want to focus. I use the TimeTimer as a visual aid to see my progress. I then use Alfred and quick workflow I create to tigger the RescueTime API. The source code can be found at https://github.com/KingOfSpades/FocusStarter

![Focus Starter Example](/assets/images/focusStarter.gif)

This kicks of the following events
![Focus Time Workflow](/assets/images/FocusFlow.png)

I use a IFTTT workflow to trigger a calendar event creation for in the weekly review and I use a TimeTimer as a visual aid.

![TimeTimer in Action](/assets/images/TimeTimer.jpeg)

# The Why
Why am I using all this? Well the RescueTime FocusTime feature blocks all distracting websites during a session which is kind of neat. It also gives me some traceability of how productive my day has been and keeps me accountable.

# The How
## Setting up RescueTime
Not much setup needed. If you have a RescueTime account on https://www.rescuetime.com you can get started setting everything up.

## Setting up Slack
Go to the integrations page on RescueTime (https://www.rescuetime.com/anapi/setup/overview) to setup the Slack integration. RescueTime wil now set your status to Do Not Disturb when using the Focus Time

## Calling the API
You'll need an API get call the API and use the Alfred workflow, create the API key at https://www.rescuetime.com/anapi/manage and het the workflow from my GitHub page (you can also incorporate the API command's in your own script) at https://github.com/KingOfSpades/FocusStarter

When you have a key you can easily use the API key to start a focus session, example:
```bash
curl --location --request POST \
'https://www.rescuetime.com/anapi/start_focustime?key=<your_api_key>&duration=20'
```

or to stop a session, example:
```bash
curl --location --request POST \
'https://www.rescuetime.com/anapi/end_focustime?key=<your_api_key>'
```

More information at https://www.rescuetime.com/apidoc . You an also use the API to get feedback on your daily RescueTime score, checkout my BitBar plugin rescueTimeBar at https://github.com/KingOfSpades/rescueTimeBar

## IFTTT Trigger (optional)
I setup a IFTTT trigger to create a calendar event when I start a Focus session. This helps me to trace my use of focus time trough the week. You can set it up at IFTTT. I'm planning to replace this with a integrated option in my Alfred workflow in the future.

# Closing
I really enjoy the speed that I can trigger a focus session at the moment. It really eliminated almost all the friction between "I should do a focus moment" and "I'm doing a focus moment". In the future I would like to expend the workflow so it also triggers DND on macOS and I want to replace the IFTTT trigger with something local. Maybe I'll even add a little "On the Air"  light to my setup.