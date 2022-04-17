---
title: Function pointer
tag: cpp
---







## 함수 포인터

이와 유사하게 **함수 포인터(function pointer)**는 함수를 가리키는 변수다. 즉, 함수의 주소를 저장하는 변수다.



### 함수에 대한 포인터 (pointer to function)

비 상수 함수 포인터(non-const function pointer) 생성하는 문법은 C++에서 볼 수 있는 못생긴 문법 중 하나다.

```cpp
// fcnPtr 는 인수가 없고 정수를 반환하는 함수에 대한 포인터다.
int (*fcnPtr)();
```

위 코드에서 *fcnPtr*은 인수가 없고 정수를 반환하는 함수에 대한 포인터다.

<u>(즉, 함수 포인터) 그러므로 이 타입과 같은 함수를 가리킬 수 있다.</u>

괄호가 필요한 이유는`int* fcnPtr()`과 같은 코드는 정수에 대한 포인터를 반환하는 인수가 없는 함수 fcnPtr의 전방 선언으로 해석되기 때문에 우선순위를 위해서다.

또한, 오류를 발생시키는 흔한 실수는 아래와 같다:

```cpp
fcnPtr = goo();
```

<u>상수 함수 포인터를 만들기 위해서는 `*` 뒤에 `const` 키워드를 사용하면 된다.</u>

```cpp
int (*const fcnPtr)();
```

만약 int 앞에 const가 오면 함수가 const int를 반환한다는 것을 의미한다.



### 함수 포인터에 함수 할당

함수 포인터는 함수로 초기화 할 수 있다.

```cpp
int foo()
{
    return 5;
}

int goo()
{
    return 6;
}

int main()
{
    int (*fcnPtr)() = foo; // fcnPtr points to function foo
    fcnPtr = goo; // fcnPtr now points to function goo

    return 0;
}
```

C++는 기본 자료형과 달리 필요할 경우 함수를 함수 포인터로 암묵적으로 변환하므로 주소 연산자 &를 사용할 필요가 없다.



### 함수 포인터를 사용해서 함수 호출

함수 포인터로 실제 함수를 호출할 수 있다. 

호출하는 두 가지 방법이 있는데, 첫 번째는 명시적인 역참조 방법이다.

```cpp
int foo(int x)
{
    return x;
}

int main()
{
    int (*fcnPtr)(int) = foo; // fcnPtr에 foo함수를 할당한다.
    (*fcnPtr)(5); // foo(5)를 fcnPtr로 호출한다.

    return 0;
}
```

두 번째 방법은 암시적 역참조를 통한 방법이다.

```cpp
fcnPtr(5); // foo(5)를 fcnPtr로 호출한다.
```

<u>일반적인 함수 이름은 함수의 포인터가 되기 때문에 암시적 추론을 통한 역참조 방법은 일반 함수 호출과 똑같이 생겼다. 현대 컴파일러는 대부분 이 방법을 지원한다.</u>



### 다른 함수의 인자로 함수 전달하기 (Passing functions as arguments to other functions)

<u>함수 포인터의 가장 유용한 기능은 함수를 다른 함수에 전달하는 것이다.</u> 

<u>다른 함수에 대한 인수로써 사용되는 함수를 콜백 함수라고 부르기도 한다.</u>

> 배열 정렬을 생각해보자. 정렬을 수행하는 기본적인 함수에서 사용자가 오름차순으로 할지 내림차순으로 할지 정할 수 있는 함수를 예로 한다.

+ 모든 정렬 알고리즘은 유사한 개념으로 작동한다. 

숫자 목록을 통해 반복되고, 숫자의 쌍을 비교하며, 그 비교 결과를 바탕으로 숫자의 순서를 바꾼다. 

결과적으로, 비교를 변경함으로써 나머지 분류 코드에 영향을 주지 않고 함수의 정렬 방식을 변경할 수 있다.

```cpp
void SelectionSort(int* array, int size)
{
    for (int i = 0; i < size; ++i)
    {
        int smallestIndex = i;

        for (int j = i + 1; j < size; ++j)
        {
            if (array[smallestIndex] > array[j]) // 비교 부분
                smallestIndex = j;
        }

        std::swap(array[smallestIndex], array[j]);
    }
}
```

위에서 비교 부분을 함수를 이용해서 대체할 수 있다. 

비교 함수는 두 개의 정수를 비교한 다음 부울 값을 반환함으로써 교체 여부를 나타낼 수 있다.

```cpp
bool ascending(int x, int y)
{
    return x > y; // 첫 번째 요소(x)가 두 번째 요소(y) 보다 크면 교환한다.
}
```

다음은 `ascending()` 함수를 이용해서 비교를 수행하는 선택 정렬 함수다.

```cpp
void SelectionSort(int* array, int size)
{
    for (int i = 0; i < size; ++i)
    {
        int smallestIndex = i;

        for (int j = i + 1; j < size; ++j)
        {
            if (ascending(array[sammlestIndex], array[j])) // 비교 부분
                smallestIndex = j;
        }

        std::swap(array[smallestIndex], array[j]);
    }
}
```

이제 하드코딩된 비교 함수를 사용하는 대신 <u>호출자가 비교 작업을 결정할 수 있도록 함수 포인터를 사용해보자.</u>

호출자의 비교 함수는 두 정수를 비교하고 부울 값을 반환하므로 이러한 함수에 대한 포인터는 아래와 같을 것이다.

```cpp
bool (*comparisonFcn)(int, int);
```

<u>따라서 호출자가 정렬 함수에 원하는 비교 함수의 포인터를 세 번째 매개 변수로 전달하도록 허용한 다음 호출자의 함수를 사용하여 비교를 수행할 수 있다.</u> 

다음은 함수 포인터 매개변수를 사용하여 사용자 정의 비교를 수행하는 선택 정렬이다.

```cpp
void SelectionSort(int* array, int size, bool (*comparisonFcn)(int, int))
{
    for (int i = 0; i < size; ++i)
    {
        int smallestIndex = i;

        for (int j = i + 1; j < size; ++j)
        {
            if (comparisonFcn(array[sammlestIndex], array[j])) // 비교 부분
                smallestIndex = j;
        }

        std::swap(array[smallestIndex], array[j]);
    }
}

bool ascending(int x, int y)
{
    return x > y; // 오름차순
}

bool descending(int x, int y)
{
    return x < y; // 내림차순
}

void printArray(int *array, int size)
{
    for (int index=0; index < size; ++index)
        std::cout << array[index] << " ";
    std::cout << '\n';
}

int main()
{
    int array[9] = { 3, 7, 9, 5, 6, 1, 8, 2, 4 };

    // 내린차순 선택정렬
    selectionSort(array, 9, descending);
    printArray(array, 9);

    // 오름차순 선택정렬
    selectionSort(array, 9, ascending);
    printArray(array, 9);

    return 0;
}

9 8 7 6 5 4 3 2 1
1 2 3 4 5 6 7 8 9
```

이제 호출자가 원하는 비교 방식으로 정렬할 수 있다.



### 함수 포인터를 더 예쁘게 만들기

`typedef`를 사용해서 함수 포인터를 일반 변수처럼 보이게 할 수 있다.

```cpp
typedef bool (*validateFcn)(int, int);
```

위 코드는 인수로 두 개의 int를 가지고 bool 반환하는 *validateFcn*라는 함수 포인터를 정의한다.

이제 이렇게 하는 대신:

```cpp
bool validate(int x, int y, bool (*fcnPtr)(int, int)); // ugly
```

```cpp
bool validate(int x, int y, validateFcn pfcn) // clean
```

C++ 11에서는 `using`을 사용해서 함수 포인터 타입에 대한 별칭을 만들 수 있다.

```cpp
using validateFcn = bool(*)(int, int); // type alias
```

typedef보다 좀 더 자연스럽게 읽을 수 있다.



### C++ 11에서의 std::function

C++ 11에서는 표준 라이브러리 `<function>` 헤더에 정의되어 있는 `std::function`을 이용해서 함수 포인터를 정의할 수 있다.

```cpp
#include <functional>
bool validate(int x, int y, std::function<bool(int, int)> fcn); //std::function<반환타입(인수..)>
```

보시다시피, 반환 타입과 매개 변수는 꺾쇠(<>)안에 들어 있다. 

매개 변수를 꺾쇠 속 괄호 안에 둠으로써 좀 더 명시적으로 읽을 수 있다. 

매개 변수가 없다면 괄호 안의 내용을 비워두면 된다.

```cpp
#include <functional>
#include <iostream>

int foo()
{
    return 5;
}

int goo()
{
    return 6;
}

int main()
{
    std::function<int()> fcnPtr; // int를 반환하고 매개 변수가 없는 함수 포인터 변수 fctPtr 선언
    fcnPtr = goo; // fcnPtr은 함수 goo를 가리킨다.
    std::cout << fcnPtr(); // 일반적인 함수처럼 함수를 호출할 수 있다.

    return 0;
}
```



함수 포인터는 주로 함수를 다른 함수에 전달할 때 유용하다. 함수 포인터를 선언하는 원시적인 방법은 보기 나쁘고 오류를 발생시키기 쉽우므로 `typedef`, `using` 또는 `std::function`을 사용하는 게 좋다.

---

 [정리: 소년코딩](https://boycoding.tistory.com/233?category=1011971)

----

https://boycoding.tistory.com/235?category=1011971