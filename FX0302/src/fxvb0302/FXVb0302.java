package fxvb0302;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

    public class FXVb0302 extends Application {
        
        @Override
        public void start(Stage primaryStage) {
            Pane root = new Pane();
            Scene scene = new Scene(root, 500, 500);
            
            primaryStage.setTitle("FXVb0302");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            new Vb030506(root);

        }
        
        public static void main(String[] args) {
            launch(args);
        }
    }

