---
title: package.json
tag: nodejs
---



## 정의

패키지에 관한 정보와 의존중인 버전에 관한 정보를 담고 있습니다.

**name**과 **version**이 있죠. **필수**로 입력해야하는 부분

- **description**과 **keywords**

  각각 설명과 키워드를 알려주는데요. 사람들이 검색할 때 쉽게 찾을 수 있습니다.

- . **private**

   이 패키지를 비공개할건지 여부를 알려줍니다. 저는 true라서 여러분은 제 패키지를 볼 수 없습니다.

- ....

- **main**

   이 패키지의 메인 파일이 뭔지를 알려주고, **scripts**는 여러가지 npm 명령어를 알려줍니다. 

  ```
  $ npm start
  >> node server/index.js
  ```

  

  preinstall은 누군가가 이 패키지를 install했을 때 설치하기 전에 하는 행동을 말합니다. 비슷한 것으로 postinstall(설치한 후의 동작)이 있습니다. 

   publish, uninstall, start, restart, test, version 등이 있습니다. 모두 pre나 post를 붙일 수 있습니다. 

  임의로 자기가 script를 만들어도 됩니다. scripts에 build라는 명령어를 만들었으면 **npm run build**하면 해당 명령어가 실행됩니다.



## 의존

### dependencies, devDependencies, peerDependencies

package.json은 의존중인 패키지들의 버전을 기록해줍니다. 



## Version 규칙

**[메이저].[마이너].[패치]** 

- 메이저는 대규모 업데이트(이전 버전과 호환 안 됨)
- 마이너는 소규모 업데이트(이전 버전과 호환은 됨)
- 패치는 버그 수정 시에 버전을 올립니다. 

npm에서 특정 버전을 설치하고 싶다면 **npm install [패키지명]@버전**하면 됩니다.



[npm](https://docs.npmjs.com/files/package.json)