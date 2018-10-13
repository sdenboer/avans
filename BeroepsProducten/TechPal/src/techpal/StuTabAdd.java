package techpal;

import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import techpal.Models.Lesson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class StuTabAdd extends Tab {
    private DatePicker dpDate;
    private ComboBox<String> cbxProg, cbxDev, cbxPer;
    private Text lblTitle, lblDate, lblProg, lblDev, lblPer;
    private Button btn;
    private DbConnector conn;

    public StuTabAdd() {
        GridPane grid = new GridPane();
        conn = new DbConnector();
        this.setText("Les Toevoegen");
        lblTitle = new Text("Voeg een les toe");
        lblDate = new Text("Datum: ");
        lblDev = new Text("Toestel: ");
        lblProg = new Text("Onderwerp: ");
        lblPer = new Text("Tijd: ");
        dpDate = new DatePicker();
        cbxProg = new ComboBox<>();
        cbxDev = new ComboBox<>();
        cbxPer = new ComboBox<>();
        btn = new Button("Plan de les");

        dpDate.setValue(LocalDate.now());
        dpDate.setDayCellFactory(callback -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.compareTo(LocalDate.now()) < 0);
                long x = ChronoUnit.DAYS.between(
                        dpDate.getValue(), item
                );
                setTooltip(new Tooltip(
                        "Deze dag is over " + x + " dagen")
                );
            }
        });


        Session.listPrograms.forEach(program -> {
            cbxProg.getItems().add(program.getProgNm());
        });

        Session.hasDevices.forEach(device -> {
            cbxDev.getItems().add(device.getTstl());
        });

        Session.listPeriods.forEach(period -> {
            cbxPer.getItems().add(period.getPer());
        });

        btn.setOnAction(event -> {
            Lesson lesson = new Lesson();
            lesson.setStu(Session.currentUser.getUserNm());
            lesson.setDtm(dpDate.getValue());
            lesson.setPer(cbxPer.getSelectionModel().getSelectedItem());
            lesson.setProg((cbxProg.getSelectionModel().getSelectedItem()));
            lesson.setIsFin(0);
            lesson.setTstl(cbxDev.getSelectionModel().getSelectedItem());
            Session.listLessons.add(lesson);
            String sqlAdd = "INSERT INTO lessen (stu, dtm, periode_per, programma_prognm, isFin, tstl) " +
                    "VALUES ('"+lesson.getStu()+"', to_date('"+lesson.getDtm()+"', 'yyyy/mm/dd')" +
                    ", '"+lesson.getPer()+"', '"+lesson.getProg()+"', "+lesson.getIsFin()+", '"+lesson.getTstl()+"')";
            System.out.println(sqlAdd);
            int result = conn.executeDML(sqlAdd);
        });


        grid.add(lblDev, 0, 1);
        grid.add(cbxDev, 1, 1);
        grid.add(lblProg, 0, 2);
        grid.add(cbxProg, 1, 2);
        grid.add(lblDate, 0, 3);
        grid.add(dpDate, 1, 3);
        grid.add(lblPer, 0, 4);
        grid.add(cbxPer, 1, 4);
        grid.add(btn, 0, 5);

        this.setContent(grid);
    }
}
