public class Oop2 {
    public static void main(String[] args) {
        Student student = new Student();
        //빈 생성자 호출로 기본값 들어있음
        student.setNo(1);
        student.setName("김사과");
        student.setHp("010-548-152185");
        student.setKor(100);
        student.setMath(90);
        student.setEng(90);
        student.info();
        System.out.println(student);// 가상 메모리를 가리키는 주소값
        // 위의 값과 아래의 값은 동일한다. .toString은 sout에서 생략하여 쓸 수 있다.
        System.out.println(student.toString());// heap의 주소 즉 가상 메모리 주소
        // 패키지 + 클래스 + 메모리 주소 => 출려된다. -> 가상메모리 주소
        Student[] students = new Student[6];//배열선언
        students[0] = new Student();// 빈 생성자를 만들고 값을 채워넣자
        students[0].setNo(2);
        students[0].setName("송바나나나");
        students[0].setKor(90);
        students[0].setMath(80);
        students[0].setEng(70);
        students[0].info();

        students[1] = new Student();// 빈 생성자를 만들고 값을 채워넣자
        students[1].setNo(3);
        students[1].setName("나나나");
        students[1].setHp("5445나나나");
        students[1].setKor(60);
        students[1].setMath(20);
        students[1].setEng(78);
        students[1].info();

        students[2] = new Student(4,"sdw","46654",50,51,241);
        students[2].info();//한번에 값 넣기


        for( Student s : students){
            System.out.println(s);
            System.out.println(s.getName());
        }

    }
}
