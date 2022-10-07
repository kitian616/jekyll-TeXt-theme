public class Bank implements Runnable { // Thread를 사용하기 위해 Runnable사용
    private int money = 10000000;

    public void withdraw(int money){
        this.money -= money;
        System.out.println(Thread.currentThread().getName()+"가"+money+"출금");//현재 스레드를 알 수 있는 메소드/name은 이름
        System.out.println("잔액 : " + this.money+"원");
    }

//    public synchronized void withdraw(int money){// synchronized 통해 동기화
//        this.money -= money;
//        System.out.println(Thread.currentThread().getName()+"가"+money+"출금");//현재 스레드를 알 수 있는 메소드/name은 이름
//        System.out.println("잔액 : " + this.money+"원");
//    }

    @Override // 실 작업공간
    public void run() {
        synchronized (this){// 전체가 코드가 동기화 되어 하나 씩 읽어가는 부분이 있다.
            for(int i=0;i<10;i++){
                withdraw(100000);
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
