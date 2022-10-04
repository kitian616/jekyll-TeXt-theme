public interface Movable {

    public void move(int x, int y);

    public default void printMove() {
        System.out.println("Movable 이터페이스의 printMove()");
    }

}
