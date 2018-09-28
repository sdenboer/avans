package fx0501;

import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;

public class FXEx0506 {
    private final Text text;

    public FXEx0506(GridPane p) {
        int x = 0;
        for (int i = x; i <= 1000; i++) x += i;
        text = new Text(String.format("%2d", x));
        p.add(text, 0, 0);
    }
}
