public class ThreadTask4 extends Thread {
    private ThreadTask threadTask ;

    ThreadTask4(ThreadTask threadTask){
        this.threadTask = threadTask;
    }
    @Override
    public void run(){
        for(int i=0;i<10;i++){
            threadTask.methodB(i);
        }
    }

}
