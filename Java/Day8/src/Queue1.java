import java.util.LinkedList;
import java.util.Queue;

public class Queue1 {
    public static void main(String[] args) {
        //queue 인터페이스 따로 없어서 LinkedList<>() 사용
        Queue<Integer> queue = new LinkedList<>();// queue를 사용할 경우 LinkedList를 사용하여야 한다.
        queue.offer(10);//data추가 법
        queue.offer(50);
        queue.offer(80);
        queue.offer(20);
        queue.offer(70);
        System.out.println(queue); //선입선출(FIFO)
        System.out.println(queue.peek());// 첫 번째 데이터가 무엇인지 반환
        System.out.println(queue);
        System.out.println(queue.poll());// 첫 번째 데이터를 반환하고 삭제까지 실시 -> 10제거
        System.out.println(queue);
        queue.clear();// 큐에 있는것 전부 초기화
        System.out.println(queue);




    }
}
