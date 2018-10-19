package techpal.views;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import techpal.controllers.DbConnector;
import techpal.controllers.Statics;
import techpal.models.Lesson;
import techpal.controllers.Session;

import java.time.LocalDate;

public class TtrTabLesson extends Tab {
    private TableView<Lesson> tblLessons;
    private Text title;
    private VBox vbox;
    private GridPane grid;
    private Button delete, isFin, btnLocation;
    private DbConnector conn;

    public TtrTabLesson() {
        this.setText("Mijn planning");
        conn = new DbConnector();
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);

        title = new Text("Mijn Planning");
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

        tblLessons.setItems(Session.oblLessons);
        tblLessons.getColumns().addAll(colDate, colPer, colProg, colTstl, colStuNm, colStuPc, colStuHnr, colStuNiv);
        tblLessons.setPlaceholder(new Text("U heeft nog geen geplande lessen"));
        tblLessons.setPrefHeight(500);
        tblLessons.setPrefWidth(800);

        delete = new Button("Les Afzeggen");
        delete.disableProperty().bind(Bindings.size(Session.oblLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty
        delete.disableProperty().bind(Bindings.isEmpty(tblLessons.getSelectionModel().getSelectedItems())); //disables button when nothing is selected
        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TechPal");
            alert.setHeaderText(null);
            alert.setContentText("Weet u het zeker?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                LocalDate pkDtm = tblLessons.getSelectionModel().getSelectedItem().getDtm();
                String fkTtr = Session.currentUser.getUserNm();
                String pkStu = tblLessons.getSelectionModel().getSelectedItem().getStu();
                String pkPer = tblLessons.getSelectionModel().getSelectedItem().getPer();
                Session.oblAvailableLessons.add(tblLessons.getSelectionModel().getSelectedItem());
                Session.oblLessons.remove(tblLessons.getSelectionModel().getSelectedIndex());
                String sqlDelete = "UPDATE lessen " +
                        "SET ttr = NULL " +
                        "WHERE dtm = to_date('" + pkDtm + "', 'yyyy/mm/dd') " +
                        "AND stu = '" + pkStu + "' " +
                        "AND ttr = UPPER('" + fkTtr + "') " +
                        "AND periode_per = '" + pkPer + "' ";
                int result = conn.executeDML(sqlDelete);
            }
        });

        isFin = new Button("Les Afgelopen");
        isFin.disableProperty().bind(Bindings.size(Session.oblLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty
        isFin.disableProperty().bind(Bindings.isEmpty(tblLessons.getSelectionModel().getSelectedItems())); //disables button when nothing is selected
        isFin.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TechPal");
            alert.setHeaderText(null);
            alert.setContentText("U bevestigt hiermee dat de les is afgerond");
            if (alert.showAndWait().get() == ButtonType.OK) {
                LocalDate pkDtm = tblLessons.getSelectionModel().getSelectedItem().getDtm();
                String fkTtr = Session.currentUser.getUserNm();
                String pkStu = tblLessons.getSelectionModel().getSelectedItem().getStu();
                String pkPer = tblLessons.getSelectionModel().getSelectedItem().getPer();
                Session.oblPrevLessons.add(tblLessons.getSelectionModel().getSelectedItem());
                Session.oblLessons.remove(tblLessons.getSelectionModel().getSelectedIndex());
                String sqlisFin = "UPDATE lessen " +
                        "SET isFin = 1" +
                        "WHERE dtm = to_date('"+pkDtm+"', 'yyyy/mm/dd') " +
                        "AND stu = '"+pkStu+"' "+
                        "AND ttr = UPPER('" +fkTtr+"') " +
                        "AND periode_per = '" +pkPer+ "' ";
                int result = conn.executeDML(sqlisFin);
            }
        });

        btnLocation = new Button("Locatie");
        btnLocation.disableProperty().bind(Bindings.size(Session.oblLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty
        btnLocation.disableProperty().bind(Bindings.isEmpty(tblLessons.getSelectionModel().getSelectedItems()));
        btnLocation.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("U verlaat TechPal");
            alert.setHeaderText(null);
            alert.setContentText("U opent nu Google Maps via uw standaard browser");
            if (alert.showAndWait().get() == ButtonType.OK) {
                String zipcode = tblLessons.getSelectionModel().getSelectedItem().getStuPc();
                String number = tblLessons.getSelectionModel().getSelectedItem().getStuHnr();
                Statics.openMap(zipcode, number);
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
