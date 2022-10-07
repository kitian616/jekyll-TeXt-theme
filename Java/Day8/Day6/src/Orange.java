public class Orange extends Fruit {
    private int dangdo;

    public int getDangdo() {
        return dangdo;
    }

    public void setDangdo(int dangdo) {
        this.dangdo = dangdo;
    }

    public Orange(String name, int price, String color
            , String from, int dangdo) {
        super(name, price, color, from);
        this.dangdo = dangdo;
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "당도 : " + dangdo + "}";


    }
}

