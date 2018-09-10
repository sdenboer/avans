package fixedgrowth;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
;

public class FixedGrowth extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        FlowPane root = new FlowPane();
        Scene scene = new Scene(root, 200, 900);
        
        primaryStage.setTitle("Fixed or Growth?");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        new Quiz(root);
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
