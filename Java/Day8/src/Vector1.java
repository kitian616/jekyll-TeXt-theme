import java.util.Vector;

public class Vector1 {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        System.out.println("벡터 데이터의 길이 : "+vector.size());
        System.out.println("벡터 데이터의 크기 : "+vector.capacity()); // 초기값은 10이다.

        vector.addElement("김사과"); //
        vector.addElement("오렌지");
        vector.addElement("반하나");
        vector.addElement("이메론");
        vector.addElement("배애리");
        vector.addElement("채리");
        vector.addElement("김사과");
        vector.addElement("오렌지");
        vector.addElement("반하나");

        System.out.println("벡터 데이터의 길이 : "+vector.size()); // 실제 데이터 수
        System.out.println("벡터 데이터의 크기 : "+vector.capacity()); // 얼마나 저장공간을 가질것인가

        vector.addElement("오렌지");
        vector.addElement("반하나");

        System.out.println("벡터 데이터의 길이 : "+vector.size());
        System.out.println("벡터 데이터의 크기 : "+vector.capacity());
        // 데이터의 크기다 초기 10이며 10을 넘으면 크기를 추가(이사)한다.
        // 그렇게 11일 때 10 -> 20으로 크기가 변경된다.
        System.out.println("벡터의 요소 : "+ vector.elementAt(0));

        vector.remove(2); // 벡터의 크기가 줄어들어도 이미 이사(변화) 되도 백터의 크기는 변경되지 않는다.
        System.out.println("벡터 데이터의 길이 : "+vector.size()); // 2번 자리 제거 및 데이터 길이 -1
        System.out.println("벡터 데이터의 크기 : "+vector.capacity()); // 크기 동일

        for(int i=0;i<vector.size();i++){
            System.out.print(vector.get(i) + " ");
        }
        System.out.println();

        vector.removeAllElements();//모주 지우기
        System.out.println("벡터 데이터의 길이 : "+vector.size());// 길이 : 0
        System.out.println("벡터 데이터의 크기 : "+vector.capacity());// 크기 : 20
    }
}
