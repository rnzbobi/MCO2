public class Ingredient {
    protected final String name;
    protected final int calories;
    protected int price;

    public Ingredient(String name, int calories, int price){
        this.name = name;
        this.calories = calories;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}