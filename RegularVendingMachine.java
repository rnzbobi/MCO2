import java.util.*;

/**
 * The RegularVendingMachine class represents a vending machine that sells items using denominations and keeps track of sales and inventory.
 * It allows adding and removing ingredients, updating prices, handling user denominations, and dispensing change to customers.
 */
public class RegularVendingMachine{
    protected String name;
    protected int numberOfSlots;
    protected ArrayList<ArrayList<Ingredient>> slots;
    protected ArrayList<ArrayList<Denomination>> denominations;
    protected ArrayList<Denomination> userDenominations;
    protected int currentUserBalance;
    protected int currentBalance;
    protected HashMap<Ingredient,Integer> startingInventory;
    protected HashMap<Ingredient,Integer> currentInventory;
    protected Sales salesRecord;
    /**
     * Creates a new RegularVendingMachine instance with the given name and number of slots.
     *
     * @param name          The name of the vending machine.
     * @param numberOfSlots The number of slots in the vending machine.
     */
    public RegularVendingMachine(String name, int numberOfSlots) {
        this.name = name;
        this.numberOfSlots = numberOfSlots;
        this.slots = new ArrayList<ArrayList<Ingredient>>();
        this.denominations = new ArrayList<ArrayList<Denomination>>();
        this.userDenominations = new ArrayList<Denomination>();
        this.currentUserBalance = 0;
        this.currentBalance = 0;
        this.startingInventory = new HashMap<Ingredient, Integer>();
        this.currentInventory = new HashMap<Ingredient, Integer>();
        this.salesRecord = new Sales();
    }
   /**
     * Sorts the denominations in descending order based on their values.
     */
    public void sortDenominationsDescending() {
        // Sort the outer list based on the value of the first element of each inner list
        Collections.sort(denominations, new Comparator<ArrayList<Denomination>>() {
            @Override
            public int compare(ArrayList<Denomination> list1, ArrayList<Denomination> list2) {
                // Compare the first element of each inner list in reverse order (largest to smallest)
                return Integer.compare(list2.get(0).getValue(), list1.get(0).getValue());
            }
        });
    }
        /**
     * Dispenses change to the customer after a purchase.
     *
     * @param amountToPay    The amount to be paid for the item.
     * @param amountInserted The amount inserted by the customer.
     * @return A Change object containing the denominations for the change, or null if change cannot be given.
     */
    public Change dispenseChange(int amountToPay, int amountInserted) {
        int changeAmount = amountInserted - amountToPay;
        ArrayList<ArrayList<Denomination>> changeDenominations = new ArrayList<>();
        for(Denomination denomination : userDenominations){
            addDenomination(denomination.getValue());
        }
        sortDenominationsDescending();
    
        for (int i = 0; i < denominations.size() && changeAmount > 0; i++) {
            ArrayList<Denomination> denominationList = denominations.get(i);
            int denominationValue = denominationList.get(0).getValue();
    
            int numDenominationsRequired = changeAmount / denominationValue;
            int numDenominationsAvailable = denominationList.size();
    
            int numDenominationsToAdd = Math.min(numDenominationsRequired, numDenominationsAvailable);
            if (numDenominationsToAdd > 0) {
                ArrayList<Denomination> usedDenominations = new ArrayList<>();
                for (int j = 0; j < numDenominationsToAdd; j++) {
                    usedDenominations.add(new Denomination(denominationValue));
                }
                changeDenominations.add(usedDenominations);
                changeAmount -= numDenominationsToAdd * denominationValue;
            }
        }
    
        if (changeAmount != 0) {
            return null;
        }
    
        currentBalance -= (amountInserted - changeAmount);
        
        // Update the denominations ArrayList by removing the used denominations
        for (int i = 0; i < denominations.size(); i++) {
            int denominationValue = denominations.get(i).get(0).getValue();
            for (int j = 0; j < changeDenominations.size(); j++) {
                if (changeDenominations.get(j).get(0).getValue() == denominationValue) {
                    int numUsedDenominations = changeDenominations.get(j).size();
                    if (numUsedDenominations == denominations.get(i).size()) {
                        // All denominations of this value have been used up, remove the entire list
                        denominations.remove(i);
                        i--; // Adjust the index after removal
                        break;
                    } else if (numUsedDenominations < denominations.get(i).size()) {
                        // Remove only the used denominations
                        denominations.get(i).subList(0, numUsedDenominations).clear();
                        break;
                    }
                }
            }
        }

        userDenominations.clear();
        return new Change(changeDenominations);
    }
    /**
     * Adds an ingredient to the vending machine slots or increases its quantity if it already exists.
     *
     * @param name     The name of the ingredient to add.
     * @param calories The calories of the ingredient to add.
     * @param price    The price of the ingredient to add.
     * @param quantity The quantity of the ingredient to add.
     * @return true if the ingredient was successfully added or updated, false otherwise.
     */
    public boolean addIngredient (String name, int calories, int price, int quantity){
        
        if(slots.size() >= numberOfSlots){
            return false;
        }
        for(int i = 0; i < slots.size(); i++){
            if(slots.get(i).get(0).getName().equalsIgnoreCase(name)){
                for(int j = 0; j < quantity; j++){
                    Ingredient ingredient = new Ingredient(name,calories,price);
                    slots.get(i).add(ingredient);
                }
                updateCurrentInventory();
                return true;
            }
        }

        ArrayList<Ingredient> newSlot = new ArrayList<>();
        for(int k = 0; k < quantity; k++){
            Ingredient ingredient = new Ingredient(name,calories,price);
            newSlot.add(ingredient);
        }
        slots.add(newSlot);
        updateCurrentInventory();
        return true;
    }
    /**
     * Increases the quantity of an existing ingredient in the vending machine slots.
     *
     * @param name     The name of the ingredient to add.
     * @param quantity The quantity of the ingredient to add.
     * @return true if the ingredient quantity was successfully increased, false otherwise.
     */
    public boolean addQuantity(String name, int quantity) {
        if(quantity > 0){
            for (int i = 0; i < slots.size(); i++) {
                if (slots.get(i).get(0).getName().equalsIgnoreCase(name)) {
                    ArrayList<Ingredient> currentSlot = slots.get(i);
                    for (int j = 0; j < quantity; j++) {
                        Ingredient newIngredient = new Ingredient(
                            currentSlot.get(0).getName(),
                            currentSlot.get(0).getCalories(),
                            currentSlot.get(0).getPrice()
                        );
                        currentSlot.add(newIngredient);
                    }
                    updateCurrentInventory();
                    return true;
                }
            }
        }
    
        return false;
    }
    /**
     * Selects an item (ingredient) from the vending machine slots based on its name.
     *
     * @param itemName The name of the item to select.
     * @return The selected Ingredient instance, or null if the item is not found.
     */
    public Ingredient selectItem(String itemName){
        for (ArrayList<Ingredient> slot : slots) {
            for (Ingredient ingredient : slot) {
                if (ingredient.getName().equalsIgnoreCase(name)) {
                    return ingredient;
                }
            }
        }
        return null;
    }
    /**
     * Removes an item (ingredient) from the vending machine slots based on its name.
     *
     * @param itemName The name of the item to remove.
     * @return true if the item was successfully removed, false otherwise.
     */
    public boolean removeItem(String itemName){
        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                if (slots.get(i).get(j).getName().equalsIgnoreCase(itemName)) {
                    slots.get(i).remove(j);
                    updateCurrentInventory();
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Adds a new slot (ingredient) to the vending machine slots.
     *
     * @param slot The ArrayList of ingredients to add as a new slot.
     * @return true if the slot was successfully added, false if the maximum number of slots is reached.
     */
    public boolean addSlot(ArrayList<Ingredient> slot){
        if(slots.size() >= numberOfSlots){
            return false;
        } else{
            slots.add(slot);
            updateCurrentInventory();
            return true;
        }
    }
    /**
     * Removes an ingredient slot (a group of the same ingredients) from the vending machine slots.
     *
     * @param itemName The name of the ingredient to remove from the slots.
     * @return true if the ingredient slot was successfully removed, false otherwise.
     */
    public boolean removeIngredient(String itemname){
        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                if (slots.get(i).get(j).getName().equals(itemname)) {
                    slots.remove(i);
                    updateCurrentInventory();
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Adds a new denomination to the vending machine's list of available denominations.
     *
     * @param value The value of the denomination to add.
     * @return true if the denomination was successfully added, false if the value is not valid.
     */
    public boolean addDenomination(int value) {
        Denomination newDenomination = new Denomination(value);
        
        if (value != 1 && value != 5 && value != 10 && value != 20 && value != 50 && 
            value != 100 && value != 200 && value != 500 && value != 1000){
                return false;
        }
        else{
            for(int i = 0; i < denominations.size(); i++){
                for(int j = 0; j < denominations.get(i).size(); j++){
                    if(denominations.get(i).get(j).getValue() == value){
                        denominations.get(i).add(newDenomination);
                        return true;
                    }
                }
            }
            ArrayList<Denomination> newDenominationList = new ArrayList<>();
            newDenominationList.add(newDenomination);
            denominations.add(newDenominationList);
            return true;
        }
    }
    /**
     * Adds a new denomination to the user's list of inserted denominations.
     *
     * @param value The value of the denomination to add.
     * @return true if the denomination was successfully added, false if the value is not valid.
     */
    public boolean addUserDenomination(int value) {
        Denomination newDenomination = new Denomination(value);
        
        if (value != 1 && value != 5 && value != 10 && value != 20 && value != 50 && 
            value != 100 && value != 200 && value != 500 && value != 1000){
                return false;
        }
        else{
            userDenominations.add(newDenomination);
            return true;
        }
    }
        /**
     * Collects a specific quantity of a denomination from the vending machine's list of available denominations.
     *
     * @param value    The value of the denomination to collect.
     * @param quantity The quantity of the denomination to collect.
     * @return true if the specified quantity of the denomination was successfully collected, false otherwise.
     */
    public boolean collectDenomination(int value, int quantity) {
        if(quantity > 0){
            for (int i = 0; i < denominations.size(); i++) {
                if(denominations.get(i).get(0).getValue() == value){
                    if(denominations.get(i).size() == quantity){
                        denominations.remove(i);
                        return true;
                    }
                    else if(denominations.get(i).size() > quantity){
                        for(int j = 0; j < quantity; j++){
                            denominations.get(i).remove(j);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
        /**
     * Generates a summary of sales and inventory since the last maintenance.
     *
     * @return A string containing the summary of sales and inventory.
     */
    public String createSummary() {
        StringBuilder stringBuilder = new StringBuilder();
    
        stringBuilder.append("SUMMARY OF SALES SINCE LAST MAINTENANCE\n\n");
        stringBuilder.append("Total Sales: Php " + salesRecord.getTotalSales() + "\n");
        stringBuilder.append("Total Items Sold: " + salesRecord.getTotalIngredientsSold() + "\n\n");
    
        stringBuilder.append("+-----------------------------+\n");
        stringBuilder.append("|   Starting Inventory   |\n");
        stringBuilder.append("+-----------------------------+\n");
    
        for (Map.Entry<Ingredient, Integer> entry : startingInventory.entrySet()) {
            Ingredient ingredient = entry.getKey();
            int quantity = entry.getValue();
            String name = ingredient.getName();
    
            stringBuilder.append("[" + quantity + "] " + name + "\n");
        }
    
        stringBuilder.append("+-----------------------------+\n\n");
    
        stringBuilder.append("+-----------------------------+\n");
        stringBuilder.append("|   Current Inventory   |\n");
        stringBuilder.append("+-----------------------------+\n");
    
        for (Map.Entry<Ingredient, Integer> entry : currentInventory.entrySet()) {
            Ingredient ingredient = entry.getKey();
            int quantity = entry.getValue();
            String name = ingredient.getName();
    
            stringBuilder.append("[" + quantity + "] " + name + "\n");
        }
    
        stringBuilder.append("+-----------------------------+\n\n");
        stringBuilder.append("Summary Done!\n");
    
        return stringBuilder.toString();
    }
   /**
     * Counts the occurrences of a specific ingredient in the vending machine slots.
     *
     * @param ingredientName The name of the ingredient to count occurrences for.
     * @return The number of occurrences of the specified ingredient in the vending machine slots.
     */
    public int countIngredient(String ingredientName){
        int count = 0;
        for (ArrayList<Ingredient> slot : slots) {
            for (Ingredient ingredient : slot) {
                if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Updates the current balance of the vending machine based on the available denominations.
     */
    public void updateCurrentBalance() {
        currentBalance = 0;
        for(int i = 0; i < denominations.size(); i++){
            for(int j = 0; j < denominations.get(i).size(); j++){
                if(denominations.get(i).get(j) != null){
                    currentBalance += denominations.get(i).get(j).getValue();
                }
            }
        }
    }

    /**
     * Updates the current user balance based on the user-inserted denominations.
     */
    public void updateCurrentUserBalance() {
        currentUserBalance = 0;
        for(int i = 0; i < userDenominations.size(); i++){
            if(userDenominations.get(i) != null){
                currentUserBalance += userDenominations.get(i).getValue();
            }
        }
    }
    /**
     * Updates the starting inventory based on the current inventory.
     */
    public void updateStartingInventory() {
        startingInventory.clear();
    
        for (Map.Entry<Ingredient, Integer> entry : currentInventory.entrySet()) {
            Ingredient ingredient = entry.getKey();
            int quantity = entry.getValue();
            startingInventory.put(ingredient, quantity);
        }
    }
    /**
     * Updates the current inventory based on the vending machine slots.
     */
    public void updateCurrentInventory(){
        currentInventory.clear();
        for(int i = 0; i < slots.size(); i++){
            currentInventory.put(slots.get(i).get(0),countIngredient(slots.get(i).get(0).getName()));
        }
    }
    /**
     * Updates the sales record for a specific item (ingredient) sold.
     *
     * @param itemName The name of the item (ingredient) sold.
     */
    public void updateSales(String itemName){
        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                if (slots.get(i).get(j).getName().equalsIgnoreCase(itemName)) {
                        salesRecord.totalSales += slots.get(i).get(j).getPrice();
                        salesRecord.totalIngredientsSold += 1;
                        break;
                }
            }
        }
    }
    /**
     * Sets the price of an existing item (ingredient) in the vending machine slots.
     *
     * @param itemName The name of the item to set the price for.
     * @param price    The new price of the item.
     * @return true if the price was successfully set, false if the item is not found.
     */
    public boolean setPrice(String itemName, int price){
        boolean found = false;
        for (ArrayList<Ingredient> slot : slots) {
            for (Ingredient ingredient : slot) {
                if (ingredient.getName().equalsIgnoreCase(itemName) && price > 0) {
                    ingredient.setPrice(price);
                    found = true;
                }
            }
        }

        return found;
    }

    public String getName() {
        return name;
    }


    public int getNumberOfSlots() {
        return numberOfSlots;
    }

    public ArrayList<ArrayList<Ingredient>> getSlots() {
        return slots;
    }


    public ArrayList<ArrayList<Denomination>> getDenominations() {
        return denominations;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public HashMap<Ingredient, Integer> getStartingInventory() {
        return startingInventory;
    }


    public HashMap<Ingredient, Integer> getCurrentInventory() {
        return currentInventory;
    }

    public ArrayList<Denomination> getUserDenominations() {
        return userDenominations;
    }

    public int getCurrentUserBalance() {
        return currentUserBalance;
    }

    public Sales getSalesRecord() {
        return salesRecord;
    }
    /**
     * Clears the list of user-inserted denominations.
     */
    public void clearUserDenominations(){
        userDenominations.clear();
    }
}