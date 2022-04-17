---
title: reference variable, reference
tag: cpp
---





## 참조형 변수

참조형은 다른 객체 또는 값의 별칭으로 사용되는 C++ 타입이다.

C++은 세 가지 종류의 참조형을 지원한다.

1. non-const 값 참조형
2. const 값 참조형
3. r-value 참조형



### Reference to noo-const values

non-const 값에 대한 참조형은 자료형 뒤에 앰퍼샌드(&)를 사용하여 선언한다.

- 자료형& 별명 = 기존 변수명;

```cpp
int value = 5; // normal integer
int& ref = value; // reference to variable value
```

위 코드에서 `&`는 주소(address)를 의미하지 않고 참조(reference)를 의미한다.



### Reference as aliases

참조형은 참조하는 값과 동일하게 작동한다. 

이런 의미에서 참조형은 참조되는 객체의 별칭으로 사용된다.

`ref`와 `value`는 동의어로 취급된다.

참조형에 주소 연산자(`&`)를 사용하면 참조되는 값의 주소가 반환된다.

```cpp
cout << &value; // prints 0012FF7C
cout << &ref;   // prints 0012FF7C
```



### A brief review of l-values and r-values

- l-value는 메모리 주소를 가진 객체

- r-value는 메모리 주소가 없고, 표현식 범위에만 있는 임시 값이다.



### References must be initialized

참조형은 선언과 동시에 반드시 초기화해야 한다.

```cpp
int value = 5;
int& ref = value; // valid reference, initialized to variable value

int& invalidRef; // invalid, needs to reference something
```

null 값을 저장할 수 있는 포인터와 다르게, null 참조 같은 것은 없다.

non-const 값에 대한 참조는 non-const 값으로만 초기화할 수 있다. 

- const 값 또는 r-value로 초기화할 수 없다.

```cpp
//case1
int x = 5;
int& ref1 = x; // okay, x is an non-const l-value

//case2
const int y = 7;
int& ref2 = y; // not okay, y is a const l-value

//case3
int& ref3 = 6; // not okay, 6 is an r-value
```



### Reference can not be reassigned

초기화된 후에는 다른 변수를 참조하도록 변경할 수 없다.

```cpp
int value1 = 5;
int value2 = 6;

int& ref = value1; // okay, ref is now an alias for value1
ref = value2; // assigns 6 (the value of value2) to value1 -- does NOT change the reference!
//같은 의미: value1 = value2;
```



### Reference as function parameters

참조형은 함수 매개 변수로 가장 많이 사용된다.

이때 매개 변수는 인수의 별칭으로 사용되며, <u>복사본이 만들어지지 않는다.</u> 

> 복사하는데 비용이 많이 드는 경우, 참조자 매개변수를 사용하면 성능이 향상될 수 있다.

함수에 포인터 인수 전달하면 <u>함수 안에서 포인터를 역참조하여 인수의 값을 직접 수정할 수 있었다.</u> 

이런 점에서 참조형은 유사하게 작동한다. 

참조형 매개 변수는 인수의 별칭으로 사용되므로 참조 매개 변수를 사용하는 함수는 <u>전달된 인수를 수정할 수 있다.</u>

```cpp
#include <iostream>

// ref is a reference to the argument passed in, not a copy
void changeN(int& ref)
{
    ref = 6;
}

int main()
{
    int n = 5;
    std::cout << n << '\n';
    changeN(n); // note that this argument does not need to be a reference
    std::cout << n << '\n';
    return 0;
}

// 5 
// 6
```

인수 `n`이 함수에 전달되면 함수 매개변수 `ref`가 인수 `n`에 대한 참조로 설정된다. 

이것은 함수가 `ref`를 통해 `n`의 값을 변경할 수 있게 한다. 

변수 `n` 자체가 참조형일 필요는 없다.



### References as shortcuts

참조형의 또 다른 장점은 중첩된 데이터에를 쉽게 접근할 수 있게 한다는 것이다.

```cpp
struct Something
{
    int value1;
    float value2;
};

struct Other
{
    Something something;
    int otherValue;
};

Other other;
```

`other.somthing.value1`에 접근할 경우 타이핑 양이 많아지고 여러 개면 코드가 엉망이 될 수 있다. 참조를 통해 좀 더 쉽게 접근할 수 있다.

```cpp
int& ref = other.something.value1;
// ref can now be used in place of other.something.value1
```

따라서 다음 두 명령문은 같다.

```cpp
other.something.value1 = 5;
ref = 5;
```

이렇게 하면 코드가 더 명확하고 읽기 쉽게 유지할 수 있다.



### References vs Pointers

참조형과 포인터는 흥미로운 관계에 있다. 

참조형은 접근할 때 암시적으로 역참조되는 포인터와 같은 역할을 한다. (참조형은 내부적으로 포인터를 사용하여 컴파일러서 구현된다.)

```cpp
int value = 5;
int* const ptr = &value;
int& ref = value;
```

`*ptr`과 `ref`는 동일하게 평가된다. 그러므로 다음 두 명령문은 같은 효과를 낸다.

```cpp
*ptr = 5;
ref = 5;
```

참조형은 선언과 동시에 유효한 객체로 초기화해야 하고, 일단 초기화되면 변경할 수 없으므로 포인터보다 사용하는 것이 훨씬 안전하다. (널 포인터를 역참조하면 위험하다.)

주어진 문제가 참조형과 포인터 둘 다로 해결할 수 있다면 참조형을 사용하는게 더 좋다.



출처: https://boycoding.tistory.com/207?category=1009770 [소년코딩]



---

[정리: 소년코딩](https://boycoding.tistory.com/207?category=1009770)

---

## 참조자

### 참조자와 포인터의 차이점

+ 레퍼런스는 정의 시에 반드시 누구의 별명인지 명시 해야 함.

```
int& a; // 불가능
int* p; // 가능
```

+ 한 번 어떤 변수의 참조자가 되버린다면, 이 더이상 다른 변수를 참조할 수 없게 됩니다.

+ 레퍼런스는 메모리 상에 존재하지 않을 수 도 있다.

만일 내가 컴파일러라면 `another_a` 위해서 메모리 상에 공간을 할당할 필요가 있을까요? 아니죠! 왜냐하면 `another_a` 가 쓰이는 자리는 모두 `a` 로 바꿔치기 하면 되니까요. 따라서 이 경우 레퍼런스는 메모리 상에 존재하지 않게 됩니다. 물론 그렇다고 해서 항상 존재하지 않은 것은 아닙니다. 



### 함수의 인자로 존재할 때

+  C++ 문법 상 참조자의 참조자를 만드는 것은 금지되어 있습니다.
+  참조자를 사용하게 되면 불필요한 `&` 와 `*` 가 필요 없기 때문에 코드를 훨씬 간결하게 나타낼 수 있습니다.
+ C++ 문법 상 상수 리터럴을 일반적인 레퍼런스가 참조하는 것은 불가능하게 되어 있습니다.

```cpp
//대신
const int &ref = 4;
int a = ref; // a=4;
```

**주소값이 존재한다라는 의미는 해당 원소가 메모리 상에서 존재한다** 라는 의미와 같습니다. 하지만 레퍼런스는 특별한 경우가 아닌 이상 메모리 상에서 공간을 차지 하지 않습니다. 따라서 이러한 모순 때문에 레퍼런스들의 배열을 정의하는 것은 언어 차원에서 금지가 되어 있는 것입니다.

but , 포인터와는 다르게 배열의 레퍼런스의 경우 참조하기 위해선 반드시 배열의 크기를 명시해야 합니다.

### Dangling reference

레퍼런스는 있는데 원래 참조 하던 것이 사라진 레퍼런스를 댕글링 레퍼런스 (Dangling reference) 라고 부릅니다. *Dangling* 이란 단어의 원래 뜻은 약하게 결합대서 **달랑달랑** 거리는 것을 뜻하는데, 레퍼런스가 참조해야 할 변수가 사라져서 혼자서 덩그러니 남아 있는 상황과 유사하다고 보시면 됩니다.

```cpp
//외부 변수의 레퍼런스를 리턴
int& function(int& a) 
{
  a = 5;
  return a;
}

int main() 
{
  int b = 2;
  int c = function(b);
  return 0;
}
```

그렇다면 이렇게 참조자를 리턴하는 경우의 장점이 무엇일까요? C 언어에서 엄청나게 큰 구조체가 있을 때 해당 구조체 변수를 그냥 리턴하면 전체 복사가 발생해야 해서 시간이 오래걸리지만, 해당 구조체를 가리키는 포인터를 리턴한다면 그냥 포인터 주소 한 번 복사로 매우 빠르게 끝납니다.

마찬가지로 레퍼런스를 리턴하게 된다면 **레퍼런스가 참조하는 타입의 크기와 상관 없이 딱 한 번의 주소값 복사로 전달이 끝나게 됩니다**. 따라서 매우 효율적이죠!



### 참조자가 아닌 값을 리턴하는 함수를 참조자로 받기

원칙상 함수의 리턴값은 해당 문장이 끝나면 소멸되는 것이 정상입니다. 따라서 기존에 `int&` 로 받았을 때에는 컴파일 자체가 안되었습니다. 하지만 예외적으로 **상수 레퍼런스로 리턴값을 받게 되면 해당 리턴값의 생명이 연장됩니다**. 그리고 그 연장되는 기간은 레퍼런스가 사라질 때 까지 입니다.

---

함수의 매개변수로 레퍼런스를 전달할 때는 주소를 전달한다라고 하셨는데, 이것을 힙, 스택, 데이터 메모리 영역 분리 관점에서 호출 스택이 추가될 때 주소값을 전달한다.

1) 호출 스택이 달라질 때, 해당 메모리에 접근하기 위해 주소가 필요하다. 주소 메모리 공간이 필요하다. 

2) 호출 스택이 같을 때, 바로 접근하며 따로 주소 메모리 공간이 불필요하다. 이렇게 생각해도 될까요?

---

[정리: 씹어먹는 C++](https://modoocode.com/141)

---