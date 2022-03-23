package circuits;

import database.ConnectionUtils;
import database.DatabaseService;
import fileServices.ReadService;
import fileServices.WriteService;
import matches.ScoreSet;
import matches.TennisMatch;
import matches.TennisMatchSingles;
import persons.TennisPlayer;
import tournaments.Tournament;

import javax.xml.crypto.Data;
import java.io.*;
import java.sql.Connection;
import java.util.*;

public class Circuit {

    private TreeSet<TennisPlayer> ranking;
    private Vector<Tournament> tournaments;
    private Connection connection;
    private static PlayerService playerService = PlayerService.getInstance();
    private static TournamentService tournamentService = TournamentService.getInstance();
    private static DatabaseService databaseService = DatabaseService.getInstance();

    //SINGLETON
    private static Circuit instance = new Circuit();
    public static Circuit getInstance(){
        return instance;
    }

    //FUNCTII PENTRU SERVICII
    public Connection getConnection() {
        return connection;
    }
    public TreeSet<TennisPlayer> getRanking() {
        return ranking;
    }
    Vector<Tournament> getTournaments() {
        return tournaments;
    }

    //CONSTRUCTOR
    private Circuit(){
        ranking = new TreeSet<>((o1,o2) -> {int res = o2.getPoints().compareTo(o1.getPoints());
                                            if(res == 0) return o1.getIdPlayer().compareTo(o2.getIdPlayer());
                                            else return res;});
        tournaments = new Vector<>();
    }

    //FOR PLAYERS
    public void registerPlayer(TennisPlayer p, boolean logger){
        playerService.registerPlayer(this, p, logger);
    }
    public Boolean unregisterPlayer(Integer id, boolean logger){
        return playerService.unregisterPlayer(this, id, logger);
    }
    public void displayRankings(boolean logger){
        playerService.displayRankings(this, logger);
    }

    public TennisPlayer getPlayer(Integer id){
        return playerService.getPlayer(this,id);
    }
    public void updatePlayer(TennisPlayer player,int points) {playerService.updatePlayer(this, player, points);}

    //FOR TOURNAMENTS
    public void registerTournament(Tournament t, boolean logger){
        tournamentService.registerTournament(this,t, logger);
    }
    public Boolean unregisterTournament(Integer id, boolean logger){
        return tournamentService.unregisterTournament(this,id, logger);
    }
    public void listTournaments(boolean logger) {
        tournamentService.listTournaments(this, logger);
    }

    public Tournament getTournament(Integer id){
        return tournamentService.getTournament(this,id);
    }


    //IMPORT DATA
    public void importData(){
        connection = ConnectionUtils.getInstance().getDBConnection();
//        this.importConstants();
//        this.importPlayers();
//        this.importTournaments();
//        this.importMatches();
        Circuit that = this;
        Thread thread_players = new Thread(() -> that.importPlayersSQL(connection));
        Thread thread_tournaments = new Thread(() -> that.importTournamentsSQL(connection));
        thread_players.start();
        thread_tournaments.start();
        try{
            thread_players.join();
            thread_tournaments.join();
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
        this.importMatchesSQL(connection);
        this.importScoresSQL(connection);
    }
    private void importConstants(){
        Properties properties = new Properties();

        try {
            properties.load(new FileReader(new File("src/files/constants.properties")));
//            this.counterIdPlayer = Integer.parseInt(properties.getProperty("CircuitCounterPlayer", Integer.toString(0)));
//            this.counterIdTournament = Integer.parseInt(properties.getProperty("CircuitCounterTournament", Integer.toString(0)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void importPlayers(){
        LinkedList<String[]> data = ReadService.getInstance().importDataCSV("src/files/Players.csv");
        int auxCount = 0;
        for(String[] player : data) {
            auxCount++;
            if(auxCount == 1) continue;
            String nume = player[0];

            Calendar dob = Calendar.getInstance();
            String[] date = player[1].split("/");
            dob.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
            dob.set(Calendar.MONTH, Integer.parseInt(date[1]));
            dob.set(Calendar.YEAR, Integer.parseInt(date[2]));

            String nat = player[2];

            Character dh = player[3].charAt(0);

            int id = Integer.parseInt(player[4]);

            Integer points = Integer.parseInt(player[5]);

            TennisPlayer newPlayer = new TennisPlayer(nume,dob,nat,dh);
            newPlayer.setIdPlayer(id);
//            newPlayer.setPoints(points);

            this.registerPlayer(newPlayer, false);
        }
    }
    private void importTournaments(){
        LinkedList<String[]> data = ReadService.getInstance().importDataCSV("src/files/Tournaments.csv");
        int auxCount = 0;
        for(String[] tournament : data) {
            auxCount++;
            if(auxCount == 1) continue;
            String nume = tournament[0];

            Double pointsModifier = Double.parseDouble(tournament[1]);

            Integer id = Integer.parseInt(tournament[2]);

            Tournament newTournament = new Tournament(nume,id,pointsModifier);

            this.registerTournament(newTournament, false);
        }
    }
    private void importMatches(){
        LinkedList<String[]> data = ReadService.getInstance().importDataCSV("src/files/Matches.csv");
        int auxCount = 0;
        for(String[] matches : data) {
            auxCount++;
            if(auxCount == 1) continue;
            String surface = matches[0];

            Boolean indoor = Boolean.parseBoolean(matches[1]);

            Integer setsToWin = Integer.parseInt(matches[2]);

            String[] scor;
            if(matches[3].length() == 0)
                scor = new String[0];
            else scor = matches[3].split(";");

            Integer player1ID = Integer.parseInt(matches[4]);

            Integer player2ID = Integer.parseInt(matches[5]);

            //matches[6,7] for doubles matches
            //TO DO

            Integer idTournament = Integer.parseInt(matches[8]);

            TennisMatchSingles newMatch = new TennisMatchSingles(indoor, surface, setsToWin, this.getPlayer(player1ID), this.getPlayer(player2ID));

            Tournament t = this.getTournament(idTournament);
            int len = t.getMatches().size();

            t.addMatch(newMatch,false);
            for (String s : scor) {
                String[] scorSet = s.split("-");
                Integer scor1 = Integer.parseInt(scorSet[0]);
                Integer scor2 = Integer.parseInt(scorSet[1]);
                t.playSet(len, new ScoreSet(scor1, scor2), false);
            }
        }
    }

    //IMPORT DATA SQL
    private void importPlayersSQL(Connection conn){
        Vector<TennisPlayer> players = databaseService.selectPlayers(conn);
        for(TennisPlayer player : players){
            this.registerPlayer(player, false);
        }
    }
    private void importTournamentsSQL(Connection conn) {
        Vector<Tournament> tournaments = databaseService.selectTournaments(conn);
        for(Tournament tournament : tournaments){
            this.registerTournament(tournament, false);
        }
    }
    private void importMatchesSQL(Connection conn) {
        for(Tournament tournament: this.getTournaments()){
            Vector<TennisMatchSingles> matches = databaseService.selectMatches(conn, tournament.getId());
            for(TennisMatchSingles match : matches) {
                tournament.addMatch(match, false);
            }
        }
    }
    private void importScoresSQL(Connection conn) {
        for(Tournament tournament : this.getTournaments()){
            for(int index = 0; index < tournament.getMatches().size(); ++index){
                Vector<ScoreSet> scores = databaseService.selectScores(conn, tournament.getMatches().get(index).getIdMatch());
                for(ScoreSet score : scores){
                    tournament.playSet(index, score,false);
                }
            }
        }
    }

    //EXPORT DATA
    public void exportData(){
        ConnectionUtils.getInstance().closeDBConnection(connection);
//        this.exportConstants();
//        this.exportPlayers();
//        this.exportTournaments();
//        this.exportMatches();
    }
    private void exportConstants(){
        Properties properties = new Properties();

        try {
            properties.load(new FileReader(new File("src/files/constants.properties")));
//            properties.setProperty("CircuitCounterPlayer", this.counterIdPlayer.toString());
//            properties.setProperty("CircuitCounterTournament", this.counterIdTournament.toString());
            properties.store(new PrintStream(new File("src/files/constants.properties")), "Constants File");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void exportPlayers(){
        LinkedList<String[]> result = new LinkedList<>();
        result.add(this.getHeaderPlayers());
        for(TennisPlayer player : this.getRanking()){
            String[] informations = new String[6];
            informations[0] = player.getNume();

            Calendar dateOfBirth = player.getDateOfBirth();
            String[] date = new String[3];
            date[0] = Integer.toString(dateOfBirth.get(Calendar.DAY_OF_MONTH));
            date[1] = Integer.toString(dateOfBirth.get(Calendar.MONTH));
            date[2] = Integer.toString(dateOfBirth.get(Calendar.YEAR));
            informations[1] = String.join("/",date);

            informations[2] = player.getNationality();

            informations[3] = player.getDominantHand().toString();

            informations[4] = player.getIdPlayer().toString();

            informations[5] = player.getPoints().toString();

            result.add(informations);
        }
        WriteService.getInstance().exportDataCSV(result,"src/files/Players.csv");
    }
    private void exportTournaments(){
        LinkedList<String[]> result = new LinkedList<>();
        result.add(this.getHeaderTournaments());
        for(Tournament tournament : this.getTournaments()){
            String[] informations = new String[3];
            informations[0] = tournament.getName();

            informations[1] = tournament.getPointsModifier().toString();

            informations[2] = tournament.getId().toString();

            result.add(informations);
        }
        WriteService.getInstance().exportDataCSV(result,"src/files/Tournaments.csv");
    }
    private void exportMatches(){
        LinkedList<String[]> result = new LinkedList<>();
        result.add(this.getHeaderMatches());
        for(Tournament currentTournament : this.getTournaments()){
            for(TennisMatch match : currentTournament.getMatches()){
                String[] informations = new String[9];
                informations[0] = match.getSurface();

                informations[1] = match.getIndoor().toString();

                informations[2] = match.getNumberOfSetsToWin().toString();

                String[] scorSet = new String[match.getNumberOfSetsPlayed()];
                for(int i=0; i<scorSet.length; ++i){
                    String scor1 = match.getScore(i).getScore1().toString();
                    String scor2 = match.getScore(i).getScore2().toString();
                    scorSet[i] = String.join("-",scor1,scor2);
                }
                String scor = String.join(";", scorSet);
                informations[3] = scor;

                informations[4] = match.getPlayer1().getIdPlayer().toString();

                informations[5] = match.getPlayer2().getIdPlayer().toString();

                informations[6] = (match.getPlayer3() == null ? "-1" : match.getPlayer3().getIdPlayer().toString());

                informations[7] = (match.getPlayer4() == null ? "-1" : match.getPlayer4().getIdPlayer().toString());

                informations[8] = currentTournament.getId().toString();

                result.add(informations);
            }
        }
        WriteService.getInstance().exportDataCSV(result,"src/files/Matches.csv");
    }

    private String[] getHeaderPlayers() {
        return new String[]{"Name","DateOfBirth","Nationality","DominantHand","IdPlayer","Points"};
    }
    private String[] getHeaderMatches() {
        return new String[] {"Surface","Indoor","SetsToWin","Scor","Player1ID","Player2ID","Player3ID","Player4ID","TournamentID"};
    }
    private String[] getHeaderTournaments() {
        return new String[]{"Name","PointsModifier","ID"};
    }
}
