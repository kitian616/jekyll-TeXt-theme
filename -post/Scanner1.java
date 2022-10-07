import java.util.Scanner;

public class Scanner1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);//alt enter -> java.util~
        int input = sc.nextInt();//정수형으로 저장할 수 있다.
        System.out.println("input : "+ input);
        double input2 = sc.nextDouble();//실수형으로 저장
        System.out.println("input2 : "+ input2);
    }
}
