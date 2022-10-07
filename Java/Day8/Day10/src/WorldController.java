import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class WorldController {
    private ArrayList<World> list;

    public ArrayList<World> getList() {
        return list;
    }

    public void setList(ArrayList<World> list) {
        this.list = list;
    }

    public void insert(String eng) {
        String kor, wdate;
        int lev;
        World w;
        Scanner sc = new Scanner(System.in);
        System.out.println("* 뜻을 입력하세요");
        kor = sc.next();
        System.out.println("* 레벨을 입력하세요");
        lev = sc.nextInt();
        System.out.println("* 날짜를입력하세요");
        wdate = sc.next();
        w = new World(eng, kor, lev, wdate);
        list.add(w);

    }

    public void list() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public boolean find() {
        Scanner sc = new Scanner(System.in);
        boolean isFind = false;
        System.out.println("🤷‍찾는 단어를 입력하세요");
        String find = sc.next();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getEnglish().equals(find)) {
                System.out.println("있습니다.");
                System.out.println(list.get(i));
                isFind = true;
            }
        }
        return isFind;
    }

    public void save(String file) {
        try {
            PrintWriter pw = new PrintWriter((new FileOutputStream(file)));
            for (World w : list) {
                pw.println(w);
            }
            System.out.println("저장되었습니다.");
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void read(String file) {
        Scanner sc = null;
        String temp;
        String[] arr;
        World w;
        try {
            sc = new Scanner(new FileInputStream(file));
            while (sc.hasNextLine()) {
                temp = sc.nextLine();
                arr = temp.split(", ");
                w = new World(arr[0], arr[1], Integer.parseInt(arr[2]), arr[3]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}