public class World {//먼저 변수를 선언한다.
    private String english;
    private String korean;
    private int level;
    private String wdate;
    // 변수가 private로 선언되어 있기 때문에 this를 이용하영 받아온 값을 넘겨준다.
    public World(String english, String korean, int level, String wdate) {
        this.english = english;
        this.korean = korean;
        this.level = level;
        this.wdate = wdate;
    }

    //getter/setter을 이용하여  메인 메소드에 값을 전달해주거나 받아오기 위해 선언해준다.
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getKorean() {
        return korean;
    }

    public void setKorean(String korean) {
        this.korean = korean;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getWdate() {
        return wdate;
    }

    public void setWdate(String wdate) {
        this.wdate = wdate;
    }
    // 오버라이드를 통해 메인에서 사용할 수 있게 해준다.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(english).append(", ").append(korean).append(", ")
                .append(level).append(", ").append(wdate);
        return sb.toString();
    }
}
