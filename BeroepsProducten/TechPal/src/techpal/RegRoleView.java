package techpal;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;

public class RegRoleView extends GridPane {
    private Text lblChoice;
    private Button btnStudent, btnTutor;

    public RegRoleView(Pane p) {
        lblChoice = new Text("Wat ben je?");
        btnStudent = new Button("Student");
        btnTutor = new Button("Tutor");

        btnStudent.setOnAction(event -> {
            this.getChildren().clear();
            p.getChildren().add(new RegStuView(this));
        });

        btnTutor.setOnAction(event -> {
            this.getChildren().clear();
            p.getChildren().add(new RegTtrView(this));
        });

        add(lblChoice, 0, 0);
        add(btnStudent, 0, 1);
        add(btnTutor, 1, 1);

        p.getChildren().add(this);
    }
}
