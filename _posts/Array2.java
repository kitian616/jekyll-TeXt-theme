import java.util.Scanner;

public class Array2 {
    public static void main(String[] args) {
        System.out.println("몇개의 값을 저장할까요?");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        int [] arr = new int[num];

        for(int i =0; i<arr.length; i++){ // arr.length 은 arr에 들어가있는 총 개수
            System.out.println((i+1)+"번째 저장할 숫자를 입력하세요");
           arr[i] = sc.nextInt();
        }
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+ " ");// arr만 기입하면 기계어가 나온다.
        }
    }
}
