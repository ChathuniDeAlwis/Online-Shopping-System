import java.io.Serializable;

// The Clothing class extends the Product class and implements the Serializable interface
public class Clothing extends Product implements Serializable {
    private String size;  // Size of the clothing
    private String color; // Color of the clothing

    // Default constructor needed for deserialization
    public Clothing() {
    }

    // Parameterized constructor to initialize Clothing object with specific attributes
    public Clothing(String ID, String name, int numOfAvaItems, double price, String size, String color) {
        // Call the constructor of the superclass (Product) using the 'super' keyword
        super(ID, name, numOfAvaItems, price);
        this.size = size;
        this.color = color;
    }

    // Getter method for retrieving the size of the clothing
    public String getSize() {
        return size;
    }

    // Setter method for setting the size of the clothing
    public void setSize(String size) {
        this.size = size;
    }

    // Getter method for retrieving the color of the clothing
    public String getColor() {
        return color;
    }

    // Setter method for setting the color of the clothing
    public void setColor(String color) {
        this.color = color;
    }

    // Override the info() method from the superclass (Product)
    @Override
    public void info() {
        // Display information about the clothing, including name, size, and color
        System.out.println("Clothing: " + getName() + ", Size: " + size + ", Color: " + color);
    }
}
