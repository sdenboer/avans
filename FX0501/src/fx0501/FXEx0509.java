package fx0501;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;

public class FXEx0509 {
    private final TextField input;
    private final Text outcome;

    public FXEx0509 (GridPane p) {
        input = new TextField();
        outcome = new Text();

        input.setOnAction(event -> {
            int intInput = Integer.parseInt(input.getText());
            boolean is_leapyear = ((intInput % 4 == 0)) && (intInput % 100 != 0) || (intInput % 400 == 0);
            if (is_leapyear) {
                outcome.setText(String.format("%2d is a leap year", intInput));
            } else {
                outcome.setText(String.format("%2d is not a  leap year", intInput));
            }
        });

        p.add(input, 0, 0);
        p.add(outcome, 0, 1);
    }
}
