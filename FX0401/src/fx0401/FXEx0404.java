package fx0401;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class FXEx0404 {
    private final Text 
            addPriceLabel, subTotalLabel, taxLabel, totalExTaxLabel, totalLabel;
    private final TextField addPrice, subTotal, tax, totalExTax, total;
    private final Button totalButton, clear;
    private final Kassa kassa;
    double subTot;
    
    public FXEx0404 (GridPane p) {
        kassa = new Kassa();
        addPriceLabel = new Text("Add price");
        subTotalLabel = new Text("Subtotal");
        taxLabel = new Text("Tax");
        totalExTaxLabel = new Text("Total ex tax");
        totalLabel = new Text("Total");
        
        addPrice = new TextField();
        subTotal = new TextField();
        subTotal.setEditable(false);
        tax = new TextField();
        tax.setEditable(false);
        totalExTax = new TextField();
        totalExTax.setEditable(false);
        total = new TextField();
        total.setEditable(false);
        
        totalButton = new Button("Total");
        clear = new Button("Reset");
        
        addPrice.setOnAction(e-> {
           String strPrice = addPrice.getText(); 
           addPrice.clear();
           double dblPrice = Double.parseDouble(strPrice);
           kassa.count(dblPrice);
           subTot = kassa.getSubTotal();
           subTotal.setText(String.valueOf(subTot));
        });
        
        totalButton.setOnAction(e-> {
            tax.setText(String.format("%.2f", kassa.calculateTax()));
            totalExTax.setText(String.format("%.2f", kassa.calculateTotalExTax()));
            total.setText(String.valueOf(subTot));
        });
        
        clear.setOnAction(e-> {
            addPrice.clear();
            subTotal.clear();
            tax.clear();
            totalExTax.clear();
            total.clear();
        });
        
        p.add(addPriceLabel, 0, 0);
        p.add(subTotalLabel, 0, 1);
        p.add(taxLabel, 0, 2);
        p.add(totalExTaxLabel, 0, 3);
        p.add(totalLabel, 0, 4);
        
        p.add(addPrice, 1, 0);
        p.add(subTotal, 1, 1);
        p.add(tax, 1, 2);
        p.add(totalExTax, 1, 3);
        p.add(total, 1, 4);
        
        p.add(totalButton, 2, 1);
        p.add(clear, 2, 4);
    }
}
//
//    private final Text 
//            addPriceLabel, subTotalLabel, taxLabel, totalExTaxLabel, totalLabel;
//    private final TextField addPrice, subTotal, tax, totalExTax, total;
//    private final Button totalButton, clear;