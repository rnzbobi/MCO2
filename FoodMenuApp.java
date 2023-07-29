import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

public class FoodMenuApp extends JFrame{
    private VendingMachineFactory vendingMachineFactory;
    private RegularVendingMachine vendingMachine;
    private ArrayList<FoodItem> foodItems;
    private JPanel innerPanel;
    private JScrollPane scrollPane;
    private int columns = 3;
    private JTextField selectItemField;
    private JTextField moneyField;
    private int maxItems;
    private int currentSlots;
    private JFrame frame; // Declare frame as an instance variable
    private JButton addQuantityButton;
    private JButton submitButton;
    private JLabel nameLabel; // Add nameLabel variable here
    private JLabel balanceLabel;
    private static FoodMenuApp instance;
    private String machineName;
    private Map<String, Integer> ingredientPrices;
    private static Map<String, FoodMenuApp> instances = new HashMap<>();
    // New instance variable for Owner's Balance
    private int ownerBalance = 0;
    private JTextField balanceToTransferField; // Add balanceToTransferField as a class-level variable
    
    public FoodMenuApp(String machineName) {
        this.machineName = machineName;
        foodItems = new ArrayList<>();
        innerPanel = new JPanel(new GridLayout(0, columns, 10, 10));
        innerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ingredientPrices = new HashMap<>();
        scrollPane = new JScrollPane(innerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame = new JFrame(machineName); // Set the machine name as the title of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1700, 960));
        // Create and set the preferred size for the buttons
        Dimension buttonSize = new Dimension(150, 50);

        maxItems = -1;
        do {
            String maxItemsStr = JOptionPane.showInputDialog("Enter the number of items you can add (must be more than 10):");
            if (maxItemsStr == null) {
                JOptionPane.showMessageDialog(null, "Number of items cannot be empty. Exiting...", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

            try {
                maxItems = Integer.parseInt(maxItemsStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (maxItems <= 10) {
                JOptionPane.showMessageDialog(null, "Number of items must be more than 10.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } while (maxItems <= 10);

        vendingMachineFactory = new VendingMachineFactory();
        vendingMachine = vendingMachineFactory.createRegularVendingMachine(machineName, maxItems);
        System.out.println(vendingMachine.getName());
        System.out.println(vendingMachine.getNumberOfSlots());
        currentSlots = 0;
        addPredefinedIngredients();

        // Create the "BALANCE"
        balanceLabel = new JLabel("BALANCE: 0                                                                                                  OWNER'S BALANCE: 0");
        balanceLabel.setFont(new Font(balanceLabel.getFont().getName(), Font.PLAIN, 18));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Set Y_AXIS orientation
        nameLabel = new JLabel("<html><div style='text-align: center;'>" + "</div></html>");
        nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 36));
        topPanel.add(Box.createVerticalStrut(20)); // Add some padding
        topPanel.add(nameLabel);
        topPanel.add(Box.createVerticalStrut(10)); // Add some vertical spacing
        topPanel.add(balanceLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        selectItemField = new JTextField(10);
        buttonPanel.add(new JLabel("Select Item: "));
        buttonPanel.add(selectItemField);

        JButton addButton = new JButton("Add Item");
        addButton.setPreferredSize(buttonSize);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddFoodItemDialog();
                for(int i = 0; i < vendingMachine.getSlots().size(); i++){
            System.out.println(vendingMachine.getSlots().get(i).get(0).getName());
            System.out.println(vendingMachine.getSlots().get(i).get(0).getCalories());
            System.out.println(vendingMachine.getSlots().get(i).get(0).getPrice());
            System.out.println(vendingMachine.countIngredient(vendingMachine.getSlots().get(i).get(0).getName()));
            System.out.println("");
        }
            }
        });

        JButton insertMoneyButton = new JButton("INSERT");
        insertMoneyButton.setPreferredSize(buttonSize);
        insertMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the MoneyInsertionFrame
                new MoneyInsertionFrame().setVisible(true);
            }
        });

        JButton printSummaryButton = new JButton("Summary");
        printSummaryButton.setPreferredSize(buttonSize);
        printSummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,vendingMachine.createSummary(),"Show Summary", JOptionPane.INFORMATION_MESSAGE);
                vendingMachine.updateStartingInventory();
            }
        });

        JButton setItemButton = new JButton("Set Price");
        setItemButton.setPreferredSize(buttonSize);
        setItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the logic to set an item here
                showSetPriceDialog();
                for(int i = 0; i < vendingMachine.getSlots().size(); i++){
            System.out.println(vendingMachine.getSlots().get(i).get(0).getName());
            System.out.println(vendingMachine.getSlots().get(i).get(0).getCalories());
            System.out.println(vendingMachine.getSlots().get(i).get(0).getPrice());
            System.out.println(vendingMachine.countIngredient(vendingMachine.getSlots().get(i).get(0).getName()));
            System.out.println("");
        }
            }
        });

        JButton collectButton = new JButton("Collect Money");
        collectButton.setPreferredSize(buttonSize);
        collectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the logic to collect items here
            }
        });

        JButton replenishButton = new JButton("Replenish Money");
        replenishButton.setPreferredSize(buttonSize);
        replenishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the logic to replenish items here
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(buttonSize);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        addQuantityButton = new JButton("Add Quantity");
        addQuantityButton.setPreferredSize(buttonSize);
        addQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItemName = selectItemField.getText();
                FoodItem selectedItem = findPredefinedItem(selectedItemName);
                if (selectedItem != null) {
                    showAddQuantityDialog(selectedItem);
                } else {
                    showErrorMessage("Please select a valid predefined item.", "Error");
                }

                for(int i = 0; i < vendingMachine.getSlots().size(); i++){
            System.out.println(vendingMachine.getSlots().get(i).get(0).getName());
            System.out.println(vendingMachine.getSlots().get(i).get(0).getCalories());
            System.out.println(vendingMachine.getSlots().get(i).get(0).getPrice());
            System.out.println(vendingMachine.countIngredient(vendingMachine.getSlots().get(i).get(0).getName()));
            System.out.println("");
        }
            }

            
        });
        buttonPanel.add(addQuantityButton);

        submitButton = new JButton("Submit");
        submitButton.setPreferredSize(buttonSize);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItemName = selectItemField.getText();
                FoodItem selectedItem = findPredefinedItem(selectedItemName);
                StringBuilder stringBuilder = new StringBuilder();
                if (selectedItem != null) {
                    Change change = vendingMachine.dispenseChange(Integer.parseInt(selectedItem.getPrice()),vendingMachine.getCurrentUserBalance());
                    int count = 0;
                    if(change != null){
                        stringBuilder.append("+-----------------------+\n");
                        stringBuilder.append("|       Receipt   |\n");
                        stringBuilder.append("+-----------------------+\n");
                        stringBuilder.append("[1] " + selectedItem.getName() + "\n");
                        stringBuilder.append("+-----------------------+\n");
                        stringBuilder.append("Total: " + selectedItem.getPrice() + "\n");
                        stringBuilder.append("Amount Paid: " + vendingMachine.getCurrentUserBalance() + "\n");
                        stringBuilder.append("Change: " + (vendingMachine.getCurrentUserBalance() - Integer.parseInt(selectedItem.getPrice())) + "\n");
                        for(int i = 0; i < change.getDenominations().size(); i++){
                            count = 0;
                            stringBuilder.append(change.getDenominations().get(i).get(0).getValue());
                            for(int j = 0; j < change.getDenominations().get(i).size(); j++){
                                count++;
                            }
                            stringBuilder.append(count + "\n");
                        }
                        stringBuilder.append("+-----------------------+\n");
                        stringBuilder.append("Thank you for Purchasing!");
                        String receipt = stringBuilder.toString();
                        
                        vendingMachine.updateSales(selectedItemName);
                        vendingMachine.removeItem(selectedItemName);
                        vendingMachine.updateCurrentInventory();
                        JOptionPane.showMessageDialog(null,receipt,"RECEIPT", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        showErrorMessage("Insufficient Change", "Error");
                    }
                }else if(selectedItem.getQuantity() == 0){
                    showErrorMessage("Item is SOLD OUT!", "Error");
                } else {
                    showErrorMessage("Please select a valid predefined item.", "Error");
                }
            }
        });
        buttonPanel.add(submitButton);

        // Initialize the balanceToTransferField
        balanceToTransferField = new JTextField(10);
        balanceToTransferField.setFont(balanceToTransferField.getFont().deriveFont(16f));
        balanceToTransferField.setHorizontalAlignment(JTextField.RIGHT);
        balanceToTransferField.setEditable(false);
        balanceToTransferField.setText("Balance to Transfer: 0"); 

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        setMachineName(machineName);
        buttonPanel.add(insertMoneyButton);
        buttonPanel.add(addButton);
        buttonPanel.add(printSummaryButton);
        buttonPanel.add(setItemButton);
        buttonPanel.add(collectButton);
        buttonPanel.add(replenishButton);
        buttonPanel.add(exitButton);
        showFundsInsertionDialog();

        for(int i = 0; i < vendingMachine.getDenominations().size(); i++){
            for(int j = 0; j < vendingMachine.getDenominations().get(i).size(); j++){
                System.out.println(vendingMachine.getDenominations().get(i).get(j).getValue());
                System.out.println("");
            }
        }

        System.out.println(vendingMachine.getCurrentBalance());

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    public static FoodMenuApp getInstance(String machineName) {
        if (!instances.containsKey(machineName)) {
            instances.put(machineName, new FoodMenuApp(machineName));
        }
        return instances.get(machineName);
    }

    private void showFundsInsertionDialog() {
        // Create the dialog
        JDialog fundsDialog = new JDialog(frame, "Insert Funds");
        fundsDialog.setPreferredSize(new Dimension(300, 400));
        fundsDialog.setModal(true); // Set the dialog to modal
        fundsDialog.setLayout(new BorderLayout());
    
        // Create the moneyField
        moneyField = new JTextField(10);
        moneyField.setFont(moneyField.getFont().deriveFont(24f)); // Increase the font size
        moneyField.setHorizontalAlignment(JTextField.RIGHT);
        moneyField.setEditable(false); // Make the text field read-only
    
        // Create a text field to show the current balance to be transferred
        balanceToTransferField.setFont(balanceToTransferField.getFont().deriveFont(16f));
        balanceToTransferField.setHorizontalAlignment(JTextField.RIGHT);
        balanceToTransferField.setEditable(false);
        balanceToTransferField.setText("Balance to Transfer: 0"); // Initialize to 0
    
        // Create the number pad for the funds insertion
        JPanel numberPadPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        int[] denominations = {1000, 500, 200, 100, 50, 20, 10, 5, 1};
        for (int denomination : denominations) {
            JButton button = new JButton(String.valueOf(denomination));
            button.setFont(button.getFont().deriveFont(20f)); // Increase the font size of the buttons
            button.addActionListener(new NumberPadActionListener());
            numberPadPanel.add(button);
        }
    
        // Create the clear button
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moneyField.setText("");
                balanceToTransferField.setText("Balance to Transfer: 0"); // Reset the field
            }
        });

        // Create the submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = moneyField.getText();
                if (isValidPositiveInteger(amountStr)) {
                    int amount = Integer.parseInt(amountStr);
                    vendingMachine.addDenomination(Integer.parseInt(amountStr));
                    vendingMachine.updateCurrentBalance();
                    ownerBalance += amount; // Transfer funds to owner's balance
                    moneyField.setText("");
                    balanceToTransferField.setText("Balance to Transfer: 0"); // Reset the field
                    updateBalanceLabel();
                } else {
                    showErrorMessage("Invalid amount. Please enter a valid integer.", "Error");
                }
            }
        });

    
        // Create the finish button
        JButton finishButton = new JButton("Finish");
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fundsDialog.dispose();
            }
        });
    
        // Create a panel to hold the clear, submit, and finish buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(clearButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(finishButton);
    
        // Create a panel to hold the moneyField and balanceToTransferField
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(moneyField, BorderLayout.NORTH);
        topPanel.add(balanceToTransferField, BorderLayout.SOUTH);
    
        // Add the components to the dialog
        fundsDialog.add(topPanel, BorderLayout.NORTH);
        fundsDialog.add(numberPadPanel, BorderLayout.CENTER);
        fundsDialog.add(buttonPanel, BorderLayout.SOUTH);
    
        fundsDialog.pack();
        fundsDialog.setLocationRelativeTo(frame); // Center the dialog relative to the frame
        fundsDialog.setVisible(true);
    }

    // Helper method to update the balance label with the current owner balance
    private void updateBalanceLabel() {
        balanceLabel.setText("BALANCE: 0                                                                                                  OWNER'S BALANCE: " + ownerBalance);
    }

    // Method to set the machine name in the nameLabel
    // Method to set the machine name in the nameLabel
    public void setMachineName(String name) {
        nameLabel.setText("<html><div style='text-align: center;'>" + name + "</div></html>");
    }

    // Make the formatMachineName method static
    private static String formatMachineName(String name) {
        // Capitalize the first letter and add an apostrophe and "s" at the end
        return Character.toUpperCase(name.charAt(0)) + name.substring(1) + "'s Vending Machine!";
    }

    // Method to ask for the name of the vending machine
    public String askForMachineName() {
        String machineName = JOptionPane.showInputDialog("Enter the name of the vending machine:");
        if (machineName != null && !machineName.trim().isEmpty()) {
            machineName = formatMachineName(machineName);
        } else {
            JOptionPane.showMessageDialog(null, "Machine name cannot be empty. Exiting...", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return machineName;
    }

    private FoodItem findPredefinedItem(String name) {
        for (FoodItem item : foodItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    private class NumberPadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();
            String currentMoney = moneyField.getText();
            moneyField.setText(currentMoney + buttonText);

                // Update the balance to transfer field
                if (isValidPositiveInteger(moneyField.getText())) {
                    int amount = Integer.parseInt(moneyField.getText());
                    balanceToTransferField.setText("Balance to Transfer: " + amount);
                } else {
                    balanceToTransferField.setText("Balance to Transfer: 0");
                }
            }
    }

    private void addPredefinedIngredients() {
        HashMap<String, FoodItem> predefinedIngredients = new HashMap<>();
        predefinedIngredients.put("Espresso Roast", new FoodItem("1.png", "5", "Espresso Roast", "999"));
        predefinedIngredients.put("Milk", new FoodItem("6.png", "146", "Milk", "99"));
        predefinedIngredients.put("Vanilla Syrup", new FoodItem("9.png", "77", "Vanilla Syrup", "49"));
        predefinedIngredients.put("Caramel Syrup", new FoodItem("7.png", "30", "Caramel Syrup", "49"));
        predefinedIngredients.put("Chocolate Chips", new FoodItem("5.png", "136", "Chocolate Chips", "29"));
        predefinedIngredients.put("Whipped Cream", new FoodItem("3.png", "72", "Whipped Cream", "199"));
        predefinedIngredients.put("Milk Whisk", new FoodItem("8.png", "149", "Milk Whisk", "59"));
        predefinedIngredients.put("Hot Water", new FoodItem("4.png", "12", "Hot Water", "1"));
        predefinedIngredients.put("Cinnamon", new FoodItem("2.png", "19", "Cinnamon", "189"));
        predefinedIngredients.put("Espresso", new FoodItem("10.png", "3", "Espresso", "349"));

        for (FoodItem item : predefinedIngredients.values()) {
            boolean dialogCompleted = showAddFoodItemDialog(item);
            if (!dialogCompleted) {
                // User canceled or closed the dialog, so we should exit the application
                System.exit(0);
            }
        }

        for(int i = 0; i < vendingMachine.getSlots().size(); i++){
            System.out.println(vendingMachine.getSlots().get(i).get(0).getName());
            System.out.println(vendingMachine.getSlots().get(i).get(0).getCalories());
            System.out.println(vendingMachine.getSlots().get(i).get(0).getPrice());
            System.out.println(vendingMachine.countIngredient(vendingMachine.getSlots().get(i).get(0).getName()));
            System.out.println("");
        }
    }

    private void showSetPriceDialog() {
        String ingredientName = selectItemField.getText();
        if (ingredientName.isEmpty()) {
            showErrorMessage("Please enter the ingredient name.", "Error");
            return;
        }
    
        String newPriceStr = JOptionPane.showInputDialog(frame, "Enter the new price for " + ingredientName + ":");
        if (newPriceStr == null || newPriceStr.trim().isEmpty()) {
            showErrorMessage("Invalid price. Please enter a valid integer.", "Error");
            return;
        }
    
        // Find the FoodItem corresponding to the selected ingredient name
        FoodItem selectedItem = findPredefinedItem(ingredientName);
        if (selectedItem != null) {
            // Update the price of the ingredient
            vendingMachine.setPrice(ingredientName, Integer.parseInt(newPriceStr));
            selectedItem.setPrice(newPriceStr);
    
            // Update the display of the ingredient's information
            updateQuantityDisplay(selectedItem);
        } else {
            showErrorMessage("Please select a valid predefined item.", "Error");
        }
    
        selectItemField.setText("");
    }

    private void showAddQuantityDialog(FoodItem item) {
        String quantityStr = showIntegerInputDialog("Enter quantity to add:");
        if (quantityStr != null && isValidPositiveInteger(quantityStr)) {
            int quantityToAdd = Integer.parseInt(quantityStr);
            item.setQuantity(item.getQuantity() + quantityToAdd);
            vendingMachine.addQuantity(item.getName(), quantityToAdd);
            updateQuantityDisplay(item);
        }
        // Clear the text field
        selectItemField.setText("");
    }

    private void updateQuantityDisplay(FoodItem item) {
        for (Component component : innerPanel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                String labelText = label.getText();
                if (labelText.contains(item.getName())) {
                    String newLabelText = "<html>Calorie: " + item.getCalorie() + "<br>Name: " + item.getName() +
                                          "<br>Price: " + item.getPrice() + "<br>Quantity: " + item.getQuantity() + "</html>";
                    label.setText(newLabelText);
                    break;
                }
            }
        }
    }

    private boolean showAddFoodItemDialog(FoodItem predefinedItem) {
        String quantityStr = showIntegerInputDialog("Enter quantity for " + predefinedItem.getName() + ":");
        if (quantityStr == null) {
            return false; // User canceled or closed the dialog, do nothing
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                showErrorMessage("Invalid quantity. Please enter a positive integer.", "Error");
                return false; // Quantity must be a positive integer
            }

            predefinedItem.setQuantity(quantity);

            if (predefinedItem.getQuantity() >= 10) {
                vendingMachine.addIngredient(predefinedItem.getName(), Integer.parseInt(predefinedItem.getCalorie()), Integer.parseInt(predefinedItem.getPrice()), quantity);
                vendingMachine.updateStartingInventory();
                vendingMachine.updateCurrentInventory();
                addFoodItem(predefinedItem.getImagePath(), predefinedItem.getCalorie(), predefinedItem.getName(), predefinedItem.getPrice(), quantity);
                innerPanel.revalidate();
            } else {
                // Notify the user that the quantity is less than 10 and not added to the list
                showErrorMessage("Quantity must be at least 10.", "Warning");
            }
        } catch (NumberFormatException ex) {
            showErrorMessage("Invalid quantity. Please enter a valid integer.", "Error");
        }
        return true;
    }

    private void showAddFoodItemDialog() {
        String imagePath = askForImagePath();
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return; // User chose to cancel adding an image or closed the dialog
        }
    
        String calorieStr = askForCalorieCount();
        if (calorieStr == null || !isValidPositiveInteger(calorieStr)) {
            return; // User chose to cancel adding calorie count or closed the dialog
        }
    
        String name = askForItemName();
        if (name == null || name.trim().isEmpty()) {
            return; // User chose to cancel adding an item name or closed the dialog
        }
    
        String priceStr = askForPrice();
        if (priceStr == null || !isValidPositiveInteger(priceStr)) {
            return; // User chose to cancel adding a price or closed the dialog
        }
    
        String quantityStr = askForQuantity();
        if (quantityStr == null || !isValidPositiveInteger(quantityStr)) {
            return; // User chose to cancel adding a quantity or closed the dialog
        }
    
        // If the user reaches this point, they have completed the addition of a food item.
        // You can now process the data and add the food item to the list.
    
        int calorie = Integer.parseInt(calorieStr);
        int price = Integer.parseInt(priceStr);
        int quantity = Integer.parseInt(quantityStr);

        vendingMachine.addIngredient(name, calorie, price, quantity);
        vendingMachine.updateCurrentInventory();
        addFoodItem(imagePath, String.valueOf(calorie), name, String.valueOf(price), quantity);
        innerPanel.revalidate();
    }
    
    private String askForImagePath() {
        return showStringInputDialog("Enter image path (e.g., example.jpg):");
    }
    
    private String askForCalorieCount() {
        String calorieStr;
        do {
            calorieStr = showIntegerInputDialog("Enter calorie count:");
        } while (calorieStr != null && !isValidPositiveInteger(calorieStr));
        return calorieStr;
    }
    
    private String askForItemName() {
        return showStringInputDialog("Enter item name:");
    }
    
    private String askForPrice() {
        String priceStr;
        do {
            priceStr = showIntegerInputDialog("Enter price:");
        } while (priceStr != null && !isValidPositiveInteger(priceStr));
        return priceStr;
    }
    
    private String askForQuantity() {
        String quantityStr;
        do {
            quantityStr = showIntegerInputDialog("Enter quantity:");
        } while (quantityStr != null && !isValidPositiveInteger(quantityStr));
        return quantityStr;
    }

    private String showStringInputDialog(String message) {
        JOptionPane pane = new JOptionPane();
        pane.setWantsInput(true);
        pane.setOptionType(JOptionPane.OK_OPTION);
        return (String) pane.showInputDialog(null, message, "Add Food Item", JOptionPane.PLAIN_MESSAGE, null, null, "");
    }

    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void showWarningMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    private String showIntegerInputDialog(String message) {
        String input = JOptionPane.showInputDialog(frame, message);
        if (input == null) {
            return null; // Return null to indicate cancel or closed dialog
        }
    
        input = input.trim();
        if (!isValidPositiveInteger(input)) {
            showErrorMessage("Invalid input. Please enter a positive integer.", "Error");
            return null; // Return null to indicate invalid input
        }
    
        return input;
    }
    
    private boolean isValidPositiveInteger(String str) {
        try {
            int num = Integer.parseInt(str);
            return num > 0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private void addFoodItem(String imagePath, String calorie, String name, String price, int quantity) {
        if (currentSlots >= maxItems) {
            // Maximum number of slots reached
            JOptionPane.showMessageDialog(null, "Maximum number of items reached (" + maxItems + ").", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Increment the number of slots used
        currentSlots++;

        FoodItem foodItem = new FoodItem(imagePath, calorie, name, price);
        foodItem.setQuantity(quantity);
        foodItems.add(foodItem);

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\rhenz\\Documents\\GitHub\\mp\\resources\\" + imagePath);
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel label = new JLabel();
        label.setIcon(resizedIcon);
        label.setText("<html>Calorie: " + calorie + "<br>Name: " + name + "<br>Price: " + price + "<br>Quantity: " + foodItem.getQuantity() + "</html>");
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        innerPanel.add(label);
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