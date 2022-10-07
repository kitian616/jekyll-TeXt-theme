class Parent{ }
class Chield extends Parent{ }

public class Instanceof1 {
    public static void main(String[] args) {
        Parent parent = new Parent();
        Chield chield = new Chield();

        System.out.println(parent instanceof Parent);
        System.out.println(chield instanceof Parent);
        System.out.println(chield instanceof Chield);

        //false 부모객체는 자식을 모르기 때문에
        System.out.println(parent instanceof Chield);

    }
}
