---
title: Dynamically allocating arrays
tag: cpp
---



## 동적으로 배열 할당하기

배열을 동적으로 할당하면 런타임 동안에 배열 길이를 선택할 수 있다.

```cpp
#include <iostream>

int main()
{
    std::cout << "Enter a positive integer: ";
    int length;
    std::cin >> length;

    int *array = new int[length]; // 배열 버전 new 연산자를 사용한다. 배열 길이는 상수가 아니여도 된다.
    std::cout << "I just allocated an array of integers of length " << length << '\n';

    array[0] = 5; // 요소 0을 값 5로 설정

    delete[] array; // 배열 할당 해제를 위해 배열 버전 delete 연산자를 사용한다.

    return 0;
}
```

배열을 할당하기 때문에 `new[]`와 같은 배열 버전 new 연산자를 사용

 C++에서 많은 메모리를 할당해야 하는 프로그램은 동적으로 메모리를 할당하는 게 일반적이다.



### Dynamically deleting arrays

+ delete[]

  단일 변수 대신 여러 변수를 정리해야 한다는 것을 CPU에 알려준다. 

+ 배열 해제 시 어떻게 해제할 메모리양을 알 수 있을까? 

  `new[]`가 변수에 할당된 메모리양을 추적하지만 `delete[]`를 이용한 적절한 메모리 해제는 불행하게도 프로그래머에게 이 크기∙길이에 대한 접근 권한을 주지 않는다. (알 수 없다.)



### Dynamic arrays are almost identical to fixed arrays

동적 배열은 배열의 첫번째 요소를 가리키는 포인터다. 

이것은 고정 배열이 포인터로 변환된 형태와 동일하게 작동하지만, `delete []`를 통해 동적 배열을 할당 해제해야 한다.



### Initiallizing dynamically allocated arrays

```cpp
int* array = new int[length]();
```

C++ 11 이전에는 동적 배열을 0이 아닌 값으로 초기화하는 간단한 방법이 없었기 때문에 반복해서 요소 값을 명시적으로 지정해야 했다.

> 하지만 C++ 11부터 초기화 리스트(initializer list)를 사용해 동적 배열을 초기화할 수 있다!

```cpp
int fixedArray[5] = { 9, 7, 5, 3, 1 };     // initialize a fixed array in C++03
int* array = new int[5] { 9, 7, 5, 3, 1 }; // initialize a dynamic array in C++11
```

일관성을 위해 C++ 11에서는 유니온 초기화를 사용하여 고정 배열도 초기화할 수 있다.

```cpp
int fixedArray[5] { 9, 7, 5, 3, 1 };     // initialize a fixed array in C++11
char fixedArray[14] { "Hello, world!" }; // initialize a fixed array in C++11
```

주의사항: 동적 배열을 C 스타일 문자열로 초기화할 수 없다.

```cpp
char* array = new char[14] { "Hello, world!" }; // doesn't work in C++11
```

> 이렇게 해야 하는 경우 다음과 같이 해야 한다.

또한, **동적 배열은 명시적으로 길이를 설정해 선언해야 한다.**

```cpp
int fixedArray[] {1, 2, 3}; // okay: implicit array size for fixed arrays

int* dynamicArray1 = new int[] {1, 2, 3}; // not okay: implicit size for dynamic arrays

int* dynamicArray2 = new int[3] {1, 2, 3}; // okay: explicit size for dynamic arrays
```

---

 [정리: 소년코딩](https://boycoding.tistory.com/205?category=1009770)

---