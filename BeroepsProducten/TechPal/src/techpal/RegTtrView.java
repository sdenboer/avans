package techpal;


import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.Models.Device;

import java.util.ArrayList;


public class RegTtrView extends GridPane {
    private Text lblUserName, lblPassword, lblName, lblZipCode, lblDevices;
    private TextField tfdUserName, tfdName, tfdZipCode;
    private PasswordField pwfPassword;
    private Button btnRegister;
    private ArrayList<CheckBox> listCheckbox;
    private DbConnector conn;
    private TtrMainView ttrMainView;

    public RegTtrView(AnchorPane body) {
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.setMinHeight(800);
        this.setMinWidth(800);
        this.getColumnConstraints().add(new ColumnConstraints(200));

        conn = new DbConnector();
        //ASSIGN labels and textfields
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
        lblDevices = new Text("Specializaties: ");
        lblDevices.setId("text-label");
        listCheckbox = new ArrayList<>();
        btnRegister = new Button("Registeren");

        for (int i = 0; i < Session.listDevices.size(); i++) {
            String tstl = Session.listDevices.get(i).getTstl();
            CheckBox checkBox = new CheckBox(tstl);
            add(checkBox, 1, 4+i); //adds a new device and checkbox to the grid
            listCheckbox.add(checkBox);
            checkBox.setId("text-label");
            Session.hasDevices.forEach(device -> {
                if (device.getTstl().equals(tstl)) {
                    checkBox.setSelected(true); //loops through the devices owned by the current user and checks them
                }
            });
        }

        btnRegister.setOnAction(event -> {
            if (tfdZipCode.getText().toUpperCase().matches("(\\d{4})\\s*([A-Z]{2})")
                    && tfdName.getText().matches("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇČŠŽ ,.'-]+$")) {
                Session.currentUser.setUserNm(tfdUserName.getText());
                Session.currentUser.setPw(pwfPassword.getText());
                Session.currentUser.setNm(tfdName.getText());
                Session.currentUser.setPc(tfdZipCode.getText());
                Session.currentUser.setRol("tutor");

                String sqlRegisterTutor = "INSERT INTO personen (userNm, pw, nm, pc, rollen_rol)" +
                        "VALUES (UPPER('"+Session.currentUser.getUserNm()+"'), '"+Session.currentUser.getPw()+"', " +
                        "'" +Session.currentUser.getNm()+ "', UPPER('"+Session.currentUser.getPc()+ "'), '"+Session.currentUser.getRol()+"')";
                int result = conn.executeDML(sqlRegisterTutor);

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
                ttrMainView = new TtrMainView(body);
                body.getChildren().clear();
                body.getChildren().add(ttrMainView);
//                this.getChildren().clear();
//                body.getChildren().add(new TtrMainView(this)); //opens the Student pane
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
        add(lblDevices, 0, 4);
        add(btnRegister, 0, 7);
        body.getChildren().add(this);
    }
}
