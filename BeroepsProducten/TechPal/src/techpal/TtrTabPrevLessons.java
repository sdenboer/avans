package techpal;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import techpal.Models.Lesson;

import java.time.LocalDate;

public class TtrTabPrevLessons extends Tab{

    private TableView<Lesson> tblLessons;
    private Text title;
    private VBox vbox;

    public TtrTabPrevLessons() {
        this.setText("Historie");
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);

        title = new Text("Mijn vorige lessen");
        title.setId("text-title");

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
        tblLessons.setPlaceholder(new Text("U heeft nog geen lessen gegeven"));
        tblLessons.setPrefHeight(500);
        tblLessons.setPrefWidth(800);

        vbox.getChildren().addAll(title, tblLessons);
        this.setContent(vbox);

    }
}
