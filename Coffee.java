import java.util.*;

public class Coffee {
    protected String name;
    protected final int calories;
    protected final int price;
    protected final ArrayList<ArrayList<Ingredient>> ingredients;

    public Coffee(int calories, int price, ArrayList<ArrayList<Ingredient>> ingredients) {
        this.name = "Coffee";
        this.calories = calories;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName(){
        return name;
    }

    public void setCoffeeName(){
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

        for(int i = 0; i < ingredients.size(); i++){
            for(int j = 0; j < ingredients.get(i).size(); j++){
                if(ingredients.get(i).get(0).getName().equalsIgnoreCase("Hot Water")){
                    hotWaterCount += 1;
                }
                else if(ingredients.get(i).get(0).getName().equalsIgnoreCase("Milk")){
                    milkCount += 1;
                }
                else if(ingredients.get(i).get(0).getName().equalsIgnoreCase("Espresso")){
                    espressoCount += 1;
                }
                else if(ingredients.get(i).get(0).getName().equalsIgnoreCase("Vanilla Syrup")){
                    vanillaSyrupCount += 1;
                }
                else if(ingredients.get(i).get(0).getName().equalsIgnoreCase("Caramel Syrup")){
                    caramelSyrupCount += 1;
                }
                else if(ingredients.get(i).get(0).getName().equalsIgnoreCase("Coconut")){
                    coconutCount += 1;
                }
                else if(ingredients.get(i).get(0).getName().equalsIgnoreCase("Roast Nuts")){
                    roastNutsCount += 1;
                }
                else if(ingredients.get(i).get(0).getName().equalsIgnoreCase("Chocolate Chips")){
                    chocolateChipsCount += 1;
                }
                else if(ingredients.get(i).get(0).getName().equalsIgnoreCase("Cinnamon")){
                    cinnamonCount += 1;
                }
                else if(ingredients.get(i).get(0).getName().equalsIgnoreCase("Whipped Cream")){
                    whippedCreamCount += 1;
                }
            }
        }

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
            this.name = "Cappucino";
        }
        else{
            this.name = "Coffee";
        }
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