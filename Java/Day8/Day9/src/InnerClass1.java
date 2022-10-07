class OuterClass{
    private int num1 = 10;

    public void method1(){
        System.out.println("num1:"+num1);
    }
    public void printInner(){//외부에서 객체를 생성해서 내부것으 사용할 수 있다.
    InnerClass innerClass = new InnerClass();
    innerClass.method2();
//    innerClass.num2 = 10  // 외부 클래스에서 내부 클래스의 private 접근이 가능
    }
    public class InnerClass{// 내부에 있는 클래스로 클래스명 동일해도 상관없다.
        private int num2 =20;

        public void method2(){
            System.out.println("OuterClass.num1 : "+ num1);
            System.out.println("InnerClass.num1 : "+ num2);
        }
    }


}

public class InnerClass1 {
    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        outerClass.method1(); //num1:10
        outerClass.printInner(); //OuterClass.num1 : 10 InnerClass.num1 : 20
        System.out.println();

        OuterClass.InnerClass innerClass = outerClass.new InnerClass();
        // 외부클래스를 통해서 내부클래스로 들어가면 내부클래스도 객체를 생성해서 사용이 가능하다.
        innerClass.method2();//OuterClass.num1 : 10 InnerClass.num1 : 20

    }
}
