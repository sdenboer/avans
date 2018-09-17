
package fxvb0302;

import java.util.Arrays;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Vb030506 {
    private final Button 
            plusknop, 
            productknop, 
            verschilknop, 
            quotientknop, 
            restknop, 
            clearknop;
    
    private final TextField invoervak1, invoervak2, resultaatvak;
        // method returning an array of integers from the input
        public int[] getInput(){ 
            String invoer1 = invoervak1.getText();
            int getal1 = Integer.parseInt(invoer1);
            String invoer2 = invoervak2.getText();
            int getal2 = Integer.parseInt(invoer2);
            return new int[] {getal1, getal2};
        }
    
    public  Vb030506 (Pane p) {
        plusknop = new Button ( "+" );
        productknop = new Button ("x");
        verschilknop = new Button ("v");
        quotientknop = new Button("/");
        restknop = new Button ("%");
        clearknop = new Button ("CLEAR");
        invoervak1 = new TextField();
        invoervak2 = new TextField();
        resultaatvak = new TextField();
        
        invoervak1.relocate(20, 20);
        invoervak2.relocate(20, 50);
        plusknop.relocate(20, 80);
        productknop.relocate(50, 80);
        verschilknop.relocate(80, 80);
        quotientknop.relocate(110, 80);
        restknop.relocate(140, 80);
        clearknop.relocate(180, 80);
        resultaatvak.relocate(20, 110);
       
        invoervak1.setAlignment(Pos.CENTER_RIGHT);
        invoervak2.setAlignment(Pos.CENTER_RIGHT);
        resultaatvak.setAlignment(Pos.CENTER_RIGHT);
                
        plusknop.setOnAction(e -> {
            int res[] = getInput();
            resultaatvak.setText("" + (res[0] + res[1]));
        });
        
        productknop.setOnAction(e -> {
            int res[] = getInput();
            resultaatvak.setText("" + (res[0] * res[1]));
        });
        
        verschilknop.setOnAction(e -> {
            int res[] = getInput();
            Arrays.sort(res);
            resultaatvak.setText("" + (res[1] - res[0]));
        });
        
        quotientknop.setOnAction(e -> {
            int res[] = getInput();
            resultaatvak.setText("" + (res[0] / res[1]));
        });
        
        restknop.setOnAction(e -> {
            int res[] = getInput();
            resultaatvak.setText("" + (res[0] % res[1]));
        });
        
        clearknop.setOnAction(e-> {
            invoervak1.clear();
            invoervak2.clear();
            resultaatvak.clear();
        });
        
        
        p.getChildren().addAll(
                invoervak1, 
                invoervak2, 
                plusknop, 
                productknop, 
                verschilknop, 
                quotientknop,
                restknop,
                clearknop,
                resultaatvak
        );
    }   
}
