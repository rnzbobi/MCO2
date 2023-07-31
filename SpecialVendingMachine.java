import java.util.*;

public class SpecialVendingMachine extends RegularVendingMachine{
    protected ArrayList<ArrayList<Ingredient>> coffeeIngredients;

    public SpecialVendingMachine(String name, int numberOfSlots) {
        super(name, numberOfSlots);
        this.coffeeIngredients = new ArrayList<>();
    }

    public SpecialVendingMachine(){
        super("",0);
    }

    public void updateSales(Coffee coffee){
        for(int i = 0; i < coffeeIngredients.size(); i++){
            for(int j = 0; j < coffeeIngredients.get(i).size(); j++){
                salesRecord.totalIngredientsSold += 1;
            }
        }
        salesRecord.totalSales = coffee.getPrice();
        salesRecord.updateCoffeeSold();
    }

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

    public void removeCoffeeIngredients(){
        coffeeIngredients.clear();
    }

    public ArrayList<ArrayList<Ingredient>> getCoffeeIngredients(){
        return coffeeIngredients;
    }
}