---
Title: synchronization
tag: java synchronization 
---


## 필요
멀티스레드를 잘 사용하면 프로그램적으로 좋은 성능을 낼 수 있지만, 멀티스레드 환경에서 반드시 고려해야할 점인 스레드간 동기화라는 문제는 꼭 해결해야함.  

예를 들어 스레드 간 서로 공유하고 있는 데이터가 있는데 스레드 간 동기화가 되지 않은. 상태에서  멀티스레드 프로그램을 돌리면, 데이터의 안정성과 신뢰성을 보장할 수 없음.  
 
> 데이터의 thread-safe 를 하기 위해 자바에서는 `synchronized `키워드를 제공해 스레드 간 동기화를 시켜 데이터의 thread-safe를 가능케함.     


- - - -

## 개념 
Synchronized 키워드는 여러개의 스레드가 한개의 자원을 사용하고자 할 때, 현재 데이터를 사용하고 있는 해당 스레드를 제외하고 나머지 스레드들은 데이터에 접근 할 수 없도록 막는 개념입니다.


- - - -
## 사용법

`Synchronized`  키워드는 다음의 유형을 가짐.
1. 인스턴스 메소드
2. 스태틱 메소드
3. 인스턴스 메소드 코드블록
4. 스태틱 메소드 코드블록


### Usage

1. 메서드에서 사용

```java
 public synchronized void method() {
	// ....
}
```

2. 객체 변수에 사용
```java
private Object obj= new Object();
public void exampleMethod() {
	synchronized(obj) {
		// .....	
	}
}
```

---
## 참고 문헌
**[코딩스타트](https://coding-start.tistory.com/68)**
