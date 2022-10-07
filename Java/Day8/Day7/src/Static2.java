public class Static2 {
    public static void main(String[] args) {
        Block block1 = new Block(); // 처음 객체를 만들었을 때 static 블록 한번만 나오고 그다음
        block1.print();
        block1.print();

        Block block2 = new Block(); // 객체를 만들어도 static블록은 출력되지 않는다.
        block2.print();

        Block block3 = new Block();
        block3.print();
    }
}
