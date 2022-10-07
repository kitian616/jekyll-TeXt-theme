public class Abstract1 {//  추상 클래스를 자식이 상속받으면 부모의 메소드도 쓸 수 있다

    public static void main(String[] args) {
       // Parent parent = new Parent() 추상클래스로 객체를 생성할 수 없다.

        // 추상 클래스를 사용하기 위해 자식에게 상속을 시키도 자식명으로 객체를 만들어 부모 메소드를 사용할 수 있다.

        Child child = new Child();
       child.print();
       int result = child.sum(10,5);
        System.out.println(result);

    }
}
