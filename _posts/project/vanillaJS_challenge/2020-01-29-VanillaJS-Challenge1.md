---
title: VanillaJS Challenge (1)
tag: VanillaJS Challenge
---

​						

## 01-29 Quiz

What is an expression on JavaScript?

> **It's an instruction**

How does JavaScript execute our code? 

> **From top to bottom**



---

## Based on the videos #2.1 - #2.4	

```
console.log();
```

`console`이라는 `object` `log`라는 `key`가 있음 

`built-in function`

`argument` 우리가 주는 값을 저장하는 값임. like `variable`

`back tick` : `

```
console.log(`Hello ${name} lksadlk`);
```

`querySelector`

모든 자신들 중에서 첫 원소를 찾으려고 할꺼야

---

## 과제

### 조건

1. The text of the title should change when the mouse is **on top** of it.
2. The text of the title should change when the mouse is **leaves** it.
3. When the window is **resized** the title should change.
4. On **right click** the title should also change.
5. The colors of the title should come from a color from the **colors** array.
6. **DO NOT** **CHANGE** **.css,** or **.html** files.
7. **ALL** function handlers should be **INSIDE** of **"superEventHandler"**

> `h1` 클래스에 변화를 주어야함. String과 color의

+ 기본 hello
+ 마우스 인 The muse is here!
+ 마우스 아웃 The muse is gone! 
+ 좌클릭 That was a right click!
+ 윈도우창의 변화 U just resized!



## 분할

`innerHTML`로 string의 변화 

`style.color`로 color의 변화 

`window.addEventListener("resize")`로 윈도우창의 변화 이벤트

`addEventListener("rightClick")` 좌클릭 시 이벤트 발생

if 마우스 인  mouseup

else 마우스 아웃





