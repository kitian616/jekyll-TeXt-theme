public class Student1 {


    private int no;
    private String name;
    private int kor;
    private int math;
    private int eng;

    private int sum;
    private double avg;

    public Student1() {
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
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

    @Override
    public String toString() {
        return "학생[" +
                "번호 :" + no +
                ", 이름 :" + name +
                ", 국어점수 :" + kor +
                ", 수학점수 :" + math +
                ", 영어점수 :" + eng +
                ", 총점 :" + (kor + math + eng) +
                ", 평균 :" + (kor + math + eng / 3.0) +
                "]";
    }
}
