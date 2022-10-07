public class Thread4 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new PrioritySet(), "김사과");
        Thread thread2 = new Thread(new PrioritySet(),"김중과");
        Thread thread3 = new Thread(new PrioritySet(),"김대과");

        thread1.setPriority((Thread.MAX_PRIORITY));
        thread2.setPriority((Thread.NORM_PRIORITY));
        thread3.setPriority((Thread.MIN_PRIORITY));

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
