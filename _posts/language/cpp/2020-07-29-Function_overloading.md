---
title: Function Overloading
tag: cpp
---





## 함수 오버로딩

### Function return types are not considered for uniqueness

**함수의 반환 타입은 함수 오버로딩에서 고려되지 않는다.** 

(함수는 인수에만 기반하여 호출된다. 반환 값이 포함된 경우 함수의 어떤 버전이 호출되었는지 쉽게 알 수 있는 구문 방식이 없다)

난수를 반환하는 함수를 작성하려고 하지만 int를 반환하는 버전과 double을 반환하는 다른 버전이 필요한 경우를 생각해보자. 아래와 같은 유혹에 빠질 수 있다.

```cpp
int getRandomValue();
double getRandomValue();
```

그러나 컴파일러는 오류를 발생시킨다. 위 두 함수는 동일한 매개 변수(:없음)를 가지므로 결과적으로 두 번째 `getRandomValue()`는 첫 번째 함수를 잘못된 재선언으로 처리된다.

이 문제를 해결하는 가장 좋은 방법은 함수에 서로 다른 이름을 지정하는 것이다.

```cpp
int getRandomInt();
double getRandomDouble();
```

또 다른 방법은 함수가 *void*를 반환하도록 하고 반환 값을 호출자에게 *out* 참조 매개 변수로 전달하도록 하는 것이다.

```cpp
void getRandomValue(int& out);
void getRandomValue(double& out);
```

이러한 함수에는 다른 매개 변수가 있으므로 고유한 것으로 간주된다.



### Typedefs are not distinct

>Rule: 프로그램을 단순화 하려면 함수 오버로딩을 사용하자.

---

[정리: 소년코딩](https://boycoding.tistory.com/221?category=1011971)

---



## 과정

C++ 컴파일러에서 함수를 오버로딩하는 과정은 다음과 같습니다.

### 1 단계

자신과 타입이 정확히 일치하는 함수를 찾는다.

### 2 단계

정확히 일치하는 타입이 없는 경우 아래와 같은 형변환을 통해서 일치하는 함수를 찾아본다.

- `Char, unsigned char, short` 는 `int` 로 변환된다.
- `Unsigned short` 는 `int` 의 크기에 따라 `int` 혹은 `unsigned int` 로 변환된다.
- `Float` 은 `double` 로 변환된다.
- `Enum` 은 `int` 로 변환된다.

### 3 단계

위와 같이 변환해도 일치하는 것이 없다면 아래의 좀더 포괄적인 형변환을 통해 일치하는 함수를 찾는다.

- 임의의 숫자(numeric) 타입은 다른 숫자 타입으로 변환된다. (예를 들어 `float -> int)`
- `Enum` 도 임의의 숫자 타입으로 변환된다 (예를 들어 `Enum -> double)`
- `0` 은 포인터 타입이나 숫자 타입으로 변환된 0 은 포인터 타입이나 숫자 타입으로 변환된다
- 포인터는 `void` 포인터로 변환된다.

### 4 단계

유저 정의된 타입 변환으로 일치하는 것을 찾는다 (이 부분에 대해선 나중에 설명!)([출처](http://www.learncpp.com/cpp-tutorial/76-function-overloading/))

만약에 컴파일러가 위 과정을 통하더라도 일치하는 함수를 찾을 수 없거나 같은 단계에서 두 개 이상이 일치하는 경우에 **모호하다 (ambiguous)** 라고 판단해서 오류를 발생하게 됩니다.

```
void Date::ShowDate() {}
//Date:: 을 함수 이름 앞에 붙여주게 되면 
//이 함수가 "Date 클래스의 정의된 함수" 라는 의미를 부여하게 됩니다.
```

```cpp
class Date
{
	int year;
	int moth;
	int day;
	
	public:
		void setDate(int year, int month, int date);
};

//class의 정의와 본체는 분리하는 것이 일반적
//"Date 클래스의 정의된 함수" 라는 의미를 부여하게 됩니다. 
void Date::setDate(int y, int m, int d)
{
	year = y;
	month = m;
	day = d;
}
```

### 생성자

```
Date day(2011, 3, 1);         // 암시적 방법 (implicit), 선호
Date day = Date(2012, 3, 1);  // 명시적 방법 (explicit)
```

---

[정리: 씹어먹는 C++, 함수의 오버로딩](https://modoocode.com/188)

---