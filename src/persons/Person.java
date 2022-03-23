package persons;

import java.util.Calendar;

public class Person {
    //DATE MEMBRE
    private String nume;
    private Calendar dateOfBirth;
    private String nationality;

    //CONSTRUCTORI
    public Person(){}
    public Person(String nume, Calendar dob, String nat) {
        this.nume = nume;
        this.dateOfBirth = Calendar.getInstance();
        this.dateOfBirth.setTime(dob.getTime());
        this.nationality = nat;
    }

    //GETTERS
    public String getNume() {
        return nume;
    }
    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }
    public String getNationality() {
        return nationality;
    }

    //SETTERS
    public void setNume(String nume) {
        this.nume = nume;
    }
    public void setDateOfBirth(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    //FUNCTII UTILITARE
    public Integer computeAge() {
        Calendar present = Calendar.getInstance();
        Integer yearsBetween = present.get(Calendar.YEAR) - this.dateOfBirth.get(Calendar.YEAR);
        Calendar aux = Calendar.getInstance();
        aux.set(Calendar.YEAR, present.get(Calendar.YEAR));
        if(present.after(aux))
            return yearsBetween;
        else return yearsBetween - 1;
    }
}
