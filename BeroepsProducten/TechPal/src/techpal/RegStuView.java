package techpal;


import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegStuView extends GridPane {
    private Text lblUserName, lblPassword, lblName, lblZipCode, lblNumber, lblLevel, lblError;
    private TextField tfdUserName, tfdName, tfdZipCode, tfdNumber;
    private PasswordField pwfPassword;
    private Button btnRegister;
    private ComboBox<String> cbxLevel;
    private DbConnector conn;

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

        //Fill the combobox with data from the database
        cbxLevel = new ComboBox<>();
        String sqlLevel = "SELECT nivOm FROM niveaus";
        ResultSet resLevel = conn.getData(sqlLevel);
        try {
            while (resLevel.next()) {
                String strLevel = resLevel.getString("nivOm");
                cbxLevel.getItems().add(strLevel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                Session.currentStudent.setNiveau(cbxLevel.getSelectionModel().getSelectedItem());

                String sqlRegisterStudent = "INSERT INTO personen VALUES ('"+Session.currentUser.getUserNm()+"','"+Session.currentUser.getPw()+"','"+Session.currentUser.getNm()+"'," +
                        "'"+Session.currentUser.getPc()+"','"+Session.currentStudent.getHnr()+"','"+Session.currentStudent.getNiveau()+"', 'Session.currentUser')";
                int result = conn.executeDML(sqlRegisterStudent);
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
        add(lblLevel, 0, 5);
        add(cbxLevel, 1, 5);
        add(btnRegister, 0, 6);
        add(lblError, 0, 7);

        body.getChildren().add(this);
    }

}
