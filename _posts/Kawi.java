import java.util.Scanner;

/*
제2.
가위바위보(1,2,3) 게임을 만들어보자.
1. 랜덤한 주사위 값을 뽑아 입력한 값과 일치하는지 확인
2. 값의 범위는 1~3까지로 함
(문자비교를 해도됨)
3. 값을 맞출때 까지 무한루프를 사용

    가위, 바위, 보 중 하나를 선택하세요. 가위
    컴퓨토 : 바위
    나 : 가위
    젔습니다.

    가위, 바위, 보 중 하나를 선택하세요. 가위
    컴퓨토 : 보
    나 : 가위
    이겼습니다. 프로그램을 종료합니다.
 */
public class Kawi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("🎈가위바위보 게임🎈");


        while (true) {
            int rn = (int) (Math.random() * 3) + 1;

            System.out.print("가위 바위 보 중에 하나를 선택하세요 : ");
            int my = sc.nextInt();
            System.out.println(my);
            if (rn == 1 && my == 1) {
                System.out.println("컴퓨터 : 가위");
                System.out.println("나 : 가위");
                System.out.println("비겼습니다.");
                continue;
            } else if (rn == 1 && my == 2) {
                System.out.println("컴퓨터 : 가위");
                System.out.println("나 : 바위");
                System.out.println("이겼습니다.");
                break;
            } else if (rn == 1 && my == 3) {
                System.out.println("컴퓨터 : 가위");
                System.out.println("나 : 보");
                System.out.println("젔습니다.");
                continue;
            }else if (rn == 2 && my == 1) {
                System.out.println("컴퓨터 : 바위");
                System.out.println("나 : 가위");
                System.out.println("젔습니다.");
                continue;
            }else if (rn == 2 && my == 2) {
                System.out.println("컴퓨터 : 바위");
                System.out.println("나 : 바위");
                System.out.println("비겼습니다.");
                continue;
            }else if (rn == 2 && my == 3) {
                System.out.println("컴퓨터 : 바위");
                System.out.println("나 : 보");
                System.out.println("이겼습니다.");
                break;
            }else if (rn == 3 && my == 1) {
                System.out.println("컴퓨터 : 보");
                System.out.println("나 : 가위");
                System.out.println("이겼습니다.");
                break;
            }else if (rn == 3 && my == 2) {
                System.out.println("컴퓨터 : 보");
                System.out.println("나 : 바위");
                System.out.println("젔습니다.");
                continue;
            }else if (rn == 3 && my == 3) {
                System.out.println("컴퓨터 : 보");
                System.out.println("나 : 보");
                System.out.println("비겼습니다.");
                continue;
            }


        }


    }
}