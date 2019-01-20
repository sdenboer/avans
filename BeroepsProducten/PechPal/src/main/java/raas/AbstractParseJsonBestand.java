package pechpal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import pechpal.views.StartUpView;

import java.io.IOException;
import java.nio.file.Files;

public abstract class AbstractParseJsonBestand extends AbstractParseJson {
    protected JsonArray jsonArray;

    public abstract String fileName();

    protected void filterBestand() {
        try {
            Files.walk(StartUpView.pthTempDir)
                .filter(path -> path.toString().contains("/"+fileName()) && path.toString().endsWith(".json"))
                .forEach(bestand -> {
                    try {
                        JsonObject jsonObject = super.readJsonFile(bestand.toFile());
                        this.parseJson(jsonObject);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseJson(JsonObject jsonObject) {
        this.jsonArray = jsonObject.get(fileName()).getAsJsonArray();
    }
}
