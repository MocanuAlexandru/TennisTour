package matches;

import persons.TennisPlayer;

public class TennisMatchSingles extends TennisMatch {
    //DATE MEMBRE
    private TennisPlayer player1;
    private TennisPlayer player2;

    //CONSTRUCTORI
    public TennisMatchSingles(){}
    public TennisMatchSingles(TennisPlayer player1, TennisPlayer player2){
        this.player1 = player1;
        this.player2 = player2;
    }
    public TennisMatchSingles(Boolean indoor, String surface,TennisPlayer player1, TennisPlayer player2) {
        super(indoor,surface);
        this.player1 = player1;
        this.player2 = player2;
    }
    public TennisMatchSingles(Boolean indoor, String surface, Integer numberOfSetsToWin, TennisPlayer player1,
                              TennisPlayer player2) {
        super(indoor,surface,numberOfSetsToWin);
        this.player1 = player1;
        this.player2 = player2;
    }

    //GETTER
    public TennisPlayer getPlayer1() {
        return player1;
    }
    public TennisPlayer getPlayer2() {
        return player2;
    }
    public TennisPlayer getPlayer3() {
        return null;
    }
    public TennisPlayer getPlayer4() {
        return null;
    }

    //SETTER
    public void setPlayer1(TennisPlayer player1) {
        this.player1 = player1;
    }
    public void setPlayer2(TennisPlayer player2) {
        this.player2 = player2;
    }

    //FUNCTII UTILITARE
    public TennisPlayer getWinner(){
        int numberSetsPlayer1 = 0;
        int numberSetsPlayer2 = 0;
        int numberSets = getNumberOfSetsPlayed();
        int numberSetsToWin = getNumberOfSetsToWin();
        for(Integer i = 0;i< numberSets ; ++i){
            ScoreSet scor_set = getScore(i);
            if(scor_set == null) return null;
            if(scor_set.getScore1() > scor_set.getScore2()) {
                numberSetsPlayer1++;
                if(numberSetsPlayer1==numberSetsToWin) return player1;
            }
            else {
                numberSetsPlayer2++;
                if(numberSetsPlayer2==numberSetsToWin) return player2;
            }
        }
        return null;
    }

    //TO_STRING FUNCTION
    @Override
    public String toString() {
        String winner;
        if(this.getWinner() == null)
            winner = "Meci in desfasurare";
        else
            winner = this.getWinner().getNume();
        StringBuilder ans = new StringBuilder(player1.getNume() +
                " vs " + player2.getNume() + '\n' +
                "Castigatorul meciului: " + winner + '\n' +
                "Scor: ");
        for(int i=0;i<getNumberOfSetsPlayed();++i){
            ans.append(getScore(i).toString()).append(" ");
        }
        ans.append("\n");
        return ans.toString();
    }
}
