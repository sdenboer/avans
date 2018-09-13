package fxvb0303;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Vb0316 {
    private final Button m, f;
    private final Text mC, fC;
    int fI;
    int mI;
    
    public void increment(int e, Text eC) {
        eC.setText("" + e);
    }
    
    public Vb0316 (Pane p) {
        m = new Button("male");
        f = new Button ("female");
        m.relocate(30, 200);
        f.relocate(200, 200);
                
        mC = new Text("0");
        mC.relocate(30, 100);
        fC = new Text("0");
        fC.relocate(200, 100);
        
        m.setOnAction(e-> {
            mI++;
            increment(mI, mC);          
        });
        
        f.setOnAction(e-> {
            fI++; 
            increment(fI, fC);
        });
        
        
        p.getChildren().addAll(m, f, mC, fC);
    }
}
