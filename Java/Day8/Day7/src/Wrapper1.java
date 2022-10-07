public class Wrapper1 {
    public static void main(String[] args) {
        int num1 = 100;
        // Integer num2 = new Integer(num1); ==
        Integer num2 = num1; // 생략가능한 표현이다. Boxing
        System.out.println(num1);
        System.out.println(num2);

        int num3 = num2.intValue();// UnBoxing
        // num2의 값을 가저와 num3에 입력한다.
        int num4 = num2;// UnBoxing, 생략가능한 표현
        System.out.println(num3);
        System.out.println(num4);

//      Integer num5 = new Integer(10);  ==
        Integer num5 = 100;
        System.out.println(num1 + num5); // 일반 데이터타입 + 래퍼클래스 (가능)
        Integer num6 = 100;
        System.out.println(num5 + num6);//  래퍼클래스 + 래퍼클래스 (가능)

        System.out.println(num1 == num5);// 일반 데이터타입, 래퍼클래스의 값 비교 (TRUE)
        // Integer는 주소비교가 아닌 값비교를 하여 TRUE가 나온다.
        System.out.println(num5 == num6);// 래퍼클래스, 래퍼클래스의 값 비교 (TRUE)
    }

}
