public class inform1 {
    public static void main(String[] args) {
        String str ="agile";//01234
        int x[] = {1,2,3,4,5}; // 0,1,2,3,4
        char y[] = new char[5]; //5칸 짜리 선언
        int i=0;

        while (i<str.length()){ // 5자리
            y[i] = str.charAt(i); // 문자열에서 지정된 위치의 문자를 반환
            i++;
        }
        System.out.println(i);
        for(int p:x){
            i--;
            System.out.print(y[i]);
            System.out.print(p+" ");
        }
        System.out.println(y[4]);
    }
}
