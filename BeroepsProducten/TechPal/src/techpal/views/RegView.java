package techpal.views;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.controllers.*;
import techpal.models.Person;
import techpal.models.Student;

import java.util.ArrayList;

public class RegView extends GridPane {
    private Text title, lblUserName, lblPassword, lblName, lblZipCode, lblNumber, lblLevel, lblDevices;
    private TextField tfdUserName, tfdName, tfdZipCode, tfdNumber;
    private PasswordField pwfPassword;
    private Button btn;
    private ComboBox<String> cbxLvl;
    private ArrayList<CheckBox> alCheckBox;
    private TabView tabView;

    public RegView(AnchorPane body, Person user, TechPalNavBar navBar) {
        alCheckBox = new ArrayList<>();
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
        checkBoxRowInd = user instanceof Student ? 16 : 15; //student has an extra row
        DevicesController.devicesCheckBoxPlacement(alCheckBox, checkBoxRowInd, this); //sets the checkboxes on the pane

        if (user instanceof Student) {
            lblNumber = new Text("Huisnummer: ");
            lblNumber.setId("text-label");
            tfdNumber = new TextField();
            tfdNumber.setId("text-label");
            lblLevel = new Text("Niveau: ");
            lblLevel.setId("text-label");
            lblDevices.setText("Toestellen: ");
            cbxLvl = new ComboBox<>(); //filling a combobox with the available lessons
            LevelsController.alLevels.forEach(level -> cbxLvl.getItems().add(level.getLvl()));
            cbxLvl.getSelectionModel().selectFirst(); //default selection in combobox
            this.add(lblNumber, 0, 15);
            this.add(tfdNumber, 1, 15);
            this.add(lblLevel, 0, checkBoxRowInd+alCheckBox.size());
            this.add(cbxLvl, 1, checkBoxRowInd+alCheckBox.size());
            this.add(btn, 0, checkBoxRowInd+alCheckBox.size()+1);
        } else { //if user is a tutor
            lblDevices.setText("Specializaties: ");
            this.add(btn, 0, checkBoxRowInd+alCheckBox.size());
        }

        btn.setOnAction(event -> {
            if (!pwfPassword.getText().isEmpty()
                    && tfdZipCode.getText().toUpperCase().matches("(\\d{4})\\s*([A-Z]{2})")
                    && (tfdNumber == null || !tfdNumber.getText().isEmpty()) //number is only necessary if current user is a student
                    && tfdName.getText().matches("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇČŠŽ ,.'-]+$")) {
                    PersonsController.setNewUser(user, tfdUserName.getText(), pwfPassword.getText(), tfdName.getText(), tfdZipCode.getText()); //assign general info to class
                    if (user instanceof Student) {
                        PersonsController.setNewStudent(user, tfdNumber.getText(), cbxLvl.getSelectionModel().getSelectedItem());
                    } else {
                        PersonsController.setNewTutor(user);
                    }
                    DevicesController.setHasDevicesNewUser(alCheckBox, user);
                tabView = new TabView(body, user);
                body.getChildren().clear();
                body.getChildren().add(tabView);
                navBar.loginSuccess();
            } else {
                BaseController.alert("Oeps!", "Er is een probleem met de invoergegevens", Alert.AlertType.ERROR);
            }
        });

        BaseController.setGrid(this); //applying some constraints for the grid
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



}
