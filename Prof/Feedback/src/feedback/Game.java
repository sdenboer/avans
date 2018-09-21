package feedback;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Game {
    
    //(question, number, right/wrong)
    private final Button q1R, q1W, q2W, q2R, q3W, q3R, q4W, q4R, q5W, q5R, q6W;
    private final Button q6R, q7W, q7R, q8W, q8R, q9W, q9R, q10W, q10R;
    private final Circle circle;
    
    public Game(FlowPane p) {
        circle = new Circle();
        circle.setCenterX(100.0f);
        circle.setCenterY(100.0f);
        circle.setRadius(50.0f);
        
        EventHandler wrong = event -> circle.setFill(Color.RED);
        EventHandler right = event -> circle.setFill(Color.GREEN);                
        
        q1W = new Button ("Fixed");                
        q1R = new Button ("Growth");
        q2W = new Button ("Growth");
        q2R = new Button ("Fixed");
        q3W = new Button ("Fixed");
        q3R = new Button ("Growth");
        q4W = new Button ("Fixed");
        q4R = new Button ("Growth");
        q5W = new Button ("Growth");
        q5R = new Button ("Fixed");
        q6W = new Button ("Growth");
        q6R = new Button ("Fixed");
        q7W = new Button ("Growth");
        q7R = new Button ("Fixed");
        q8W = new Button ("Fixed");
        q8R = new Button ("Growth");
        q9W = new Button ("Growth");
        q9R = new Button ("Fixed");
        q10W = new Button ("Fixed");
        q10R = new Button ("Growth");

        

        q1R.setOnAction(right);
        q2R.setOnAction(right);
        q3R.setOnAction(right);
        q4R.setOnAction(right);
        q5R.setOnAction(right);
        q6R.setOnAction(right);
        q7R.setOnAction(right);
        q8R.setOnAction(right);
        q9R.setOnAction(right);
        q10R.setOnAction(right);
        
        q1W.setOnAction(wrong);
        q2W.setOnAction(wrong);
        q3W.setOnAction(wrong);
        q4W.setOnAction(wrong);
        q5W.setOnAction(wrong);
        q6W.setOnAction(wrong);
        q7W.setOnAction(wrong);
        q8W.setOnAction(wrong);
        q9W.setOnAction(wrong);
        q10W.setOnAction(wrong);
 
       //Q1
        p.getChildren().add(new Text("  Talent leer je                        "));
        p.getChildren().add(q1R);
        p.getChildren().add(q1W);
        
       //Q2
        p.getChildren().add(new Text("\n\n  Belonen van\n  intelligentie               "));
        p.getChildren().add(q2W);
        p.getChildren().add(q2R);
        
       //Q3
        p.getChildren().add(new Text("\n\n  Belonen van moeite \n  en progressie   "));
        p.getChildren().add(q3R);
        p.getChildren().add(q3W);
        
       //Q4
        p.getChildren().add(new Text("\n\n  Nieuwe dingen\n  proberen                          "));
        p.getChildren().add(q4R);
        p.getChildren().add(q4W); 
        
       //Q5
        p.getChildren().add(new Text("\n\n  Vermijden van\n  negatieve feedback   "));
        p.getChildren().add(q5W);
        p.getChildren().add(q5R);
        
       //Q6
        p.getChildren().add(new Text("\n\n  Zoekt contacten voor\n  meer zelfvertrouwen   "));
        p.getChildren().add(q6W);
        p.getChildren().add(q6R);
        
       //Q7
        p.getChildren().add(new Text("\n\n  Snel opgeven               "));
        p.getChildren().add(q7W);
        p.getChildren().add(q7R);
        
       //Q8
        p.getChildren().add(new Text("\n\n  \"Late bloomer\"                      "));
        p.getChildren().add(q8R);
        p.getChildren().add(q8W);
        
       //Q9
        p.getChildren().add(new Text("\n\n  \"I peaked when I was\n   25\"               "));
        p.getChildren().add(q9W);
        p.getChildren().add(q9R);
            
       //Q10
        p.getChildren().add(new Text("\n\n  Dweck is een\n  voorstander van:    "));
        p.getChildren().add(q10R);
        p.getChildren().add(q10W);
        
        p.getChildren().add(circle);

    }
}


//package feedback;
//
//import javafx.scene.input.KeyCode;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//
//
//public class Game {
//    private final Rectangle a1, a2, a3, a4, a5, a6, a7, a8, a9, a10,
//            b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
//       
//    public void setRectangle(Rectangle[] rcArr) {
//        int posY = 200; 
//        int posX = 200;
//        for (Rectangle r : rcArr) {
//            r.setX(posX);
//            r.setY(posY);
//            r.setFill(Color.WHITE);
//            r.setWidth(100);
//            r.setHeight(100);
//            r.setStroke(Color.BLACK);
//            r.setStrokeWidth(2);
//            if (posX <= 1000) {
//                posX += 100; 
//                posY += 0;
//            } else if (posX > 1000) {
//                posX = 100;
//                posY = 600; 
//                posX += 100;
//            }
//        }
//    }
//    
//    public Game (Pane p) {
//        a1 = new Rectangle(); a2 = new Rectangle(); a3 = new Rectangle();
//        a4 = new Rectangle(); a5 = new Rectangle(); a6 = new Rectangle();
//        a7 = new Rectangle(); a8 = new Rectangle(); a9 = new Rectangle();
//        a10 = new Rectangle(); b1 = new Rectangle(); b2 = new Rectangle(); 
//        b3 = new Rectangle(); b4 = new Rectangle(); b5 = new Rectangle();
//        b6 = new Rectangle(); b7 = new Rectangle(); b8 = new Rectangle(); 
//        b9 = new Rectangle(); b10 = new Rectangle(); 
//        
//        Rectangle[] rcArr = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a10,
//            b1, b2, b3, b4, b5, b6, b7, b8, b9, b10};
//        setRectangle(rcArr);
//        
//        p.setOnKeyPressed(e-> {
//            if (e.getCode() == KeyCode.ENTER) {
//                System.out.println("HELLo");
//            } else {
//                System.out.println("NO");
//            }
//        });
//        
//        
//        
//        p.getChildren().addAll(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10,
//            b1, b2, b3, b4, b5, b6, b7, b8, b9, b10); 
//        
//    }
//}
