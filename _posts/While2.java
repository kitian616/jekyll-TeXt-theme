public class While2 {
    public static void main(String[] args) {
        int i = 1;
        int sum = 0;

        while(i<=10){
//i는 11로 출력되지만 sum의 합은 중괄호 내에 i의 값이 10까지 계산하는 식이다.
            sum+=i;
            i++;
        }
        System.out.println(sum);// 중괄호 내의 반복문이 종료 후 나와 출력한다.
    }
}
