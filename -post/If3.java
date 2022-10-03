import java.util.Scanner;

public class If3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("나이를 입력 : ");
        int age = sc.nextInt();

        if(age >19) {//모든문장은 19살보다 크다 하지만 if문에서는
// 위에서 아래로 이동하며 true이면 if문을 나가기 때문에 위 문제가 발생하지 않는다.
            System.out.println("성인입니다.");
        }else if(age>14){
            System.out.println("청소년입니다.");
        }else if(age>6){
            System.out.println(" 어린이입니다.");
        }else{
            System.out.println("유아입니다.");
        }
        System.out.println("당신의 나이는 : "+ age);
    }
}
