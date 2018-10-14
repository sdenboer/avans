package techpal;

import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class StuMainView extends TabPane {
    private StuTabLesson tbLessons;
    private StuTabAdd tbAdd;
    private StuTabProf tbProf;

    public StuMainView(Pane p) {
        tbLessons = new StuTabLesson();
        tbAdd = new StuTabAdd(this); //tabPane is given to the tab for redirecting
        tbProf = new StuTabProf(this); //tabPane is given to the tab for redirecting
        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        this.getTabs().addAll(tbLessons, tbAdd, tbProf);
    }
}
