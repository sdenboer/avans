package raas;

import com.google.gson.JsonObject;

import java.io.IOException;

interface IParseJson {

    void parseJson(JsonObject jsonObject);
    JsonObject readJsonFile(Object object) throws IOException;

}
