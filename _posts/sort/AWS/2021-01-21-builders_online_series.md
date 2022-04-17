---
title: AWS builders online series
tags: AWS
---


## 목적에 맞는 다양한 관리형 Database 서비스 알아보기  
  
- 데이터의 증가, 데이터 및 분석 요구 사항 변경 ,데브옵스 기반  
- 이러한 데이터의 발생을 효율적으로 관리하기 위해서는 DB를 바꾸어야할 필요성이 생김 -> 완전 관리형 데이터베이스, 용도에 맞는 DB  
- 관계형 DB -> RDS, 오로라  

![DB](https://imgur.com/MsZeUys.png)  

오픈 소드 DB는 한계가 있어서 사용 DB 수준의 성능과 안정성을 원함. (아마존 오로라의 탄생 배경)

- Aurora Serverless  

- Amazon DynamoDB로 앱 현대화  
  key-value 처리, 확장 용이, 서버리스 아키텍처, 엔터 수준의 보안, 글로벌 복제  

![DynamoDB](https://imgur.com/zWhWR0T.png)  

-> `데이터 분산`을 위한 파티션 키, 빠른 검색을 위한 소프트키



[원문: aws-builders-online-series](https://kr-resources.awscloud.com/aws-builders-online-series/fy21q1-aws-builders-online-series-kr-track1-session1-ondemand)  

---  

## 다양한 업무에 적합한 AWS의 스토리지 서비스 알아보기    

- 스토리지의 일반적인 사용 용도  
  애플리케이션 파일 저장소, 사용자 파일 저장소, 데이터베이스, 분석, 백업 및 재해 복구  
  블록, 파일, 오브젝트  
  
- 블록 스토리지, Amazon Elastic Block Store (EBS)  
  EBS multi attach 다중 연결 기능  

- Amazon Elastic File System (EFS)  
  파일 기반 애플리케이션의 다양한 요구 사항을 만족하는 안전하고 다양한 기능을 제공하는 AWS의 완전 관리형 파일 스토리지
  이미 만들어진 파일시스템을 OS에 붙여서 사용하는 방식
  클라우드 네이티브 NFS 파일 스토리지 서비스  
  하이브리드  
  리전단위 서비스 (리전에 있는 모든 가용 영역을 사용해서 파일시스템이 생성됨.)

![EFS](https://imgur.com/fTM3a3q.png)  
![EFS 호환](https://imgur.com/ExH0nVA.png)  
![EFS 고가용성 아키텍처](https://imgur.com/34fN4al.png)  
  
- Amazon EFS 수명 주기    
  엑세스 80% 안씀  
  Infrequent access(IA) 스토리지 클래스로 보내버림. (Cost down)  

- 데이터 마이그레이  
  DataSync 서비스  
  on premises -> Region 할때, 쉽게 가능

- EFS 백업  
자동화된 백업체계 구축 가능함.  
  
- EFS 고가용성 아키텍처   
  하이브리드 운영 가능함.  
  증분 백업이 기능함.  

- Amazon FSx for Windows  
  윈도우 서버에서 구축, 윈도우 파일시스템 호환  

- Amazon Simple Storage Service (S3)  
  보안, 규정 준수, inspection 기능
  스토리지 렌즈(대시보드 기능)

![S3](https://imgur.com/OcAIvc1.png)  
  
[아마존 무료 교육](aws.amazon.com/trining)  

---  
  
## AWS 에서 DevOps 시작하기    

- 오퍼레이션 모델   
- 아키텍처 패턴  
- 소프트웨어 배포  

- 아마존의 데브옵스
작은 단위, 자동화, 표준 도구, 프로세스 공정화, 코드로서의 인프라

- 모놀리스 개발 라이프 사이클
운영 단일 파이프라인 -> 분할  

![lifecycle](https://imgur.com/jw7mV8s.png)  

- 작은 단위로 서비스를 나누는 원칙
 API를 이용해서 통신하는 것 
-> 투 피자 팀(12~15명의 팀), 조직 재구성 (중요한 과정, 문화적 변화 요건), 모든 것을 개발팀이 직접 한다.
현대적 애플리케이션 요구사항  

![aotumation](https://imgur.com/0qk1BVD.png)  

- DevOps 파이프라인 예제
철저한 검증 -> 프로덕션(베타, 감마, 프로덕션) //카오스 엔지니어링  

- CI/CD를 위한 AWS 개발자 도구  

![tools](https://imgur.com/aC3cTMK.png)  

- AWS Cloud Development Kit  

![kit](https://imgur.com/CUfKa6Y.png)  

- CDK: 개별 개발환경 배포  

![deploy](https://imgur.com/hAGx3gM.png)  

- Amazon DevOps Guru  

![guru](https://imgur.com/cnXLhAB.png)  

[원문 AWS 에서 DevOps 시작하기](https://kr-resources.awscloud.com/aws-builders-online-series?undefined=)  
  
---  
  
