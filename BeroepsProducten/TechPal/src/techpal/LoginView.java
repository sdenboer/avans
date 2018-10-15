package techpal;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.Models.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginView extends GridPane {
    private Text logo, lblUserName, lblPassword, lblSignUp;
    private TextField tdfUserName;
    private PasswordField pwfPassword;
    private Button btnOk, btnRegister;
    private DbConnector conn;

    public LoginView(AnchorPane body, TechPalNavBar navBar) {
        conn = new DbConnector();
        logo = new Text("TechPal");
        lblUserName = new Text("Gebruikersnaam");
        tdfUserName = new TextField();
        lblPassword = new Text("Wachtwoord");
        pwfPassword = new PasswordField();
        lblSignUp = new Text("");
        lblSignUp.setFill(Color.RED);
        btnOk = new Button("OK");
        btnRegister = new Button("Registeren");

        btnOk.setOnAction(event -> {
            Session.currentUser.setUserNm(tdfUserName.getText());
            Session.currentUser.setPw(pwfPassword.getText());
            String sql = "SELECT * FROM personen WHERE userNm = UPPER('"+Session.currentUser.getUserNm()+"') AND pw = '"+Session.currentUser.getPw()+"'";
            System.out.println(sql);
            ResultSet res = conn.getData(sql);
            lblSignUp.setText("");
            try {
                if (res.next()) {
                    Session.currentUser.setNm(res.getString("nm"));
                    Session.currentUser.setPc(res.getString("pc"));
                    Session.currentUser.setRol(res.getString("rollen_rol"));
                    Session.currentStudent.setHnr(res.getString("hnr"));
                    Session.currentStudent.setNiveau(res.getString("niveau_nivom"));
                    navBar.loginSuccess();
                    setLessons(); //method call to get all lessons from database and add them to an ArrayList in the Session class
                    initStage.setHasDevices(); //method call to get all devices owned by the current user.

                    if (Session.currentUser.getRol().equals("student")) {
                        this.getChildren().clear();
                        body.getChildren().add(new StuMainView(this)); //opens the Student pane
                    } else {
                        initStage.setAvailableLessons();
                        this.getChildren().clear();
                        body.getChildren().add(new TtrMainView(this)); //opens the Tutor pane
                    }
                } else {
                    lblSignUp.setText("Uw gebruikersnaam/wachtwoord combinatie klopt niet. Als u geen account heeft kunt u registeren");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        btnRegister.setOnAction(event -> { //button to register a new Person
            this.getChildren().clear();
            body.getChildren().add(new RegRoleView(this));
        });

        add(logo, 0, 0);
        add(lblUserName, 0, 1);
        add(tdfUserName, 1, 1);
        add(lblPassword, 0, 2);
        add(pwfPassword, 1, 2);
        add(btnOk, 0, 3);
        add(btnRegister, 1, 3);
        add(lblSignUp, 0, 4);
        body.getChildren().add(this);
    }

    //This method sets the user specific lessons in the Lesson and Session classes. Session is an arrayList of Lesson instances.
    private void setLessons() {
        String role = Session.currentUser.getRol().equals("student") ? "stu" : "ttr"; //checks whether the user is a student or a tutor
        //The following sql query joins the person table twice,
        // as I want to show the name of the tutor in the lesson tab view,
        // and not just the tutor's username. PS stands for PersoonStudent and PT means PersoonTutor
        String sqlLessen = "SELECT ps.usernm, ps.nm AS stuNm, ps.pc, ps.hnr, ps.niveau_nivom, l.stu, l.dtm, l.periode_per, " +
                "l.programma_prognm, l.isFin, l.tstl, l.ttr, pt.nm AS ttrNm " +
                "FROM personen ps " +
                "INNER JOIN lessen l ON(l.stu = ps.userNm) " +
                "LEFT OUTER JOIN personen pt ON(l.ttr = pt.usernm) " +
                "WHERE "+role+" = UPPER('"+Session.currentUser.getUserNm()+"')";
        ResultSet res = conn.getData(sqlLessen);
        System.out.println(sqlLessen);
        try {
            while (res.next()) {
                Lesson lesson = new Lesson();
                lesson.setStu(res.getString("stu"));
                lesson.setStuNm(res.getString("stuNm"));
                lesson.setDtm(res.getDate("dtm").toLocalDate());
                lesson.setPer(res.getString("periode_per"));
                lesson.setProg(res.getString("programma_progNm"));
                lesson.setIsFin(res.getInt("isFin"));
                lesson.setTstl(res.getString("tstl"));
                lesson.setTtr(res.getString("ttr"));
                lesson.setStuNiv(res.getString("niveau_nivOm"));
                lesson.setTtrNm(res.getString("ttrNm"));
                lesson.setStuPc((res.getString("pc")));
                lesson.setStuHnr(res.getString("hnr"));
                Session.oblLessons.add(lesson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
