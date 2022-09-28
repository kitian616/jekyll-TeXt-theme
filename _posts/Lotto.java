import java.util.Scanner;

public class Lotto {
    /*
    과제3.
    로또번호 추출 프로그램을 작성해보자.
    1. 1 ~ 45까지 임의의 숫자 6개 추출
    2. 번호는 중복되지 않아야 함
    3. 출력시 오름차순 정렬
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] ar = new int[6];
        System.out.println("🎈로또번호 추출 프로그램🎈");

        for (int i = 0; i < ar.length; i++) {
            int rn = (int) (Math.random() * 45) + 1;
            System.out.print((i + 1) + "자리의 번호 입력 : ");
                ar[i] = sc.nextInt();
            for (int j = 0; j < i; j++) {
                if (ar[i] == ar[j]) {
                    i--;
                }
            }
        }
        int temp =0;
        for(int i=0; i<ar.length-1; i++) {
            for (int j = i+1; j < ar.length; j++) {
                if (ar[i] > ar[j]) {
                    temp = ar[i];
                    ar[i] = ar[j];
                    ar[j] = temp;
                }
            }
        }
        System.out.println();
        for (int i = 0; i < ar.length; i++) {
            System.out.print(ar[i] + " ");

        }
        System.out.print("당첨 로또번호 : ");
        for(int i=0;i<ar.length;i++){
            System.out.print(((int) (Math.random() * 45) + 1)+" ");
        }

    }
}





