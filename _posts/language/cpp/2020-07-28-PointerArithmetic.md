---
title: Pointer arithmetic and array indexing
tag: cpp
---



## 포인터 산술과 배열 인덱싱

포인터 산술 표현식을 계산할 때 컴파일러는 항상 피연산자에 가리키고 있는 객체의 크기를 곱한다. 

이를 **스케일링(scaling)**이라고 한다.



### Arrays are laid out sequentially in memory

주소 연산자(`&`)를 사용해서 배열이 메모리에 순차적으로 배치되었는지 확인할 수 있다. 즉 요소 0, 1, 2, ...은 모두 순서대로 인접해 있다.

```cpp
#include <iostream>

int main()
{
    int array[] = { 9, 7, 5, 3, 1 };

    std::cout << "Element 0 is at address: " << &array[0] << '\n';
    std::cout << "Element 1 is at address: " << &array[1] << '\n';
    std::cout << "Element 2 is at address: " << &array[2] << '\n';
    std::cout << "Element 3 is at address: " << &array[3] << '\n';

    return 0;
}

/*output
Element 0 is at address: 0041FE9C
Element 1 is at address: 0041FEA0
Element 2 is at address: 0041FEA4
Element 3 is at address: 0041FEA8
*/
```

이 메모리 주소는 각각 4바이트씩 떨어져 있으며, 테스트한 컴퓨터의 정수 크기다.



### Pointer arithmeitc, arrays, and the magic behind indexing

일반적으로 `array[n]`은 `*(array + n)`과 같다.



### Using a pointer to iterate through an array

---

 [정리: 소년코딩](https://boycoding.tistory.com/202?category=1009770)

---