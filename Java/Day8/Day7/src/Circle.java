public class Circle implements Drawable {// 인터페이스 추상메서드 오버라이드\
    double radius =10;
    @Override
    public void drawPrint(String msg) {
        System.out.println("메세지 : " + msg);
    }
    @Override
    public void setColor(String color) {
        System.out.println(color + "색상으로 설정함");
    }

    public double calcCircle(double radius){

        return radius * radius * Math.PI;
    }
}
