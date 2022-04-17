---
title: Logical operators, Bitwise operators
tag: cpp
---



## 논리 연산자

특정 조건이 `참(true)`인지 `거짓(false)`인지를 테스트할 수 있지만 한 번에 하나의 조건만 테스트할 수 있다. 

그러나 한 번에 여러 가지 조건을 테스트 해야 하는 경우가 있다. 즉, 다중 조건이 참인지 아닌지를 확인해야 한다.

C++에는 3가지 논리 연산자가 있다.

| Operator    | Symbol | Form    | Operation                                       |
| ----------- | ------ | ------- | ----------------------------------------------- |
| Logical NOT | !      | !x      | true if x is false, or false if x is true       |
| Logical AND | &&     | x && y  | true if both x and y are true, false otherwise  |
| Logical OR  | l l    | x l l y | true if either x or y are true, false otherwise |

---

### 논리 부정 (Logical NOT)

논리 부정 연산자( `!`) 연산자가 피연산자 `true`를 평가하면 `false`가 된다. 

반대로, `false`를 평가하면 `true`가 된다. 즉, **논리 부정 연산자(`!`)는 부울(bool) 값을 반전시킨다.**



### 논리 OR (Logical OR)

논리합, 

**논리 OR 연산자는 두 조건 중 하나가 참(true)인지 테스트하는 데 사용한다.** 

왼쪽 피연산자가 `true`로 평가되거나, 오른쪽 피연산자가 `true`로 평가되면 논리 OR 연산자(`||`)는 `true`를 반환한다.

둘 중 하나라도 `true`이면 논리 OR 연산자(`||`)는 `true`로 평가된다.



### 논리 AND (Logical AND)

논리곱,

**논리 AND 연산자는 두 조건이 모두 참인지 여부를 테스트하는 데 사용한다.** 

두 조건이 모두 `참(true)`이면 논리 AND 연산자(`&&`)는 `참(true)`을 반환한다. 그렇지 않으면 `거짓(false)`을 반환한다.



### Short circuit evaluation

단락 평가, 

논리 AND 연산자(&&)가 true로 평가되려면 두 피연산자가 모두 true여야 한다. 

만약 첫 번째 피연산자가 false로 평가되면, 논리 AND 연산자는 두 번째 피연산자가 true인지 false인지와 관계없이 false를 반환해야 한다는 것을 알 된다. 

그러므로 논리 AND 연산자는 즉시 false를 반환한다. 

이 경우를 **단락 평가(short circuit evaluation)**라고 하며 주로 최적화 목적으로 수행된다.

---

[정리: 소년코딩](https://boycoding.tistory.com/162?category=1007523)

---

## 비트 단위 연산자

