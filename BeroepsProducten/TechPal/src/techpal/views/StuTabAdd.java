package techpal.views;

import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import techpal.controllers.DbConnector;
import techpal.models.Device;
import techpal.models.Lesson;
import techpal.controllers.Session;
import techpal.controllers.Statics;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StuTabAdd extends Tab {
    private DatePicker dpDate;
    private ComboBox<String> cbxProg, cbxPer;
    private ComboBox<Device> cbxDev;
    private Text title, lblDate, lblProg, lblDevices, lblPer;
    private Button btn;
    private DbConnector conn;

    public StuTabAdd(TabView tabPane) {
        this.setText("Les toevoegen");
        GridPane grid = new GridPane();
        conn = new DbConnector();
        title = new Text("Voeg een les toe");
        title.setId("text-title");
        lblDate = new Text("Datum: ");
        lblDate.setId("text-label");
        lblDevices = new Text("Toestel: ");
        lblDevices.setId("text-label");
        lblProg = new Text("Onderwerp: ");
        lblProg.setId("text-label");
        lblPer = new Text("Tijd: ");
        lblPer.setId("text-label");
        dpDate = new DatePicker();
        dpDate.setId("datepicker");
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
            cbxProg.getItems().add(program.getProgNm()); //add all available programs to the combobox
        });
        cbxProg.getSelectionModel().selectFirst();

        cbxDev.setItems(Session.hasDevices);
        cbxDev.getSelectionModel().selectFirst();
        cbxDev.setConverter(new StringConverter<Device>() {
            //since the combobox is filled with objects, they should be converted to strings.
            @Override
            public String toString(Device object) { return object.getTstl(); }
            @Override
            public Device fromString(String string) { return null; }
        });

        Session.listPeriods.forEach(period -> {
            cbxPer.getItems().add(period.getPer());
        });
        cbxPer.getSelectionModel().selectFirst();

        btn.setOnAction(event -> {
            try {
                Lesson lesson = new Lesson();
                lesson.setStu(Session.currentStudent.getUserNm());
                lesson.setDtm(dpDate.getValue());
                lesson.setPer(cbxPer.getSelectionModel().getSelectedItem());
                lesson.setProg((cbxProg.getSelectionModel().getSelectedItem()));
                lesson.setIsFin(0);
                lesson.setTstl(cbxDev.getValue().getTstl());
                String sqlAdd = "INSERT INTO lessen (stu, dtm, periode_per, programma_prognm, isFin, tstl) " +
                        "VALUES (UPPER('"+lesson.getStu()+"'), to_date('"+lesson.getDtm()+"', 'yyyy/mm/dd')" +
                        ", '"+lesson.getPer()+"', '"+lesson.getProg()+"', "+lesson.getIsFin()+", '"+lesson.getTstl()+"')";
                Session.oblLessons.add(lesson);
                int result = conn.executeDML(sqlAdd);
                if (result == 0) {
                    Statics.alert("Oeps!", "Er is iets fout gegaan met de invoer", Alert.AlertType.INFORMATION);
                    Session.oblLessons.remove(lesson); //removes the lesson
                } else {
                    tabPane.getSelectionModel().select(0); //Return to first tab
                }
            } catch (Exception e){
                Statics.alert("Oeps!", "Er is iets fout gegaan met de invoer", Alert.AlertType.INFORMATION);
            }
        });

        Statics.setGrid(grid);
        grid.add(title, 0, 0);
        grid.add(lblDevices, 0, 11);
        grid.add(cbxDev, 1, 11);
        grid.add(lblProg, 0, 12);
        grid.add(cbxProg, 1, 12);
        grid.add(lblDate, 0, 13);
        grid.add(dpDate, 1, 13);
        grid.add(lblPer, 0, 14);
        grid.add(cbxPer, 1, 14);
        grid.add(btn, 0, 15);
        this.setContent(grid);
    }
}
