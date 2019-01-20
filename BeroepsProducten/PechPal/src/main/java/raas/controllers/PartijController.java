package pechpal.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import pechpal.AbstractParseJsonBestand;
import pechpal.models.Partij;
import pechpal.ReferentieBestandCollectie;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

public class PartijController extends AbstractParseJsonBestand {

    public PartijController() {
        super.filterBestand();
    }

    @Override
    public String fileName() {
        return "partijen";
    }

    public Partij findPartijByVKL(String nummer) {
        Partij partij = new Partij();
        HashMap<String, String> hmPartij = new HashMap<>();
        try {
            JsonElement jsonElement = StreamSupport.stream(this.jsonArray.spliterator(), false)
                    .map(value -> (JsonObject) value)
                    .filter(value -> {
                        try {
                            return value.get("VKL_NUMMER").getAsString().equals(nummer);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                    }).findFirst()
                    .orElseThrow(NoSuchElementException::new);
        connectReferentieEnPartij(jsonElement, hmPartij);
        partij.setPartijKenmerken(hmPartij);
        } catch (NoSuchElementException e) {
            return null; //geen partij
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partij;
    }


    private void connectReferentieEnPartij(JsonElement je, HashMap<String, String> hmPartij) {
        String partijKey;
        String partijValue;
        for (Map.Entry entry : je.getAsJsonObject().entrySet()) {
            if (!entry.getValue().toString().matches("\"\"")) {
                String entryValue = entry.getValue().toString().replaceAll("\"", "").trim();
                partijKey = entry.getKey().toString().replaceAll("\"", "");
                try {
                    String entryKey = "";
                    for (String ref : ReferentieBestandCollectie.referentieBestanden.keySet()) {
                        if (entry.getKey().toString().startsWith(ref)) {
                            entryKey = ref;
                        }
                    }
                    partijValue = ReferentieBestandCollectie.referentieBestanden
                            .get(entryKey)
                            .get(entryValue);
                    hmPartij.put(partijKey, partijValue);
                } catch (NullPointerException e) {
                    hmPartij.put(partijKey, entryValue);
                }
            }
        }
    }
}



