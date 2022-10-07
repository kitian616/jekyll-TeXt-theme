import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class File7 {
    public static void main(String[] args) {
        String file = "output3.txt";
        String[] arr = {"김사과", "오렌지", "반하나", "이메론"};

        FileOutputStream fos = null;
        try {
//            fos = new FileOutputStream(file);
//            PrintWriter pw = new PrintWriter(fos);
            PrintWriter pw = new PrintWriter((new FileOutputStream(file)));
            for(int i=0;i<arr.length;i++){
                System.out.print(arr[i]+" ");
                //pw.println(arr[i]);
                pw.print(arr[i]+" ");
            }
            pw.close();//닫지 않으면 저장이 되지않음
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
