public abstract class Parent { // 추상 클래스를 생성할려면 추상 메소드를 생성해야한다..
    private  int num;

    public void print(){
        System.out.println("print() 호출!");
    }
    public abstract int sum(int num1,int num2);// 추상 메소드
}
