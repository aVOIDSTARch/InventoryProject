package io.casinelli.inventoryproject.Model;

/**
 * Part subclass used to store parts made in-house
 * @author Louis Casinelli Jr
 */

public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, double price, int stock,
                   int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId from part
     */
    public void setMachineId(int machineId) { this.machineId = machineId; }

    /**
     * @return machine ID from this part
     */
    public int getMachineId() { return machineId; }
}