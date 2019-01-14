package pechpal.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import pechpal.AbstractParseJsonBestand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.Math.abs;

public class PuntLocatieController extends AbstractParseJsonBestand {
    private JsonArray jsonArray;

    public PuntLocatieController() throws IOException {
        super.fileName = "puntlocaties";
        super.filterBestand();
    }

    @Override
    public void parseJson(JsonObject jsonObject) {
        this.jsonArray = jsonObject.get(fileName).getAsJsonArray();
    }

    public List<JsonElement> getPossiblePuntLocaties(String hm, double locArray[]) {
        List<JsonElement> jePuntLocaties;
        jePuntLocaties =  StreamSupport.stream(this.jsonArray.spliterator(), false).map(value -> (JsonObject) value)
                .filter(value -> {
                    try {
                        double xvalue = locArray[0];
                        double yvalue = locArray[1];
                        return (value.get("FK_VELD5").getAsString().endsWith(hm)
                                && abs(value.get("Y_COORD").getAsDouble() - yvalue) < 100
                                && abs(value.get("X_COORD").getAsDouble() - xvalue) < 100);
                    } catch (Exception e) {
                        System.out.println("errorPuntLoc");
                        return false;
                    }
                }).collect(Collectors.toList());
        return jePuntLocaties;
    }
}
