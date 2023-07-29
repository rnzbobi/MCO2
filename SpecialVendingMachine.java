import java.util.*;

public class SpecialVendingMachine extends RegularVendingMachine{
    public SpecialVendingMachine(String name, int numberOfSlots) {
        super(name, numberOfSlots);
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

    public Ingredient selectItem(String itemName){
        return super.selectItem(itemName);
    }

    public Ingredient removeItem(String itemName){
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

    /*public Coffee createCoffee(ArrayList<ArrayList<Ingredient>> ingredients){
    }*/
}