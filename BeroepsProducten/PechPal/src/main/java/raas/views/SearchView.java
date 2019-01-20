package pechpal.views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SearchView extends GridPane {
    public Text query;

    public SearchView(String title, String query, String btnText, CenterView cp, SideView sp, String imageUrl) {
        Label header = new Label(title);
        header.setId("side-title");
        this.query = new Text(query);
        this.query.setId("side-search-query");
        Button button = new Button(btnText);
        button.setId("side-search-btn");
        ImageView logo = new ImageView(new Image(imageUrl));

        button.setOnAction(event -> {
            if (!cp.getChildren().contains(cp.searchPopUpView)) {
                cp.addSearchPopUp(title, sp);
            } else {
                cp.removeSearchPopUp();
            }
        });


        this.add(logo, 0, 0);
        this.add(header, 1, 0);
        this.add(this.query, 1, 1);
        this.add(button, 1, 2);


        this.setId("side-pane");
        this.setHgap(20);
        this.setVgap(20);
    }
}
