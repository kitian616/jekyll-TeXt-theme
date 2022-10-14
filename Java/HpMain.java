import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class HpMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        HpContoller hc = new HpContoller();//다른 클래스에서 메소드 호출하기 위해
        hc.setList(new ArrayList<Hp>());

        String file = "Phone.txt";//불러오기

        hc.read(file);//읽어오기

        System.out.println("✔   전화 번호부 메뉴   ✔");
        while(true){
            System.out.println("✔A : 전화번호부 입력");
            System.out.println("✔B : 전화번호부 출력");
            System.out.println("✔C : 전화번호 검색");
            System.out.println("✔D : 전화 번호 저장");
            System.out.println("✔E : 전화번호 삭제");
            System.out.println("✔F : 전화번호 수정");
            System.out.println("✔G : 종료");
            String A = sc.next();
            if(A.equals("G")) break;
// case안에 조건식을 사용하는 경우 코드에 가독성이 떨어지기 때문에
// 따로 클래스를 만들어 메소드를 이용해 보자

            switch (A){//입력
                case "B" ://리스트 출력 메서드 이용
                    hc.list();

                    break;
                case "C"://찾는 번호
                    if(!hc.find()) System.out.println("없습니다.");
                    break;
                case "D"://저장
                        hc.save(file);
                        break;
                case "E"://삭제
                        hc.delete();
                        break;
                case "F": //수정
                    hc.update();
                    break;
                default :
                    hc.insert(A);
            }

        }

    }
}