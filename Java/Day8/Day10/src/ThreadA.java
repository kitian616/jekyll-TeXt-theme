public class ThreadA extends Thread{
    boolean stop = false;
    boolean work = true;

    @Override
    public void run() {
        while(!stop){
            if(work){
                System.out.println("ThreadA 😁");
                try{
                    sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("A일 멈춤");
                Thread.yield(); // 양보
            }
        }
        System.out.println("ThreadA 종료!");
    }
}
