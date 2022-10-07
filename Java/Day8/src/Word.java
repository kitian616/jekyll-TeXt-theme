public class Word {
    private String english;
    private String korean;
    private int level;
    private String wdate;

    public Word(String english, String korean, int level, String wdate) {
        this.english = english;
        this.korean = korean;
        this.level = level;
        this.wdate = wdate;
    }

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

    @Override
    public String toString() {// 반환 값을 즉 주소값 변환하여 사용하기 위해 toString
        //apple : 사과 (레벨1, 날짜 2022-10-05
        return english + ": " + korean + " (레벨" + level + ", 날짜" + wdate + ")";
    }
}
