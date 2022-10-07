import java.util.Scanner;

public class Static3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("radius 입력 : ");
        double r = sc.nextDouble();
        double area = CircleUI.calcCircle(r);// static이 있어 클래스명.메소드 이름
        System.out.println("원의 넓이 : "+area);
    }
}
