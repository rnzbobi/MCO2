import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class FoodItem {
    private String imagePath;
    private String calorie;
    private String name;
    private String price;
    private int quantity;

    public FoodItem(String imagePath, String calorie, String name, String price) {
        this.imagePath = imagePath;
        this.calorie = calorie;
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCalorie() {
        return calorie;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

public class FoodMenuApp {
    private ArrayList<FoodItem> foodItems;
    private JPanel innerPanel;

    public FoodMenuApp() {
        foodItems = new ArrayList<>();
        innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        // Add some example food items
        addFoodItem("example.jpg", "200", "Food Item 1", "5.99");
        addFoodItem("example.jpg", "300", "Food Item 2", "7.99");

        // Create the scroll pane
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Create the main frame
        JFrame frame = new JFrame("Food Menu App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }

    private void addFoodItem(String imagePath, String calorie, String name, String price) {
        FoodItem foodItem = new FoodItem(imagePath, calorie, name, price);
        foodItems.add(foodItem);

        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel label = new JLabel();
        label.setIcon(resizedIcon);
        label.setText("<html>Calorie: " + calorie + "<br>Name: " + name + "<br>Price: " + price + "<br>Quantity: " + foodItem.getQuantity() + "</html>");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        innerPanel.add(label);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FoodMenuApp());
    }
}