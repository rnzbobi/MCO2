/**
 * The Sales class represents the sales record of a coffee vending machine.
 * It keeps track of the total sales amount, the total number of ingredients sold,
 * and the total number of coffees sold.
 */
public class Sales {
    protected int totalSales;
    protected int totalIngredientsSold;
    protected int totalCoffeeSold;

    /**
     * Creates a new Sales instance with initial sales records set to zero.
     */
    public Sales(){
        this.totalSales = 0;
        this.totalIngredientsSold = 0;
        this.totalCoffeeSold = 0;
    }

    /**
     * Gets the total sales amount.
     *
     * @return The total sales amount.
     */
    public int getTotalSales() {
        return totalSales;
    }

    /**
     * Gets the total number of ingredients sold.
     *
     * @return The total number of ingredients sold.
     */
    public int getTotalIngredientsSold() {
        return totalIngredientsSold;
    }

    /**
     * Gets the total number of coffees sold.
     *
     * @return The total number of coffees sold.
     */
    public int getTotalCoffeeSold(){
        return totalCoffeeSold;
    }

    /**
     * Sets the total sales amount.
     *
     * @param totalSales The new total sales amount to set.
     */
    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * Sets the total number of ingredients sold.
     *
     * @param totalIngredientsSold The new total number of ingredients sold to set.
     */
    public void setTotalIngredientsSold(int totalIngredientsSold) {
        this.totalIngredientsSold = totalIngredientsSold;
    }

    /**
     * Sets the total number of coffees sold.
     *
     * @param totalCoffeeSold The new total number of coffees sold to set.
     */
    public void setTotalCoffeeSold(int totalCoffeeSold) {
        this.totalCoffeeSold = totalCoffeeSold;
    }

    /**
     * Updates the total number of coffees sold by incrementing it by 1.
     */
    public void updateCoffeeSold(){
        this.totalCoffeeSold += 1;
    }
}