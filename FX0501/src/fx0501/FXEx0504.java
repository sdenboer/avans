package fx0501;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FXEx0504 {
    private final Canvas canvas;
    private final GraphicsContext gc;

    public FXEx0504 (VBox p) {
        canvas = new Canvas(500, 500);
        gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < 10; i++) {
            gc.strokeRect(150-10*i, 150-10*i, 20 * i, 20 * i);
        }
        p.getChildren().addAll(canvas);
    }
}

