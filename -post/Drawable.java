public interface Drawable {
    int MAX = 100; // public static final int MAX = 100; 읽기 기능만 있는 변수

    // 추상 메소드
    public void drawPrint(String msg); // public abstract void drawPrint(String msg);

    public void setColor(String color);

    // 인스턴스 메소드 -> 출력문이 있으면 default
    public default void printInfo() {
        System.out.println("Drawable 인터페이스의 인스턴스 메소드 printInfo()!");
    }

    // static 메소드
    public static void staticMethod() {
        System.out.println("Drawable  인터페이스의 static메소드 staticMethod");
    }

}
