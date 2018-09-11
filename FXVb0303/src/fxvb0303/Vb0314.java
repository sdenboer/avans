package fxvb0303;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class Vb0314 {
    public final Rectangle trafficLight, pole;
    public final Circle red, green, orange;
    
    public Vb0314 (Pane p) {
        
        trafficLight = new Rectangle(50, 140);
        pole = new Rectangle(10, 200);
        pole.relocate(150, 50);
        trafficLight.relocate(130, 20);
        
        
        
                
        red = new Circle();
        red.setCenterY(45);
        red.setCenterX(155);
        red.setRadius(20);
        red.setFill(Color.GREY);
        
        orange = new Circle();
        orange.setCenterY(90);
        orange.setCenterX(155);
        orange.setRadius(20);
        orange.setFill(Color.GREY);
        
        green = new Circle();
        green.setCenterY(135);
        green.setCenterX(155);
        green.setRadius(20);
        green.setFill(Color.GREY);
        

        
        p.getChildren().addAll(trafficLight, pole, red, orange, green);
        
        
    }
}
