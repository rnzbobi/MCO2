import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MoneyInsertionFrame extends JFrame {
    private JTextField moneyField;
    private Map<String, Integer> denominations;
    private int totalAmount;

    public MoneyInsertionFrame() {
        super("Balance");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));

        // Initialize the denominations
        denominations = new HashMap<>();
        denominations.put("1000", 1000);
        denominations.put("500", 500);
        denominations.put("200", 200);
        denominations.put("100", 100);
        denominations.put("50", 50);
        denominations.put("20", 20);
        denominations.put("10", 10);
        denominations.put("5", 5);
        denominations.put("1", 1);

        totalAmount = 0;

        // Create a panel to hold the "INSERT MONEY" label and text field
        JPanel insertMoneyPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel insertMoneyLabel = new JLabel("INSERT MONEY: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        insertMoneyPanel.add(insertMoneyLabel, gbc);

        moneyField = new JTextField(10);
        moneyField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        insertMoneyPanel.add(moneyField, gbc);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalAmount = 0;
                updateMoneyField();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        insertMoneyPanel.add(clearButton, gbc);

        add(insertMoneyPanel, BorderLayout.NORTH);

        // Create the number pad buttons
        JPanel numberPadPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        for (String denomination : denominations.keySet()) {
            JButton button = new JButton(denomination);
            button.addActionListener(new NumberPadActionListener());
            numberPadPanel.add(button);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the frame on "Submit" button press
            }
        });

        numberPadPanel.add(submitButton); // Add the "Submit" button to the number pad panel
        add(numberPadPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private class NumberPadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();

            if (denominations.containsKey(buttonText)) {
                int amount = denominations.get(buttonText);
                totalAmount = amount;
                updateMoneyField();
            }
        }
    }

    private void updateMoneyField() {
        moneyField.setText(String.valueOf(totalAmount));
    }
}