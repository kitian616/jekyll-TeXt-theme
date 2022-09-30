import java.util.Scanner;

public class Oop4 {
    public static void main(String[] args) {
        Fruit[] fruits = new Fruit[3];

        fruits[0] = new Apple("사과", 1000, "red", "청주", "소과");
        fruits[1] = new Banana("바나나", 1500, "yello", "필리핀", "델몬트");
        fruits[2] = new Orange("오렌지", 2000, "orange", "캘리포니아", 8);

//        for(Fruit fr : fruits){
//            System.out.println(fr);
//        }
        Fruit[] order = new Fruit[10];//구입한 과일 목록을 저장할 배열
        int cnt = 0;//과일 구입 갯수

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("🥝🍇🍎🥭🍍🍌🍋🍊🍉매뉴를 고르세요🍈🍏🍐🍑🍒🍓🍅🍆🌽");
            System.out.println("1. 사과구입🍎");
            System.out.println("2. 바나나🍌");
            System.out.println("3. 오렌지🍊");
            System.out.println("4. 구입목록 보기");
            System.out.println("5. 과일 검색");
            System.out.println("6. 프로그램 종료");
            int sel = sc.nextInt();
            if (sel == 6) break;
            switch (sel) {
                case 1:
                case 2:
                case 3:
                    order[cnt++] = fruits[sel - 1];
                    System.out.println(fruits[sel - 1].getName() + "가 담겼습니다.");
                    break;
                case 4:
                    System.out.println("구입한 과일 👌👌");
                    for (int i = 0; i < cnt; i++) {
                        System.out.println((i + 1) + ":" + order[i].toString());
                    }
                    break;
                    case 5 :
                    System.out.println("검색할 과일을 선택하세요");
                        System.out.println("1.사과 2.바나나 3. 오렌지");
                        int choice = sc.nextInt();
                        System.out.println(fruits[choice-1]);
                        break;
                default:
                    System.out.println("입력을 확인하세여");
            }
        }
    }
}
