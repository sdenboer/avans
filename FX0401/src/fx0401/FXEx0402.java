package fx0401;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class FXEx0402 {
    
    private final TextField input1, input2, output;
    private final Text inp1Label, inp2Label, outLabel;
    private final Button clear;

    
    public FXEx0402 (GridPane p) {
        
        p.setVgap(5);
        p.setHgap(5);
        p.setPadding(new Insets(10,10,10,10));
        input1 = new TextField();
        input2 = new TextField();
        output = new TextField();
        output.setEditable(false);
        inp1Label = new Text("number 1:");
        inp2Label = new Text("number 2:");
        outLabel = new Text("average:");
        clear = new Button("clear");
       
        
        input2.setOnAction(e-> {
            double dblInput1 = Double.parseDouble(input1.getText());
            double dblInput2 = Double.parseDouble(input2.getText());
            double average = (dblInput1 + dblInput2) / 2;
            output.setText(String.format("%.2f", average));
        });
        
        clear.setOnAction(e->{
            input1.clear();
            input2.clear();
            output.clear();
        });
        
        p.add(inp1Label, 0, 0);
        p.add(input1, 1, 0);
        p.add(inp2Label, 0, 1);
        p.add(input2, 1, 1);
        p.add(outLabel, 0,2);
        p.add(output, 1,2);
        p.add(clear, 1, 3);
        
    }
}
