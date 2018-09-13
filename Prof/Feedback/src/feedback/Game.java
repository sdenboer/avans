package feedback;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Game {
    private final Rectangle a1, a2, a3, a4, a5, a6, a7, a8, a9, a10,
            b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
       
    public void setRectangle(Rectangle[] rcArr) {
        int posY = 200; 
        int posX = 200;
        for (Rectangle r : rcArr) {
            r.setX(posX);
            r.setY(posY);
            r.setFill(Color.WHITE);
            r.setWidth(100);
            r.setHeight(100);
            r.setStroke(Color.BLACK);
            r.setStrokeWidth(2);
            if (posX <= 1000) {
                posX += 100; 
                posY += 0;
            } else if (posX > 1000) {
                posX = 100;
                posY = 600; 
                posX += 100;
            }
        }
    }
    
    public Game (Pane p) {
        a1 = new Rectangle(); a2 = new Rectangle(); a3 = new Rectangle();
        a4 = new Rectangle(); a5 = new Rectangle(); a6 = new Rectangle();
        a7 = new Rectangle(); a8 = new Rectangle(); a9 = new Rectangle();
        a10 = new Rectangle(); b1 = new Rectangle(); b2 = new Rectangle(); 
        b3 = new Rectangle(); b4 = new Rectangle(); b5 = new Rectangle();
        b6 = new Rectangle(); b7 = new Rectangle(); b8 = new Rectangle(); 
        b9 = new Rectangle(); b10 = new Rectangle(); 
        
        Rectangle[] rcArr = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a10,
            b1, b2, b3, b4, b5, b6, b7, b8, b9, b10};
        setRectangle(rcArr);
        
        p.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                System.out.println("HELLo");
            } else {
                System.out.println("NO");
            }
        });
        
        
        
        p.getChildren().addAll(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10,
            b1, b2, b3, b4, b5, b6, b7, b8, b9, b10);
        
        
        
        
        
        
        
    }
}
