public class ThisSuper1 {
    public static void main(String[] args) {
  //      - this 키워드 : 현재 클래스 안의 개체를 가져오는 참조 변수를 의미합니다.
        // 자식 호출
        Child child = new Child();


        // 자식에서 메서드 호출
        child.CrazyKim();
    }
}

// 부모 클래스 선언
class Mother {
    public String blog = "MoCrazyKim";
    public int Period = 6;

    public void CrazyKim() {
        System.out.println(blog + "의 블로그입니다. 블로그는 만들어진지 " + Period + "년이 됐습니다.");
    }
}

// 자식 클래스 선언
class Child extends Mother1 {
    String blog = "ChCrazyKim";
    int Period = 10;

    public void CrazyKim() {
        System.out.println(this.blog + "의 블로그입니다. 블로그는 만들어진지 " + this.Period + "년이 됐습니다.");
    }
}
