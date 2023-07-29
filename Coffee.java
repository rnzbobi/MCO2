import java.util.*;

public class Coffee {
    protected final String name;
    protected int calories;
    protected int price;
    protected final ArrayList<ArrayList<Ingredient>> ingredients;

    public Coffee(String name, int calories, int price, ArrayList<ArrayList<Ingredient>> ingredients) {
        this.name = name;
        this.calories = calories;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<ArrayList<Ingredient>> getIngredients() {
        return ingredients;
    }
}