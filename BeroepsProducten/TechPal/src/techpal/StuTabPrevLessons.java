package techpal;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import techpal.Models.Lesson;

import java.time.LocalDate;

public class StuTabPrevLessons extends Tab{

    private TableView<Lesson> tblLessons;
    private Text label;
    private VBox vbox;

    public StuTabPrevLessons() {
        this.setText("Historie");
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        label = new Text("Mijn vorige lessen: ");
        label.setFont(new Font("Arial", 20));

        tblLessons = new TableView<>();
        TableColumn colDate = new TableColumn("datum");
        colDate.setCellValueFactory(new PropertyValueFactory<Lesson, LocalDate>("dtm"));
        TableColumn colPer = new TableColumn("tijd");
        colPer.setCellValueFactory(new PropertyValueFactory<Lesson, String>("per"));
        TableColumn colProg = new TableColumn("onderwerp");
        colProg.setCellValueFactory(new PropertyValueFactory<Lesson, String>("prog"));
        TableColumn colTstl = new TableColumn("toestel");
        colTstl.setCellValueFactory(new PropertyValueFactory<Lesson, String>("tstl"));
        TableColumn colStuNm = new TableColumn("student");
        colStuNm.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuNm"));


        tblLessons.setItems(Session.oblPrevLessons);
        tblLessons.getColumns().addAll(colDate, colPer, colProg, colTstl, colStuNm);
        tblLessons.setPlaceholder(new Text("U heeft nog geen lessen gehad"));
        tblLessons.setPrefHeight(600);
        tblLessons.setPrefWidth(800);


        vbox.getChildren().addAll(label, tblLessons);
        this.setContent(vbox);

    }
}