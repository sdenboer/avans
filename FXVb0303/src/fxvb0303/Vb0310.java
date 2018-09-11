package fxvb0303;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Vb0310 {
    
    private final Rectangle playBoard, ws1, ws2, ws3, ws4;
    
    public Vb0310 (Pane p) {
        
        playBoard = new Rectangle (180, 180);
        playBoard.setStroke(Color.BLACK);
        playBoard.setStrokeWidth(1);
        
        ws1 = new Rectangle (20, 60);
        ws1.setFill(Color.WHITE);
        ws1.relocate(60, 0);
        
        ws2 = new Rectangle (20, 60);
        ws2.setFill(Color.WHITE);
        ws2.relocate(0, 60);
        
        ws3 = new Rectangle (20, 60);
        ws3.setFill(Color.WHITE);
        ws3.relocate(120, 60);
        
        ws4 = new Rectangle (60, 60);
        ws4.setFill(Color.WHITE);
        ws4.relocate(60, 120);
        
 
        
        
         
        
        p.getChildren().addAll(playBoard, ws1, ws2, ws3, ws4);
    }
}