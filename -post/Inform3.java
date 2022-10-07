abstract class Animal {
    String a = " is animal";
    abstract void look();
    void show() {
        System.out.println("zoo");
    }
}
class Chicken extends Animal{// 상속받은 부분부터 출력된다.
    Chicken(){
        look();
        display();
    }
    void look(){
        System.out.println("Chicken"+a);
    }
    void display() {
        System.out.println("two wings");
    }
}
public class Inform3 {
    public static void main(String[] args) {
        Animal a = new Chicken();// a참조변수에 Chicken 자식클래스 부분의 주소가 들어가 자식부터 실시
        a.show();
    }
}
