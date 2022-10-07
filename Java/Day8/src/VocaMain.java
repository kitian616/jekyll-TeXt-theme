import java.util.ArrayList;
import java.util.Scanner;

public class VocaMain {// 시험과 유사한 문제
    public static void main(String[] args) {
        System.out.println("😍[영어단어장]😍");
        System.out.println("print : 지금까지 등록한 영단어가 출력");
        System.out.println("find : 영어 단어를 검색할 수 있음");
        System.out.println("exit : 프로그램을 종료");

        Scanner sc = new Scanner(System.in);

        ArrayList<Word> list = new ArrayList<>();
        while (true) {
            System.out.println("✔ 영어단어 또는 메뉴를 입력하세요");
            String eng = sc.next();
            if (eng.equals("exit")) break;

            switch (eng) {
                case "print":
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(list.get(i));
                        //System.out.println(list.get(i).getEnglish()+"/"+list.get(i).getKorean());
                    }
                    break;
                case "find":
                    System.out.println("🤷‍찾는 단어를 입력하세요");
                    String find = sc.next();
                    boolean isFind = false;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getEnglish().equals(find)) {
                            System.out.println("있습니다.");
                            System.out.println(list.get(i));
                            isFind = true;
                        }
                        } if(!isFind) System.out.println("없습니다.");
                        break;

                default:
                    System.out.println("* 뜻을 입력하세요");
                    String kor = sc.next();
                    System.out.println("* 레벨을 입력하세요");
                    int lev = sc.nextInt();
                    System.out.println("* 날짜를입력하세요");
                    String wdate = sc.next();

                    //w는  stack에 저장되어 계속 유지를 못해 arraylist를 이용해서 저장시킨다.
                    Word w = new Word(eng, kor, lev, wdate);
                    list.add(w);
                    System.out.println(list);
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }
}
