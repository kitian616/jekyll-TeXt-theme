interface Inter1{ // 익명 클래스는 매우 중요하다.
    // 이벤트를 만드는 프로그램이나 한번 사용하고 다음부터 필요없는 기능을 구현하기에 도움이 된다.
    void method1();
}

class Class1 implements Inter1{
    @Override
    public void method1() { // 오버라이드를 해야 인터페이스 사용간 오류X
        System.out.println("Inter1을 구현한 클래스");
    }
}

interface  Inter2 {
    String method2();
}

class Class2 implements Inter2{// 클래스3에 메개변수 인터2가 있어 구현한 클래스2가 사용
    @Override
    public String method2() {
        return "Inter2를 구현한 클래스(실명 클래스)";
    }
}

class Class3 {
    public void method3(Inter2 inter2){ // 인터페이스를 구현한 인터2의 객체타입은 클래스2이다.
        System.out.println(inter2.method2());
    }
}
public class Anonymous {
    public static void main(String[] args) {
        Class2 class2 = new Class2();
        Class3 class3 = new Class3();
        class3.method3(class2);

        class3.method3(new Class2(){ // 참조변수 없이 인터페이스2구현
        // 참조변수가 없기 떄문에 한번 사용하고 사라진다. 힙에는 저장이 되지않고 널로 있다.
            @Override
            public String method2() {
                return "익명 클래스";
            }
        });
    }
}

//Inter2를 구현한 클래스(실명 클래스)
//익명 클래스