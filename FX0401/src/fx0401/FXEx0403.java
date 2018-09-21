package fx0401;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class FXEx0403 {
    private final Text label;
    private final Button button;
    private final TextField population;
    int year = 2018;
    long total = (long) 3e9;
    
    public FXEx0403 (GridPane p) {
        System.out.println(total);
        label = new Text(String.format(String.valueOf(year)));
        button = new Button(String.format(String.valueOf(year + 1)));
        population = new TextField(String.format("%,d", total));
        
        button.setOnAction(e-> {
            year += 1;
            total *= 1.0165;
            population.setText(String.format("%,d", total));
            label.setText(String.valueOf(year));
            button.setText(String.valueOf(year+1));
        });
        
        p.add(label,0,0);
        p.add(population, 1, 0);
        p.add(button, 0, 1);
    }
}
