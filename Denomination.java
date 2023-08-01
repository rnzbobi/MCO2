/**
 * The Denomination class represents a denomination used in a vending machine.
 * It contains information about the value of the denomination.
 */
public class Denomination {
    protected int value;

    /**
     * Creates a new Denomination instance with the specified value.
     *
     * @param value The value of the denomination.
     */
    public Denomination(int value){
        this.value = value;
    }

    /**
     * Gets the value of the denomination.
     *
     * @return The value of the denomination.
     */
    public int getValue() {
        return value;
    }
}