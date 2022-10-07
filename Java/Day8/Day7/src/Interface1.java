public class Interface1 {
    public static void main(String[] args) {
        Drawable.staticMethod(); // 스태틱은 메모리에 먼저 올라가기 떄문에 인터페이스명으로 호출
        //  Drawable drawable = new Drawable()  인터페이스는 객체를 만들지 못함
        Circle circle = new Circle();
        circle.drawPrint("원의 넓이를 구해라");
        circle.setColor("노랑");
        circle.printInfo();

        Rectangle rectangle = new Rectangle();
        rectangle.drawPrint("4 * 7");
        rectangle.setColor("파랑");
        System.out.println(rectangle.calcRectangle(4,7));

        Printer printer = new Printer();
        printer.move(10,5);
        printer.test();

        Shape shape = new Shape();
        shape.drawPrint("shape 메세지");
        shape.setColor("핑크");
        shape.move(2,6);


        //------------------------------------------------------------------
        // Drawable에 있는 메소드만 사용이 가능하다.

        Drawable drawable = new Circle();// 인터페이스를 구현한 것을 사용한다.
        drawable.drawPrint("drawable");
        drawable.setColor("검정");

        // 구현부에서 만든 메소드는 사용이 불가능하다. 업캐스팅과 유사하다.

        Drawable shape2 = new Shape();
        shape2.drawPrint("drawable");
        shape2.setColor("녹색");
       // shape2.move()   Movable 형태로 만들어젔기 때문에 사용이 불가능하다.

        Movable shape3 = new Shape();
        shape3.move(2,6); // 구현부 클래스의 오버라이드
        shape3.printMove(); // 해당 인터페이스의 메서드


    }
}
















