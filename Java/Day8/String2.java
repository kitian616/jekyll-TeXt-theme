public class String2 {
    public static void main(String[] args) {
        String str1 = new String("Java");
        String str2 = new String("Programming");
        System.out.println(str1);
        System.out.println(str2);

        // concat(): 문자열과 문자열을 연결
        System.out.println(str1.concat(str2)); // 1
        String temp = str1.concat(str2);    // 2
        System.out.println(temp);
        System.out.println(str1 + str2);    // 3

        // indexOf(): 원하는 문자열을 찾아 해당하는 문자열의 index를 반환
        System.out.println(str2.indexOf("P")); // Programming, 0
        System.out.println(str2.indexOf("p"));  // -1
        System.out.println(str2.indexOf("gra"));    // 3
        System.out.println(str2.indexOf("r", 3));   // 4

        // trim(): 문자열의 앞뒤 공백을 제거
        String str3 = new String("     자바     ");
        System.out.println("✔" + str3 + "✔");
        System.out.println("✔" + str3.trim() + "✔");

        // toLowerCase(): 소문자로 변환, toUpperCase(): 대문자료 변환
        System.out.println(str2.toLowerCase());
        System.out.println(str2.toUpperCase());

        // length(): 문자열의 길이를 반환
        System.out.println(str2.length());  // 11

        // substring(): 원하는 문자를 추출
        // Programming
        System.out.println(str2.substring(3));  // 문자열 index 3부터 끝까지 추출
        temp = str2.substring(3);
        System.out.println(temp);
        System.out.println(str2.substring(3, 7));   // 문자열 index 3부터 7직전까지 추출

        // 문자열을 숫자로 변환
        String str4 = "100";
        int i = 200;
        System.out.println(str4 + i);   // 100200
        System.out.println(Integer.parseInt(str4) + i);

        //특정 값을 기준으로 문자열을 자으로 배열에 저장
        String str5 = "김사과,반하나,오렌지,이메론";
        String[] arr = str5.split(",");//split "," 컴마를 기준으로 자르겠다는 메소드 컴마는 없어짐
        for(String s : arr){
            System.out.print(s+" ");
        }
    }
}