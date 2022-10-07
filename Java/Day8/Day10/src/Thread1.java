public class Thread1 {
    public static void main(String[] args) {
        Thread th1 = new PrintThread1();
        Runnable r1 = new PrintThread2();
        Thread th2 = new Thread(r1);
        Thread th3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i<10;i++){
                    if(i%2==0){
                        System.out.println("🚀 PrintThread1 : "+i);
                    }
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        th1.start();
        th2.start();
        th3.start();
        for(int i =0;i<10;i++){
            if(i%2==0){
                System.out.println("🍊 main : "+i);
            }
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
