package techpal.views;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.controllers.DbConnector;
import techpal.controllers.Session;
import techpal.controllers.Statics;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginView extends GridPane {
    private Text title, lblUserName, lblPassword;
    private TextField tdfUserName;
    private PasswordField pwfPassword;
    private Button btnOk, btnRegister;
    private DbConnector conn;

    public LoginView(AnchorPane body, TechPalNavBar navBar) {
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.setMinHeight(800);
        this.setMinWidth(850);
        this.getColumnConstraints().add(new ColumnConstraints(150));
        conn = new DbConnector();
        title = new Text("TechPal");
        title.setId("text-title");
        lblUserName = new Text("Gebruikersnaam");
        lblUserName.setId("text-label");
        tdfUserName = new TextField();
        lblPassword = new Text("Wachtwoord");
        lblPassword.setId("text-label");
        pwfPassword = new PasswordField();
        btnOk = new Button("Inloggen");
        btnOk.setId("btn-login");
        btnRegister = new Button("Registeren");
        btnRegister.setId("btn-reg");

        btnOk.setOnAction(event -> {
            Session.currentUser.setUserNm(tdfUserName.getText().toUpperCase()); //storing the username in the local storage
            Session.currentUser.setPw(pwfPassword.getText()); //storing the password to check
            String sql = "SELECT * FROM personen WHERE userNm = '"+Session.currentUser.getUserNm()+"' AND pw = '"+Session.currentUser.getPw()+"'";
            ResultSet res = conn.getData(sql);
            try {
                if (res.next()) { //storing the current user's information in local storage
                    Session.currentUser.setNm(res.getString("nm"));
                    Session.currentUser.setPc(res.getString("pc"));
                    Session.currentUser.setRol(res.getString("rollen_rol"));
                    Session.currentStudent.setHnr(res.getString("hnr"));
                    Session.currentStudent.setNiveau(res.getString("niveau_nivom"));
                    navBar.loginSuccess();
                    Statics.setLessons(); //method call to get all lessons from database and add them to an ArrayList in the Session class
                    Statics.setHasDevices(); //method call to get all devices owned by the current user.
                    if (Session.currentUser.getRol().equals("tutor")) {
                        Statics.setAvailableLessons(); //selects all the lessons and stores them in an Observable Array, to be used in TableViews
                    }
                    openNextView(new TabView(body), body);
                } else {
                    //if the sql query can't find anyone, this window pops up to alert the user.
                    Statics.alert("Oeps!",
                            "Uw gebruikersnaam/wachtwoord combinatie klopt niet. Als u geen account heeft kunt u registeren",
                            Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        btnRegister.setOnAction(event -> { //button to register a new user as a Persoon
            openNextView(new RegRoleView(body), body);
            navBar.register();
        });

        add(title, 0, 0);
        add(lblUserName, 0, 16);
        add(tdfUserName, 1, 16);
        add(lblPassword, 0, 17);
        add(pwfPassword, 1, 17);
        add(btnOk, 0, 18);
        add(btnRegister, 1, 18);
        body.getChildren().add(this);
    }

    private void openNextView(Node next, AnchorPane body) { //sets the next view
        body.getChildren().clear();
        body.getChildren().add(next);
    }
}
