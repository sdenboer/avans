package fxvb0303;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class Vb0308 {
    
    private final Line houseRoofLeft, houseRoofRight; 
            
    private final Rectangle houseRectangle;
    
    public Vb0308 (Pane p) {

        
        houseRoofLeft = new Line(50, 0, 0, 100);
        houseRoofRight = new Line(50, 0, 100, 100);
        houseRectangle = new Rectangle(0, 100, 100, 110);
        houseRectangle.setFill(Color.BLUE);
        
        p.getChildren().addAll(houseRoofLeft, houseRoofRight, houseRectangle);
    }
}
