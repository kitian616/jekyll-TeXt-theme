---
title: 두 개의 클래스가 서로를 사용해야하는 경우
tag: cpp
---



[https://namoeye.tistory.com/entry/%EC%BB%B4%ED%8C%8C%EC%9D%BC%EA%B3%BC%EC%A0%95-%EC%A0%95%EB%A6%AC-cpp%EC%99%80-h%EB%A5%BC-%EB%82%98%EB%88%84%EB%8A%94-%EC%9D%B4%EC%9C%A0](https://namoeye.tistory.com/entry/컴파일과정-정리-cpp와-h를-나누는-이유)

+ 컴파일러가 위에서 아래로 번역한다.

```cpp
class A
{
    public:
    void showData()
    {
        cout << "A객체"<<endl;
    }
};

class B
{
public:
	B()
    {
        mp_a_obj=NULL;
    }
    
    void Link(A * ap_a_obj)
    {
        mp_a_obj = ap_a_obj;
        cout<<"A와 연결됨."<<endl;
    }
   
    void showData()
    {
        cout<<"B객체"<<endl;
    }
    
    void shoDataA()
    {
        mp_a_obj->showData();
    }
private:
    A *mp_a_obj;
};


```

