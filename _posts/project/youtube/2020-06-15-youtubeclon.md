---
title: youtube clone 
tags: youtube nodejs express
---

+ package.json 
  `scripts`

+ .gitignore
  모듈관리

+ express
  `라우터` 사용, 서버 열기

Q.  변수가 없으면 서버는 무한 Lording

> 서버는 일반적으로 HTTML을 응답하도록 함.

- request obj : 어떤 데이터가 전송되었는지
-  response obj

---

+ 루트 / : 


app.get("/profile", handleProfile);


+ babel
  `stage`
  `preset` 

// 문법 변환 룰?

+ babelrc
  `preset` 저장, 컨트롤

+ 프로젝트의 `dependency`와 별개로 설치할 필요가 있을 때
  npm 라이브러리 끝에 `-D`
  
+ `nodemon`
  nodemon --exec // scripts

+ middleware
  유저와 마지막 응답사이에 존재하는 것.
  app.use //절차 지향

  > morgan, helmet, bodyparser, cookieParser

+ `router`를 이용해서 url 분할
  express.Router()
  export // import { userRouter } 
  `use`로 받으면 다음 라우터로 유저를 받음을 의미

---

+ Model, View, Control
  data, how does the data look, function that looks for the data
  
+ Pub
  express에서 View를 다루는 방식 중 하나 =템플릿 //HTML 전달 
  DRY( Don't Repeat Yourself )
  Node Express Template Engine
  
+ 템플릿
  
  ​	템플릿은 서식이나 틀을 말한다. 

+ 템플릿과 프레임워크의 차이점

  템플릿은 CI프레임워크의 MVC 중 V 즉 view에 해당합니다.

  ​	프레임워크는 URL라우팅과 데이터베이스, 기타 유용한 라이브러리(템플릿도 포함 가능)의 모음이라 할 수 있습니다.

+ app.set(name value)
  Application Setting: view engine
  The default engine extension to use when omitted.
  default : N/A (undefined)
  이 설정 값을 pug로 변경

+ `views` : express와 더불어 위치 설정 기능이 포함되어 있음
  **A directory or an array of directories** for the application's views.
  default : process.cwd() + '/views'  //작업 디렉토리 + `/views` = 폴더 생성

+ res.render
  views 폴더에서 입력한 파일명과 확장자가 pug인 템플릿 파일을 찾은 후전송

