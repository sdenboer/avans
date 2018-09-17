package fx0401;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class FxEx0401 {
    
    private final TextField input, output, tax;
    private final Button clear;
    private final Text strLabelMonth, strLabelYear, strLabelTax;
    double intOutput, intTax;
    private final int box1, box3;
    
    public FxEx0401 (GridPane p) {
    
    p.setVgap(5);
    p.setHgap(5);
    p.setPadding(new Insets(10,10,10,10));
    
    box1 = 20_142;
    box3 = 68_507;
    
    input = new TextField();
    output = new TextField();
    tax = new TextField();
    output.setEditable(false);
    tax.setEditable(false); 
    
    clear = new Button("clear");
    strLabelMonth = new Text("Salaris per maand:");
    strLabelYear = new Text("Salaris per jaar:");
    strLabelTax = new Text("Belasting:");
    
    input.setOnAction(e-> {
    String strInput = input.getText();
    double intInput = Double.parseDouble(strInput);
    double x = intInput * 12;
    
    
    if (x < box1) {
        intTax = x * .3655;
        intOutput = x - intTax;
    } else if (x >= box1 && x < box3) {
        intTax = x * .4085;
        intOutput = x - intTax;
    } else {
        intTax = x * .5195;
        intOutput = x - intTax;
    }
    
    
    output.setText(String.format("%.2f", intOutput));
    tax.setText(String.format("%.2f", intTax));
    });
   
    
    clear.setOnAction(e->{
        input.clear();
        output.clear();
    });
    
    p.add(strLabelMonth, 0, 0);
    p.add(input, 1, 0);
    p.add(strLabelYear, 0, 1);
    p.add(output, 1, 1);
    p.add(strLabelTax, 0,2);
    p.add(tax, 1,2);
    p.add(clear, 1, 3);
    
    
    }
}
