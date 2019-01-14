package pechpal.models;

import java.util.HashMap;

public class Ongeval extends WegVak{
    private HashMap<String, String> ongevalKenmerken;
    private String hectoMeter;
    private String vklNummer;
    private String wegNummer;


    public Ongeval() {
    }

    public String getWegNummer() {
        return wegNummer;
    }

    public void setWegNummer(String wegNummer) {
        this.wegNummer = wegNummer;
    }

    public String getVklNummer() {
        return vklNummer;
    }

    public void setVklNummer(String vklNummer) {
        this.vklNummer = vklNummer;
    }

    public String getHectoMeter() {
        return hectoMeter;
    }

    public void setHectoMeter(String hectoMeter) {
        this.hectoMeter = hectoMeter;
    }

    public HashMap<String, String> getOngevalKenmerken() {
        return ongevalKenmerken;
    }

    public void setOngevalKenmerken(HashMap<String, String> ongevalKenmerken) {
        this.ongevalKenmerken = ongevalKenmerken;
    }
}
