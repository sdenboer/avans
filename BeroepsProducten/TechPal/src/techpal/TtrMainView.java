package techpal;

import javafx.geometry.Pos;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TtrMainView extends TabPane {
    private TtrTabLesson tbLessons;
    private TtrTabAdd tbAdd;
    private TtrTabProf tbProf;
    private TtrTabPrevLessons tbPrev;

    public TtrMainView(AnchorPane body) {
        this.setMinHeight(800);
        this.setMinWidth(850);
        tbLessons = new TtrTabLesson();
        tbAdd = new TtrTabAdd(this);
        tbProf = new TtrTabProf(this);
        tbPrev = new TtrTabPrevLessons();

        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        this.getTabs().addAll(tbLessons, tbAdd, tbProf, tbPrev);
    }
}
