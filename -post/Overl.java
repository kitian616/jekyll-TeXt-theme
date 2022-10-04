
    //오버라이딩 :부모 클래스로부터 상속받은 메소드를 자식 클래스에서 재정의하는 것을 오버라이딩이라고 한다.
    // 상속받은 메소드를 그대로 사용할 수도 있지만, 자식 클래스에서 상황에 맞게 변경해야하는 경우 오버라이딩할 필요가 생긴다.

public class Overl {

    public static void main(String[] args) {
        Person person = new Person();
        Child child = new Child();
        Senior senior = new Senior();

        person.cry();
        child.cry();
        senior.cry();
    }
}
class Person {
    void cry() {
        System.out.println("흑흑");
    }
}

class Child extends Person {
    @Override
    protected void cry() {
        System.out.println("잉잉");
    }
}
class Senior extends Person {
    @Override
    public void cry() {
        System.out.println("훌쩍훌쩍");
    }
}