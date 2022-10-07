public class PrintThread2 implements Runnable {
    @Override // Runnable을 이용한 스레드 생성 방법
    public void run() {
        for(int i =0;i<10;i++){
            if(i%2==0){
                System.out.println("🐱👤 PrintThread1 : "+i);
            }
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
