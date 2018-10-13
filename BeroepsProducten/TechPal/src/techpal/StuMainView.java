package techpal;

import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class StuMainView extends TabPane {
    private StuTabLesson tbLessons;
    private StuTabAdd tbAdd;

    public StuMainView(Pane p) {
        tbLessons = new StuTabLesson();
        tbAdd = new StuTabAdd();
        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        this.getTabs().addAll(tbLessons, tbAdd);
    }
}
