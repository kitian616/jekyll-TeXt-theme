---
layout: article
title: "VISUG XL 2022 - Orleans"
key: "visugxl2022orleans"
tags: dotnet csharp orleans
aside:
    toc: true
---

During [VisugXL][6], there was a talk about Orleans by Johnny Hooyberghs. This is the summary of my notes during that talk.

<!--more-->

## What is Orleans?

Orleans is Microsoft's implementation of the actor model. 
It is also called a `Virtual Actor Model`, because we can always reach all actors defined in the system.
If the actor is not loaded in memory yet, Orleans will spin it up for us.

When an actor is not used for a long time, it will be deactivated by the garbage collector.

## Definitions

### Actor Model

>The actor model is a programming model in which each actor is a lightweight, concurrent, immutable object that encapsulates a piece of state and corresponding behavior. 

[Microsoft learn][1]

Let's look into a simple example. Suppose that we want to create a small game with 2 players, and a chat functionality. In the actor model, we can represent this game with the diagram below.

```mermaid
graph TD;
subgraph Game Example
A[Player 1]
B[Player 2]
E[Game]
F[Chat]
A-->E
B-->E
A<-->F
B<-->F
end
```

### Grain

A grain is simply another word for an Actor. It consists of 3 parts:

```mermaid
graph TD;
subgraph grain
A[Identity]
B[Behavior]
C["State (optional)"]
end
```

The **Identity** can be a name, guid, number, ...
Anything goes. The only requirement is that it should be unique for each grain within the cluster. [Source][3]

The **Behavior** identifies the business logic that the actor should handle.

The **State** can be persisted to a database, or can be kept in memory for test projects as well.
Multiple implementations exist to persist a grain state, ranging from SQL server to postgres. 
You can also write your own implementations.

Important to note is that a grain is always **single-threaded**. This is to prevent conflicting state changes.

### Silo

A silo represents a host in the Orleans framework. Our system can contain 1 or multiple silos.
Each silo can contain one or more grains.

All grains can talk to each other, regardless of which silo they are in.

> Keep in mind, when a lot of communication exists between certain grains, they preferrably exist in the same silo.
This is because communication between grains in the same silo is more performant.
The Orleans framework allows us to configure in which silo a grain will be created. 
However, we are not required to configure this. The framework can take care of the **grain placement** as well.

Below, the previous example of the game is illustrated, with the actors being placed in different silos. Actors can communicate with one another, regardless of which silo they are in.

```mermaid
graph TD;
subgraph Game Example
subgraph Player Silo
A[Player 1]
B[Player 2]
end
subgraph Game Silo
E[Game]
end
subgraph Chat Silo
F[Chat]
end
A-->E
B-->E
A<-->F
B<-->F
end
```

### Dashboard

To allow for easier monitoring of our Orleans cluster, an open [source project has been created][2].
We can simply add the dashboard to our middleware by using `UseOrleansDashboard`.
Then, when running our project, we can open the browser and navigate to the dashboard.

On linux, we can also add `UseLinuxEnvironmentStatistics` to the middleware.
If we do this, we can monitor the CPU and ram usage of the silo(s) in the dashboard.

> The dashboard should only be used in testing environments.

## Communication

### Grain-to-grain

Orleans has a `IGrainFactory` in place to allow grain-to-grain communication.
When you add the `UseOrleans` extension to the middleware, this interface will be registered in the DI container.

### Grain-to-backend

Grain-to-backend communication is a bit trickier. In previous versions, we had a `grain client` available.
This allowed us to communicate with the Orleans cluster from external sources.
However, it is not recommended to let client apps talk with a grain directly.
Rather, a backend should be used to serve as the middleman between a client, and the Orleans cluster.
For this reason, the grain client is deprecated, starting from version 7.

The recommended way to set up backend-to-grain communication, is by putting your backend in a silo.
`UseOrleans` can be called in the backend middleware. This way, we can also use the `IGrainFactory`,
as discussed in [grain-to-grain communication](#grain-to-grain)
This is called a `heterogenous silo`: the silo contains not only grains, but serves also as the entry point to our Orleans cluster (i.e. our backend).

```mermaid
graph TD;
subgraph Game Example with heterogenous silos
subgraph Player Silo
A[Player 1]
B[Player 2]
end
subgraph Game Silo
E[Game]
end
subgraph Chat Silo
F[Chat]
end
subgraph Heterogenous silo
G[Backend]
end
H[Frontend]
end
A-->E
B-->E
A<-->F
B<-->F
A<-->G
E<-->G
G<-->H
```

## When to use

Orleans is a very useful tool for writing applications with complicated business logic, and scaling requirements.
It is less useful when your application will only contain simple CRUD logic. In that case, a REST API will be easier to set up and maintain.

## Hosting

Orleans projects can be hosted on [multiple platforms, in multiple configurations.][7]
To me, [kubernetes hosting][5] seems the most interesting, because it is very scalable, and provider-agnostic.

## Conclusion

Orleans is Microsoft's implementation of the `virtual actor pattern`. 
Through [easy-to-grasp concepts](#definitions), it becomes relatively easy to write scalable .NET applications, without much boilerplate code.
Debugging becomes easy with the [open-source dashboard project][2].

Use Orleans when there is complex business logic in your application and performance is important.
Do not use Orleans for simple applications that mainly consist of CRUD operations.

[1]: <https://learn.microsoft.com/en-us/dotnet/orleans/overview>
[2]: <https://github.com/OrleansContrib/OrleansDashboard>
[3]: <https://learn.microsoft.com/en-us/dotnet/orleans/overview#what-are-grains>
[4]: <https://learn.microsoft.com/en-us/dotnet/orleans/>
[5]: <https://learn.microsoft.com/en-us/dotnet/orleans/deployment/kubernetes>
[6]: <https://www.visug.be/Events/80>
[7]: <https://learn.microsoft.com/en-us/dotnet/orleans/deployment/>
