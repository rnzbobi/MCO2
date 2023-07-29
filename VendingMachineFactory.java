import java.util.*;
public class VendingMachineFactory {

    public VendingMachineFactory() {
        
    }
    public RegularVendingMachine createRegularVendingMachine(String name, int numberOfSlots) {
        return new RegularVendingMachine(name, numberOfSlots);
    }

    public SpecialVendingMachine createSpecialVendingMachine(String name, int numberOfSlots){
        return new SpecialVendingMachine(name, numberOfSlots);
    }
}