---
title: "Sentry 部署小笔记"
subtitle: '使用 Docker 部署 Sentry'
tags:
  - Sentry
---

Update:Sentry 部署笔记

---

## Sentry 部署笔记
### 1. Sentry 依赖
&emsp;&emsp;使用 docker 部署 Sentry 目标主机需要安装python、docker和docker-compose。

**硬件要求**

|资源|大小|描述|
|:---:|:---:|:---:|
|内存|最少2GB|推荐4GB|

**软件要求**

|软件|版本|描述|
|:---:|:---:|:---:|
|Python|2.7或更高版本|[Python安装指南](https://www.centos.bz/2018/01/在centos上安装python3的三种方法/)
|Docker engine|1.10或更高版本|[Docker安装指南](https://docs.docker.com/install/linux/docker-ce/centos/)|
|Docker Compose|1.6.0或更高版本|[Docker Compose 安装指南](https://docs.docker.com/compose/install/)|
|Openssl|推荐最新版本|为Harbor生成证书和密钥|
|Docker image postgres|9.6|
|Docker image redis|3.2-alpine|

### 2. 安装
-  **启动 redis 容器** <br>
    ```
    docker run -d --name sentry-redis redis
    ```
    <br>
-  **启动 postrgre 容器** <br>
    ```
    docker run -d --name sentry-postgres -e POSTGRES_PASSWORD=secret -e POSTGRES_USER=sentry postgres
    ```
    <br>
-   **生成 sentry 密钥，用于后面连接容器，要记住** <br>
    ```
    docker run --rm sentry config generate-secret-key
    ```
    <br>
-  **连接 redis，postgres 和 sentry， 将下面命令中的 secret_key 替换成上一步中生成的密钥** <br>
    ```
    docker run -it --rm -e SENTRY_SECRET_KEY='<secret-key>' --link sentry-postgres:postgres --link sentry-redis:redis sentry upgrade
    ```
    这一步会在数据库中初始化 sentry 数据，具体花费时间取决于极其性能，我用的是阿里云1核1G的服务器，这一步跑了差不多一个小时。<br>
- **启动 Sentry Server，同时添加端口映射。Sentry的端口为9000，可以使用 -p 9000:9000 参数，在启动后可以通过访问http://localhost:9000或http://host-ip:9000进入Sentry的web管理页面：**
    ```
    docker run -d --name my-sentry -p 9000:9000 -e SENTRY_SECRET_KEY='<secret-key>' --link sentry-redis:redis --link sentry-postgres:postgres sentry
    ```
    <br>
-  **启动 worker 和 cron** <br>
    ```
    docker run -d --name sentry-worker-1 -e SENTRY_SECRET_KEY='<secret-key>' --link sentry-postgres:postgres --link sentry-redis:redis sentry run worker
    ```
    <br>
    
    ```
    docker run -d --name sentry-worker-1 -e SENTRY_SECRET_KEY='<secret-key>' --link sentry-postgres:postgres --link sentry-redis:redis sentry run worker
    ```

