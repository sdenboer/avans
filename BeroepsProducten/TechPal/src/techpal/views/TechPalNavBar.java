package techpal.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import techpal.controllers.*;
import techpal.models.Person;
import techpal.models.Student;

public class TechPalNavBar extends GridPane {

    private Button btnBackToLogin;
    private LoginView resetLoginView;

    public TechPalNavBar(AnchorPane body) {
        this.setMinWidth(850);
        this.setId("navbar");
        this.setAlignment(Pos.TOP_RIGHT);
        btnBackToLogin = new Button("");
        btnBackToLogin.setId("nav-btn");

        btnBackToLogin.setOnAction(event -> {
            resetLoginView = new LoginView(body, this);
            btnBackToLogin.setText("");
            body.getChildren().clear();
            body.getChildren().add(resetLoginView);
            PersonsController.currentUser = new Person(); //resets the current user
            StudentsController.currentStudent = new Student(); //resets the current user if he or she is a student
            LessonsController.olLessons.clear(); // clear all the lessons of the previous user in the session
            DevicesController.olHasDevices.clear();
            LessonsController.olAvailableLessons.clear();
            LessonsController.olPrevLessons.clear();
        });
        add(btnBackToLogin, 0, 0);
    }

    public void loginSuccess() { //changes the text once button is pressed
        this.getChildren().clear();
        btnBackToLogin.setText("Uitloggen");
        add(btnBackToLogin, 0, 0);
    }

    public void register() {
        this.getChildren().clear();
        btnBackToLogin.setText("Terug");
        add(btnBackToLogin, 0, 0);
    }
}
