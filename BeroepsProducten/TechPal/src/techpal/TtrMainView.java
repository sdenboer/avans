package techpal;

import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class TtrMainView extends TabPane {
    private TtrTabLesson tbLessons;
    private TtrTabAdd tbAdd;
    private TtrTabProf tbProf;

    public TtrMainView(Pane p) {
        tbLessons = new TtrTabLesson();
        tbAdd = new TtrTabAdd(this);
        tbProf = new TtrTabProf(this);

        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        this.getTabs().addAll(tbLessons, tbAdd, tbProf);
    }
}
