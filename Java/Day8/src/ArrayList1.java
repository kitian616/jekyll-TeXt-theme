import java.util.ArrayList;
import java.util.Iterator;

public class ArrayList1 {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        arrayList.add(80);
        arrayList.add(95);
        arrayList.add(65);
        arrayList.add(100);
        arrayList.add(90);
        arrayList.add(55);

        System.out.println(arrayList);
        System.out.println(arrayList.size());

        for(int i=0;i<arrayList.size();i++){
            System.out.print(arrayList.get(i)+ " ");
        }
        System.out.println();

        arrayList.remove(2);
        System.out.println(arrayList);

        arrayList.set(4,85);
        System.out.println(arrayList);

        Iterator<Integer> iterator = arrayList.iterator();
        while(iterator.hasNext()){//내가 가리키고 있는 컬렉션에 데이터가 있는지 없는지 확인하는 것
            System.out.print(iterator.next()+" ");//next 현재 내가 있는 값을 찍어주고 다음으로 넘겨라

        }
    }
}
