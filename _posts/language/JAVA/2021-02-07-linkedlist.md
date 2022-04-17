---
title: LinkedList, 4 week java
tag: java
toc: true
---

## 목표  

1. LinkedList에 대해 공부하세요.  
2. 정수를 저장하는 ListNode 클래스를 구현하세요.  
3. ListNode add(ListNode head, ListNode nodeToAdd, int position)를 구현하세요.  
4. ListNode remove(ListNode head, int positionToRemove)를 구현하세요.  
5. boolean contains(ListNode head, ListNode nodeTocheck)를 구현하세요.  

---   
## 0. List  

![list](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F2PTnh%2FbtqzP5ORLWv%2FzrKYCCjIICsavZGwomKB9k%2Fimg.png)  
 
- 랜덤 엑세스 가능
  간단한 연산을 통해 메모리 주소를 계산하여 접근할 수 있다.  
  내가 원하는 메모리 주소 == 배열의 시작주소 + (내가 찾고자하는 칸의 인덱스 * 1칸의 크기)  
- 크기가 고정   
  기존의 배열칸 이상의 데이터를 저장할 경우 rellocation이 필요하다.  
- 삽입과 삭제연산 비용 큼  
  리스트 중간에 원소를 삽입하거나 삭제할 경우, 다수의 데이터를 옮겨야 하기 때문에 비용이 크다.

원소들 간의 논리적이 순서를 위한 자료구조  
원소들 간의 순서는 논리적(추상적)으로 지켜지며 원소가 저장되는 물리적인 위치는 상관하지 않는다.  

배열을 이용해 리스트를 구현하면 논리적인 순서를 지키기 위해 원소의 이동이 많아짐 => 포인터 변수를 이용한 연결 리스트 이용  

> 포인터 변수와 동적 메모리 할당을 이용해 메모리 낭비를 막을 수 있다.  


## 1. LinkedList에 대해서  

> Linked List는 엘리먼트와 엘리먼트 간의 연결(link)을 이용해서 리스트를 구현한 것.  

**각 노드가 데이터와 포인터를 가지고 한 줄로 연결되어 있는 방식으로 데이터를 저장하는 자료 구조.**  

데이터를 담고 있는 노드들이 연결되어 있는데, 노드의 포인터가 다음이나 이전의 노드와의 연결을 가능하게 해줌.  

- 연결 리스트는 늘어선 노드의 중간지점에서도 자료의 추가와 삭제가 O(1)의 시간에 가능.  
- 그러나 특정 위치의 데이터를 검색해 내는데에는 O(n)의 상수 시간이 걸림. (비효율)  

> 데이터 영역에 데이터를 저장하고, 포인터(링크)영역에 다음/이전 노드로 가는 주소를 저장하여 순서를 나타낸다.

- 삽입과 삭제연산 비용 적음  
- 길이제한 없음   
  노드를 연결한 형태이기 때문에 길이제한이 없다.
- 메모리 효율 낮음   
  노드를 연결하기 위한 포인터 저장 영역이 필요하기 때문에, 배열에 비해 메모리 효율이 낮다.
- 랜덤 엑세스 불가능  
  연속된 메모리 주소에 데이터가 저장된 것이 아니기 때문에, 배열처럼 랜덤 액세스가 불가능하다.


![기본 구조](https://postfiles.pstatic.net/MjAyMDEyMDFfMjU2/MDAxNjA2Nzk3NDA4OTUy.2vXZPVs0TxU4EY3SVip7YHQTL2Vs1fZl9pYvXetXnHgg.rhiZXuoyBBs81HDVW7AkSJleEvevaV_Jji_GQbu7BIEg.PNG.swoh1227/1.PNG?type=w773)  

### C  

- `노드`는 C언어에서 `struct 데이터 타입`으로 구현된다.  
  노드의 데이터 필드는 리스트의 원소값을 저장하기 위한 부분으로 필요에 따라 여러 필드로 이루어질 수 있다.  
  즉 데이터 필드에는 여러 종류의 값이나 정보가 저장될 수 있다.  
- 링크 필드는 리스트에서 다음에 오는 리스트 원소가 저장되어 있는 노드 주소를 저장한다.  
  이 주소를 이용하여 다음 원소를 찾아간다.  
  `링크 필드`는 C언어에서 `포인터 변수`를 이용하여 구현된다.  

> 연결 리스트의 노드 삽입(②, ③ 순서 중요) :  
> ① 삽입할 노드를 생성한다.  
> ② 삽입할 노드의 링크가 삽입될 위치의 후행 노드를 가리키게 한다.  
> ③ 삽입될 위치의 선행 노드의 링크가 삽입할 노드를 가리키게 한다.  



- 리스트의 `head 포인터 변수`는 연결 리스트의 시작 노드(리스트의 첫 번째 원소)를 가리킨다.    
- 마지막 노드를 제외한 나머지 모든 노드의 링크는 논리적으로 바로 다음에 위치하는 노드를 가리키는 주소를 갖는다.  
- 마지막 노드의 링크는 더 이상 가리킬 것이 없는 null 포인터로 표현한다.   

![메모리 공간 할당](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FHxwhf%2FbtqzR2DqhFX%2F1AmC6HNIPcc0spuwKIq1q0%2Fimg.png)  


### 노드   
  `데이터 + 다음 데이터`의 주소가 하나로 묶여있는 단위를 의미   

- 각 노드는 하나의 데이터(data) 필드와 하나의 링크(next) 필드로 구성된다.    
- 첫 번째 노드의 주소는 따로 저장(head)해야한다.    
- 마지막 노드의 next 필드에서는 NULL을 저장하여 연결리스트이 끝임을 표시한다.  


### java.util.LinkedList는  

```
// 상속
java.lang.Object
  java.util.AbstractCollection<E>
    java.util.AbstractList<E>
      java.util.AbstractSequentialList<E>
        java.util.LinkedList<E>
```  
**All Implemented Interfaces:**  
- Serializable  
- Cloneable  
- Iterable  
- Collections  
- Deque  
- List  
- Queue  

*List와 Deque 인터페이스의 이중연결리스트 구현체이다.*

> 비동기적으로 구현되기 때문에, 여러 스레드가 동시에 LinkedList의 노드를 추가/삭제할 수 없다.  
> 즉, 외부적으로만 동기화 될 수 있다.  





---   

## 참고자료   

- [oracle](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/LinkedList.html)  
- [git reposity: TheAlgorithms-JAVA](https://github.com/TheAlgorithms/Java/blob/master/DataStructures/Lists/SinglyLinkedList.java)  
- [git reposity: TheAlgorithms-C, singly link list](https://github.com/TheAlgorithms/C/blob/master/data_structures/linked_list/singly_link_list_deletion.c)
- [Knowledge Repository](https://atoz-develop.tistory.com/entry/%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0-%EB%8B%A8%EC%88%9C-%EC%97%B0%EA%B2%B0-%EB%A6%AC%EC%8A%A4%ED%8A%B8-%EC%A0%95%EB%A6%AC-%EB%B0%8F-%EC%97%B0%EC%8A%B5%EB%AC%B8%EC%A0%9C)  
- [killog blog](https://kils-log-of-develop.tistory.com/349)  
- [ahnyezi gitblog](https://ahnyezi.github.io/java/javastudy-4-linkedlist/)  
- [Seung's blog](https://blog.naver.com/swoh1227/222161294264)  
- [](https://www.notion.so/Live-Study-4-ca77be1de7674a73b473bf92abc4226a)  
- [kjw217 git](https://github.com/kjw217/whiteship-live-study/blob/master/4th-week/%EC%A0%9C%EC%96%B4%EB%AC%B8.md)  
- [어떤 프로그래머 저장소 blog](https://blog.naver.com/hsm622/222159930944)  
- [Junior Lob!](https://lob-dev.tistory.com/entry/Live-StudyWeek-04-%EC%A0%9C%EC%96%B4%EB%AC%B8-%EA%B3%BC%EC%A0%9C)  
