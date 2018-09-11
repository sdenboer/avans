package fxvb0303;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXVb0303 extends Application {
    
    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("FXVv0303");
        primaryStage.setScene(scene);
        primaryStage.show();
//        
//        new Vb0307(root);
//        new Vb0308(root);
//        new Vb0309(root);
//        new Vb0310(root);
//        new Vb0311(root);
//        new Vb0312(root);
//        new Vb0313(root);
          new Vb0314(root);
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
