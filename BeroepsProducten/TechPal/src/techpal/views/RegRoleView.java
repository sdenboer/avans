package techpal.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.controllers.*;
import techpal.models.Person;

public class RegRoleView extends GridPane {
    private Text lblChoice;
    private Button btnStudent, btnTutor;
    private Image imTutor, imStudent;
    private ImageView ivTutor, ivStudent;

    public RegRoleView(AnchorPane body, TechPalNavBar navBar) {
        BaseController.setGrid(this);
        lblChoice = new Text("Bent u een student of een tutor?");
        lblChoice.setId("text-choice");
        lblChoice.setY(100);
        btnStudent = new Button("Student");
        btnStudent.setId("btn-reg");
        btnStudent.setPadding(new Insets(0, 12, 0, 0));
        btnTutor = new Button("Tutor");
        btnTutor.setId("btn-reg");
        imTutor = new Image("techpal/style/resources/tutor.png");
        imStudent = new Image("techpal/style/resources/student.png");
        ivStudent = new ImageView(imStudent);
        ivTutor = new ImageView(imTutor);

        btnStudent.setOnAction(event -> {
            Person student = StudentsController.currentStudent;
            PersonsController.openRegView(body, student, navBar);
        });

        btnTutor.setOnAction(event -> {
            Person tutor = TutorsController.currentTutor;
            PersonsController.openRegView(body, tutor, navBar);
        });

        this.add(lblChoice, 0, 0);
        this.add(btnStudent, 0, 6);
        this.add(btnTutor, 1, 6);
        this.add(ivTutor, 1, 5);
        this.add(ivStudent, 0, 5);
        body.getChildren().add(this);
    }
}
