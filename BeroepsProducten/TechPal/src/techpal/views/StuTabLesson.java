package techpal.views;

import javafx.beans.binding.Bindings;
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

public class StuTabLesson extends Tab {
    private TableView<Lesson> tblLessons;
    private Text title;
    private VBox vbox;
    private Button delete;

    public StuTabLesson() {
        this.setText("Mijn lessen");
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        title = new Text("Mijn lessen");
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
        TableColumn<Lesson, String> colTtr = new TableColumn<>("tutor");
        colTtr.setCellValueFactory(new PropertyValueFactory<>("ttrNm"));
        colTtr.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/20));

        tblLessons.setItems(LessonsController.olLessons);
        tblLessons.getColumns().addAll(Arrays.asList(colDate, colPer, colProg, colTstl, colTtr));
        tblLessons.setPlaceholder(new Text("U heeft nog geen lessen"));
        tblLessons.setPrefHeight(500);
        tblLessons.setPrefWidth(800);

        delete = new Button("Verwijder les");
        delete.disableProperty().bind(Bindings.size(LessonsController.olLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty
        delete.disableProperty().bind(Bindings.isEmpty(tblLessons.getSelectionModel().getSelectedItems())); //disables button when nothing is selected
        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TechPal");
            alert.setHeaderText(null);
            alert.setContentText("Weet u het zeker?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                LessonsController.deleteLesson(tblLessons.getSelectionModel().getSelectedItem().getDtm(),
                        tblLessons.getSelectionModel().getSelectedItem().getPer(),
                        tblLessons.getSelectionModel().getSelectedIndex());
            }
        });

        vbox.getChildren().addAll(title, tblLessons, delete);
        this.setContent(vbox);
    }
}
