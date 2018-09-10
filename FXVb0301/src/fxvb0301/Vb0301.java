package fxvb0301;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class Vb0301 {
        private final int year, day, hour; 
        private final Text secondsInAYear, secondsInADay, secondsInAnHour;
        
        public Vb0301 (Pane p) {
            hour = 60 * 60;
            day = 24 * hour;
            year = 365 * day;      
            
            secondsInAYear = new Text(50, 30, "Seconds in a year: " + year);
            secondsInADay = new Text(50, 60, "Seconds in a day: " + day);
            secondsInAnHour = new Text(50, 90, "Seconds in an hour: " + hour);
            
            p.getChildren().addAll(secondsInAYear, secondsInADay, secondsInAnHour);
        }
}
