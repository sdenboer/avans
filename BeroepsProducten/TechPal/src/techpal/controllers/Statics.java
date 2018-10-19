package techpal.controllers;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.ColumnConstraints;
import org.json.JSONException;
import org.json.JSONObject;
import techpal.models.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringJoiner;
import static java.lang.Math.abs;
import javafx.scene.layout.GridPane;

public class Statics { //I'm saving all static methods in this file

    public Statics() {
    }

    public static void setPeriods() {
        DbConnector conn = new DbConnector();
        String sqlPer = "SELECT per FROM periodes";
        ResultSet resPer = conn.getData(sqlPer);
        try {
            while (resPer.next()) {
                Period period = new Period();
                period.setPer(resPer.getString("per"));
                Session.listPeriods.add(period);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLevels() {
        DbConnector conn = new DbConnector();
        String sqlLvl = "SELECT nivOm FROM niveaus";
        ResultSet resLvl = conn.getData(sqlLvl);
        try {
            while (resLvl.next()) {
                Level level = new Level();
                level.setLvl(resLvl.getString("nivOm"));
                Session.listLevels.add(level);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPrograms() {
        DbConnector conn = new DbConnector();
        String sqlProg = "SELECT progNm FROM programmas";
        ResultSet resProg = conn.getData(sqlProg);
        try {
            while (resProg.next()) {
                Program program = new Program();
                program.setProgNm(resProg.getString("progNm"));
                Session.listPrograms.add(program);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDevices() {
        DbConnector conn = new DbConnector();
        String sqlDev = "SELECT tstl FROM toestellen";
        ResultSet resDev = conn.getData(sqlDev);
        try {
            while (resDev.next()) {
                Device device = new Device();
                device.setTstl(resDev.getString("tstl"));
                Session.listDevices.add(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setHasDevices(Person user) {
        DbConnector conn = new DbConnector();
        String sqlDev = "SELECT toestel_tstl FROM heeftToestellen WHERE persoon_user = UPPER('"+user.getUserNm()+"')";
        ResultSet resDev = conn.getData(sqlDev);
        try {
            while (resDev.next()) {
                Device device = new Device();
                device.setTstl(resDev.getString("toestel_tstl"));
                Session.hasDevices.add(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLessons(Person user) {
        //This method sets the user specific lessons in the Lesson and Session classes.
        // Session is an arrayList of Lesson instances.
        DbConnector conn = new DbConnector();
        String role = user instanceof Student ? "stu" : "ttr";
        //checks whether the user is a student or a tutor
        //The following sql query joins the person table twice,
        // as I want to show the name of the tutor in the lesson tab view,
        // and not just the tutor's username. PS stands for PersoonStudent and PT means PersoonTutor
        String sqlLessen = "SELECT ps.usernm, ps.nm AS stuNm, ps.pc, ps.hnr, ps.niveau_nivom, l.stu, l.dtm, l.periode_per, " +
                "l.programma_prognm, l.isFin, l.tstl, l.ttr, pt.nm AS ttrNm " +
                "FROM personen ps " +
                "INNER JOIN lessen l ON(l.stu = ps.userNm) " +
                "LEFT OUTER JOIN personen pt ON(l.ttr = pt.usernm) " +
                "WHERE "+role+" = UPPER('"+user.getUserNm()+"')";
        ResultSet res = conn.getData(sqlLessen);
        try {
            while (res.next()) {
                Lesson lesson = new Lesson();
                lesson.setStu(res.getString("stu"));
                lesson.setStuNm(res.getString("stuNm"));
                lesson.setDtm(res.getDate("dtm").toLocalDate());
                lesson.setPer(res.getString("periode_per"));
                lesson.setProg(res.getString("programma_progNm"));
                lesson.setIsFin(res.getInt("isFin"));
                lesson.setTstl(res.getString("tstl"));
                lesson.setTtr(res.getString("ttr"));
                lesson.setStuNiv(res.getString("niveau_nivOm"));
                lesson.setTtrNm(res.getString("ttrNm"));
                lesson.setStuPc((res.getString("pc")));
                lesson.setStuHnr(res.getString("hnr"));
                if (lesson.getIsFin() == 0) {
                    Session.oblLessons.add(lesson);
                } else {
                    Session.oblPrevLessons.add(lesson);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void alert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void checkBoxPlacement(ArrayList<CheckBox> listCheckbox, int checkBoxRowInd, GridPane grid) {
        for (int i = 0; i < Session.listDevices.size(); i++) {
            String tstl = Session.listDevices.get(i).getTstl();
            CheckBox checkBox = new CheckBox(tstl);
            checkBox.setId("text-label");
            grid.add(checkBox, 1, checkBoxRowInd+i); //adds a new device and checkbox to the grid
            listCheckbox.add(checkBox);
            Session.hasDevices.forEach(device -> {
                if (device.getTstl().equals(tstl)) {
                    checkBox.setSelected(true); //loops through the devices owned by the current user and checks them
                }
            });
        }
    }

    public static void setGrid(GridPane grid){
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setMinHeight(800);
        grid.setMinWidth(800);
        grid.getColumnConstraints().add(new ColumnConstraints(200));
    }

    public static void openMap(String zipcode, String number) {
        //I only made this feature to challenge myself. Basically, it's an API call + JSON parser using a Dutch
        //zip code to address service. I signed up for the free subsciption
        try {
            FileReader reader = new FileReader("config");
            Properties props = new Properties();
            props.load(reader);
            String apiKey = props.getProperty("xApiKey");
            URL apiUrl = new URL("https://api.postcodeapi.nu/v2/addresses/?postcode="+zipcode+"");
            System.out.println(apiUrl);
            HttpURLConnection conn = (HttpURLConnection)apiUrl.openConnection(); //typecasting to an http
            conn.setRequestMethod("GET");  //We're GETting information
            conn.setRequestProperty("x-api-key", apiKey);
            conn.connect();
            int result = conn.getResponseCode();
            if (result == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject jsonObject = new JSONObject(response.toString());
                String street = jsonObject.getJSONObject("_embedded").getJSONArray("addresses").getJSONObject(0).getJSONObject("nen5825").getString("street");
                new Thread(() -> {
                    try {
                        Desktop.getDesktop().browse(new URI( "https://www.google.com/maps?q="+street+"+"+number+"" ));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                System.out.println(result);
                Statics.alert("Oeps!", "Er is iets foutgegaan", Alert.AlertType.ERROR);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setAvailableLessons() {
        Session.oblAvailableLessons.clear();
        DbConnector conn = new DbConnector();
        StringJoiner strjDev = new StringJoiner("', '", "'", "'");
        Session.hasDevices.forEach(device -> {
            strjDev.add(device.getTstl());
        });
        String sqlAv = "SELECT p.nm, p.pc, p.hnr, p.niveau_nivom, l.stu, l.dtm, l.periode_per, l.programma_prognm, " +
                "l.isFin, l.tstl " +
                "FROM lessen l " +
                "JOIN personen p ON(p.userNm = l.stu) " +
                "WHERE tstl IN ("+strjDev+")" +
                "AND ttr IS NULL " +
                "AND l.isFin = 0";
        ResultSet resAv = conn.getData(sqlAv);
        try {
            while (resAv.next()) {
                Lesson lesson = new Lesson();
                lesson.setStu(resAv.getString("stu"));
                lesson.setStuNm(resAv.getString("nm"));
                lesson.setStuPc(resAv.getString("pc"));;
                lesson.setStuHnr(resAv.getString("hnr"));
                lesson.setStuNiv(resAv.getString("niveau_nivom"));
                lesson.setDtm(resAv.getDate("dtm").toLocalDate());
                lesson.setPer(resAv.getString("periode_per"));
                lesson.setProg(resAv.getString("programma_progNm"));
                lesson.setIsFin(resAv.getInt("isFin"));
                lesson.setTstl(resAv.getString("tstl"));
                Session.oblAvailableLessons.add(lesson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Reordering the zip codes from nearest to furthest from the home address of the currentUser.
        int zipCode = Integer.parseInt(Session.currentTutor.getPc().replaceAll("[\\D]", ""));
        for (int i = 0; i<Session.oblAvailableLessons.size(); i++) {
            for (int j = 1; j <Session.oblAvailableLessons.size()-i; j++) {
                int intFirst = abs(Integer.parseInt(Session.oblAvailableLessons.get(j-1).getStuPc().replaceAll("[\\D]", ""))-zipCode);
                int intSecond = abs(Integer.parseInt(Session.oblAvailableLessons.get(j).getStuPc().replaceAll("[\\D]", ""))-zipCode);
                if (intFirst > intSecond) {
                    Lesson temp = Session.oblAvailableLessons.get(j-1);
                    Session.oblAvailableLessons.set(j-1, Session.oblAvailableLessons.get(j));
                    Session.oblAvailableLessons.set(j, temp);
                }
            }
        }
//        for (int i = 0; i < Session.oblAvailableLessons.size(); i++) {
//            System.out.println(Session.oblAvailableLessons.get(i).getStuPc());
//        }

        //The code below was my test code. It's basically explaining what I'm doing above. I'm using a bubble sort algorithm to sort the array from nearest to
        //farthest
//
//        int nr = 3471;
//        int[] arr = new int[] {1111, 2222, 2222, 2222, 2222, 3333, 4444, 7771, 7771, 7771, 7771, 7812, 7712, 6666, 7777, 8888};
//        for (int i = 0; i<arr.length; i++) {
//            for (int j = 1; j<arr.length -i; j++) {
//                int firstNumber = abs(arr[j-1] - nr);
//                int secondNumber = abs(arr[j] - nr);
//                if (firstNumber > secondNumber ) {
//                    int temp = arr[j-1];
//                    arr[j-1] = arr[j];
//                    arr[j] = temp;
//                }
//            }
//        }
//        System.out.println(arr.length);
//        System.out.println(Arrays.toString(arr));
//        System.out.println(Session.oblAvailableLessons.size());
    }
}
