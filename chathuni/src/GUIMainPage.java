import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class GUIMainPage {
    private static List<Product> productList;
    private List<Electronics> electronicsList;  // List to store Electronics products
    private List<Clothing> clothingList;    // List to store Clothing products
    private JTable table;       // JTable to display product information
    User user;    // User object representing the current user
    JButton addToCart;    // Button to add selected products to the shopping cart
    private JPanel bottomPanel;    // Panel to hold components at the bottom of the GUI
    private JButton shopCartButton;    // Button to navigate to the shopping cart
    private JPanel displayProductDetailsPanel;  // Panel to display details of selected product
    static List<Product> selectedProducts = new ArrayList<>(); // List to store currently selected products
    ShoppingCart shoppingCart=new ShoppingCart(selectedProducts);    // Shopping cart instance to manage selected products
    public GUIMainPage(User user) {// Constructor for the GUIMainPage class
        initializeGUI(user);        // Initialize the GUI components
        loadDataFromFile();         // Load product data from "product_file"
        updateTableData("All");         // Update the table with all products initially
        addTableListener();         // Add listener for table events

        // Pass the reference to the current GUIMainPage instance to SecondGUI
        SecondGUI.setGUIMainPage(this);
    }
    void initializeGUI(User user) {
        // Frame setup
        JFrame frame = new JFrame();// Create a new JFrame for the GUI
        frame.setSize(800, 600);// Set the size of the frame
        frame.setTitle("Westminster Shopping Center");// Set the title of the frame
        frame.setLocationRelativeTo(null);// Center the frame on the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Set default close operation

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());// Create the main panel with a BorderLayout
        //mainPanel.setBackground(Color.GREEN);// Set the background color of the main panel
        frame.setContentPane(mainPanel);// Set the content pane of the frame to the main panel

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());// Create the top panel with a BorderLayout
        //topPanel.setBackground(Color.RED);// Set the background color of the top panel

        // Text Panel (first column)
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));// Create a panel for text with left alignment
        //textPanel.setBackground(Color.BLUE);
        JLabel text = new JLabel("Select product category");// Create a label for the text
        setupLabel(text);// Call a method to set up label properties
        textPanel.add(text);// Add the text label to the text panel
        topPanel.add(textPanel, BorderLayout.WEST);

        // Combo Box Panel
        JPanel comboPanel = new JPanel();// Create a panel for the combo box
        //comboPanel.setBackground(Color.MAGENTA);
        String[] options = {"All", "Electronics", "Clothing"};// Define options for the combo box
        JComboBox<String> comboBox = new JComboBox<>(options);// Create a combo box with the options
        comboBox.setEditable(false);// Allow user input in the combo box
        setupComboBox(comboPanel, comboBox);// Call a method to set up combo box properties
        topPanel.add(comboPanel, BorderLayout.CENTER);// Add the combo box panel to the center of the top panel

        // Shopping cart button
        JPanel shopCartPanel = new JPanel();// Create a panel for the shopping cart button
        //shopCartPanel.setBackground(Color.BLACK);
        shopCartButton = new JButton("Shopping Cart");// Create the shopping cart button
        setupButton(shopCartPanel, shopCartButton,user);// Call a method to set up button properties
        shopCartButton.addActionListener(new ActionListener() {// Add an action listener to the shopping cart button
            @Override
            public void actionPerformed(ActionEvent e) {
                SecondGUI.addToCart(selectedProducts, user);// Call addToCart method in SecondGUI
            }
        });
        topPanel.add(shopCartPanel, BorderLayout.EAST);// Add the shopping cart button to the east side of the top panel
        topPanel.setPreferredSize(new Dimension(topPanel.getPreferredSize().width, 90));// Set the preferred height of the top panel
        mainPanel.add(topPanel, BorderLayout.NORTH);// Add the top panel to the north side of the main panel


        // Middle Panel with Table
        JPanel midPanel = new JPanel(new BorderLayout());// Create the middle panel with a BorderLayout
        //midPanel.setBackground(Color.ORANGE);
        midPanel.setBorder(new EmptyBorder(28, 10, 5, 10));// Set an empty border for spacing


        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());// Create a panel for the table with a BorderLayout
        tablePanel.setBorder(new EmptyBorder(20, 10, 2, 10));// Set an empty border for spacing
        //tablePanel.setBackground(Color.YELLOW);

        DefaultTableModel tableModel = new DefaultTableModel();// Create a default table model
        setupTableModel(tableModel);// Call a method to set up table model properties

        table = new JTable(tableModel);// Create the table with the table model
        setupTable(table);// Call a method to set up table properties

        JScrollPane scroll = new JScrollPane(table);// Create a scroll pane for the table
        tablePanel.add(scroll, BorderLayout.CENTER);// Add the scroll pane to the center of the table panel
        midPanel.add(tablePanel, BorderLayout.CENTER);// Add the table panel to the center of the middle panel
        mainPanel.add(midPanel, BorderLayout.CENTER);// Add the middle panel to the center of the main panel


        // Separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);// Create a horizontal separator
        //separator.setBackground(Color.green);
        midPanel.add(separator, BorderLayout.SOUTH);// Add the separator to the south side of the middle panel

        //Bottom panel
        bottomPanel = new JPanel(new BorderLayout());// Create the bottom panel with a BorderLayout
        bottomPanel.setBorder(new EmptyBorder(5, 10, 5, 10));// Set an empty border for spacing
        JLabel detail = new JLabel("Selected Product - Details");// Create a label for selected product details
        //detail.setBackground(Color.yellow);
        detail.setBorder(new EmptyBorder(5, 10, 5, 10));// Set an empty border for spacing
        //bottomPanel.setBackground(Color.lightGray);
        bottomPanel.add(detail, BorderLayout.NORTH);// Add the detail label to the north side of the bottom panel

        //display detail panel
        displayProductDetailsPanel=new JPanel(new BorderLayout());// Create a panel for displaying product details
        //displayProductDetailsPanel.setBackground(Color.red);
        bottomPanel.add(displayProductDetailsPanel,BorderLayout.CENTER);// Add the detail display panel to the center of the bottom panel
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);// Add the bottom panel to the south side of the main panel
        bottomPanel.setPreferredSize(new Dimension(bottomPanel.getPreferredSize().width, 280));// Set the preferred height of the bottom panel

        // Create the button
        addToCart = new JButton("Add to Shopping Cart");// Create the "Add to Shopping Cart" button
        //addToCart.setBackground(Color.WHITE);

        JPanel addToCartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Create a panel for the button with right alignment
        //addToCartPanel.setBackground(Color.BLACK);
        addToCartPanel.setBorder(new EmptyBorder(1, 10, 5, 300));// Set an empty border for spacing
        addToCartPanel.add(addToCart); // Add the button to the bottom right of the addToCartPanel
        addToCart.setBorder(new EmptyBorder(5, 10, 5, 10)); // Add padding around the button
        bottomPanel.add(addToCartPanel, BorderLayout.SOUTH); // Add the addToCartPanel to the south side of the bottom panel
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the south side of the main panel

        frame.setVisible(true);// Make the frame visible
    }
    private void setupLabel(JLabel label) {
        label.setHorizontalAlignment(SwingConstants.LEFT);// Set the horizontal alignment of the label to the left
        label.setBorder(new EmptyBorder(30, 10, 10, 10));// Set an empty border around the label for spacing
    }
    private void setupComboBox(JPanel panel, JComboBox<String> comboBox) {        // Add an ActionListener to the combo box to handle selection changes
        comboBox.addActionListener(e -> {// Retrieve the selected category from the combo box
            String selectedCategory = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
            updateTableData(selectedCategory);            // Update the table data based on the selected category
        });

        panel.setBorder(new EmptyBorder(30, 10, 10, 10));        // Set an empty border around the panel for spacing
        panel.add(comboBox);        // Add the combo box to the panel
    }
    private void setupButton(JPanel panel, JButton button,User user) { //"Shopping Cart" button
        shopCartButton.addActionListener(new ActionListener() {        // Add an ActionListener to the button to handle button click events
            @Override
            public void actionPerformed(ActionEvent e) {
                SecondGUI secondGUI = new SecondGUI(selectedProducts,user);                // Create an instance of GUISecondPage with selected products and the current user
                secondGUI.setVisible(true);                // Display the GUISecondPage

            }
        });

        panel.setBorder(new EmptyBorder(5, 10, 10, 10));        // Set an empty border around the panel for spacing
        panel.add(button);        // Add the button to the panel

    }
    // set up table model
    private void setupTableModel(DefaultTableModel tableModel) {         //DefaultTableModel -> used to store and manage data for a JTable component
        tableModel.addColumn("Product ID");        // Add a column for Product ID to the table model
        tableModel.addColumn("Name");        // Add a column for Name to the table model
        tableModel.addColumn("Category");        // Add a column for Category to the table model
        tableModel.addColumn("Price($)");        // Add a column for Price($) to the table model
        tableModel.addColumn("Info");        // Add a column for Info to the table model
    }
    private void setupTable(JTable table) { //set up table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();        // Create a cell renderer for centering the content in cells
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {        // Iterate over each column in the table
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);            // Set the cell renderer for each column to center-align the content
            if (i == table.getColumnCount() - 1) {            // Adjust preferred width for the last column to accommodate longer text (Info column)
                table.getColumnModel().getColumn(i).setPreferredWidth(200);
            } else {                // Set a preferred width for other columns
                table.getColumnModel().getColumn(i).setPreferredWidth(10);
            }
        }
        table.setRowHeight(25);        // Set the row height for the table
    }
    private void loadDataFromFile() {
        productList = readProductsFromFile("product_file");        // Read product data from the file named "product_file" and assign it to the productList
        electronicsList = filterProductsByCategory(productList, Electronics.class);        // Filter products in the productList by category, specifically Electronics, and assign the result to electronicsList
        clothingList = filterProductsByCategory(productList, Clothing.class);        // Filter products in the productList by category, specifically Clothing, and assign the result to clothingList
    }
    private void updateTableData(String selectedCategory) { //add data to cells
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();        // Get the table model associated with the JTable
        tableModel.setRowCount(0);        // Clear the existing rows in the table model

        List<? extends Product> selectedList;        // Define a list to hold the selected products based on the chosen category
        if ("Electronics".equals(selectedCategory)) {// If Electronics is selected, use the electronicsList
            selectedList = electronicsList;
        } else if ("Clothing".equals(selectedCategory)) {// If Clothing is selected, use the clothingList
            selectedList = clothingList;
        } else {
            selectedList = productList;// If All or an unrecognized category is selected, use the productList
        }

        for (Product product : selectedList) {        // Iterate over the selected list of products and add their information to the table
            Object[] rowData = {product.getID(), product.getName(), product.getClass().getName(), product.getPrice(), getInfo(product)}; //cell data
            tableModel.addRow(rowData);// Add a row to the table model with product information
        }
    }
    private <T extends Product> List<T> filterProductsByCategory(List<Product> productList, Class<T> categoryClass) {        // Create a new list to store products of the specified category class
        List<T> filteredList = new ArrayList<>();
        for (Product product : productList) {        // Iterate through each product in the given productList
            if (categoryClass.isInstance(product)) {            // Check if the current product is an instance of the specified category class
                filteredList.add(categoryClass.cast(product));                // If true, cast the product to the specified category class and add it to the filteredList
            }
        }
        return filteredList;        // Return the filtered list containing products of the specified category class
    }
    private String getInfo(Product product) {        // Check if the product is an instance of Electronics
        if (product instanceof Electronics) {            // If true, return a string containing the brand and warranty period of the Electronics product
            return "Brand : " + ((Electronics) product).getBrand() + "     WarrantyPeriod : " + ((Electronics) product).getWarrantyPeriod();
        } else {            // If the product is not an instance of Electronics, assume it is Clothing
            return "Color : " + ((Clothing) product).getColor() + "    Size : " + ((Clothing) product).getSize();
        }// Return a string containing the color and size of the Clothing product
    }
    private static List<Product> readProductsFromFile(String file) {        // Create a new ArrayList to store the products read from the file
        List<Product> productList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {        // Use try-with-resources(a try statement that declares one or more resources) to handle the ObjectInputStream and FileInputStream
            productList = (List<Product>) ois.readObject();            // Read the list of products from the file and cast it to a List<Product>
            System.out.println("Products loaded from file: " + file);            // Print a message indicating that products have been successfully loaded from the file
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();            // Handle IOException and ClassNotFoundException by printing the stack trace
        }
        return productList;        // Return the list of products read from the file, which may be an empty list if an exception occurred

    }

    private int getNumOfAvailableItems(String productId, String category) {        // Define a list to hold the selected products based on the chosen category
        List<? extends Product> selectedList;
        if ("Electronics".equals(category)) {        // Check the selected category and assign the appropriate list
            selectedList = electronicsList;// If Electronics is selected, use the electronicsList
        } else if ("Clothing".equals(category)) {
            selectedList = clothingList;// If Clothing is selected, use the clothingList
        } else {
            selectedList = productList;// If All or an unrecognized category is selected, use the productList
        }

        for (Product product : selectedList) {        // Iterate over the selected list of products
            if (product.getID().equals(productId)) {            // Check if the current product has the specified productId
                return product.getNumOfAvaItems();                // Return the number of available items for the matching product
            }
        }
        return 0;        // If no matching product is found, return 0 as the default value
    }
    private Object lastSelectedValue;  // Declare a variable outside the listener
    private void addTableListener() {        // Get the selection model for the JTable
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {        // Add a list selection listener to the selection model
            if (!e.getValueIsAdjusting() && table.getSelectedRowCount() == 1) {            // Check if the selection is not adjusting and only one row is selected
                int selectedRow = table.getSelectedRow();                // Get the selected row index
                DefaultTableModel model = (DefaultTableModel) table.getModel();                // Get the table model associated with the JTable
                Object value = model.getValueAt(selectedRow, 0);                // Get the value of the first column of the selected row (assuming it contains the product ID)

                lastSelectedValue = value;                // Update the variable with the latest selected value
//                System.out.println("adadad");
//                System.out.println(value);
//                System.out.println("***********");
///////////////////////////////////////////////////////////////////////////////////////////////////////
                // Remove existing action listeners from addToCart button
                for (ActionListener al : addToCart.getActionListeners()) {
                    addToCart.removeActionListener(al);
                }
////////////////////////////////////////////////////////////////////////////////////////////////////
                addToCart.addActionListener(e1 -> {                // Add a new action listener to the addToCart button
                    // Use the lastSelectedValue here
                    System.out.println("Selected Product ID: " + lastSelectedValue);                // Use the lastSelectedValue here
                    // Iterate through the productList to find the selected product
                    for(int i=0;i<productList.size();i++){                // Iterate through the productList to find the selected product
                        if(productList.get(i).getID().equals(value)){            // Perform actions based on the type of product (Electronics or Clothing)
                            //System.out.println("Chathuni Max");
                            if(productList.get(i) instanceof Electronics){ // Actions for Electronics type
                                Object name = productList.get(i).getName();
                                int numOfAvaItems = 1;
                                double price = productList.get(i).getPrice();
                                Object brand = ((Electronics) productList.get(i)).getBrand();
                                Object warrantyPeriod = ((Electronics) productList.get(i)).getWarrantyPeriod();
                                //System.out.println("//////////////////////////");
                                if(selectedProducts.size() >0){                            // Check if selectedProducts list has items
                                    //System.out.println("***************************");

                                    for(int j=0;j <selectedProducts.size();j++){   // Iterate through selectedProducts list
                                        if(lastSelectedValue == selectedProducts.get(j).getID()){   // Check if the lastSelectedValue matches the ID in selectedProducts
                                            numOfAvaItems = selectedProducts.get(j).getNumOfAvaItems() + 1;
                                            selectedProducts.remove(j);
                                            break;
                                        }
                                    }
                                }
                                Product electronicsObj  = new Electronics((String) lastSelectedValue, (String) name,numOfAvaItems,price, (String) brand, (Double) warrantyPeriod);                            // Create a new Electronics object and add it to selectedProducts
                                numOfAvaItems = 1;
                                System.out.println("Num: "+ numOfAvaItems);
                                selectedProducts.add(electronicsObj);
                                System.out.println("selectedProducts : "+selectedProducts);
                            }
                            else if(productList.get(i) instanceof Clothing){// Actions for Clothing type

                                Object name = productList.get(i).getName();
                                int numOfAvaItems = 1;
                                double price = productList.get(i).getPrice();
                                String size = ((Clothing) productList.get(i)).getSize();
                                String color = ((Clothing) productList.get(i)).getColor();

                                if(selectedProducts.size() >0){                            // Check if selectedProducts list has items
                                    for(int j=0;j <selectedProducts.size();j++){                                // Iterate through selectedProducts list
                                        if(lastSelectedValue == selectedProducts.get(j).getID()){                                    // Check if the lastSelectedValue matches the ID in selectedProducts
                                            numOfAvaItems = selectedProducts.get(i).getNumOfAvaItems() + 1;
                                            selectedProducts.remove(j);
                                            break;
                                        }
                                    }
                                }
                                Product clothingObj = new Clothing((String) lastSelectedValue, (String) name,numOfAvaItems,price,size,color);                            // Create a new Clothing object and add it to selectedProducts
                                numOfAvaItems = 1;
                                selectedProducts.add(clothingObj);
                            }
                        }
                    }
                });
                displaySelectedProductDetails(selectedRow);                // Display details of the selected product
            }
        });
    }
    private JLabel createLabelWithBorder(String text) {
        JLabel label = new JLabel(text);        // Create a new JLabel with the specified text
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));         // Set an empty border around the label for spacing
        return label;        // Return the created and configured label
    }
    private void displaySelectedProductDetails(int selectedRow) {        // Extract product details from the selected row in the table
        String productId = table.getValueAt(selectedRow, 0).toString();
        String productName = table.getValueAt(selectedRow, 1).toString();
        String category = table.getValueAt(selectedRow, 2).toString();
        int numOfAvailableItems = getNumOfAvailableItems(productId, category);

        // Find the selected product in the productList
        Product selectedProduct = null;
        for (Product product : productList) {        // Check if the product ID matches the selected row's product ID
            if (product.getID().equals(productId)) {
                selectedProduct = product;
                break;
            }
        }

        if (selectedProduct != null) {                // Check if the selected product is found in the productList
            JPanel productDetailsPanel = new JPanel();            // Create a new panel to display product details
            productDetailsPanel.setLayout(new BoxLayout(productDetailsPanel, BoxLayout.Y_AXIS));
            //productDetailsPanel.setBackground(Color.pink);
            productDetailsPanel.setBorder(new EmptyBorder(10, 5, 5, 5));

            // Add specific details based on the product category with spaces between rows
            productDetailsPanel.add(createLabelWithBorder("Product ID: " + productId));
            productDetailsPanel.add(createLabelWithBorder("Category: " + category));
            productDetailsPanel.add(createLabelWithBorder("Name: " + productName));
            productDetailsPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space

            // Check if the selected product is an instance of Electronics
            if (selectedProduct instanceof Electronics) {            // Check if the selected product is an instance of Electronics
                Electronics electronicsProduct = (Electronics) selectedProduct;
                productDetailsPanel.add(createLabelWithBorder("Brand: " + electronicsProduct.getBrand()));
                productDetailsPanel.add(createLabelWithBorder("Warranty Period: " + electronicsProduct.getWarrantyPeriod()));
                productDetailsPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space
            } else if (selectedProduct instanceof Clothing) {          // If not Electronics, check if it is an instance of Clothing// If not Electronics, check if it is an instance of Clothing
                Clothing clothingProduct = (Clothing) selectedProduct;
                productDetailsPanel.add(createLabelWithBorder("Color: " + clothingProduct.getColor()));
                productDetailsPanel.add(createLabelWithBorder("Size: " + clothingProduct.getSize()));
                productDetailsPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space
            }
            productDetailsPanel.add(createLabelWithBorder("Items Available: " + numOfAvailableItems));            // Add the number of available items information
            bottomPanel.remove(displayProductDetailsPanel);                    // Remove the existing product details panel from the bottomPanel
            displayProductDetailsPanel = productDetailsPanel;        // Set the new product details panel to the center of the bottom panel
            bottomPanel.add(displayProductDetailsPanel, BorderLayout.CENTER);        // Add the updated product details panel to the bottomPanel's center using BorderLayout
            bottomPanel.revalidate();        // Ensure the layout is recalculated to reflect the changes
            bottomPanel.repaint();        // Trigger a repaint to update the graphical representation of the components
        }
    }
    void updateShoppingCartTable(List<Product> selectedProducts) {
    }
}
