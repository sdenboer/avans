package techpal.views;

import javafx.geometry.HPos;
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
import techpal.models.Person;
import techpal.models.Student;
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
        btnOk = new Button("LOG IN");
        btnOk.setId("btn-login");
        btnRegister = new Button("REGISTEREN");
        btnRegister.setId("btn-reg");
        this.setHalignment(btnOk, HPos.RIGHT);

        btnOk.setOnAction(event -> {
            String strUserName = tdfUserName.getText().toUpperCase(); //storing the username in the local storage
            String strPassword = pwfPassword.getText(); //storing the password to check
            String sql = "SELECT * FROM personen WHERE userNm = '"+strUserName+"' AND pw = '"+strPassword+"'";
            ResultSet res = conn.getData(sql);
            try {
                if (res.next()) {
                    String strUserRole = res.getString("rollen_rol");
                    Person user = strUserRole.equals("student") ? Session.currentStudent : Session.currentTutor;
                    setUser(user, navBar, res);
                    openNextView(new TabView(body, user), body);
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

        btnRegister.setOnAction(event -> { //button to register a new user as a Person
            openNextView(new RegRoleView(body, navBar), body);
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

    private void openNextView(Node next, AnchorPane body) { //sets the next view
        body.getChildren().clear();
        body.getChildren().add(next);
    }

    private void setUser(Person user, TechPalNavBar navBar, ResultSet res) throws SQLException {
        user.setUserNm(res.getString("userNm"));
        user.setPw(res.getString("pw"));
        user.setNm(res.getString("nm"));
        user.setPc(res.getString("pc"));
        user.setRol(res.getString("rollen_rol"));
        user.setUserNm(tdfUserName.getText().toUpperCase());
        navBar.loginSuccess();
        Statics.setLessons(user); //method call to get all lessons from database and add them to an ArrayList in the Session class
        Statics.setHasDevices(user); //method call to get all devices owned by the current user.
        if (user instanceof Student) {
            ((Student) user).setHnr(res.getString("hnr"));
            ((Student) user).setNiveau(res.getString("niveau_nivom"));
        } else {
            Statics.setAvailableLessons();
        }
    }
}
