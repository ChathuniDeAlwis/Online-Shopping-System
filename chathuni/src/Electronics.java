import java.io.Serializable;

// Electronics class represents a type of product specializing in electronic items
public class Electronics extends Product implements Serializable {

    // Additional attributes specific to Electronics
    private String brand;
    private double warrantyPeriod;

    // Default constructor needed for deserialization
    public Electronics() {}

    // Constructor to initialize the attributes of an Electronics product
    public Electronics(String ID, String name, int numOfAvaItems, double price, String brand, double warrantyPeriod) {
        super(ID, name, numOfAvaItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getter and setter methods for the brand
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Getter and setter methods for the warranty period
    public double getWarrantyPeriod() {
        return warrantyPeriod;
    }
    public void setWarrantyPeriod(double warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    // Override the info method to provide specific information about Electronics products
    @Override
    public void info() {
        System.out.println("Electronics: " + getName() + ", Brand: " + brand + ", Warranty: " + warrantyPeriod + " months");
        //System.out.println("chathuni");
    }
}
