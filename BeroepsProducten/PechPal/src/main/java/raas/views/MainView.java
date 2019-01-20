package raas.views;

import javafx.scene.layout.*;

public class MainView extends BorderPane {

    public MainView(){
        this.setId("main-pane");
        CenterView centerView = new CenterView();
        this.setCenter(centerView);
        InformationView informationView = new InformationView();
        informationView.setPrefWidth(400);
        informationView.tutorial();
        this.setRight(informationView);
        SideView sideView = new SideView(centerView, informationView);
        sideView.setPrefWidth(300);
        this.setLeft(sideView);
    }
}
