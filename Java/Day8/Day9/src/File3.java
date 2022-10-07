import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class File3 {//출력

    public static void main(String[] args) {
        String input = "input3.txt";
        String output = "output1.txt";

        try {
            FileInputStream fis = new FileInputStream(input);
            FileOutputStream fos = new FileOutputStream(output);

            int b;
            while ((b = fis.read()) != -1) { // -1은 더 이상 읽을게 없다면
                fos.write(b);
                System.out.println((char) b + " ");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }}