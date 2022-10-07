
public class Fruit {
    private String name;
    private int price;
    private String color;
    private String from;

    public Fruit() {
    }

    public Fruit(String name, int price, String color, String from) {
        this.name = name;// private 이며 자식에서 super을 사용하기 위해서는 부모 클래스에서
        // 생성자에 오버로딩할 수 있는 상황이 있어야 한다.
        this.price = price;
        this.color = color;
        this.from = from;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void info() {// 부모클래스 출력방법
        System.out.println("[과일명 : " + name + ", 가격 : " + price +
                ", 색상 : " + color + ", 원산지 : " + from + "]");
    }

    //오버라이딩 : 부모 값을 덮어쓰기 (맘에 안들어서) -> 동일한 메소드를
    public String toString() {//toString은 fruit의 부모값을 자식에서 변경
        return "Fruit{" +
                "과일 명 : " + name +
                ", 가격 : " + price +
                ", 색상 : " + color +
                ", 원산지 : " + from +
                "}";
        //       return "🍎";//메모리 주소를 사과 이모티콘으로 변경
    }//java.lang.object에서 물려받음
}
