package matches;

import persons.TennisPlayer;

public abstract class TennisMatch {
    //DATE MEMBRE
    private Boolean indoor;
    private String surface;
    private Integer numberOfSetsToWin;
    private ScoreSet[] scor;
    private Integer idMatch;

    //CONSTRUCTORI
    protected TennisMatch(){}
    protected TennisMatch(Boolean indoor, String surface){
        this.indoor = indoor;
        this.surface = surface;
        scor = new ScoreSet[2 * numberOfSetsToWin - 1];
    }
    protected TennisMatch(Boolean indoor, String surface, Integer numberOfSetsToWin) {
        this.indoor = indoor;
        this.surface = surface;
        this.numberOfSetsToWin = numberOfSetsToWin;
        scor = new ScoreSet[2 * numberOfSetsToWin - 1];
    }

    //GETTERS
    public Boolean getIndoor() {
        return indoor;
    }
    public String getSurface() {
        return surface;
    }
    public Integer getNumberOfSetsToWin() {
        return numberOfSetsToWin;
    }
    public ScoreSet getScore(Integer set){
        return scor[set];
    }
    public Integer getIdMatch() {
        return idMatch;
    }
    public abstract TennisPlayer getPlayer1();
    public abstract TennisPlayer getPlayer2();
    public abstract TennisPlayer getPlayer3();
    public abstract TennisPlayer getPlayer4();

    //SETTERS
    public void setIndoor(Boolean indoor) {
        this.indoor = indoor;
    }
    public void setSurface(String surface) {
        this.surface = surface;
    }
    public void setNumberOfSetsToWin(Integer numberOfSetsToWin) {
        this.numberOfSetsToWin = numberOfSetsToWin;
    }
    public void setIdMatch(Integer idMatch) {
        this.idMatch = idMatch;
    }
    public Boolean setScoreSet(Integer set, Integer score1, Integer score2){
        if(this.getNumberOfSetsPlayed() < set - 1) return false;
        scor[set - 1] = new ScoreSet(score1,score2);
        return true;
    }

    //FUNCTII UTILITARE
    public Boolean removeScoreSet(Integer set){
        if(this.getNumberOfSetsPlayed() != set) return false;
        scor[set - 1] = null;
        return true;
    }
    public Integer getNumberOfSetsPlayed(){
        Integer result = 0;
        for(ScoreSet aux : this.scor){
            if(aux == null) return result;
            result++;
        }
        return result;
    }
}
