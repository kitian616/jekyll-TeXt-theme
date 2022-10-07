import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionTask {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int value = 0;
        try {
            System.out.println("숫자를 입력하세요 : ");
            value = sc.nextInt();


        } catch (InputMismatchException im) {
            value = -1;
        } finally {

                System.out.println("value : " + value);
            }
        }

    }
