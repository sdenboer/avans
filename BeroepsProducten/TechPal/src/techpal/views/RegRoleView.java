package techpal.views;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import techpal.controllers.Statics;

public class RegRoleView extends GridPane {
    private Text lblChoice;
    private Button btnStudent, btnTutor;
    private RegView regView;

    public RegRoleView(AnchorPane body) {
        Statics.setGrid(this);
        lblChoice = new Text("Bent u een student of een tutor?");
        lblChoice.setId("text-choice");
        btnStudent = new Button("Student");
        btnStudent.setId("btn-student");
        btnTutor = new Button("Tutor");
        btnTutor.setId("btn-reg");

        btnStudent.setOnAction(event -> {
            openRegView(body, btnStudent);
        });

        btnTutor.setOnAction(event -> {
            openRegView(body, btnTutor);
        });

        this.add(lblChoice, 0, 0);
        this.add(btnStudent, 0, 1);
        this.add(btnTutor, 0, 4);
        body.getChildren().add(this);
    }

    private void openRegView(AnchorPane body, Button btn) {
        String role = btn.getText().toLowerCase();
        regView = new RegView(body, role);
        body.getChildren().clear();
        body.getChildren().add(regView);
    }
}
