public class Block {
    static {//객체를 만들때 한번만 실행되고 실행하지 않는다.
        System.out.println("✔ static 블록 ✔");
        System.out.println("이 블록은 객체를 생성할 때 단 한번만 실행합니다.");
    }// 객체마다 한번의 작업을 수행을 원하는 객체를 따로따로 만들어서 수행시키는 기능
    public void print(){
        System.out.println("😍😒😘💕😁👍🙌🤦‍♀️🎶😢");
    }
}
