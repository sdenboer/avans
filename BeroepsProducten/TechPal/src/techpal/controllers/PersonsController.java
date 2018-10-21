package techpal.controllers;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import techpal.models.Person;
import techpal.models.Student;
import techpal.views.RegView;
import techpal.views.TabView;
import techpal.views.TechPalNavBar;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonsController {
    private static DbConnector conn;

    public PersonsController() {
    }

    public static Person currentUser = new Person();

    public static void getUser(String strUserName, String strPassword, TechPalNavBar navBar, AnchorPane body) {
        conn = new DbConnector();
        String sql = "SELECT * FROM personen WHERE userNm = '" + strUserName + "' AND pw = '" + strPassword + "'";
        System.out.println(sql);
        ResultSet res = conn.getData(sql);
        try {
            if (res.next()) {
                String strUserRole = res.getString("rollen_rol");
                Person user = strUserRole.equals("student") ? StudentsController.currentStudent : TutorsController.currentTutor;
                setUser(user, navBar, res, strUserName);
                openNextView(new TabView(body, user), body);
            } else {
                //if the sql query can't find anyone, this window pops up to alert the user.
                BaseController.alert("Oeps!",
                        "Uw gebruikersnaam/wachtwoord combinatie klopt niet. Als u geen account heeft kunt u registeren",
                        Alert.AlertType.ERROR);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setUser(Person user, TechPalNavBar navBar, ResultSet res, String strUserName) throws SQLException {
        user.setUserNm(res.getString("userNm"));
        user.setPw(res.getString("pw"));
        user.setNm(res.getString("nm"));
        user.setPc(res.getString("pc"));
        user.setRol(res.getString("rollen_rol"));
        user.setUserNm(strUserName);
        navBar.loginSuccess();
        LessonsController.setLessons(user); //method call to get all lessons from database and add them to an ArrayList in the LessonsController class
        DevicesController.setHasDevices(user); //method call to get all devices owned by the current user.
        if (user instanceof Student) {
            ((Student) user).setHnr(res.getString("hnr"));
            ((Student) user).setNiveau(res.getString("niveau_nivom"));
        } else {
            LessonsController.setAvailableLessons();
        }
    }

    public static void openNextView(Node next, AnchorPane body) { //sets the next view
        body.getChildren().clear();
        body.getChildren().add(next);
    }

    public static void openRegView(AnchorPane body, Person role, TechPalNavBar navBar) {
        RegView regView = new RegView(body, role, navBar);
        body.getChildren().clear();
        body.getChildren().add(regView);
    }

    public static void setNewUser(Person user, String userName, String password, String name, String zipCode) { //sets a new Person
        user.setUserNm(userName);
        user.setPw(password);
        user.setNm(name);
        user.setPc(zipCode);
    }

    public static void setNewStudent(Person user, String number, String level){ //adds specific setters and getters if the Person is a Student
        conn = new DbConnector();
        user.setRol("student");
        ((Student) user).setHnr(number);
        ((Student) user).setNiveau(level);
        String sql = "INSERT INTO personen VALUES (UPPER('"+user.getUserNm()+"'), " +
                "'"+user.getPw()+"','"+user.getNm()+"', " +
                "UPPER('"+user.getPc()+"'),'"+((Student) user).getHnr()+"', " +
                "'"+((Student) user).getNiveau()+"', '"+user.getRol()+"')";
        int result = conn.executeDML(sql);
    }



    public static void setNewTutor(Person user){ //if the new Person is a Tutor
        conn = new DbConnector();
        user.setRol("tutor");
        String sql = "INSERT INTO personen (userNm, pw, nm, pc, rollen_rol)" +
                "VALUES (UPPER('"+user.getUserNm()+"'), '"+user.getPw()+"', " +
                "'" +user.getNm()+ "', UPPER('"+user.getPc()+ "'), '"+user.getRol()+"')";
        int result = conn.executeDML(sql);
    }

    public static void updateUser(Person user){
        String sql;
        if (user instanceof Student) {
            sql = "UPDATE personen " +
                    "SET pw = '"+user.getPw()+"', nm = '"+user.getNm()+"', " +
                    "pc = UPPER('"+user.getPc()+"'), hnr = '"+StudentsController.currentStudent.getHnr()+"', " +
                    "niveau_nivOm = '"+StudentsController.currentStudent.getNiveau()+"' " +
                    "WHERE userNm = UPPER('"+user.getUserNm()+"')";
        } else {
            sql = "UPDATE personen " +
                    "SET pw = '"+user.getPw()+"', nm = '"+user.getNm()+"', " +
                    "pc = UPPER('"+user.getPc()+"') " +
                    "WHERE userNm = UPPER('"+user.getUserNm()+"')";
        }
        int resultUpdatePerson = conn.executeDML(sql);
    }


}
