package techpal.controllers;

import javafx.scene.control.Alert;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class MapsController {

    public MapsController() {
    }

    public static void openMap(String zipcode, String number) {
        //Unfortunately, entering a zip-code + number in google maps won't give you an actual address.
        //I only made this feature to challenge myself. Basically, it's an API call + JSON parser using a Dutch
        //zip code to address service. I signed up for the free subscription.
        //After implementing the feature, I tried replacing AWT Desktop by JavaFX, but this seems too time consuming.
        //It should be possible by accessing the startup TechPal class extending Application in this class, then adding
        //startupTechPalClass.getHostServices().showDocument("the url to google maps");
        try {
            FileReader reader = new FileReader("config"); //reading the config file with the key needed for the api call
            Properties props = new Properties();
            props.load(reader); //loading the properties of the reader
            String apiKey = props.getProperty("xApiKey"); //getting the api key and saving it in a string, to be used for the api call
            URL apiUrl = new URL("https://api.postcodeapi.nu/v2/addresses/?postcode="+zipcode+"");
            //using a Dutch API service for converting zip codes into addresses
            HttpURLConnection conn = (HttpURLConnection)apiUrl.openConnection(); //typecasting to http and opening a connection
            conn.setRequestMethod("GET");  //We're GETting information
            conn.setRequestProperty("x-api-key", apiKey); //apiKey is coming from the config file
            conn.connect(); //connecting to the api service
            int apiResponse = conn.getResponseCode();
            System.out.print("API postcodeapi.nu RESPONSE: ");
            System.out.println(apiResponse); //429 will likely pop up if the Api Key has been used too often (only 100 per day)
            if (apiResponse == 200) { //200 means everything went fine and we got a result
                BufferedReader in = new BufferedReader( //reads characters from a stream
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder(); //gotta build string from the response. hard to read otherwise
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine); //adding the inputline string to the stringbuilding
                }
                in.close();
                JSONObject jsonObject = new JSONObject(response.toString()); //we got an object from the stringbuilder
                String street = jsonObject.getJSONObject("_embedded").getJSONArray("addresses").getJSONObject(0).getJSONObject("nen5825").getString("street");
                new Thread(() -> { //I had to initiate a new thread, otherwise the program would crash.
                    try {
                        String zipcodeNr = zipcode.replaceAll("[\\D]", ""); //deleting the characters after the zip code numbers
                        Desktop.getDesktop().browse(new URI( "https://www.google.com/maps?q="+street+"+"+number+"+"+zipcodeNr+"")); //opening the default browser
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }).start(); //starts the new thread
            } else {
                BaseController.alert("Oeps!", "Er is iets foutgegaan met het openen van de map", Alert.AlertType.ERROR);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            BaseController.alert("Oeps!", "Er is iets foutgegaan", Alert.AlertType.ERROR);
        }
    }
}
