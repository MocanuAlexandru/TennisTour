package tournaments;

import matches.ScoreSet;
import matches.TennisMatch;
import matches.TennisMatchSingles;

import java.util.Vector;

public class Tournament {
    private String name;
    private Integer id;
    private Double pointsModifier;
    private Vector<TennisMatchSingles> matches;
    private static MatchService matchService = MatchService.getInstance();

    //CONSTRUCTORS
    {
        matches = new Vector<>();
    }
    public Tournament(){}
    public Tournament(String name, Double pointsModifier) {
        this.name = name;
        this.pointsModifier = pointsModifier;
    }
    public Tournament(String name, Integer id, Double pointsModifier) {
        this.name = name;
        this.id = id;
        this.pointsModifier = pointsModifier;
    }

    //GETTER
    public String getName() {
        return name;
    }
    public Integer getId() {
        return id;
    }
    public Double getPointsModifier() {
        return pointsModifier;
    }
    public Vector<TennisMatchSingles> getMatches() {
        return matches;
    }

    //SETTER
    public void setName(String name) {
        this.name = name;
    }
    public void setPointsModifier(Double pointsModifier) {
        this.pointsModifier = pointsModifier;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    //FOR MATCHES
    public void addMatch(TennisMatchSingles match, boolean logger){
        matchService.addMatch(this, match, logger);
    }
    public void cancelMatch(Integer index, boolean logger){
        matchService.cancelMatch(this, index, logger);
    }
    public void cancelAllMatches(boolean logger){matchService.cancelAllMatches(this,logger);}
    public void playSet(int index, ScoreSet scor, boolean logger){
        matchService.playSet(this, index, scor, logger);
    }
    public void cancelSetScore(Integer id, boolean logger){matchService.cancelSetScore(this,id,logger);}
    public void cancelMatchScore(Integer id, boolean logger){matchService.cancelMatchScore(this,id,logger);}
    public void listMatches(boolean logger){
        matchService.listMatches(this, logger);
    }

    TennisMatchSingles getMatch(Integer index) {
        return matchService.getMatch(this, index);
    }
}
