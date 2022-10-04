public class Shape implements Drawable,Movable{
    @Override
    public void drawPrint(String msg) {
        System.out.println("메시지"+msg);
        System.out.println("Drawable의 drawPrint() 호출");
    }

    @Override
    public void setColor(String color) {
        System.out.println("색상"+color);
        System.out.println("Drawable의 setcolor() 호출");
    }

    @Override
    public void move(int x, int y) {
        System.out.println("x :"+x+", y : "+y);
        System.out.println("Movable의 Move() 호출");
    }
}
