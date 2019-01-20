package raas.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import raas.AbstractParseJsonBestand;
import raas.models.Ongeval;

import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

public class WegVakController extends AbstractParseJsonBestand {

    public WegVakController() {
        super.filterBestand();
    }

    @Override
    public String fileName() {
        return "wegvakken";
    }

    public void findWegVakByVKL(Ongeval ongeval) {
        JsonElement jsonElement = StreamSupport.stream(jsonArray.spliterator(), false)
                .map(value -> (JsonObject) value)
                .filter(value -> value.get("WVK_ID").getAsString().equals(ongeval.getWegVakId()))
                .findFirst().get();
        for (Map.Entry entry : jsonElement.getAsJsonObject().entrySet()) {
            String value = entry.getValue()
                    .toString()
                    .replace("\"", "")
                    .trim();
            String key = entry.getKey()
                    .toString()
                    .replace("\"", "");
            if (key.equals("WEGNUMMER")) {
                if (value.matches("^\\d*")) {
                    value = "A" + value.replaceAll("^0{1,2}", "");
                }
                ongeval.setWegNummer(value);
            } else if (key.equals("HECTOLTTR")) {
                ongeval.setHectoLetter(value);
            } else if (key.equals("RPE_CODE")) {
                ongeval.setRichting(value);
            }
        }
    }

    public JsonElement narrowDownPuntLocaties(List<JsonElement> jePuntLocaties, String toevoeging, String richting) {
        jePuntLocaties.removeIf(je -> StreamSupport.stream(this.jsonArray.spliterator(), false)
                .map(value -> (JsonObject) value)
                .noneMatch(value -> (value.get("WVK_ID").equals(je.getAsJsonObject().get("WVK_ID"))
                        && (value.get("HECTOLTTR").getAsString().equals(toevoeging))
                        && (value.get("RPE_CODE").getAsString().equals(richting)))));
        try {
            return jePuntLocaties.stream().findFirst().get().getAsJsonObject();
        } catch (IndexOutOfBoundsException iob) {
            throw iob;
        }
    }
}
