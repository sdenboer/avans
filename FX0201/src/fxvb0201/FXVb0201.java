package fxvb0201;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class FXVb0201 extends Application {

    @Override
    public void start(Stage primaryStage) {
        FlowPane root = new FlowPane();
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Eerste applicatie");
        primaryStage.show();

        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
