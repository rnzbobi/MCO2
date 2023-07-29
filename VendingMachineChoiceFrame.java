import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VendingMachineChoiceFrame extends JFrame {
    private JTextField machineNameField;
    private JButton startRegularButton;
    private JButton startSpecialButton;
    private JButton exitButton;

    public VendingMachineChoiceFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Vending Machine Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 500));
        setLayout(new BorderLayout());

        // Create a label for the factory name
        JLabel factoryLabel = new JLabel("FACTORY");
        factoryLabel.setFont(new Font(factoryLabel.getFont().getName(), Font.BOLD, 24));
        factoryLabel.setHorizontalAlignment(JLabel.CENTER);
        factoryLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Create a label and text field to enter the machine name
        JLabel nameLabel = new JLabel("Enter Your Name:");
        machineNameField = new JTextField(20);

        // Create the start buttons
        startRegularButton = new JButton("Start Regular Vending Machine");
        startRegularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the entered machine name
                String machineName = machineNameField.getText();

                // Close the VendingMachineChoiceFrame
                dispose();

                // Launch the FoodMenuApp with the entered machine name
                FoodMenuApp.getInstance(machineName);
            }
        });

        startSpecialButton = new JButton("Start Special Vending Machine");
        startSpecialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Do nothing for the Special Vending Machine
            }
        });

        // Create the exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the application
                System.exit(0);
            }
        });

        // Create a panel to hold the components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(factoryLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        panel.add(machineNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(startRegularButton, gbc);

        gbc.gridy = 3;
        panel.add(startSpecialButton, gbc);

        gbc.gridy = 4;
        panel.add(exitButton, gbc);

        // Add the panel to the content pane
        getContentPane().add(panel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VendingMachineChoiceFrame choiceFrame = new VendingMachineChoiceFrame();
                choiceFrame.setVisible(true);
            }
        });
    }
}