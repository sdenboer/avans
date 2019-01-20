package raas;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class AbstractParseJson implements IParseJson {

    @Override
    public abstract void parseJson(JsonObject jsonObject);

    @Override
    public JsonObject readJsonFile(Object object) throws IOException {
        JsonObject jsonObject = null;
        if (object instanceof URL) {
            HttpURLConnection conn = (HttpURLConnection) ((URL) object).openConnection();
            conn.setRequestMethod("GET");
            jsonObject =  new Gson().fromJson(new InputStreamReader(conn.getInputStream()), JsonObject.class);
            conn.disconnect();
        } else if (object instanceof File){
            jsonObject = new Gson().fromJson(new FileReader((File) object), JsonObject.class);
        }
        return jsonObject;
    }
}
