---
title: VanillaJS Challenge (8)
tag: VanillaJS Challenge
---



## 조건

**The final project should have the following features:**

1. Clock.
2. Username Persistance.
3. To Do List.
4. Random Background Image.
5. Weather with Geolocation.

> This is a two day challenge.
>
> Based on the section #3
>
> **Finish** the course project and **deploy** to Github Pages.



---

## 1. Clock

+ 현재 시간을 가져오기

+ 시간을 갱신 1000ms

+ 시간 출력

---

```js
let clock= document.querySelector(".clock");

let clockSec= document.querySelector("#clockSec");
function getDate(){
    let toDay= new Date();
    let hours= toDay.getHours(),
     min= toDay.getMinutes(),
     sec= toDay.getSeconds();
    clock.innerHTML= `
        ${hours < 10 ? `0${hours}` : hours}
        : ${min < 10 ? `0${min}` : min}
    `
    clockSec.innerHTML = `:${sec < 10 ? `0${sec}` : sec}`
}

setInterval(getDate, 1000);
```



---

## 2. Username Persistance

+ 유저 이름을 입력 받음
+ 유저이름을 저장	/	 불러옴

----

> 입력받은 값을 텍스트로 출력

+ 입력 값을 출력하는 함수

```js
function paint(currentVal){
    form.classList.remove("form");
    userInput.classList.add(userName_CN);   
    userInput.innerHTML= `${input.value} ㅎㅇ`;
}
```



+ 값을 입력 받는 이벤트

```js
function handleSubmit(event){
    event.preventDefault();
    const currentVal =input.value;
    paint(currentVal);
}
```



+ 유저이름을 저장	/	 불러옴

```js
function saveVal(currentVal){
    localStorage.setItem(`User`, currentVal);
}
```



```js
//paint
const nameForm= document.querySelector(".nameForm"),
 input= document.querySelector("input"), 
 nameViewer= document.querySelector(".nameViewer");

const userName_CN="showing";

function paint(text){   
    nameForm.classList.remove(userName_CN);
    nameViewer.classList.add(userName_CN);   
    nameViewer.innerHTML= `Hi ${text}`;
}

function handleSubmit(event){   
    event.preventDefault();
    const currentVal = input.value;
    paint(currentVal);
    saveVal(currentVal);
}

function saveVal(currentVal){   
    localStorage.setItem(`User`, currentVal);
}

function load(){  
    let currentVal= localStorage.getItem("User");
    if(currentVal !== null){    
        paint(currentVal);
    } else {
        askName();
    }
}

function askName(){ 
    nameForm.classList.add(userName_CN);
    nameForm.addEventListener("submit", handleSubmit);
}

function init(){
    load();
}

init();
```

​    

---

## 3. To Do List.

+ 할일은 배열. 배열 선언

+ 배열 입력 받기

  > 2와 동일

---

```js
const toDoForm= document.querySelector(".toDoForm");
const formInput= toDoForm.querySelector("input");

let toDoListArry= [];

function handleSubmit(event){   
    event.preventDefault();
    const currentVal= formInput.value;
    paintList(currentVal);
    formInput.value= "";
}

const toDoViewer= document.querySelector(".toDoViewer");

function paintList(text){   
    const li= document.createElement("li");
    const span= document.createElement("span");
    const delBtn= document.createElement("button");

    delBtn.innerText="❌";
    delBtn.addEventListener("click", deleteBtn);

    li.appendChild(span);
    li.appendChild(delBtn);
    toDoViewer.appendChild(li);

    const newId= toDoListArry.length +1;
    span.innerText= text;
    li.id= newId;
    const toDoObj={
        text: text,
        id: newId
    };
    toDoListArry.push(toDoObj);
    saveArry();
}

function deleteBtn(event){
    const btnParent= event.target;
    const li= btnParent.parentNode;
    toDoViewer.removeChild(li);

    const cleanList= toDoListArry.filter(function(toDo) {
        return toDo.id !== parseInt(li.id);
    });
    toDoListArry= cleanList;
    saveArry();
}

const list_VAL="List";

function loadPreVal(){  
    const getObj= localStorage.getItem(list_VAL);
    if(getObj !== null){
        const getString= JSON.parse(getObj);
        getString.forEach(function(toDo){
        paintList(toDo.text);
        });
    }
}

function saveArry(){  
    localStorage.setItem(list_VAL, JSON.stringify(toDoListArry));
}

function init(){
    loadPreVal();
    toDoForm.addEventListener("submit",handleSubmit);
}

init();
```



---

## 4. Random Background Image



```js
const body= document.querySelector("body");

function inputBg(num){
    const image= new Image();
    image.src= `image/${num+1}.jpg`;
    image.classList.add(`bgImage`);
    body.prepend(image);
}

const ImgNum=7;
function generageNum(){
    const ran= Math.floor(Math.random()*ImgNum);
    return ran;
}

function init(){
    const num= generageNum();
    inputBg(num);
}

init();
```



---

## 5. Weather with Geolocation.

+ [API KEY 얻기 (지리 정보로 날씨 정보를 얻는)](https://openweathermap.org/current)
+ API 연결
+ [지리 정보를 요청하는 기능](https://developer.mozilla.org/ko/docs/WebAPI/Using_geolocation)
+ 정보를 얻는데 실패했을 때의 기능
+ [지리 정보를 저장하는 기능](https://developer.mozilla.org/ko/docs/Web/API/Web_Storage_API/Using_the_Web_Storage_API)
+ localstorage는 배열을 저장할 때 오브젝트로 처리. 저장도 그렇게 함.
+ JSON으로 데이터 처리
+ 저장한 값을 불러와 api에 다시 줌

>  날씨 api에 필요한 위도와 경도 데이터를 저장, 그리고 그 값으로 날씨를 불러옴

```js
const weather= document.querySelector(".weather");

const API_KEY= "";
const Weather_Val="Weather";

function handleGeo(position){   
    const latitude= position.coords.latitude;
    const longitude= position.coords.longitude;
    const crdObj={
        latitude,
        longitude
    };
    save(crdObj);
    getWeather(latitude, longitude);
}

function getWeather(latitude, longitude){
    fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=${API_KEY}&units=metric`)
    .then(response=> {
        return response.json();
    }).then(json => {
        const temperatuer= json.main.temp;
        const place= json.name;
        weather.innerText=`${temperatuer} in ${place}`;
    });
}

function save(crdObj){
    localStorage.setItem(Weather_Val, JSON.stringify(crdObj));
}

function load(url){
    const key= localStorage.getItem(Weather_Val);
    if(key !== null){
        const loadWeather= JSON.parse(key);
        getWeather(loadWeather.latitude, loadWeather.longitude);
    } else{
        askGeo();
    }
}

function askGeo(){
    navigator.geolocation.getCurrentPosition(handleGeo, handleError);
}

function handleError(){
    console.log(`cant access geo location`);
}

function init(){
    load();
}

init();
```











