public class List {
//    메뉴
//1. 학생입력 -----> 학번, 이름, 나이, 연락처
//2. 학생리스트 -------> 학번으로 오름차순
//3. 학생검색
//4. 학생삭제
//5. 종료
private int no;
private String name;
private int age;
private int ph;

    public List(int no, String name, int age, int ph) {
        this.no = no;
        this.name = name;
        this.age = age;
        this.ph = ph;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPh() {
        return ph;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }

    @Override
    public String toString() {
        return "List(" +
                "학번 : " + no +
                ", 이름 : " + name +
                ", 나이 : " + age +
                ", 연락처 : " + ph +
                ")";
    }
}
