import java.util.LinkedList;

public class LimkedList1 {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("ㅁㄴㅇ");
        linkedList.add("ㅎㅇㄹㄴ");
        linkedList.add("ㅈㄷㅅㄱ");
        linkedList.add("ㅋㅌㅊㅍ");
        linkedList.add("ㅊㅍㅇ");
        System.out.println(linkedList);
        System.out.println(linkedList.peek());//첫 데이터를 반환
        System.out.println(linkedList);
        System.out.println(linkedList.poll());
        System.out.println(linkedList);
        linkedList.remove("ㅋㅌㅊㅍ");// 원하는자리 삭제가능
        System.out.println(linkedList);
    }
}
