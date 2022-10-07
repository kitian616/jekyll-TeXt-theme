public class ThreadB extends Thread{
    boolean stop = false;
    boolean work = true;

    @Override
    public void run() {
        while(!stop){
            if(work){
                System.out.println("ThreadB 🤢");
                try{
                    sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("B일 멈춤");
                Thread.yield();
            }
        }
        System.out.println("ThreadB 종료!");
    }
}
