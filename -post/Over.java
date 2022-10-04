

    public class Over {
        //오버로딩 : 자바의 한 클래스 내에 이미 사용하려는 이름과 같은 이름을 가진 메소드가 있더라도
        // 매개변수의 개수 또는 타입이 다르면, 같은 이름을 사용해서 메소드를 정의할 수 있다
        public static void main(String[] args) {
            Over1 om = new Over1();

            om.print();
            System.out.println(om.print(3));
            om.print("Hello!");
            System.out.println(om.print(4, 5));
        }
    }

    class Over1 {
        public void print() {
            System.out.println("오버로딩1");
        }

        String print(Integer a) {
            System.out.println("오버로딩2");
            return a.toString();
        }

        void print(String a) {
            System.out.println("오버로딩3");
            System.out.println(a);
        }

        String print(Integer a, Integer b) {
            System.out.println("오버로딩4");
            return a.toString() + b.toString();
        }

    }
