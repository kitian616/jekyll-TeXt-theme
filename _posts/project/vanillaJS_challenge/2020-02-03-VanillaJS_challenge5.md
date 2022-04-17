---
title: VanillaJS Challenge (5)
tag: VanillaJS Challenge
---



## 조건

1. Make a To Do list with two boards: **Pending**, **Finished.**
2. Allow the user to switch between boards.
3. Allow the user to delete To Dos.
4. Save everything on **localStorage** and restore everything **on refresh**.

수요일 AM 06:00 까지

---

## 알고리즘

> 시작 전 개요 

필요한 함수:

+ 박스 이벤트 `생성` 함수

+ 박스 이벤트의 자동 `submit`를 막는 함수

+ `localstorage`에 값을 저장하는 함수   *키 값이  `Pending`, `Finished`*
+ `value`의 키 값을 바꾸는 함수
+ `value`의 값을 삭제하는 함수



> 추론

+ 일단 `pending`을 완성 시킴

  입력 받은 값 저장

  저장한 값을 불러 들여서 h6으로 추가해서 만듬

+ `delet`버튼의 이벤트를 만듬

+  `move`버튼의 이벤트를 만듬


---

## 실행

리스트를 만들 대는 <ul>에 클래스를 만들어 `innerhtml`

`createElement`로 <li>하위 원소로 

```js
 function listSave(text){
    const li= document.createElement("li"); 
    const delBtn= document.createElement("button");
    const moBtn= document.createElement("button");
    delBtn.innerText= "❌"; 
    moBtn.innerText="🚩";
    const span= document.createElement("span");
    span.innerText= text;
    li.appendChild(span);
    li.appendChild(delBtn);
    li.appendChild(moBtn);
    pending.appendChild(li);
    localStorage.setItem(pending_LS, text);
 }
```



`li`의 원소를 생성, button이라는 원소 생성 그리고 `X`라는 텍스트를 삽입

`span`원소 생성 그리고 텍스트(=텍스트 변수)를 삽입

li에 `id`를 할당해야 어떤 버튼이 눌렸는지 안다. class로는 모른다는 말

배열을 저장하면 string이 아닌 obj으로 저장됨. `JSON` 필요



---

## 정답해설

*HTML*

+ 각 `form`, `ul`에 대해 `id`선언

*JS*

+ + `getElementById`로 pending, finished, form 선언 

  `querySelector`로 input 선언

+ `KeyValue`로 PENDING, FINISHED 선언

+ `pendingTasks`, `FinishedTasks` 배열 선언

+ 배열의 `Id`선언 : `Date.now`로 변칙적인 id생성하고 `String`으로 리턴 

  ```js
  function getTaskObject(text) {
    return {
      id: String(Date.now()),
      text
    };
  }
  ```

+ 선언한 `Id`로 작업 구별해서 목록 찾기 and 삭제하기

  ```js
  function findInFinished(taskId) {
    return finishedTasks.find(function(task) {
      return task.id === taskId;
    });
  }
  
  function findInPending(taskId) {
    return pendingTasks.find(function(task) {
      return task.id === taskId;
    });
  }
  ```

> _`Array.prototype.find()`_

`find()` 메서드는 주어진 판별 함수를 만족하는 **첫 번째 요소**의 **값**을 반환합니다. 그런 요소가 없다면 [`undefined`](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/undefined)를 반환합니다.

+ 삭제

  ```js
  filter(function(task) {
      return task.id !== taskId;
  }
  ```

+ `addTo`로 finished / pending > push 

+ `deleteTask(e)`는 부모 노트 제거를 동시에 함. (=구분 x)

+ `finishedClick` 

  `li.id`를 받아서 pending / finished 구별하고 

  `delet`pending / `add` finished

  `paint`finishedTask

  `save`  새로운 array

+ `backClick(e)`

  `remove` finished

  `add` pending

  `paint` pending

  `save`

+ `deletBtn(task)`

  ```js
    deleteBtn.addEventListener("click", deleteTask);
    li.append(span, deleteBtn);
    li.id = task.id;
    return li;
  ```

+ `painPendingTask(task)`

  ```js
  const genericLi = buildGenericLi(task);
  ```

  이렇게 위에 버튼을 받아오고 덧붙여서

  ```js
  genericLi.append(completeBtn);
  pendingList.append(genericLi);
  ```

### [parentNode.append](https://developer.mozilla.org/en-US/docs/Web/API/ParentNode/append)

The **`ParentNode.append()`** method inserts a set of [`Node`](https://developer.mozilla.org/en-US/docs/Web/API/Node) objects or [`DOMString`](https://developer.mozilla.org/en-US/docs/Web/API/DOMString) objects after the last child of the `ParentNode`. [`DOMString`](https://developer.mozilla.org/en-US/docs/Web/API/DOMString) objects are inserted as equivalent [`Text`](https://developer.mozilla.org/en-US/docs/Web/API/Text) nodes.

*insert `Node Object` or `DOMString Object`   To `parentNode`*

_`define`: 동사* [FORMAL] When you **append** something **to** something else, especially a piece of writing, you attach it or add it to the end of it._

>  Differences from [`Node.appendChild()`](https://developer.mozilla.org/en-US/docs/Web/API/Node/appendChild)

- `ParentNode.append()` allows you to also append [`DOMString`](https://developer.mozilla.org/en-US/docs/Web/API/DOMString) objects, whereas `Node.appendChild()` only accepts [`Node`](https://developer.mozilla.org/en-US/docs/Web/API/Node) objects. 
- `ParentNode.append()` has no return value, whereas `Node.appendChild()` returns the appended [`Node`](https://developer.mozilla.org/en-US/docs/Web/API/Node) object.
- `ParentNode.append()` can append several nodes and strings, whereas `Node.appendChild()` can only append one node. 

 //DOMString에 접근, 수락만 가능   //리턴 값x, 리턴 Node  // 접근 개수 몇몇, 단지 한개 node

```js
// Ex) Element & Text
let parent = document.createElement("div")
let p = document.createElement("p")
parent.append("Some text", p)

console.log(parent.childNodes) // NodeList [ #text "Some text", <p> ]	
```



+ `painFinishedTask(Task)` 

  

+ `load`

  ```js
  function loadState() {
    pendingTasks = JSON.parse(localStorage.getItem(PENDING)) || [];
    finishedTasks = JSON.parse(localStorage.getItem(FINISHED)) || [];
  }
  ```

  `얻어온 Key value` or연산  `[]`: 둘다 해당 1, 하나만 해당 0, 둘다 아님 0 

+ `ture 만 반환하는 array 만듬`

  ```js
  function restoreState() {
    pendingTasks.forEach(function(task) {
      paintPendingTask(task);
    });
    finishedTasks.forEach(function(task) {
      paintFinishedTask(task);
    });
  }
  ```

  

+ `handleFormSubmit(e)`

  `Date(now)`로 불러온 랜덤한 id을 value로 가져와 선언

  처음 벨류는 ""

  이 정보를 가져와서 `paintPendingTask`, `savePendingTask`, `saveState()`

