import java.io.*;
import java.util.ArrayList;
import java.util.List;

// WestminsterShoppingManager implements the ShoppingManager interface
public class WestminsterShoppingManager implements ShoppingManager {

    // Main method to test the functionality
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart(SecondGUI.selectedProducts);
        InputClass inputs = new InputClass();
        inputs.gettingInputs();
       // InputClass.gettingInputs();

    }
    private List<Product> productList; // List to store products

    // Constructor initializes the product list
    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
    }

    // Method to add a product to the system
    @Override
    public void addProductToSystem(Product product) {       // This method adds a product to the system, checking for duplicates and the maximum limit.
        int size = productList.size();
        // Check if the product with the same ID already exists
        for (int i = 0; i < size; i++) {
            if (productList.get(i).getID().equals(product.getID())) {
                System.out.println("Product with ID " + product.getID() + " already exists. Cannot add duplicate products.");
                return;
            }
        }
        // Check if the maximum limit of products has been reached
        if (size < 50) {
            productList.add(product);
            System.out.println("The product was added successfully.");
        } else {
            System.out.println("Cannot add more products. Maximum limit reached.");
        }
    }

    // Method to delete a product from the system based on its ID
    @Override
    public void deleteProductFromSystem(String ID) {        // This method deletes a product from the system based on its ID.
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            if (product.getID().equals(ID)) {
                productList.remove(i);
                System.out.println("Product deleted successfully.");
                return;
            }
        }
        System.out.println("Product with ID " + ID + " not found.");
    }


    // Method to print the product list after sorting by ID
    @Override
    public void printProductList() {            // This method prints the product list after sorting it based on the product ID.
        int size = productList.size();
        // Sort the list using a simple bubble sort
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (productList.get(j).getID().compareTo(productList.get(j + 1).getID()) > 0) {
                    // Swap productList[j] and productList[j + 1]
                    Product temp = productList.get(j);
                    productList.set(j, productList.get(j + 1));
                    productList.set(j + 1, temp);
                }
            }
        }
        // Print the sorted list
        System.out.println("Product List:");
        for (int i = 0; i < size; i++) {
            productList.get(i).info(); //specialized info of each category
        }
    }

    // Method to save the product list to a file
    @Override
    public void saveProductsToFile(String file_name) {      // This method saves the product list to a file using object serialization.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_name))) {
            oos.writeObject(productList);
            System.out.println("Products saved to file_name: " + file_name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read the product list from a file
    public void readProductsFromFile(String file) {     // This method reads the product list from a file using object deserialization.
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            productList = (List<Product>) ois.readObject();
            System.out.println("Products loaded from file: " + file);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Getter method for the product list
    public List<Product> getProductList() {
        return productList;
    }
}
