public class Child extends Parent {
    //추상 메소드가 있으면 상속하기 위해 추상메소드를 오버라이드해야 한다.

    @Override
    public int sum(int num1, int num2) {
        return num1 + num2;
    }
}
