/**
 * The VendingMachineFactory class provides methods to create different types of vending machines.
 * It serves as a factory class for creating RegularVendingMachine and SpecialVendingMachine instances.
 */
public class VendingMachineFactory {

    /**
     * Creates a new RegularVendingMachine instance with the specified name and number of slots.
     *
     * @param name          The name of the vending machine.
     * @param numberOfSlots The number of slots (shelves) in the vending machine.
     * @return A new RegularVendingMachine instance with the given name and number of slots.
     */
    public RegularVendingMachine createRegularVendingMachine(String name, int numberOfSlots) {
        return new RegularVendingMachine(name, numberOfSlots);
    }

    /**
     * Creates a new SpecialVendingMachine instance with the specified name and number of slots.
     *
     * @param name          The name of the vending machine.
     * @param numberOfSlots The number of slots (shelves) in the vending machine.
     * @return A new SpecialVendingMachine instance with the given name and number of slots.
     */
    public SpecialVendingMachine createSpecialVendingMachine(String name, int numberOfSlots) {
        return new SpecialVendingMachine(name, numberOfSlots);
    }
}