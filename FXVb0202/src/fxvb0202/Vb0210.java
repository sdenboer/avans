package fxvb0202;

import java.util.Random;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;


public class Vb0210 {
    private final Button animalA, animalB, animalC, clear;
    private final TextField textField;  
    
    
    public String randomAnimal(String[] animalArray) {
        String[] arr = animalArray;
        Random r = new Random();
        int random = r.nextInt(arr.length);
        return arr[random];
    };

    public Vb0210(FlowPane x) {
                
        animalA = new Button ("A");
        animalB = new Button ("B");
        animalC = new Button ("C");
        clear = new Button ("clear");
        textField = new TextField();
      
        
        animalA.setOnAction(event -> {
            String[] arr = {
                "Aardvark",
                "Albatross",
                "Alligator",
                "Alpaca",
                "Ant",
                "Anteater",
                "Antelope",
                "Ape",
                "Armadillo"
            };
            textField.setText(randomAnimal(arr));
        });
        
        animalB.setOnAction(event -> {
            String[] arr = {
                "Baboon",
                "Badger",
                "Barracuda",
                "Bat",
                "Bear",
                "Beaver",
                "Bee",
                "Bison",
                "Boar",
                "Buffalo",
                "Butterfly"
            };
            textField.setText(randomAnimal(arr));
        });
        
        animalC.setOnAction(event -> {
            String[] arr = {
                "Camel",
                "Capybara",
                "Caribou",
                "Cassowary",
                "Cat",
                "Caterpillar",
                "Cattle",
                "Chamois",
                "Cheetah",
                "Chicken",
                "Chimpanzee",
                "Chinchilla",
                "Chough",
                "Clam",
                "Cobra",
                "Cockroach",
                "Cod",
                "Cormorant",
                "Coyote",
                "Crab",
                "Crane",
                "Crocodile",
                "Crow",
                "Curlew"
            };
            textField.setText(randomAnimal(arr));
        });
        
        clear.setOnAction(event-> {
            textField.clear();
        });
        
        
        
        
        x.getChildren().add(animalA);
        x.getChildren().add(animalB);
        x.getChildren().add(animalC);
        x.getChildren().add(clear);
        x.getChildren().add(textField);

    }
}
