public class While1 {
    public static void main(String[] args) {
        int i = 1;

        while(i<=5){ // i=1,2,3,4,5,6 까지 올라왔다가 5초가로 while문 빠저나온다.
            System.out.println("안녕하세요! 5이하 입니다.");
            i++;//없으면 무한루프 발생
        }
        System.out.println(i);
        //i가 최종 6으로 올라가고 while문을 나오기 때문에 6이다.
    }
}
