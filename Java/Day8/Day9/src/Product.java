public class Product {
//    과제 1.
//            "product.txt" 에서 데이터를 읽어 product 객체에 각각 데이터를 저장하고 ArrayList에 담아
//    출력하는 프로그램을 작성해보자.
//
//1. 파일을 읽어드림
//2. 스플릿하여 각 배열에 저장
//
//-> product class 따로 만들어야 함 name, price, name -> arrayList 에 5개를 저장해서 -> tostring해서 표현
//
//    product.txt 내용
//    갤럭시노트,1200000,삼성
//    아이버드,130000,삼성
//    그램노트북,1000000,엘지
//60인치TV,3000000,소니
//    맥북프로,1800000,애플
    private String productName;
    private int price;
    private String name;

    public Product() {
    }

    public Product(String productName, int price, String name) {
        this.productName = productName;
        this.price = price;
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product(" +
                "기종 : '" + productName  +
                ", 가격 : " + price +
                ", 브랜드 : " + name + ")"
                ;
    }
}
