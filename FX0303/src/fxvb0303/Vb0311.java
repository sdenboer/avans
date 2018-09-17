package fxvb0303;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Vb0311 {
    
    private final Button move;
    private final Circle circle;
    int x = 100;
    
    public Vb0311 (Pane p) {
        move = new Button();
        circle = new Circle();
        
        
        circle.setCenterX(x);
        circle.setCenterY(100);
        circle.setRadius(50.0f);
        circle.setFill(Color.RED);
        
        move.setOnAction( e-> {
            x++;
            circle.setCenterX(x);
        });
        
        
        p.getChildren().addAll(move, circle);
    }
}