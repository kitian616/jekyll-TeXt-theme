public class PrintThread1 extends Thread {
    @Override
    public void run() {
        for(int i =0;i<10;i++){ // 스레드를 호출하면 실행하게 됨
            if(i%2==0){
                System.out.println("😁 PrintThread1 : "+i);
            }
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
