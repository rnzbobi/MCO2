/**
 * The Ingredient class represents an ingredient used in a coffee recipe.
 * It contains information about the name, calories, and price of the ingredient.
 */
public class Ingredient {
    protected final String name;
    protected final int calories;
    protected int price;

    /**
     * Creates a new Ingredient instance with the specified name, calories, and price.
     *
     * @param name The name of the ingredient.
     * @param calories The calories in the ingredient.
     * @param price The price of the ingredient.
     */
    public Ingredient(String name, int calories, int price){
        this.name = name;
        this.calories = calories;
        this.price = price;
    }

    /**
     * Gets the name of the ingredient.
     *
     * @return The name of the ingredient.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the calories in the ingredient.
     *
     * @return The calories in the ingredient.
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Gets the price of the ingredient.
     *
     * @return The price of the ingredient.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the ingredient.
     *
     * @param price The new price to set for the ingredient.
     */
    public void setPrice(int price) {
        this.price = price;
    }
}





