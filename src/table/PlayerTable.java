package table;

import circuits.Circuit;
import database.DatabaseService;
import persons.TennisPlayer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.TreeSet;
import java.util.Vector;

public class PlayerTable extends AbstractTableModel {
    private Vector<TennisPlayer> players;
    private String editedName = "";
    private Calendar editedDateOfBirth = Calendar.getInstance();
    private String editedNationality = "";
    private Character editedDominantHand = 'r';
    private static DatabaseService databaseService = DatabaseService.getInstance();

    public PlayerTable(TreeSet<TennisPlayer> players) {
        this.players = new Vector<>();
        for(TennisPlayer player : players) {
            TennisPlayer newPlayer = new TennisPlayer();
            newPlayer.setIdPlayer(player.getIdPlayer());
            newPlayer.setNume(player.getNume());
            newPlayer.setDateOfBirth(player.getDateOfBirth());
            newPlayer.setNationality(player.getNationality());
            newPlayer.setDominantHand(player.getDominantHand());
            newPlayer.setPoints(player.getPoints());
            this.players.add(newPlayer);
        }
    }

    private Vector<String> getAcceptedCountries(){
        Vector<String> ans = new Vector<>();
        ans.add("Romania");
        ans.add("Switzerland");
        ans.add("Spain");
        ans.add("Serbia");
        ans.add("Germany");
        ans.add("Austria");
        return ans;
    }
    private String[] getHeader() {
        return new String[]{"Name","Date Of Birth","Nationality","Right Handed?","ID Player","Points", "Options"};
    }

    public int getRowCount(){
        return this.players.size() + 1;
    }
    public int getColumnCount() {
        return this.getHeader().length;
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex < 0 || columnIndex >= this.getColumnCount()) return null;
        if (rowIndex < 0 || rowIndex >= this.getRowCount()) return null;
        if (rowIndex == this.getRowCount() - 1) {
            //ADD ROW
            switch(columnIndex) {
                case 0:
                    return editedName;
                case 1:
                    Calendar cal = editedDateOfBirth;
                    return cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
                case 2:
                    return editedNationality;
                case 3:
                    return editedDominantHand.equals('r');
                case 4:
                case 5:
                    return 0;
                case 6: {
                    JButton button = new JButton("Add");
                    PlayerTable that = this;
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            TennisPlayer newPlayer = new TennisPlayer(editedName, editedDateOfBirth, editedNationality, editedDominantHand);
                            newPlayer.setPoints(100);
                            databaseService.insertPlayer(Circuit.getInstance().getConnection(),newPlayer);
                            that.players.add(newPlayer);
                            editedName = "";
                            editedDateOfBirth = Calendar.getInstance();
                            editedNationality = "";
                            editedDominantHand = 'r';
                            that.fireTableRowsInserted(rowIndex, rowIndex);
                        }
                    });
                    return button;
                }
                default: return null;
            }
        }
        switch(columnIndex) {
            case 0:
                return this.players.elementAt(rowIndex).getNume();
            case 1: {
                    Calendar cal = this.players.elementAt(rowIndex).getDateOfBirth();
                    return cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
                }
            case 2:
                return this.players.elementAt(rowIndex).getNationality();
            case 3:
                return (this.players.elementAt(rowIndex).getDominantHand().equals('r'));
            case 4:
                return this.players.elementAt(rowIndex).getIdPlayer();
            case 5:
                return this.players.elementAt(rowIndex).getPoints();
            case 6: {
                JButton button = new JButton("Delete");
                PlayerTable that = this;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        databaseService.deletePlayer(Circuit.getInstance().getConnection(),that.players.elementAt(rowIndex).getIdPlayer());
                        players.remove(rowIndex);
                        that.fireTableRowsDeleted(rowIndex, rowIndex);
                    }
                });
                return button;
            }
        }
        return null;
    }
    public String getColumnName(int columnIndex) {
        return this.getHeader()[columnIndex];
    }
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex >= 0 && columnIndex <= 2){
            return String.class;
        }
        if(columnIndex == 3){
            return Boolean.class;
        }
        if(columnIndex == 4 || columnIndex == 5){
            return Integer.class;
        }
        if(columnIndex == 6) {
            return JButton.class;
        }
        return null;
    }
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 0 && columnIndex <= 3;
    }
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(rowIndex == this.getRowCount() -1) {
            switch(columnIndex) {
                case 0:
                    editedName = (String)aValue;
                    return ;
                case 1:
                    String[] date = ((String) aValue).split("/");
                    if(date.length != 3) return ;
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
                    newDate.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
                    newDate.set(Calendar.YEAR, Integer.parseInt(date[2]));
                    editedDateOfBirth = newDate;
                    return ;
                case 2:
                    if(!this.getAcceptedCountries().contains(aValue)) return ;
                    editedNationality = (String)aValue;
                    return ;
                case 3:
                    if(((Boolean) aValue)){
                        editedDominantHand = 'r';
                    }
                    else {
                        editedDominantHand = 'l';
                    }
                    return ;
            }
            return ;
        }
        if(columnIndex == 0) {
            if(aValue instanceof String) {
                this.players.elementAt(rowIndex).setNume((String)aValue);
            }
        }
        if(columnIndex == 1) {
            if(aValue instanceof String){
                String[] date = ((String) aValue).split("/");
                if(date.length != 3) return ;
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
                newDate.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
                newDate.set(Calendar.YEAR, Integer.parseInt(date[2]));
                this.players.elementAt(rowIndex).setDateOfBirth(newDate);
            }
        }
        if(columnIndex == 2) {
            if(aValue instanceof String) {
                if(!this.getAcceptedCountries().contains(aValue)) return ;
                this.players.elementAt(rowIndex).setNationality((String) aValue);
            }
        }
        if(columnIndex == 3) {
            if(aValue instanceof Boolean) {
                if(((Boolean) aValue)){
                    this.players.elementAt(rowIndex).setDominantHand('r');
                }
                else {
                    this.players.elementAt(rowIndex).setDominantHand('l');
                }
            }
        }
        databaseService.updatePlayer(Circuit.getInstance().getConnection(), this.players.elementAt(rowIndex));
    }
}
