import java.util.ArrayList;
import java.util.Scanner;

public class WordMain {
    public static void main(String[] args) {
        System.out.println("😍[영어단어장]😍");
        System.out.println("print : 지금까지 등록한 영단어가 출력");
        System.out.println("find : 영어 단어를 검색할 수 있음");
        System.out.println("save : 파일에 저장");
        System.out.println("exit : 프로그램을 종료");

        Scanner sc  = new Scanner(System.in);

        WorldController wc = new WorldController();
        wc.setList(new ArrayList<World>());

        String file = "wordbook.txt";//불러오기
        wc.read(file);//읽어오기
        while(true){
            System.out.println("✔ 영어단어 또는 메뉴를 입력하세요");
            String eng = sc.next();
            if (eng.equals("exit")) break;

            // case안에 조건식을 사용하는 경우 코드에 가독성이 떨어지기 때문에
            // 따로 클래스를 만들어 메소드를 이용해 보자

            switch (eng){
                case "print":
                    wc.list();
                    break;
                case "find":
                    if(!wc.find()) System.out.println("찾는 단어가 없습니다.");
                    break;
                case "save":
                    wc.save(file);
                    break;
                default:
                    wc.insert(eng);

            }
        }
    }
}
