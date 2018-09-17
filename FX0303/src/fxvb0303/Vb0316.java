package fxvb0303;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Vb0316 {
    private final Button m, f, t;
    private final Text mC, fC, tC;
    int fI;
    int mI;
    
    public void increment(int e, Text eC) {
        eC.setText("" + e);
    }
    
    public Vb0316 (Pane p) {
        m = new Button("male");
        m.relocate(30, 200);        
        f = new Button("female");
        f.relocate(200, 200);        
        t = new Button("total");
        t.relocate(100, 200);

                
        mC = new Text("0");
        mC.relocate(30, 100);
        fC = new Text("0");
        fC.relocate(200, 100);
        tC = new Text("");
        tC.relocate(100, 100);
        
        m.setOnAction(e-> {
            mI++;
            increment(mI, mC);          
        });
        
        f.setOnAction(e-> {
            fI++; 
            increment(fI, fC);
        });
        
        t.setOnAction(e-> {
            tC.setText("" + (mI + fI));
        });
        
        
        p.getChildren().addAll(m, f, t, mC, fC, tC);
    }
}
