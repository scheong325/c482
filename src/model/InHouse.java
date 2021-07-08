package model;

/**
 * This displays the in-house part.
 *
 *
 */
public class InHouse extends Part{

    /**
     * This declares the machine ID.
     */
    private int machineId;

    /**
     * This is the getter for machineId.
     *
     * @return Returns machineId.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * This is the setter of the machineId.
     *
     * @param machineId machine ID.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * This is the constructor for InHouse.
     * @param id The ID for the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The inventory level of the part.
     * @param min The minimum inventory level of the part.
     * @param max The maximum inventory level of the part.
     * @param machineId machine ID.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

}
