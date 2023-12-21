---
layout: article
title: Sundial Case Study
aside:
  toc: true
---
## 1 Introduction


### 1.1 Overview of Sundial

Sundial is a self-hosted, open-source cron job monitoring and management system that users can operate across one or multiple nodes. 

Designed primarily for individuals and small to medium teams, it provides a readily deployable option to aid in setting up, modifying, and monitoring cron jobs. 

This case study introduces the cron utility and its problem space, followed by the design and implementation of Sundial. It concludes with a selection of tradeoffs and technical challenges.


### 1.2 The Cron Utility

Cron is a time-based job scheduler found in Unix and Unix-like operating systems, allowing users to automate the execution of tasks at specified intervals. Created in 1975, it remains a fundamental tool for scheduling routine processes.

#### 1.2.1 Use Cases


Some common use cases of the cron utility are:


1. **Database Backups** \
Database backups are crucial for data management and risk mitigation, enabling organizations to recover from data loss swiftly and maintain business continuity. They must be performed regularly, making them a perfect candidate for automation using cron.

2. **Log Rotation** \
System logs can grow over time and consume valuable disk space. Cron can rotate and compress log files at scheduled intervals, preventing them from becoming too large.

3. **System Maintenance** \
System maintenance tasks such as cleaning up temporary files, performance testing, and updating software can be automated using cron. Regularly performing these tasks helps ensure the system's smooth operation.

4. **Producing Reports** \
Cron jobs can be used to generate and send regular reports, such as sales figures, to relevant departments - this can help keep an organization efficient and productive.

5. **Time-Based Scaling** \
Organizations with predictable traffic hours can use cron to scale their applications based on predefined schedules. Cron jobs can deploy additional server instances during peak hours and then remove them again later. This aids the efficient use of resources and conserves costs without requiring manual adjustments.

#### 1.2.2 Cron Job


A cron job is a command or shell script executed periodically according to a fixed schedule, such as a specific time, date, or interval. Cron jobs comprise a schedule and a script; below is an example.


[cron job pic]


The schedule is articulated in a cron-specific syntax, detailed further in the Limitations [LINK TO LIMIATIONS???] section. The script denotes the specific executable to be run by cron at the scheduled intervals.

#### 1.2.3 Crontab


The term ‘crontab’ refers to _both_ a **configuration file** used for managing cron jobs and a **command-line tool** to interact with said configuration file. Subsequent references in this document pertain to the configuration file.


See how the command crontab -l displays the contents of the crontab configuration file.


[crontab -l opening vid]


Each user on a machine has an individual crontab, and there is also a system-wide crontab.

#### 1.2.4 Crond


Crond functions as a background process, also called a daemon. It regularly scans crontab files for scheduled jobs. When crond identifies a job scheduled to run, it initiates a child process dedicated to executing the job, as shown below. 


[crond launching process]


Crond configures the environment to match the user's specifications, and the child process inherits the environment from crond, ensuring access to essential file paths and permissions while operating independently from both crond and the user shell.


### 1.3 Limitations of Cron

Despite being widely used, cron has limitations. A brief search for 'cron job issues' returns many results, indicating that users often face challenges when working with cron. Common cron user concerns include [CITATIONS]:

* Did my cron job start?
* Was my job completed?
* Is my schedule correct?
* Where can I find logs for cron jobs?

The remainder of this section explains the specific issues associated with cron that we designed Sundial to address.


#### 1.3.1 Cron jobs fail silently


The cron utility lacks alerting capabilities for errors during job execution. When a job encounters failure, there are two potential scenarios: it either fails to start or fails to complete successfully.


A failure to start indicates an issue with the cron utility, where crond did not execute the script. Cron cannot initiate jobs if they contain specific user errors like incorrect paths or typos in the script names. Additionally, job initiation may fail during situations where system resources are depleted.


A failure to complete often suggests an issue inherent to the job itself. It could be the result of a bug or a failure of a dependency.


A particularly problematic scenario of job failure arises when the scheduled interval for a job is shorter than the duration of the job itself. This results in concurrent execution, known as **overlapping jobs,** and can potentially deplete system resources.


[graphic of overlapping jobs example]


The impact of a job failing to run can vary from being merely inconvenient to severely detrimental. While the inconvenience of a marketing email not being sent might be manageable, the absence of essential database backups and the lack of crucial security updates can leave the system vulnerable.

#### 1.3.2 Cron logs are not centralized


While the cron utility does not have built-in alerting for failed job execution, it records every attempt at cron job execution in the syslog. Therefore, users can manually inspect the syslog to confirm whether cron initiated the job.


Logs related to cron job output, including errors, aren’t saved anywhere by default. A user must manually direct the data to a location to capture these outputs. Options for this include configuring the cron utility to send output to their email or creating a log file and directing output from the cron job to that file.


Users must review these logs manually to determine if the job encountered any issues. If a user has numerous cron jobs or is running just a few frequently, it requires time, effort, and careful attention from the user to sift through the large number of logs produced to find and verify the desired information. 

#### 1.3.3 Writing cron jobs is error-prone

The cron scheduling syntax can be unintuitive, especially for new users. 


Figure 1 (left) is an example of a schedule that indicates once a year, and Figure 2 (right) [ADJUST DESCRIPTIONS ONCE IMAGES ARE IN] indicates once a month.


[once a month and once a year figures]


The image below explains each field's meaning.

[image of cron scheduling syntax meanings]

It’s easy to accidentally write the wrong schedule, leading to jobs running at unexpected times. Additionally, a user must manually edit the crontab. When numerous other cron jobs exist, it’s easy to edit the wrong one mistakenly.


#### 1.3.4 Summary


To review the problems users encounter while using cron:


* jobs fail silently
* logging is not centralized
* writing cron jobs is error-prone


### 1.4 Existing Solutions

There are many solutions to the issues we’ve mentioned. Services might focus on job monitoring or job management or offer both. Some do not use cron but instead implement the same functionality using different technologies, so they handle scheduling and execution through their own platform. We can split these options into the following categories:


#### 1.4.1 Paid Services

Two paid solutions that aim to improve the cron experience are **Cronitor** and **Cronhub**.

**Cronitor** prioritizes monitoring and provides a Command Line Interface (CLI) that automatically identifies all existing cron jobs on a system. It also offers monitoring of workflows, health checks, status sites, and other related features.

**Cronhub** offers both monitoring and job scheduling on a single platform. The scheduling implemented by Cronhub does not rely on cron; any jobs scheduled with Cronhub will only appear on the Cronhub interface and not on a user’s own machine.

Both paid services have disadvantages:



1. **Data ownership**: Dependence on a third-party service for monitoring or scheduling introduces the risk of relinquishing a user's data ownership. This may be undesirable for several reasons, including privacy concerns, as job error logs could contain sensitive system information, or legal reasons, such as the requirement to store data in the user's own country.
2. **Monthly fees**: Both solutions impose a monthly fee on the user, determined by the number of monitors they have.


#### 1.4.2 Open-Source

Two open-source solutions are **Uptime Kuma** and **Cronicle**.

**Uptime Kuma** is a monitoring tool for various services, e.g. HTTP(s), TCP, and Docker Containers. While Uptime Kuma offers tracking of cron jobs through a Push monitor/webhook, its primary focus isn't on cron jobs. Despite having numerous features related to uptime monitoring, it lacks specific capabilities for collecting cron-related data such as start times, durations, or error logs.

**Cronicle** is a cron-like service that handles the scheduling and execution of jobs internally without any help from the cron utility. Its main downside is that a user needs to transition any existing cron jobs to the service, which could be time-consuming and error-prone. Users may also be more comfortable relying on the cron utility instead of a system that claims to function like it.

[comparison table just paid + open source]


#### 1.4.3 DIY

A developer can also choose to build their own solution by copying the pattern used by the existing solutions. The pattern is to use HTTP requests for monitoring: a request will be sent before and after a job has been executed. The requests indicate if a job started and ended, and can be used to gather other details, like error messages.

[curls pic]

Users don’t get a monitoring interface or other features like the named services offer, and setup and maintenance would require additional work. Still, it is customizable and contained wholly on their system.

[comparison table just paid + open source + DYI]


### 1.5 Sundial

In exploring the options above, we saw a need for a product that would work for a user that: 



* Has existing cron jobs and does not want to use a cron-like service
* Prioritizes owning their data (no third party)
* Seeks out cost-effective solutions
* Wants the option to monitor and manage their jobs in one place

To meet this use case, we based our decisions on the following goals:



1. **Control** \
We created Sundial as an open-source and self-hosted product, allowing users to maintain full control over their code and data.
2. **In-Depth Cron Monitoring** \
Our monitoring system focuses on the cron utility. Sundial's oversight of cron jobs centers on automatically discovering tasks with a script the user can execute from the CLI and gathering jobs’ start times, durations, and error logs at runtime.
3. **Cron-Based Management** \
Sundial offers a centralized platform for monitoring and managing all your cron jobs. Any modifications made to jobs within the user interface (UI) will update the crontab automatically.

[comparison table just paid + open source + DYI +  Sundial]

In summary, Sundial is an open-source, self-hosted solution that focuses specifically on the cron utility and provides:



* reliable monitoring
* centralized error logging
* convenient job management from a UI

## 2 The Sundial System


### 2.1 Architecture

Sundial provides cron job monitoring and management across one or multiple nodes.


#### 2.1.1 General Overview of Components

The Sundial system consists of two main components:



1. The Monitoring Service
2. The Linking Client

X[2.1pic of both big boxes] 

The following sections will give a general outline of the Monitoring Service and the Linking Client. We’ll explain the details of individual components, their roles, and the monitoring or management concepts where they apply.


##### 2.1.1.1 Monitoring Service

The **Monitoring Service** is primarily responsible for actively monitoring the execution of cron jobs. Additionally, it provides users with an interface, offering a way to interact with and manage their cron jobs.

The Service consists of four components: 



* a UI
* an application server that exposes an API
* Task Queues
* a PostgreSQL database

X[2.2pic of monitoring service with components in docker]

We’ve containerized the Monitoring Service for straightforward deployment. The UI, database, and application server (including the Task Queues) are each encapsulated into a Docker image. A Docker Compose script runs them collectively as a single package.


##### 2.1.1.2 Linking Client

The **Linking Client** serves as a link between the Monitoring Service and the crontab.

X[2.3pic of linking them together]

It consists of: 



* a lightweight HTTP server known as the **Listening Service**
* a binary executable containing a collection of scripts

X[pic of LC and its components]

The Linking Client is packaged as a standalone binary executable. Users can install the Linking Client on any Linux server without additional dependencies. 

Once installed, the Linking Client scripts can be executed by other processes, notably crond, or by the user via commands in the CLI.

After installation, the user must execute one such command: `sundial register`. This executes the registration script, which establishes the connection between the Monitoring Service and the Linking Client and configures the Listening Service to run as a background process.


#### 2.1.2 Adding Nodes

Sundial accommodates both single and multi-node setups.

In a **single-node configuration**, the Monitoring Service and the Linking Client coexist on the same node.

X[2.4“vid” of single node]

For **multi-node scenarios**, additional nodes - termed **remote nodes** - are integrated through the installation of the Linking Client.

The Monitoring Service, on the other hand, only runs on one designated node, referred to as the **hub node**. 

If desired, the hub node can exclusively host the Monitoring Service, monitoring the crontabs of remote nodes across a distributed network. This provides users with flexibility in their deployment configurations.

X[2.5“vid” of multi node]


### 2.2 Job Monitoring

Monitoring aims to detect issues promptly, such as errors during job execution or jobs failing to run. Sundial conveys this information through its UI, using color to highlight potential faults during job execution.

[IMAGE OF JOB PAGE]

The Monitoring Service monitors the system by documenting each job execution and any instances where a scheduled job fails to execute.


#### 2.2.1 Awareness of Jobs and their Execution

This section focuses on how the Monitoring Service documents job execution. To do this, the Monitoring Service requires two things:


1. Prior knowledge of a user’s jobs and when they are due to execute.
2. Real-time notification of when jobs start, when they end, and when they encounter errors.

The Linking Client provides both requirements to the Monitoring Service. Next, we explain the two scripts that enable the Linking Client to do so.  

X[3.1pic of linking client box with scripts box inside ]


##### 2.2.1.1 Prior Knowledge - `discover`

The Linking Client uses its `discover` script to provide the Monitoring Service with knowledge of jobs in a user’s crontab. The user executes the command `sundial discover` from the CLI to run the `discover` script.

The `discover` script sends information about each job in the crontab file, such as the schedule and command, to the Monitoring Service. The Monitoring Service stores this information in its database.

X[3.2crontab and matching monitors]

Additionally, the `discover` script sets up the real-time notifications of job execution that the Monitoring Service requires through a process called “**wrapping**”.

X[3.3pic of prewrapped and wrapped crontabs]

As shown above, cron jobs are considered “**wrapped**” when the text `sundial run`, followed by a string of letters and numbers, has been inserted in between the schedule and the command.

The string of alphanumeric characters, known as the **endpoint key**, is used by the Monitoring Service to link execution notifications from a job with the job’s corresponding database entity. 

The following section will go over the `run` script in detail. 


##### 2.2.1.2 Real-Time Notification - `run`

Once the Linking Client has wrapped a job, it can send notifications about its execution via the Linking Client’s `run` script.

The `run` script sends information to the Monitoring Service via requests to the Monitoring Services API; these requests are called **pings**.



??[vid of run script from LS sending ping to Monitoring Service]


There are **three** types of pings:


* When a job starts executing, the run script sends a **start ping** to the Monitoring Service
* When a job finishes execution, the run script sends an **end ping** to the Monitoring Service
* When a job encounters an error, the run script sends an **error ping** to the Monitoring Service

These pings give the Monitoring Service real-time notification of when jobs start, end, or encounter errors.


---

The remainder of this section is a detailed explanation of how the `run` script sends pings.

First, a refresher on how the cron utility executes cron jobs: the cron daemon executes anything following the schedule string. In the example below, crond executes the `rotate-log` script.

[3.4 rotatee log pre-wrapped]


X[3.5vid of launching rotate-log]

When a job is “wrapped”, the schedule string is followed by `sundial run`, an endpoint key, and the original job script. With this setup, the cron daemon executes `sundial run` with two arguments. Recall that `sundial run` is simply a script installed as part of the Linking Client.


X[3.6sundial run process running vid]

[3.7 roate log wrapped]

When launched, the `run` process sends a start ping to the Monitoring Service to notify the Service that the job has begun.


X[3.8vid of start ping]

Next, the `run` process spawns a child process that executes the actual job script.


X[3.9vid of launching  job process]

Since child processes inherit the environmental variables of their parent processes, the `run` process inherits the user context set up by _crond_ and passes that on to the job process. This means the job is executed in the same environment as if it were run directly by _crond_.

Once the job finishes executing, the `run` process is made aware of its completion through an exit code - this is again because the job process is run as a child of the `run` process. The `run` process sends an end ping to the Monitoring Service.


X[3.10vid of end ping]

Additionally, if the exit code provided by the job process signifies an error occurred, the `run` process sends the Monitoring Service an error ping. This ping is different from an end ping in that it contains the error log, if one is available, returned by the job process.


X[3.11vid of error process plus ping]

Finally, the `run` process exits.

In summary, the Linking Client's scripts allow the Monitoring Service to document the execution of cron jobs and store error logs.


#### 2.2.2 Awareness of Missed Execution

Recall that to monitor effectively; the Monitoring Service must document not only the execution of cron jobs but also any instances where a scheduled job fails to execute as intended.

A few reasons for irregular or failed job execution include:



* Host node resources could be depleted, preventing the cron daemon from running jobs
* A job might be running for longer than usual - perhaps because it's processing a larger than normal data set or it’s stuck in an infinite loop
* Manual changes to the crontab may not have been propagated to the monitoring service
* Network issues

When a job fails to execute, the Linking Client doesn't send any pings to the Monitoring Service. Despite this, the Service must maintain a record of this event. Solving this challenge is not straightforward because it requires the Monitoring Service to recognize the absence of a ping, also known as a **missed ping**. 

The Monitoring Service uses **Task Queues** to deal with missed pings.

Task Queues are implemented with **pg-boss** [[3]](https://github.com/timgit/pg-boss) [ENSURE CITATION NUMBER CORRECT], an npm package built on PostgreSQL. Specifically, we leverage the **deferred tasks** feature, where tasks are added with a specified delay and are processed by a worker only after that delay has passed.

X[3.12bg boss worker pull off vid]

Above is an example of a deferred task in action: there are three tasks on a queue, and as time elapses, the delay eventually reaches zero, and the worker processes the task.

X[3.13pic of task queue box in monitoring service]

Task Queues refers to a Start Queue and an End Queue. The Queues use **tasks** to keep track of the expected arrival times of each cron job’s start and end pings.

Every task on either Queue is added with a delay and waits for a specific ping from the Linking Client. If the ping arrives, the Monitoring Service removes the task. If the delay elapses without the arrival of the ping, a worker processes the task. 

In the examples, we will elaborate on a few items the worker is responsible for. Critically, the worker documents that the Monitoring Service did not receive a specific job’s expected start or end ping.


##### 2.2.2.1 Start and End Queues

Start and End Queues use tasks to recognize the absence of start and end pings, respectively. 

A task exists in the Start Queue for every job in the Monitoring Service at all times. This is because, by definition, there is always a next expected start time for any given cron job. 



??[pic of monitors to tasks in queue to cron jobs]

A task is added to the End Queue only after a job’s start ping arrives. If the Monitoring Service does not receive a job's start ping, the Service’s logic dictates that an end ping should not be expected, and the End Queue is not used.

The most critical component of the Queues is the **delay** for each task. If the Monitoring Service doesn’t receive a ping within the specified delay, the associated task is processed, and the Service documents the ping as missed.

Calculating the delay for a Start Queue task:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **time until next scheduled execution of job + grace period**



The grace period is set to 5s to account for expected delays such as network latency or high load on the Monitoring Service.

Calculating the delay for an End Queue task :

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **tolerable runtime + grace period**


Tolerable runtime signifies the maximum acceptable duration of the job. The default is set to 15s, but users can configure this in the UI to suit their preferences.

Since every task has a delay, these calculations occur every time a new task is added or when one is updated.

In summary, the Task Queues allow the Monitoring Service to document the missed execution of cron jobs.


#### 2.2.3 Utilizing the Data

Through the methods discussed above, the Monitoring Service can record data about the execution of each job. 

Execution data is organized as database entities called **runs**. For each expected execution of a cron job, Sundial creates a new run. Runs contain information regarding the existence or absence of start and end pings captured in the run’s **state**. The UI displays this data to the user.

[more variety of runs in screenshot]

The system accounts for seven various **run states**. Each state provides insight into the execution status, occurrences of errors and irregularities, and, taken together with other listed runs, the overall health of the cron job. 


#### 2.2.4 Examples

The detailed examples that follow outline the sequence of events triggered within the Monitoring Service for when the Service: 



* _receives_ an expected start ping
* _receives_ an expected end ping
* _does not receive_ an expected start ping
* _does not receive_ an expected end ping


##### 2.2.4.1 Pings Received

Pings contain:



* **an endpoint key,** used for matching a job to a monitor
* **a run token**, used for associating an executing job’s start and end ping

**Start Ping Arrives:**



1. The Linking Client sends a start ping before the job starts.
2. The Monitoring Service creates and stores a run. The run contains the supplied run token and is given a state: `started`. 
3. The Monitoring Service displays information derived from the run state using Server-Sent Events (SSE) in the UI.  
[IMAGE OF STARTED RUN IN UI]
4. To ensure that the Task Queues are constantly keeping track of when a job’s start or end pings are expected to arrive _next_, the Monitoring Service makes changes in **both** the Start and End Queues:
    * _Update_ task in Start Queue:
      1. The Services uses the ID (in this case, 7) to find the task in the start queue that is associated with the correct monitor
      2. The Service updates the delay to reflect the next expected arrival of a start ping (12 hours)  
        X[3.14 UPDATE START QUEUE]

    * _Create_ task in End Queue:
      1. The Service adds a task with the **run token** provided by the ping. The Service uses the run token to ensure it pairs data from the correct start and end pings.
      2. The Service sets the delay using the **tolerable runtime** (1 minute)  
        X[3.15 ADD TO END QUEUE]


**End Ping Arrives:**



1. The Linking Client sends an end ping once the job ends.
2. The Monitoring Service retrieves the run created by the start ping and updates its state to `completed`.
3. The Service removes the associated End Queue task because the end ping came within the tolerable runtime.
    * _Remove_ task from End Queue:  
X[3.16remove from end queue]



##### 2.2.4.2 Pings Missing

**No Start Ping Arrives:**



1. The Linking Client does not send a start ping when the Monitoring Service expects one.
2. The delay on the task in the Start Queue elapses, and a worker processes the task.  
X[3.17worker at  start queue]
3. The worker creates a new run with the state `missed`.  
X[IMAGE OF MISSED RUN UI]
4. The worker creates a new task in the Start Queue to recognize the next expected start ping.

**No End Ping Arrives:**



1. The Linking Client does not send an end ping when the Monitoring Service expects one.
2. The delay on the task in the End Queue elapses, and a worker processes the task.  
X[3.18worker at end queue]
3. The worker retrieves the run created by the start ping and updates the state to `unresolved`.  
X[IMAGE OF UNRESOLVED RUN IN UI]  

### 2.3 Job Management

The third and final service Sundial provides is cron job management. This is the ability for users to add, edit, and delete jobs across one or multiple nodes from the UI. Managing jobs through the UI reduces the risk of errors associated with manual crontab changes. It also adds user convenience by providing a centralized platform for interacting with one or multiple crontabs.

Each cron job is added or edited from its form, like the one shown here.

X[FORM IN UI IMAGE]

The form allows the user to see the schedule and command of a job clearly at a glance and makes it harder to modify the wrong cron job. Additionally, the schedule field includes an automatic Schedule Translator. It translates the schedule string to text in real time as a user enters data into the form. The Translator confirms the accuracy of the schedule, clarifying the cryptic cron schedule syntax.

X[IMAGE OF SCHEDULE TRANSLATION]

The Monitoring Service automatically synchronizes changes made to jobs from the UI with the crontab. In a multi-node setup, the user must specify the node when adding new jobs.


#### 2.3.1 Altering Crontab from UI

New jobs or job updates written to the UI are referred to as **management data**. This section explains how management data travels to its intended crontab. The components involved in management are: 

[image of components, with numbers]

1. UI
2. Database
3. Listening Service
4. Linking Client `update` script
5. crontab

Recall:
* The Monitoring Service runs in a Docker container on one node, the hub node.
* The user installs the Linking Client on each node with a crontab they want to integrate with Sundial. 

As shown, the UI and database are part of the Monitoring Service. When the user inputs new management data to the UI, the data is saved to the first persistent data store it encounters: the database. 

[vid ui to database]

Next, the management data must travel from the Monitoring Service’s database to the appropriate crontab. Since the crontab and the Monitoring Service might reside on different nodes, the management data may have to travel over the network. Even in the single-node architecture, editing the crontab of the host machine directly from the Monitoring Service poses difficulties because the Monitoring Service runs in a Docker container. 

To address this issue, the Linking Client’s `update` script fetches management data from the Monitoring Service’s database and writes it to the crontab. 

[update script sending request and getting data and writing to crontab video]

The **Listening Service**, a simple HTTP server integrated into the Linking Client, executes the Linking Client’s `update` script.  

[ping sent to listening service launches update script video]

The Listening Service has one role: to await requests from the Monitoring Service. A request signals new management data is available in the Monitoring Service's database. When the Listening Service receives this request, it initiates the execution of the `update` script. Note that the `update` script is idempotent, guaranteeing consistent and predictable outcomes with each execution.

Below is a diagram of the complete management data flow.

[video]

While this process may seem circuitous, deliberately routing management data through the Linking Client’s `update` script is intentional. We will explain our design considerations in the Engineering Decisions section.


