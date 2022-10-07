public class Generic1 {
    public static void main(String[] args) {
       // Box2<String> box1 = new Box2<String>();
       // Box2<String> box1 = new Box2<>();
          Box2<String> box1 = new Box2();
          box1.setT("안녕하세요");
        System.out.println(box1.getT());
        String str = box1.getT();// 형변환이 필요없다. -> 제네릭의 장점 : 자동변환

        Box2<Integer> box2= new Box2<>();
        box2.setT(100);
        System.out.println(box2.getT());
        Integer i = box2.getT();

        Apple apple = new Apple("사과",1000,"red","충주","소과");
        Box2<Apple> box3 = new Box2<>();
        box3.setT(apple);

        Apple apple2 = box3.getT();
        System.out.println(apple2);


    }
}
