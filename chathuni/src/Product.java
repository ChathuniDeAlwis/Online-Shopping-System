import java.io.Serializable;

// Product is an abstract class representing a generic product in the shopping system
public abstract class Product implements Serializable {

    // Private fields representing common attributes of all products
    private String ID;
    private String name;
    private int numOfAvaItems;
    private double price;

    // Constructor to initialize the common attributes of a product
    public Product(String ID, String name, int numOfAvaItems, double price) {
        this.ID = ID;
        this.name = name;
        this.numOfAvaItems = numOfAvaItems;
        this.price = price;
    }

    // Default constructor needed for serialization
    public Product() {}

    public Product(String p001, String s, double v) { //FOR TEST CLASS
    }

    // Getter and setter methods for the ID
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    // Getter and setter methods for the name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter methods for the number of available items
    public int getNumOfAvaItems() {
        return numOfAvaItems;
    }
    public void setNumOfAvaItems(int numOfAvaItems) {
        this.numOfAvaItems = numOfAvaItems;
    }

    // Getter and setter methods for the price
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    // Abstract method to provide information about the specific product
    public abstract void info();
}
