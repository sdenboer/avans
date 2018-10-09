package techpal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TechPal extends Application {

    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setTitle("TechPal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
