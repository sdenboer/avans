package techpal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Locale;


public class TechPal extends Application {

    public void start(Stage primaryStage) {
        Locale.setDefault(new Locale("nl"));
        AnchorPane body = new AnchorPane();
        TechPalNavBar navBar = new TechPalNavBar(body);
        VBox root = new VBox(navBar, body);
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add("techpal/style.css");
        new LoginView(body, navBar);
        primaryStage.setTitle("TechPal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
