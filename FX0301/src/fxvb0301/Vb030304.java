package fxvb0301;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Vb030304 {
    private final int 
            leeftijdInSeconden, 
            leeftijdInMinuten, 
            leeftijdInUren, 
            leeftijdInDagen, 
            leeftijdInWeken, 
            leeftijdInJaren;
    
    private final Text jaren, weken, dagen, uren, minuten, seconden;
    
    public Vb030304 (Pane p) {
        leeftijdInSeconden = 5_454_532;
        leeftijdInMinuten = leeftijdInSeconden / 60;
        leeftijdInUren = leeftijdInMinuten / 60;
        leeftijdInDagen = leeftijdInUren / 24;
        leeftijdInWeken = leeftijdInDagen / 7;
        leeftijdInJaren = leeftijdInWeken / 52;
        
                
        jaren = new Text(50, 150, "Leeftijd: \n" + leeftijdInJaren + " jaar");
        weken = new Text(50, 180, leeftijdInWeken + " weken");
        dagen = new Text(50, 200,  leeftijdInDagen + " dagen");
        uren = new Text(50, 220, leeftijdInUren + " uren");
        minuten = new Text(50, 240, leeftijdInMinuten + " minuten");
        seconden = new Text(50, 260, leeftijdInSeconden + " seconden");
        
        
        p.getChildren().addAll(jaren, weken, dagen, uren, minuten, seconden);
    }
    
    
}
