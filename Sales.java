public class Sales {
    protected int totalSales;
    protected int totalIngredientsSold;
    protected int totalCoffeeSold;

    public Sales(){
        this.totalSales = 0;
        this.totalIngredientsSold = 0;
        this.totalCoffeeSold = 0;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public int getTotalIngredientsSold() {
        return totalIngredientsSold;
    }

    public int getTotalCoffeeSold(){
        return totalCoffeeSold;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public void setTotalIngredientsSold(int totalIngredientsSold) {
        this.totalIngredientsSold = totalIngredientsSold;
    }

    public void setTotalCoffeeSold(int totalCoffeeSold) {
        this.totalCoffeeSold = totalCoffeeSold;
    }

    public void updateCoffeeSold(){
        this.totalCoffeeSold += 1;
    }
}