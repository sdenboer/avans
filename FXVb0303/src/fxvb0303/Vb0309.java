package fxvb0303;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Vb0309 {
    
    private final Rectangle firstRectangle, secondRectangle, thirdRectangle;
    
    public Vb0309 (Pane p) {

        firstRectangle = new Rectangle(100, 30);
        firstRectangle.relocate(50, 50);
        firstRectangle.setFill(Color.RED);
        
        secondRectangle = new Rectangle(100, 30);
        secondRectangle.relocate(50, 80);
        secondRectangle.setFill(Color.WHITE);
        
        thirdRectangle = new Rectangle(100, 30);
        thirdRectangle.relocate(50, 110);
        thirdRectangle.setFill(Color.BLUE); 
        
        p.getChildren().addAll(firstRectangle, secondRectangle, thirdRectangle);
    }
}