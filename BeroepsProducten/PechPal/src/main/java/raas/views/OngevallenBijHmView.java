package pechpal.views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import pechpal.controllers.OngevalController;

public class OngevallenBijHmView extends GridPane {
    private Label title;
    private ScrollPane scrollPane;
    private SideView sp;

    public OngevallenBijHmView(SideView sp) {
        this.sp = sp;
        this.title = new Label("ONGEVALLEN");
        this.title.setId("side-title");
        ImageView logo = new ImageView(new Image("frontal-crash.png"));
        scrollPane = new ScrollPane();
        scrollPane.setPrefSize(200, 218);
        scrollPane.setId("vkl-sc");
        this.add(logo, 0, 0);
        this.add(title, 1, 0);
        this.add(scrollPane, 1, 1);

        this.setId("side-pane");
        this.setHgap(20);
        this.setVgap(20);
    }

    public void showAllOngevallen(String[] arr) {
        GridPane gridPane = new GridPane();
        OngevalController ongC = new OngevalController();
        for (int i = 0; i < arr.length ; i++) {
            Button btn = new Button(arr[i]);
            btn.setId("vkl-btn");
            if (btn.getText().equals(sp.searchByVKL.query.getText())) {
                btn.setId("vkl-btn-selected");
            }
            btn.setOnAction(event -> {
                sp.loadOngevalVKL(ongC, btn.getText());
            });
            gridPane.add(btn, 0, i);
        }
        scrollPane.setContent(gridPane);
    }

    public void noOngevallen() {
        GridPane gridPane = new GridPane();
        Label label = new Label("Geen internet");
        label.setId("vkl-btn");
        gridPane.add(label, 0, 0);
        scrollPane.setContent(gridPane);
    }
}
