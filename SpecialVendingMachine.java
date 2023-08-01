import java.util.*;
/**
 * The SpecialVendingMachine class represents a special vending machine that can create and sell
 * custom coffee drinks using specific ingredients from the regular vending machine's slots.
 * It extends the RegularVendingMachine class and adds functionality for managing coffee ingredients
 * and creating custom coffee drinks.
 */
public class SpecialVendingMachine extends RegularVendingMachine{
    protected ArrayList<ArrayList<Ingredient>> coffeeIngredients;
    /**
     * Creates a new SpecialVendingMachine instance with the given name and number of slots.
     * Initializes the coffeeIngredients list to an empty ArrayList.
     *
     * @param name          The name of the special vending machine.
     * @param numberOfSlots The number of slots in the vending machine.
     */
    public SpecialVendingMachine(String name, int numberOfSlots) {
        super(name, numberOfSlots);
        this.coffeeIngredients = new ArrayList<>();
    }
    /**
     * Updates the sales record for a given Coffee instance by adding the coffee's price to
     * the total sales and incrementing the total number of ingredients and coffees sold.
     *
     * @param coffee The Coffee instance to update the sales record for.
     */
    public void updateSales(Coffee coffee){
        int price = coffee.getPrice();
        for(int i = 0; i < coffeeIngredients.size(); i++){
            for(int j = 0; j < coffeeIngredients.get(i).size(); j++){
                salesRecord.totalIngredientsSold += 1;
            }
        }
        salesRecord.totalSales += price;
        salesRecord.updateCoffeeSold();
    }
    /**
     * Overrides the createSummary method from the parent class to include the total number
     * of coffees sold in the summary.
     *
     * @return A string containing the summary of sales and inventory since the last maintenance.
     */
    @Override
    public String createSummary() {
        StringBuilder stringBuilder = new StringBuilder();
    
        stringBuilder.append("SUMMARY OF SALES SINCE LAST MAINTENANCE\n\n");
        stringBuilder.append("Total Sales: Php " + salesRecord.getTotalSales() + "\n");
        stringBuilder.append("Total Items Sold: " + salesRecord.getTotalIngredientsSold() + "\n");
        stringBuilder.append("Total Coffee Sold: " + salesRecord.getTotalCoffeeSold() + "\n\n");
    
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
     * Adds a specified quantity of a regular vending machine ingredient to the coffee ingredients list.
     * The method checks if there is enough of the ingredient available in the regular vending machine
     * before adding it to the coffee ingredients list.
     *
     * @param name     The name of the ingredient to add.
     * @param quantity The quantity of the ingredient to add.
     * @return true if the ingredient was successfully added, false otherwise.
     */
    public boolean addCoffeeIngredient(String name, int quantity){
        boolean added = false;
        for(int i = 0; i < slots.size(); i++){
            if(slots.get(i).get(0).getName().equalsIgnoreCase(name)){
                if(countIngredient(slots.get(i).get(0).getName()) >= quantity){
                    for(int j = 0; j < coffeeIngredients.size(); j++){
                        if(coffeeIngredients.get(j).get(0).getName().equalsIgnoreCase(name)){
                            for(int k = 0; k < quantity; k++){
                                coffeeIngredients.get(j).add(slots.get(i).get(0));
                            }
                            return added = true;
                        }
                    }
                    if(!added){
                        ArrayList<Ingredient> newSlot = new ArrayList<>();
                        for(int l = 0; l < quantity; l++){
                            newSlot.add(slots.get(i).get(0));
                        }
                        coffeeIngredients.add(newSlot);
                        return added = true;
                    }
                }
            }
        }
        return added;
    }
    /**
     * Updates the vending machine slots and current inventory by removing the ingredients used to create
     * a coffee from the coffee ingredients list.
     *
     * @param ingredients The ingredients used to create a coffee.
     * @return true if the vending machine slots and inventory were updated, false otherwise.
     */
    public boolean updateSlots(ArrayList<ArrayList<Ingredient>> ingredients){
        boolean removed = false;
        for(ArrayList<Ingredient> ingredientList : ingredients){
            for(Ingredient ingredient : ingredientList){
                removeItem(ingredient.getName());
                removed = true;
            }
        }

        updateCurrentInventory();
        return removed;
    }
    /**
     * Creates a custom Coffee instance based on the coffee ingredients and calculates its total calories and price.
     *
     * @return The created Coffee instance with the total calories, price, and coffee ingredients.
     */
    public Coffee createCoffee(){
        int calories = 0, price = 0;

        for(int i = 0; i < coffeeIngredients.size(); i++){
            for(int j = 0; j < coffeeIngredients.get(i).size(); j++){
                if(coffeeIngredients.get(i).get(0).getPrice() > 70){
                    price += 50;
                } else {
                    price += coffeeIngredients.get(i).get(0).getPrice();
                }
                calories += coffeeIngredients.get(i).get(j).getCalories();
            }
        }

        return new Coffee(calories, price, coffeeIngredients);
    }
    /**
     * Counts the occurrences of a specific ingredient in the coffee ingredients list.
     *
     * @param ingredientName The name of the ingredient to count occurrences for.
     * @return The number of occurrences of the specified ingredient in the coffee ingredients list.
     */
    public int countCoffeeIngredient(String ingredientName){
        int count = 0;
        for (ArrayList<Ingredient> coffeeIngredientsList : coffeeIngredients) {
            for (Ingredient coffeeIngredients : coffeeIngredientsList) {
                if (coffeeIngredients.getName().equalsIgnoreCase(ingredientName)) {
                    count++;
                }
            }
        }

        return count;
    }
    /**
     * Removes all coffee ingredients from the coffee ingredients list.
     */
    public void removeCoffeeIngredients(){
        coffeeIngredients.clear();
    }
    /**
     * Gets the list of coffee ingredients used to create custom coffee drinks.
     *
     * @return The list of coffee ingredients.
     */
    public ArrayList<ArrayList<Ingredient>> getCoffeeIngredients(){
        return coffeeIngredients;
    }
}