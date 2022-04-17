---
title: VanillaJS Challenge (7)
tag: VanillaJS Challenge
---



## 조건

**JS Calculator!**

>  This is a two day challenge.
>
> Based on the section #3
>
> Make a calculator using only Vanilla JS!



**The calculator should:**

1. Have a **reset** (C) button.
2. Support **all** basic operations (+ , - , * , / )
3. Support for **'equals' ( = )** button.
4. Allow value carrying. i.e 2 * 2 * 2 * 2 * 2 **without** pressing equals.
5. Don't give up!



---

## 알고리즘

> 필요한 기능

1. 리셋 버튼과 기능

   저장된 값 삭제

   입력된 값 삭제

2. 사칙연산 버튼과 기능

   +,-,*,/ 버튼 생성

   사칙 연산 함수의 배열 생성

3. 합 버튼(=)과 기능

   입력된 변수를 함수에 대입해서 결과값을 리턴

4. 계산된 값을 저장하는 기능

5. 제곱 연산의 경우 계산값이 리얼타임으로 표시

   제곱 연산의 함수? 매소드를 사용해서 값을 리턴

   

---

## 실행1

### HTML

1. 버튼 만들고 지정하기.

+ `class` 선언

  숫자는 `num`

  사칙연산은 `muti`

  리셋은 `clear`

  =은 `result`

  결과의 출력은 `viewer`

+ [데이터 속성 사용하기]([https://developer.mozilla.org/ko/docs/Learn/HTML/Howto/%EB%8D%B0%EC%9D%B4%ED%84%B0_%EC%86%8D%EC%84%B1_%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0](https://developer.mozilla.org/ko/docs/Learn/HTML/Howto/데이터_속성_사용하기))

  `data-multi`="plus", "minus", "times", "divided by"

  `data-result` 

  `data-num` = 0~9

> why?



+ `id` 선언

  버튼 C, =

   



---

## 실행 2

> `onclick`의 이용

```html
<input id="calculator">
<input id="viewer">


<stript>
	function add(){
    	calculator.value= calculator.value + char;
	}
</stript>
```





---

## 실행 3





```js

let tOf= false; // 2번 연속 연산자면 1, 아니면 0
function add(num){
    if(tOf === false){
        if( overlap(num) === true){

        } else {
            calculator.value += num;
            calculator.value= eval(calculator.value);
        }
    }else{
        calculator.value += num;
        calculator.value= eval(calculator.value);
    }
}
const multi= document.querySelector(".multi");
console.log(multi);

function overlap(num){
    if(num == String){
        tOf= true;
    } else{ 
        tOf= false;
    }
} 
```



### `eval`의 오류

> [HTML + javascript 계산기 만들기 프로젝트!](https://blog.cordelia273.space/32)

```js
var numberClicked = false; // 전역 변수로 numberClicked를 설정
    function add (char) {
        if(numberClicked == false) { 
            if(isNaN(char) == true) { 
               
            } else { 
                document.getElementById('display').value += char;
            }
        } else {
            document.getElementById('display').value += char; 
        }
 
 
        
        if(isNaN(char) == true) { 
            numberClicked = false; 
        } else {
            numberClicked = true; 
        }
    }
```

> [javascript 계산기 / 자바스크립트 계산기 만들기](https://cofs.tistory.com/209)

```js
   if (isNaN(su))  
    {
        flag2++;
    }else{
        flag2 = 0;
        }
        
    if (flag2 >1)  
    {
        return; 
    }
    f.disp.value += su;   
}
```

_inNaN function으로 숫자인지 문자열인지 판단_

_count 변수를 만들어서 연산자가 입력된 수를 제어_

> 완성

```js
function add(num){

    if(isNaN(num) == true){
        tofCount++;     
    } else{ 
        tofCount=0;
    }

    if(tofCount > 1){
        return tof=true;
    } 

    if(tOf == false){
        claculation(num);
        calculator.value= eval(calculator.value);
    } else {   
        return;
    }
}
```



---

### viewer에 string이 보여진다는 것의 해결

예시에서 곱셈일 경우 

2(2) , *(2) ,2(4), *(4), 2(8) 이런식으로 작동함



> [HTMLJavaScript-계산기-만들기](https://olsh1108o.tistory.com/entry/HTMLJavaScript-계산기-만들기)



---

## 실행4

### HTML

`onclikc`으로 실행시킬 함수 5개

● [HTML_17Line] clearAll() : 설정된 변수 및 text value 초기화

● [HTML_19Line] getResult() : 입력된 값 계산 후 result 변수에 저장

● [HTML_24Line] selectedBtn(id) : 선택된 number 버튼의 값을 매개변수로 전달.

● [HTML_30Line] selectedOp(op) : 선택된 operation 버튼의 값을 매개변수로 전달.

● [HTML_45Line] mathText(text) : 선택된 버튼의 값을 매개변수로 전달.

   

### JS



---

## 정답 해설



## HTML

`class` result result, reset reset, number, operation operation, number zero ,equals



   

## JS

```js
const result = document.querySelector(".js-result");
const reset = document.querySelector(".js-reset");
const equals = document.querySelector(".js-equals");
const numbers = Array.from(document.querySelectorAll(".js-number"));
const operations = Array.from(document.querySelectorAll(".js-operation"));

let firstValue = "",
  firstDone,
  secondValue = "",
  secondDone,
  currentOperation;
```

​      

> Array.form

The `**Array.from()**` method creates a new, shallow-copied `Array` instance from an array-like or iterable object.

_`numbers` `oprations`는 배열로 반환되는데 js-number / js-operation 의 원소를 가짐.

   

> [Arrow function expression](https://poiemaweb.com/es6-arrow-function)	//화살표 함수

```js

    () => { ... } 
     x => { ... } // 매개변수가 한 개인 경우, 소괄호를 생략할 수 있다.
(x, y) => { ... } // 매개변수가 여러 개인 경우, 소괄호를 생략할 수 없다.

// 함수 몸체 지정 방법
x => { return x * x }  // single line block
x => x * x             // 함수 몸체가 한줄의 구문이라면 중괄호를 생략할 수 있으며 암묵적으로 return된다. 위 표현과 동일하다.

() => { return { a: 1 }; }
() => ({ a: 1 })  // 위 표현과 동일하다. 객체 반환시 소괄호를 사용한다.

() => {           // multi line block.
  const x = 10;
  return x * x;
};
```

   

> 사칙연산

```js
switch (currentOperation) {
    case "+":
      return intValueA + intValueB;
    case "-":
      return intValueA - intValueB;
    case "/":
      return intValueA / intValueB;
    case "*":
      return intValueA * intValueB;
    default:
      return;	
```

   

> 외부 실행문

```js
numbers.forEach(function(number) {
  number.addEventListener("click", handleNumberClick);
});
operations.forEach(function(operation) {
  operation.addEventListener("click", handleOperationClick);
});
reset.addEventListener("click", handleReset);
equals.addEventListener("click", handleEqualsClick);
```

숫자 클래스의 원소에 대해서 각각 실행.

클릭당하면 handleNumberClick실행

```js
function handleNumberClick(e) {
  const clickedNum = e.target.innerText;
  if (!firstDone) {
    firstValue = firstValue + clickedNum;
    result.innerHTML = firstValue;
  } else {
    secondValue = secondValue + clickedNum;
    result.innerHTML = secondValue;
    secondDone = true;
  }
}
```

​    

오퍼레이션 클래스의 원소들에 대해서 각각 실행

```js
function handleOperationClick(e) {
  const clickedOperation = e.target.innerText;
  if (!firstDone) {
    firstDone = true;
  }
  if (firstDone && secondDone) {
    calculate();
  }
  currentOperation = clickedOperation;
}
```

첫번째 값의 매개변수

첫번째 값 && 두번째 값을 입력 받았다면 계산

   

```js
function handleReset() {
    ...
  result.innerHTML = "0";	
}
```

   

```js
function handleEqualsClick() {
  if (firstDone && secondDone) {
    calculate();	// `=` click
  }
}
```



