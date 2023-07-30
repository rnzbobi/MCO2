import java.util.*;

public class SpecialVendingMachine extends RegularVendingMachine{
    protected ArrayList<ArrayList<Ingredient>> coffeeIngredients;

    public SpecialVendingMachine(String name, int numberOfSlots) {
        super(name, numberOfSlots);
        this.coffeeIngredients = new ArrayList<>();
    }

    public void sortDenominationsDescending() {
        super.sortDenominationsDescending();
    }

    public Change dispenseChange(int amountToPay, int amountInserted){
        return super.dispenseChange(amountToPay, amountInserted);
    }

    public boolean addIngredient(String name, int calories, int price, int quantity){
        return super.addIngredient(name, calories, price, quantity);
    }

    public boolean addQuantity(String name, int quantity){
        return super.addQuantity(name, quantity);
    }

    public Ingredient selectItem(String itemName){
        return super.selectItem(itemName);
    }

    public boolean removeItem(String itemName){
        return super.removeItem(itemName);
    }

    public boolean addSlot(ArrayList<Ingredient> slot){
        return super.addSlot(slot);
    }

    public boolean removeIngredient(String itemname){
        return super.removeIngredient(itemname);
    }

    public boolean addDenomination(int value) {
        return super.addDenomination(value);
    }

    public boolean addUserDenomination(int value) {
        return super.addUserDenomination(value);
    }

    public boolean collectDenomination(int value, int quantity){
        return super.collectDenomination(value, quantity);
    }

    public String createSummary(){
        return super.createSummary();
    }

    public int countIngredient(String ingredientName){
        return super.countIngredient(ingredientName);
    }

    public void updateCurrentBalance(){
        super.updateCurrentBalance();
    }

    public void updateCurrentUserBalance(){
        super.updateCurrentUserBalance();
    }

    public void updateStartingInventory(){
        super.updateStartingInventory();
    }

    public void updateCurrentInventory(){
        super.updateCurrentInventory();
    }

    public void updateSales(String itemName){
        super.updateSales(itemName);
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

    public boolean setPrice(String itemName, int price){
        return super.setPrice(itemName, price);
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
    
    public ArrayList<ArrayList<Ingredient>> getCoffeeIngredients(){
        return coffeeIngredients;
    }
}