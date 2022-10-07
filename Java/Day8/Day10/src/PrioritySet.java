public class PrioritySet extends Thread{
    PrioritySet(){}

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        int priority = Thread.currentThread().getPriority();
        System.out.println(name+" > "+priority);
    }
}
