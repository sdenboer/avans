package techpal.views;

import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import techpal.controllers.*;
import techpal.models.Device;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StuTabAdd extends Tab {
    private DatePicker dpDate;
    private ComboBox<String> cbxProg, cbxPer;
    private ComboBox<Device> cbxDev;
    private Text title, lblDate, lblProg, lblDevices, lblPer;
    private Button btn;

    public StuTabAdd(TabView tabPane) {
        this.setText("Les toevoegen");
        GridPane grid = new GridPane();
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

        ProgramsController.alPrograms.forEach(program -> {
            cbxProg.getItems().add(program.getProgNm()); //add all available programs to the combobox
        });
        cbxProg.getSelectionModel().selectFirst();

        cbxDev.setItems(DevicesController.olHasDevices);
        cbxDev.getSelectionModel().selectFirst(); //sets the first device as a default in the combobox
        cbxDev.setConverter(new StringConverter<Device>() {
            //since the combobox is filled with objects, they should be converted to strings.
            @Override
            public String toString(Device object) { return object.getTstl(); }
            @Override
            public Device fromString(String string) { return null; } //no need for this
        });

        PeriodsController.alPeriods.forEach(period -> cbxPer.getItems().add(period.getPer()));
        cbxPer.getSelectionModel().selectFirst();

        btn.setOnAction(event -> {
            try {
                LessonsController.addNewLesson(dpDate.getValue(),
                        cbxPer.getSelectionModel().getSelectedItem(),
                        cbxProg.getSelectionModel().getSelectedItem(),
                        cbxDev.getValue().getTstl(),
                        tabPane);
            } catch (Exception e){
                BaseController.alert("Oeps!", "Er is iets fout gegaan met de invoer", Alert.AlertType.INFORMATION);
            }
        });

        BaseController.setGrid(grid);
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
