package fx0501;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.util.Arrays;

public class FXEx0502 {
    private final TextField value;
    private final Text outcome;

    public FXEx0502(GridPane p) {
        value = new TextField();
//        value2 = new TextField();
//        value3 = new TextField();
        outcome = new Text();

        value.setOnAction(event -> {
            int[] intValues = Arrays.stream(value.getText().split(", ")).mapToInt(Integer::parseInt).toArray();
            int temp;

            for (int i = 0; i < intValues.length -1; i++) {
                for (int x = 0; x < ((intValues.length -1) -i); x++) {
                    if (intValues[x] > intValues[x + 1]) {
                        temp = intValues[x];
                        intValues[x] = intValues[x + 1];
                        intValues[x+1] = temp;
                    }
                }
            }
            StringBuilder str = new StringBuilder();
            for (int i : intValues) {
                String word = String.valueOf(i) + "    ";
                str.append(word);
            }
            outcome.setText(str.toString());
        });

        p.add(value, 0, 0);
        p.add(outcome, 1, 0);
    }

}
