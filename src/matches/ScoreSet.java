package matches;

public class ScoreSet {
    //DATE MEMBRE
    private Integer score1;
    private Integer score2;

    //CONSTRUCTORI
    public ScoreSet(Integer score1, Integer score2){
        this.score1 = score1;
        this.score2 = score2;
    }

    //GETTER
    public Integer getScore1() {
        return score1;
    }
    public Integer getScore2() {
        return score2;
    }

    //SETTER
    public void setScore1(Integer score1) {
        this.score1 = score1;
    }
    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    //TO_STRING
    @Override
    public String toString() {
        return score1 + "-" + score2 ;
    }
}
