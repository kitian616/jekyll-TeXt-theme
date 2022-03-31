---
tags: Java Spring
categories:
  - cs
  - lang
  - java
---



# 使用Spring提供的JdbcDaoSupport

在传统的多层应用程序中，通常是Web层调用业务层，业务层调用数据访问层。业务层负责处理各种业务逻辑，而数据访问层只负责对数据进行增删改查。因此，实现数据访问层就是用`JdbcTemplate`实现对数据库的操作。



ORM：Object-Relational Mapping。这种把关系数据库的表记录映射为Java对象的过程