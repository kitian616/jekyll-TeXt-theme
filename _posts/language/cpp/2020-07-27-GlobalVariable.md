---
title: Global variable and linkage
tag: cpp
---





## 전역 변수와 링크



+ 전역 변수가 정의된 이후, 프로그램 어디에서든지 접근 가능하다.
+ 전역 변수가 정의된 이후, 프로그램 어디서든지 접근 가능하다.

중첩된 블록(nested block)이 이름이 같은 외부 블록의 변수를 숨기는 것처럼, 전역 변수와 같은 이름을 가진 지역 변수는 전역 변수를 숨긴다. 

그러나 전역 범위 연산자(`::`)를 사용하면 컴파일러는 지역 변수 대신 전역 변수를 사용한다.

+ g_ 접두사를 붙임. (관습적)



### Internal and external linkage via the static and extern keywords

**링크는 같은 이름의 여러 식별자가 같은 식별자를 참조하는지를 결정한다.**

링크가 없는 변수는 정의되어진 제한된 범위에서만 참조할 수 있다. 

지역 변수가 링크가 없는 변수의 예이다. 이름은 같지만 다른 함수에서 정의된 지역 변수는 링크가 없다. 각 변수는 독립적이다.

+ static

내부 링크가 있는 변수를 `static` 변수라고 한다. `static` 변수는 변수가 정의된 소스 파일 내에서 어디서나 접근할 수 있지만, 소스 파일 외부에서는 참조할 수 없다.

```cpp
//ex) static
static int g_x; // g_x is static, and can only be used within this file
```

+ extern

외부 링크가 있는 변수를 `extern` 변수라고 한다. `extern` 변수는 정의된 소스 파일과 다른 소스 파일 모두에서 접근할 수 있다.

```cpp
//ex) extern
extern double g_y(9.8); // g_y is external, and can be used by other files
```

기본적으로 전역 변수는 `extern` 변수로 간주한다. 

그러나 상수(`const`) 전역 변수는 `static` 변수로 간주된다.



### Variable forward declarations via the extern keyword

extern 키워드를 통한 변수 전방 선언,

다른 소스 파일에서 선언된 외부 전역 변수를 사용하려면 '변수 전방 선언(variable forward declarations)'을 해야 한다.

`extern` 키워드는 두 가지 다른 의미가 있다. 

1. 어떤 상황에서는 extern 키워드가 '외부 링크가 있는 변수를 의미' 하고,
2. 다른 상황에서는 '다른 어딘가에서 정의된 변수에 대한 전방 선언'을 의미한다.

###### global.cpp:

```cpp
// 두 개의 전역 변수를 정의한다.
// non-const globals have external linkage by default
int g_x;           // external linkage by default
extern int g_y(2); // external linkage by default, so this extern is redundant and ignored

// in this file, g_x and g_y can be used anywhere beyond this point
```

###### main.cpp:

```cpp
#include <iostream>
#include "global.cpp"

extern int g_x; // forward declaration for g_x (defined in global.cpp) -- g_x can now be used beyond this point in this file
int main()
{
    extern int g_y; // forward declaration for g_y (defined in global.cpp) -- g_y can be used beyond this point in main() only
    g_x = 5;
    std::cout << g_y; // should print 2

    return 0;
}
```

만약 변수 전방 선언이 함수 외부에서 선언되면 소스 파일 전체에 적용된다. 함수 내에서 선언되면 해당 블록 내에서만 적용된다.

> 변수가 `static`으로 선언된 경우, 이에 접근하기 위해 변수 전방 선언을 해도 적용되지 않는다.

###### constant.cpp

```cpp
static const double g_gravity(9.8);
```

###### main.cpp:

```cpp
#include <iostream>
#include "constant.cpp"

extern const double g_gravity; // This will satisfy the compiler that g_gravity exists

int main()
{
    std:: cout << g_gravity; // This will cause a linker error because the only definition of g_gravity is inaccessible from here
    return 0;
}
```



### Function linkage

함수는 변수와 같은 링크 속성을 가진다. 함수는 항상 외부 링크로 기본 설정되지만 `static` 키워드를 통해 내부 링크로 설정할 수 있다.

```cpp
// This function is declared as static, and can now be used only within this file
// Attempts to access it via a function prototype will fail
static int add(int x, int y)
{
    return x + y;
}
```

함수 전방 선언에는 `extern` 키워드가 필요하지 않다. 컴파일러는 함수 몸체인지 함수 원형인지 알아서 판단한다.

---

[정리: 소년코딩](https://boycoding.tistory.com/167?category=1007833)

---