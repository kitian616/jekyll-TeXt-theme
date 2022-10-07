public class DoWhile {
    public static void main(String[] args) {
        int i = 10;
        while(i<10){// false 값으로 실행하지 않음
            System.out.println("while");
            System.out.println(i);
            i++;
        }

        do{ //밑에 while의 조건이 아니여도 한번은 실행하고 false로 실행 종료
            System.out.println("do while");
            System.out.println(i);
            i++;
        }while(i<10);

    }
}
