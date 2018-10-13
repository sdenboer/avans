package techpal;


import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.Models.Person;


public class RegTtrView extends GridPane {
    private Text lblUserName, lblPassword, lblName, lblZipCode, lblError;
    private TextField tfdUserName, tfdName, tfdZipCode;
    private PasswordField pwfPassword;
    private Button btnRegister;
    private DbConnector conn;

    public RegTtrView(Pane body) {

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
        btnRegister = new Button("Registeren");
        lblError = new Text("");

        btnRegister.setOnAction(event -> {
            if (tfdZipCode.getText().toUpperCase().matches("(\\d{4})\\s*([A-Z]{2})")) {
                lblError.setText("");
                Person newTutor = new Person();
                newTutor.setUserNm(tfdUserName.getText());
                newTutor.setPw(pwfPassword.getText());
                newTutor.setNm(tfdName.getText());
                newTutor.setPc(tfdZipCode.getText());

                String sqlRegisterTutor = "INSERT INTO personen (userNm, pw, nm, pc, rollen_rol)VALUES ('" + newTutor.getUserNm() + "','" + newTutor.getPw() + "','" + newTutor.getNm() + "'," +
                        "'" + newTutor.getPc() + "','tutor')";
                int result = conn.executeDML(sqlRegisterTutor);
//                this.getChildren().clear();
//                body.getChildren().add(new LoginView(this));
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
        add(btnRegister, 0, 4);
        add(lblError, 0, 5);

        body.getChildren().add(this);
    }

}
