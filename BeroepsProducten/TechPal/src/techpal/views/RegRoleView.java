package techpal.views;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.controllers.Session;
import techpal.controllers.Statics;
import techpal.models.Person;

public class RegRoleView extends GridPane {
    private Text lblChoice;
    private Button btnStudent, btnTutor;
    private RegView regView;

    public RegRoleView(AnchorPane body, TechPalNavBar navBar) {
        Statics.setGrid(this);
        lblChoice = new Text("Bent u een student of een tutor?");
        lblChoice.setId("text-choice");
        btnStudent = new Button("Student");
        btnStudent.setId("btn-student");
        btnTutor = new Button("Tutor");
        btnTutor.setId("btn-reg");

        btnStudent.setOnAction(event -> {
            Person student = Session.currentStudent;
            openRegView(body, student, navBar);
        });

        btnTutor.setOnAction(event -> {
            Person tutor = Session.currentTutor;
            openRegView(body, tutor, navBar);
        });

        this.add(lblChoice, 0, 0);
        this.add(btnStudent, 0, 1);
        this.add(btnTutor, 0, 4);
        body.getChildren().add(this);
    }

    private void openRegView(AnchorPane body, Person role, TechPalNavBar navBar) {
        regView = new RegView(body, role, navBar);
        body.getChildren().clear();
        body.getChildren().add(regView);
    }
}
