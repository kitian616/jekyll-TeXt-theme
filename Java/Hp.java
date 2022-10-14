public class Hp {//먼저 변수를 선언한다.
    private String name; // 이름, 휴대폰번호, 주소,메모,저장 선언
    private String phone;
    private String address;
    private String memo;
    private String date;
    // 변수가 private로 선언되어 있기 때문에 this를 이용하영 받아온 값을 넘겨준다.

    public Hp(String s, String s1, String s2, String s3) {
    }

    public Hp(String name, String phone, String address,
              String memo, String date) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.memo = memo;
        this.date = date;
    }
//getter/setter을 이용하여  메인 메소드에 값을 전달해주거나 받아오기 위해 선언해준다.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // 오버라이드를 통해 메인에서 사용할 수 있게 해준다.
    @Override
    public String toString() {
//        return "Hp[" +
//                "이름 = " + name +
//                ", 휴대폰번호 = " + phone +
//                ", 주소 = " + address +
//                ", 메모 = " + memo +
//                ", 등록된 날짜 = " + date +
//                "]";
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(", ").append(phone).append(", ")
                .append(address).append(", ").append(memo)
                .append(", ").append(date);
        return sb.toString();

    }
}
