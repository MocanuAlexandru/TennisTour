package demo;

import table.PlayerTable;
import circuits.Circuit;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

    private static void printOptions(){
        System.out.println("Introduceti numarului optiunii pe care o doriti: ");
        System.out.println("1.Adaugati un jucator.");
        System.out.println("2.Eliminati un jucator.");
        System.out.println("3.Afisati clasamentul.");
        System.out.println("4.Adaugati turneu.");
        System.out.println("5.Stergeti un turneu.");
        System.out.println("6.Listati turneele.");
        System.out.println("7.Adaugati un meci.");
        System.out.println("8.Stergeti un meci.");
        System.out.println("9.Stergeti toate meciurile de la un turneu.");
        System.out.println("10.Introduceti scorul unui set pentru un meci.");
        System.out.println("11.Anulati scorul ultimului set jucat pentru un meci.");
        System.out.println("12.Anulati scorul unui meci.");
        System.out.println("13.Listati meciurile din cadrul unui turneu.");
        System.out.println("14.Inchideti aplicatia.");
        System.out.println();
    }

    public static void main(String[] args) {

//        Meniu pentru etapa 1
//        ----------------------------------------------------------------------------------------------
//        Circuit circuit = new Circuit();
//
//         //CITIRE SI AFISARE JUCATORI (command line)
//         Scanner cin = new Scanner(System.in);
//        System.out.print("Cati jucatori doriti sa adaugati? ");
//        Integer n = cin.nextInt();
//        //TennisPlayer[] players = new TennisPlayer[n];
//        System.out.println("Introduceti informatiile celor " + n + " jucatori.");
//        for(Integer i = 0; i < n; ++i){
//            System.out.print("Nume: ");
//            String nume = cin.nextLine();
//
//            System.out.print("Date of Birth: ");
//            Calendar dob = Calendar.getInstance();
//            dob.set(Calendar.DAY_OF_MONTH, cin.nextInt());
//            dob.set(Calendar.MONTH, cin.nextInt());
//            dob.set(Calendar.YEAR, cin.nextInt());
//
//            System.out.print("Nationality: ");
//            String nat = cin.next();
//
//            System.out.print("Dominant Hand: ");
//            Character dh = cin.next().charAt(0);
//
//            System.out.println();
//
//            circuit.registerPlayer(new TennisPlayer(nume,dob,nat,dh));
//        }
//        for(Integer i = 0; i < n; ++i){
//            System.out.println();
//            System.out.println("Informatiile jucatorului "+ i);
//            System.out.println(circuit.getPlayer(i+1));
//        }
//
//        TennisMatchSingles match = new TennisMatchSingles(true, "clay", circuit.getPlayer(1), circuit.getPlayer(2));
//        circuit.addMatch(match);
//        circuit.playSet(match, new ScoreSet(6,4));
//        circuit.playSet(match, new ScoreSet(3,6));
//        circuit.playSet(match, new ScoreSet(6,7,2));
//        System.out.println("\n" +  circuit.getMatch(0));


//        System.out.println(circuit.getRanking());
//
//        Circuit circuit = Circuit.getInstance();
//        circuit.importData();
//        System.out.println(circuit.getMatches());
//        circuit.exportData();

//        ----------------------------------------------------------------------------------------------
//        Meniu pentru etapa 2
//        ----------------------------------------------------------------------------------------------
//        Circuit circuit = Circuit.getInstance();
//        circuit.importData();
//
//        boolean running = true;
//
//        while(running) {
//            Scanner cin = new Scanner(System.in);
//            printOptions();
//            int n = cin.nextInt();
//            switch (n) {
//                case 1: {
//                    System.out.println("Introduceti informatiile jucatorului.");
//                    System.out.print("Nume: ");
//                    String nume = cin.next();
//
//                    System.out.print("Date of Birth: ");
//                    Calendar dob = Calendar.getInstance();
//                    dob.set(Calendar.DAY_OF_MONTH, cin.nextInt());
//                    dob.set(Calendar.MONTH, cin.nextInt());
//                    dob.set(Calendar.YEAR, cin.nextInt());
//
//                    System.out.print("Nationality: ");
//                    String nat = cin.next();
//
//                    char dh;
//                    do {
//                        System.out.print("Dominant Hand (left or right): ");
//                        dh = cin.next().charAt(0);
//                    } while (dh != 'l' && dh != 'r');
//                    System.out.println();
//
//                    circuit.registerPlayer(new TennisPlayer(nume, dob, nat, dh), true);
//
//                    System.out.println("Jucator inregistrat cu succes.");
//                    System.out.println();
//                    break;
//                } //ADD PLAYER
//                case 2: {
//                    circuit.displayRankings(false);
//                    System.out.println("Introduceti id-ul jucatorului pe care doriti sa-l eliminati.");
//                    int id = cin.nextInt();
//                    if(circuit.unregisterPlayer(id,true))
//                        System.out.println("Jucator eliminat din circuit cu succes.");
//                    else
//                        System.out.println("Jucatorul cu id-ul indicat nu este inscris in circuit.");
//                    System.out.println();
//                    break;
//                } //REMOVE PLAYER
//                case 3: {
//                    circuit.displayRankings(true);
//                    System.out.println();
//                    break;
//                } //DISPLAY RANKINGS
//                case 4: {
//                    System.out.println("Introduceti informatiile turneului.");
//                    System.out.print("Nume: ");
//                    String nume = cin.next();
//
//                    System.out.print("Introduceti importanta turneului (un numar real intre 0 si 5): ");
//                    Double pointsModifier = cin.nextDouble();
//
//                    circuit.registerTournament(new Tournament(nume, pointsModifier), true);
//
//                    System.out.println("Turneu inregistrat cu succes.");
//                    System.out.println();
//                    break;
//                } //ADD TOURNAMENT
//                case 5: {
//                    circuit.listTournaments(false);
//                    System.out.println("Introduceti id-ul turneului pe care doriti sa-l eliminati.");
//                    int id = cin.nextInt();
//                    if (circuit.unregisterTournament(id,true))
//                        System.out.println("Turneu eliminat din circuit cu succes.");
//                    else
//                        System.out.println("Turneul cu id-ul indicat nu este inscris in circuit.");
//                    System.out.println();
//                    break;
//                } //REMOVE TOURNAMENT
//                case 6: {
//                    circuit.listTournaments(true);
//                    System.out.println();
//                    break;
//                } //LIST TOURNAMENTS
//                case 7: {
//                    circuit.listTournaments(false);
//                    System.out.print("Introduceti id-ul turneului la care doriti sa adaugati un meci: ");
//                    int idTurneu = cin.nextInt();
//                    if (circuit.getTournament(idTurneu) == null) {
//                        System.out.println("Id invalid!");
//                        break;
//                    }
//
//                    System.out.println("Introduceti informatiile meciului.");
//                    System.out.print("Suprafata: ");
//                    String surface = cin.next();
//
//                    System.out.print("Indoor: ");
//                    Boolean indoor = cin.nextBoolean();
//
//                    System.out.print("Number of Sets To Win the Match: ");
//                    Integer setsToWin = cin.nextInt();
//
//                    circuit.displayRankings(false);
//                    System.out.print("Introduceti id-ul primului jucator: ");
//                    int idPlayer1 = cin.nextInt();
//                    if (circuit.getPlayer(idPlayer1) == null) {
//                        System.out.println("Id invalid.");
//                        break;
//                    }
//                    System.out.print("Introduceti id-ul celui de-al doilea jucator (diferit de primul): ");
//                    int idPlayer2 = cin.nextInt();
//                    if (circuit.getPlayer(idPlayer2) == null || idPlayer2 == idPlayer1) {
//                        System.out.println("Id invalid.");
//                        break;
//                    }
//
//                    circuit.getTournament(idTurneu).addMatch(new TennisMatchSingles(indoor, surface, setsToWin,
//                            circuit.getPlayer(idPlayer1), circuit.getPlayer(idPlayer2)), true);
//
//                    System.out.println("Meci adaugat cu succes.");
//                    System.out.println();
//                    break;
//                } //ADD MATCH
//                case 8: {
//                    circuit.listTournaments(false);
//                    System.out.print("Introduceti id-ul turneului din care face parte meciul pe care doriti sa-l eliminati: ");
//                    int idTurneu = cin.nextInt();
//                    if (circuit.getTournament(idTurneu) == null) {
//                        System.out.println("Id invalid!");
//                        break;
//                    }
//                    Tournament selectedTournament = circuit.getTournament(idTurneu);
//                    selectedTournament.listMatches(false);
//                    System.out.print("Introduceti numarul meciului pe care doriti sa-l stergeti: ");
//                    int idMatch = cin.nextInt();
//                    if(idMatch <= 0 || idMatch > selectedTournament.getMatches().size()){
//                        System.out.println("Numar invalid!");
//                        break;
//                    }
//                    selectedTournament.cancelMatch(idMatch-1, true);
//                    System.out.println();
//                    System.out.println("Meciul a fost eliminat cu succes.");
//                    System.out.println();
//                    break;
//                } //REMOVE MATCH
//                case 9: {
//                    circuit.listTournaments(false);
//                    System.out.print("Introduceti id-ul turneului ale carui meciuri doriti sa le eliminati: ");
//                    int idTurneu = cin.nextInt();
//                    if (circuit.getTournament(idTurneu) == null) {
//                        System.out.println("Id invalid!");
//                        break;
//                    }
//                    Tournament selectedTournament = circuit.getTournament(idTurneu);
//                    selectedTournament.cancelAllMatches(true);
//                    System.out.println("Meciurile au fost eliminate cu succes.");
//                    System.out.println();
//                    break;
//                } //REMOVE MATCHES
//                case 10: {
//                    circuit.listTournaments(false);
//                    System.out.print("Introduceti id-ul turneului din care face parte meciul al carui scor vreti sa-l modificati: ");
//                    int idTurneu = cin.nextInt();
//                    if (circuit.getTournament(idTurneu) == null) {
//                        System.out.println("Id invalid!");
//                        break;
//                    }
//                    Tournament selectedTournament = circuit.getTournament(idTurneu);
//                    selectedTournament.listMatches(false);
//                    System.out.print("Introduceti numarul meciului pentru care doriti sa adaugati scorul setului: ");
//                    int idMatch = cin.nextInt();
//                    if(idMatch <= 0 || idMatch > selectedTournament.getMatches().size()){
//                        System.out.println("Numar invalid!");
//                        break;
//                    }
//                    System.out.print("Introduceti scorul primului jucator: ");
//                    int scor1 = cin.nextInt();
//                    System.out.print("Introduceti scorul celui de-al doilea jucator: ");
//                    int scor2 = cin.nextInt();
//                    selectedTournament.playSet(idMatch-1, new ScoreSet(scor1, scor2), true);
//                    System.out.println("Scor introdus cu succes.");
//                    break;
//                } //PLAY SET
//                case 11: {
//                    circuit.listTournaments(false);
//                    System.out.print("Introduceti id-ul turneului din care face parte meciul al carui scor vreti sa-l anulati: ");
//                    int idTurneu = cin.nextInt();
//                    if (circuit.getTournament(idTurneu) == null) {
//                        System.out.println("Id invalid!");
//                        break;
//                    }
//                    Tournament selectedTournament = circuit.getTournament(idTurneu);
//                    selectedTournament.listMatches(false);
//                    System.out.print("Introduceti numarul meciului pentru care doriti sa anulati scorul ultimului set: ");
//                    int idMatch = cin.nextInt();
//                    if(idMatch <= 0 || idMatch > selectedTournament.getMatches().size()){
//                        System.out.println("Numar invalid!");
//                        break;
//                    }
//                    selectedTournament.cancelSetScore(idMatch-1, true);
//                    System.out.println();
//                    System.out.println("Scorul setului a fost anulat cu succes.");
//                    System.out.println();
//                    break;
//                } //CANCEL LAST SET SCORE
//                case 12: {
//                    circuit.listTournaments(false);
//                    System.out.print("Introduceti id-ul turneului din care face parte meciul al carui scor vreti sa-l anulati: ");
//                    int idTurneu = cin.nextInt();
//                    if (circuit.getTournament(idTurneu) == null) {
//                        System.out.println("Id invalid!");
//                        break;
//                    }
//                    Tournament selectedTournament = circuit.getTournament(idTurneu);
//                    selectedTournament.listMatches(false);
//                    System.out.print("Introduceti numarul meciului pentru care doriti sa anulati scorul: ");
//                    int idMatch = cin.nextInt();
//                    if(idMatch <= 0 || idMatch > selectedTournament.getMatches().size()){
//                        System.out.println("Numar invalid!");
//                        break;
//                    }
//                    selectedTournament.cancelMatchScore(idMatch-1, true);
//                    System.out.println();
//                    System.out.println("Scorul meciului a fost anulat cu succes.");
//                    System.out.println();
//                    break;
//                } //CANCEL MATCH SCORE
//                case 13: {
//                    circuit.listTournaments(false);
//                    System.out.print("Introduceti id-ul turneului pentru care doriti sa listati meciurile: ");
//                    int idTurneu = cin.nextInt();
//                    if (circuit.getTournament(idTurneu) == null) {
//                        System.out.println("Id invalid!");
//                        break;
//                    }
//                    circuit.getTournament(idTurneu).listMatches(true);
//
//                    System.out.println();
//                    break;
//                } //LIST MATCHES FROM TOURNAMENT
//                case 14: {
//                    running = false;
//                    break;
//                } // EXIT
//                default: {
//                    System.out.println("Varianta aleasa este invalida. Introduceti alta optiune.");
//                } //INVALID
//            }
//        }
//        circuit.exportData();
//        ----------------------------------------------------------------------------------------------
//        Meniu pentru etapa 3
//        ----------------------------------------------------------------------------------------------
        Circuit circuit = Circuit.getInstance();

        circuit.importData();

        JFrame jFrame = new JFrame("Table with Tennis Players");

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTable table = new JTable(new PlayerTable(circuit.getRanking()));

        TableCellRenderer buttonRenderer = new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return (JButton)value;
            }
        };
        table.getColumn("Options").setCellRenderer(buttonRenderer);
        table.addMouseListener(new JTableButtonMouseListener(table));

        jFrame.add(new JScrollPane(table));
        jFrame.setSize(640, 480);

        jFrame.setVisible(true);

//        circuit.exportData();
    }

    private static class JTableButtonMouseListener extends MouseAdapter {
        private final JTable table;

        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        public void mouseClicked(MouseEvent e) {
            int column = table.getColumnModel().getColumnIndexAtX(e.getX());
            int row    = e.getY()/table.getRowHeight();
            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);
                if (value instanceof JButton) {
                    ((JButton)value).doClick();
                }
            }
        }
    }
}
