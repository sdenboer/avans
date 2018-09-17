package fxvb0202;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 *
 * @author sven
 */
public class FXVb0202 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        FlowPane root = new FlowPane();        
        Scene scene = new Scene(root, 300, 250);
        
        new Vb0202(root);
        new Vb0210(root);
        
        primaryStage.setTitle("Vb0202");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
