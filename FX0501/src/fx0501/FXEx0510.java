package fx0501;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;


public class FXEx0510 {
    private final Canvas canvas;
    private final GraphicsContext gc;

    public FXEx0510(GridPane p) {
        canvas = new Canvas(800, 800);
        gc = canvas.getGraphicsContext2D();

        int x = 400;
        int y = x + 10;
        for (int i = 2; i < 80; i++) {
            gc.strokeLine(x, x, x, y); //vertical
            gc.strokeLine(x, y, y, y); //horizontal
            x = y;
            y = i % 2 != 0 ? y + i * 10 : y - i * 10;
        }

    p.add(canvas, 0, 0);

    }

}
