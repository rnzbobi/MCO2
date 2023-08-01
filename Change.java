/**
 * The Change class represents the denominations to be returned as change in a vending machine transaction.
 * It holds a list of denominations, where each element in the list represents a group of denominations of the same value.
 * For example, if the change includes three 100-peso bills and two 50-peso bills, the denominations list will contain
 * two inner lists. The first inner list will have three Denomination objects representing 100-peso bills, and the second
 * inner list will have two Denomination objects representing 50-peso bills.
 */
public class Change {
    protected ArrayList<ArrayList<Denomination>> denominations;

    /**
     * Constructs a Change object with the specified denominations.
     *
     * @param denominations The list of denominations to be returned as change.
     */
    public Change(ArrayList<ArrayList<Denomination>> denominations) {
        this.denominations = denominations;
    }

    /**
     * Retrieves the list of denominations representing the change.
     *
     * @return The list of denominations to be returned as change.
     */
    public ArrayList<ArrayList<Denomination>> getDenominations() {
        return denominations;
    }

    /**
     * Sets the list of denominations representing the change.
     *
     * @param denominations The list of denominations to be set as change.
     */
    public void setDenominations(ArrayList<ArrayList<Denomination>> denominations) {
        this.denominations = denominations;
    }
}