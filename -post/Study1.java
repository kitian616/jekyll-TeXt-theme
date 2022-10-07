import java.util.Scanner;
public class Study1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("1과목 점수 : ");
        int a = sc.nextInt();
        System.out.print("2과목 점수 : ");
        int b = sc.nextInt();
        System.out.print("3과목 점수 : ");
        int c = sc.nextInt();
        int sum = a + b + c;
        double avg = sum / 3.0;
        System.out.println("총점은 " + sum + "평균은 " + avg);

        switch ((int) (avg/10)) {
            case 9: case 10:
                System.out.println("A");
                break;
            case 8:
                System.out.println("B");
                break;
            case 7:
                System.out.println("C");
                break;
            case 6:
                System.out.println("D");
                break;
            default:
                System.out.println("F");

        }
    }}
