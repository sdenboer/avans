package fxvb0202;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class Vb0202 {
    private final Button firstName;
    private final Button lastName;
    private final Button clearFirst;
    private final Button clearLast;
    private final TextField firstNameText;
    private final TextField lastNameText;
    

    public Vb0202(FlowPane p) {
        firstName = new Button ("Voor");
        lastName = new Button ("Achter");
        clearFirst = new Button ("clear");
        clearLast = new Button ("clear");
        firstNameText = new TextField();
        lastNameText = new TextField();
        
        firstName.setOnAction(event -> {
            firstNameText.setText("Sven");
        });
        
        lastName.setOnAction(event -> {
             lastNameText.setText("den Boer");
        });
        
        clearFirst.setOnAction(event -> {
            firstNameText.clear();
        });
        
        clearLast.setOnAction(event-> {
            lastNameText.clear();
        });
        
        
        
        
        p.getChildren().add(firstName);
        p.getChildren().add(firstNameText);
        p.getChildren().add(clearFirst);
        p.getChildren().add(lastName);
        p.getChildren().add(lastNameText);
        p.getChildren().add(clearLast);

    }
}
