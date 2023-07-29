import java.util.*;


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
        this.salesRecord = new Sales(0,0);
    }

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
    
    public Change dispenseChange(int amountToPay, int amountInserted) {
        int changeAmount = amountInserted - amountToPay;
        ArrayList<ArrayList<Denomination>> changeDenominations = new ArrayList<>();
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

        return new Change(changeDenominations);
    }

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

    public boolean addQuantity(String name, int quantity) {
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
    
        return false;
    }

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

    public boolean addSlot(ArrayList<Ingredient> slot){
        if(slots.size() >= numberOfSlots){
            return false;
        } else{
            slots.add(slot);
            updateCurrentInventory();
            return true;
        }
    }

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
    
    public boolean collectDenomination(int value, int quantity) {
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
        return false;
    }
    
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

    public void updateCurrentUserBalance() {
        currentUserBalance = 0;
        for(int i = 0; i < userDenominations.size(); i++){
            if(userDenominations.get(i) != null){
                currentUserBalance += userDenominations.get(i).getValue();
            }
        }
    }

    public void updateStartingInventory() {
        startingInventory.clear();
    
        for (Map.Entry<Ingredient, Integer> entry : currentInventory.entrySet()) {
            Ingredient ingredient = entry.getKey();
            int quantity = entry.getValue();
            startingInventory.put(ingredient, quantity);
        }
    }

    public void updateCurrentInventory(){
        currentInventory.clear();
        for(int i = 0; i < slots.size(); i++){
            currentInventory.put(slots.get(i).get(0),countIngredient(slots.get(i).get(0).getName()));
        }
    }

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

    public boolean setPrice(String itemName, int price){
        boolean found = false;
        for (ArrayList<Ingredient> slot : slots) {
            for (Ingredient ingredient : slot) {
                if (ingredient.getName().equalsIgnoreCase(itemName)) {
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
}