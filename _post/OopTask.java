import java.util.Scanner;

public class OopTask {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("몇 명의 학생을 등록할까요?");
        Student1[] st = new Student1[3];
        st[0] = new Student1();
        st[1] = new Student1();
        st[2] = new Student1();


        int size = sc.nextInt();

        for (int i = 0; i < size; i++) {
            System.out.println((i+1)+ "번쨰 학생의 번호를 입력하세요");
            st[i].setNo(sc.nextInt());
            System.out.println((i+1)+ "번쨰 학생의 이름을 입력하세요");
            st[i].setName(sc.next());
            System.out.println((i+1)+ "번쨰 학생의 국어점수를 입력하세요");
            st[i].setKor(sc.nextInt());
            System.out.println((i+1)+ "번쨰 학생의 수학점수를 입력하세요");
            st[i].setMath(sc.nextInt());
            System.out.println((i+1)+ "번쨰 학생의 영어점수를 입력하세요");
            st[i].setEng(sc.nextInt());
        }

            for(Student1 s : st ){
                System.out.println(s);
            }
//        for (int i=0;i<size;i++) {
//
//            System.out.println(st.toString());
//
//
//        }
    }}