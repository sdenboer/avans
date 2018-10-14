package techpal;


import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import techpal.Models.Device;

import java.util.ArrayList;

public class StuTabProf extends Tab {
    private GridPane grid;
    private Text lblUserName, txtUserName, lblTitle, lblPassword, lblName, lblZipCode, lblNumber, lblDevice, lblLevel;
    private PasswordField pwfPassword;
    private TextField tfdName, tfdZipCode, tfdNumber;
    private ComboBox<String> cbxLevel;
    private Button btn;
    private DbConnector conn;
    private ArrayList<CheckBox> listCheckbox;

    public  StuTabProf(StuMainView tabPane) {
        this.setText("Mijn Profiel");
        conn = new DbConnector();
        grid = new GridPane();
        listCheckbox = new ArrayList<>();
        lblTitle = new Text("Wijzig uw gegevens");
        lblUserName = new Text("Gebruikersnaam: ");
        txtUserName = new Text(Session.currentUser.getUserNm());
        lblPassword = new Text("Wachtwoord: ");
        lblName = new Text("Naam: ");
        lblZipCode = new Text("Postcode: ");
        lblNumber = new Text("Huisnummer: ");
        lblDevice = new Text("Toestellen: ");
        lblLevel = new Text("Niveau: ");
        btn = new Button("Wijzigen");

        pwfPassword = new PasswordField();
        tfdName = new TextField(Session.currentUser.getNm());
        tfdZipCode = new TextField(Session.currentUser.getPc());
        tfdNumber = new TextField(Session.currentStudent.getHnr());
        cbxLevel = new ComboBox<>();
        Session.listLevels.forEach(level -> {
            cbxLevel.getItems().add(level.getLvl());
        });
        cbxLevel.setValue(Session.currentStudent.getNiveau());

        for (int i = 0; i < Session.listDevices.size(); i++) {
            String tstl = Session.listDevices.get(i).getTstl();
            CheckBox checkBox = new CheckBox(tstl);
            grid.add(checkBox, i+1, 6); //adds a new device and checkbox to the grid
            listCheckbox.add(checkBox);
            Session.hasDevices.forEach(device -> {
                if (device.getTstl().equals(tstl)) {
                    checkBox.setSelected(true); //loops through the devices owned by the current user and checks them
                }
            });
        }

        btn.setOnAction(event -> {
            if (!pwfPassword.getText().isEmpty()
                    && tfdZipCode.getText().toUpperCase().matches("(\\d{4})\\s*([A-Z]{2})")
                    && !tfdNumber.getText().isEmpty()
                    && tfdName.getText().matches("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇČŠŽ ,.'-]+$")) {
                lblPassword.setFill(Color.BLACK);
                Session.currentUser.setPw(pwfPassword.getText());
                Session.currentUser.setNm(tfdName.getText());
                Session.currentUser.setPc(tfdZipCode.getText());
                Session.currentStudent.setHnr(tfdNumber.getText());
                Session.currentStudent.setNiveau(cbxLevel.getSelectionModel().getSelectedItem());
                String sqlUpdatePerson = "UPDATE personen " +
                        "SET pw = '"+Session.currentUser.getPw()+"', nm = '"+Session.currentUser.getNm()+"', " +
                        "pc = '"+Session.currentUser.getPc()+"', hnr = '"+Session.currentStudent.getHnr()+"', " +
                        "niveau_nivOm = '"+Session.currentStudent.getNiveau()+"' " +
                        "WHERE userNm = UPPER('"+Session.currentUser.getUserNm()+"')";
                System.out.println(sqlUpdatePerson);
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
        grid.add(lblTitle, 1, 0);
        grid.add(lblUserName, 0, 1);
        grid.add(txtUserName, 1, 1);
        grid.add(lblPassword, 0, 2);
        grid.add(pwfPassword, 1, 2);
        grid.add(lblName, 0, 3);
        grid.add(tfdName, 1, 3);
        grid.add(lblZipCode, 0, 4);
        grid.add(tfdZipCode, 1, 4);
        grid.add(lblNumber, 0, 5);
        grid.add(tfdNumber, 1, 5);
        grid.add(lblDevice, 0, 6);
        grid.add(lblLevel, 0, 7);
        grid.add(cbxLevel, 1, 7);
        grid.add(btn, 0, 8);
        this.setContent(grid);
    }

}
