import java.io.FileWriter;
import java.io.IOException;

public class File5 {
    public static void main(String[] args) {
        String str = "내일만 공부하면 4일쉰다.";

        try{
            FileWriter fw = new FileWriter("output2.txt");
            fw.write(str.charAt(0)); // 내
            fw.write("\n"); // 다음 줄
            fw.write(str); // 전체 출력
            fw.write("\n");
            fw.write(str.charAt(0));
            fw.write("\t"); // tab
            fw.write("일만 공부하면 4일 쉰다.");
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
