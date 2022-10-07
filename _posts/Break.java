import java.util.Scanner;

public class Break {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("음식 자판기");

        while(true) {
            System.out.println("✔원하는 메뉴를 선택✔");
            System.out.println("1. 피자 2. 치킨 3. 냉면 4. 김치찜 5. 종료");
            int sel = sc.nextInt();
            switch (sel) {
                case 1:
                    System.out.println("피자가 나왔습니다.");
                    break;
                case 2:
                    System.out.println("치킨이 나왔습니다.");
                    break;
                case 3:
                    System.out.println("냉면 나왔습니다.");
                    break;
                case 4:
                    System.out.println("김치찜이 나왔습니다.");
                    break;
                case 5:
                    System.out.println("종료");
            }
            if(sel == 5)break;
        }
    }
}

