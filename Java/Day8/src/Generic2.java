public class Generic2 {
    public static void main(String[] args) {
        GenericTest<String,Integer> generic2 = new GenericTest<>();
        generic2.set("1",100);

        System.out.println("first : "+generic2.getFirst());
        System.out.println("second : "+generic2.getSecond());

        System.out.println("K Type : "+ generic2.getFirst().getClass().getName());
        System.out.println("V Type : "+ generic2.getSecond().getClass().getName());
    }
}
