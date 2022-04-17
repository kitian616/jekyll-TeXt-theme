---
Title: Blocking, Non-blocking Algorithms. 
Tag: Multi_Thread
---

## Blocking
- 블로킹 동시성 알고리즘

> A: 쓰레드에 의해 요청된 동작을 수행  OR  
> B: 쓰레드가 동작을 수행할 수 있을 때까지 대기  

![블로킹 알고리즘 ](https://t1.daumcdn.net/cfile/tistory/2567054D5940060802)
*공유된 자료구조를 보호하는 블로킹 알고리즘의 동작*	


- - - -
## Non-blocking
- 논 블로킹 동시성 알고리즘

> A: 쓰레드에 의해 요청된 동작을 수행 OR  
> B: 요청된 동작이 수행될 수 없는 경우 이를 요청 쓰레드에게 통지  

**논 블로킹 자료구조 클래스**
- AtomicBoolean
- AtomicInteger
- AtomicLong
- AtomicReference

![논 블로킹 알고리즘](https://t1.daumcdn.net/cfile/tistory/2571E6485940062424)
*공유된 자료구조를 보호하는 논 블로킹 알고리즘의 동작*	

- - - -
## 논 블로킹 동시성 자료구조
- 멀티쓰레드 시스템에서 쓰레드들을 일반적으로 어떤 조율의 자료구조를 통해 서로 협업
다수의 쓰레드의 동시 접근에 제대로 기능하기 위해, 자료구조는 반드시 동시성 알고리즘에 의해 보호되어야 함. 
이러한 알고리즘이 자료구조를 동시성 자료구조로 만드는  것임.

- 동시성 자료구조를 보호하는 알고리즘이 쓰레드를 중단시키는 방법을 통한다면 이 알고리즘을 블로킹 알고리즘이라 한다.
자료구조는 블로킹 동시성 자료구조가 된다.

- 동시성 자료구조를 보호하는 알고리즘이 쓰레드를 중단시키지 않는 방법을 통한다면 이 알고리즘을 논 블로킹 알고리즘이라 한다. 
자료구조는 논 블로킹 동시성 자료구조가 된다.

- - - -
## volatile 변수 
변수의 값을 항상 메인 메모리에서 직접 읽히도록 함. 
= 메인 메모리로 즉시 쓰여짐 
= 다른 CPU에서 동작하는 다른 쓰레드에게 항상 volatile변수의 최신 값이 읽히도록 보장함.

> volatile 변수로의 쓰기는 원자적 연산이고, 다른 쓰레드가 끼어들 수 없다. 하지만 volatile 변수라 할지라도 연속적인 읽기-값 변경-쓰기 동작은 원자적이지 않다. 다음 코드는 둘 이상의 쓰레드에 의해 수행된다면 여전히 경합을 유발한다.    

```java
volatile myVar = 0;

...
int temp = myVar;
temp++;
myVar = temp;
```

위 코드와 동일 
```java
myVar++;
```

 두 쓰레드가 이 코드를 실행하여 똑같이 myVar 을 읽고, 값을 1 증가시키고, 값을 메인 메모리로 저장한다면, myVar 의 증가되는 값은 2 가 아닌, 1 이 될 여지가 있다.

> 위 코드가 실행되면 myVar 의 값은 CPU 레지스터 또는 CPU 캐시로 읽히고, 1 증가되고, CPU 레지스터나 CPU 캐시에서 다시 메인 메모리로 저장된다.  

- - - -
## The Single Writer Case

 - 경합 조건
 다수의 쓰레드가 같은 공유 변수에 대해 읽기-값 변경-쓰기 순서의 연산을 수행할 때 발생

> 즉, 읽기-값 변경-쓰기 연산을 수행하는 쓰레드가 하나 뿐이고, 나머지 모든 쓰레드는 읽기 연산만을 수행한다면 경합 조건은 발생하지 않는다.  
> = 공유 변수로 쓰기를 수행하는 쓰레드가 하나이고 이 변수를 읽는 쓰레드가 다수일 때는 어떠한 경합도 발생하지 않는다.   

 **따라서 쓰기 쓰레드가 하나인 경우라면 언제든 volatile 변수를 사용해도 좋다.**

### synchronized를 사용하지 않고 동시성을 유지하는 single writer counter

```java
public class SingleWriterCounter {
    private volatile long count = 0;

    /**
     * 시점을 막론하고 오직 한 쓰레드만 이 메소드를 호출해야 함.
     * 그렇지 않으면 경합 조건을 유발할 수 있음.
     */
    public void inc() {
        this.count++;
    }

    /**
     * 다수의 읽기 쓰레드가 이 메소드를 호출할 수 있음.
     * @return
     */
    public long count() {
        return this.count;
    }
}
```

![다이어그램](https://t1.daumcdn.net/cfile/tistory/2577E14A5940064534)

- - - -
## volatile 기반의 더 발전된 자료구조



- - - -
## 원문

**[parkcheolu tistory](https://parkcheolu.tistory.com/33?category=654619)
