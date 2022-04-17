---
title: Vue computed, watch
tag: VueJS
---





## computed 

간단한 연산일 때만 이용하는 것이 좋습니다.  `{message.split('').reverse().join('')}`

복잡함

### 변환

```html
<!-- HTMl -->
<div id="example">
    <p>원본 메세지: "{{ message }}" </p>
    <p>역순으로 표시한 메세지: "{{ reverseMessage }}"</p>
</div>
```

```javascript
//JS
var vm= new Vue({
    el: '#example',
    data: {
        message: '안녕하세요'
    },
    computed: {
        //계산된 getter
        reversedMessage: function() {
            // 'this'는 vm 인스턴스
            return this.message.split('').reverse().join('')
        }
    }
})
```

우리가 작성한 함수는 `vm.reversedMessage`속성에 대한 getter 함수로 사용됩니다.

그리고 가장 중요한 것은 우리가 선언적으로(역자 주: 선언형 프로그래밍 방식에 따라서(아래 computed와 watch 비교에 추가 설명)) 의존 관계를 만들었다는 것입니다. computed 속성의 getter 함수는 사이드 이펙트가 없어 코드를 테스트하거나 이해하기 쉽습니다.



## computed 속성의 캐싱 vs methods


이에 비해 메소드를 호출하면 렌더링을 다시 할 때마다 **항상** 함수를 실행합니다.

캐싱이 왜 필요할까요? 계산에 시간이 많이 걸리는 computed 속성인 **A**를 가지고 있다고 해봅시다. 

이 속성을 계산하려면 거대한 배열을 반복해서 다루고 많은 계산을 해야 합니다. 그런데 **A** 에 의존하는 다른 computed 속성값도 있을 수 있습니다. 

캐싱을 하지 않으면 **A** 의 getter 함수를 꼭 필요한 것보다 더 많이 실행하게 됩니다! 캐싱을 원하지 않는 경우 메소드를 사용하십시오.



## computed 속성 vs watch 속성



Vue는 Vue 인스턴스의 <u>데이터 변경을 관찰하고 이에 반응하는</u> 보다 일반적인 **watch 속성**을 제공합니다. 

다른 데이터 기반으로 변경할 필요가 있는 데이터가 있는 경우 `watch`를 남용하는 경우가 있습니다. 하지만 명령적인 `watch` 콜백보다 computed 속성을 사용하는 것이 더 좋습니다.

(역자 주: watch 속성은 감시할 데이터를 지정하고 그 데이터가 바뀌면 이런 함수를 실행하라는 방식으로 소프트웨어 공학에서 이야기하는 ‘명령형 프로그래밍’ 방식. computed 속성은 계산해야 하는 목표 데이터를 정의하는 방식으로 소프트웨어 공학에서 이야기하는 ‘선언형 프로그래밍’ 방식.) 



## computed 속성의 setter 함수

```javascript
// JS

var vm= new Vue({
    el: '#demo',
    data: 
    {
        firstName: 'Foo',
        lastName: 'Bar'
    },
    computed: 
    {
        //getter
        get: function () 
        {
            return this.firstName + ' ' + this.lastName
        },

        //setter
        set: function (newValue) 
        {
            var names= newValue.split(' ')
            this.firstName= names[0]
            this.lastName= names[names.length -1]
        }
    }
})
```

이제 `vm.fullName = 'John Doe'`를 실행하면 설정자가 호출되고 `vm.firstName`과 `vm.lastName`이 그에 따라 업데이트 됩니다.



## watch 속성

이는 데이터 변경에 대한 응답으로 비동기식 또는 시간이 많이 소요되는 조작을 수행하려는 경우에 가장 유용합니다.

```
https://kr.vuejs.org/v2/guide/computed.html
```

이 경우 `watch` 옵션을 사용하면 비동기 연산 (API 엑세스)를 수행하고, 우리가 그 연산을 얼마나 자주 수행하는지 제한하고, 최종 응답을 얻을 때까지 중간 상태를 설정할 수 있습니다. 계산된 속성은 이러한 기능을 수행할 수 없습니다.