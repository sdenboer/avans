package raas.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import raas.*;
import raas.models.Ongeval;
import raas.models.PuntLocatie;

import java.io.IOException;
import java.util.*;
import java.util.stream.StreamSupport;

public class OngevalController extends AbstractParseJsonBestand {
    private HmApi hmApi;

    public OngevalController() {
        super.filterBestand();
    }

    @Override
    public String fileName() {
        return "ongevallen";
    }

    public Ongeval findOngevalByVKL(String vklNummer) {
        Ongeval ongeval = new Ongeval();
        HashMap<String, String> hmOngeval = new HashMap<>();
        JsonElement jsonElement = StreamSupport
                .stream(this.jsonArray.spliterator(), false)
                .map(value -> (JsonObject) value)
                .filter(value -> value.get("VKL_NUMMER").getAsString().equals(vklNummer)
                        && value.get("FK_VELD5").getAsString().contains("HTT"))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        connectReferentieEnOngeval(jsonElement, hmOngeval, ongeval);
        ongeval.setOngevalKenmerken(hmOngeval);
        new WegVakController().findWegVakByVKL(ongeval);
        try {
            this.hmApi = new HmApi(ongeval.getWegNummer(), ongeval.getHectoMeter(), ongeval.getHectoLetter());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ongeval;
    }

    private void connectReferentieEnOngeval(JsonElement jsonElement, HashMap<String, String> hmOngeval, Ongeval ongeval) {
        String ongevalKey;
        String ongevalValue;
        for (Map.Entry entry : jsonElement.getAsJsonObject().entrySet()) {
            if (!entry.getValue().toString().matches("\"\"")) {
                String entryValue = entry.getValue().toString().replaceAll("\"", "").trim();
                ongevalKey = entry.getKey().toString().replaceAll("\"", "");
                try {
                    String entryKey= null;
                    for (String ref: ReferentieBestandCollectie.referentieBestanden.keySet()) {
                        if (entry.getKey().toString().startsWith(ref)){
                            entryKey = ref;
                        }
                    }
                    ongevalValue = ReferentieBestandCollectie.referentieBestanden
                            .get(entryKey)
                            .get(entryValue);
                    hmOngeval.put(ongevalKey, ongevalValue);
                } catch (NullPointerException e) {
                    hmOngeval.put(ongevalKey, entryValue);
                } finally {
                    switch (entry.getKey().toString()) {
                        case "WVK_ID":
                            ongeval.setWegVakId(entryValue.replaceAll("[\\D]0{0,2}", ""));
                            break;
                        case "HECTOMETER":
                            ongeval.setHectoMeter(entryValue);
                            break;
                        case "VKL_NUMMER":
                            ongeval.setVklNummer(entryValue);
                            break;
                    }
                }
            }
        }
    }

    public String[] findOngevalByHm(String weg, String hm, String toevoeging, String richting) throws IOException {
        this.hmApi = new HmApi(weg, hm, toevoeging);
        loadMap();
        double rd[] = hmApi.rd();
        List<JsonElement> jePuntLocaties = new PuntLocatieController().getPossiblePuntLocaties(hm, rd);
        PuntLocatie puntLocatie = new PuntLocatie();
        try {
            jePuntLocaties = new HectoPuntController().connectWvkIdToPuntlocatie(jePuntLocaties);
        } catch (NullPointerException e) {
            jePuntLocaties = hectoPuntDocumentMissing(jePuntLocaties);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JsonElement correctPuntLocatie = new WegVakController().narrowDownPuntLocaties(jePuntLocaties, toevoeging, richting);
            puntLocatie.setFkVeld5(correctPuntLocatie.getAsJsonObject().get("FK_VELD5").getAsString());
        }
        return StreamSupport.stream(this.jsonArray.spliterator(), false)
                .map(value -> (JsonObject) value)
                .filter(value -> value.get("FK_VELD5").getAsString().equals(puntLocatie.getFkVeld5()))
                .map(v -> v.get("VKL_NUMMER").getAsString())
                .toArray(String[]::new);

    }

    public double[] loadMap() {
        return this.hmApi.wgs84();
    }

    private List<JsonElement> hectoPuntDocumentMissing(List<JsonElement> jePuntLocaties) {
        for (JsonElement je : jePuntLocaties) {
            String value = je.getAsJsonObject().get("FK_VELD5").getAsString();
            value = value.replaceAll("HTT0+", "").replaceAll("\\d{4}$", "");
            je.getAsJsonObject().addProperty("WVK_ID", value);
        }
        return jePuntLocaties;
    }
}

