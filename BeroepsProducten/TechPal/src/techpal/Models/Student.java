package techpal.Models;

public class Student extends Person {
    private String hnr, niveau;

    public Student() {
    }

    public String getHnr() {
        return hnr;
    }

    public void setHnr(String hnr) {
        this.hnr = hnr;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}
