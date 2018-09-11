package fxvb0303;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Vb0312 {
    
    private final Button moveLeft, moveRight, increase, decrease;
    private final Circle circle;
    int x = 100;
    
    public Vb0312 (Pane p) {
        circle = new Circle();
        moveLeft = new Button("L");
        moveLeft.relocate(150, 200);
        moveRight = new Button("R");
        moveRight.relocate(190, 200);
        increase = new Button("+");
        increase.relocate(230, 200);
        decrease = new Button("-");
        decrease.relocate(270, 200);
        
  
        
        
        circle.setCenterX(x);
        circle.setCenterY(100);
        circle.setRadius(x);
        circle.setFill(Color.RED);
        
        moveLeft.setOnAction( e-> {
            x--;
            circle.setCenterX(x);
        });
        moveRight.setOnAction( e-> {
            x++;
            circle.setCenterX(x);
        });
        increase.setOnAction( e-> {
            x++;
            circle.setRadius(x);
        });        
        
        decrease.setOnAction( e-> {
            x--;
            circle.setRadius(x);
        });
        
        
        p.getChildren().addAll(circle,moveLeft, moveRight, increase, decrease);
    }
}