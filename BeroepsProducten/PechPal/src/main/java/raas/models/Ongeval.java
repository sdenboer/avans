package pechpal.models;

import java.util.HashMap;


/**
 * De Ongeval class is de class die ongevallen laadt uit het Ongevallen.txt bestand van de BRON.
 * Ieder jaar zijn de ongeval kenmerken anders en verandert de dataset dus. Dmv de OngevalKenmerken combineer ik alle
 * kenmerken en is de dataset dus voor ieder jaar te gebruiken, mocht deze weer veranderen.
 */
public class Ongeval extends WegVak {
    private HashMap<String, String> ongevalKenmerken;
    private String hectoMeter;
    private String vklNummer;

    public Ongeval() {
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
