package techpal;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import techpal.Models.Lesson;

import java.time.LocalDate;

public class TtrTabAdd extends Tab {
    private TableView<Lesson> tblLessons;
    private Text label;
    private VBox vbox;
    private Button addLesson;
    private DbConnector conn;

    public TtrTabAdd(TtrMainView tabPane) {
        this.setText("Mijn lessen");
        conn = new DbConnector();
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        label = new Text("Beschikbare Lessen: ");
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
        tblLessons.setItems(Session.oblAvailableLessons);
        tblLessons.getColumns().addAll(colDate, colPer, colProg, colTstl, colStuNm, colStuPc, colStuHnr);
        tblLessons.setPlaceholder(new Text("U heeft nog geen geplande lessen"));

        addLesson = new Button("Les Afspreken");
        addLesson.disableProperty().bind(Bindings.size(Session.oblLessons).isEqualTo(0)); //disables button when the arraylist & tableview is empty
        addLesson.disableProperty().bind(Bindings.isEmpty(tblLessons.getSelectionModel().getSelectedItems()));

        addLesson.setOnAction(event -> {
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
                    "SET ttr = UPPER('" +lesson.getTtr()+"') " +
                    "WHERE dtm = to_date('"+lesson.getDtm()+"', 'yyyy/mm/dd') " +
                    "AND stu = '"+lesson.getStu()+"' "+
                    "AND periode_per = '" +lesson.getPer()+ "' ";
            System.out.println(sqlAdd);
            int result = conn.executeDML(sqlAdd);
            System.out.println(result);
        });

        vbox.getChildren().addAll(label, tblLessons, addLesson);
        this.setContent(vbox);

    }
}
