---
title:  "hey for load testing http applications"
date: 2022-10-25
toc: true
mermaid: true
categories: [Commandline]
tags: 
  - cli
  - macos
  - devops
---

**short**

`hey` is a HTTP load tester CLI tool to benchmark HTTP requests to a HTTP end-point. Get it at [GitHub](https://github.com/rakyll/hey)

---

Today we will be taking a look at a small utility called `hey`. You can use `hey` to load test HTTP applications or generate load for a web application. This comes in handy when you want to simulate use or check what your app does when it receives 1000s of requests.

**Warning**: Using a load test on website that you do not own or have permission to test can result in you being banned or blocked
{:.error}

# Installation

Installation on macOS with `brew` is really easy, just run:

```bash
brew install hey
```

For other installation options, check out: [Hey Installation](https://github.com/rakyll/hey#installation)

# Usage

So, we are going to run this against a local docker container, just to be sure that we don't mess with anyone's website.

## Setting up a container

This is pretty straight forward. Start Docker and run the following command to start it in the background:

```bash
$ docker run --name webserver -p 8080:80 -d nginx
```

To make sure it's running we can check using the `docker ps` command:

```bash
$ docker ps
CONTAINER ID   IMAGE     COMMAND                  CREATED         STATUS         PORTS                                   NAMES
c2c829348c89   nginx     "/docker-entrypoint.…"   3 seconds ago   Up 3 seconds   0.0.0.0:8080->80/tcp, :::8080->80/tcp   webserver
```

Now you can also start a `tail` on the container to see your load test in action:

```bash
docker logs -f webserver
```

Example output:

```bash
172.17.0.1 - - [25/Oct/2022:15:12:18 +0000] "GET / HTTP/1.1" 200 615 "-" "hey/0.0.1" "-"
172.17.0.1 - - [25/Oct/2022:15:12:18 +0000] "GET / HTTP/1.1" 200 615 "-" "hey/0.0.1" "-"
172.17.0.1 - - [25/Oct/2022:15:12:18 +0000] "GET / HTTP/1.1" 200 615 "-" "hey/0.0.1" "-"
```

## Using hey

So let's get testing. `hey` supports some great arguments like `-n` (number) the amount of requests to send, `-c` (concurrently) for the number of workers to run concurrently and `-z` (Duration) to perform a test over *x* time.
All options can be found on [Hey Usage](https://github.com/rakyll/hey#usage)

## Examples

I'll be showing some common usages

### Example 1: One hundred (100) requests

Let's fire up some requests. First up, 100 request using 5 concurrent workers:

```bash
$ hey -n 100 -c 5 http://localhost:8080
```

Results:

```yaml
Summary:
  Total:	0.0448 secs
  Slowest:	0.0089 secs
  Fastest:	0.0010 secs
  Average:	0.0022 secs
  Requests/sec:	2231.3041

  Total data:	61500 bytes
  Size/request:	615 bytes

Response time histogram:
  0.001 [1]	|■
  0.002 [34]	|■■■■■■■■■■■■■■■■■■■■■■■
  0.003 [60]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
  0.003 [0]	|
  0.004 [0]	|
  0.005 [0]	|
  0.006 [0]	|
  0.007 [0]	|
  0.007 [0]	|
  0.008 [0]	|
  0.009 [5]	|■■■


Latency distribution:
  10% in 0.0016 secs
  25% in 0.0017 secs
  50% in 0.0019 secs
  75% in 0.0021 secs
  90% in 0.0022 secs
  95% in 0.0087 secs
  99% in 0.0089 secs

Details (average, fastest, slowest):
  DNS+dialup:	0.0002 secs, 0.0010 secs, 0.0089 secs
  DNS-lookup:	0.0002 secs, 0.0000 secs, 0.0041 secs
  req write:	0.0000 secs, 0.0000 secs, 0.0002 secs
  resp wait:	0.0019 secs, 0.0009 secs, 0.0038 secs
  resp read:	0.0000 secs, 0.0000 secs, 0.0002 secs

Status code distribution:
  [200]	100 responses
```

### Example 2: Five (5) requests with csv output

Dump the results to `csv` output:

```bash
$ hey -n 1 -c 1 -o csv http://localhost:8080
```

Result:

```csv
response-time,DNS+dialup,DNS,Request-write,Response-delay,Response-read,status-code,offset
0.0087,0.0047,0.0042,0.0001,0.0019,0.0001,200,0.0016
```

### Example 3: Thirty (30) seconds of requests

Now we are going to use the duration (`-z`) option:

```bash
$ hey -z 30s http://localhost:8080
```

Results:

```bash
Summary:
  Total:	30.0082 secs
  Slowest:	0.1231 secs
  Fastest:	0.0010 secs
  Average:	0.0070 secs
  Requests/sec:	7096.5514

  Total data:	130967325 bytes
  Size/request:	615 bytes

Response time histogram:
  0.001 [1]	|
  0.013 [199692]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
  0.025 [12165]	|■■
  0.038 [868]	|
  0.050 [159]	|
  0.062 [47]	|
  0.074 [11]	|
  0.086 [9]	|
  0.099 [1]	|
  0.111 [1]	|
  0.123 [1]	|


Latency distribution:
  10% in 0.0035 secs
  25% in 0.0045 secs
  50% in 0.0061 secs
  75% in 0.0085 secs
  90% in 0.0115 secs
  95% in 0.0140 secs
  99% in 0.0212 secs

Details (average, fastest, slowest):
  DNS+dialup:	0.0000 secs, 0.0010 secs, 0.1231 secs
  DNS-lookup:	0.0000 secs, 0.0000 secs, 0.0069 secs
  req write:	0.0000 secs, 0.0000 secs, 0.0025 secs
  resp wait:	0.0069 secs, 0.0009 secs, 0.1230 secs
  resp read:	0.0001 secs, 0.0000 secs, 0.0431 secs

Status code distribution:
  [200]	212955 responses
```

### Limiting requests 

Now, as you can see, we just fired off 212955 requests. That might be a bit of overkill. To prevent this, we can use the `-q` (Rate limit) and `-c` option. We will perform a load test of 5 seconds and use `-c` to limit ourselves to 2 workers, and we will set a limit of 5 request per second per worker:

```bash
$ hey -z 5s -c 2 -q 5 http://localhost:8080
```

This results in 50 requests being made:

```bash
...

Status code distribution:
  [200]	50 responses
```

# Wrapping up

So that's `hey`. A super simple HTTP load tester. You can use `hey` to do some advanced things like posting code, testing authentication and other things. Take a look at the [readme](https://github.com/rakyll/hey)

