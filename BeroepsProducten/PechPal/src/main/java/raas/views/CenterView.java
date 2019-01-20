package raas.views;

import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.util.Objects;

/**
 * Deze view laadt de center-child van de BorderPane <code>MainView</code>,
 * regelt de html kaart <code>kaart.html</code>(Google Maps API)
 * en pop-ups die uit de left-child <code>SideView</code> komen.
 * @author sven
 * @version 1.0.0
 * @see MainView
 * @see SideView
 */
public class CenterView extends StackPane {
    public SearchPopUpView searchPopUpView;
    public WebEngine webEngine;

    /**
     * Initialiseert de centerview en definieert de kaart in het hml bestand.
     */
    public CenterView() {
        String url = Objects.requireNonNull(CenterView.class.getClassLoader().getResource("kaart.html"))
                .toExternalForm();
        this.getChildren().add(addWebView(url));
        this.setId("center");
    }

    /**
     * definieert de grote van de kaart en laadt deze.
     * @param url de lokale url in de resource package
     * @return de kaart die geladen wordt
     */
    public WebView addWebView(String url) {
        WebView webView = new WebView();
        webView.setMaxSize(600, 580);
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load(url);
        return webView;
    }

    /**
     * de SearchPopUps komen uit de <code>SideView</code>
     * @param s is de header van de popup URL
     * @param sv is de SideView die geladen moet worden
     */
    public void addSearchPopUp(String s, SideView sv) {
        searchPopUpView = new SearchPopUpView();
        searchPopUpView.search(s, sv, this);
        this.getChildren().add(searchPopUpView);
    }

    /**
     * Mocht een popup geladen zijn dan removed deze methode de popup
     */
    public void removeSearchPopUp() {
        this.getChildren().remove(searchPopUpView);
    }

}
