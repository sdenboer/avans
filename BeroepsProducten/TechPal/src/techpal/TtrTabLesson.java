package techpal;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import techpal.Models.Lesson;

import java.time.LocalDate;

public class TtrTabLesson extends Tab {
    private TableView<Lesson> tblLessons;
    private Text title;
    private VBox vbox;
    private GridPane grid;
    private Button delete, isFin;
    private DbConnector conn;

    public TtrTabLesson() {
        this.setText("Mijn lessen");
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
        TableColumn colPer = new TableColumn("tijd");
        colPer.setCellValueFactory(new PropertyValueFactory<Lesson, String>("per"));
        TableColumn colProg = new TableColumn("onderwerp");
        colProg.setCellValueFactory(new PropertyValueFactory<Lesson, String>("prog"));
        TableColumn colTstl = new TableColumn("toestel");
        colTstl.setCellValueFactory(new PropertyValueFactory<Lesson, String>("tstl"));
        TableColumn colStuNm = new TableColumn("student");
        colStuNm.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuNm"));
        TableColumn colStuPc = new TableColumn("postcode");
        colStuPc.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuPc"));
        TableColumn colStuHnr = new TableColumn("huisnummer");
        colStuHnr.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuHnr"));
        TableColumn colStuNiv = new TableColumn("niveau");
        colStuNiv.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuNiv"));

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
                System.out.println(sqlDelete);
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

        grid = new GridPane();
        grid.add(delete, 0, 0);
        grid.add(isFin, 1, 0);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(50);



        vbox.getChildren().addAll(title, tblLessons, grid);
        this.setContent(vbox);
    }
}
