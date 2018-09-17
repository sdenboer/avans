package fxvb0301;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

    public class FXVb0301 extends Application {
        
        @Override
        public void start(Stage primaryStage) {
            Pane root = new Pane();
            Scene scene = new Scene(root, 500, 500);
            
            primaryStage.setTitle("FXVb0301");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            new Vb0301(root);
            new Vb0302(root);
            new Vb030304(root);
        }
        
        public static void main(String[] args) {
            launch(args);
        }
    }

