import java.util.*;

public class Change {
    protected ArrayList<ArrayList<Denomination>> denominations;

    public Change(ArrayList<ArrayList<Denomination>> denominations) {
        this.denominations = denominations;
    }

    public ArrayList<ArrayList<Denomination>> getDenominations() {
        return denominations;
    }

    public void setDenominations(ArrayList<ArrayList<Denomination>> denominations) {
        this.denominations = denominations;
    }
}