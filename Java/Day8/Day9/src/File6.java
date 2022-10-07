import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class File6 {
    public static void main(String[] args) {
        String file = "input1.txt";

        try {//            FileInputStream fis = new FileInputStream(file);
//            Scanner sc = new Scanner(fis);
            Scanner sc = new Scanner(new FileInputStream(file));

            while (sc.hasNextLine()) { //한 줄을 읽고 있으면 와일문으로
                String str = sc.nextLine();
                System.out.println(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
