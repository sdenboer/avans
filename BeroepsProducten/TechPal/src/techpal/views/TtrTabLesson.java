package techpal.views;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import techpal.controllers.*;
import techpal.models.Lesson;

import java.time.LocalDate;
import java.util.Arrays;

public class TtrTabLesson extends Tab {
    private TableView<Lesson> tblLessons;
    private Text title;
    private VBox vbox;
    private GridPane grid;
    private Button delete, isFin, btnLocation;

    public TtrTabLesson() {
        this.setText("Mijn planning");
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);

        title = new Text("Mijn Planning");
        title.setId("text-title");

        tblLessons = new TableView<>();
        TableColumn<Lesson, LocalDate> colDate = new TableColumn<>("datum");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dtm"));
        colDate.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/12.5));
        TableColumn<Lesson, String> colPer = new TableColumn<>("tijd");
        colPer.setCellValueFactory(new PropertyValueFactory<>("per"));
        colPer.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/12.5));
        TableColumn<Lesson, String> colProg = new TableColumn<>("onderwerp");
        colProg.setCellValueFactory(new PropertyValueFactory<>("prog"));
        colProg.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/10.5));
        TableColumn<Lesson, String> colTstl = new TableColumn<>("toestel");
        colTstl.setCellValueFactory(new PropertyValueFactory<>("tstl"));
        colTstl.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/10.5));
        TableColumn<Lesson, String> colStuNm = new TableColumn<>("student");
        colStuNm.setCellValueFactory(new PropertyValueFactory<>("stuNm"));
        colStuNm.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/18.5));
        TableColumn<Lesson, String> colStuPc = new TableColumn<>("postcode");
        colStuPc.setCellValueFactory(new PropertyValueFactory<>("stuPc"));
        colStuPc.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/10.5));
        TableColumn<Lesson, String> colStuHnr = new TableColumn<>("huisnummer");
        colStuHnr.setCellValueFactory(new PropertyValueFactory<>("stuHnr"));
        colStuHnr.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/12.5));
        TableColumn<Lesson, String> colStuNiv = new TableColumn<>("niveau");
        colStuNiv.setCellValueFactory(new PropertyValueFactory<>("stuNiv"));
        colStuNiv.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/12.5));

        tblLessons.setItems(LessonsController.olLessons);
        tblLessons.getColumns().addAll(Arrays.asList(colDate, colPer, colProg, colTstl, colStuNm, colStuPc, colStuHnr, colStuNiv));
        tblLessons.setPlaceholder(new Text("U heeft nog geen geplande lessen"));
        tblLessons.setPrefHeight(500);
        tblLessons.setPrefWidth(800);

        delete = new Button("Les Afzeggen");
        delete.disableProperty().bind(Bindings.size(LessonsController.olLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty
        delete.disableProperty().bind(Bindings.isEmpty(tblLessons.getSelectionModel().getSelectedItems())); //disables button when nothing is selected
        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TechPal");
            alert.setHeaderText(null);
            alert.setContentText("Weet u het zeker?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                LessonsController.cancelLesson(tblLessons.getSelectionModel().getSelectedItem().getDtm(),
                        tblLessons.getSelectionModel().getSelectedItem().getStu(),
                        tblLessons.getSelectionModel().getSelectedItem().getPer(),
                        tblLessons.getSelectionModel().getSelectedItem(),
                        tblLessons.getSelectionModel().getSelectedIndex());
            }
        });

        isFin = new Button("Les Afgelopen");
        isFin.disableProperty().bind(Bindings.size(LessonsController.olLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty
        isFin.disableProperty().bind(Bindings.isEmpty(tblLessons.getSelectionModel().getSelectedItems())); //disables button when nothing is selected
        isFin.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TechPal");
            alert.setHeaderText(null);
            alert.setContentText("U bevestigt hiermee dat de les is afgerond");
            if (alert.showAndWait().get() == ButtonType.OK) {
                LessonsController.finishLesson(tblLessons.getSelectionModel().getSelectedItem().getDtm(),
                        tblLessons.getSelectionModel().getSelectedItem().getStu(),
                        tblLessons.getSelectionModel().getSelectedItem().getPer(),
                        tblLessons.getSelectionModel().getSelectedItem(),
                        tblLessons.getSelectionModel().getSelectedIndex());
                }
        });

        btnLocation = new Button("Locatie");
        btnLocation.disableProperty().bind(Bindings.size(LessonsController.olLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty
        btnLocation.disableProperty().bind(Bindings.isEmpty(tblLessons.getSelectionModel().getSelectedItems()));
        btnLocation.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("U verlaat TechPal");
            alert.setHeaderText(null);
            alert.setContentText("U opent nu Google Maps via uw standaard browser");
            if (alert.showAndWait().get() == ButtonType.OK) {
                String zipCode = tblLessons.getSelectionModel().getSelectedItem().getStuPc();
                String number = tblLessons.getSelectionModel().getSelectedItem().getStuHnr();
                MapsController.openMap(zipCode, number);
            }
        });

        grid = new GridPane();
        grid.add(delete, 0, 0); //sets the buttons in a centered GridPane
        grid.add(isFin, 2, 0);
        grid.add(btnLocation, 1, 0);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(50);

        vbox.getChildren().addAll(title, tblLessons, grid);
        this.setContent(vbox);
    }
}
