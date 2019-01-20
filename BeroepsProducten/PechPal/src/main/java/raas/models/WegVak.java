package raas.models;

public class WegVak{
    String wegVakId;
    String wegNummer;
    String hectoLetter;
    String richting;

    public WegVak(){
    }

    public String getRichting() {
        return richting;
    }

    public void setRichting(String richting) {
        this.richting = richting;
    }

    public String getWegVakId() {
        return wegVakId;
    }

    public void setWegVakId(String wegVakId) {
        this.wegVakId = wegVakId;
    }

    public String getWegNummer() {
        return wegNummer;
    }

    public void setWegNummer(String wegNummer) {
        this.wegNummer = wegNummer;
    }

    public String getHectoLetter() {
        return hectoLetter;
    }

    public void setHectoLetter(String hectoLetter) {
        this.hectoLetter = hectoLetter;
    }
}
