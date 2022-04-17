---
title: Momentom clon
tag: VanillaJS Challenge
---



## 기능

- [x] 현재 시간
- [x] 현재 온도
- [x] 이름 
- [x] 할 일 리스트
- [x] 명언



---



## 명언 기능 추가

+ [API](https://quotes.rest/#!/qod/get_qod) 얻기

```js
const say= document.querySelector(".quotes");

fetch(`http://quotes.rest/qod.js?category=inspire`)
 .then(function(response) {
     return response.json();	
})
 .then(function(myJson) {
    const val= JSON.stringify(myJson);	
	const quotes= val.contens.quotes;
    const auther= val.contens.auther;
    say.innerHTML= `${quotes} by ${auther}`;
});
```

+ fetch MDN을 참조

```js
const say= document.querySelector(".quotes");

fetch(`http://quotes.rest/qod.js?category=inspire`)
 .then(function(response) {
     return response.json();	
})
 .then(function(myJson) {
    console.log(JSON.stringify(myJson));	
});
```





```js
const say= document.querySelector(".quotes");

fetch(`http://quotes.rest/qod.js?category=inspire`, {
  headers: {
    "Accept": "application/json",
    "Content-Type": "application/json"
  }
})
 .then(function(response) {
     return response.json();
})
 .then(function(myJson) {
    console.log(JSON.stringify(myJson));
});
```



Access to fetch at 'http://quotes.rest/qod.js?category=inspire' from origin 'null' has been blocked by CORS policy: Request header field content-type is not allowed by Access-Control-Allow-Headers in preflight response.

quotes.js:4 GET http://quotes.rest/qod.js?category=inspire net::ERR_FAILED

index.html:1 Uncaught (in promise) TypeError: Failed to fetch
Promise.then (async)
(anonymous) @ quotes.js:13

   

2. 

This endpoint is not returning application/JSON, it is returning application/javascript. This looks like its probably used as a part of a JSONP request because it is returning javascript that when executed continues the call chain.

> 문제 :
>
> endpoint not returning
>
> 해결:
>
> a JSONP request 

In order to interact with the endpoint you need to either have a library that supports JSONP such as jquery or implement the support yourself. https://en.wikipedia.org/wiki/JSONP

> have a library 
>
> JSONP 

   

> http://quotes.rest/#!/qod/get_qod_categories
>


