package fx0501;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;

public class FXEx0508 {
    private final TextField input;
    private final Button check;
    private final Text outcome;
    private Double dblPrice;
    private void check(Double input) {
        dblPrice = 2.00;
        if (input >= 10 && input < 50) {
            dblPrice -= 0.5;
        } else if (input >= 50 && input < 100) {
            dblPrice -= 0.75;
        } else if (input >= 100) {
            dblPrice -= 0.90;
        }
    }

    public FXEx0508 (GridPane p) {
        input = new TextField();
        check = new Button("check price");
        outcome = new Text();

        check.setOnAction(event -> {
            Double dblInput = Double.parseDouble(input.getText());
            check(dblInput);
            outcome.setText("The individual price = €" + String.format("%.2f", dblPrice) +
                    "\nThe total price = €" + String.format("%.2f", (dblInput * dblPrice)));
        });

        p.add(input, 0, 0);
        p.add(check, 1, 0);
        p.add(outcome, 0, 1);
    }
}
