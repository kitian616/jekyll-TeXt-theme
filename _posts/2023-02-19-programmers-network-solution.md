---
layout: single
title:  "[Programmers] 네트워크"
categories: Programmers
tag: [python, algorithm, dfs/bfs]
toc: true
toc_sticky: true
author_profile: true
excerpt: "프로그래머스 네트워크 문제풀이"

---

프로그래머스 코팅테스트 연습문제를 푸는 과정에서 

굉장히 깔끔하게 풀린 문제가 있어서 풀이과정을 공유하고자 작성하게 되었다!

# 1. 문제

문제링크 : [https://school.programmers.co.kr/learn/courses/30/lessons/43162](https://school.programmers.co.kr/learn/courses/30/lessons/43162){:target="_blank"}

먼저 입력은 컴퓨터의 개수 `n` 과 컴퓨터 간 연결 관계에 대한 리스트 `computers` 가 주어지고 

두 정보를 이용하여 네트워크의 개수 즉, 컴퓨터가 몇 묶음으로 연결되어있는지를 출력해야 한다. 

# 2. 접근 방법
이 문제를 해결하기 위해서는 `computers[i]` 를 통해 i 번째 컴퓨터와 연결되어있는 컴퓨터의 정보를 얻고 

만약 j 번째 컴퓨터와 연결되어있다면 또한 `comuputers[j]` 를 통해 연결된 컴퓨터 정보를 얻어 i 번째 컴퓨터가 j 번째 컴퓨터를 거쳐 어디까지 네트워크를 형성하고있는지 파악해야하는 **dfs** 유형의 문제이다.

우선 dfs를 수행할 함수를 정의해야 하는데 그 파라메터를 `i 번째 컴퓨터` 와 `i 번째 컴퓨터와 연결된 컴퓨터 정보` 로 설정해야 i 번째 컴퓨터와 어떤 컴퓨터가 연결되어있다면 그 컴퓨터와 그 컴퓨터에 대한 정보를 재귀함수로 넘겨서 진행할 수 있다.

# 3. 문제 풀이


```python
def solution(n, computers):
    visited = [0] * n
    answer = 0
    def connect(i,coms):
        for j in range(n):
            if i != j and coms[j] == 1 and visited[j] == 0:
                visited[j] = 1
                connect(j,computers[j])             
    for i in range(n):
        if visited[i] == 0:
            connect(i,computers[i])
            answer += 1

    return answer
```

`connect(i,coms)` 를 정의했고 여기서 `i` 는 몇 번째 컴퓨터인지 `coms` 는 그 컴퓨터에 대한 연결 정보. 즉`computers[i]` 를 의미한다.

여기서 중요한 것은 `visited` 라고 정의한 리스트이다. 

이 리스트는 이 컴퓨터가 네트워크에 연결되었는지를 확인하는 리스트인데,

`visited[1] == 1` 이라면 1 번째 컴퓨터는 어떤 네트워크에 연결되었는지는 모르지만 어디든 일단 속해있다는 뜻이다.

이 리스트를 통해 **재귀함수가 무한으로 반복되는 것을 방지** 하고 **네트워크의 개수를 계산** 할 수 있다.

네트워크의 개수가 계산되는 과정은  

for문을 통해 처음 `connect(0,computers[0])` 이 실행되면 0 번째 컴퓨터와 연결된 컴퓨터들은 if 문의 조건들을 모두 만족하기 때문에 `visited`의 값이 모두 `1` 이 된다.

그렇게되면 for문이 돌아도 if 문에 막혀서 `connect()` 를 호출하지 못하는 `i` 값이 생기고

**0 번째 컴퓨터와 연결되지 않은 컴퓨터의 i 값** 만  `connect()` 함수를 호출하게 된다.
