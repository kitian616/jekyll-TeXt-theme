import java.awt.image.BaseMultiResolutionImage;

public class Oop3 {
    public static void main(String[] args) {
        Fruit fruit1 = new Fruit();//일반적인 생성자 및 힙에 있는 것 확인
        fruit1.info();
        fruit1.setName("사과");
        fruit1.setPrice(1000);
        fruit1.setColor("red");
        fruit1.setFrom("청송");
        //fruit.setSize("소과) 자식에서 추가된 필드를 수정할 수 없음
        fruit1.info();
        System.out.println(fruit1);//toString 생략 -> 주소 값

        Apple apple = new Apple();
// Fruit을 상속받아 따로 .geter/seter 메소드, 생성자, 변수(필드값)을 선언하지 않아도 사용 가능
        apple.info();
        apple.setName("사과");
        apple.setPrice(10000);
        apple.setColor("파랑");
        apple.setFrom("대전");
        apple.setSize("소과"); // 자식에 추가된 필드를 수정할 수 있음

        apple.info();
        System.out.println(apple);// 부모걸 오버라이딩 하면 자기가 설정한 기능으로 변경된다.

        Banana banana = new Banana("바나나", 1500, "yello", "필리핀", "델몬트");// 오버라이딩 추가 연습
        System.out.println(banana);// 값을 넣지 않아 초기값 출력
    }
}