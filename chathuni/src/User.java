import java.io.*;
import java.util.ArrayList;
import java.util.List;

// User class represents a user in the shopping system
public class User implements Serializable {

    // Private fields to store user information
    private String username;
    private String password;
    private boolean type;
    private List<User> userDetails;

    // Constructor to initialize user attributes
    public User(String username, String password, boolean type) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.userDetails=new ArrayList<>();
    }

    public User() {

    }

    // Getter method for retrieving the username
    public String getUsername() {
        return username;
    }

    // Getter method for retrieving the user type
    public boolean isType() {
        return type;
    }

    // Setter method for setting the user type
    public void setType(boolean type) {
        this.type = type;
    }

    // Setter method for setting the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method for retrieving the password
    public String getPassword() {
        return password;
    }

    // Setter method for setting the password
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }

    public  void saveUserDetailsToFile() {
        File file = new File("userDetails.txt");    // Create a File object representing the file "userDetails.txt"
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            // Save the ArrayList<User> userDetails to the text file using ObjectOutputStream
            objectOutputStream.writeObject(userDetails);        // Print a success message to the console
            System.out.println("Details saved to file successfully");

        } catch (IOException e) {        // Print an error message if an IOException occurs during the file writing process
            System.err.println("Error saving user details to file: " + e.getMessage());
        }
    }
    // Method for loading user details from a file
    public void loadUserDetailsFromFile() {
        File file = new File("userDetails.txt");    // Create a File object representing the file "userDetails.txt"

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {// Use ObjectInputStream to read serialized objects(conversion of an object to a series of bytes) from a FileInputStream
            // Code inside this block reads objects from the input stream
            // The try-with-resources(try statement that declares one or more resources) statement ensures that the ObjectInputStream is closed automatically
            userDetails = (ArrayList<User>) objectInputStream.readObject();
            // Print a success message to the console
            System.out.println("Details loaded from text file successfully");

        } catch (IOException | ClassNotFoundException e) {
            // Print an error message if an IOException or ClassNotFoundException occurs during the file reading process
            System.err.println("Error loading user details from text file: " + e.getMessage());
        }
    }

    public List<User> getUserDetails() {
        return userDetails;
    }
//    public void printarrayyy(){
//       for (User user:userDetails){
//           System.out.println(userDetails.toString());
//           System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//       }
//    }
}
