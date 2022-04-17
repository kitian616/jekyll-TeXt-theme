---
title: Code Coverage
tag: TDD
---



## 코드 커버리지

소프트웨어 테스트가 "잘", "성공적으로" 수행되었는지를 어떻게 확인할 수 있을까요?

### 정의

코드 커버리지란, 테스트 수행 결과를 정량적인 수치로 나타내는 방법

= 소프트웨어를 이루는 소스 코드(테스트 대상) 중 테스트를 통해 실행된 코드의 비율을 뜻합니다.

소스 코드를 구분할 때는 크게 구문(Statement), 조건(Condition), 결정(Decision)의 요소로 합니다. 

- 구문(Statement)은 Line(줄)과 비슷하고, 

- 조건(Condition)은 "x<0"과 같은 조건식, 

- 결정(Decision)은 조건으로 인해 나올 수 있는 결과 값입니다.

###  구문

일반적으로 많이 사용되는 커버리지는 바로 구문(Statement) 커버리지입니다.

예시에서, 10 라인 중 else 구문이 수행되지 않았다면 커버리지는 83.33%가 됩니다.

계산식: (5개 line 수행) / (6개 line 대상) * 100 = 83.33%

### 조건

조건(Condition) 커버리지는 조건에 대한 참 또는 거짓이 수행되면 커버되었다고 합니다.

예시에서, 10 라인 중 else 구문이 수행되지 않았다면 커버리지는 50%가 됩니다.

계산식: (1개 조건(참) 수행) / (2개 조건 대상) * 100 = 50%

### 결정

결정(Decision) 커버리지는 각 분기의 결과에 대한 참 또는 거짓이 수행되면 커버되었다고 합니다.

예시에서, 10 라인 중 else 구문이 수행되지 않았다면 커버리지는 50%가 됩니다.

계산식: (1개 결정(참) 수행) / (2개 결정 대상) * 100 = 50%

![코드 커버리지 종류](https://mblogthumb-phinf.pstatic.net/MjAxNjEwMjFfMjAz/MDAxNDc3MDQyNzM4OTQy.uRlYllVzUqBOFDf_dakhUI8QHFGYiko_JA_-o5bQFVMg.XlHA7UGMY4M2hPNKe5eW-2IWkuunAd52aYoHUs-xcIog.JPEG.suresofttech/1.png?type=w800)

---

## 유닛 테스트

유닛 테스트는 특정 모듈(함수 or 특정 코드)이 내가 원하는 바 대로 정확히 작동하는지 검증하는 절차입니다.

즉, 모든 함수와 메소드에 대한 테스트 케이스를 작성하는 절차를 말합니다.

우리는 이를 자동화하고 반복할 수 있게 하여 코드가 수정되어 문제가 발생하였을 경우에 이를 빠르게 찾고 수정할 수 있도록 해주고, 유닛 테스트를 통과했다면 해당 모듈이 잘 작동하고 있음을 확신하고 코드를 작성할 수 있게 해줍니다.



[참고 1](http://www.secmem.org/blog/2019/07/21/test-coverage/)

----

 HLD(High Level Design)/DLD(Detailed Level Design)

SEQUENCE DIAGRAM