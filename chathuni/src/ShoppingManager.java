// ShoppingManager interface represents the functionality required for managing a shopping system
public interface ShoppingManager {

    // Method to add a product to the system
    void addProductToSystem(Product product);

    // Method to delete a product from the system based on its ID
    void deleteProductFromSystem(String Id);

    // Method to print the list of products in the system
    void printProductList();

    // Method to save the list of products to a file
    void saveProductsToFile(String file);
}
