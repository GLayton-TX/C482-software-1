package Model;

/**
 * In-House class that represents a subclass of the part class.
 * This represents a part that was created by the company on one of their own machines.
 */
public class InHouse extends Part{
    int machineId;

    /**
     *  The Constructor for the InHouse object.
     * @param id The Unique ID for the part.
     * @param name The name of the part.
     * @param price The cost of the part to be made.
     * @param stock The quantity of the part on hand.
     * @param min The minimum quantity of the part to keep.
     * @param max The maximum quantity of the part to keep.
     * @param machineId The ID number of the machine that was used to create the part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max,int machineId) {
        super(id,name,price,stock,min,max);
        setMachineId(machineId);
    }
    /**
     * Method to return the machine ID number.
     * @return The machine ID number.
     */
    public int getMachineId() {
        return machineId;
    }
    /**
     * Method to set the machines ID number.
     * @param machineId The machine ID number to be set.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
