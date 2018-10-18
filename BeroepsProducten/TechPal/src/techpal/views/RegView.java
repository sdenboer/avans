package techpal.views;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.controllers.DbConnector;
import techpal.models.Device;
import techpal.controllers.Session;
import techpal.controllers.Statics;
import techpal.models.Person;
import techpal.models.Student;
import techpal.models.Tutor;

import java.util.ArrayList;

public class RegView extends GridPane {
    private Text title, lblUserName, lblPassword, lblName, lblZipCode, lblNumber, lblLevel, lblDevices;
    private TextField tfdUserName, tfdName, tfdZipCode, tfdNumber;
    private PasswordField pwfPassword;
    private Button btn;
    private ComboBox<String> cbxLvl;
    private DbConnector conn;
    private ArrayList<CheckBox> listCheckbox;
    private TabView tabView;
    private String sql; //this sql string is set within an if statement as the sql update depends on the role of the current user

    public RegView(AnchorPane body, Person user, TechPalNavBar navBar) {
        conn = new DbConnector();
        listCheckbox = new ArrayList<>();
        title = new Text("Welkom");
        title.setId("text-title");
        lblUserName = new Text("Gebruikersnaam: ");
        lblUserName.setId("text-label");
        tfdUserName = new TextField();
        tfdUserName.setId("text-label");
        lblPassword = new Text("Wachtwoord: ");
        lblPassword.setId("text-label");
        pwfPassword = new PasswordField();
        lblName = new Text("Naam: ");
        lblName.setId("text-label");
        tfdName = new TextField();
        tfdName.setId("text-label");
        lblZipCode = new Text("Postcode: ");
        lblZipCode.setId("text-label");
        tfdZipCode = new TextField();
        tfdZipCode.setId("text-label");
        lblDevices = new Text("");
        lblDevices.setId("text-label");
        btn = new Button("Registeren");

        int checkBoxRowInd; //this integer is set within the 'if' statement as the location of the checkboxes depends on the role.
        checkBoxRowInd = user instanceof Student ? 16 : 15;
        Statics.checkBoxPlacement(listCheckbox, checkBoxRowInd, this); //sets the checkboxes on the pane

        if (user instanceof Student) {
            lblNumber = new Text("Huisnummer: ");
            lblNumber.setId("text-label");
            tfdNumber = new TextField();
            tfdNumber.setId("text-label");
            lblLevel = new Text("Niveau: ");
            lblLevel.setId("text-label");
            lblDevices.setText("Toestellen: ");
            cbxLvl = new ComboBox<>(); //filling a combobox with the available lessons
            Session.listLevels.forEach(level -> cbxLvl.getItems().add(level.getLvl()));
            cbxLvl.getSelectionModel().selectFirst();
            this.add(lblNumber, 0, 15);
            this.add(tfdNumber, 1, 15);
            this.add(lblLevel, 0, checkBoxRowInd+listCheckbox.size());
            this.add(cbxLvl, 1, checkBoxRowInd+listCheckbox.size());
            this.add(btn, 0, checkBoxRowInd+listCheckbox.size()+1);
        } else { //if user is a tutor
            lblDevices.setText("Specializaties: ");
            this.add(btn, 0, checkBoxRowInd+listCheckbox.size());
        }

        btn.setOnAction(event -> {
            if (!pwfPassword.getText().isEmpty()
                    && tfdZipCode.getText().toUpperCase().matches("(\\d{4})\\s*([A-Z]{2})")
                    && (tfdNumber == null || !tfdNumber.getText().isEmpty()) //number is only necessary if current user is a student
                    && tfdName.getText().matches("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇČŠŽ ,.'-]+$")) {
                    setUser(user); //assign info to class
                int result = conn.executeDML(sql);
                listCheckbox.forEach(checkBox -> {
                    if (checkBox.isSelected()) {
                        Device device = new Device();
                        device.setTstl(checkBox.getText());
                        Session.hasDevices.add(device);
                        String sqlAddHasDevice = "INSERT INTO heeftToestellen VALUES (UPPER('"+user.getUserNm()+"'), " +
                                "'"+device.getTstl()+"')";
                        int resultAddHasDevice = conn.executeDML(sqlAddHasDevice);
                        if (user instanceof Tutor) {
                            Statics.setAvailableLessons();
                        }
                    }
                });
                tabView = new TabView(body, user);
                body.getChildren().clear();
                body.getChildren().add(tabView);
                navBar.loginSuccess();
            } else {
                Statics.alert("Oeps!", "Er is een probleem met de invoergegevens", Alert.AlertType.ERROR);
            }
        });

        Statics.setGrid(this); //applying some constraints for the grid
        this.add(title, 0, 0);
        this.add(lblUserName, 0, 11);
        this.add(tfdUserName, 1, 11);
        this.add(lblPassword, 0, 12);
        this.add(pwfPassword, 1, 12);
        this.add(lblName, 0, 13);
        this.add(tfdName, 1, 13);
        this.add(lblZipCode, 0, 14);
        this.add(tfdZipCode, 1, 14);
        this.add(lblDevices, 0, checkBoxRowInd);
        body.getChildren().add(this);
    }

    private void setUser(Person user) {
        user.setUserNm(tfdUserName.getText());
        user.setPw(pwfPassword.getText());
        user.setNm(tfdName.getText());
        user.setPc(tfdZipCode.getText());
        if (user instanceof Student) {
            user.setRol("student");
            ((Student) user).setHnr(tfdNumber.getText());
            ((Student) user).setNiveau(cbxLvl.getSelectionModel().getSelectedItem());
            sql = "INSERT INTO personen VALUES (UPPER('"+user.getUserNm()+"'), " +
                    "'"+user.getPw()+"','"+user.getNm()+"', " +
                    "UPPER('"+user.getPc()+"'),'"+((Student) user).getHnr()+"', " +
                    "'"+((Student) user).getNiveau()+"', '"+user.getRol()+"')";
        } else {
            user.setRol("tutor");
            sql = "INSERT INTO personen (userNm, pw, nm, pc, rollen_rol)" +
                    "VALUES (UPPER('"+user.getUserNm()+"'), '"+user.getPw()+"', " +
                    "'" +user.getNm()+ "', UPPER('"+user.getPc()+ "'), '"+user.getRol()+"')";
        }
    }

}
