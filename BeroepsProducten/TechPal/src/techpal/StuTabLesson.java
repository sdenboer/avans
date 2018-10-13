package techpal;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import techpal.Models.Lesson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StuTabLesson extends Tab {
    private TableView<Lesson> tblLessons;
    private Text label;
    private VBox vbox;
    private Button delete;
    private ObservableList<Lesson> oblLessons;
    private DbConnector conn;

    public StuTabLesson() {
        this.setText("Mijn lessen");
        conn = new DbConnector();
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        label = new Text("Mijn lessen: ");
        label.setFont(new Font("Arial", 20));

        oblLessons = FXCollections.observableArrayList(Session.listLessons);
        tblLessons = new TableView<>();
        TableColumn colDate = new TableColumn("datum");
        colDate.setCellValueFactory(new PropertyValueFactory<Lesson, LocalDate>("dtm"));
        TableColumn colPer = new TableColumn("tijd");
        colPer.setCellValueFactory(new PropertyValueFactory<Lesson, String>("per"));
        TableColumn colProg = new TableColumn ("onderwerp");
        colProg.setCellValueFactory(new PropertyValueFactory<Lesson, String>("prog"));
        TableColumn colTstl = new TableColumn("toestel");
        colTstl.setCellValueFactory(new PropertyValueFactory<Lesson, String>("tstl"));
        TableColumn colTtr = new TableColumn("tutor");
        colTtr.setCellValueFactory(new PropertyValueFactory<Lesson, String>("ttrNm"));
        tblLessons.setItems(oblLessons);
        tblLessons.getColumns().addAll(colDate, colPer, colProg, colTstl, colTtr);
        tblLessons.setPlaceholder(new Text("U heeft nog geen lessen"));
        tblLessons.setPrefHeight(300);
        tblLessons.setPrefWidth(500);

        delete = new Button("Verwijder les");
        delete.disableProperty().bind(Bindings.size(oblLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty

        delete.setOnAction(event -> {
            LocalDate pkDtm = tblLessons.getSelectionModel().getSelectedItem().getDtm();
            String pkStu = Session.currentUser.getUserNm();
            String pkPer = tblLessons.getSelectionModel().getSelectedItem().getPer();
            Session.listLessons.remove(tblLessons.getSelectionModel().getSelectedIndex());
            tblLessons.getItems().remove(tblLessons.getSelectionModel().getSelectedIndex());
            String sqlDelete = "DELETE FROM lessen " +
                    "WHERE dtm = '"+pkDtm.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+"' " +
                    "AND stu = '"+pkStu+"' " +
                    "AND periode_per = '"+pkPer+"' ";
            System.out.println(sqlDelete);
            int result = conn.executeDML(sqlDelete);

        });

        vbox.getChildren().addAll(label, tblLessons, delete);
        this.setContent(vbox);

    }
}
