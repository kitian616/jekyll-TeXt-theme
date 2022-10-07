public class ThisSuper {
//   - super 키워드 : 부모 클래스로부터 상속받은 필드나 메소드를 자식 클래스에 참조하는데 사용하는 참조변수
//    인스턴스 변수의 이름과 지역 변수의 이름이 같은 경우 인스턴스 변수 앞에 this 키워드를 사용하여 구분가능
//    부모 클래스의 멤버와 자식 클래스의 멤버 이름이 같을 경우 super 키워드를 사용하여 구별 가능하다. 즉 super
//    참조변수를 사용하여 부모 클래스의 멤버에 접근 가능하다.


    public static void main(String[] args) {
        // 자식 호출
        Child1 child = new Child1();

        // 자식에서 메서드 호출
        child.CrazyKim();
    }
}

// 부모 클래스 선언 
class Mother1 {
    public String blog = "MoCrazyKim";
    public int Period = 6;

    public void CrazyKim() {
        System.out.println(blog + "의 블로그입니다. 블로그는 만들어진지 " + Period + "년이 됐습니다.");
    }
}

// 자식 클래스 선언
class Child1 extends Mother1 {
    String blog = "ChCrazyKim";
    int Period = 10;

    public void CrazyKim() {
        super.CrazyKim();
        System.out.println(blog + "의 블로그입니다. 블로그는 만들어진지 " + Period + "년이 됐습니다.");
        System.out.println(super.blog + "의 블로그입니다. 블로그는 만들어진지 " + super.Period + "년이 됐습니다.");
    }
}
