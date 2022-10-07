public class For2 {
    public static void main(String[] args) {
        int sum=0;

//        for(int i=0; i<=100; i+=2){
//            sum = sum + i;
//        }// 첫 번째 방법 아이를 2씩 증가하여 짝수
        for(int i=0;i<=100;i++){
            if(i%2==0){
                sum+=i;
            }
        }// 두 번째 방법 if문 활용하여 2로 나눈 나머지 값 확인
        System.out.println(sum);
    }
}
