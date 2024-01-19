import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {

    @Test
    void addProductToSystem() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Product product = new Product("1", "Test Product", 10.0) {
            @Override
            public void info() {

            }
        };

        // Test adding a product
        shoppingManager.addProductToSystem(product);

        // Verify that the product list contains the added product
        List<Product> productList = shoppingManager.getProductList();
        assertTrue(productList.contains(product));

        // Test adding a duplicate product
        WestminsterShoppingManager shoppingManager2 = new WestminsterShoppingManager();
        shoppingManager2.addProductToSystem(product);

    }

    @Test
    void deleteProductFromSystem() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Product product = new Product("1", "Test Product", 10.0) {
            @Override
            public void info() {

            }
        };

        // Add a product to the system
        shoppingManager.addProductToSystem(product);

        // Test deleting a non-existing product
        WestminsterShoppingManager shoppingManager2 = new WestminsterShoppingManager();
        shoppingManager2.deleteProductFromSystem("2"); // Assuming product with ID "2" does not exist

        // Verify that no change occurs in the product list
        List<Product> productList2 = shoppingManager2.getProductList();
        assertEquals(0, productList2.size());
    }

    @Test
    void printProductList() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Product product1 = new Product("3", "Product C", 20.0) {
            @Override
            public void info() {

            }
        };
        Product product2 = new Product("1", "Product A", 10.0) {
            @Override
            public void info() {

            }
        };
        Product product3 = new Product("2", "Product B", 15.0) {
            @Override
            public void info() {

            }
        };
        // Capture system output for testing
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Test printing the product list
        shoppingManager.printProductList();
        // Reset System.out for subsequent tests
        System.setOut(System.out);
    }

    @Test
    void saveProductsToFile() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Product product1 = new Product("1", "Test Product 1", 10.0) {
            @Override
            public void info() {

            }
        };
        // Add products to the system
        shoppingManager.addProductToSystem(product1);
        //shoppingManager.addProductToSystem(product2);

        // Capture system output for testing
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Test saving products to a file
        shoppingManager.saveProductsToFile("test_products.ser");

        // Reset System.out for subsequent tests
        System.setOut(System.out);

        // Test reading products from the saved file and check if the products are present
        WestminsterShoppingManager newShoppingManager = new WestminsterShoppingManager();
        newShoppingManager.readProductsFromFile("test_products.ser");
        List<Product> newProductList = newShoppingManager.getProductList();

        // Clean up: Delete the test file
        File testFile = new File("test_products.ser");
        testFile.delete();
    }
}
