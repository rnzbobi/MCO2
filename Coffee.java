import java.util.*;

public class Coffee {
    protected final int calories;
    protected final int price;
    protected final ArrayList<ArrayList<Ingredient>> ingredients;

    public Coffee(int calories, int price, ArrayList<ArrayList<Ingredient>> ingredients) {
        this.calories = calories;
        this.price = price;
        this.ingredients = ingredients;
    }

    public int getCalories() {
        return calories;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<ArrayList<Ingredient>> getIngredients() {
        return ingredients;
    }
}