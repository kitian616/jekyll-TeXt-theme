public class Oop1 {
    public static void main(String[] args) {
        Student student1 = new Student();
        //자동 생성, 생성자 호출
        //student1 이라는 참조변수룰 사용
        student1.info();//생성자 활용
        //처음에 데이터 채우기 -> 빈 생성자를 만들어야한다.
        Student student2 = new Student(1,"sonmg","0120659",199,22,54);
        //새성자에 값을 변수를 선언해서 매인에서 값넣기
        student2.info();//메소드 호춯 및 인스턴스 객체화
        Student student3 = new Student();
        // 직접 값을 수정(넣고)하고 싶지만 class에서 private이기 때문에 Setter메소드를 사용한다.
        student3.info();
        student3.setNo(2);
        student3.setEng(100);
        student3.setHp("4568754687456");
        student3.setKor(23);
        student3.setMath(45);
        student3.setEng(245);
        student3.info();


        //따로 적재가 되지는 않고 생성자에 따라 다르게 생각하자.
    }
}
