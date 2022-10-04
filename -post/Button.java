public class Button { // static은 선언하면 바로 메모리에 올라간다.
    //private int count = 0;
    // final static int count =0; // 클래스 변수
    private static int count = 0; // static 사용으로 모든 객체가  count를 공용으로 사용, 공용변수

    public static void press(){// 객체를 만들었을 때 올라간다. Button button  = new Button();
        count++;
        System.out.println("고객닝의 대기번호 : " + count);
    }
}
