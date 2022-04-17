---
title: inheritance, override
tag: cpp
---



# [상속, 오버라이딩](https://modoocode.com/209)

---

 이제 각각의 `Employee` 클래스를 만들었으니, 이 `Employee` 객체들을 관리할 수 있는 무언가가 있어야 겠지요? 물론 단순히 배열을 사용해서 사원들을 관리할 수 있겠지만, 불편합니다. 그래서 저는 `EmployeeList` 클래스를 만들어서 간단하게 처리하도록 할 것입니다.

우리는 다음과 같은 멤버 변수들을 이용해서 사원 데이터를 처리할 것입니다.

```cpp
int alloc_employee;        // 할당한 총 직원 수
int current_employee;      // 현재 직원 수
Employee **employee_list;  // 직원 데이터
```

언제나 동적으로 데이터를 할당하는 것을 처리하기 위해서는 두 개의 변수가 필요 했는데, 하나는 현재 할당된 총 크기고, 다른 하나는 그 중에서 실제로 사용하고 있는 양이지요. 이렇게 해야지만 할당된 크기 보다 더 많은 양을 실수로 사용하는 것을 막을 수 있습니다. 따라서 우리도 `alloc_employee` 가 할당된 크기를 알려주는 배열이고, `current_employee` 는 현재 `employee_list` 에 등록된 사원 수라고 볼 수 있지요.

`employee_list` 가 `Employee**` 타입으로 되어 있는 이유는, <u>우리가 이를 `Employee*` 객체를 담는 배열로 사용할 것이기 때문입니다.</u> 그렇다면 <u>`EmployeeList` 클래스의 생성자는 아래와 같이 쉽게 구성할 수 있겠지요.</u>

```cpp
EmployeeList(int alloc_employee) : alloc_employee(alloc_employee) {
  employee_list = new Employee*[alloc_employee];
  current_employee = 0;
}
```

그리고 사원을 추가하는 함수는 아래처럼 단순하게 구성할 수 있습니다.

```cpp
void add_employee(Employee* employee) {
  // 사실 current_employee 보다 alloc_employee 가 더
  // 많아지는 경우 반드시 재할당을 해야 하지만, 여기서는
  // 최대한 단순하게 생각해서 alloc_employee 는
  // 언제나 current_employee 보다 크다고 생각한다.
  // (즉 할당된 크기는 현재 총 직원수 보다 많음)
  employee_list[current_employee] = employee;
  current_employee++;
}
```



---

 C++ 에서는 이와 같은 경우, 다른 클래스의 내용을 그대로 포함할 수 있는 작업을 가능토록 해줍니다. 바로 **상속** 이라는 것을 통해 말이지요.

사실 상속이라는 단어 속에 무언가를 물려 받아서 사용한다는 의미가 있습니다. 즉, C++ 에서 상속을 통해 다른 클래스의 정보를 물려 받아서 사용할 수 있습니다.

일단은 바로 `Employee` 와 `Manager` 클래스에 적용하기 전에 간단한 클래스를 먼저 만들어서 어떻게 C++ 에서 상속이라는 기능이 사용되는지 알아보도록 하겠습니다.

> C++ 의 경우 여러 명의 **부모** 를 가질 수 있기에, 부모, 자식 클래스라 하기 보단, **기반, 파생 클래스라 부르는 것이 낫다고 생각합니다.**

```cpp
Derived() : Base(), s("파생") {
   std::cout << "파생 클래스" <<  std::endl;

  // Base 에서 what() 을 물려 받았으므로
  // Derived 에서 당연히 호출 가능하다
  what();
}
```

초기화 리스트에서 `Base` 를 통해 기반의 생성자를 먼저 호출하게 됩니다. 참고로 기반 클래스의 생성자를 명시적으로 호출하지 않을 경우 기반 클래스의 디폴트 생성자가 호출됩니다.

>  실행 결과

```
 === 기반 클래스 생성 ===
기반 클래스
 === 파생 클래스 생성 ===
기반 클래스
파생 클래스
기반
```

그런데, `what` 함수를 호출했을 때, *파생* 이 아니라 *기반* 라고 출력이 되었는데, `what` 함수를 보면 `s` 의 값을 출력하도록 되어 있습니다. 이러한 일이 발생한 이유는, `what` 함수는 `Base` 에 정의가 되어 있기 때문에 `Derived` 의 `s` 가 아니라 `Base` 의 `s` 가 출력되어 "기반" 라고 나오게 되는 것입니다.

---

##  protected

 C++ 에서는 `protected` 라는 `public` 과 `private` 에 중간 위치에 있는 접근 지시자를 지원합니다. 이 키워드는, **상속받는 클래스에서는 접근 가능하고 그 외의 기타 정보는 접근 불가능** 이라고 보시면 됩니다. 

