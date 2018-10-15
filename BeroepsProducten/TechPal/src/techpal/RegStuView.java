package techpal;


import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.Models.Device;

import java.util.ArrayList;

public class RegStuView extends GridPane {
    private Text lblUserName, lblPassword, lblName, lblZipCode, lblNumber, lblLevel, lblError, lblDevices;
    private TextField tfdUserName, tfdName, tfdZipCode, tfdNumber;
    private PasswordField pwfPassword;
    private Button btnRegister;
    private ComboBox<String> cbxLvl;
    private DbConnector conn;
    private ArrayList<CheckBox> listCheckbox;

    public RegStuView(Pane body) {


        conn = new DbConnector();
        //ASSIGN labels and textfields
        lblUserName = new Text("Gebruikersnaam: ");
        tfdUserName = new TextField();
        lblPassword = new Text("Wachtwoord: ");
        pwfPassword = new PasswordField();
        lblName = new Text("Naam: ");
        tfdName = new TextField();
        lblZipCode = new Text("Postcode: ");
        tfdZipCode = new TextField();
        lblNumber = new Text("Huisnummer: ");
        tfdNumber = new TextField();
        btnRegister = new Button("Registeren");
        lblError = new Text("");
        lblLevel = new Text("Niveau: ");
        lblDevices = new Text("Ik bezit deze toestellen: ");
        listCheckbox = new ArrayList<>();

        //Fill the combobox with data from the database
        cbxLvl = new ComboBox<>();
        Session.listLevels.forEach(level -> {
            cbxLvl.getItems().add(level.getLvl());
        });
        cbxLvl.getSelectionModel().selectFirst();

        for (int i = 0; i < Session.listDevices.size(); i++) {
            String tstl = Session.listDevices.get(i).getTstl();
            CheckBox checkBox = new CheckBox(tstl);
            add(checkBox, i+1, 5); //adds a new device and checkbox to the grid
            listCheckbox.add(checkBox);
            Session.hasDevices.forEach(device -> {
                if (device.getTstl().equals(tstl)) {
                    checkBox.setSelected(true); //loops through the devices owned by the current user and checks them
                }
            });
        }

        btnRegister.setOnAction(event -> {
            //CHECK IF ZIP CODE IS NOT NULL AND LEGIT ACCORDING TO DUTCH STANDARDS AND IF THE NUMBER ISN'T EMPTY
            if (tfdZipCode.getText().toUpperCase().matches("(\\d{4})\\s*([A-Z]{2})")
                    && !tfdNumber.getText().isEmpty()
                    && tfdName.getText().matches("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇČŠŽ ,.'-]+$")) {
                lblError.setText("");
                Session.currentUser.setUserNm(tfdUserName.getText());
                Session.currentUser.setPw(pwfPassword.getText());
                Session.currentUser.setNm(tfdName.getText());
                Session.currentUser.setPc(tfdZipCode.getText());
                Session.currentStudent.setHnr(tfdNumber.getText());
                Session.currentStudent.setNiveau(cbxLvl.getSelectionModel().getSelectedItem());
                Session.currentUser.setRol("student");

                String sqlRegisterStudent = "INSERT INTO personen VALUES (UPPER('"+Session.currentUser.getUserNm()+"'),'"+Session.currentUser.getPw()+"','"+Session.currentUser.getNm()+"'," +
                        "UPPER('"+Session.currentUser.getPc()+"'),'"+Session.currentStudent.getHnr()+"','"+Session.currentStudent.getNiveau()+"', '"+Session.currentUser.getRol()+"')";
                int result = conn.executeDML(sqlRegisterStudent);
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
                this.getChildren().clear();
                body.getChildren().add(new StuMainView(this)); //opens the Student pane
            } else {
                lblError.setText("Er is een probleem met de invoergegevens");
            }
        });

        add(lblUserName,0,0);
        add(tfdUserName, 1, 0);
        add(lblPassword, 0, 1);
        add(pwfPassword, 1, 1);
        add(lblName, 0, 2);
        add(tfdName, 1, 2);
        add(lblZipCode, 0, 3);
        add(tfdZipCode, 1, 3);
        add(lblNumber, 0, 4);
        add(tfdNumber, 1, 4);
        add(lblDevices, 0, 5);
        add(lblLevel, 0, 6);
        add(cbxLvl, 1, 6);
        add(btnRegister, 0, 7);
        add(lblError, 0, 8);

        body.getChildren().add(this);
    }

}
