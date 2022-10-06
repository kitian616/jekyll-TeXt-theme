public class Exception3 { // 캄파일시 발생하는 에러
    public static void main(String[] args) {
        try {
            Class obj = Class.forName("com.koreaot.test");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // 어떤 예외가 발생했는지 알려줌
        }
        System.out.println("프로그램이 정상적으로 종료되었습니다.");

    }
}