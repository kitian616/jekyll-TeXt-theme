public class Apple extends Fruit {
    private String size;

    public String getSize() {
        return size;
    }

    public Apple() {

    }

    public Apple(String name, int price, String color, String from, String size) {
        super(name, price, color, from);
        this.size = size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override //어노테이션 : 컴파일러에게 덮어쓴 부분이라고 참고하라는 뜻
     public String toString() {
        return super.toString()+"{" +//super. 메소드 부모거를 가저오는 기능
                //변수 즉 필즈 값까지는 저장하는것이 아니다.
                "크기='" + size +
                "}";
    }
}
