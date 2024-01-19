// Importing necessary libraries
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

// Main class for handling user input and interaction
public class InputClass {
    static ArrayList<User> userInputs = new ArrayList<>();// Creating an ArrayList to store User objects
    char userType;
    Scanner scanner = new Scanner(System.in); //to read input
    public void registeringCustomer() {
        System.out.print("Enter your username: ");
        String username = scanner.next().trim(); //trim() method is called to erase any extra whitespaces
//        if (username.isEmpty()) {
//            System.out.println("Username cannot be empty. Please try again.");
//            return;
//        }

        System.out.print("Enter your password: ");
        String password = scanner.next().trim();
//        if (password.isEmpty() || password.length() < 8) {
//            System.out.println("Password should be at least 8 characters long. Please try again.");
//            return;
//        }

        User user = new User(username, password, true);
        user.loadUserDetailsFromFile();
       // user.printarrayyy(); to see who are the registered users in the system
        user.getUserDetails().add(user); //return the userDetails list and add a user obj
        user.saveUserDetailsToFile();

        System.out.println("Display the GUI");
        GUIMainPage gui = new GUIMainPage(user);
    }

    public void customerLogin() {
        System.out.print("Enter your username: ");
        String username = scanner.next().trim();
//        if (username.isEmpty()) {
//            System.out.println("Username cannot be empty. Please try again.");
//            return;
//        }

        System.out.print("Enter your password: ");
        String password = scanner.next().trim();
//        if (password.isEmpty() || password.length() < 8) {
//            System.out.println("Password should be at least 8 characters long. Please try again.");
//            return;
//        }

        User user = new User(username, password, false);
        user.loadUserDetailsFromFile();
        List<User> user_details = user.getUserDetails();

        boolean isValidUser = false;

        for (User u : user_details) {
            if (user.getUsername().equals(u.getUsername()) && user.getPassword().equals(u.getPassword())) {
                System.out.println("Display the GUI");
                GUIMainPage gui = new GUIMainPage(user);
                isValidUser = true;
                break;
            }
        }

        if (!isValidUser) {
            System.out.println("Invalid username or password");
        }
    }


    // Main method for getting user inputs and handling interactions
    public void gettingInputs() {
        // Create an instance of WestminsterShoppingManager and a Scanner for user input
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.readProductsFromFile("product_file");

        char userType;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("Press 'm' for manager, 'c' for customer: ");
            userType = Character.toLowerCase(scanner.next().charAt(0));

            if (userType == 'c') {
                String customerType;
                do {
                    System.out.print("Are you a registered customer? Enter 'r' for registered, 'un' for unregistered: ");
                    customerType = scanner.next();
                    if (customerType.equals("un")) {
                        registeringCustomer();
                    } else if (customerType.equals("r")) {
                        customerLogin();
                    } else {
                        System.out.println("Invalid input. Please enter 'r' for registered or 'un' for unregistered.");
                    }
                } while (!customerType.equals("r") && !customerType.equals("un"));
            } else if (userType == 'm') {
                managerMenu(scanner, shoppingManager);
            } else {
                System.out.println("Invalid input. Please enter 'm' for manager or 'c' for customer.");
            }
        } while (userType != 'm' && userType != 'c');
    }



    // Method for handling manager menu interactions
    private static void managerMenu(Scanner scanner, WestminsterShoppingManager shoppingManager) {
        boolean exitRequested = false;

        do {
            // Display manager menu options
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print product list");
            System.out.println("4. Save products to file");
            //System.out.println("5. Load data from saved file");
            System.out.println("0. Exit");

            // Prompt the user to enter a choice
            System.out.print("Enter your choice -> ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();

                // Switch case to handle user choices
                switch (choice) {
                    case 1: // Add a new product
                        String productType = getValidStringInput(scanner, "Enter product type (Electronics/Clothing): ", "Invalid product type. Please enter 'Electronics' or 'Clothing'.");
//                        System.out.print("Enter the product type :");
//                        String productType =scanner.next();
                        String productId = getValidStringInput(scanner, "Enter product ID : ", "Invalid product ID. Please enter a non-empty string :");
                        String productName = getValidStringInput(scanner, "Enter product name : ", "Invalid product name. Please enter a non-empty string : ");
                        int availableItems = getValidIntInputNew(scanner, "Enter the number of available items:  : ", "Invalid input. Please enter a valid integer : ");
                        double price = getValidDoubleInputNew(scanner, "Enter price : ", "Invalid input. Please enter a valid double: ");

                        if ("Electronics".equalsIgnoreCase(productType)) {
                            String brand = getValidStringInput(scanner, "Enter brand : ", "Invalid brand. Please enter a non-empty string : ");
                            int warrantyPeriod = getValidIntInputNew(scanner, "Enter warranty period (months): ", "Invalid input. Please enter a valid integer: ");
                            shoppingManager.addProductToSystem(new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod));
                            //System.out.println("The product was added successfully.");
                        } else if ("Clothing".equalsIgnoreCase(productType)) {
                            String size = getValidClothingSizeInput(scanner, "Enter size (L/M/S): ", "Invalid size. Please enter 'L', 'M', or 'S'.");
                            String color = getValidStringInput(scanner, "Enter color : ", "Invalid color. Please enter a non-empty string : ");
                            shoppingManager.addProductToSystem(new Clothing(productId, productName, availableItems, price, size, color));
                            //System.out.println("The product was added successfully.");
                        } else {
                            System.out.println("Invalid product type. Please enter 'Electronics' or 'Clothing'.");
                        }
                        break;

                    case 2: // Delete a product
                        String deleteProductId = getValidStringInput(scanner, "Enter product ID to delete: ", "Invalid product ID. Please enter a non-empty string.");
                        shoppingManager.deleteProductFromSystem(deleteProductId);
                        break;

                    case 3: // Print product list
                        shoppingManager.printProductList();
                        break;

                    case 4: // Save products to file
                        shoppingManager.saveProductsToFile("product_file");
                        break;

//                    case 5: // Load data from saved file
//                        System.out.print("Want to load data from 'product_file' ? (yes/no): ");
//                        String userInput = scanner.nextLine().trim().toLowerCase();
//
//                        if (userInput.equals("yes") || userInput.equals("y")) {
//                            shoppingManager.readProductsFromFile("product_file");
//                        } else {
//                            System.out.println("Returning to the main menu.");
//                        }
//                        break;

                    case 0: // Exit the program
                        System.out.println("Exiting the program!!!");
                        exitRequested = true;
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();  // Consume the invalid input
            }

        } while (!exitRequested);
    }




    private static double getValidDoubleInputNew(Scanner scanner, String prompt, String errorMessage) {
        double input = 0.0;
        boolean isValid = false;

        do {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                input = scanner.nextDouble();
                isValid = true;
            } else {
                System.out.println(errorMessage);
                scanner.nextLine();
            }
        } while (!isValid);

        scanner.nextLine();
        return input;
    }

    private static int getValidIntInputNew(Scanner scanner, String prompt, String errorMessage) {
        int input = 0;
        boolean isValid = false;

        do {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                isValid = true;
            } else {
                System.out.println(errorMessage);
                scanner.nextLine();
            }
        } while (!isValid);

        scanner.nextLine();
        return input;
    }

    // Validation method for getting a valid integer input
    private static int getValidIntInput(Scanner scanner, String prompt, String errorMessage, int min, int max) {
        int input = 0;
        boolean isValid = false;

        do {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    isValid = true;
                } else {
                    System.out.println("Input should be between " + min + " and " + max + ". " + errorMessage);
                }
            } else {
                System.out.println(errorMessage);
                scanner.nextLine();
            }
        } while (!isValid);

        scanner.nextLine();
        return input;
    }

    // Validation method for getting a valid double input
    private static double getValidDoubleInput(Scanner scanner, String prompt, String errorMessage, double min, double max) {
        double input = 0.0;
        boolean isValid = false;

        do {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                input = scanner.nextDouble();
                if (input >= min && input <= max) {
                    isValid = true;
                } else {
                    System.out.println("Input should be between " + min + " and " + max + ". " + errorMessage);
                }
            } else {
                System.out.println(errorMessage);
                scanner.nextLine();
            }
        } while (!isValid);

        scanner.nextLine();
        return input;
    }
    // Validation method for getting a valid non-empty string input
    private static String getValidStringInput(Scanner scanner, String prompt, String errorMessage) {
        String input = "";       // Variable to store the user input
        boolean isValid = false;  // Flag to track if the input is valid

        do {
            System.out.print(prompt);  // Display the prompt to the user
            input = scanner.next().trim();  // Get the user input as a trimmed string

            if (!input.isEmpty()) {
                isValid = true;  // Set the flag to true as the input is valid
            } else {
                System.out.println(errorMessage); // Display the error message for empty input
            }
        } while (!isValid); // Repeat until a valid non-empty string input is provided

        return input;       // Return the valid non-empty string input
    }

    // Validation method for getting a valid clothing size input
    private static String getValidClothingSizeInput(Scanner scanner, String prompt, String errorMessage) {
        String input = "";       // Variable to store the user input
        boolean isValid = false;  // Flag to track if the input is valid

        do {
            System.out.print(prompt);  // Display the prompt to the user
            input = scanner.next().trim();  // Get the user input as a trimmed string

            // Check if the input is a valid size for 'Clothing' items
            if (Arrays.asList("L", "M", "S", "l", "m", "s").contains(input)) {
                isValid = true;  // Set the flag to true as the input is valid
            } else {
                System.out.println(errorMessage); // Display the error message for invalid size
            }
        } while (!isValid); // Repeat until a valid size input is provided

        return input;       // Return the valid size input
    }

    // Method for checking the user status and displaying the appropriate GUI
    public void checkUserStatus(String username, String password) {
        Scanner scanner = new Scanner(System.in);

        boolean isExistingUser = false;

        String customerType;
        do {
            System.out.print("Are you a registered customer? Enter 'r' for registered, 'un' for unregistered: ");
            User userprint=new User();
            //userprint.printarrayyy();
            customerType = scanner.next();
            if (!customerType.equals("r") && !customerType.equals("un")) {
                System.out.println("Invalid input. Please enter 'r' or 'un'.");
            }
        } while (!customerType.equals("r") && !customerType.equals("un"));

        if (customerType.equals("r")) {
//            System.out.println("enter username : ");
//            String R_username=scanner.nextLine();
//            System.out.println("enter password : ");
//            String R_password=scanner.nextLine();
            //loadUserDetailsFromFile();
            User user=new User(username,password,true);
            user.loadUserDetailsFromFile();
            List <User> userDetails=user.getUserDetails();
            if (userDetails!=null){
                for (User user1:userDetails){
                    if (user.getUsername().equals(user1.getUsername()) && user.getPassword().equals(user1.getPassword())){
                        System.out.println("succes log innnnnn");
                    } else {
                        System.out.println("INCORRECT USERNAMEM PASWOD");
                    }
                }

            }

        }
        else if (customerType.equals("un")) {
            // Unregistered customer, create a new user
            String nameOfUser = username;
            String passwordOfUser = password;
            boolean typeOfUser = true;
            User newUser = new User(nameOfUser, passwordOfUser, typeOfUser);
            newUser.loadUserDetailsFromFile();
            newUser.getUserDetails().add(newUser);
            newUser.saveUserDetailsToFile();
            System.out.println("new user successuflhthuhuhuh");
            //newUser.printarrayyy();
           // userInputs.add(newUser);
//            loadUserDetailsFromFile();
//            saveUserDetailsToFile();

            //printarrayyy();
            GUIMainPage gui = new GUIMainPage(newUser);
            System.out.println("Welcome to the shop! and get a 10% discount");
            // gui.initializeGUI(newUser);
        }
        if (!isExistingUser) {
            System.out.println("Invalid username or password. Please try again."+username+"   "+password);
        }
    }

    // Method for saving user details to a file
    public static void saveUserDetailsToFile() {
        File file = new File("userDetails.txt");

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            // Save userInputs to text file
            objectOutputStream.writeObject(userInputs);
            System.out.println("Details saved to file successfully");

        } catch (IOException e) {
            System.err.println("Error saving user details to file: " + e.getMessage());
        }
    }

}
