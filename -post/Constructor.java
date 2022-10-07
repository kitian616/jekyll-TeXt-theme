public class Constructor {
// 생성자 : 새로 생성하는 작업을 하는 역할을 하는 것처럼 보이지만 실제로는
// 인스턴스가 생성될 때 호출되는 인스턴스의 초기화 메서드 이다. 주로 인스턴스 변수의 초기화 작업에 주로 사용되며,
// 인스턴스 생성시 수행되어야할 작업을 위해서도 사용된다.
    private int x, y;

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void printXY() {
        System.out.println("X 좌표 : " + x + "\nY 좌표 : " + y);
    }

    public static void main(String[] ar) {
        Constructor constructor = new Constructor();

        constructor.printXY();

        constructor.setXY(10, 20);
        constructor.printXY();
    }
}
