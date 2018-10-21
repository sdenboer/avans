package techpal.views;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.controllers.*;

public class LoginView extends GridPane {
    private Text title, lblUserName, lblPassword;
    private TextField tdfUserName;
    private PasswordField pwfPassword;
    private Button btnOk, btnRegister;

    public LoginView(AnchorPane body, TechPalNavBar navBar) {
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.setMinHeight(800);
        this.setMinWidth(850);
        this.getColumnConstraints().add(new ColumnConstraints(150));
        title = new Text("TechPal");
        title.setId("text-title");
        lblUserName = new Text("Gebruikersnaam");
        lblUserName.setId("text-label");
        tdfUserName = new TextField();
        lblPassword = new Text("Wachtwoord");
        lblPassword.setId("text-label");
        pwfPassword = new PasswordField();
        btnOk = new Button("LOG IN");
        btnOk.setId("btn-login");
        btnRegister = new Button("REGISTEREN");
        btnRegister.setId("btn-reg");
        setHalignment(btnOk, HPos.RIGHT);

        btnOk.setOnAction(event -> PersonsController.getUser(
                tdfUserName.getText().toUpperCase(),  //storing the username in the local storage
                pwfPassword.getText(), //storing the password to check
                navBar,
                body));

        btnRegister.setOnAction(event -> { //button to register a new user as a Person
            PersonsController.openNextView(new RegRoleView(body, navBar), body);
            navBar.register();
        });

        add(title, 0, 0);
        add(lblUserName, 0, 16);
        add(tdfUserName, 1, 16);
        add(lblPassword, 0, 17);
        add(pwfPassword, 1, 17);
        add(btnOk, 1, 20);
        add(btnRegister, 0, 20);
        body.getChildren().add(this);
    }
}
