
public class TypeConv {
    public static void main(String[] args) {
        byte var1 =10;
        int var2 = var1; // byte -> int 작은 곳에서 큰곳으로 이동 시 문제없음(자동형변환)
        System.out.println(var2);

        int var3 = 1000;
        byte var4 = (byte)var3;//int->byte, -128~127 (강제형변환)
        // -> 타입의 크기를 넘어서는 값을 대입시 이상한 값이 출력된다.
        System.out.println(var4);

        char var5 = 'A'; //2byte 유니코드
        int var6 = var5;
        System.out.println(var6);//자동형변환

        int var7 =10;
        double var8 = 3.0;
        int var9 = 3;
        System.out.println(var7/var9);// 정수 / 정수 = 정수
        System.out.println(var7/var8);// 정수 / 실수 = 실수
    }
}
