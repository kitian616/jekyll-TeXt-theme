public class GUGU {
    public static void main(String[] args) {
//        for(int i=2; i<=9;i++ ){
//            System.out.println(i+"단");
//            for(int j=1; j<=9; j++){
//                System.out.println(i+"*"+j+"="+i*j);
//            }
//        }
        int i = 2, j = 1;// 변수 선언 및 초기화 실시
        while (i <= 9) {
            System.out.println(i+"단");
            while (j <= 9) {
                System.out.println(i + "*" + j + "=" + i * j);
                j++;
            }
            j=1; // 초기화한다. 왜냐하면 j while문에서 10이 되고 내려가면 false로
            // 안들어가기 때문에 다시 초기화를 해야 반복이 된다.
            i++;// j가 끝나고 i가 증가해야 하기 때문
        }
    }
}

