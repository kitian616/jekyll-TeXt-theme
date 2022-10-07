import java.util.*;

public class Student {
    //    메뉴
//1. 학생입력 -----> 학번, 이름, 나이, 연락처
//2. 학생리스트 -------> 학번으로 오름차순
//3. 학생검색
//4. 학생삭제
//5. 종료
    public static void main(String[] args) {
        System.out.println("✔메뉴✔");
        System.out.println("1번 : 학생정보를 입력");
        System.out.println("2번 : 학생리스트 출력");
        System.out.println("3번 : 학생검색");
        System.out.println("4번 : 학생삭제");
        System.out.println("5번 : 종료");

        Scanner sc = new Scanner(System.in);

        ArrayList<List> list = new ArrayList<>();

        while(true){
            System.out.println("메뉴 번호를 입력 : ");
            int num =sc.nextInt();
//            String st = sc.next();
//            if(st.equals(5)) break;
            switch (num){
                case 1:
                    System.out.println("학번을 입력 : ");
                    int no =sc.nextInt();
                    System.out.println("이름을 입력 : ");
                    String name = sc.next();
                    System.out.println("나이 입력 : ");
                    int age = sc.nextInt();
                    System.out.println("연락처 입력 : ");
                    int ph = sc.nextInt();
                    List l = new List(no,name,age,ph);
                    list.add(l);
                    break;
                case 2:
                    Iterator<List> iterator = list.iterator();
                    while(iterator.hasNext()){
                        for(List i : list){
                            if(list.get().getNo()>list.get().getNo())
                            System.out.println(iterator.next()+ " ");
                        }
                    }

                    break;
                case 3:
                    System.out.println("찾는 학생은?");
                    String find = sc.next();
                    boolean isfind = false;
                    for(int i=0;i< list.size();i++){
                        if(list.get(i).getName().equals(find)){
                        System.out.println("찾았습니다.");
                        System.out.println(list.get(i));
                        isfind = true;
                    }

                }if(!isfind) System.out.println("없습니다.");
                    break;
                case 4:
                    System.out.println("삭제할 학생은?");
                    String na = sc.next();
                    boolean isFind = false;
                    for(int i=0; i< list.size();i++){
                        if(list.get(i).getName().equals(na)){
                            System.out.println("  찾음");
                            list.remove(i);
                            System.out.println(list.get(i));
                            isFind = true;
                    } }if(!isFind) System.out.println("없습니다.");
                    break;

                case 5:
                    System.out.println("종료");
                    break;



            } if(num==5)break;

        }

    }

}



















