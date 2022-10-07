//public class ThreadTask2 {
//    public synchronized void methodC(){
//        System.out.println("B공격");
//        notify();
//        try{
//            wait();
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//    }
//    public synchronized void methodD(){
//        System.out.println("B방어");
//        notify();
//        try{
//            wait();
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//    }
//}
