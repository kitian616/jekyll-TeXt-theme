import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class File2 {
    public static void main(String[] args) {
        byte[] arr = new byte[20]; // 20byte

        try {
            FileInputStream fis = new FileInputStream("input3.txt");
            System.out.println((char)fis.read()); // H ()에 아무것도 없으면 선언한 1byte만 가저온다.
            fis.read(arr,0,6);// arr1에 어디서 어디까지 가저올거냐? e l l o ' ' 6개를 가저오고 나머지 14는 빈공간
            for(byte b:arr){ // 20개를 선언한다.
                System.out.print((char)b+ " ");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
