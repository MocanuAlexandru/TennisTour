package tournaments;

import circuits.Circuit;
import database.DatabaseService;
import fileServices.LoggerService;
import matches.ScoreSet;
import matches.TennisMatchSingles;
import persons.TennisPlayer;

class MatchService {

    private static LoggerService loggerService = LoggerService.getInstance();
    private static DatabaseService databaseService = DatabaseService.getInstance();
    private static MatchService ourInstance = new MatchService();

    //SINGLETON FUNCTIONS
    static MatchService getInstance() {
        return ourInstance;
    }
    private MatchService() {
    }

    //SERVICE FUNCTIONS
    void addMatch(Tournament t, TennisMatchSingles match, boolean logger){
        t.getMatches().add(match);
        if(logger) {
            databaseService.insertMatch(Circuit.getInstance().getConnection(), match, t.getId());
            String message = "Added match to the tournament with id " + t.getId();
            loggerService.logMessage(message);
        }
    }
    void playSet(Tournament t, int index, ScoreSet scor, boolean logger){
        TennisMatchSingles match = t.getMatch(index);
        if(match.getWinner() != null) return;
        int nrSets = match.getNumberOfSetsPlayed();
        match.setScoreSet(nrSets + 1,scor.getScore1(), scor.getScore2());
        TennisPlayer winner = match.getWinner();
        if(logger) {
            databaseService.insertScore(Circuit.getInstance().getConnection(),scor, match.getIdMatch());
            String message = "Played set " + (nrSets+1) + " in match with number " + (index + 1) + " in the tournament with id " + t.getId();
            loggerService.logMessage(message);
        }
        if(winner == null) return;
        TennisPlayer loser = (winner == match.getPlayer1() ? match.getPlayer2() : match.getPlayer1());
        int winnerPoints = (int)(winner.getPoints() + Math.round(20 * t.getPointsModifier()));
        int loserPoints = (int)(loser.getPoints() - Math.round(5 * t.getPointsModifier()));
        Circuit circuit = Circuit.getInstance();
        circuit.updatePlayer(winner,winnerPoints);
        circuit.updatePlayer(loser,loserPoints);
    }
    void listMatches(Tournament t, boolean logger) {
        int counter = 0;
        for(TennisMatchSingles match : t.getMatches()) {
            counter++;
            System.out.println("Meciul " + counter + ": ");
            System.out.println(match.toString());
        }
        if(logger) {
            String message = "Display matches from the tournament with id " + t.getId();
            loggerService.logMessage(message);
        }
    }
    void cancelSetScore(Tournament t, Integer index, boolean logger){
        TennisMatchSingles match = t.getMatch(index);
        int nrSets = match.getNumberOfSetsPlayed();
        if(nrSets == 0) return;
        boolean finished = (match.getWinner() != null);
        TennisPlayer winner = null, loser = null;
        if(finished){
            winner = match.getWinner();
            loser = (winner == match.getPlayer1() ? match.getPlayer2() : match.getPlayer1());
        }
        match.removeScoreSet(nrSets);
        if(logger) {
            databaseService.deleteScore(Circuit.getInstance().getConnection(),match.getIdMatch());
            String message = "Canceled set " + nrSets + " in match with number " + (index + 1) + " in the tournament with id " + t.getId();
            loggerService.logMessage(message);
        }
        if(!finished) return;
        int winnerPoints = (int)(winner.getPoints() - Math.round(20 * t.getPointsModifier()));
        int loserPoints = (int)(loser.getPoints() + Math.round(5 * t.getPointsModifier()));
        Circuit circuit = Circuit.getInstance();
        circuit.updatePlayer(winner,winnerPoints);
        circuit.updatePlayer(loser,loserPoints);
    }
    void cancelMatchScore(Tournament t, Integer index, boolean logger){
        TennisMatchSingles match = t.getMatch(index);
        while(match.getNumberOfSetsPlayed() > 0){
            cancelSetScore(t,index,true);
        }
        if(logger) {
            String message = "Canceled score for match with number " + index + " in the tournament with id " + t.getId();
            loggerService.logMessage(message);
        }
    }
    void cancelMatch(Tournament t, Integer index, boolean logger){
        cancelMatchScore(t,index,true);
        TennisMatchSingles match = t.getMatch(index);
        t.getMatches().remove(match);
        if(logger) {
            databaseService.deleteMatch(Circuit.getInstance().getConnection(), match.getIdMatch());
            String message = "Removed match with number " + (index + 1) + " in the tournament with id " + t.getId();
            loggerService.logMessage(message);
        }
    }
    void cancelAllMatches(Tournament t, boolean logger){
        while(t.getMatches().size() > 0){
            cancelMatch(t, 0 ,true);
        }
        if(logger) {
            String message = "Removed all matches from the tournament with id " + t.getId();
            loggerService.logMessage(message);
        }
    }

    TennisMatchSingles getMatch(Tournament t, Integer index) {
        return t.getMatches().elementAt(index);
    }
}
