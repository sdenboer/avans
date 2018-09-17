package fx0401;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FX0401 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 500, 500);
        
        primaryStage.setTitle("FX0401");
        primaryStage.setScene(scene);
        primaryStage.show();
        
//        new FXEx0401(root);
        new FXEx0402(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
