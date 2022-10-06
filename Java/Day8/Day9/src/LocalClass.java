public class LocalClass {
    private int num1 = 10;

    public void method1() {
        int num2 = 20;

        class Local {
            private int num3 = 30;
            public final int num4 = 40;

            public void method2() {
                System.out.println("num1 : " + num1);
                System.out.println("num2 : " + num2);
                System.out.println("num3 : " + num3);
                System.out.println("num4 : " + num4);
            }
        }
        Local local = new Local();
        local.method2();
    }

    public static void main(String[] args) {
        LocalClass localClass = new LocalClass();
        // 전체 메소드를 실행하면 method1이 실행되고 이 후 method2를 사용하여 출력이 된다.
        localClass.method1();


    }
}
