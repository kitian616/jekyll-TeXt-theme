public class Exception1 {
    public static void main(String[] args) {

        try {//실제 코드가 들어가는 곳으로 예외 상황이 발생할 가능성이 있는 코드
//            int num=10;
//            int result =num/0;
//            System.out.println(result);

//            String str = null;
//            System.out.println(str.length());

//            int [] num = new int[3];
//            num[0] =100;
//            num[3] =10;

            String str = "천";
            System.out.println(Integer.parseInt(str));
        } catch (ArithmeticException ae) {
            System.out.println("0으로 나눈 예외상황 발생");
        } catch (NullPointerException ne) {
            System.out.println("객체를 정의하지 않음");
        } catch (ArrayIndexOutOfBoundsException ae) {
            System.out.println("인덱스를 벗어남!");
        } catch (NumberFormatException nfe) {
            System.out.println("숫자로 변환하지 못한다");
        } catch (Exception e) {
            // 여러 예외상황이 있으면 가장 마지막에 작성해야한다.
            //모든 예외를 처리하는데 다른 예외가 하위에 있으면 오류가 발생하기 때문이다.
            System.out.println("모든 예외를 처리!");
        }

        System.out.println("프로그램을 정상적으로 종료합니다.");

    }
}
