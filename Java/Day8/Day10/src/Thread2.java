public class Thread2 {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Thread customer1 = new Thread(bank, "김사과");
        Thread customer2 = new Thread(bank, "긴중과");
        Thread customer3 = new Thread(bank, "김대과");
        Thread customer4 = new Thread(bank);//이름을 브여하지 않으면 자동 반영

        customer1.start();//비동기식 처리
        customer2.start();
        customer3.start();
        customer4.start();
    }
}
