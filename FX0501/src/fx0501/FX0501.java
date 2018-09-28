package fx0501;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FX0501 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
//        GridPane root = new GridPane();
        VBox rootVBox = new VBox();
        Scene scene = new Scene(rootVBox, 800, 800);
        
        primaryStage.setTitle("FX0501");
        primaryStage.setScene(scene);
        primaryStage.show();
        
//        new FXExtraPane(root);
//        new FXEx0501(root);
//        new FXEx0502(root);
//        new FXEx0503(root);
//          new FXEx0504(rootVBox);
//          new FXEx0505(root);
//        new FXEx0506(root);
        new FXEx0507(rootVBox);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
