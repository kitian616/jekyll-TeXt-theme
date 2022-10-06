import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class File4 {
    public static void main(String[] args) {
        char[] arr = new char[50];
        try{
            FileReader fr = new FileReader("input1.txt");
            System.out.println((char) fr.read());
            fr.read(arr);
            for(char c :arr){
                System.out.print(c);
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
