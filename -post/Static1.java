public class Static1 { // 변수로만 이용하여 문제풀기
    public static void main(String[] args) {
        Button cutomer1 = new Button();
        cutomer1.press();
        cutomer1.press();
        cutomer1.press();
        cutomer1.press(); // 4
// 각 customer가 다른 메모리를 호출하고 있어서 메소드를 따로보고 있다
        // 같은 메소드를 사용하고 싶을 때는 Static사용
        Button customer2 = new Button();
        customer2.press(); // 1 -> static 사용 후 5로 카운트

        // Button. 사용하기 위해서는 클래스나 메소드에 Static을 사용하여 공용으로 사용한다.
        Button.press();
        System.out.println(Math.PI);
    }
}
