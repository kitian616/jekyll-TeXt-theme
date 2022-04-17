---
title: Calculator (2)
tag: VanillaJS Challenge
---



## 기능

입력받은 숫자와 연산자를 변수로 나누어 3개의 변수를 생성한 뒤 숫자 변수 두개와 연산자 변수 한개를 통합해서 연산하는 방법

- [x] 사칙 연산
- [x] 연산자 중복 방지
- [x] 결과 값의 출력
- [x] 계산기 디자인
- [x] 루트, 로그
- [x] 제곱

> ver0.1과의 차이점

ver0.2는 결과창에 연산자가 출력되지 않음.

루트와 로그 기능 구현



---

## 디자인

> HTML table태그

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <link rel="stylesheet" href="index.css">
        <title>Calculator</title>
    </head>
    <body>
        <table style="padding: 3%; padding-top: 4%;">
            <tr>
                <td colspan="4">
                    <form class="result" inputmode="text">
                    0 </form>
                </td>
            </tr>

            <tr>
                <td>
                    <input class="btn multi" type="button" value="log" >
                </td>
                <td>
                    <input class="btn multi" type="button" value="√" >
                </td>
                <td>
                    <input class="btn multi" type="button" value="^">
                </td>
                <td>
                    <input class="btn reset" type="button" value="Ⅽ" >
                </td>
            </tr>

            <tr>
                <td>
                    <input class="btn num" type="button" value="7" >
                </td>
                <td>
                    <input class="btn num" type="button" value="8" >
                </td>
                <td>
                    <input class="btn num" type="button" value="9">
                </td>
                <td>
                    <input class="btn multi" type="button" value="+">
                </td>
            </tr>
    
            <tr>
                <td>
                    <input class="btn num" type="button" value="4" >
                </td>
                <td>
                    <input class="btn num" type="button" value="5" >
                </td>
                <td>
                    <input class="btn num" type="button" value="6">
                </td>
                <td>
                    <input class="btn multi" type="button" value="-">
                </td>
            </tr>
    
            <tr>
                <td>
                    <input class="btn num" type="button" value="1">
                </td>
                <td>
                    <input class="btn num" type="button" value="2">
                </td>
                <td>
                    <input class="btn num" type="button" value="3">
                </td>
                <td>
                    <input class="btn multi" type="button" value="*">
                </td>
            </tr>
    
            <tr>
                <td colspan="2">
                    <input class="btn num" type="button" value="0" style="width: 100%;">
                </td>
                
                <td>
                    <input class="btn equals" type="button" value="=" style="background-color: antiquewhite; border-width: 8px; border-bottom-color: white;">
                </td>
                <td>
                    <input class="btn multi" type="button" value="/ ">
                </td>
            </tr>
    
        </table>

        

    <script src="index.js"></script> 
    </body>
</html>

```

   

## CSS

```css
html{
    background-color: white;
    color: white;
    background: url(src/1.jpg) no-repeat center center fixed;
    -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}

.btn{
    width: 80pt;
    height: 60pt;
    border-radius: 15%;
    font-size: 45px;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.multi{
    background-color: rgb(253, 92, 17);
    border-width: 8px;
    border-bottom-color: white;
}

.num{
    background-color: rgb(48, 46, 45);
    border-width: 8px;
    border-bottom-color: white;
    color: white;
}

table{
    background-color: black;
    border-radius: 15px;
}

.result{
    background-color: white;
    color: red;
    font-family: Impact, Haettenschweiler, 'Arial Narrow Bold', sans-serif;
    font-size: 500px;
    width: 96%; 
    height: 80px; 
    font-size: 40px; 
    text-align: right; 
    padding-right: 10px;
}
```





---

## 기능

### 참고 모델

> 버튼을 통제하는 방식

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



```js
let firstValue = "",
  firstDone,
  secondValue = "",
  secondDone,
  currentOperation;
```



> 연산자 구별

switch / Case 사용



_참고 모델은 CSS grid를 이용해서 디자인_ 

   

---

## 실행

> array.form

배열 저장에 이용

> parseInt()

string을 10진수의 숫자로 반환

> log,  루트

```js
  switch{
      case "log":
         if(!valOne == null)return Math.log(valOne);
         else return Math.log(valTwo);
      case "√":
         if(!valOne==null) return Math.sqrt(valOne);
         else return Math.sqrt(valTwo);
}  
```



## 완성

```js
const num= document.querySelectorAll(".num"),
 multi= document.querySelectorAll(".multi"); 
const nums= Array.from(num),   

let firstValue = "", 
  firstDone,         //0 or 1 첫 숫자와 연산자 사이 카운트
  secondValue = "",  // 두번째 숫자 값
  secondDone,        // 연산자와 두번째 숫자값 사이 카운트
  currentOperationVal;  //현재 입력 값

const result= document.querySelector(".result");
const reset= document.querySelector(".reset");
 equals= document.querySelector(".equals");

function handleNumberClick(e){
   let clickNumber= e.target.value;
   if(!firstDone){   
      firstValue += clickNumber; 
      result.innerHTML= firstValue;
   } else { 
      secondValue += clickNumber;   
      result.innerHTML= secondValue;   
      secondDone= true; 
   }
}

function handleMultiClick(e){
   const operationVal= e.target.value;
   if(!firstDone) {  
      firstDone= true;  
   } 
   if(firstDone && secondDone){  
      calculation(); 
   }

   currentOperationVal= operationVal;  
}

function calculation(){
   const operation= doOperation();
   result.innerHTML= operation; 
   firstValue= operation;  
   secondDone= false;   
   secondValue="";   
}

function doOperation(){
   const valOne= parseInt(firstValue, 10);   
   const valTwo= parseInt(secondValue, 10);

   switch(currentOperationVal){
      case "+": 
         return valOne+valTwo;
      case "-":
         return valOne-valTwo;
      case "*":
         return valOne*valTwo;
      case "/":
         return valOne/valTwo;
      case "log":
         if(!valOne == null)return Math.log(valOne);
         else return Math.log(valTwo);
      case "√":
         if(!valOne==null) return Math.sqrt(valOne);
         else return Math.sqrt(valTwo);
      case "^":
         return Math.pow(valOne,valTwo);
      default:
         return;
   }
}


function handleReset(){
   firstValue="";
   secondvalue="";
   currentOperationVal=null;
   result.innerHTML="0";
   firstDone= false;
   secondDone= false;
}

function handleEquals(){
   if( firstDone && secondDone ){
      calculation();
   }
}

nums.forEach(function(numval){
   numval.addEventListener("click", handleNumberClick);
});
multis.forEach(function(multiVal){
   multiVal.addEventListener("click", handleMultiClick);
});
reset.addEventListener("click", handleReset);
equals.addEventListener("click", handleEquals);
```