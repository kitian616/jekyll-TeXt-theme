public class Student {
    private  int no;
    private  String name;
    private  String hp;
    //heap에 저장되기 때문에 참조변수가 필여하고 null로 초기값을 넣는다.
    private  int kor;
    private  int math;
    private  int eng;

    Student(){
    //클래스내 생성자 안에 메개변수 사용하기 위해서는 빈 생성자를 만들어야 오류가 안나온다.
    }

    // 생성자 만들기 우클릭 -> generate -> constructor
    public Student(int no, String name, String hp,
                   int kor, int math, int eng) {
        this.no = no;
        this.name = name;
        this.hp = hp;
        this.kor = kor;
        this.math = math;
        this.eng = eng;
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

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public int getKor() {
        return kor;
    }

    public void setKor(int kor) {
        this.kor = kor;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEng() {
        return eng;
    }

    public void setEng(int eng) {
        this.eng = eng;
    }

    public void info(){
        System.out.println("[no: "+no+", name : "+ name+ ", hp : "+ hp
        +", kor :"+ kor+ ", math : "+ math + ", eng : "+ eng);
    }
}
