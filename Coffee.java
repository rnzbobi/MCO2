import java.util.*;

/**
 * The Coffee class represents a coffee with its name, calories, price, and ingredients.
 * It provides methods to get the coffee name, calories, price, and ingredients,
 * as well as a method to set the coffee name based on its ingredients.
 */
public class Coffee {
    protected String name;
    protected final int calories;
    protected final int price;
    protected final ArrayList<ArrayList<Ingredient>> ingredients;

    /**
     * Creates a new Coffee instance with the specified calories, price, and ingredients.
     *
     * @param calories The number of calories in the coffee.
     * @param price The price of the coffee.
     * @param ingredients The list of ingredients used to make the coffee.
     */
    public Coffee(int calories, int price, ArrayList<ArrayList<Ingredient>> ingredients) {
        this.name = "Coffee";
        this.calories = calories;
        this.price = price;
        this.ingredients = ingredients;
    }

    /**
     * Gets the name of the coffee.
     *
     * @return The name of the coffee.
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name of the coffee based on its ingredients.
     * It checks the counts of specific ingredients to determine the coffee name.
     */
    public void setCoffeeName(){
        // Counters for specific ingredient types
        int hotWaterCount = 0;
        int milkCount = 0;
        int espressoCount = 0;
        int vanillaSyrupCount = 0;
        int caramelSyrupCount = 0;
        int coconutCount = 0;
        int roastNutsCount = 0;
        int chocolateChipsCount = 0;
        int cinnamonCount = 0;
        int whippedCreamCount = 0;

        // Count the occurrences of each ingredient type
        for(int i = 0; i < ingredients.size(); i++){
            for(int j = 0; j < ingredients.get(i).size(); j++){
                String ingredientName = ingredients.get(i).get(0).getName();
                if(ingredientName.equalsIgnoreCase("Hot Water")){
                    hotWaterCount += 1;
                }
                else if(ingredientName.equalsIgnoreCase("Milk")){
                    milkCount += 1;
                }
                else if(ingredientName.equalsIgnoreCase("Espresso")){
                    espressoCount += 1;
                }
                else if(ingredientName.equalsIgnoreCase("Vanilla Syrup")){
                    vanillaSyrupCount += 1;
                }
                else if(ingredientName.equalsIgnoreCase("Caramel Syrup")){
                    caramelSyrupCount += 1;
                }
                else if(ingredientName.equalsIgnoreCase("Coconut")){
                    coconutCount += 1;
                }
                else if(ingredientName.equalsIgnoreCase("Roast Nuts")){
                    roastNutsCount += 1;
                }
                else if(ingredientName.equalsIgnoreCase("Chocolate Chips")){
                    chocolateChipsCount += 1;
                }
                else if(ingredientName.equalsIgnoreCase("Cinnamon")){
                    cinnamonCount += 1;
                }
                else if(ingredientName.equalsIgnoreCase("Whipped Cream")){
                    whippedCreamCount += 1;
                }
            }
        }

        // Determine the coffee name based on ingredient counts
        if(roastNutsCount == 1 && milkCount == 1 && vanillaSyrupCount == 2 && caramelSyrupCount == 1){
            this.name = "Caramel Macchiato";
        }
        else if(roastNutsCount == 1 && chocolateChipsCount == 3 && milkCount == 2 && whippedCreamCount == 1){
            this.name = "White Chocolate Mocha";
        }
        else if(hotWaterCount == 1 && roastNutsCount == 1){
            this.name = "Caffe Americano";
        }
        else if(roastNutsCount == 1 && milkCount == 2 && coconutCount == 1 && vanillaSyrupCount == 2){
            this.name = "Caffe Latte";
        }
        else if(espressoCount == 2 && milkCount == 1 && cinnamonCount == 2){
            this.name = "Cappuccino";
        }
        else{
            this.name = "Coffee";
        }
    }

    /**
     * Gets the number of calories in the coffee.
     *
     * @return The number of calories in the coffee.
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Gets the price of the coffee.
     *
     * @return The price of the coffee.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets the list of ingredients used to make the coffee.
     *
     * @return The list of ingredients.
     */
    public ArrayList<ArrayList<Ingredient>> getIngredients() {
        return ingredients;
    }
}