package fx0501;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Panel {

    public Panel (VBox v) {
        HBox buttons = new HBox();
        Button b1 = new Button("1");
        Button b2 = new Button("2");
        Button b3 = new Button("3");
        Button b4 = new Button("4");
        Button b5 = new Button("5");
        Button b6 = new Button("6");
        Button b7 = new Button("7");
        Button b8 = new Button("8");
        Button b9 = new Button("9");
        Button b10 = new Button("10");
        Button b11 = new Button("11");
        Button b12 = new Button("12");
        Button b13 = new Button("13");
        Button b14 = new Button("14");
        buttons.getChildren().addAll(b1,b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14);

        HBox hbox = new HBox();

        b1.setOnAction(event -> {
            hbox.getChildren().clear();
            hbox.getChildren().add(new FXExtraPane(new GridPane()));
        });
        b2.setOnAction(event -> {
            hbox.getChildren().clear();
            hbox.getChildren().add(new FXEx0501(hbox));
        });
//        new FXEx0501(gridFile);
//        new FXEx0502(gridFile);
//        new FXEx0503(gridFile);
//        new FXEx0504(vboxFile);
//        new FXEx0505(gridFile);
//        new FXEx0506(gridFile);
//        new FXEx0507(vboxFile);
//        new FXEx0508(gridFile);
//        new FXEx0509(gridFile);
//        new FXEx0510(gridFile);
//        new FXEx0511(gridFile);
//        new FXEx0512(vboxFile);


        v.getChildren().addAll(buttons, hbox);
    }

}
