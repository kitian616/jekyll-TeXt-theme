import java.util.Scanner;


/*
과제1.
주사위 게임을 만들어보자.
1. 랜덤한 주사위 값을 뽑아 입력한 값과 일치하는지 확인
2. 값의 범위는 1~6까지로 함
3. 값을 맞출때 까지 무한루프를 사용

    주사위를 던집니다
    값을 입력하세요. 3
    틀렸습니다. 값은 1입니다.

    //무힌루프

    주사위를 던집니다
    값을 입력하세요. 2
    맞췄습니다. 프로그램을 종료합니다.
 */

public class Jusawi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        System.out.println("🎈주사위 게임🎈");


        while (true) {

            System.out.println("주사위를 던집니다.");
            System.out.print("값을 입력하세여 : ");
            int my = sc.nextInt();
            System.out.println(my);

            while (true) {
                int rn = (int) (Math.random() * 6) + 1;
                while (my != rn) {

                    System.out.println("틀렸습니다. 값은 : " + rn);
                    break;
                }
                if (my == rn) {

                    System.out.println("맞췄습니다. 프로그램을 종료합니다.");
                    break;
                }
                break;
            }
        }
    }
}

