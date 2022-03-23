package database;

import circuits.Circuit;
import matches.ScoreSet;
import matches.TennisMatchSingles;
import persons.TennisPlayer;
import tournaments.Tournament;

import java.sql.*;
import java.util.Calendar;
import java.util.Vector;

public class DatabaseService {
    private static DatabaseService ourInstance = new DatabaseService();

    private DatabaseService() {
    }

    public static DatabaseService getInstance() {
        return ourInstance;
    }

    // SELECT
    public Vector<TennisPlayer> selectPlayers(Connection conn){
        Vector<TennisPlayer> result = new Vector<>();
        String selectText = "SELECT * FROM players";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectText);
            while (rs.next()){
                TennisPlayer selectedPlayer = new TennisPlayer();
                selectedPlayer.setIdPlayer(rs.getInt(1));
                selectedPlayer.setNume(rs.getString(2));
                Calendar dob = Calendar.getInstance();
                dob.setTime(rs.getDate(3));
                selectedPlayer.setDateOfBirth(dob);
                selectedPlayer.setNationality(rs.getString(4));
                selectedPlayer.setDominantHand(rs.getString(5).charAt(0));
                selectedPlayer.setPoints(rs.getInt(6));
                result.add(selectedPlayer);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public Vector<Tournament> selectTournaments(Connection conn) {
        Vector<Tournament> result = new Vector<>();
        String selectText = "SELECT * FROM tournaments";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectText);
            while (rs.next()){
                Tournament selectedTournament = new Tournament();
                selectedTournament.setId(rs.getInt(1));
                selectedTournament.setName(rs.getString(2));
                selectedTournament.setPointsModifier(rs.getDouble(3));
                result.add(selectedTournament);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public Vector<TennisMatchSingles> selectMatches(Connection conn, Integer id) {
        Vector<TennisMatchSingles> result = new Vector<>();
        String selectText = "SELECT * FROM matches where tournament_id = ?";
        try {
            PreparedStatement prepStmt = conn.prepareStatement(selectText);
            prepStmt.setInt(1, id);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()){
                String surface = rs.getString(3);
                Boolean indoor = rs.getString(4).equals("indoor");
                Integer numberOfSetsToWin = rs.getInt(5);
                TennisPlayer player1 = Circuit.getInstance().getPlayer(rs.getInt(6));
                TennisPlayer player2 = Circuit.getInstance().getPlayer(rs.getInt(7));

                TennisMatchSingles selectedMatch = new TennisMatchSingles(indoor, surface, numberOfSetsToWin,player1,player2);

                selectedMatch.setIdMatch(rs.getInt(2));

                result.add(selectedMatch);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public Vector<ScoreSet> selectScores(Connection conn, int id) {
        Vector<ScoreSet> result = new Vector<>();
        String selectScor = "SELECT * FROM scores where match_id = ?";
        try {
            PreparedStatement prepStmt = conn.prepareStatement(selectScor);
            prepStmt.setInt(1, id);
            ResultSet rs = prepStmt.executeQuery();
            while(rs.next()){
                ScoreSet score = new ScoreSet(rs.getInt(3),rs.getInt(4));
                result.add(score);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //INSERT
    public void insertPlayer(Connection conn, TennisPlayer p) {
        String insertText = "INSERT INTO players(name,dateOfBirth,nationality,dominantHand) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement prepStmt = conn.prepareStatement(insertText, Statement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, p.getNume());
            prepStmt.setDate(2,new Date(p.getDateOfBirth().getTimeInMillis()));
            prepStmt.setString(3, p.getNationality());
            prepStmt.setString(4, p.getDominantHand().toString());
            prepStmt.executeUpdate();
            ResultSet rs = prepStmt.getGeneratedKeys();
            rs.next();
            p.setIdPlayer(rs.getInt(1));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void insertTournament(Connection conn, Tournament t) {
        String insertText = "INSERT INTO tournaments(name,pointsModifier) VALUES (?, ?)";
        try{
            PreparedStatement prepStmt = conn.prepareStatement(insertText, Statement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, t.getName());
            prepStmt.setDouble(2, t.getPointsModifier());
            prepStmt.executeUpdate();
            ResultSet rs = prepStmt.getGeneratedKeys();
            rs.next();
            t.setId(rs.getInt(1));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertMatch(Connection conn, TennisMatchSingles match, Integer tournament_id) {
        String insertText = "INSERT INTO matches(tournament_id,surface,indoor,numberOfSetsToWin,player1_id,player2_id)" +
                            " VALUES (?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement prepStmt = conn.prepareStatement(insertText, Statement.RETURN_GENERATED_KEYS);
            prepStmt.setInt(1, tournament_id);
            prepStmt.setString(2, match.getSurface());
            prepStmt.setString(3, match.getIndoor() ? "indoor" : "outdoor");
            prepStmt.setInt(4,match.getNumberOfSetsToWin());
            prepStmt.setInt(5, match.getPlayer1().getIdPlayer());
            prepStmt.setInt(6, match.getPlayer2().getIdPlayer());
            prepStmt.executeUpdate();
            ResultSet rs = prepStmt.getGeneratedKeys();
            rs.next();
            match.setIdMatch(rs.getInt(1));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertScore(Connection conn, ScoreSet scor, Integer idMatch) {
        String insertText = "INSERT INTO scores(match_id,scor1,scor2) VALUES (?, ?, ?)";
        try{
            PreparedStatement prepStmt = conn.prepareStatement(insertText);
            prepStmt.setInt(1, idMatch);
            prepStmt.setInt(2, scor.getScore1());
            prepStmt.setInt(3, scor.getScore2());
            prepStmt.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //DELETE
    public void deletePlayer(Connection conn, Integer id){
        String deleteText = "DELETE FROM players WHERE player_id = ?";
        try {
            PreparedStatement prepStmt = conn.prepareStatement(deleteText);
            prepStmt.setInt(1,id);
            prepStmt.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteTournament(Connection conn, Integer id) {
        String deleteText = "DELETE FROM tournaments WHERE tournament_id = ?";
        try {
            PreparedStatement prepStmt = conn.prepareStatement(deleteText);
            prepStmt.setInt(1,id);
            prepStmt.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteMatch(Connection conn, Integer idMatch) {
        String deleteText = "DELETE FROM matches WHERE match_id = ?";
        try {
            PreparedStatement prepStmt = conn.prepareStatement(deleteText);
            prepStmt.setInt(1,idMatch);
            prepStmt.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteScore(Connection conn, Integer idMatch) {
        String deleteText = "DELETE FROM scores WHERE score_id = (select max(score_id) from (select * from scores) something where match_id = ?)";
        try {
            PreparedStatement prepStmt = conn.prepareStatement(deleteText);
            prepStmt.setInt(1,idMatch);
            prepStmt.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //UPDATE
    public void updatePlayer(Connection conn, TennisPlayer player) {
        String updateText = "UPDATE players SET name = ?, dateOfBirth = ?, nationality = ?, dominantHand = ?  WHERE player_id = ?";
        try {
            PreparedStatement prepStmt = conn.prepareStatement(updateText);
//            System.out.println(player);
            prepStmt.setString(1,player.getNume());
            prepStmt.setDate(2,new Date(player.getDateOfBirth().getTimeInMillis()));
            prepStmt.setString(3, player.getNationality());
            prepStmt.setString(4,player.getDominantHand().toString());
            prepStmt.setInt(5,player.getIdPlayer());
            prepStmt.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
