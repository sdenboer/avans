package techpal;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import techpal.Models.Device;
import techpal.Models.Person;
import java.util.ArrayList;

public class TabProf extends Tab {
    private GridPane grid;
    private Text lblUserName, txtUserName, title, lblPassword, lblName, lblZipCode, lblNumber, lblDevices, lblLevel;
    private PasswordField pwfPassword;
    private TextField tfdName, tfdZipCode, tfdNumber;
    private ComboBox<String> cbxLevel;
    private Button btn;
    private DbConnector conn;
    private ArrayList<CheckBox> listCheckbox;

    public TabProf(Person currentUser, TabView tabPane) {
        this.setText("Mijn Profiel");
        conn = new DbConnector();
        grid = new GridPane();
        listCheckbox = new ArrayList<>();
        title = new Text("Wijzig uw gegevens");
        title.setId("text-title");
        lblUserName = new Text("Gebruikersnaam: ");
        lblUserName.setId("text-label");
        txtUserName = new Text(currentUser.getUserNm());
        txtUserName.setId("text-username");
        lblPassword = new Text("Wachtwoord: ");
        lblPassword.setId("text-label");
        pwfPassword = new PasswordField();
        lblName = new Text("Naam: ");
        lblName.setId("text-label");
        tfdName = new TextField(currentUser.getNm());
        lblZipCode = new Text("Postcode: ");
        lblZipCode.setId("text-label");
        tfdZipCode = new TextField(currentUser.getPc());
        lblDevices = new Text("");
        lblDevices.setId("text-label");
        btn = new Button("Wijzigen");

        int checkBoxRowInd; //this integer is set within the ternary statement as the location of the checkboxes depends on the role.
        String sql;  //this sql string is set within an if statement as the sql update depends on the role of the current user
        checkBoxRowInd = currentUser.getRol().equals("student") ? 16 : 15;
        Statics.checkBoxPlacement(listCheckbox, checkBoxRowInd, grid); //sets the checkboxes on the pane

        if (currentUser.getRol().equals("student")) {
            lblNumber = new Text("Huisnummer: ");
            lblNumber.setId("text-label");
            tfdNumber = new TextField(Session.currentStudent.getHnr());
            lblLevel = new Text("Niveau: ");
            lblLevel.setId("text-label");
            lblDevices.setText("Toestellen: ");
            cbxLevel = new ComboBox<>(); //filling a combobox with the available lessons
            Session.listLevels.forEach(level -> cbxLevel.getItems().add(level.getLvl()));
            cbxLevel.setValue(Session.currentStudent.getNiveau());
            sql = "UPDATE personen " +
                    "SET pw = '"+currentUser.getPw()+"', nm = '"+currentUser.getNm()+"', " +
                    "pc = UPPER('"+currentUser.getPc()+"'), hnr = '"+Session.currentStudent.getHnr()+"', " +
                    "niveau_nivOm = '"+Session.currentStudent.getNiveau()+"' " +
                    "WHERE userNm = UPPER('"+currentUser.getUserNm()+"')";
            grid.add(lblNumber, 0, 15);
            grid.add(tfdNumber, 1, 15);
            grid.add(lblLevel, 0, checkBoxRowInd+listCheckbox.size());
            grid.add(cbxLevel, 1, checkBoxRowInd+listCheckbox.size());
            grid.add(btn, 0, checkBoxRowInd+listCheckbox.size()+1);
        } else { //if the current user is a tutor
            lblDevices.setText("Specializaties: ");
            sql = "UPDATE personen " +
                    "SET pw = '"+currentUser.getPw()+"', nm = '"+currentUser.getNm()+"', " +
                    "pc = UPPER('"+currentUser.getPc()+"') " +
                    "WHERE userNm = UPPER('"+currentUser.getUserNm()+"')";
            grid.add(btn, 0, checkBoxRowInd+listCheckbox.size());
        }

        btn.setOnAction(event -> {
            if (!pwfPassword.getText().isEmpty()
                    && tfdZipCode.getText().toUpperCase().matches("(\\d{4})\\s*([A-Z]{2})")
                    && (tfdNumber == null || !tfdNumber.getText().isEmpty()) //number is only necessary if current user is a student.
                    && tfdName.getText().matches("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇČŠŽ ,.'-]+$")) {
                currentUser.setPw(pwfPassword.getText());
                currentUser.setPc(tfdZipCode.getText());
                currentUser.setNm(tfdName.getText());
                int resultUpdatePerson = conn.executeDML(sql);

                //I apologize for the next lines of code. I don't know how to do a merge insert in Oracle DB to avoid duplicate errors.
                //In the following nightmarish lines I delete the devices of the person, then add them again in a loop.
                //I'm sorry...
                String sqlDeleteHasDevice = "DELETE heeftToestellen WHERE persoon_user = UPPER('"+currentUser.getUserNm()+"')";
                int resultDeleteHasDevice = conn.executeDML(sqlDeleteHasDevice);
                Session.hasDevices.clear();
                listCheckbox.forEach(checkBox -> {
                    if (checkBox.isSelected()) {
                        Device device = new Device();
                        device.setTstl(checkBox.getText());
                        Session.hasDevices.add(device);
                        String sqlAddHasDevice = "INSERT INTO heeftToestellen VALUES (UPPER('"+currentUser.getUserNm()+"'), " +
                                "'"+device.getTstl()+"')";
                        int resultAddHasDevice = conn.executeDML(sqlAddHasDevice);
                    }
                });
                if (currentUser.getRol().equals("tutor")) { Statics.setAvailableLessons();} //resets the available lessons as the specializations might be changed
                //It's over. You can stop crying.
                Statics.alert("Succes!", "Uw gegevens zijn aangepast!", Alert.AlertType.INFORMATION);
                tabPane.getSelectionModel().select(0);
            } else {
                Statics.alert("Oeps!", "Er is een probleem met de invoergegevens", Alert.AlertType.ERROR);
            }
        });

        Statics.setGrid(grid);
        grid.add(title, 0, 0);
        grid.add(lblUserName, 0, 11);
        grid.add(txtUserName, 1, 11);
        grid.add(lblPassword, 0, 12);
        grid.add(pwfPassword, 1, 12);
        grid.add(lblName, 0, 13);
        grid.add(tfdName, 1, 13);
        grid.add(lblZipCode, 0, 14);
        grid.add(tfdZipCode, 1, 14);
        grid.add(lblDevices, 0, checkBoxRowInd);
        this.setContent(grid);
    }
}
