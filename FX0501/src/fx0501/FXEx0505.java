package fx0501;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class FXEx0505 {
    private final List<Text> text;

    public FXEx0505 (GridPane p) {
        text =  new ArrayList<>();
        Double dbl = 10.00;
        for (int i = 1; i < 9; i++) {
            dbl += 0.25;
            text.add(new Text(String.format("%2d   %.2f", i, dbl)));

        }
        text.forEach((_item) -> {
            p.add(_item, 0, text.indexOf(_item));
        });
    }
}
