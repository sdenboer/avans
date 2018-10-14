package techpal;

import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import techpal.Models.Device;
import techpal.Models.Lesson;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StuTabAdd extends Tab {
    private DatePicker dpDate;
    private ComboBox<String> cbxProg, cbxPer;
    private ComboBox<Device> cbxDev;
    private Text lblTitle, lblDate, lblProg, lblDev, lblPer;
    private Button btn;
    private DbConnector conn;

    public StuTabAdd(StuMainView tabPane) {
        this.setText("Les Toevoegen");
        GridPane grid = new GridPane();
        conn = new DbConnector();
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
        dpDate.setDayCellFactory(callback -> new DateCell() { //disabling all dates before today
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.compareTo(LocalDate.now()) < 0);
                long x = ChronoUnit.DAYS.between( //hovering the mouse counts the days between today and the selected date
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

        cbxDev.setItems(Session.hasDevices);
        cbxDev.setConverter(new StringConverter<Device>() {
            @Override
            public String toString(Device object) { return object.getTstl(); }
            @Override
            public Device fromString(String string) { return null; }
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
            lesson.setTstl(cbxDev.getValue().getTstl());
            String sqlAdd = "INSERT INTO lessen (stu, dtm, periode_per, programma_prognm, isFin, tstl) " +
                    "VALUES (UPPER('"+lesson.getStu()+"'), to_date('"+lesson.getDtm()+"', 'yyyy/mm/dd')" +
                    ", '"+lesson.getPer()+"', '"+lesson.getProg()+"', "+lesson.getIsFin()+", '"+lesson.getTstl()+"')";
            System.out.println(sqlAdd);
            Session.oblLessons.add(lesson);
            int result = conn.executeDML(sqlAdd);
            if (result == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("TechPal");
                alert.setHeaderText(null);
                alert.setContentText("Er is iets fout gegaan met de invoer");
                alert.showAndWait();
            } else {
                tabPane.getSelectionModel().select(0);
            }
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
