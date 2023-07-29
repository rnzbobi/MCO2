public class Sales {
    protected int totalSales;
    protected int totalIngredientsSold;

    public Sales(int totalSales, int totalIngredientsSold){
        this.totalSales = totalSales;
        this.totalIngredientsSold = totalIngredientsSold;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public int getTotalIngredientsSold() {
        return totalIngredientsSold;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public void setTotalIngredientsSold(int totalIngredientsSold) {
        this.totalIngredientsSold = totalIngredientsSold;
    }
}