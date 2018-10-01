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
        

        VBox root = new VBox();
        Scene scene = new Scene(root, 800, 800);
//        GridPane root = new GridPane();
//        Scene scene = new Scene(root, 800, 800);
        
        primaryStage.setTitle("FX0501");
        primaryStage.setScene(scene);
        primaryStage.show();
        
//        new FXExtraPane(root);
//        new FXEx0501(root);
//        new FXEx0502(root);
//        new FXEx0503(root);
//        new FXEx0504(rootVBox);
//        new FXEx0505(root);
//        new FXEx0506(root);
//        new FXEx0507(rootVBox);
//        new FXEx0508(root);
//        new FXEx0509(root);
//        new FXEx0510(root);
//        new FXEx0511(root);
        new FXEx0512(root);

    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
