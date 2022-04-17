---
title: namespace, std
tag: cpp
---



# 네임스페이스

이름 충돌은 두 개 이상의 식별자가 같은 [스코프](https://brunch.co.kr/@stdcpp/15)에 있는 경우 컴파일러가 어느 식별자를 사용해야 하는지 명확하게 알 수 없을 때 일어난다.

자세하게 말하자면 같은 프로그램에 이 둘을 같이 포함하면 이름과 매개 변수가 같은 함수가 같은 스코프에 있음으로써 이름 충돌이 발생한다. 따라서 컴파일러가 다음과 같은 오류를 발생시킨다.

> 이런 이름 충돌 문제를 해결하기 위해 **네임스페이스 (namespace)** 개념이 도입되었다.

## 개념

네임스페이스는 모든 식별자(변수, 함수, 형식 등의 이름)가 고유하도록 보장하는 코드 영역을 정의한다.

> 중복된 이름들을 구별하기 위해서 상위의 카테고리를 만든 것이 네임스페이스

## 스코프 분석 연선자(::)

컴파일러에 사용할 `doSomething()` 함수의 버전을 알려주는 방법은 **스코프 분석 연산자(`::`)**를 통한 방법과 **`using` 명령문**을 사용하는 방법 두 가지가 있다.

> 중복된 이름이기 때문에 어느 카테고리에서 사용되었는지 범위를 지정해줄 필요성이 있다.

+ 네임스페이스는 다른 네임스페이스 안에 중첩될 수 있다.



---

## std

또 표준 라이브러리에 도입된 새로운 기능이 충돌할 수 있으므로 한 버전의 C++에서 컴파일될 프로그램이 향후 버전의 C++에서 컴파일되지 않을 수 있다. 그래서 **C++은 표준 라이브러리의 모든 기능을 `std namespace`라는 특별한 영역으로 옮겼다.**

---

### Using

```
#include "header1.h" //foo()
#include "header2.h" //foo()

using header1::foo; //헤더1의 foo만 사용
```

```
#include "header1.h" //foo()
#include "header2.h" //foo()

using header1; //헤더1의 모든 함수 사용
```

---

### 이름없는 이름 공간

이 경우 해당 이름 공간에 정의된 것들은 해당 파일 안에서만 접근할 수 있게 됩니다. 이 경우 마치 `static` 키워드를 사용한 것과 같은 효과를 냅니다.

```cpp
#include <iostream>

namespace 
{
// 이 함수는 이 파일 안에서만 사용할 수 있습니다.
// static int A()
    int A() {} 

// static int x
	int x = 0;
}

int main() 
{
  A();
  x = 3;
}
```

Q. static과 동일하다는 말은 함수가 시작하기 전에 값이 이미 할당되었다는 의미인가?

---

[namespace](https://modoocode.com/136)

---

### 실전 적용

> calculator.h

```cpp
namespace calculator
{    
	int add(int x,int y)
	{
		return x+y;
	}
}
```



> calculator.cpp


```cpp
#include <iosteam>
#include "calculator.h"

int void main
{
	calculator::add(3,4);    
}
```

or

```cpp
#include <iosteam>
#include "calculator.h"
//충돌위험이 잇어별로 권장되지 않는 방식
using namespace calculator; 
int void main
{
	add(3,4);    
}
```

or

```cpp
#include <iosteam>
#include "calculator.h"

using calculator::add; 
int void main
{
	add(3,4);    
}
```

