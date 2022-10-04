public class Printer implements Movable {

    String tools = "프린터";

    @Override
    public void move(int x, int y) {
        System.out.println(tools + "로 x :" + x + ", y : " + y + "좌표 그림을 그립니다.");
    }

    public void test(){// 일반 메소드
        System.out.println(tools + "로 테스트 출력합니다.");
    }
}
