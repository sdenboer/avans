package techpal;


import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.Models.Device;

import java.util.ArrayList;

public class RegStuView extends GridPane {
    private Text lblUserName, lblPassword, lblName, lblZipCode, lblNumber, lblLevel, lblDevices;
    private TextField tfdUserName, tfdName, tfdZipCode, tfdNumber;
    private PasswordField pwfPassword;
    private Button btnRegister;
    private ComboBox<String> cbxLvl;
    private DbConnector conn;
    private ArrayList<CheckBox> listCheckbox;
    private StuMainView stuMainView;

    public RegStuView(AnchorPane body) {
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.setMinHeight(800);
        this.setMinWidth(800);
        this.getColumnConstraints().add(new ColumnConstraints(200));

        conn = new DbConnector();
        lblUserName = new Text("Gebruikersnaam: ");
        lblUserName.setId("text-label");
        tfdUserName = new TextField();
        lblPassword = new Text("Wachtwoord: ");
        lblPassword.setId("text-label");
        pwfPassword = new PasswordField();
        lblName = new Text("Naam: ");
        lblName.setId("text-label");
        tfdName = new TextField();
        lblZipCode = new Text("Postcode: ");
        lblZipCode.setId("text-label");
        tfdZipCode = new TextField();
        lblNumber = new Text("Huisnummer: ");
        lblNumber.setId("text-label");
        tfdNumber = new TextField();
        btnRegister = new Button("Registeren");
        lblLevel = new Text("Niveau: ");
        lblLevel.setId("text-label");
        lblDevices = new Text("Ik bezit deze toestellen: ");
        lblDevices.setId("text-label");
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
            checkBox.setId("text-label");
            add(checkBox, 1, 5+i); //adds a new device and checkbox to the grid
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
//                body.getChildren().clear();
//                this.getChildren().add(new StuMainView(body)); //opens the Student pane
                stuMainView = new StuMainView(body);
                body.getChildren().clear();
                body.getChildren().add(stuMainView);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR); //if the sql query can't find anyone, this window pops up to alert the user.
                alert.setTitle("Oeps!");
                alert.setHeaderText(null);
                alert.setContentText("Er is een probleem met de invoergegevens");
                alert.showAndWait();
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
        add(lblLevel, 0, 8);
        add(cbxLvl, 1, 8);
        add(btnRegister, 0, 9);
        body.getChildren().add(this);
    }
}
