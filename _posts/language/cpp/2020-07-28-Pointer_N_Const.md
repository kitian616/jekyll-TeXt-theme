---
title: Pointer and const
tag: cpp
---



## 포인터와 const

### Pointing to const variables

```cpp
//const(const)가 아닌 값을 가리키는 비-const(non-const) 포인터
int value = 5;
int* ptr = &value;
*ptr = 6; // change value to 6
```

하지만 값이 const 인 경우에는 어떻게 될까?

```cpp
const int value = 5; // value is const
int ptr = &value; // compile error: cannot convert const int* to int*
*ptr = 6; // change value to 6
```

위 코드는 컴파일되지 않는다. 

const 변수는 값을 변경할 수 없다. 

만약 const가 아닌 포인터가 <u>const 변수를 가리킨 다음에 역참조하여 값을 바꿀 수 있다면, const의 의도를 위반하게 되므로</u> const가 아닌 포인터는 const 변수를 가리킬 수 없다.



### Pointer to const value

**const를 가리키는 포인터는 <u>const 변수의 주소를 가리키는 (non-const) 포인터다</u>.**

+ const 변수에 대한 포인터를 선언하려면 자료형 앞에 `const` 키워드를 사용하면 된다.

```cpp
const int value = 5;
const int* ptr = &value; // this is okay, ptr is a non-const pointer that is pointing to a "const int"
*ptr = 6; // not allowed, we can't change a const value
```

위 예제에서 `ptr`은 `const int`를 가리킨다.

- const 변수에 대한 포인터는 const가 아닌 변수를 가리킬 수 있다. 

```cpp
int value = 5; // value is not constant
const int* ptr = &value; // this is still okay
```

const 변수에 대한 포인터는 <u>변수가 초기에 `const`로 정의되었는지에 관계 없이 포인터를 통해 접근할 때 변수를 const로 취급한다.</u>

+ 따라서 다음은 가능하다.

```cpp
int value = 5;
const int* ptr = &value; // ptr points to a "const int"
value = 6; // the value is non-const when accessed through a non-const identifier
```

value 값에 대해서 정수 6을 복사해서 대입할 수 있다.

+ 그러나 다음은 불가능하다.

```cpp
int value = 5;
const int* ptr = &value; // ptr points to a "const int"
*ptr = 6; // ptr treats its value as const, so changing the value through ptr is not legal
```

역참조해서 값을 바구는 것은 불가능하다.

+ **const를 가리키는 포인터는 const를 <u>가리킬 뿐</u>, const <u>자체가 아니므로 다른 값을 가리킬 수 있다</u>**.

```cpp
int value1 = 5;
const int* ptr = &value1; // ptr points to a const int

int value2 = 6;
ptr = &value2; // okay, ptr now points at some other const 
```



### Const pointer to a const value

마지막으로, 자료형 앞뒤에 `const` 키워드를 사용해서 const를 가리키는 const 포인터를 선언할 수 있다.

```cpp
int value = 5;
const int *const ptr = &value;
```

**const를 가리키는 const 포인터는 <u>다른 주소를 가리키도록 수정할 수 없으며, 역참조를 통해 값을 수정할 수도 없다</u>.**

---

 [정리: 소년코딩](https://boycoding.tistory.com/206?category=1009770)

---