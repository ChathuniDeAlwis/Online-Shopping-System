import java.util.ArrayList;
import java.util.List;

// ShoppingCart class represents a shopping cart that can hold products
public class ShoppingCart {
    private List<Product> products; // List to store products

    // Constructor initializes the shopping cart with selected products
    public ShoppingCart(List<Product> selectedProducts) {
        this.products = new ArrayList<>(); // ArrayList chosen to store products
    }
    // Method to add a product to the shopping cart
    public void add(Product product) {
        products.add(product);
    }
    // Method to remove a product from the shopping cart
    public void remove(Product product) {
        products.remove(product);
    }

    // Method to calculate the total cost, discounts, and final total cost of the products in the shopping cart
    public double[] calculateTotalCost(List<Product> products,boolean type) {
        double totalCost = 0.0;
        double discountOne = 0.0;
        double discountTwo = 0.0;
        double finalTotalValue = 0.0;
        int electronicBuy = 0;
        int clothingBuy = 0;
        double[] finalResultArray = new double[4];

        // Loop through the products in the shopping cart
        for (int i = 0; i < products.size(); i++) {
            // Check if the product is an instance of Electronics
            if (products.get(i) instanceof Electronics) {
                electronicBuy += products.get(i).getNumOfAvaItems();
            }
            // Check if the product is an instance of Clothing
            if (products.get(i) instanceof Clothing) {
                clothingBuy += products.get(i).getNumOfAvaItems();
            }
            // Accumulate the total cost
            totalCost  += (products.get(i).getPrice() * products.get(i).getNumOfAvaItems());
        }
        if(type == true){        // Apply discount if type is true
            discountOne = totalCost * 0.1;// Apply a 10% discount
            // Round discountOne to three decimal points
            discountOne = Math.round(discountOne * 1000.0) / 1000.0;
        }

        // Calculate the final total value and apply discounts if conditions are met
        finalTotalValue = totalCost;
        if (electronicBuy >= 3 || clothingBuy >= 3) {
            finalTotalValue = totalCost * 0.8; // Apply a 20% discount
            discountTwo = totalCost * 0.2; // Calculate the discount amount
        }
        else{
            finalTotalValue = totalCost - discountOne;
        }
        // Round discountTwo to three decimal points
        discountTwo = Math.round(discountTwo * 1000.0) / 1000.0;
        // Populate the result array with calculated values
        finalResultArray[0] = totalCost;
        finalResultArray[1] = discountOne;
        finalResultArray[2] = discountTwo;
        finalResultArray[3] = finalTotalValue;

        return finalResultArray; // Return the array with total cost, discounts, and final total
    }
}
