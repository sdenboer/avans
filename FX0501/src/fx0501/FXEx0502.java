package fx0501;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.Arrays;

public class FXEx0502 extends VBox {
    private final TextField value;
    private final Text outcome;

    public FXEx0502(HBox h) {
        value = new TextField();
        outcome = new Text();

        value.setOnAction(event -> {
            int[] intValues = Arrays.stream(value.getText().split(", ")).mapToInt(Integer::parseInt).toArray();
            int temp;

            for (int i = 1; i < intValues.length; i++) {
                int key = intValues[i];
                int j = i -1;
                while (j >= 0 && key < intValues[j]) {
                    temp = intValues[j];
                    intValues[j] = intValues[j + 1];
                    intValues[j+1] = temp;
                    j--;
                }
            }
            StringBuilder str = new StringBuilder();
            for (int i : intValues) {
                String word = String.valueOf(i) + "    ";
                str.append(word);
            }
            outcome.setText(str.toString());
        });

        GridPane p = new GridPane();
        p.add(value, 0, 0);
        p.add(outcome, 1, 0);

        h.getChildren().add(p);
    }

}
