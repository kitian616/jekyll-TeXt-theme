import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Phone {
    public static void main(String[] args) {
//        String str1 = "갤럭시노트,1200000,삼성";
//        String str2 = "아이버드,130000,삼성";
//        String str3 = "그램노트북,1000000,엘지";
//        String str4 = "60인치TV,3000000,소니";
//        String str5 = "맥북프로,1800000,애플";
        ArrayList<Product> pr = new ArrayList<>();
        String productName = "";
        int price = 0;
        String name = "";

        String file = "product.txt";

//        Product p = new Product(productName, price, name);
//        for (int i = 0; i < pr.size(); i++) {
//            System.out.println(pr.get(i));
//        }
//        String[] pr = sc.nextLine().split(",");
        try {
            String[] arr = file.split(",");

            Scanner sc = new Scanner(new FileInputStream(file));
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                System.out.println(str);
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}


