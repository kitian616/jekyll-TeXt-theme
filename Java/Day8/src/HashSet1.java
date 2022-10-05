import java.util.HashSet;
import java.util.Iterator;

public class HashSet1 {
    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();

        hashSet.add("김사과");
        hashSet.add("반하나");
        hashSet.add("오렌지");
        hashSet.add("이메론");
        hashSet.add("채애리");
        System.out.println(hashSet);
        hashSet.add("김사과");
        hashSet.add("이메론");
        hashSet.add("배애리");
        System.out.println(hashSet);// 중복은 저장되지 않는다.

        for(String s : hashSet){
            System.out.print(s + " ");
        }
        System.out.println();

        Iterator<String> iterator = hashSet.iterator();
        while(iterator.hasNext()){
            System.out.print(iterator.next()+ " ");
        }
        System.out.println();
    }
}
