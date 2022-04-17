---
title: Range-based for statement, void pointer
tag: cpp
---



## 범위 기반 for 문

C++ 11에서는 **범위 기반 for 문(ranged-based for statement)**이라는 새로운 유형의 루프를 도입하여 더 간단하고 안전하게 배열 등의 모든 요소를 반복하는 방법을 제공한다.

### Ranged-based for loops

범위 기반 for 문(ranged-based for statement)의 문법은 다음과 같다.

```cpp
for (element_declaration : array)
    statement;
```

루프는 각 `array`의 요소를 반복하여 `element_declaration`에 선언된 변수에 현재 배열 요소의 값을 할당한다. 

최상의 결과를 얻으려면 `element_declaration`이 <u>배열 요소와 같은 자료형이어야 한다.</u> 그렇지 않으면 형 변환이 발생한다.



```cpp
//범위 기반 for 문을 사용해서 fibonacci 배열의 모든 요소를 출력하는 예제
#include <iostream>

int main()
{
    int fibonacci[] = { 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 };
    for (int number : fibonacci) // iterate over array fibonacci
       std::cout << number << ' '; // we access the array element for this iteration through variable number

    return 0;
}

// 0 1 1 2 3 5 8 13 21 34 55 89
```

*배열에 대한 인덱스가 아니라 배열의 요소값이 number에 할당된다.*



### Ranged-based for loops and the auto keyword

`element_declaration`은 <u>배열 요소와 같은 자료형을 가져야 하므로, auto 키워드를 사용해서 C++이 자료형을 추론하도록 하는 것이 이상적이다.</u>

```cpp
#include <iostream>

int main()
{
    int fibonacci[] = { 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 };
    for (auto number : fibonacci) // type is auto, so number has its type deduced from the fibonacci array
       std::cout << number << ' ';

    return 0;
}
```



### Ranged-based for llops and references

위에서 본 예제들에서 `element_declaration`은 값으로 선언된다.

```cpp
int array[5] = { 9, 7, 5, 3, 1 };
    for (auto element: array) // element will be a copy of the current array element
        std::cout << element << ' ';
```

+ 반복된 각 배열 요소가 `element`에 복사된다. 배열 요소를 복사하는 것은 비용이 많이들 수 있다. 

>  다행히도 다음과 같이 참조를 사용할 수 있다.

```cpp
int array[5] = { 9, 7, 5, 3, 1 };
    for (auto &element: array) // The ampersand makes element a reference to the actual array element, preventing a copy from being made
        std::cout << element << ' ';
```

위 예제에서 `element`는 현재 반복된 배열 요소에 대한 참조이므로 <u>값이 복사되지 않는다.</u>

<u>또한, `element`를 수정하면 배열의 요소에 영향을 미친다.</u>

<u>읽기 전용으로 사용하려는 경우 `element`를 const로 만드는 것이 좋다.</u>

```cpp
int array[5] = { 9, 7, 5, 3, 1 };
    for (const auto& element: array) // element is a const reference to the currently iterated array element
        std::cout << element << ' ';
```

> *성능상의 이유로 ranged-based for 루프에서 참조 또는 const 참조를 사용하는 게 좋다.*



### Rewriting the max scores example using a ranged-based for loop

```cpp
#include <iostream>

int main()
{
    const int numStudents = 5;
    int scores[numStudents] = { 84, 92, 76, 81, 56 };
    int maxScore = 0; // keep track of our largest score
    for (const auto& score: scores) // iterate over array scores, assigning each value in turn to variable score
        if (score > maxScore)
            maxScore = score;

    std::cout << "The best score was " << maxScore << '\n';

    return 0;
}
```



### Ranged-based for loops and non-arrays

범위 기반 for 루프(ranged-based for loop)는 고정 배열뿐만 아니라 `std::vector`, `std::list`, `std::set`, `std::map`과 같은 구조에서도 작동한다. 

```cpp
#include <vector>
#include <iostream>

int main()
{
    std::vector<int> fibonacci = { 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 }; // note use of std::vector here rather than a fixed array
    for (const auto& number : fibonacci)
        std::cout << number << ' ';

    return 0;
}
```



###  Ranged-based for loops doesn’t work with pointers to an array

포인터로 변환된 배열에서 범위 기반 for 루프를 사용할 수 없다. (배열의 크기를 알지 못하기 때문이다.)

```cpp
#include <iostream>

int sumArray(int array[]) // array is a pointer
{
    int sum = 0;
    for (const auto& number : array) // compile error, the size of array isn't known
        sum += number;

    return sum;   
}

int main()
{
     int array[5] = { 9, 7, 5, 3, 1 };
     std::cout << sumArray(array); // array decays into a pointer here
     return 0;
}
```

마찬가지로, 동적 배열은 같은 이유로 범위 기반 for 루프와 작동하지 않는다.

---

[정리: 소년코딩](https://boycoding.tistory.com/210?category=1009770 )

---

## void pointer

<u>제네릭 포인터(generic pointer)라고도 불리는 **void pointer**는 모든 데이터 자료형을 가리킬 수 있는 특별한 타입의 포인터다.</u>

 void 포인터는 `void` 키워드를 사용하여 일반 포인터처럼 선언한다.

```cpp
int nValue;
float fValue;

struct Something
{
    int n;
    float f;
};

Something sValue;

void* ptr; // ptr is a void pointer

ptr = &nValue; // valid
ptr = &fValue; // valid
ptr = &sValue; // valid
```

그러나 void 포인터는 어떤 데이터 자료형의 객체를 가리키는 알지 못하기 때문에 <u>직접 역참조할 수 없다.</u>

그러므로 역참조를 하기 전에 먼저 void 포인터를 다른 포인터 유형으로 명시적 형 변환 해야 한다.

```cpp
int value = 5;
void* voidPtr = &value;

//cout << *voidPtr << endl; // illegal: cannot dereference a void pointer

int* intPtr = static_cast<int*>(voidPtr); // however, if we cast our void pointer to an int pointer...

cout << *intPtr << endl; // then we can dereference it like normal

// 5
```



### Void pointer miscellany

void 포인터를 null 값으로 설정할 수 있다.

```cpp
void* ptr = 0; // ptr is a void pointer that is currently a null pointer
```

일부 컴파일러에서는 동적으로 할당 된 메모리를 가리키는 void 포인터를 삭제할 수 있지만 정의되지 않은 동작이 발생할 수 있다.

포인터 산술은 포인터가 어떤 크기의 객체를 가리키는지 알아야 하므로 포인터를 적절하게 증감할 수 없는 void 포인터에서는 포인터 연산을 수행할 수 없다.

void 참조(reference) 같은 건 존재하지 않는다.

---

[정리: 소년코딩](https://boycoding.tistory.com/211?category=1009770)

---