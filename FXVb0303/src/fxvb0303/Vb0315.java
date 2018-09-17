package fxvb0303;

import java.util.Random;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Vb0315 {
    private final Button roll1, roll2, reset, ok1, ok2;
    private final Rectangle dice;
    private final Circle eye1, eye2, eye3, eye4, eye5, eye6, eye7, eye8, eye9;
    private final Text player1, player2, winner, game;
        int p1;
        int p2;
  
    
    public void setCircleColor(Circle[] crArr) { //resets color of eyes after rolling
         for (Circle a : crArr) {
              a.setFill(Color.WHITE);
        }
    }
      
    public void finished(Button r1, Button r2, Button r) { //resets buttons after finishing the game
      r1.setDisable(true);
      r2.setDisable(true);
      r.setDisable(false);
    }
      
      public int assignColors(Text t, int p, Circle[] a) {  //assigns black color to eyes
          Random random2 = new Random();
          int y = random2.nextInt(7-1)+1; //random number between 1 and 6 (eyes on the dice)
          t.setText(String.valueOf(p+y));
          switch(y) {
                case 1: a[4].setFill(Color.BLACK);
                        p += 1;
                        break;
                case 2: a[2].setFill(Color.BLACK);
                        a[6].setFill(Color.BLACK);
                        p += 2;
                        break;
                case 3: a[2].setFill(Color.BLACK);
                        a[4].setFill(Color.BLACK);
                        a[6].setFill(Color.BLACK);
                        p += 3;
                        break;
                case 4: a[0].setFill(Color.BLACK);
                        a[2].setFill(Color.BLACK);
                        a[6].setFill(Color.BLACK);
                        a[8].setFill(Color.BLACK);
                        p += 4;
                        break;
                case 5: a[0].setFill(Color.BLACK);
                        a[2].setFill(Color.BLACK);
                        a[6].setFill(Color.BLACK);
                        a[8].setFill(Color.BLACK);
                        a[4].setFill(Color.BLACK);
                        p += 5;
                        break;
                case 6: a[0].setFill(Color.BLACK);
                        a[1].setFill(Color.BLACK);
                        a[2].setFill(Color.BLACK);
                        a[6].setFill(Color.BLACK);
                        a[7].setFill(Color.BLACK);
                        a[8].setFill(Color.BLACK);
                        p += 6;                    
            }
          return p;
      }
    
    public Vb0315 (Pane p){
//ASSIGNING
        p1 = 0;
        p2 = 0;
       
        eye1 = new Circle(120, 90, 9);
        eye2 = new Circle(120, 120, 9);
        eye3 = new Circle(120, 150, 9);
        eye4 = new Circle(150, 90, 9);
        eye5 = new Circle(150, 120, 9);
        eye6 = new Circle(150, 150, 9);
        eye7 = new Circle(180, 90, 9);
        eye8 = new Circle(180, 120, 9);
        eye9 = new Circle(180, 150, 9);
        Circle[] crArr = {eye1, eye2, eye3, eye4, eye5, eye6, eye7, eye8, eye9};
        setCircleColor(crArr);
        
        dice = new Rectangle(100, 100, Color.WHITE);
        dice.setStroke(Color.BLACK);
        dice.setStrokeWidth(2);
        dice.setArcWidth(20);
        dice.setArcHeight(20);
        dice.relocate(100, 70);
        
        player1 = new Text(String.valueOf(p1));
        player1.relocate(10, 30);
        
        player2 = new Text(String.valueOf(p2));
        player2.relocate(280, 30);
        
        winner = new Text();
        winner.relocate(100, 190);
        winner.setFill(Color.GREEN);
        
        game = new Text("The player closest to or with 21\npoints wins, but if you go over\nyou lose!");
        game.relocate(45, 10);
        
        roll1 = new Button("P1");
        ok1 = new Button("✓");
        ok1.relocate(0, 50);
        roll2 = new Button("P2");
        roll2.relocate(270, 0);
        roll2.setDisable(true);
        ok2 = new Button("✓");
        ok2.setDisable(true);
        ok2.relocate(270, 50);
        
        reset = new Button("reset");
        reset.relocate(120, 220);
        reset.setDisable(true);

//EVENTLISTENERS
        roll1.setOnAction(e -> {
           setCircleColor(crArr);
            p1 = assignColors(player1, p1, crArr);
            if (p1 > 21) {
                winner.setText("PLAYER 2 wins!");
                finished(roll1, roll2, reset);
                ok1.setDisable(true);
            } else if (p1 == 21) {
                winner.setText("PLAYER 1 wins!");
                finished(roll1, roll2, reset);
                ok1.setDisable(true);
            }
        }); 
        roll2.setOnAction(e -> {
            setCircleColor(crArr);
            p2 = assignColors(player2, p2, crArr);
            if (p2 > 21) {
                winner.setText("PLAYER 1 wins!");
                finished(roll1, roll2, reset);
                ok2.setDisable(true);
            } else if (p2 == 21) {
                winner.setText("PLAYER 2 wins!");
                finished(roll1, roll2, reset);
                ok2.setDisable(true);
            }       
        });
        
        ok1.setOnAction(e -> {
            roll1.setDisable(true);
            roll2.setDisable(false);
            ok1.setDisable(true);
            ok2.setDisable(false);
        });
        ok2.setOnAction(e -> {
            roll2.setDisable(true);
            ok2.setDisable(true);
            if (p2 > p1) {
                winner.setText("PLAYER 2 wins!");
            } else if (p1 > p2) {
                winner.setText("PLAYER 2 wins!");
            } else {
                winner.setText("DRAW!");
            }
            reset.setDisable(false);
        });
        
        reset.setOnAction(e -> {
            p1 = 0;
            p2 = 0;
            player2.setText(String.valueOf(p2));
            player1.setText(String.valueOf(p1));
            winner.setText("");
            roll1.setDisable(false);
            ok1.setDisable(false);
            reset.setDisable(true);
            setCircleColor(crArr);

            
        });
        p.getChildren().addAll(dice, roll1, roll2, eye1, eye2, eye3, eye4, eye5, eye6, eye7, eye8, eye9, player1, player2, winner, ok1, ok2, reset, game);  
    }
}