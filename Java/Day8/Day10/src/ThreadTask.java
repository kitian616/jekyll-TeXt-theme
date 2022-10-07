//       공격과 방어를 하는 프로그램을 작성해보자.
//        (단, 스레드르 사용)
//        1. 공격 class 생성
//        2. 방어 class 생성
//        3. 공격을 하지 않았는데 방어를 할 수 없음
//        4. 방어를 하지 않았는데 공격을 또 할 수 없음
//
//        1번째 공격
//        1번째 방어
//        2번째 공격
//        2번째 방어
//...
//        10번째 공격
//        10번째 방어

public class ThreadTask {
    public synchronized void methodA(int i){
        System.out.println((i+1)+"번 방어");
        notify();
        try{
            wait();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public synchronized void methodB(int i){

            System.out.println((i+1)+"번 공격");
        notify();
        try{
            wait();
        }catch (InterruptedException e) {
            e.printStackTrace();

        }
}}

