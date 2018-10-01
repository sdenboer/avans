package fx0501;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FXEx0512 {
    private final Label label;
    private final TextField askYear;
    private final Button confirmYear;
    private final Text romanNumerals;
    private final HBox hbox;
    
    public FXEx0512 (VBox p) {
        label = new Label("Enter a year");
        askYear = new TextField();
        confirmYear = new Button("convert");
        hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.getChildren().addAll(label, askYear, confirmYear);
        romanNumerals = new Text("");

        confirmYear.setOnAction(event -> {
            int intYear = Integer.parseInt(askYear.getText());
            StringBuilder string = new StringBuilder();
            while (intYear >= 1000) {
                string.append("M");
                intYear -= 1000;
            }
            while (intYear >= 900) {
                string.append("CM");
                intYear -= 900;
            }
            while (intYear >= 500) {
                string.append("D");
                intYear -= 500;
            }
            while (intYear >= 400) {
                string.append("CD");
                intYear -= 400;
            }
            while (intYear >= 100) {
                string.append("C");
                intYear -= 100;
            }
            while (intYear >= 90) {
                string.append("XC");
                intYear -= 90;
            }
            while (intYear >= 50) {
                string.append("L");
                intYear -= 50;
            }
            while (intYear >= 40) {
                string.append("XL");
                intYear -= 40;
            }
            while (intYear >= 10) {
                string.append("X");
                intYear -= 10;
            }
            while (intYear >= 9) {
                string.append("IX");
                intYear -= 9;
            }
            while (intYear >= 5) {
                string.append("V");
                intYear -= 5;
            }
            while (intYear >= 4) {
                string.append("IV");
                intYear -= 4;
            }
            while (intYear >= 1) {
                string.append("I");
                intYear -= 1;
            }
            romanNumerals.setText(string.toString());
        });
        p.getChildren().addAll(hbox, romanNumerals);
        p.setAlignment(Pos.BASELINE_CENTER);
        p.setSpacing(10);
    }

}
