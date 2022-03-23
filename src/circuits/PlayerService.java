package circuits;

import database.DatabaseService;
import fileServices.LoggerService;
import matches.TennisMatchSingles;
import persons.TennisPlayer;

import java.sql.Connection;

class PlayerService {
    private static LoggerService loggerService = LoggerService.getInstance();
    private static DatabaseService databaseService  = DatabaseService.getInstance();
    private static PlayerService ourInstance = new PlayerService();

    //SINGLETON FUNCTIONS
    static PlayerService getInstance() {
        return ourInstance;
    }
    private PlayerService() {
    }

    //SERVICE FUNCTIONS
    void registerPlayer(Circuit c, TennisPlayer p, boolean logger){
        if(logger) databaseService.insertPlayer(c.getConnection(), p);
        c.getRanking().add(p);
        if(logger) {
            String message = "Added player with id " + p.getIdPlayer();
            loggerService.logMessage(message);
        }
    }
    Boolean unregisterPlayer(Circuit c, Integer id, boolean logger){
        for(TennisPlayer player : c.getRanking()){
            if(player.getIdPlayer().equals(id)){
                c.getRanking().remove(player);
                if(logger) {
                    databaseService.deletePlayer(c.getConnection(), id);
                    String message = "Removed player with id " + player.getIdPlayer();
                    loggerService.logMessage(message);
                }
                return true;
            }
        }
        return false;

    }
    void displayRankings(Circuit c, boolean logger){
        int ranking = 0;
        for(TennisPlayer player: c.getRanking()){
            ranking++;
            System.out.println(ranking + ". " + player.getNume() + "(id: " + player.getIdPlayer() + ") " + player.getPoints() + "pt");
        }
        if(logger) {
            String message = "Displayed the players";
            loggerService.logMessage(message);
        }
    }

    TennisPlayer getPlayer(Circuit c, Integer id){
        for (TennisPlayer next : c.getRanking()) {
            if (next.getIdPlayer().equals(id)) {
                return next;
            }
        }
        return null;
    }
    void updatePlayer(Circuit c, TennisPlayer player, int points){
        if(c.getRanking().remove(player)){
            player.setPoints(points);
            c.getRanking().add(player);
        }
    }
}
