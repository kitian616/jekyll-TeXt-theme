public class Continue1 {
    public static void main(String[] args) {
        for(int i=1; i<=100;i++){//continue문이면 for문으로
            if(i%3==0){
                System.out.print("👏");
                continue;

            }
            System.out.print(i+" ");
        }
    }
}
