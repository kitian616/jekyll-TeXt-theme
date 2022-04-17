---
title: My SQL
tag: SQL
---



## MYSQL

+ 관계형 데이터베이스

데이터 베이스 서버 //관리

데이터 베이스

> = 스키마

테이블 > 연관 있는 테이블 모음 > 데이터 베이스

> 테이블// TABLE

=데이터 표 

열과 행 // column, Row

데이터의 성격

성격에 대한 구분

교차 지점 = 필드 // 접근은 열과 행의 정보가 필요

레코드// 행에 있는 구체적인 데이터

> 데이터 클라이언트 //요청 

### 설치



파일은 접근, 수정이 쉬움

> 데이터베이스의 필요성

#### 서버 접속

권한에 따라 나누어서 나중에 margin하는 편이 안전함 > 깃허브

p쳐서 들어가면 스키마에 접근할 수 있음

#### 스키마의 사용

데이터 베이스의 생성

```
create database first;
> Query OK, 1 row affected (0.42 sec)
```

삭제

```
drop database first;
```

확인

```
show databases;
+--------------------+
| Database           |
+--------------------+
| first              |
| information_schema |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
5 rows in set (0.00 sec)
```

스키마 진입

```
mysql> use first;
Database changed
```

   

#### sql과 테이블의 구조

Structured Query Language

   

#### 테이블의 생성

1. column생성 =목차가 있어야 함.

>  cheat sheet

정리 정돈된 자료를 찾을 수 있음

column의 데이터 타입을 강제할 수 있다.

> 방대한 데이터에서 정리되게 저장하면 로드 할 때 에러 없고, 빠르게 가능해서 , 그리고 에러가 나니까 입력을 강제 시킴

+ datatype

integer

+ varchar

=variable char

   

```
CREATE TABLE food(
```

테이블 `food`생성

```
 id int(11) not null auto_increment,
```

1 Column `id`, `정수형`, `공백값 제외`, `자동으로 id의 정수 증가`

```
title VARCHAR(100) NOT NULL,
```

2 Column `title`, `입력 문자 100개 제한`, `널값 포함하지 않음`

```
description TEXT NULL,
```

3 Column  `description`, `텍스트 저장 값이 큰 형태`, `널값 포함`

...

```sql
mysql> CREATE TABLE food(
    -> id INT(11) NOT NULL AUTO_INCREMENT,
    -> title VARCHAR(100) NOT NULL,
    -> description TEXT NULL,
    -> created DATETIME NOT NULL,
    -> author VARCHAR(30) NULL,
    -> profile VARCHAR(100) NULL,
    -> PRIMARY KEY(id));
Query OK, 0 rows affected, 1 warning (2.18 sec)
```

   

#### CRUD

+ Create

+ Read

+ Update

+ Delete



####  INSERT

DESC: 구조를 표로 표시

```
DESC topic;
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| id          | int(11)      | NO   | PRI | NULL    | auto_increment |
| title       | varchar(100) | NO   |     | NULL    |                |
| description | text         | YES  |     | NULL    |                |
| created     | datetime     | NO   |     | NULL    |                |
| author      | varchar(30)  | YES  |     | NULL    |                |
| profile     | varchar(100) | YES  |     | NULL    |                |
+-------------+--------------+------+-----+---------+----------------+
6 rows in set (0.00 sec)
```

데이터 생성

```
INSERT INTO topic (title,description,created,author,profile) VALUES('MySQL','MySQL is ....',NOW(),'egoing','developer');
Query OK, 1 row affected (0.41 sec)
```



데이터 로드

```
mysql> SELECT*FROM topic;
+----+--------+-----------------------+---------------------+--------+-----------+
| id | title  | description           | created             | author | profile   |
+----+--------+-----------------------+---------------------+--------+-----------+
|  1 | MySQL  | MySQL is ....         | 2020-02-17 21:55:32 | egoing | developer |
|  2 | C      | C is Low Language     | 2020-02-17 22:13:14 | B      | developer |
|  3 | JAVA   | JAVA is High Language | 2020-02-17 22:14:52 | tree   | developer |
|  4 | HTML   | HTML is Web Language  | 2020-02-17 22:17:31 | NULL   | developer |
|  5 | Docker | Docker is container   | 2020-02-17 22:23:02 | ALL    | developer |
+----+--------+-----------------------+---------------------+--------+-----------+
5 rows in set (0.00 sec)
```



#### SELECT

`SELECT`  column `FROM` table `WHERE `특정 행의 데이터 `ORDER BY` id `DESC`;

```
mysql> SELECT id,created,title FROM topic ORDER BY id DESC;
+----+---------------------+--------+
| id | created             | title  |
+----+---------------------+--------+
|  5 | 2020-02-17 22:23:02 | Docker |
|  4 | 2020-02-17 22:17:31 | HTML   |
|  3 | 2020-02-17 22:14:52 | JAVA   |
|  2 | 2020-02-17 22:13:14 | C      |
|  1 | 2020-02-17 21:55:32 | MySQL  |
+----+---------------------+--------+
5 rows in set (0.00 sec)
```

정렬의 조건, 산술 연산자, 

   

#### UPDATE

```
mysql> UPDATE topic SET description='SQL is very Low Language', title='SQL' WHERE id='1';
Query OK, 1 row affected (0.27 sec)
Rows matched: 1  Changed: 1  Warnings: 0
```



   

#### DELETE

```
DELETE FROM topic WHERE id='5';
Query OK, 1 row affected (0.18 sec)
```

   

> Relational

#### 관계형 데이터 베이스의 필요성

데이터의 중복 > 개선할 필요성이 있음





​    

#### 테이블 분리하기

테이블을 두 개 생성

1. 데이터가 들어 있는 테이블
2. 1의 분할된 데이터가 들어 있는 테이블

+ 1의 column을 2의 id로 수정
+ 새로운 데이터가 존재하면 2)테이블의 데이터를 추가
+ 참조할 때 표를 붙게 출력하는 옵션을 넣고, 참조

​    

#### JOIN

+ 데이터 베이스의 역할



```
SELECT* FROM topic LEFT JOIN author ON topic.author_id= author.id;
```

JOIN의 기준 `ON`



```
SELECT topic.id,title,description,created,name,profile FROM topic LEFT JOIN author ON topic.author_id= author.id;
```



+ 테이블 합칠 때 id값 수정 가능

```sql
SELECT topic.id AS topic_id, .....
```



   

#### MySQL Workbench

+ My SQL client 중 하나

=우리가 하는 것은 sql문을 생성에서 서버에 전송하는 것

_자동으로 명령문을 생성해줌_

데이터의 정보

데이터 서버의 백업,이전, 확인 가능



> DATABASE MySQL수업 끝
>
> ~02.18 / 16:31

+ index	//데이터의 양
+ modeling //데이터의 모양 :정규화, 비정규화, 역정규화 등등
+ backup //mysqldump 등등
+ cloud //대기업 회사들의 데이터 서버를 원격으로 빌림 
+ programming //정보 관리 기능을 이용해서 자신의 문제를 해결 python mysql.api,PHP ,Java 등등 쉽게 핸들링 가능한 조작 장치를 찾을 수 있음.


