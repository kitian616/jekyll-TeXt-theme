---
title: 'PROJECT | 微博数据采集项目'
date: 2021-04-06 17:17:07
tags: [项目文档,社会计算]
---
![](https://xtopia-1258297046.cos.ap-shanghai.myqcloud.com/%E5%BE%AE%E5%8D%9A%E6%95%B0%E6%8D%AE%E9%87%87%E9%9B%86.jpg)

本文为项目[XWang20/WeiboCrawler](https://github.com/XWang20/WeiboCrawler)的文档。

<!--more-->

# 项目说明

本项目参考了[dataabc/weibo-crawler](https://github.com/nghuyong/WeiboSpider)和[nghuyong/WeiboSpider](https://github.com/dataabc/weibo-crawler)，感谢他们的开源。

## 项目介绍

新浪微博是国内主要的社交舆论平台，对社交媒体中的数据进行采集是舆论分析的重要方法之一。

本采集工具无需cookie，可以连续爬取一个或多个新浪微博用户信息、用户微博及其微博评论转发。

## 采集实例
抓取用户信息
![user](https://xtopia-1258297046.cos.ap-shanghai.myqcloud.com/20210406163110.png)
抓取用户微博
![mblog](https://xtopia-1258297046.cos.ap-shanghai.myqcloud.com/20210406163931.png)
抓取微博转发
![Repost](https://xtopia-1258297046.cos.ap-shanghai.myqcloud.com/20210406164056.png)
抓取微博评论
![Comment](https://xtopia-1258297046.cos.ap-shanghai.myqcloud.com/20210406164127.png)

# 使用方法
## 拉取项目
```
git clone https://github.com/XWang20/WeiboCrawler.git
```

## 安装依赖
本项目Python版本为Python3.8
```
cd WeiboCrawler
python -m pip install -r requirements.txt
```

## 安装数据库（可选）
默认使用MongoDB数据库，可在[settings.py](https://github.com/XWang20/WeiboCrawler/blob/main/WeiboCrawler/settings.py)中修改URL和数据库名，默认为localhost、weibo。

## 运行程序
### 基本程序
抓取用户信息
```
scrapy crawl user
```
抓取用户微博
```
scrapy crawl mblog
```
抓取微博转发
```
scrapy crawl repost
```
抓取微博评论
```
scrapy crawl comment
```

### 自定义选项
1. 采集id和时间范围等信息可根据自己实际需要重写`./WeiboCrawler/spiders/*.py`中的`start_requests`函数。

2. 默认输出到数据库中，若需要输出json或csv文件：在命令后加入`-o *.json`或`-o *.csv`，例如：
```
scrapy crawl user -o user.csv
```

3. 添加账号cookie：可在[settings.py](https://github.com/XWang20/WeiboCrawler/blob/main/WeiboCrawler/settings.py)中添加默认头，或在start_request函数中添加。

4. 默认下载延迟为3，可在[settings.py](https://github.com/XWang20/WeiboCrawler/blob/main/WeiboCrawler/settings.py)修改DOWNLOAD_DELAY。

5. 暂停并恢复爬行：详见 [作业:暂停并恢复爬行 — Scrapy  文档](https://scrapy-16.readthedocs.io/zh_CN/1.6/topics/jobs.html)

# 无cookie版限制的说明
* 单用户微博最多采集200页，每页最多10条
* 单微博评论最多采集两页，约20条
* 单微博转发采集无限制
以上限制都可以通过添加账号cookie解决。

# 设置多线程和代理ip

* 多线程：
在[settings.py](https://github.com/XWang20/WeiboCrawler/blob/main/WeiboCrawler/settings.py)文件中将以下代码取消注释: 
```python
# Configure maximum concurrent requests performed by Scrapy (default: 16)
CONCURRENT_REQUESTS = 100
# The download delay setting will honor only one of:
CONCURRENT_REQUESTS_PER_DOMAIN = 100
CONCURRENT_REQUESTS_PER_IP = 100
```

* 代理ip池
1. 填写[middlewares.py](https://github.com/XWang20/WeiboCrawler/blob/main/WeiboCrawler/middlewares.py)中的`fetch_proxy`函数。
2. 在[settings.py](https://github.com/XWang20/WeiboCrawler/blob/main/WeiboCrawler/settings.py)文件中将以下代码取消注释: 
```python
# Enable or disable downloader middlewares
# See https://doc.scrapy.org/en/latest/topics/downloader-middleware.html
DOWNLOADER_MIDDLEWARES = {
    'WeiboCrawler.middlewares.IPProxyMiddleware': 100,
}
```
3. 在[settings.py](https://github.com/XWang20/WeiboCrawler/blob/main/WeiboCrawler/settings.py)文件中将DOWNLOAD_DELAY设置为0。
```python
DOWNLOAD_DELAY = 0
```

# 字段说明
## 用户信息
* _id: 用户ID
* nick_name: 昵称
* gender: 性别
* brief_introduction: 简介
* mblogs_num: 微博数
* follows_num: 关注数
* fans_num: 粉丝数
* vip_level: 会员等级
* authentication: 认证
* person_url: 首页链接

## 微博信息
* _id: 微博id
* bid: 微博bid
* weibo_url: 微博URL
* created_at: 微博发表时间
* like_num: 点赞数
* repost_num: 转发数
* comment_num: 评论数
* content: 微博内容
* user_id: 发表该微博用户的id
* tool: 发布微博的工具


## 转发信息
* _id: 转发id
* repost_user_id: 转发用户的id
* content: 转发的内容
* mblog_id: 转发的微博的id
* created_at: 转发时间
* source: 转发工具


## 评论信息
* _id: 评论id
* comment_user_id: 评论用户的id
* content: 评论的内容
* mblog_id: 评论的微博的id
* created_at: 评论发表时间
* like_num: 点赞数
* root_comment_id:  根评论id，只有二级评论有该项

# 写在最后
如需交流，欢迎联系：<wangxing@xtopia.fun>
首页封面插画：[Natal Rock Python](https://www.oldbookillustrations.com/illustrations/natal-rock-python/)