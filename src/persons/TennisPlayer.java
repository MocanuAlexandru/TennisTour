package persons;

import java.util.Calendar;

public class TennisPlayer extends Person{
    private Character dominantHand = 'r';
    private Integer idPlayer;
    private Integer points = 100;

    //CONSTRUCTORI
    public TennisPlayer(){}
    public TennisPlayer(String nume, Calendar dob, String nat, Character dHand){
        super(nume,dob,nat);
        this.dominantHand = dHand;
    }

    //TO_STRING FUNCTION
    @Override
    public String toString() {
        Calendar aux = getDateOfBirth();
        String hand;
        if(dominantHand == 'r') hand = "right";
        else hand = "left";
        return "TennisPlayer{" +
                "nume='" + getNume() +
                //", dateOfBirth=" + aux.get(Calendar.DAY_OF_MONTH) + "/" + (aux.get(Calendar.MONTH) + 1) + "/" + aux.get(Calendar.YEAR) +
                "', age=" + computeAge() +
                ", nationality='" + getNationality() + '\'' +
                ", dominantHand=" + hand +
                ", idPlayer=" + idPlayer +
                ", points=" + points +
                '}';
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof TennisPlayer)
        {
            return this.getIdPlayer().equals(((TennisPlayer) obj).getIdPlayer());
        }
        return false;
    }

    public Character getDominantHand() {
        return dominantHand;
    }
    public Integer getIdPlayer() {
        return idPlayer;
    }
    public Integer getPoints() { return points; }

    public void setDominantHand(Character dominantHand) {
        this.dominantHand = dominantHand;
    }
    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }
    public void setPoints(Integer points) { this.points = points; }
}
