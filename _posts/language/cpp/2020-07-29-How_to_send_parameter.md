---
title: How to send fucntion parameters
tag: cpp
---



함수에 인수를 전달하는 3가지 방법이 있다.

- 값으로 전달(pass by value)
- 참조로 전달(pass by reference)
- 주소로 전달(pass by address)

# Pass by value

## 값으로 전달

C++에서 포인터가 아닌 인수는 값으로 전달된다. 인수가 값으로 전달되면 인수의 값은 해당 함수 매개 변수의 값으로 복사된다.

인수의 복사본이 함수로 전달되므로 원래 인수는 함수 안에서 수정할 수 없다.

값으로 전달된 함수 매개 변수도 `const`로 만들 수 있다. 함수가 매개 변수의 값을 변경하지 못하게 할 때 유용하다.

### 값으로 전달의 장단점 (Pros and cons of pass by value)

**값으로 전달의 장점:**

- 호출되는 함수에 의해 인수가 변경되지 않으므로 예기치 못한 부작용이 발생하지 않는다.

**값으로 전달의 단점:**

- 함수를 여러 번 호출하는 경우 구조체(struct) 및 클래스(class)를 복사하는데 큰 비용이 들어 성능이 저하될 수 있다.

**값으로 전달을 사용해야 하는 경우:**

- 기본 자료형과 열거자를 전달할 때(함수가 인수를 변경할 필요가 없을 때)

**값으로 전달을 사용하지 않아야 하는 경우:**

- 배열(array), 구조체(struct) 및 클래스(class)를 전달할 때

값에 의한 전달은 함수가 인수를 변경할 필요가 없을 때 기본 자료형 변수를 전달하는 가장 좋은 방법이다.

---

# Pass by reference

## 참조로 전달

변수를 참조로 전달하려면 함수 매개 변수를 일반 변수가 아닌 참조로 선언해야 한다.

변수에 대한 참조는 변수 자체와 똑같이 취급되므로 참조에 대한 모든 변경 사항은 인수에도 적용된다.

### const 참조로 전달 (Pass by const reference)

참조를 사용하면 함수가 인수의 값을 변경할 수 있으므로 인수가 읽기 전용일 때는 사용하기에 바람직하지 않다. 

<u>함수가 인수의 값을 변경해서는 안 되지만 값으로 전달하지 않으려면 `const` 참조를 전달하는 것이 가장 좋다.</u>

**const 참조는 변수가 참조를 통해 변경되는 것을 허용하지 않는 참조**다.

<u>그러므로 const 참조를 매개 변수로 사용하면 함수가 인수를 변경하지 않는다는 것을 호출자에게 보장한다!</u>

> const 참조를 사용하는 것은 다음과 같은 이유로 유용하다.

- 컴파일러는 변경해서 안 되는 값을 변경하지 않도록 한다. (위의 예제처럼 시도할 경우 오류가 발생한다.)
- 함수가 인수의 값을 변경하지 않는다는 것을 프로그래머에게 알려준다. (디버깅에 도움이 될 수 있다.)
- const가 아닌 참조 매개 변수에는 const 인수를 전달할 수 없다. const 매개 변수를 사용하면 non-const 및 const 인수를 함수에 전달할 수 있다.
- const 참조는 l-value, const l-value 및 r-value를 포함한 모든 유형의 인수를 허용할 수 있다.

> *규칙: 참조로 인수를 전달할 때 인수 값을 변경해야 하는 경우가 아니면 항상 const 참조를 사용하자.*



### 포인터에 대한 참조 (References to pointers)

포인터를 참조로 전달하고 함수가 포인터의 주소를 완전히 변경하도록 할 수 있다.

```cpp
#include <iostream>

void foo(int*& ptr) // pass pointer by reference
{
    ptr = nullptr; // 실제 ptr 인수를 변경할 수 있다.
}

int main()
{
    int x = 5;
    int *ptr = &x;
    std::cout << "ptr is: " << (ptr ? "non-null" : "null") << '\n'; // prints non-null
    foo(ptr);
    std::cout << "ptr is: " << (ptr ? "non-null" : "null") << '\n'; // prints null

    return 0;
}
```



### 참조로 전달의 장단점 (Pros and cons of pass by reference)

**참조로 전달의 장점:**

- 참조를 사용하면 함수가 인수의 값을 변경할 수 있다. 또한 const 참조를 사용해서 함수가 인수를 변경하지 않는다는 것을 보장할 수 있다.
- 인수의 복사본이 만들어지지 않으므로 큰 구조체나 클래스와 함께 사용하는 경우에도 전달이 빠르다.
- 참조는 함수에서 매개 변수를 통해 여러 값을 반환할 수 있다.
- 참조는 초기화되어야 하므로 null 값에 대해 걱정할 필요가 없다.

**참조로 전달의 단점:**

- non-const 참조는 const 값 또는 r-value(리터럴 또는 표현식)으로 초기화할 수 없으므로 참조 매개 변수에 대한 인수는 일반 변수여야 한다.
- 인수가 변경될 수 있는지는 함수 호출에서 알 수 없다. 값으로 전달이나 참조로 전달 모두 인수에서는 동일하게 보인다. 함수 선언을 보고 인수가 값으로 전달인지 참조로 전달인지 알 수 있다. 이것은 프로그래머가 함수에서 인수의 값을 변경한다는 것을 인식하지 못하는 상황을 초래할 수 있다.

**참조로 전달을 사용해야 하는 경우:**

- 구조체 또는 클래스를 전달할 때 (읽기 전용인 경우 const 사용)
- 인수를 수정하는 함수가 필요할 때
- 고정 배열의 유형 정보에 접근해야 할 때

**참조로 전달을 사용하지 않아야 하는 경우:**

- 수정할 필요가 없는 기본 자료형을 전달할 때 (값으로 전달 사용)



----

# Pass by address

## 주소로 전달

인수가 주소이기 때문에 함수 매개 변수는 포인터다. 함수는 가리키는 값에 접근하거나 변경하기 위해 포인터를 역참조할 수 있다.

주소로 전달(Pass by address)은 주로 배열과 함께 사용한다. 예를 들어, 다음 함수는 배열의 모든 값을 출력한다.

```cpp
void printArray(int* array, int length)
{
    for (int index=0; index < length; ++index)
        std::cout << array[index] << ' ';
}
```

다음은 위 함수를 호출하는 예제 프로그램이다.

```cpp
int main()
{
    int array[6] = { 6, 5, 4, 3, 2, 1 }; // remember, arrays decay into pointers
    printArray(array, 6); // so array evaluates to a pointer to the first element of the array here, no & needed
}

// 6 5 4 3 2 1
```

배열이 함수에 전달될 때 포인터로 변환되므로 길이를 별도의 매개 변수로 전달해야 한다는 것을 기억하자.

주소를 통해 전달된 매개 변수를 역참조하기 전에 <u>null 포인터인지 항상 확인하는 게 좋다.</u> null 포인터를 역참조하면 프로그램이 중단된다. 다음은 null 포인터 검사를 수행하는 *printArray()* 함수다.

```cpp
void printArray(int* array, int length)
{
    // if user passed in a null pointer for array, bail out early!
    if (!array)
        return;

    for (int index=0; index < length; ++index)
        cout << array[index] << ' ';
}

int main()
{
    int array[6] = { 6, 5, 4, 3, 2, 1 };
    printArray(array, 6);
}
```



### 주소는 실제로 값에 의해 전달된다. (Addresses are actually passed by value)

함수에 포인터를 전달하면 포인터의 값(포인터가 가리키는 주소)이 인수에서 <u>함수의 매개 변수로 복사된다.</u> 

다시 말해서, <u>값으로 전달되었다! 함수 매개 변수의 값을 변경하면 복사본만 변경된다.</u> 

>  따라서 원래 포인터 인수는 변경되지 않는다.

```cpp
#include <iostream>

void setToNull(int* tempPtr)
{
    // we're making tempPtr point at something else, not changing the value that tempPtr points to.
    tempPtr = nullptr; // use 0 instead if not C++11
}

int main()
{ 
    // First we set ptr to the address of five, which means *ptr = 5
    int five = 5;
    int* ptr = &five;

    // This will print 5
    std::cout << *ptr << std::endl;

    // tempPtr will receive a copy of ptr
    setToNull(ptr);

    // ptr is still set to the address of five!

    // This will print 5
    if (ptr)
        std::cout << *ptr;
    else
        std::cout << " ptr is null";

    return 0;
}
// 5
// 5
```

매개 변수 tempPtr은 ptr이 보유하고 있는 <u>주소의 복사본을 전달받는다.</u> 

tempPtr을 다른 값(Ex. nullptr)을 가리키도록 변경하더라도 <u>ptr이 가리키는 값은 변경되지 않는다.</u>

>  주소 자체가 값에 의한 전달이 되더라도 여전히 그 주소를 역참조하여 인수의 값을 변경할 수 있다.

- 인수를 주소로 전달할 때 함수 매개 변수는 인수에서 주소의 복사본을 받는다. <u>이 시점에서 함수 매개 변수와 인수는 모두 같은 값을 가리킨다.</u>
- 함수 매개 변수가 가리키는 값을 변경하기 위해 역참조 하면 <u>함수 매개 변수와 인수가 모두 같은 값을 가리키기 때문에 인수가 가리키는 값에 영향을 준다.</u>
- <u>함수 매개 변수가 다른 주소로 지정되면</u> 함수 매개 변수가 복사본이기 때문에 인수에 영향을 미치지 않으므로 복사본을 변경해도 원본에 영향을 미치지 않는다. 
  - <u>함수 매개 변수의 주소를 변경한 후에는 함수 매개 변수와 인수가 다른 값을 가리키므로 역참조하더라도 더는 인수가 가리키는 값에 영향을 미치지 않는다.</u>



### 참조로 주소 전달하기 (Passing addresses by reference)

그러면 "함수 내에서 인수가 가리키는 주소를 변경하려면 어떻게 해야 할까?" 라는 의문이 들 수 있다. 

간단하게 참조로 주소를 전달해서 해결할 수 있다.

```cpp
#include <iostream>

// tempPtr is now a reference to a pointer, so any changes made to tempPtr will change the argument as well!
void setToNull(int*& tempPtr)
{
    tempPtr = nullptr; // use 0 instead if not C++11
}

int main()
{ 
    // First we set ptr to the address of five, which means *ptr = 5
    int five = 5;
    int* ptr = &five;

    // This will print 5
    std::cout << *ptr << std::endl;

    // tempPtr is set as a reference to ptr
    setToNull(ptr);

    // ptr has now been changed to nullptr!

    if (ptr)
        std::cout << *ptr;
    else
        std::cout << " ptr is null";

    return 0;
}

// 5
// ptr is null
```

*setToNull(ptr)* 호출이 *ptr*의 값을 *&five*에서 nullptr로 실제로 변경했음을 보여준다.



### 주소로 전달의 장단점 (Pros and cons of pass by address)

**주소로 전달의 장점:**

- 주소로 전달은 함수가 인수 값을 변경할 수 있으므로 유용하다.
- <u>인수의 복사본이 만들어지지 않으므로 구조체 또는 클래스와 함께 사용하는 경우에 빠르다.</u>

**주소로 전달의 단점:**

- <u>리터럴과 표현식은 주소가 없으므로 사용할 수 없다.</u>
- <u>모든 값은 null인지 확인해야 한다. null 값을 역참조하려고 하면 충돌이 발생한다.</u>
- 포인터 역참조는 값에 직접 접근하는 것보다 더 느리다.

**주소로 전달을 사용해야 하는 경우:**

- 내장 배열을 전달할 때
- 포인터를 전달할 때

**주소로 전달을 사용하지 않아야 하는 경우:**

- 구조체 또는 클래스를 전달할 때 (참조로 전달 사용)
- 기본 자료형의 값을 전달할 때 (값으로 전달 사용)

주소로 전달과 참조로 전달은 거의 같은 장단점이 있다. 

그러나 참조로 전달하는 것이 주소로 전달하는 것보다 더 안전하므로 대부분의 경우 참조로 전달하는 게 좋다.

---

[정리: 소년코딩](https://boycoding.tistory.com/218?category=1011971)

----