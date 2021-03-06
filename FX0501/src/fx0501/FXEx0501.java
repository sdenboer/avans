package fx0501;


import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class FXEx0501 extends VBox {
    private final TextField value1, value2;
    private final Button button;
    private final Text message;

    public FXEx0501 (HBox h) {
        //assign values
        value1 = new TextField();
        value2 = new TextField();
        button = new Button("Flawless Algorithm");
        message = new Text();

        //set action on button
        button.setOnAction(event -> {

            if (value1.getText().isEmpty() || value2.getText().isEmpty()) {
                message.setText("Please enter a number");
            } else {
                if (Integer.parseInt(value1.getText()) > Integer.parseInt(value2.getText())) {
                    message.setText("Left is bigger");
                } else {
                    message.setText("Right is bigger");
                }
            }
        });

        GridPane p = new GridPane();

        p.add(value1, 0, 0);
        p.add(value2, 1, 0);
        p.add(button, 2,0);
        p.add(message, 1,1);

        h.getChildren().add(p);
    }

}
