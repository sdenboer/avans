package raas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import raas.views.StartUpView;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

/**
 * Dit is de main startup class.
 * In deze klasse wordt de verbinding gecheckt en de scene geinitialiseert.
 * Ook wordt het Stylesheet geladen
 * @author sven
 * @version 1.0.0
 * @see StartUpView voor de startup View
 */
public class Raas extends Application {

    /**
     * Eerst wordt de verbinding gecheckt, daarna eventueel de error geladen en vervolgens laadt de
     * applicatie volledig.
     * @param primaryStage is de stage die geladen moet worden
     */
    public void start(Stage primaryStage) {
        try {
            isInternetPresent();
        } catch (UnknownHostException e) {
            noConnectionPresent();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StartUpView root = new StartUpView();
            Scene scene = new Scene(root, 1366, 768);
            scene.getStylesheets().add("style.css");
            primaryStage.getIcons().add(new Image("cone.png"));
            primaryStage.setScene(scene);
            primaryStage.setTitle("Road Accident Analysis Software");
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Deze methode checkt de connectie met de API.
     * @throws IOException
     * Mocht deze offline zijn dan geeft ie een
     * Exception error terug en wordt de noConnectionPresent alert geladen
     */
    private void isInternetPresent() throws IOException {
            final URL url = new URL("https://maps.googleapis.com/");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
    }

    /**
     * Dit is een alert die in beeld komt zodra er geen connectie is.
     */
    private void noConnectionPresent() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Geen verbinding");
        alert.setContentText("Zonder verbinding met de kaartservice kunt u alleen zoeken op VKL nummer en werkt de kaart niet");
        alert.showAndWait();
    }
}
