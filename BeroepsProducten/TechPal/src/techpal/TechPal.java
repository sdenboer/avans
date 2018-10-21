package techpal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import techpal.controllers.*;
import techpal.views.LoginView;
import techpal.views.TechPalNavBar;
import java.util.Locale;

public class TechPal extends Application {

    public void start(Stage primaryStage) {
        Locale.setDefault(new Locale("nl")); //Setting the Dutch locale.
        AnchorPane body = new AnchorPane();
        TechPalNavBar navBar = new TechPalNavBar(body);
        VBox root = new VBox(navBar, body);
        Scene scene = new Scene(root, 850, 850);
        scene.getStylesheets().add("techpal/style/style.css"); //ID"s are set throughout the application.
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Montserrat"); //nicer font than default
        PeriodsController.setPeriods(); //the unfortunately named PeriodsController class sets all static values from the database.
        LevelsController.setLevels();
        ProgramsController.setPrograms();
        DevicesController.setDevices();
        new LoginView(body, navBar); //for fun, enter [' OR '1'='1] in both the username and password field and enjoy the power of sql injections
        primaryStage.setTitle("TechPal");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaxHeight(850); //screw responsiveness.
        primaryStage.setMinHeight(850);
        primaryStage.setMaxWidth(850);
        primaryStage.setMinWidth(850);
        primaryStage.getIcons().add(new Image("techpal/style/resources/icon.png"));
    }
    public static void main(String[] args) {
        launch(args);
    }
}

