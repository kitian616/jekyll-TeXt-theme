public class StaticClass {
    public static void print(){
        System.out.println("Static 메소드!");
    }
    static class Static1{
        int num =0;
        public int add(){
            num++;
            return num;
        }
    }

    public static void main(String[] args) {
        print(); //Static 메소드! // static이 붙어있어 바로 사용 가능
        StaticClass.print(); //Static 메소드!

        StaticClass.Static1 static1 = new StaticClass.Static1();
        System.out.println(static1.add());// 1

    }
}
