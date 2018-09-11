package fxvb0303;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class Vb0307 {
    
    private final Line triangleLeft, triangleRight, triangleBottom;
    
    public Vb0307 (Pane p) {
        triangleBottom = new Line(0, 100, 100, 100);
        triangleLeft = new Line(50, 0, 0, 100);
        triangleRight = new Line(50, 0, 100, 100);
        
        triangleRight.setStroke(Color.BLUE);
        triangleRight.setStrokeWidth(3);
        triangleLeft.setStroke(Color.RED);
        triangleLeft.setStrokeWidth(3);
        triangleBottom.setStroke(Color.GREEN);
        triangleBottom.setStrokeWidth(3);
        
        p.getChildren().addAll(triangleRight, triangleLeft, triangleBottom);
    }
}
