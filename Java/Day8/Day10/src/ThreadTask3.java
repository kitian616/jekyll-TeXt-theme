public class ThreadTask3 extends Thread {
    private ThreadTask threadTask;

    ThreadTask3(ThreadTask threadTask) {
        this.threadTask = threadTask;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            threadTask.methodA(i);



        }
    }

}
