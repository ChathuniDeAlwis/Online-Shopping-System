import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

// SecondGUI class extends JFrame to create a shopping cart GUI
public class SecondGUI extends JFrame {
    private static GUIMainPage guiMainPage;
    private JTable table;
    static List<Product> selectedProducts;  //hold the selected products.

//    User user=new User();
//    List<User> userInputs=user.getUserDetails();
    ShoppingCart s1 = new ShoppingCart(selectedProducts);

    // Setter method for GUIMainPage
    public static void setGUIMainPage(GUIMainPage guiMainPage) {
        SecondGUI.guiMainPage = guiMainPage;
    }

    // Method to add selected products to the shopping cart and update the GUI
    public static void addToCart(List<Product> selectedProducts, User user){   //takes a list of selected products and a user.
        SecondGUI.selectedProducts = selectedProducts;  //sets the static variable selectedProducts of the SecondGUI class to the provided list.
        if (guiMainPage != null) {  //If guiMainPage is not null, it calls the updateShoppingCartTable method on guiMainPage to update the shopping cart table in the main page GUI.
            guiMainPage.updateShoppingCartTable(selectedProducts);
        }
//        System.out.println("second gui");
        System.out.println(selectedProducts);
        System.out.println(user);

    }

    // Constructor to initialize the SecondGUI with selected products
    public SecondGUI(List<Product> selectedProducts, User user) {  //constructor for SecondGUI takes a list of selected products.
        this.selectedProducts = selectedProducts;   //initializes the instance variable selectedProducts with the provided list.
        initialization(user);   //calls the initialization method to set up the GUI components.
        updateShoppingCartTable(selectedProducts);  //update the shopping cart table with the selected products.

    }

    // Method to initialize the GUI components
    private void initialization(User user) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Shopping Cart");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create main panel
        JPanel fullPanel = new JPanel();
        fullPanel.setLayout(new BorderLayout());
        //fullPanel.setBackground(Color.GREEN);
        add(fullPanel);

        // Create upper panel
        JPanel upPanel = new JPanel();
        //upPanel.setBackground(Color.RED);
        upPanel.setBorder(new EmptyBorder(28, 10, 10, 10));
        upPanel.setLayout(new BorderLayout());
        fullPanel.add(upPanel, BorderLayout.NORTH);

        // Create table model for the shopping cart
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Product");
        model.addColumn("Quantity");
        model.addColumn("Price ($)");

        // Create JTable and add it to a JScrollPane
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 250));
        table.setRowHeight(70);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));

        upPanel.add(scrollPane, BorderLayout.CENTER);

        // Create lower panel
        JPanel downPanel = new JPanel(new BorderLayout());
        //downPanel.setBackground(Color.BLUE);
        downPanel.setBorder(new EmptyBorder(250, 10, 50, 10));

        // Create left panel for labels
        JPanel downLabelsPanel = new JPanel(new GridLayout(4, 1));
        //downLabelsPanel.setBackground(Color.YELLOW);
        downLabelsPanel.setBorder(new EmptyBorder(15, 150, 15, 5));

        // Create labels for total, discounts, and final total
        JLabel total = new JLabel("Total");
        JLabel firstDiscount = new JLabel("First purchase discount 10%");
        JLabel secondDiscount = new JLabel("Three items in the same category discount 20%");
        JLabel finalTotal = new JLabel("Final Total");

        // Set labels to right-align within downLabelsPanel
        total.setHorizontalAlignment(SwingConstants.RIGHT);
        firstDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
        secondDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
        finalTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        // Add labels to the left panel
        downLabelsPanel.add(total);
        downLabelsPanel.add(firstDiscount);
        downLabelsPanel.add(secondDiscount);
        downLabelsPanel.add(finalTotal);

        downPanel.add(downLabelsPanel, BorderLayout.WEST);

        // Create right panel for text values
        JPanel downTextPanel = new JPanel(new GridLayout(4, 1));
        //downTextPanel.setBackground(Color.cyan);
        downTextPanel.setBorder(new EmptyBorder(0, 0, 0, 70));



        // Create text labels for total, discounts, and final total
        JLabel label = new JLabel(String.valueOf(s1.calculateTotalCost(selectedProducts, user.isType())[0])+" $");
        JLabel label1 = new JLabel(String.valueOf(s1.calculateTotalCost(selectedProducts,user.isType())[1])+" $");
        JLabel label2 = new JLabel(String.valueOf(s1.calculateTotalCost(selectedProducts,user.isType())[2])+" $");
        JLabel label3 = new JLabel(String.valueOf(s1.calculateTotalCost(selectedProducts,user.isType())[3])+" $");


        if(user.isType() == true){

                for(int i=0;i<InputClass.userInputs.size();i++){

                    if (user.getUsername().equals(InputClass.userInputs.get(i).getUsername())) {
                        InputClass.userInputs.get(i).setType(false); // user become products brought customer
                    }
                }
        }
        System.out.println(user.getUsername());
        System.out.println("Type: "+user.isType());
        InputClass.saveUserDetailsToFile();
//        System.out.println("11141414141414");
//        for (int i=0;i<InputClass.userInputs.size();i++){
//            System.out.println(InputClass.userInputs.get(i).getUsername()+"  "+InputClass.userInputs.get(i).getPassword());
//        }
        // Add text labels to the right panel
        downTextPanel.add(createCenteredRightAlignedPanel(label));
        downTextPanel.add(createCenteredRightAlignedPanel(label1));
        downTextPanel.add(createCenteredRightAlignedPanel(label2));
        downTextPanel.add(createCenteredRightAlignedPanel(label3));

        downPanel.add(downTextPanel, BorderLayout.EAST);

        // Add lower panel to the main panel
        fullPanel.add(downPanel, BorderLayout.SOUTH);
    }

    // Method to update the shopping cart table with selected products
    public void updateShoppingCartTable(List<Product> selectedProducts) {
        DefaultTableModel model = (DefaultTableModel) table.getModel(); //a DefaultTableModel named model is created, representing the table model associated with the JTable named table in the GUI.
        model.setRowCount(0);   //clears all existing rows in the table.

        for (Product product : selectedProducts) {  //through the selectedProducts list, and for each product, it creates an Object array (rowData) containing the product's name, the number of available items, and the price.
            Object[] rowData = {product.getName(), product.getNumOfAvaItems(), product.getPrice()};
            model.addRow(rowData);  //rowData is added as a new row to the table
        }
    }

    // Method to create a centered and right-aligned panel for a label
    private JPanel createCenteredRightAlignedPanel(JLabel label) {
        JPanel panel = new JPanel(new BorderLayout());
        //panel.setBackground(Color.YELLOW);
        panel.add(label, BorderLayout.CENTER);
        label.setHorizontalAlignment(SwingConstants.RIGHT); //horizontal alignment of the label is set to right-aligned
        return panel;
    }

}
