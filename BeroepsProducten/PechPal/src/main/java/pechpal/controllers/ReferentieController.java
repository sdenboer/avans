package pechpal.controllers;

import com.google.gson.*;
import pechpal.AbstractParseJsonBestand;
import pechpal.models.Referentie;
import pechpal.ReferentieBestandCollectie;

import java.io.*;
import java.util.*;

public class ReferentieController extends AbstractParseJsonBestand {
    private String referentieBestandKey, value;
    private HashMap refHash;
    private String[] chosenValueColumn = {
            "OMS",
            "LNG"
    };

    public ReferentieController() throws IOException {
        super.fileName = "Referentie";
        super.filterBestand();
    }

    @Override
    public void parseJson(JsonObject jsonObject) {
        String entryKey = jsonObject.keySet().toString().replaceAll("[\\[\\]']", "");
        JsonArray jsonArray = jsonObject.getAsJsonArray(entryKey);
        String[] stringArray = jsonArray.get(0).getAsJsonObject().keySet().toString().replaceAll("[\\[\\]']", "").split(", ");
        referentieBestandKey = stringArray[0];
        setChosenValueColumn(stringArray);
        refHash = jsonToRefHash(jsonArray);
        ReferentieBestandCollectie.referentieBestanden.put(referentieBestandKey, refHash);
    }

    private void setChosenValueColumn(String[] sa) {
        if (sa.length > 2) {
            value = Arrays.stream(sa)
                    .filter(y -> Arrays.stream(chosenValueColumn)
                            .parallel()
                            .anyMatch(y::contains))
                    .findFirst().get();
        } else {
            value = sa[1];
        }
    }

    private HashMap<String, String> jsonToRefHash(JsonArray jsonArray){
        Referentie ref = new Referentie();
        HashMap<String, String> hmReferentie = new HashMap<>();
        for (JsonElement element : jsonArray) {
            JsonObject ob = element.getAsJsonObject();
            ref.setKey(ob.get(referentieBestandKey).getAsString());
            ref.setValue(ob.get(value).getAsString());
            hmReferentie.put(ref.getKey(), ref.getValue());
        }
        return hmReferentie;
    }
}