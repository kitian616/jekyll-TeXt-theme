public class Banana extends Fruit {
    private String brand;//내가 가지고 있는 필드로 내 클래스에서 사용
    // 그렇기 때문에 this.brand 사용하여 오버라이딩하여 사용한다.

    public Banana(String name, int price, String color, String from, String brand) {
        super(name, price, color, from);//부모쪽으로
        this.brand = brand;//오버로딩 사용할 경우 this.
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return super.toString() +"{"+
                "브랜드 : " + brand +"}" ;
    }
}
