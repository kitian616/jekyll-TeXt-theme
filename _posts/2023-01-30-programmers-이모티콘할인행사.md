---
layout: single
title:  "[Programmers] 이모티콘 할인행사"
categories: Programmers
tag: [python, algorithm]
toc: true
toc_sticky: true
author_profile: true
excerpt: "프로그래머스 이모티콘 할인행사"
---

# 1. 문제
문제링크 : [https://school.programmers.co.kr/learn/courses/30/lessons/150368](https://school.programmers.co.kr/learn/courses/30/lessons/150368){:target="_blank"}

문제 설명이 매우 복잡하게 느껴졌지만 

사용자들의 할인율 조건(?)과 이모티콘 플러스를 가입하기 전까지의 한도금액 정보와

이모티콘의 금액 정보를 통해 각 이모티콘의 할인율을 조정하여

1. 이모티콘 플러스의 가입자를 최대한 늘리는 것
1. 이모티콘 판매액을 최대한 늘리는 것

이 두 조건을 만족하는 `[이모티콘 플러스의 가입자 수, 이모티콘 판매액]` 를 return 해야한다

# 2. 접근 방법

우선 `사용자 수`, `각 사용자의 할인 조건`, `각 사용자의 구매 한도`, 등 변수로 지정할 것이 많기에 

나름대로의 네이밍으로 변수를 지정해주었다
```python
n_users = len(users)            # 유저 수
cond_list = []                  
limit_list = []                 
n_sub = 0                       # 이모티콘 플러스 가입자 수 
n_emo = len(emoticons)          # 이모티콘 수 
dis_emo_list = [40] * n_emo     # 각 이모티콘 별 할인율
price_list = [0] * n_users      # 각 사용자의 구매 총합

for user in users:
    cond_list.append(user[0])   # 각 사용자의 이모티콘을 구매하는 할인 조건 리스트
    limit_list.append(user[1])  # 각 사용자의 구매 비용 한도 (넘을시 이모티콘플러스 서비스 가입)
```

우선 조건이 이모티콘 플러스의 가입자를 최대한 늘리는 것이기 때문에 우선 사용자들이 

이모티콘을 구매를 해야한다고 생각했고 따라서 모든 이모티콘의 할인율을 40% 로 지정한 후 

어떠한 기준을 통해 할인율을 조정하며 최적의 할인율 조합을 찾아야 한다고 생각하였다

하지만 그 **어떠한 기준** 을 찾는 것이 어렵다고 판단하였고

이모티콘의 개수도 7개로 제한되어있었기 때문에 할인율의 모든 경우의 수에 따라 

이모티콘 플러스의 가입자 수와 그 때의 매출액을 계산해보았다.

# 3. 문제 해결

```python
def solution(users, emoticons):

    n_users = len(users) # 유저 수
    cond_list = [] # 각 사용자의 이모티콘을 구매하는 할인 조건 리스트
    limit_list = [] # 각 사용자의 구매 비용 한도 (넘을시 이모티콘플러스 서비스 가입)
    n_sub = 0 # 이모티콘 플러스 가입자 수 
    n_emo = len(emoticons) # 이모티콘 수 
    dis_emo_list_c = list(itertools.product(([10,20,30,40]), repeat = n_emo)) # 각 이모티콘 별 할인율
    price_list = [0] * n_users # 각 사용자의 구매 총합

    for user in users:
        cond_list.append(user[0])
        limit_list.append(user[1])

    take = 0
    for dis_emo_list in dis_emo_list_c:
        price_list = [0] * n_users # 각 사용자의 구매 총합
        n_sub_tmp = 0
        for i,price in enumerate(emoticons):
            for user in range(n_users):
                if cond_list[user] <= dis_emo_list[i]:
                    price_list[user] += price * (1- dis_emo_list[i]/100)

        for user in range(n_users):
            if limit_list[user] <= price_list[user]:
                n_sub_tmp += 1
                price_list[user] = 0
    
        if n_sub < n_sub_tmp:
            n_sub = n_sub_tmp
            take = sum(price_list)
        elif n_sub == n_sub_tmp:
            take = max(take,sum(price_list))
        
    return [n_sub,int(take)]
solution([[40, 10000], [25, 10000]]	,[7000, 9000])
```
최종코드는 이렇게 된다.

실제 우리가 머리 속으로 계산하는 순서 그대로 코드로 구현하였다. 

`cond_list[user]` (할인율 조건) 이 `dis_emo_list[i]` (할인율) 보다 낮을 시 할인율을 계산하여 

사용자의 구매 총합 리스트에 더해주면 반복문이 다 끝나고 난뒤 `price_list` 에는 각 사용자 별 지불해야 하는 비용이 남게 된다.

만약 그 값이 각 `limit_list` 를 넘는다면 가입자 수를 `+=1` 해주고 모든 구매를 취소하기 위해 해당하는 사용자의 

`price_list` 를 `0` 으로 초기화 하였다. 

이 때 각 `가입자 수` 와 `총 매출액` 의 조합을 리스트에 추가하여 여기서 다시 반복문을 통해 최적의 조합을 찾을 수도 있지만

반복문을 최소화하기 위해 모든 할인율의 조합에 대해 가입자수와 매출액을 계산하는 동시에 최대값을 갱신해 주는 방식으로 

코드를 작성하였다. 

itertools 라이브러리를 이용하여 이모티콘의 할인율에 대한 모든 조합을 구해 계산하는 것이 이 문제의 key였던 것 같다. 



# 4. 다른 사람의 풀이 리뷰

문제를 맞추긴 했지만 썩 잘 짠 코드는 아니라고 생각하여 다른 사람의 풀이 중 가장 좋아요를 많이 받은 분의 코드를 

내 코드와 비교하며 리뷰하려고 한다.

```python
from itertools import product

def solution(users, emoticons):
    E = len(emoticons)
    result = [0, 0]
    percents = (10, 20, 30, 40)
    prod = product(percents, repeat=E)

    for p in prod:
        prod_members, prod_price = 0, 0
        for buy_percent, max_price in users: 
            user_price = 0
            for item_price, item_percent in zip(emoticons, p):
                if item_percent >= buy_percent:
                    user_price += item_price * (100-item_percent) * 0.01

            if user_price >= max_price:
                prod_members += 1
            else:
                prod_price += user_price

        result = max(result, [prod_members, prod_price])

    return result
```

필자는 변수 초기화와 계산하는 코드를 분리한 반면 이 분은 변수 초기화를 따로 하지 않고 바로 본 코드에 들어가신 것 같다. 

또한 필자는 `enumerate()` 함수를 통해 이모티콘의 가격과 할인율을 매칭시켰지만 이 분은 `zip()` 함수를 사용하여 매칭 시켰다. 

다음부턴 더 가독성이 좋은 코드를 작성해야겠다!




