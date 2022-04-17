---
title: enumerate
tag: python
---



## 타입 검사

iterable 객체를 판별하기 위해서는 아래의 방법이 있습니다.

```
import collections

# iterable한 타입
list = [1,3,5,7]
isinstance(list, collection.Iterable)

# 종류
dict = {}
set = {}
str = ""
bytes = b'abcdef'
tuple = ()
range = range(0,5)
```

- type(list)는 안될까?

## range

## enumerate

