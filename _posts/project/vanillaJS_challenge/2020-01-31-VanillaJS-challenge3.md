---
title: VanillaJS Challenge (3)
tag: VanillaJS Challenge
---



## 조건

+ Using the boilerplate, make an app that shows the **time until Christmas Eve** in days, hours, minutes and seconds.

- [Date() documentation](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Date)﻿.
- Keep in mind: new Date() might not in KST (Korean Time), if then you have to fix that.
- **(fix) 2020년 기준으로 진행해주시면 됩니다!**



---

## 알고리즘

1. 현재 시간 구하기
2. 2020년 크리스마스 이브 00시 구하기
3.  2)에서 1)를 빼기 
4. 뺀 값에서 1초를 뺌(그리고 1초마다 갱신)
5. 뺀 값이 0보다 작으면 함수를 삭제



---

## 실행

[Javascript 타이머 만들기](https://basketdeveloper.tistory.com/4)

+ 현재와  크리스마스 이브 시간 가져오기 

> getTime

1970년 1월 1일 자정을 기준

현재시간을 숫자로 나열한 것

나열된 시간은 millisecond. 1/1000초

+ `millisecond`를 `day`, `hours`, `min`, `sec`로 변환

```js
const day=Math.floor(substraction / (1000*60*60*24)),
 hours = Math.floor((RemainDate % (1000 * 60 * 60 * 24))/(1000*60*60)),
 min = Math.floor((RemainDate % (1000 * 60 * 60)) / (1000*60)),
 sec = Math.floor((RemainDate % (1000 * 60)) / 1000);
```

+  남은 시간이 0보다 작다면 타이머를 해제하고 그렇지 않으면 -1초

```js
 if (RemainDate < 0) {      
    // 시간이 종료 되었으면..
    clearInterval(tid);   // 타이머 해제
  }else{
    RemainDate = RemainDate - 1000; // 남은시간 -1초
  }
```

> [Date 메소드](http://tcpschool.com/javascript/js_standard_dateMethod)



---

## 정답 해설

+ Date() object 만들어 변수에 저장
+ `뺀 값`을 다시 Date() obj을 만듬

|  `milisecond`  | `시간 단위`  |
| :------------: | :----------: |
| Sm= 뺀 값/1000 | Sec= Sm % 60 |
|   Mm =Sm/60    | Min= Mm % 60 |
|   Hm= Mm/60    |  H= Hm % 60  |
|   Dm= Hm/24    |      ""      |

+ `getTime`을 쓰지 않음.





