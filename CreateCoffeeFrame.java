import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateCoffeeFrame extends JFrame {
    private JLabel titleLabel;
    private JLabel slotImageLabel;
    private JButton addIngredientButton;
    private JButton finishButton;
    private JButton cancelButton;
    private JTextField ingredientTextField;
    private JTextField quantityTextField; // New JTextField for quantity

    public CreateCoffeeFrame() {
        super("Create Your Coffee");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 500));
        setLayout(new BorderLayout());

        // Title Label
        titleLabel = new JLabel("CREATE YOUR COFFEE!");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Slot Image Label (You can replace "path/to/slot/image.png" with your actual image path)
        ImageIcon slotImageIcon = new ImageIcon("C:\\Users\\rhenz\\Documents\\GitHub\\MCO2\\Coffee.png");
        slotImageLabel = new JLabel(slotImageIcon);
        slotImageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(slotImageLabel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add Ingredient Button
        addIngredientButton = new JButton("Add Ingredient");
        buttonPanel.add(addIngredientButton);

        // Ingredient Text Field
        ingredientTextField = new JTextField(15);
        buttonPanel.add(ingredientTextField);

        // Quantity Text Field (New)
        quantityTextField = new JTextField(5);
        buttonPanel.add(new JLabel("Quantity:"));
        buttonPanel.add(quantityTextField);

        // Finish Button
        finishButton = new JButton("Finish");
        buttonPanel.add(finishButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the frame
            }
        });
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Pack and center the frame on the screen
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CreateCoffeeFrame frame = new CreateCoffeeFrame();
                frame.setVisible(true);
            }
        });
    }
}