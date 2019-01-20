package raas.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import raas.AbstractParseJsonBestand;
import java.util.List;
import java.util.stream.StreamSupport;

public class HectoPuntController extends AbstractParseJsonBestand {

    public HectoPuntController()  {
        super.filterBestand();
    }

    @Override
    public String fileName() {
        return "hectopunten";
    }

    public List<JsonElement> connectWvkIdToPuntlocatie(List<JsonElement> jePuntLocaties) {
        for (JsonElement je : jePuntLocaties) {
            JsonObject jo = StreamSupport.stream(this.jsonArray.spliterator(), false).map(value -> (JsonObject) value)
                    .filter(value -> {
                        try {
                            return value.get("FK_VELD5").equals(je.getAsJsonObject().get("FK_VELD5"));
                        } catch (Exception e) {
                            System.out.println("errHectoPuntController");
                            return false;
                        }
                    }).findFirst().get();
            je.getAsJsonObject().addProperty("WVK_ID",jo.get("WVK_ID").getAsString());
        }
        return jePuntLocaties;
    }
}
