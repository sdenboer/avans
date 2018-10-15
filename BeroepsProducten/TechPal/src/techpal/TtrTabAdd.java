package techpal;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import techpal.Models.Lesson;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class TtrTabAdd extends Tab {
    private TableView<Lesson> tblLessons;
    private Text title;
    private VBox vbox;
    private Button addLesson;
    private DbConnector conn;
    private GridPane grid;

    public TtrTabAdd(TtrMainView tabPane) {
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
        tblLessons.setItems(Session.oblAvailableLessons);
        tblLessons.getColumns().addAll(colDate, colPer, colProg, colTstl, colStuNm, colStuPc, colStuHnr, colStuNiv);
        tblLessons.setPlaceholder(new Text("Er zijn geen lessen beschikbaar voor u"));
        tblLessons.setPrefHeight(500);
        tblLessons.setPrefWidth(800);

        addLesson = new Button("Les Afspreken");
        addLesson.disableProperty().bind(Bindings.size(Session.oblLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty
        addLesson.disableProperty().bind(Bindings.isEmpty(tblLessons.getSelectionModel().getSelectedItems()));

        addLesson.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TechPal");
            alert.setHeaderText(null);
            alert.setContentText("Weet u het zeker?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Lesson lesson = new Lesson();
                lesson.setStu(tblLessons.getSelectionModel().getSelectedItem().getStu());
                lesson.setDtm(tblLessons.getSelectionModel().getSelectedItem().getDtm());
                lesson.setPer(tblLessons.getSelectionModel().getSelectedItem().getPer());
                lesson.setProg(tblLessons.getSelectionModel().getSelectedItem().getProg());
                lesson.setIsFin(0);
                lesson.setTstl(tblLessons.getSelectionModel().getSelectedItem().getTstl());
                lesson.setTtr(Session.currentUser.getUserNm());
                lesson.setStuHnr(tblLessons.getSelectionModel().getSelectedItem().getStuHnr());
                lesson.setStuNm(tblLessons.getSelectionModel().getSelectedItem().getStuNm());
                lesson.setStuNiv(tblLessons.getSelectionModel().getSelectedItem().getStuNiv());
                lesson.setStuPc(tblLessons.getSelectionModel().getSelectedItem().getStuPc());
                Session.oblAvailableLessons.remove(tblLessons.getSelectionModel().getSelectedIndex());
                Session.oblLessons.add(lesson);
                String sqlAdd = "UPDATE lessen " +
                        "SET ttr = UPPER('" + lesson.getTtr() + "') " +
                        "WHERE dtm = to_date('" + lesson.getDtm() + "', 'yyyy/mm/dd') " +
                        "AND stu = '" + lesson.getStu() + "' " +
                        "AND periode_per = '" + lesson.getPer() + "' ";
                System.out.println(sqlAdd);
                int result = conn.executeDML(sqlAdd);
                tabPane.getSelectionModel().select(0);
            }
        });

        grid = new GridPane();
        grid.add(addLesson, 0, 0);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(50);

        vbox.getChildren().addAll(title, tblLessons, addLesson);
        this.setContent(vbox);

    }
}
