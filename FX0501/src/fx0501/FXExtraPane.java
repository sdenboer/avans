package fx0501;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class FXExtraPane extends VBox {
    private final FXExtraMain calc1, calc2, calc3;

    public FXExtraPane (GridPane p) {
        p.setHgap(50);
        calc1 = new FXExtraMain();
        calc2 = new FXExtraMain();
        calc3 = new FXExtraMain();
        
        p.add(calc1, 0, 0);
        p.add(calc2, 1, 0);
        p.add(calc3, 2, 0);
    }

}
