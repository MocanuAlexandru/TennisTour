package circuits;

import database.DatabaseService;
import fileServices.LoggerService;
import tournaments.Tournament;

class TournamentService {
    private static LoggerService loggerService = LoggerService.getInstance();
    private static DatabaseService databaseService = DatabaseService.getInstance();
    private static TournamentService ourInstance = new TournamentService();

    //SINGLETON FUNCTIONS
    static TournamentService getInstance() {
        return ourInstance;
    }
    private TournamentService() {
    }

    //SERVICE FUNCTIONS
    void registerTournament(Circuit c, Tournament t, boolean logger){
        c.getTournaments().add(t);
        if(logger) {
            databaseService.insertTournament(c.getConnection(), t);
            String message = "Added tournament with id " + t.getId();
            loggerService.logMessage(message);
        }
    }
    Boolean unregisterTournament(Circuit c, Integer id, boolean logger){
        for(Tournament tournament : c.getTournaments()){
            if(tournament.getId().equals(id)){
                tournament.cancelAllMatches(true);
                c.getTournaments().remove(tournament);
                if(logger) {
                    databaseService.deleteTournament(c.getConnection(), id);
                    String message = "Removed tournament with id " + tournament.getId();
                    loggerService.logMessage(message);
                }
                return true;
            }
        }
        return false;
    }
    void listTournaments(Circuit c, boolean logger) {
        for(Tournament tourn: c.getTournaments()){
            System.out.println(tourn.getName() + "(id: " + tourn.getId() + ") Importance: " + tourn.getPointsModifier());
        }
        if(logger) {
            String message = "Displayed the tournaments";
            loggerService.logMessage(message);
        }
    }
    Tournament getTournament(Circuit c, Integer id){
        for (Tournament t : c.getTournaments()) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }
}
