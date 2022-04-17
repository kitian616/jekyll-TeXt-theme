---
title: Null pointer
tag: cpp
---



##  널 포인터

### Null values and null pointers

C++에서 포인터로 null 값을 지정하려면 리터럴 0으로 초기화하거나 할당하면 된다.

```cpp
float* ptr { 0 };  // ptr is now a null pointer

float* ptr2;       // ptr2 is uninitialized
ptr2 = 0;          // ptr2 is now a null pointer
```

포인터가 null이면 부울값 `false`로, null이 아닌 경우에는 불린 값 `true`로 반환된다. 

따라서 조건부를 사용하여 포인터가 null인지 여부를 테스트할 수 있다.

```cpp
double* ptr { 0 };

// pointers convert to boolean false if they are null, and boolean true if they are non-null
if (ptr)
    cout << "ptr is pointing to a double value.";
else
    cout << "ptr is a null pointer.";
```

*다른 값을 지정하지 않을 땐 포인터를 null 값으로 초기화하자.*



### Dereferencing null pointers

null 포인터를 역참조하면 정의되지 않은 동작이 발생한다. 대부분은 응용 프로그램이 중단된다.

개념적으로 이것은 의미가 있다. 포인터를 역참조한다는 것은 **'포인터가 가리키는 주소를 이동하여 그 값에 접근'**을 의미한다. 널 포인터에는 주소가 없다. 따라서 해당 주소의 값에 접근하려면 어떻게 해야 할까?



### nullptr in C++ 11

C++ 11에서는 `nullptr`이라는 새로운 키워드를 도입했다. 

`nullptr`은 `true` 및 `false`와 같은 불린 키워드와 마찬가지로 **키워드 및 r-value 상수**다.

```cpp
int *ptr { nullptr }; // note: ptr is still an integer pointer, just set to a null value
```

C++은 암시적으로 nullptr을 포인터 모든 유형으로 변환한다. 

따라서 위의 예에서 nullptr은 암시적으로 정수 포인터로 변환된 다음 nullptr 값이 ptr에 할당된다. 이것은 정수 포인터 ptr을 널 포인터로 만드는 효과가 있다.

이것은 nullptr 리터럴로 함수를 호출하는 데에도 사용할 수 있다.

```cpp
#include <iostream>

void doSomething(double* ptr)
{
    // pointers convert to boolean false if they are null, and boolean true if they are non-null
    if (ptr)
        std::cout << "You passed in " << *ptr << '\n';
    else
        std::cout << "You passed in a null pointer\n";
}

int main()
{
    doSomething(nullptr); // the argument is definitely a null pointer (not an integer)

    return 0;
}
```

*Best practice: C++ 11에서는 nullptr을 사용해서 포인터를 null 값으로 초기화하자.*



### std::nullptr_t in C++11

C++ 11은 또한 `<cstddef>` 헤더에 잇는 `std::nullptr_t`라는 새로운 유형을 도입했다. 

`std::nullptr_t`는 `nullptr` 하나의 값만 가질 수 있다. 

> nullptr 인자를 받아들이는 함수를 작성하고자 하면 어떤 타입의 매개 변수를 만들까? `std::nullptr_t`이다.

```cpp
#include <cstddef> // for std::nullptr_t

void doSomething(std::nullptr_t ptr)
{
    std::cout << "in doSomething()\n";
}

int main()
{
    doSomething(nullptr); // call doSomething with an argument of type std::nullptr_t

    return 0;
}
```

---

[정리: 소년코딩](https://boycoding.tistory.com/200?category=1009770)

---

