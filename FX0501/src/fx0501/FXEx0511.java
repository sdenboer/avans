package fx0501;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;

public class FXEx0511 {
    private final Canvas canvas;
    private final GraphicsContext gc;

    public FXEx0511(GridPane p) {
        canvas = new Canvas(800, 800);
        gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < 80; i++) {
            gc.strokeOval(400 - 5 * i, 400 - 5 * i, i * 10, i * 10);
        }
        p.add(canvas, 0, 0);
    }
}
