package techpal.controllers;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class BaseController {

    public BaseController() {
    }

    public static void alert(String title, String content, Alert.AlertType alertType) {
        //made this method to keep things DRY
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void setGrid(GridPane grid){
        //some views have the same grid layout
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setMinHeight(800);
        grid.setMinWidth(800);
        grid.getColumnConstraints().add(new ColumnConstraints(200));
    }
}
