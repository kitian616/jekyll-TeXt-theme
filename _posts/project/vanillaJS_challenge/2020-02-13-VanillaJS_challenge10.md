---
title: Random Number game
tag: VanillaJS Challenge
---



## 기능

- [x] 숫자 입력
- [x] 랜덤 숫자 생성
- [x] 비교
- [x] 결과 출력
- [x] BGM



---

## 개선



### JS

```js

const machineInput= document.getElementById("machineInput"),
 playId= document.getElementById("playId"),
 sliderValueView= document.getElementById('sliderValueView'),
 rangeScroll= document.getElementById(`rangeScroll`),
result= document.getElementById("result"), 
 form= document.querySelector("form"),
 userInput= document.getElementById("userInput"),
 numberId= document.getElementById("numberId");

const show= document.querySelector(".show"),
 showTwo= document.querySelector(".showTwo"); 

function handleSubmit(e){
    e.preventDefault();
    inputUser();
    show.classList.add("on");
}

function inputUser(){   
    userInput.innerHTML= numberId.value;
}

function createNumber(){
    let choseM= Math.floor(Math.random()*rangeScroll.value+1);  
    machineInput.innerHTML= choseM;    
    showTwo.classList.add("on");

    if( numberId.value == choseM){
        result.innerText=`U Win`;
    } else{
        result.innerText=`U rose`;
    }
}

function scroll(){ 
    rangeScroll.setAttribute("oninput", "sliderValueView.innerHTML=rangeScroll.value");
}

function init(){
    form.addEventListener("submit", handleSubmit); 
    playId.addEventListener("click", createNumber);
    scroll(); 
}

init();
```

   

