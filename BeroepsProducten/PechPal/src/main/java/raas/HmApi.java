package raas;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.URL;
import java.util.Arrays;

public class HmApi extends AbstractParseJson{

    private String road, hmPaal, toevoeging, coord;
    private double locArray[];
    private URL url;
    private JsonObject jsonObject;

    public HmApi(String road, String hmPaal, String toevoeging) throws IOException {
        this.road = road;
        this.hmPaal = hmPaal;
        this.toevoeging = toevoeging;
        this.url = new URL(String.format(
                "https://geodata.nationaalgeoregister.nl/locatieserver/v3/free?q=%s-%s%s&fq=type:hectometerpaal&rows=1", road, hmPaal, toevoeging));
//        System.out.println(url);
        jsonObject = super.readJsonFile(url);
    }



    @Override
    public void parseJson(JsonObject jsonObject) {
        String check = jsonObject.getAsJsonObject("response").getAsJsonArray("docs").get(0).getAsJsonObject().get("identificatie").getAsString();
        if (String.format("%s-%s%s", road, hmPaal, toevoeging).equals(check)) {
            String locatie = jsonObject.getAsJsonObject("response").getAsJsonArray("docs").get(0).getAsJsonObject().get(coord).getAsString();
            String strLocArray[] = (locatie.replaceAll("[^\\d. ]", "").split(" "));
            locArray = Arrays.stream(strLocArray).mapToDouble(Double::parseDouble).toArray();
        }
    }

    public double[] wgs84() {
        this.coord = "centroide_ll";
        parseJson(this.jsonObject);
        double temp = locArray[0];
        locArray[0] = locArray[1];
        locArray[1] = temp;
        return locArray;
    }

    public double[] rd() {
        this.coord = "centroide_rd";
        parseJson(this.jsonObject);
        return locArray;
    }

}
