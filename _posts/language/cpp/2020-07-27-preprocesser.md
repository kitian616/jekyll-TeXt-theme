---
title: preprocesser
tag: cpp
---



## 전처리기

프로그램을 컴파일할 때 컴파일 직전에 실행되는 별도의 프로그램이다.

전처리기가 실행되면 각 코드 파일에서 지시자(directives)를 찾는다. 

지시자(directives)는 #으로 시작해서 줄 바꿈으로 끝나는 코드다. 

> 전처리기는 컴파일러가 실행되기 직전에 단순히 텍스트를 조작하는 치환 역할을 하기도 하고, 디버깅에도 도움을 주며 헤더 파일의 중복 포함도 방지해주는 기능을 가진다.



### Include

```cpp
#include <iosteam> //전처리기
```

전처리기(prerocesser)는 포함(include)된 파일의 내용을 지시자의 위치에 복사한다. 

전방 선언(forward declaration) 에 사용했었다.



### Macro

`#define` 지시자를 사용해서 매크로(macro)를 만들 수 있다. 

매크로는 입력을 출력으로 변환하는 방식을 정의하는 규칙이다.



#### 종류

+ 객체와 유사한 매크로(object-like macro)

+ 함수와 유사한 매크로(function-like macro)

함수와 유사한 매크로(function-like macro)는 함수처럼 작동한다.

객체와 유사한 매크로(object-like macro)는 아래와 같이 두 가지 방법 중 하나로 정의한다.



##### object-like macro

```
#define identifier 
#define identifier substitution_text //대체 텍스트 유무
```

```cpp
#define lvl 99

//두 문장은 같은 결과
std::cout << "lvl :" << lvl <<std::endl;
std::cout << "lvl :" << 99 <<std::endl;
```

---


```
#define USE_YEN
```

일반적으로 이 전처리 지시자는 조건부 컴파일(conditional compilation)을 하기위해 사용된다.

##### Conditional compilation

+ 조건부 컴파일, 

  조건부 컴파일 전처리 지시자를 사용하면 컴파일할 조건이나 컴파일하지 않을 조건을 지정할 수 있다.

+ #ifdef 지시자를 사용하면 전처리기가 이전 #이 정의되었는지 아닌지를 확인한다.

  정의되었다면 #ifdef와 해당 #endif 사이의 코드가 컴파일된다. 그렇지 않으면 코드가 무시된다.

```cpp
#define PRINTJOE

#ifdef PRINT_JOE
std::cout<< "Joe" <<std::endl;;
#endif

#ifdef PRINT_BOB
std::cout << "Bob" <<std::endl;
#endif
```

`PRINT_JOE`가 정의되었기 때문에 ` cout << "Joe" << endl;`는 컴파일된다. 그러나 `PRINT_BOB`은 정의되지 않았기 때문에 `cout << "Bob" << endl;`는 무시된다.

---

`#ifndef`는 `ifdef`의 반대다. 식별자(identifier)가 아직 정의되지 않았는지 확인한다.

```
#ifndef PRINT_BOB 
std::cout << "Bob" << std::endl; 
#endif
```

> PRINT_BOB은 아직 정의돈 적이 없기 때문에 (#define) "Bob"은 출력됨



[정리: 소년코딩](https://boycoding.tistory.com/145?category=1006674 ) 

---