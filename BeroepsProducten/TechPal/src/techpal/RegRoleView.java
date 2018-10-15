package techpal;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;

public class RegRoleView extends GridPane {
    private Text lblChoice;
    private Button btnStudent, btnTutor;
    private RegStuView regStuView;
    private RegTtrView regTtrView;

    public RegRoleView(AnchorPane body) {
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.setMinHeight(800);
        this.setMinWidth(800);
        this.getColumnConstraints().add(new ColumnConstraints(150));
        lblChoice = new Text("Bent u een student of een tutor?");
        lblChoice.setId("text-choice");
        btnStudent = new Button("Student");
        btnStudent.setId("btn-student");
        btnTutor = new Button("Tutor");
        btnTutor.setId("btn-reg");

        btnStudent.setOnAction(event -> {
            regStuView = new RegStuView(body);
            body.getChildren().clear();
            body.getChildren().add(regStuView);
        });

        btnTutor.setOnAction(event -> {
            regTtrView = new RegTtrView(body);
            body.getChildren().clear();
            body.getChildren().add(regTtrView);
        });

        this.add(lblChoice, 0, 0);
        this.add(btnStudent, 0, 1);
        this.add(btnTutor, 10, 1);

//        body.getChildren().add(this);
    }
}
