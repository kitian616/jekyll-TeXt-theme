---
title: Pointer and array
tag: cpp
---



## 포인터와 배열

### Similarities between pointers and fied arrays

**배열 변수는 마치 포인터인 것처럼 배열의 첫 번째 요소의 주소를 가지고 있다.** 다음 프로그램에서 이를 볼 수 있다.

C++에서 배열과 포인터가 같다고 생각하는 것은 일반적인 오류다. 두 요소 모두 배열의 첫 번째 요소를 가리키고 있지만, 형식 정보가 다르다. 위의 경우 `array`는 `int[5]`의 타입이고, 배열에 대한 포인터는 `int*` 타입이다.

포인터를 통해 배열의 모든 요소에 접근할 수 있지만, 배열 타입에서 파생된 정보(Ex. 배열 길이)는 포인터에서 접근할 수 없다.

대부분은 이를 통해 고정 배열과 포인터를 효과적으로 처리할 수 있다.

예를 들어, 배열을 역참조하여 첫 번째 요소의 값을 얻을 수 있다.

```cpp
int array[5] = { 9, 7, 5, 3, 1 };

// dereferencing an array returns the first element (element 0)
cout << *array; // will print 9!

char name[] = "Jason"; // C-style string (also an array)
cout << *name; // will print 'J'
```

실제로 배열 자체를 역참조하지는 않는다. 

(`int[5]` 타입의) 배열은 int 타입의 포인터로 암시적으로 변환되고 포인터를 역참조하여 포인터가 보유하고 있는 메모리의 주소의 값을 얻을 수 있다. (배열의 첫 번째 요소의 값)

또한, 배열을 가리키는 포인터를 지정할 수 있다.



### Differencees between pointers and fixed arrays

`sizeof()` 연산자를 사용할 때 주된 차이가 발생한다. 

+ 고정 배열에서 `sizof()` 연산자는 전체 크기를 반환한다. 

- (배열 길이 * 요소 크기) 포인터에서 사용하면 `sizeof()` 연산자는 메모리 주소의 크기(바이트)를 반환한다.

두 번째 차이는 주소 연산자 `&`를 사용할 때 발생한다. 

- 포인터의 주소를 취하면 포인터 변수의 메모리 주소가 산출된다. 

- 배열 주소를 선택하면 포인터가 전체 배열로 돌아간다. 

  또한, 이 포인터는 배열의 첫 번째 요소를 가리키지만, 타입 정보가 다르다.



### Revisiting passing fixed arrays to functions

함수에 배열로 인수를 전달하면 <u>고정 배열을 포인터로 변환되어 함수에 전달된다.</u>

```cpp
#include <iostream>

void printSize(int* array)
{
    // array is treated as a pointer here
    std::cout << sizeof(array) << '\n'; // prints the size of a pointer, not the size of the array!
}

int main()
{
    int array[] = { 1, 1, 2, 3, 5, 8, 13, 21 };
    std::cout << sizeof(array) << '\n'; // will print sizeof(int) * array length

    printSize(array); // the array argument decays into a pointer here

     return 0;
}

// 32, array 원소 전체의 바이트 값
// 4, array[0] 인자 하나의 바이트 값
```

매개 변수가 고정 배열로 선언된 경우에도 이 문제가 발생한다.

```cpp
#include <iostream>

// C++ will implicitly convert parameter array[] to *array
void printSize(int array[])//intiger
{
    // array is treated as a pointer here, not a fixed array
    std::cout << sizeof(array) << '\n'; // prints the size of a pointer, not the size of the array!
}

int main()
{
    int array[] = { 1, 1, 2, 3, 5, 8, 13, 21 };
    std::cout << sizeof(array) << '\n'; // will print sizeof(int) * array length

    printSize(array); // the array argument decays into a pointer here

     return 0;
}

// 32
// 4
```

위의 예에서 C++는 배열 구문([])을 사용해서 매개 변수를 포인터 구문(*)으로 변환한다. 

이는 다음 두 함수 선언이 같음을 의미한다.

```cpp
void printSize(int array[]);
void printSize(int* array);
```

일부 프로그래머는 [] 구문을 선호한다. 

함수가 값에 대한 포인터가 아니라 배열이 필요하다는 것을 분명히 하기 때문이다. 

그러나 대부분은 포인터가 배열의 크기를 알지 못하기 때문에 배열 크기를 별도의 매개 변수로 전달해야 한다.

> 포인터 구문을 사용하는 것이 좋다. 매개 변수가 조정 배열이 아닌 포인터로 취급되고 `sizeof()`와 같은 특정 연산이 매개 변수가 포인터인 것처럼 작동한다는 것을 명확히 하기 때문이다.



### An intro to pass by address

함수로 전달될 때 배열이 포인터로 변환되므로 함수에서 배열을 변경하면 실제 배열을 변경하는 것과 같다.

```cpp
#include <iostream>

// parameter ptr contains a copy of the array's address
void changeArray(int* ptr)
{
    *ptr = 5; // so changing an array element changes the _actual_ array
}

int main()
{
    int array[] = { 1, 1, 2, 3, 5, 8, 13, 21 };
    std::cout << "Element 0 has value: " << array[0] << '\n';

    changeArray(array);

    std::cout << "Element 0 has value: " << array[0] << '\n';

     return 0;
}
/*
Element 0 has value: 1
Element 0 has value: 5
*/
```

`changeArray()` 함수를 호출하면 배열이 포인터로 들어가고, 해당 포인터의 값(배열의 첫 번째 요소 메모리의 주소값)이 `changeArray()` 함수의 `ptr` 매개 변수로 복사된다. 

`ptr`의 값이 배열 주소의 복사본이지만, `ptr`은 여전히 실제 배열을 가리킨다. (복사본이 아니다!) 

> 결과적으로 `ptr`을 역참조하면 실제 배열이 역참조 된다.



### Arrays in structs and classes don’t decay

구조체(struct) 또는 클래스(class)의 일부인 배열은 전체 구조체 또는 클래스가 함수로 전달 될 때 변환되지 않는다. 

이렇게 하면 원하는 경우 변환을 방지하는 유용한 방법이 생기며, 나중에 배열을 사용하는 클래스를 작성할 때 유용하다.

---

[정리: 소년코딩](https://boycoding.tistory.com/201?category=1009770)

---