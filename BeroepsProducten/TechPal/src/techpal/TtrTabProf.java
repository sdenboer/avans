package techpal;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import techpal.Models.Device;
import java.util.ArrayList;


public class TtrTabProf extends Tab {
    private GridPane grid;
    private Text lblUserName, txtUserName, title, lblPassword, lblName, lblZipCode, lblNumber, lblDevices, lblLevel;
    private PasswordField pwfPassword;
    private TextField tfdName, tfdZipCode;
    private Button btn;
    private DbConnector conn;
    private ArrayList<CheckBox> listCheckbox;

    public  TtrTabProf(TtrMainView tabPane) {
        this.setText("Mijn Profiel");
        conn = new DbConnector();
        grid = new GridPane();
        listCheckbox = new ArrayList<>();
        title = new Text("Wijzig uw gegevens");
        title.setId("text-title");
        lblUserName = new Text("Gebruikersnaam: ");
        lblUserName.setId("text-label");
        txtUserName = new Text(Session.currentUser.getUserNm());
        txtUserName.setId("text-username");
        lblPassword = new Text("Wachtwoord: ");
        lblPassword.setId("text-label");
        lblName = new Text("Naam: ");
        lblName.setId("text-label");
        lblZipCode = new Text("Postcode: ");
        lblZipCode.setId("text-label");
        lblDevices = new Text("Specializaties: ");
        lblDevices.setId("text-label");
        btn = new Button("Wijzigen");
        pwfPassword = new PasswordField();
        tfdName = new TextField(Session.currentUser.getNm());
        tfdZipCode = new TextField(Session.currentUser.getPc());

        for (int i = 0; i < Session.listDevices.size(); i++) {
            String tstl = Session.listDevices.get(i).getTstl();
            CheckBox checkBox = new CheckBox(tstl);
            grid.add(checkBox, 1, 15+i); //adds a new device and checkbox to the grid
            listCheckbox.add(checkBox);
            checkBox.setId("text-label");
            Session.hasDevices.forEach(device -> {
                if (device.getTstl().equals(tstl)) {
                    checkBox.setSelected(true); //loops through the devices owned by the current user and checks them
                }
            });
        }

        btn.setOnAction(event -> {
            if (!pwfPassword.getText().isEmpty()
                    && tfdZipCode.getText().toUpperCase().matches("(\\d{4})\\s*([A-Z]{2})")
                    && tfdName.getText().matches("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇČŠŽ ,.'-]+$")) {
                lblPassword.setFill(Color.BLACK);
                Session.currentUser.setPw(pwfPassword.getText());
                Session.currentUser.setNm(tfdName.getText());
                Session.currentUser.setPc(tfdZipCode.getText());
                String sqlUpdatePerson = "UPDATE personen " +
                        "SET pw = '"+Session.currentUser.getPw()+"', nm = '"+Session.currentUser.getNm()+"', " +
                        "pc = UPPER('"+Session.currentUser.getPc()+"') " +
                        "WHERE userNm = UPPER('"+Session.currentUser.getUserNm()+"')";
                int resultUpdatePerson = conn.executeDML(sqlUpdatePerson);

                //I apologize for the next lines of code. I don't know how to do a merge insert in Oracle DB to avoid duplicate errors.
                //In the following nightmarish queries I delete the devices of the person, then add them again in a loop.
                //I'm sorry...
                String sqlDeleteHasDevice = "DELETE heeftToestellen WHERE persoon_user = UPPER('"+Session.currentUser.getUserNm()+"')";
                int resultDeleteHasDevice = conn.executeDML(sqlDeleteHasDevice);
                Session.hasDevices.clear();
                listCheckbox.forEach(checkBox -> {
                    if (checkBox.isSelected()) {
                        Device device = new Device();
                        device.setTstl(checkBox.getText());
                        Session.hasDevices.add(device);
                        String sqlAddHasDevice = "INSERT INTO heeftToestellen VALUES (UPPER('"+Session.currentUser.getUserNm()+"'), " +
                                "'"+device.getTstl()+"')";
                        int resultAddHasDevice = conn.executeDML(sqlAddHasDevice);
                    }
                });
                initStage.setAvailableLessons();
                //It's over. You can stop crying.
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("TechPal");
                alert.setHeaderText(null);
                alert.setContentText("Uw gegevens zijn aangepast!");
                alert.showAndWait();
                tabPane.getSelectionModel().select(0);

            } else {
                lblPassword.setFill(Color.RED);
            }
        });

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setMinHeight(800);
        grid.setMinWidth(800);
        grid.getColumnConstraints().add(new ColumnConstraints(200));


        grid.add(title, 0, 0);
        grid.add(lblUserName, 0, 11);
        grid.add(txtUserName, 1, 11);
        grid.add(lblPassword, 0, 12);
        grid.add(pwfPassword, 1, 12);
        grid.add(lblName, 0, 13);
        grid.add(tfdName, 1, 13);
        grid.add(lblZipCode, 0, 14);
        grid.add(tfdZipCode, 1, 14);
        grid.add(lblDevices, 0, 15);
        grid.add(btn, 0, 18);
        this.setContent(grid);
    }
}

