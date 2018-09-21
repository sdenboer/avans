package feedback;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Feedback extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        FlowPane root = new FlowPane();
        Scene scene = new Scene(root, 200, 900);
        
        primaryStage.setTitle("Fixed or Growth?");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        new Game(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
