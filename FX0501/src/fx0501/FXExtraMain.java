package fx0501;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public final class FXExtraMain extends GridPane {
    private final TextField inputA, inputB;
    private final Text times;
    private final List<Text> labels;
    private final HBox hbox;
    private final GridPane grid;

    public FXExtraMain() {
    //INITIALIZE//    
        hbox = new HBox();
        hbox.setSpacing(10);
        grid = new GridPane(); 
        labels = new ArrayList<>();
        times = new Text("x");
        inputA = new TextField();
        inputA.setPrefWidth(60);
        inputB = new TextField();
        inputB.setPrefWidth(60);
        
    //EVENT LISTENERS 
        inputA.setOnAction(e-> {
            inputB.requestFocus();
        });   
        inputB.setOnAction(e-> {
            labels.forEach((_item) -> {
                grid.getChildren().remove(_item);
            });
            labels.clear();
            int x = Integer.parseInt(inputA.getText()); 
            int y = Integer.parseInt(inputB.getText());
            for(int i = 1; i <= x; i++) {
                labels.add(new Text(i + " x " + y + " = " + String.format("%2d", i * y)));
            } 
            labels.forEach((_item) -> {
                grid.add(_item, 0, labels.indexOf(_item) + 1);
            });
        });

    //ASSIGN
        hbox.getChildren().addAll(inputA, times, inputB);
        grid.add(hbox, 0, 0);
        
        this.add(grid, 0, 0);
    }
}
