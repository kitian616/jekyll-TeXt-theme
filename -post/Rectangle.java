public class Rectangle implements Drawable {
    @Override
    public void drawPrint(String msg) {
        System.out.println(msg + "형태로 직사각형을 그립니다.");
    }

    @Override
    public void setColor(String color) {
        System.out.println("직사각형의 선을" + color + "색상으로 그립니다.");
    }

    public int calcRectangle(int x, int y) {
        return x * y;
    }
}
