---
title: this pointer
tag: cpp
---



##  this 포인터

클래스의 멤버 함수를 호출할 때 C++는 어떻게 호출할 객체(인스턴스)를 찾는가?" 이다. 이 질문에 대한 정답은 **`this`라는 숨겨진 포인터를 사용한다**는 것이다.

<u>즉, 멤버 함수의 인수로 객체(인스턴스)의 주소가 전달된다는 것이다.</u>



### 과정

멤버 함수 호출에 매개 변수가 추가되었으므로 멤버 함수의 정의 역시 매개 변수를 하나 더 받도록 수정되어야 한다.

```cpp
void SetID(int id)
{
    m_ID = id;
}
```

위 멤버 함수의 정의는 컴파일러에 의해 다음과 같이 변환된다:

```cpp
void SetID(Simple* const this, int id)
{
    this->m_ID = id;
}
```

컴파일할 때 컴파일러는 `this`를 함수에 추가한다. 

이 **this 포인터는 멤버 함수가 호출된 객체의 주소를 가리키는 숨겨진 포인터다.**

멤버 함수 안에서 모든 클래스의 멤버 변수의 참조 또한 `this->`가 추가되는 형식으로 변환되므로 위 코드에서는 `m_ID`에 대한 참조가 `this->m_ID`로 변환되었다. 결과적으로 `this->m_ID`는 `simple->m_ID`가 되는 것이다.



### 컴파일러 수행 과정 요약

1. simple.SetID(2); 를 호출하면 컴파일러는 실제로 simple.SetID(&simple, 2); 로 변환해서 호출한다.
2. setID() 멤버 함수 내부에서 'this' 포인터는 simple 객체의 주소를 가리킨다.
3. SetID() 내부의 모든 멤버 변수 앞에는 `this->`가 붙는다. 그래서 m*ID = id; 는 실제로 this->m*ID = id;로 변환된다.

이 모든 과정은 컴파일러에 의해 자동으로 수행되므로 세세하게 기억하지 않아도 된다.

 ><u>그러나 기억해야 할 것은 **모든 멤버 함수는 함수가 호출된 객체(인스턴스)를 가리키는 `this` 포인터를 가지고 있다**는 것이다</u>.



###  1. this는 항상 호출된 객체(인스턴스)를 가리킨다.

지금까지 설명한 내용처럼 this 포인터는 어떤 객체(인스턴스)에서 멤버 함수를 호출했는지에 따라 가리키는 주소가 다르다.

또한, this는 함수 매개 변수에 불과하므로 클래스에 메모리 사용량을 추가하지 않는다. (함수가 실행되는 동안에만 스택에 매개 변수 쌓인다.)



### 2. 명시적으로 this를 참조해야 하는 경우가 있다.

첫째: 멤버 변수와 이름이 같은 매개 변수를 가진 생성자(또는 멤버 함수)가 있는 경우: this를 사용해서 구분할 수 있다.



### 3. 멤버 함수 체이닝 기법

둘째로 클래스 멤버 함수가 작업 중이던 객체(인스턴스)를 반환하는 방식이 유용할 때가 종종있다. 

이렇게 하면 같은 객체의 여러 멤버 함수를 연속해서 호출할 수 있는 방법이다.

다음 클래스를 보자:

```cpp
class Calc
{
private:
    int m_Value = 0;

public:
    void Add(int value) { m_Value += value;}
    void Sub(int value) { m_Value -= value;}
    void Mul(int value) { m_Value *= value;}

    int GetValue() { return m_Value; }
}
```

위 클래스와 함께 5를 더하고 3을 빼고 4를 곱하려면 다음과 같이 해야 한다:

```cpp
int main()
{
    Calc calc;

    calc.Add(5); // return void
    calc.Sub(3); // return void
    calc.Mul(4); // return void

    cout << calc.GetValue() << endl; // 8
    return 0;
}
```

그러나 만약 각각의 멤버 함수가 **this를 반환한다면 체이닝 기능이 있는 새로운 버전의 Calc를 만들 수 있다.**

```cpp
class Calc
{
private:
    int m_Value = 0;

public:
    Calc& Add(int value) { m_Value += value; return *this; }
    Calc& Sub(int value) { m_Value -= value; return *this; }
    Calc& Mul(int value) { m_Value *= value; return *this; }

    int GetValue() { return m_Value; }
}
```

이제 *Add()*, *Sub()* 및 *Mul()* 멤버 함수는 `*this`를 반환한다. 따라서 다음과 같이 프로그래밍할 수 있다.

```cpp
int main()
{
    Calc calc;

    calc.Add(5).Sub(3).Mul(4);

    cout << calc.GetValue() << endl; // 8
    return 0;
}
```

코드 3줄을 단 1줄로 바꾸었다!

각 멤버 함수가 호출되면서 *calc* 객체의 *m_Value*를 수정하고 `*this`를 반환함으로써 자기 자신(인스턴스)을 반환한다.

```cpp
1. calc.Add(5).Sub(3).Mul(4);
2. (calc.Add(5)).Sub(3).Mul(4); // m_Value = 0 + 5
3. (calc.Sub(3)).Mul(4);        // m_Value = 5 - 3
4. calc.Mul(4);                 // m_Value = 2 * 4; => 8
```

----

[정리: 소년코딩](https://boycoding.tistory.com/250?category=1067100)

----