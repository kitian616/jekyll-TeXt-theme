import java.io.File;
import java.io.IOException;

public class File1 {
    public static void main(String[] args) {
        File file1 = new File("input1.txt");// 상대경로
        System.out.println(file1.exists());// true


        System.out.println(file1.isDirectory()); // false
        System.out.println(file1.length()); // 85 바이트

        File dir1 = new File( "C:\\Java\\Day9\\Test1");// 절대경로
        dir1.mkdir();// 파일을 만듬

        File file2 = new File(dir1,"input2.txt");// 이 파일에 객체를 만든다.
        try {
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(file2.getPath()); // 상대경로
        System.out.println(file2.getAbsoluteFile()); // 절대경로 C:\Java\Day9\Test1\input2.txt

    }
}
