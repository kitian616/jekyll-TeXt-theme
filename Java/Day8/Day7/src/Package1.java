import com.koreait.test1.ClassA;// 임포트 어디출신인지 확인
import com.koreait.test2.ClassB;// 패키지 것을 가저와 사용한다.

public class Package1 {
    public static void main(String[] args) {
        ClassA classa = new ClassA();
        classa.print();

        ClassB classb = new ClassB();
        classb.info();
    }
}
