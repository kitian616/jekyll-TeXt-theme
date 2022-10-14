import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class HpContoller {
    private ArrayList<Hp> list;

    public ArrayList<Hp> getList() {
        return list;
    }

    public void setList(ArrayList<Hp> list) {
        this.list = list;
    }

    public void insert(String A) {
        try {//예외처리 입력값이 이상이 발생하였을 때 대비
            String name, phone, address, memo, date;
            Hp h;
            Scanner sc = new Scanner(System.in);
            System.out.println("이름을 입력 : ");
            name = sc.next();


            boolean isFind = false;

//            String find = sc.next();
            System.out.println("휴대폰번호 입력 : ");
            phone = sc.next();// 휴대폰 번호와 동일한 번호가 있으면 다시 메뉴로 이동 저장불가
            for (int i = 0; i < list.size(); i++) {//리스트 사이즈만큼 확인`
                if (list.get(i).getPhone().equals(phone)) {// 동일하면 있는 것
                    System.out.println("중복된 값입니다 다시 입력하세요.");
                    System.out.println(list.get(i));
                    isFind = true;
                    return;
                }
            }
            System.out.println("주소를 입력 : ");
            address = sc.next();
            System.out.println("메모를 입력 : ");
            memo = sc.next();
            System.out.println("저장 날짜 입력 : ");
            date = sc.next();
            // h는 stack에 저장되어 계속 유지를 못해 arraylist를 이용해 저장
            h = new Hp(name, phone, address, memo, date);
//        HashSet<Hp> arr1 =new HashSet<>(list);
//        ArrayList<Hp> hpArrayLi = new ArrayList<Hp>(arr1);
            list.add(h);
        } catch (Exception e) {
            System.out.println("모든예외를 처리");//이상 값이 있을경우의 예외처리
        }
    }

    public void list() {

        for (int i = 0; i < list.size(); i++) {//리스트 수만큼 출력한다.
//            for (int j = i + 1; j < list.size(); j++) {
//                if(list.get(i).getName()>list.get(i).getName()){
                System.out.println(list.get(i));
                //이중 for문으로 getname 오름차순 시도하였지만 형변환을 못했습니다.
//            }}
        }
    }
    public boolean find() {// 동일한 전화번호가 있는지 확인
        Scanner sc = new Scanner(System.in);
        boolean isFind = false; // 처음 값은 실패로
        System.out.println("전화번호 검색");
        String find = sc.next();
        for (int i = 0; i < list.size(); i++) {//리스트 사이즈만큼 확인
            if (list.get(i).getPhone().equals(find)) {// 동일하면 있는 것
                System.out.println("있습니다.");
                System.out.println(list.get(i));
                isFind = true;
            }
        }
        return isFind;
    }

    public void save(String file) {//저장하기 위해
        try {// 저장을 반복해서 넣는다, 예외가 발생라면 캐치쪽으로
            PrintWriter pw = new PrintWriter((new FileOutputStream(file)));
            for (Hp h : list) {
                pw.println();
            }
            System.out.println("저장되었습니다.");
            pw.close();
        } catch (FileNotFoundException e) { // 예외처리
            e.printStackTrace();
        }
    }

    public void delete() {// 삭제
        Scanner sc = new Scanner(System.in);
        boolean flag;
        System.out.println("삭제할 전화번호 검색");
        String search = sc.next();
        for (int i = 0; i < list.size(); i++) {
            if (search.equals(list.get(i).getPhone())) {//폰번호와 일치하면 조건
                System.out.println("번호가 있습니다. 삭제");
                list.remove(i);//제거
                flag = true;
            }
        }
    }

    public void update(){
        Scanner sc = new Scanner(System.in);
        System.out.println("수정할 휴대폰 번호를 입력");
        String phone = sc.next();
        for(int i =0; i< list.size();i++){ // 먼저 저장된 번호만큼 돌린다.
            if(phone.equals(list.get(i).getPhone())){// 동일한 번호가 있으면
                System.out.println();// 보기 편하기 위해 한칸 띄움
                phone = sc.next();// 변경할 번호를 입력하고
                list.get(i).setPhone(phone);// 새로 바꿔준다.
                System.out.println("수정완료");
            }
        }
    }


    public void read(String file) {//읽어오기
        Scanner sc = null;// 초기 값
        String temp;
        String[] arr;// 배열선언

        try {
            sc = new Scanner(new FileInputStream(file));
            while (sc.hasNextLine()) {//phone.txt에서 한 줄을 보고 있는지 판단한다.
                // 배열의 길이가 얼마나 긴지 평소에는 모르기 때문애 arr[i]로 넣고 해도 괜찮다
                temp = sc.nextLine();//한 줄 읽어 str에 삽입
                arr = temp.split(", ");
              Hp  h = new Hp(arr[0],arr[1], arr[2], arr[3], arr[4]);
                list.add(h);// 저장
            }
            for (Hp hp : list) {
                System.out.println(hp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();//오류설명
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();//오류설명
        }
    }

}
