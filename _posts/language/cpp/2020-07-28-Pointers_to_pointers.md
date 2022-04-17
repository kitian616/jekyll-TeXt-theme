---
title: Pointers to pointers and dyanmic mulitdimensional arrays
tag: cpp
---



## 이중 포인터와 동적 다차원 배열

포인터를 가리키는 포인터는 이렇게 예상 할 수 있다. : **다른 포인터의 주소를 보유하는 포인터**

`int`에 대한 일반 포인터는 하나의 별표(`*`)만 사용해서 선언한다.

```cpp
int* ptr; // pointer to an int, one asterisk
```

`int`에 대한 포인터를 가리키는 포인터는 두 개의 별표(`**`)를 사용하여 선언한다. 이것을 **이중 포인터**라고도 부른다.

```cpp
int** ptrptr; // pointer to a pointer to an int, two asterisks
```

이중 포인터는 일반 포인터처럼 작동한다.

```cpp
int value = 5;

int* ptr = &value;
std::cout << *ptr; // dereference pointer to int to get int value

int** ptrptr = &ptr;
std::cout << **ptrptr; // first dereference to get pointer to int, second dereference to get int value

// 5
// 5
```

+ 이중 포인터를 직접 값으로 설정할 수 없다.

  주소 연산자(`&`)는 l-value가 필요하지만 `&value`는 r-value이기 때문이다.

```cpp
int value = 5;
int** ptrptr = &&value; // not valid
```

+ 그러나 이중 포인터를 null로 설정할 수 있다.

```cpp
int** ptrptr = nullptr; // use 0 instead prior to C++11
```



### Arrays of pointers

동적으로 포인터의 배열을 할당할 때 이중 포인터가 흔히 쓰인다.

```cpp
int** array = new int*[10]; // allocate an array of 10 int pointers
```

배열의 요소가 정수 대신 '정수에 대한 포인터'라는 것만 다를 뿐 일반 동적 할당 배열과 같다.



### Tow-dimensional dynamically allocated arrays

- 동적으로 2차원 배열을 할당할 때도 이중 포인터가 쓰인다.



```cpp
//2차원 고정 배열 선언
int array[10][5];
```

동적으로 2차원 배열을 할당하는 것은 좀 더 어렵다. 처음에는 이렇게 시도해볼 수 있다.

```cpp
int** array = new int[10][5]; // won’t work!
```

그러나 위 코드는 작동하지 않는다.

+ 가능한 방법은 두 가지가 있다. <u>맨 오른쪽 배열 차원이 컴파일 타임 상수라면</u> 다음과 같이 할 수 있다.

```cpp
int (*array)[5] = new int[10][5];
```

적절한 우선순위를 보장하려면 괄호가 필요하다. C++ 11 이상부터는 auto 키워드를 사용하는 게 좋다.

```cpp
auto array = new int[10][5]; // so much simpler!
```

그러나 불행하게도 맨 오른쪽 차원이 컴파일 타임 상수가 아니라면 좀 더 복잡하다.

- 먼저 포인터의 배열을 할당한 다음 포인터의 배열을 반복해서 각 요소에 동적 배열을 할당해야 한다.

```cpp
int** array = new int*[10]; // allocate an array of 10 int pointers — these are our rows
for (int count = 0; count < 10; ++count)
    array[count] = new int[5]; // these are our columns
```

그런 다음 일반적인 방법으로 배열에 접근할 수 있다.

```cpp
array[9][4] = 3; // This is the same as (array[9])[4] = 3;
```

2차원 동적 배열을 할당 해제하려면 루프가 필요하다.

```cpp
for (int count = 0; count < 10; ++count)
    delete[] array[count];
delete[] array; // this needs to be done last
```

할당한 순서의 반대로 해제해야 한다. 

전체 배열을 먼저 해제하면 배열의 요소를 해제하기 위해 전체 배열에 접근해야 하므로 정의되지 않은 동작이 발생한다.

---

[정리: 소년코딩](https://boycoding.tistory.com/212?category=1009770)

---