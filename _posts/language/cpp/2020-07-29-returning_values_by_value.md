---
title: Returning values by value, reference and address
tag: cpp
---



## 값, 참조 및 주소로 값 반환

값, 참조 또는 주소를 사용해서 함수에서 호출자로 값을 반환하는 것은 함수의 매개 변수에 전달하는 것과 거의 똑같은 방식으로 작동한다.

 각 방법에 대한 동일한 장단점이 모두 있다. <u>이 둘의 주요 차이점은 단순히 데이터 흐름과 방향이 바뀌었다는 것이다.</u> 

그러나 함수의 지역 변수가 범위를 벗어나 함수가 반환될 때 파괴되므로 각 반환 유형에 대한 영향을 고려해야 한다.

 

### Return by value

**값으로 반환(return by value)**은 가장 간단하고 안전하다. 

<u>값이 반환되면 복사본이 호출자에게 반환된다.</u> 

>  값으로 반환의 또 다른 장점은 범위 지정 문제를 걱정할 필요 없다는 것이다. 

함수 내에서 선언된 지역 변수를 반환할 수  있다. 함수가 반환되기 전에 지역 변수가 평가되고, 값의 복사본이 호출자에게 반환되기 때문에 함수의 지역 변수가 함수의 끝에서 범위를 벗어날 때 아무런 문제가 없다.

```cpp
int doubleValue(int x)
{
    int value = x * 2;

    return value; // A copy of value will be returned here
} // value goes out of scope here
```

<u>값으로 반환은 함수 내에서 선언된 (지역)변수를 반환하거나 값으로</u> <u>전달된 매개 변수를 반환할 때 가장 적절하다.</u> 

> 그러나 값으로 전달과 마찬가지로 구조체 및 클래스의 경우 값으로 반환은 느리다.

**값으로 반환을 사용해야 하는 경우:**

- <u>함수 내에서 선언된 (지역)변수를 반환할 때</u>
- <u>값으로 전달된 매개 변수를 반환할 때</u>

**값으로 반환을 사용해야 하지 않아야 하는 경우:**

- 배열이나 포인터를 반환할 때 (주소로 반환 사용)
- 구조체 또는 클래스를 반환할 때 (참조로 반환 사용)



### Return by address

**주소로 반환(return by address)**은 호출자에게 변수의 주소를 반환한다.

그러나 <u>함수 안에서 선언된 지역 변수의 주소를 반환하려고 하면 프로그램에서 정의되지 않은 동작이 발생한다.</u> 

위에서 볼 수 있듯이, <u>주소가 호출자에게 반환된 직후 *value*가 소멸한다.</u> 

<u>최종적으로 할당되지 않은 메모리(dangling pointer)이기 때문에, 호출자가 이것을 사용하면 문제가 발생할 수 있다.</u> 

이것은 초보 프로그래머가 자주 범하는 실수다. 

<u>반환하는 주소가 유효한 변수가 되도록 하는 것이 중요하다.</u>

주소로 반환은 동적 할당된 메모리를 호출자에게 반환하는 데도 사용한다.

```cpp
int* allocateArray(int size)
{
    return new int[size];
}

int main()
{
    int* array = allocateArray(25);

    // do stuff with array

    delete[] array;
    return 0;
}
```

**주소로 반환을 사용해야 하는 경우:**

- 동적 할당된 메모리를 반환할 때
- 주소로 전달된 매개 변수를 반환할 때

**주소로 반환을 사용해야 하지 않아야 하는 경우:**

- 함수 내에서 선언된 (지역)변수를 반환할 때 (값으로 반환 사용)
- 참조로 전달된 구조체 또는 클래스를 반환할 때 (참조로 반환 사용)



### Return by reference

변수가 **참조로 반환(return by reference)**되면 변수에 대한 참조가 호출자에게 반환된다. 

<u>그런 다음 호출자는 이 참조를 사용해서 변수를 계속 수정할 수 있다. 참조로 반환은 빠르므로 구조체와 클래스를 반환할 때 유용하다.</u>

그러나 <u>주소로 반환처럼 참조로 함수 지역 변수를 반환하면 안 된다.</u> 다음 예제를 보자.

```cpp
int& doubleValue(int x)
{
    int value = x * 2;

    return value; // return a reference to value here
} // value is destroyed here
```

위 프로그램은 함수가 반환될 때 소멸할 *value*에 대한 참조를 반환한다. 

이것은 호출자가 쓰레기에 대한 참조를 받는다는 것을 의미한다. 다행히도, 컴파일러는 이것을 시도하면 경고나 에러를 뱉을 것이다.

<u>참조로 반환은 호출자에게 참조로 전달된 매개 변수를 반환하는 데도 사용한다.</u> 

다음 예제에서는 참조로 함수에 전달된 배열의 요소를 참조로 반환한다.

```cpp
#include <array>
#include <iostream>

// Returns a reference to the index element of array
int& getElement(std::array<int, 25>& array, int index)
{
    // we know that array[index] will not be destroyed when we return to the caller (since the caller passed in the array in the first place!)
    // so it's okay to return it by reference
    return array[index];
}

int main()
{
    std::array<int, 25> array;

    // Set the element of array with index 10 to the value 5
    getElement(array, 10) = 5;

    std::cout << array[10] << '\n';

    return 0;
}

// 5
```

*getElement(array, 10)*을 호출하면 인덱스 10인 *array*에 대한 참조를 반환한다. *main()* 함수에서는 이 참조를 사용해서 해당 요소에 값 *5*를 할당한다.

**참조로 반환을 사용해야 하는 경우:**

- <u>참조 매개 변수를 반환할 때</u>
- <u>함수에 전달된 배열의 요소를 반환할 때</u>
- <u>함수의 끝에서 소멸하지 않는 구조체나 클래스를 반환할 때</u>

**참조로 반환을 사용해야 하지 않아야 하는 경우:**

- 함수 내에서 선언된 (지역)변수를 반환할 때 (값으로 반환 사용)
- 기본 배열이나 포인터 값을 반환할 때 (주소로 반환 사용)

---

[정리: 소년코딩](https://boycoding.tistory.com/219?category=1011971)

---