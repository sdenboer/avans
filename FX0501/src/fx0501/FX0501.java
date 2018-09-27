package fx0501;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FX0501 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 800, 800);
        
        primaryStage.setTitle("FX0501");
        primaryStage.setScene(scene);
        primaryStage.show();
        
//        new FXExtraPane(root);
        new FXEx0501(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
