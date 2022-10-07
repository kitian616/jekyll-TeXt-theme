public class Object1 {
    public static void main(String[] args) {
        Box1 box1 = new Box1();// 객체생성
        box1.setObj("안녕하세요");
        System.out.println(box1.getObj());
        //String 형변환 3가지!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //String str  = (String)box1.getObj(); // (O) , null이 있을 경우 에러
        //String str  = box1.getObj().toString(); (O) , Integer 형변환 일 때 에러
        String str = String.valueOf(box1.getObj()); //(O) , 추천!! String만 사용가능


        box1.setObj(100);
        System.out.println(box1.getObj());
        Integer i = Integer.parseInt(box1.getObj().toString());

        Apple apple = new Apple("사과",1000,"red,","충주","소과");
        box1.setObj(apple);
        System.out.println(box1.getObj());

        Apple apple2 = (Apple)box1.getObj();// 다운캐스팅해서 다시 받기
        System.out.println(apple2);
    }
}
