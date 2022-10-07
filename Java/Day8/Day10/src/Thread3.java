class Runnable1 implements Runnable {

    @Override
    public void run() {
        System.out.println("스레드1 동작 중.....");
        testThread1();
        for (int i = 0; i < 5; i++) {
            System.out.println("🐱‍🚀 익명클래스: " + i);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testThread1() {
        System.out.println("스레드2 동작 중.....");
        testThread2();
    }

    public void testThread2() {
        System.out.println("스레드3 동작 중.....");
    }

}


public class Thread3 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " Start!");
        Runnable r = new Runnable1();
        Thread th1 = new Thread(r);
        th1.start();
        try{
            th1.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " End!");
    }
}
