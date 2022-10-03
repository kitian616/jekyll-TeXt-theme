import java.util.Scanner;

public class If5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("혈액형을 입력하세요 : ");
        String blood = sc.next();//그냥 next는 문자열을 입력한다.
//        System.out.println(blood);
        System.out.println("RH+, RH- 중 입력하세요");
        String rh = sc.next();
//        System.out.println(rh);

        if(blood.equals("A")){
            if(rh.equals("RH+")){
                System.out.println("RH+ A형입니다.");
            }
            else {
                System.out.println("RH- A형입니다.");
            }
        }
        else if(blood.equals("B")){
            if(rh.equals("RH+")){
                System.out.println("RH+ B형입니다.");
            }
            else{
                System.out.println("RH- B형입니다.");
            }
        }
        else if(blood.equals("O")){
            if(rh.equals("RH+")){
                System.out.println("RH+ O형입니다.");
            }
            else {
                System.out.println("RH- O형입니다.");
            }
        }
        else if(blood.equals("AB")){
            if(rh.equals("RH+")){
                System.out.println("RH+ AB형입니다.");
            }
        }
    }
}

