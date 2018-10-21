package techpal.views;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import techpal.controllers.*;
import techpal.models.Lesson;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class TtrTabAdd extends Tab {
    private TableView<Lesson> tblLessons;
    private Text title;
    private VBox vbox;
    private Button addLesson;
    private DbConnector conn;
    private GridPane grid;
    private Button btnLocation;

    public TtrTabAdd(TabView tabPane) {
        this.setText("Beschikbare lessen");
        conn = new DbConnector();
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);

        title = new Text("Beschikbare Lessen");
        title.setId("text-title");

        tblLessons = new TableView<>();
        TableColumn colDate = new TableColumn("datum");
        colDate.setCellValueFactory(new PropertyValueFactory<Lesson, LocalDate>("dtm"));
        colDate.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/12.5));
        TableColumn colPer = new TableColumn("tijd");
        colPer.setCellValueFactory(new PropertyValueFactory<Lesson, String>("per"));
        colPer.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/12.5));
        TableColumn colProg = new TableColumn("onderwerp");
        colProg.setCellValueFactory(new PropertyValueFactory<Lesson, String>("prog"));
        colProg.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/10.5));
        TableColumn colTstl = new TableColumn("toestel");
        colTstl.setCellValueFactory(new PropertyValueFactory<Lesson, String>("tstl"));
        colTstl.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/10.5));
        TableColumn colStuNm = new TableColumn("student");
        colStuNm.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuNm"));
        colStuNm.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/18.5));
        TableColumn colStuPc = new TableColumn("postcode");
        colStuPc.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuPc"));
        colStuPc.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/10.5));
        TableColumn colStuHnr = new TableColumn("huisnummer");
        colStuHnr.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuHnr"));
        colStuHnr.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/12.5));
        TableColumn colStuNiv = new TableColumn("niveau");
        colStuNiv.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuNiv"));
        colStuNiv.prefWidthProperty().bind(tblLessons.widthProperty().divide(100/12.5));

        tblLessons.setItems(LessonsController.olAvailableLessons);
        tblLessons.getColumns().addAll(colDate, colPer, colProg, colTstl, colStuNm, colStuPc, colStuHnr, colStuNiv);
        tblLessons.setPlaceholder(new Text("Er zijn geen lessen beschikbaar voor u"));
        tblLessons.setPrefHeight(500);
        tblLessons.setPrefWidth(800);

        addLesson = new Button("Les Afspreken");
        addLesson.disableProperty().bind(Bindings.size(LessonsController.olLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty
        addLesson.disableProperty().bind(Bindings.isEmpty(tblLessons.getSelectionModel().getSelectedItems()));

        addLesson.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TechPal");
            alert.setHeaderText(null);
            alert.setContentText("Weet u het zeker?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                LessonsController.addTutorLesson(tblLessons.getSelectionModel().getSelectedItem().getStu(),
                        tblLessons.getSelectionModel().getSelectedItem().getDtm(),
                        tblLessons.getSelectionModel().getSelectedItem().getPer(),
                        tblLessons.getSelectionModel().getSelectedItem().getProg(),
                        tblLessons.getSelectionModel().getSelectedItem().getTstl(),
                        tblLessons.getSelectionModel().getSelectedItem().getStuHnr(),
                        tblLessons.getSelectionModel().getSelectedItem().getStuNm(),
                        tblLessons.getSelectionModel().getSelectedItem().getStuNiv(),
                        tblLessons.getSelectionModel().getSelectedItem().getStuPc(),
                        tblLessons.getSelectionModel().getSelectedIndex());
                tabPane.getSelectionModel().select(0);
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
                String zipcode = tblLessons.getSelectionModel().getSelectedItem().getStuPc();
                String number = tblLessons.getSelectionModel().getSelectedItem().getStuHnr();
                MapsController.openMap(zipcode, number);
            }
        });

        grid = new GridPane();
        grid.add(addLesson, 0, 0);
        grid.add(btnLocation, 1, 0);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(50);

        vbox.getChildren().addAll(title, tblLessons, grid);
        this.setContent(vbox);

    }
}
