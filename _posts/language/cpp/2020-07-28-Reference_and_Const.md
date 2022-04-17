---
title: Reference and const
tag: cpp
---



## 참조와 const

### Reference to const value

const 값에 대한 포인터를 선언하는 것처럼 const 값에 대한 참조를 선언할 수 있다. 

```cpp
const int value = 5;
const int& ref = value; // ref is a reference to const value
```



### Initializing references to const values

const 참조는 non-const 값, const 값 및 r-value로 초기화할 수 있다.

```cpp
int x = 5;
const int& ref1 = x; // okay, x is a non-const l-value

const int y = 7;
const int& ref2 = y; // okay, y is a const l-value

const int& ref3 = 6; // okay, 6 is an r-value
```

상수를 가리키는 포인터처럼 const 값에 대한 참조는 const가 아닌 값을 참조할 수 있다. 이때 참조 접근을 하면 const가 아니어도 const로 간주한다.

```cpp
int value = 5;
const int& ref = value; // create const reference to variable value

value = 6; // okay, value is non-const
ref = 7;   // illegal -- ref is const
```

const에 대한 참조는 간단히 **상수 참조(const reference)**라고 한다.

------

## References to r-values extend the lifetime of the referenced value

r-value는 표현식 범위를 가지고 있다. 즉, 값이 생성된 표현식 끝에서 소멸한다.

```cpp
std::cout << 2 + 3; // 2 + 3 evaluates to r-value 5, which is destroyed at the end of this statement
```

그러나 const에 대한 참조가 r-value로 초기화하면, r-value의 수명이 참조형 변수의 수명에 맞게 확장된다.

```cpp
int somefcn()
{
    const int& ref = 2 + 3; // normally the result of 2+3 has expression scope and is destroyed at the end of this statement
    // but because the result is now bound to a reference to a const value...
    std::cout << ref; // we can use it here
} // and the lifetime of the r-value is extended to here, when the const reference dies
```

------

## Const references as function parameters

함수 매개 변수로 사용되는 참조는 const일 수 있다. 

이렇게 하면 복사본을 만들지 않고 인수에 접근할 수 있으며, 함수는 참조되는 값을 변경하지 못한다.

```cpp
// ref is a const reference to the argument passed in, not a copy
void changeN(const int& ref)
{
    ref = 6; // not allowed, ref is const
}
```

const 참조 매개 변수를 사용하여 const l-value, non-const l-value, 리터럴 및 표현식을 전달할 수 있다.

```cpp
#include <iostream>

void printIt(const int &x)
{
    std::cout << x;
}

int main()
{
    int a = 1;
    printIt(a); // non-const l-value

    const int b = 2;
    printIt(b); // const l-value

    printIt(3); // literal r-value

    printIt(2+b); // expression r-value

    return 0;
}
// 1234
```

포인터나 기본 자료형이 아닌 타입의 값비싼 복사본을 만들지 않으려면 일반적으로 참조로 전달하는 게 좋다.

기본 자료형은 함수가 값을 변경해야 하는 경우가 아니라면 값으로 전달하자.



## Member selection with pointers and references

구조체(또는 클래스)에 대한 포인터(pointer)나 참조형(reference)을 가지는 경우가 많다.

 이전에 배웠던 것처럼 멤버 선택 연산자(`.`)를 사용해 구조체의 멤버를 선택할 수 있다.

```cpp
struct Person
{
    int age;
    double weight;
};
Person person;

// Member selection using actual struct variable
person.age = 5;0
```

이 문법은 참조형에서도 같다.

```cpp
struct Person
{
    int age;
    double weight;
};
Person person; // define a person

// Member selection using reference to struct
Person& ref = person;
ref.age = 5;
```

그러나 포인터에서는 역참조 연산자(`*`)를 사용해서 역참조(dereference) 해야 한다.

```cpp
struct Person
{
    int age;
    double weight;
};
Person person;

// Member selection using pointer to struct
Person* ptr = &person;
(*ptr).age= 5;
```

멤버 선택 연산자(`.`)는 역참조 연산자(`*`)보다 우선순위가 높으므로 포인터 역참조를 괄호로 묶어야 한다.

포인터를 통해 구조체 및 클래스 멤버에 접근하는 구문은 어색하므로 C++에서는 포인터로 멤버를 선택하는데 두 번째 멤버 선택 연산자(`->`)를 제공한다. 다음 두 줄은 같다.

```cpp
(*ptr).age  = 5;
ptr->age = 5;
```

이 형식은 더 쉬울 뿐만 아니라 우선순위에 대해서 걱정할 필요가 없다.

*포인터를 통해 멤버에 접근을 하는 경우 `->` 연산자를 사용하자.*



---

 [정리: 소년코딩](https://boycoding.tistory.com/209?category=1009770)

---





