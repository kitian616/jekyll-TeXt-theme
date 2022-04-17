---
title: new, delete (Date segment, Memory stick)
tag: cpp
---



## [malloc, hip](https://modoocode.com/98)

## [데이터 세그먼트의 구조](https://modoocode.com/83)

프로그램이 실행 될 때 프로그램은 RAM 에 적재 됩니다. 다시 말해 프로그램의 모든 내용이 RAM 위로 올라오게 된다는 것이지요. 여기서 '프로그램의 모든 내용' 이라 하면 프로그램의 코드와 프로그램의 데이터를 모두 의미 하는 것입니다. 이렇게 RAM 위로 올라오는 프로그램의 내용을 크게 나누어서 **코드 세그먼트(Code Segment)** 와 **데이터 세그먼트(Data Segment)** 로 분류할 수 있습니다.

+ Stack

  스택은 지역 변수가 거처하는 곳입니다. 스택의 특징으로는 지역 변수가 늘어나면 크기가 아래로 증가하다가 지역변수가 파괴되면 다시 스택의 크기는 위로 줄어들게 됩니다. 즉, 스택이 늘어나는 방향은 메모리 주소가 낮아지는 방향(아래 방향) 이라 보시면 됩니다.

+  **읽기 전용(Read-Only) Data** 

  이전에 상수와 리터럴에 대해서 이야기 할 때 등장하였는데 이 부분에 저장되는 데이터들은 값이 절대로 변경될 수 없습니다. 다시 말해 궁극적으로 보호 받는 부분 이죠.

## [C언어의 메모리 구조](https://blog.naver.com/speciallive/98372211)

### 메모리 공간에 대한 지식

+ 할당 시기 : 프로그램이 실행될 때

+ 할당 장소 : 메인 메모리에 할당, 메인 메모리란 RAM을 의미

+ 할당 용도 : 프로그램 실행 시 필요한 메모리 공간(ex : 지역변수, 전역변수 선언을 위한)의 할당을 위해

---

- 데이터 영역 (Data Area)

> \- 전역 변수와 static 변수가 할당되는 영역
>
> \- 프로그램의 시작과 동시에 할당, 프로그램이 종료되어야만 메모리에서 소멸

- 스택 영역 (Stack Area)

> \- 함수 호출 시 생성되는 지역 변수와 매개 변수가 저장되는 영역
>
> \- 함수 호출이 완료되면 사라진다는 특징을 지님

- 힙 영역 (Heap Area)

> \- 프로그래머가 관리하는 메모리 영역
>
> \- 프로그래머의 필요에 의해서 메모리 공간이 할당 및 소멸되는 영역

---

# new, delete

 언어에서는 [malloc](https://modoocode.com/243) 과 [free](https://modoocode.com/244) 함수를 지원하여 힙 상에서의 메모리 할당을 지원하였습니다. C++ 에서도 마찬가지로 [malloc](https://modoocode.com/243) 과 [free](https://modoocode.com/244) 함수를 사용할 수 있습니다.

하지만, 언어 차원에서 지원하는 것으로 바로 `new` 와 `delete` 라고 할 수 있습니다. `new` 는 말 그대로 [malloc](https://modoocode.com/243) 과 대응되는 것으로 메모리를 할당하고 `delete` 는 [free](https://modoocode.com/244) 에 대응되는 것으로 메모리를 해제합니다. 

```cpp
    
struct ServerCapacity
{
    struct File
    {
     	char name[30]; //파일 명
        double volume; 
    } File;
    
    void CreateFile(File file)
    {
        string file_name;
        std::cout << "파일 이름을 입력하시오."<< std::endl;
        std::cin >> file_name;
        
        
        int file_volum;
        std::cout << "파일 용량을 입력하시오. (MB)"<< std::endl;
    	std::cin >> file_volume;
        
        file
        {
		this.name = file_name;
         this, volume = file_volume;
        }

    }
	//파일 이름 배열: 파일 삭제
	//<array> m_file_name;
    char *m_file_name = new char[];
    
	//파일크기 배열: 현재 용량 계산
	//<array> m_file_volume;
    double *m_file_volume = new double[];
} ServerCapacity;

ServerCapacity

    //파일 업로드 시 서버의 파일 이름 배열에 초기화
    (double file_name)
    {
        <array> m_file_name = file_name;
    }

    //파일 업로드 시 서버의 파일 크기 배열에 초기화
    ServerCapacity(double file_volume)
    {
        <array> m_file_volume = file_volume;
    }
```

---

[정리: 씹어먹는 C++](https://modoocode.com/169)

---

# Dynamic memory allocation with new and delete

## new와 delete를 사용한 동적 메모리 할당

### 필요성

외부(사용자 또는 파일) 입력을 처리할 때 <u>이러한 제약 조건</u>으로 인해 문제가 발생하는 상황이 생길 수 있다.

+ Static memory allocation, auto memory allocation (정적 및 자동 메모리 할당은)
  + 변수/배열의 크기는 컴파일 타임에 알아야 한다.

  - 메모리 할당 및 해제가 자동으로 수행된다. (변수가 인스턴스화/제거되는 경우)

+ 해결하기 위해서 최대값을 가정하고 메모리를 할당할 때 문제점
  1. 고정 배열을 포함한 대부분의 일반 변수는 **스택(stack)**이라는 메모리 영역에 할당되기 때문이다.
  2. 일반적으로 Visual Studio는 스택 크기를 1MB로 기본 설정하며, 이 크기를 초과하면 스택 오버플로가 발생하고 운영 체제가 프로그램을 종료한다.
     //고정 배열을 포함한 대부분의 일반 변수는 **스택(stack)**이라는 메모리 영역에 할당되기 때문
  3. 가장 중요한 것은 인위적인 한계 및 또는 배열 오버플로가 발생할 수 있다는 것이다. 

> 메모리에 할당할 변수 크기에 대한 문제

**동적 메모리 할당은 프로그램 실행 중에 필요한 메모리를 운영체제에 요청하는 방법이다.**

이 메모리는 프로그램의 제한된 **스택(stack)** 메모리에서 할당되는 것이 아니라 **힙(heap)**이라는 운영체제에서 관리하는 훨씬 더 큰 메모리 풀에서 할당된다. 



### dynamically allocating single variablese

> **단일 변수를 동적으로 할당하려면, `new` 연산자를 사용하면 된다.**

```cpp
new int; // dynamically allocate an integer (and discard the result)
```

 `new` 연산자는 해당 메모리를 사용하여 객체를 만든 다음 <u>할당된 메모리의 주소가 포함된 포인터를 반환한다.</u>

> 할당된 메모리에 나중에 접근할 수 있도록 <u>반환 값을 자체 포인터 변수에 할당한다.</u>

```cpp
int *ptr = new int; // dynamically allocate an integer and assign the address to ptr so we can access it later
```

- 그럼 다음 포인터를 역참조(dereference)하여 메모리에 접근할 수 있다.

```cpp
*ptr = 7; // assign value of 7 to allocated memory
```

+ 방금 할당된 <u>메모리의 주소를 유지하는 포인터가 없으면 할당된 메모리에 접근할 수 없다.</u>



### How does dynamic memory allocation work?

컴퓨터에는 응용 프로그램에서 사용할 수 있는 메모리가 있다.

응용 프로그램을 실행할 때 운영 체제는 <u>해당 메모리에 응용 프로그램을 로드한다.</u> 

한 영역에는 코드가 포함되어 있고, 또 다른 영역은 정상적인 작업(어떤 함수가 호출되었는지 추적하고, 전역 변수와 지역 변수를 생성하고 소멸하는 등)에 사용된다.

메모리를 동적으로 할당할 때는 운영 체제에 프로그램 사용을 위해 <u>해당 메모리의 일부를 예약하도록 요청해야 한다.</u> 

이 요청을 수행할 수 있으면 <u>해당 메모리의 주소가 응용 프로그램에 반환된다.</u> 

그 시점부터, 응용 프로그램은 원하는 대로 이 메모리를 사용할 수 있다.

응용 프로그램이 메모리 사용을 완료하면 이 메모리를 다시 운영 체제로 반환하여 다른 프로그램에 제공할 수 있다.

> 프로그램 자체는 동적으로 할당 된 메모리를 요청하고 처리하는 역할을 한다.



### Initializing a dynamically allocated variable

변수를 동적으로 할당할 때 직접 초기화 또는 유니온 초기화를 통해 변수를 초기화할 수 있다.

```cpp
int *ptr1 = new int (5);   // use direct initialization
int *ptr2 = new int { 6 }; // use uniform initialization
```



### Deleting single variables

동적으로 할당된 변수를 모두 사용하면 메모리를 해제하여 재사용할 수 있도록 C++에 명시적으로 알려야 한다. 단일 변수의 경우 `delete` 연산자를 통해 수행된다.

```cpp
// assume ptr has previously been allocated with operator new
delete ptr; // return the memory pointed to by ptr to the operating system
ptr = 0; // set ptr to be a null pointer (use nullptr instead of 0 in C++11)
```



### What does it mean to delete memory?

`delete` 연산자는 실제로 아무것도 삭제하지 않으며 단순히 가리키는 메모리를 다신 운영 체제로 반환한다. 그러면 운영 체제에서 해당 메모리를 다른 응용 프로그램에 다시 할당할 수 있다.

> 변수를 삭제하는 것처럼 보이지만 사실이 아니다! 

포인터 변수는 여전히 이전과 동일한 범위를 가지며 다른 변수와 마찬가지로 새 값을 할당받을 수 있다.

> 동적으로 할당된 메모리를 가리키지 않는 포인터를 삭제하면 나쁜일이 발생할 수 있다.



### Dangling pointers

<u>C++은 할당되지 않은 메모리의 내용이나 삭제되는 포인터의 값에 대해서는 보장하지 않는다.</u> 

+ 할당되지 않은 메모리의 내용

- 삭제되는 포인터의 값

운영 체제에 반환되는 메모리에는 반환되기 전의 값과 같은 값이 포함되며, 

포인터는 현재 할당 해제된 메모리를 가리킨다.

할당이 해제된 메모리를 가리키는 포인터를 **댕글링 포인터(dangling pointer)**라고 한다. 

> 댕글링 포인터를 역참조하거나 삭제하면 정의되지 않은 동작이 발생한다.

```cpp
#include <iostream>

int main()
{
    int* ptr = new int; // dynamically allocate an integer
    *ptr = 7; // put a value in that memory location

    delete ptr; // return the memory to the operating system.  ptr is now a dangling pointer.

    std::cout << *ptr; // Dereferencing a dangling pointer will cause undefined behavior
    delete ptr; // trying to deallocate the memory again will also lead to undefined behavior.

    return 0;
}
```

위 프로그램에서 이전에 할당된 메모리에 할당된 7의 값은 여전할 수 있지만, **해당 메모리의 주소의 값이 변경되었을 수 있다. **

**또한, 메모리가 다른 응용 프로그램에 할당될 수 있으며 해당 메모리에 접근하려고 하면 운영 체제가 프로그램을 종료시킨다.**

> 그럼 포인터로 접근할 수 가 없게되는 거네.? 그런데 어떻게 메모리에 할당된 값이 여전히 남아있을 수 있다는 것을 확인할 수 있을까?

- 메모리를 할당 해제하면 여러 개의 댕글링 포인터가 생성될 수 있다.

```cpp
#include <iostream>

int main()
{
    int *ptr = new int; // dynamically allocate an integer
    int *otherPtr = ptr; // otherPtr is now pointed at that same memory location

    delete ptr; // return the memory to the operating system.  ptr and otherPtr are now dangling pointers.
    ptr = 0; // ptr is now a nullptr

    // however, otherPtr is still a dangling pointer!

    return 0;
}
```

여기에 도움이 되는 몇 가지 좋은 방법이 있다.

1. 여러 포인터가 같은 동적 메모리를 가리키는 것을 피하자.
2. 포인터를 삭제할 때 포인터를 0 또는 nullptr로 설정하자.



### Operateor new can fail

### Null pointers and dynamic memory allocation

널 포인터는 동적 메모리 할당을 처리할 때 유용하다.

동적 메모리 할당과 관련하여 널 포인터는 기본적으로 "<u>이 포인터에 메모리가 할당되지 않았다."</u>를 의미한다.

###### 이렇게 하면 조건부로 메모리를 할당하는 작업을 할 수 있다.

```cpp
// If ptr isn't already allocated, allocate it
if (!ptr)
    ptr = new int;
```

널 포인터는 삭제되지 않는다. 따라서 다음과 같이 할 필요가 없다.

```cpp
if (ptr)
    delete ptr;
```

###### 대신, 다음과 같이 작업하면 된다.

```cpp
delete ptr;
//`ptr`이 `null`이 아닌 경우, 동적으로 할당된 변수가 삭제된다. 
//만약 `null`이라면, 아무 일도 일어나지 않는다.
```



### Memory leak

메모리 누수,

동적으로 할당된 메모리는 <u>범위가 없다.</u> 

> 즉, 명시적으로 할당이 해제되거나 프로그램이 종료될 때까지 할당된 상태를 유지한다. 

그러나 동적으로 할당된 메모리 주소를 유지하는 데 사용되는 <u>포인터는 일반 변수의 범위 지정 규칙을 따른다.</u> 

+ 이 불일치는 흥미로운 문제를 일으킬 수 있다.

```cpp
void doSomething()
{
    int* ptr = new int;
}
```

이 함수는 정수를 동적으로 할당하지만, delete를 사용해서 해제하지는 않는다. 

포인터 ptr은 일반 변수와 같은 규칙을 따르므로, 함수가 끝나면 ptr은 범위를 벗어난다.

 <u>ptr은 동적으로 할당된 정수의 주소를 보유하는 유일한 변수이기 때문에, ptr이 삭제되면 동적으로 할당 된 메모리에 대한 참조가 더 이상 존재하지 않는다.</u> 

이것은 프로그램이 동적으로 할 된 메모리의 주소를 '잃어버리는(lost)'는 것을 의미한다. 

결과적으로 이 동적으로 할당된 정수는 해제할 수 없다.

> 이것을 '**메모리 누수(memory leak)**'라고 한다. 

메모리 누수는 <u>프로그램이 운영 체제로 되돌리기 전에 동적으로 할당된 메모리의 일부 비트 주소를 잃어버리면 발생한다.</u> 

이런 일이 발생하면 프로그램은 동적으로 할당된 메모리를 다시는 알 수 없으므로 동적으로 할당 된 메모리를 해제할 수 없다. 

+ 운영 체제는 이 메모리를 프로그램에서 여전히 사용 중이므로 이 메모리를 사용할 수 없다.
  + 메모리 누수는 프로그램이 실행되는 동안 사용 가능한 메모리를 소모하므로 이 프로그램뿐만 아니라 다른 프로그램에서도 사용할 수 있는 메모리가 줄어든다.
  + 심각한 메모리 누수 문제가 있는 프로그램은 사용 가능한 모든 메모리를 먹어 전체 시스템이 느리게 실행되거나 충돌할 수 있다. 

> 프로그램이 종료된 후에만 운영 체제가 모든 누출 메모리를 정리하고 '회수(reclaim)' 할 수 있다.

+ 메모리 누수의 다른 원인으로, 동적으로 할당된 메모리의 주소를 보유한 포인터에 다른 값이 할당되면 메모리 누수가 발생할 수 있다.

```cpp
int value = 5;
int* ptr = new int; // allocate memory
ptr = &value;       // old address lost, memory leak results
```

이 문제는 포인터를 재할당하기 전에 해제하여 해결할 수 있다.

```cpp
int value = 5;
int* ptr = new int; // allocate memory
delete ptr;         // return memory back to operating system
ptr = &value;       // reassign pointer to address of value
```

+ 또한, 이중 할당을 통해 메모리 누수가 발생할 수 있다.

```cpp
int* ptr = new int;
ptr = new int; // old address lost, memory leak results
```

두 번째 동적 할당에서 반환된 주소는 첫 번째 동적 할당된 주소를 덮어쓴다. 

따라서 첫 번째 동적 할당은 메모리 누수가 된다!

마찬가지로 재할당하기 전에 포인터를 해제하면 이러한 문제를 방지할 수 있다.





---

 [정리: 소년코딩](https://boycoding.tistory.com/204)

---