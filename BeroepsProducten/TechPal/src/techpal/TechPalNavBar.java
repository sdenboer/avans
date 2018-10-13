package techpal;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import techpal.Models.Person;
import techpal.Models.Student;

public class TechPalNavBar extends GridPane {

    private Button btnBackToLogin;
    private LoginView resetLoginView;

    public TechPalNavBar(AnchorPane body) {
        this.setStyle("-fx-background-color:#575757");
        btnBackToLogin = new Button("Home");
        btnBackToLogin.setStyle("-fx-background-color:#575757; -fx-text-fill: white");
        btnBackToLogin.setOnMouseEntered(e -> btnBackToLogin.setStyle("-fx-background-color:#444444; -fx-text-fill: white"));
        btnBackToLogin.setOnMouseExited(e -> btnBackToLogin.setStyle("-fx-background-color:#575757; -fx-text-fill: white"));

        btnBackToLogin.setOnAction(event -> {
            resetLoginView = new LoginView(body, this);
            body.getChildren().clear();
            body.getChildren().add(resetLoginView);
            btnBackToLogin.setText("Home");
            Session.currentUser = new Person(); //resets the current user
            Session.currentStudent = new Student(); //resets the current user if he or she is a student
            Session.listLessons.clear(); // clear all the lessons of the previous user in the session
        });



        add(btnBackToLogin, 0, 0);
    }

    public void loginSuccess() {
        btnBackToLogin.setText("Uitloggen");
    }
}
