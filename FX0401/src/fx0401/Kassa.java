package fx0401;


public class Kassa {
    private double subTotal;
    private final double PERCENTAGE_TAX = 21.0;
    
    public void count(double price) {
        subTotal += price;
    }
    
    public double getSubTotal() {
        return subTotal;
    }
    
    public double calculateTotalExTax() {
        return subTotal / (1 + PERCENTAGE_TAX/100);
    }
    
    public double calculateTax() {
        return subTotal - calculateTotalExTax();
    }
}
