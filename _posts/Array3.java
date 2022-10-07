import java.util.Scanner;

public class Array3 {
    public static void main(String[] args) {
        System.out.println("몇개의 값을 저장할까요?");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int [] arr = new int[num];

        for(int i =0; i<arr.length; i++){ // arr.length 은 arr에 들어가있는 총 개수
            System.out.println((i+1)+"번째 저장할 숫자를 입력하세요");
            arr[i] = sc.nextInt();
        }
        int max = arr[0];
        int min = arr[0];

        for(int i =1; i<arr.length;i++){//1부터 시작하는 이유는 최대소를 이미 선언하였기 때문
            if(max <arr[i]){
                max = arr[i];
            }else if(min > arr[i]){
                min = arr[i];
            }
        }
        System.out.println(max +" "+ min);

    }
}
