public class Exception4 {//throw 예제
    public static void main(String[] args) {
        Exception e = new Exception("예외를 발생합니다!!!");

        try{
            throw e; // 강제로 예외를 발생시킴
        }catch (Exception ex){
            ex.printStackTrace();// 어떤 예외가 발생했는지
        }
        System.out.println("프로그램이 정상적으로 종료");
    }
}
