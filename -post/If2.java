import java.util.Scanner;

public class If2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("나이를 입력하세요 : ");
        int age = sc.nextInt();
        if(age>19) System.out.println("성인입니다.");
        else System.out.println("미성년입니다.");
        System.out.println("당신의 나이 : " + age);
    }
}
