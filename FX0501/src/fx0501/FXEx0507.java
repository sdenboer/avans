package fx0501;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FXEx0507 {
    private final Label label;
    private final TextField input;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final HBox hbox;

    public FXEx0507 (VBox p) {
        label = new Label("Aantal vierkanten: ");
        input = new TextField();
        input.setAlignment(Pos.CENTER_RIGHT);
        input.setPrefWidth(40);

        hbox = new HBox();
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.getChildren().addAll(label, input);

        canvas = new Canvas(800, 800);
        gc = canvas.getGraphicsContext2D();
        p.getChildren().addAll(hbox, canvas);

        input.setOnAction(event -> {
            int intInput = Integer.parseInt(input.getText());
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (int i = 1; i <= intInput; i++) {
                gc.strokeRect(410-10*i, 410-10*i, 20*i, 20*i);
            }
        });
    }
}
