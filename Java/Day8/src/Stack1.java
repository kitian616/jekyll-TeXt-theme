import java.util.Stack;

public class Stack1 {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(40);// data 입력
        stack.push(100);
        stack.push(80);
        stack.push(70);
        stack.push(60);
        stack.push(20);
        System.out.println(stack);
        System.out.println(stack.peek());// 마지막에 들어온 데이터를 리턴만 함
        System.out.println(stack);
        stack.pop();// 보여주기 및 지우기
        System.out.println(stack);

    }
}
