package techpal;

import javafx.beans.binding.Bindings;
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

public class TtrTabLesson extends Tab {
    private TableView<Lesson> tblLessons;
    private Text label;
    private VBox vbox;
    private Button delete;
    private DbConnector conn;

    public TtrTabLesson() {
        this.setText("Mijn lessen");
        conn = new DbConnector();
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        label = new Text("Mijn Planning: ");
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
        TableColumn colStuPc = new TableColumn("postcode");
        colStuPc.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuPc"));
        TableColumn colStuHnr = new TableColumn("huisnummer");
        colStuHnr.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuHnr"));
        TableColumn colStuNiv = new TableColumn("niveau");
        colStuNiv.setCellValueFactory(new PropertyValueFactory<Lesson, String>("stuNiv"));


        tblLessons.setItems(Session.oblLessons);
        tblLessons.getColumns().addAll(colDate, colPer, colProg, colTstl, colStuNm, colStuPc, colStuHnr, colStuNiv);
        tblLessons.setPlaceholder(new Text("U heeft nog geen geplande lessen"));
        tblLessons.setPrefHeight(600);
        tblLessons.setPrefWidth(800);

        delete = new Button("Les Afzeggen");
        delete.disableProperty().bind(Bindings.size(Session.oblLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty
        delete.disableProperty().bind(Bindings.isEmpty(tblLessons.getSelectionModel().getSelectedItems()));

        delete.setOnAction(event -> {
            LocalDate pkDtm = tblLessons.getSelectionModel().getSelectedItem().getDtm();
            String fkTtr = Session.currentUser.getUserNm();
            String pkStu = tblLessons.getSelectionModel().getSelectedItem().getStu();
            String pkPer = tblLessons.getSelectionModel().getSelectedItem().getPer();
            Session.oblLessons.remove(tblLessons.getSelectionModel().getSelectedIndex());
            String sqlDelete = "UPDATE lessen " +
                    "SET ttr = NULL" +
                    "WHERE dtm = to_date('"+pkDtm+"', 'yyyy/mm/dd') " +
                    "AND stu = '"+pkStu+"' "+
                    "AND ttr = UPPER('" +fkTtr+"') " +
                    "AND periode_per = '" +pkPer+ "' ";
            System.out.println(sqlDelete);
            int result = conn.executeDML(sqlDelete);
        });

        vbox.getChildren().addAll(label, tblLessons, delete);
        this.setContent(vbox);

    }
}
