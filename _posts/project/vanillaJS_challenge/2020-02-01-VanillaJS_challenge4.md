---
title: VanillaJS Challenge (4)
tag: VanillaJS Challenge
---



## 조건

1. Save the country selection to localStorage.
2. Load the saved selection on refresh.

**Clues:**

- Don't forget to add '**values**' to the options.
- You need to **'select'** the option that has the same value as the localStorage.
- [< select >](https://developer.mozilla.org/ko/docs/Web/API/HTMLElement/change_event)
- [< option >](https://developer.mozilla.org/ko/docs/Web/HTML/Element/option)
- [.querySelector()](https://developer.mozilla.org/ko/docs/Web/API/Document/querySelector) (Check out the section: 좀 더 복잡한 선택자)



----

## 알고리즘

### 사전 구상

1. `document.body` 변수화

2. `innerHTML`로 박스 생성 (여러 선택자를 선택하는 박스가 있던걸로 기억)

3. if `localStorage`에 값이 `null`이면 선택창,

   else 값이 존재하면 선택된 값을 저장.

4. `JSON`으로 저장된 오브젝트를 문자열로 변환

   로드 할때는 역으로 변환



### 확정

1. html `select-option`로 폼(=셀렉트 박스) 만들기

2. 셀렉트 박스의 값을 가져오기 ( Value / Text ) 

3. Key 값과 가져온 ( Value / Text )을 `localstorage`에 저장 =`setItem`

4. 만약`localstorage`에 저장된 Key값이 없다면 3)을 실행하고

   저장된 Key값이 있다면 저장된 값을 불러옴.

5. 



---

## 실행

>  [셀렉트 박스의 옵션 값과 텍스트 값 가져오기](https://stackoverflow.com/questions/1085801/get-selected-value-in-dropdown-list-using-javascript)

```js
//If you have a select element that looks like this:
<select id="ddlViewBy">
  <option value="1">test1</option>
  <option value="2" selected="selected">test2</option>
  <option value="3">test3</option>
</select>

//Running this code:
var e = document.getElementById("ddlViewBy");
var strUser = e.options[e.selectedIndex].value;

//Would make strUser be 2. If what you actually want is test2, then do this:
var e = document.getElementById("ddlViewBy");
var strUser = e.options[e.selectedIndex].text;
//Which would make strUser be test2
```



---

## 정답해설

```js
function loadCountries() {
  const selected = localStorage.getItem("country");
  if (selected) {
    const option = document.querySelector(`option[value="${selected}"]`);
    option.selected = true;
  }
}
```

`country`라는 key값을 불러와서 변수에 저장



_관련된 원소 항목: 선택된 value_

그리고 그 값은 `true`

```js
function handleChange() {
  const selected = select.value;
  localStorage.setItem("country", selected);
}
```

> select.addEventListener("change", handleChange);

`change`로 기존 value말고 새로운 value를 변수로 설정

`localStorage`에 새로운 value `selected`를 적용



