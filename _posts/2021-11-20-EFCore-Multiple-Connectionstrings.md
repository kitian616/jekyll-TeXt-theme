---
layout: article
title: "How to use multiple databases based on the same context"
key: "EFCore Multiple Databases"
tags: csharp efcore aspnet
aside:
    toc: true
---

This blogpost explains how to fetch data from multiple databases in an aspnet 5 application. The connectionstrings are switched based on a query parameter in the request.

<!--more-->

To demonstrate this functionality, I Created a test site that has 3 different SQLite databases based on the same database context.

A live preview can be found [here](https://efcoretest.willemserruys.com/home/person), and the source code [here](https://github.com/wserr/EntityFrameworkCore.Examples)

The most important part of this code, is the logic inside the [ApplicationDbContext](https://github.com/wserr/EntityFrameworkCore.Examples/blob/master/EntityFrameworkCore.Examples.Persistence/ApplicationDbContext.cs).

## Migrating the database context

My database context consists of only 1 entity: Person. To generate the migrations, we can make use of the [Design nuget package for EFCore](https://www.nuget.org/packages/Microsoft.EntityFrameworkCore.Design/) and [The EF Core cli tools](https://docs.microsoft.com/en-us/ef/core/cli/). Add this nuget package to the project where the DbContext is located, and execute the following commands in a terminal, pointing to the same folder.

```bash
dotnet tool install --global dotnet-ef
dotnet ef migrations add InitialCreate
```

For more info, head over to the [Microsoft Docs Site](https://docs.microsoft.com/en-us/ef/core/managing-schemas/migrations/?tabs=dotnet-core-cli)

The mistake I made here was that the Context should contain an empty constructor. Otherwise you will not be ableto generate the migrations.

```csharp
public ApplicationDbContext()
{
    
}
```

## Injecting the HttpContextAccessor

The `HttpcontextAccessor` allows us to read variables in the HttpContext, such as the query parameters, logged in user, and so on. To use this in our Context, we need to inject it via the constructor.

```csharp
public ApplicationDbContext(IHttpContextAccessor contextAccessor)
{
    // We will use the context accessor to extract the connectionstring
    _contextAccessor = contextAccessor;
}
```

This also requires us to add the HttpContextAccessor to our `ConfigureServices` function.

```csharp
services.AddHttpContextAccessor();
```

## Automatically create the databases on startup

We can automatically create the SQLite databases in the `Configure` method in the `Startup.cs` file. To do this, I added a `SetConnectionString` function to the ApplicationDbContext. Now, for each database we want to create, we can set the ConnectionString, and then Migrate the database. The `Migrate` function also makes sure the database exists.

```csharp
var serviceScopeFactory = app.ApplicationServices.GetRequiredService<IServiceScopeFactory>();

using (var serviceScope = serviceScopeFactory.CreateScope())
{
    var dbContext = serviceScope.ServiceProvider.GetService<ApplicationDbContext>();
    dbContext.SetConnectionString(ConnectionStrings.Cs_1);
    dbContext.Database.Migrate();
}
```
