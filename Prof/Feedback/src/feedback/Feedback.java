package feedback;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Feedback extends Application {
    
    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        new Game(root);
        Scene scene = new Scene(root, 1920, 1080);
                scene.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                System.out.println("HELLo");
            } else {
                System.out.println("NO");
            }
        });
        
        primaryStage.setTitle("Feedback Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
