import java.util.Scanner;

public class If1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("나이를 입력하세요");
        int age = sc.nextInt();

        if (age > 19) System.out.println("성인입니다.");
        //대괄호가 없으며, 성인입니다만 조건에 따라 실행하고 나이는 자동출력이다.
        System.out.println("당신의 나이 : " + age);
    }
}
