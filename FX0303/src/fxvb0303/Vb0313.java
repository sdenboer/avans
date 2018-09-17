package fxvb0303;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Vb0313 {
    public final Rectangle field, charlotte, welmer, zazie;
    public final Text cN, wN, zN, maxH, minH;
    
    public Vb0313 (Pane p) {
        
        field = new Rectangle(200, 200);
        charlotte = new Rectangle (30, 72);
        cN = new Text("C");
        welmer = new Rectangle(30, 65);
        wN = new Text("W");
        zazie = new Rectangle(30, 95);
        zN = new Text("Z");
        maxH  = new Text("200cm");
        minH = new Text("0cm");
        
        
        field.setFill(Color.WHITE);
        field.setStroke(Color.BLACK);
        field.setStrokeWidth(1);
        
        charlotte.setFill(Color.RED);
        charlotte.relocate(20, 128);
        cN.relocate(30, 210);
        
        welmer.setFill(Color.BLUE);
        welmer.relocate(70, 135);
        wN.relocate(80, 210);
        
        zazie.setFill(Color.GREEN);
        zazie.relocate(120, 105);
        zN.relocate(130, 210);
        
        maxH.relocate(205, 0);
        minH.relocate(205, 190);
        
        p.getChildren().addAll(field, charlotte, welmer, zazie, cN, wN, zN, maxH, minH);
    } 
}
