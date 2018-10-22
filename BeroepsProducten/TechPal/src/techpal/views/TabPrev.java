package techpal.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import techpal.controllers.LessonsController;
import techpal.models.Lesson;
import java.time.LocalDate;
import java.util.Arrays;

public class TabPrev extends Tab{

    private TableView<Lesson> tblLessons;
    private Text title;
    private VBox vbox;

    public TabPrev(String columnName, String cellName) {
        this.setText("Historie");
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        title = new Text("Mijn vorige lessen");
        title.setId("text-title");

        tblLessons = new TableView<>();
        TableColumn<Lesson, LocalDate> colDate = new TableColumn<>("datum");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dtm"));
        colDate.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/20));
        TableColumn<Lesson, String> colPer = new TableColumn<>("tijd");
        colPer.setCellValueFactory(new PropertyValueFactory<>("per"));
        colPer.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/20));
        TableColumn<Lesson, String> colProg = new TableColumn<>("onderwerp");
        colProg.setCellValueFactory(new PropertyValueFactory<>("prog"));
        colProg.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/20));
        TableColumn<Lesson, String> colTstl = new TableColumn<>("toestel");
        colTstl.setCellValueFactory(new PropertyValueFactory<>("tstl"));
        colTstl.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/20));
        TableColumn<Lesson, String> colNm = new TableColumn<>(columnName);
        colNm.setCellValueFactory(new PropertyValueFactory<>(cellName));
        colNm.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/20));

        tblLessons.setItems(LessonsController.olPrevLessons);
        tblLessons.getColumns().addAll(Arrays.asList(colDate, colPer, colProg, colTstl, colNm));
        tblLessons.setPlaceholder(new Text("U heeft nog geen lessen"));
        tblLessons.setPrefHeight(500);
        tblLessons.setPrefWidth(800);

        vbox.getChildren().addAll(title, tblLessons);
        this.setContent(vbox);
    }
}
