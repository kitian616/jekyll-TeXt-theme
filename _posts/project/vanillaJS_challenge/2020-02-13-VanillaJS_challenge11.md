---
title: Calculator (1)
tag: VanillaJS Challenge
---



## 기능

1. 입력받은 숫자와 연산자를 통채로 연산하는 방법

- [x] 사칙 연산
- [x] 연산자 중복 방지
- [x] 결과 값의 출력
- [x] 계산기 디자인

> HTML의 onclick 이용

---

## 디자인

> 모델

![993E1E355B06A81B13](https://user-images.githubusercontent.com/59364300/74423741-17399f00-4e94-11ea-8c3a-c879e96c9e94.png)





결과창 | c

7 8 9 +

4 5 6 -

1 2 3 *

0  = /

   

---

## 계산기 1

### HTML

> button 

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <link rel="stylesheet" href="index.css"/>
        <title>Calculator</title>
    </head>
    <body>
        
     <div>
        <input id="calculator">
        <button class="reset" onclick="reset()">C</button> <br>
        
        <button value="7" onclick="add(7)">7</button>
        <button value="8" onclick="add(8)">8</button>
        <button value="9" onclick="add(9)">9</button>
        <button class="multi" onclick="add(`+`)">+</button> <br>

        <button value="4" onclick="add(4)">4</button>
        <button value="5" onclick="add(5)">5</button>
        <button value="6" onclick="add(6)">6</button>
        <button class="multi" onclick="add(`-`)">-</button> <br>

        <button value="1" onclick="add(1)">1</button>
        <button value="2" onclick="add(2)">2</button>
        <button value="3" onclick="add(3)">3</button>
        <button class="multi" id="multi" onclick="add(`*`)">*</button> <br>

        <button class="zero" value="0" onclick="add(0)">0</button>
        <button  id="equals" onclick="result()">=</button>
        <button class="multi" onclick="add(`/`)">/</button> <br>
     </div>  
    
    <script src="index.js"></script> 
    </body>
</html>

```

   

### JS

```js
const calculator= document.getElementById("calculator"),
 equals= document.getElementById("equals");

let tOf= false, 
 tofCount=0;

function add(num){  
    if(isNaN(num) == true){
        tofCount++;     
    } else{ 
        tofCount=0;
    }

    if(tofCount > 1){
        return tOf=true;
    } 

    if(tOf == false){
        claculation(num);
    } else { 
        return;
    }
}

function claculation(num){
    if(isNaN(num) === 1){
        return;
    } else{
        calculator.value += num;
    }
}


function result(){
    calculator.value= eval(calculator.value);
}

function reset(){
    calculator.value="";
}
```

   

### CSS

```css
body{
    background-color: black;
    background-image: url(src/1.jpg);
}

button{
    text-align: center;
    font-size: 55px;
    width: 15%;
    position: relative;
    display: inline-block;
    -webkit-transition-duration: 0.4s;
    transition-duration: 0.4s;
    border-color: gray;
    border-radius: 5px;
    padding: 0%;
    margin: 0%;
}

#calculator{
    width: 45%;
    text-align: right;
    padding-right: 15px;
    margin-bottom: 2px;
    background-color: gray;
    border: none;
    line-height: 200%;
    font-size: 40px;
    color: white;
    border-radius: 10px;
    font-family: 'Times New Roman', Times, serif;
}

.zero{
    width: 31%;
}

div{
    position: absolute;
    padding-left: 10%;
    padding-top: 10%;
}

.reset{
    background-color: transparent;
    color: rgb(255, 145, 0);
    font-size: 30px;
    border: none;
}

.multi{
    background-color: rgb(255, 145, 0);
    color: white;
}

```

