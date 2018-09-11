
package fxvb0301;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Vb0302 {
    
    public final int negMax;
    public int positive;
    private final Text res;
    
    public Vb0302 (Pane p) {
        negMax = Integer.MIN_VALUE;
        positive = negMax -1 ;
       
        res = new Text(50, 120, "Some Java logic: " + negMax + " - 1 = " + positive);
        
        p.getChildren().add(res);
    }
}
