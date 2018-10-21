package techpal.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import techpal.models.Device;
import techpal.models.Person;
import techpal.models.Tutor;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DevicesController {
    private static DbConnector conn;

    public DevicesController() {
    }

    public static ArrayList<Device> alDevices = new ArrayList<>();
    public static ObservableList<Device> olHasDevices = FXCollections.observableArrayList();

    public static void setDevices() {
        //this method sets the devices recognized by TechPal then adds these to an ObservableArrayList
        conn = new DbConnector();
        String sql = "SELECT tstl FROM toestellen";
        ResultSet resDev = conn.getData(sql);
        try {
            while (resDev.next()) {
                Device device = new Device();
                device.setTstl(resDev.getString("tstl"));
                DevicesController.alDevices.add(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setHasDevices(Person user) {
        //this method checks which devices the user has, then adds these to an ObservableArrayList
        conn = new DbConnector();
        String sql = "SELECT toestel_tstl FROM heeftToestellen WHERE persoon_user = UPPER('"+user.getUserNm()+"')";
        ResultSet resDev = conn.getData(sql);
        try {
            while (resDev.next()) {
                Device device = new Device();
                device.setTstl(resDev.getString("toestel_tstl"));
                DevicesController.olHasDevices.add(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void devicesCheckBoxPlacement(ArrayList<CheckBox> listCheckbox, int checkBoxRowInd, GridPane grid) {
        for (int i = 0; i < DevicesController.alDevices.size(); i++) {
            String tstl = DevicesController.alDevices.get(i).getTstl();
            CheckBox checkBox = new CheckBox(tstl);
            checkBox.setId("text-label");
            grid.add(checkBox, 1, checkBoxRowInd+i); //adds a new device and checkbox to the grid
            listCheckbox.add(checkBox);
            DevicesController.olHasDevices.forEach(device -> {
                if (device.getTstl().equals(tstl)) {
                    checkBox.setSelected(true); //loops through the devices owned by the current user and checks them
                }
            });
        }
    }

    public static void setHasDevicesNewUser(ArrayList<CheckBox> alCheckBox, Person user) { //inserts a new device
        conn = new DbConnector();
        alCheckBox.forEach(checkBox -> {
            if (checkBox.isSelected()) {
                Device device = new Device();
                device.setTstl(checkBox.getText());
                DevicesController.olHasDevices.add(device);
                String sql = "INSERT INTO heeftToestellen VALUES (UPPER('"+user.getUserNm()+"'), " +
                        "'"+device.getTstl()+"')";
                int result = conn.executeDML(sql);
            }
            if (user instanceof Tutor) {
                LessonsController.setAvailableLessons();
            } //resets the available lessons as the specializations might be changed
        });
    }

    public static void deleteAndUpdateHasDevices(Person user, ArrayList<CheckBox> listCheckbox){
        conn = new DbConnector();
        //I apologize for the next lines of code. I don't know how to do a merge insert in Oracle DB to avoid duplicate errors.
        //In the following nightmarish lines I delete the devices of the person, then add them again in a loop.
        //I'm sorry...
        String sql = "DELETE heeftToestellen WHERE persoon_user = UPPER('"+user.getUserNm()+"')";
        int result = conn.executeDML(sql);
        DevicesController.olHasDevices.clear();
        setHasDevicesNewUser(listCheckbox, user);
        //It's over. You can stop crying.
    }
}
