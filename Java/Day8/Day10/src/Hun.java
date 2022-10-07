public class Hun {
    public static void main(String[] args) {
        ThreadTask th = new ThreadTask();

        ThreadTask3 threadTask3 = new ThreadTask3(th);
        ThreadTask4 threadTask4 = new ThreadTask4(th);


        threadTask3.start();
        threadTask4.start();



    }
}
