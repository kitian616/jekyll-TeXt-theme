public class Exception5 {//throws 예제
    public static void main(String[] args) throws Exception {
        method1();// 끝까지 책임을 회피하면 JVM이 처리한다.
        //이러면 어떤 에러가 나는지 모른다.
    }
    public static void method1() throws Exception{
        method2();
    }
    public static void method2() throws Exception{
        method3();
    }
    public static void method3() throws Exception{
        System.out.println("method3() 호출!");
        Exception e = new Exception("예외발생");
        throw e;
    }
}
