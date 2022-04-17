---
title: 프로세스 스케줄링
tag: 정처기
---



## 프로세스 스케줄링

1. 선점
2. 비선점



### 비선점

- Priority, 우선순위

  각 프로세스 별로 우선순위가 주어지고, 우선순위에 따라 CUP를 할당

- Deadline, 기한부

  작업들이 명시된 시간이나 기한 내에 완료되도록 계획

- FCFS, First Come First Serve Scheduling

  도착한 순서로 할당

- SJF, Shortest Job First

  현재 가장 적은 시간을 가지는 순위로 할당

- HRRN, Highest Response Ratio Next

  response ratio= (대기시간+서비스시간) / 서비스 시간



### 선점

- RR, Round-Robin

  우선순위 스케줄링(Priority scheduling)과 결합해 프로세스의 시간 할당량을 조절하는 방식으로 활용한다.

  할당된시간 내 처리 안되면 준비 큐 리스트의 가장 뒤로 감.

- SRT, Shortest Remaining Time

  가장 짧은 시간이 소요되는 프로세스를 먼저 수행

- Multi-level Queue

- Multi Level Feedback Queue

  큐마다 서로 다른 CPU시간 할당량을 부여

  

  

[참고](https://velog.io/@hax0r/%EC%84%A0%EC%A0%90%EB%B9%84%EC%84%A0%EC%A0%90-%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4-%EC%8A%A4%EC%BC%80%EC%A4%84%EB%A7%81)

---

## 프로세스 상태 전이

![](http://blog.skby.net/blog/wp-content/uploads/2019/04/1-31.png)

### 프로세스 상태

- 생성

  보조 기억 장치에 저장되어 있는 실행 파일 상태

- 준비

  프로세스가 CPU를 사용하여 실행 준비 된 상태

  프로세스가 우선순위에 의해 정렬됨.

- 실행

  CUP를 차지하여 실행중인 상태

  명령어들이 실행되고 있는 상태

- 대기 

  `waiting` or `block`

  사건 발생을 기다리는 상태

- 종료

  자원을 반납한 상태



### 프로세스 상태 전이

- Dispatch

  스케줄해서 CUP에 할당(Dispatch), `문맥 교환`발생

  > Context switching
  >
  > ​	현 상태를 PCB(프로셋스 제어블록)에 저장하고 다음 프로세스를 복원하는 작업

- Timer run out

  CPU를 할당받은 프로세스는 지정된 시간이 초과되면 스케줄러에 의해 PCB 저장, CPU 반납 후 다시 준비 상태로 전이됨.

- Block

  스스로 메모리를 반납하고 입출력이 완료될 때까지 대기 상태로 전이됨.

- Wake up

- Swap-out

  프로세스가 기억 장치를 읽은 경우

- Swap-in

  프로세스에게 다시 기억 장치가 할당될 경우

---

### DB서버 구성 요소

- 데이터 딕셔너리
- 테이블 스페이스, 데이터 파일
- 데이터베이스 영역 할당
- 데이터베이스 관리 시스템 메모리
- 데이터베이스 버퍼
- 로그 버퍼
- 공유 풀과 정렬 영역
- DBMS 프로세스

1