package pechpal;

import com.google.gson.JsonObject;
import pechpal.views.StartUpView;

import java.io.IOException;
import java.nio.file.Files;

public abstract class AbstractParseJsonBestand extends AbstractParseJson {
    protected String fileName;

    protected void filterBestand() throws IOException {
        Files.walk(StartUpView.pthTempDir)
            .filter(path -> path.toString().contains("/"+fileName) && path.toString().endsWith(".json"))
            .forEach(bestand -> {
                try {
                    JsonObject jsonObject = readJsonFile(bestand.toFile());
                    parseJson(jsonObject);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }
}
