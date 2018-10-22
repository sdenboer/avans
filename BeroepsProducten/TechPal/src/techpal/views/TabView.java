package techpal.views;

import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import techpal.models.Person;
import techpal.models.Student;

public class TabView extends TabPane {
    private TabProf tbProf;
    private TtrTabLesson tutorTabLesson;
    private TtrTabAdd tutorTabAdd;
    private StuTabLesson studentTabLesson;
    private StuTabAdd studentTabAdd;
    private TabPrev tbPrev;

    public TabView(AnchorPane body, Person user) {
        String columnName; //these strings set the name for the column and cell of the tableview in the previous lesson tab
        String cellName;
        this.setMinHeight(800);
        this.setMinWidth(850);
        tutorTabLesson = new TtrTabLesson();
        tutorTabAdd = new TtrTabAdd(this);
        studentTabLesson = new StuTabLesson();
        studentTabAdd = new StuTabAdd(this);
        tbProf = new TabProf(user, this);
        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        if (user instanceof Student) {
            this.getTabs().addAll(studentTabLesson, studentTabAdd);
            columnName = "tutor";
            cellName = "ttrNm";
        } else {
            this.getTabs().addAll(tutorTabLesson, tutorTabAdd);
            columnName = "student";
            cellName = "stuNm";
        }

        tbPrev = new TabPrev(columnName, cellName);
        this.getTabs().addAll(tbProf, tbPrev);

        body.getChildren().addAll(this);
    }
}