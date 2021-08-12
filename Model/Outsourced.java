package Model;

/**
 * The Outsourced class that represents a subclass of the part class.
 * This represents a part that was created by another company and purchased.
 */
public class Outsourced extends Part{
    String companyName;

    /**
     *  The Constructor for the Outsourced object.
     * @param id The Unique ID for the part.
     * @param name The name of the part.
     * @param price The cost of the part to be made.
     * @param stock The quantity of the part on hand.
     * @param min The minimum quantity of the part to keep.
     * @param max The maximum quantity of the part to keep.
     * @param companyName The name of the company that this part was purchased from.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max,String companyName) {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }
    /**
     * Method to return the company where the part was purchased name.
     * @return The company name of the manufacturer.
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * Method to set the name of the company that made the part.
     * @param companyName The name of the company who made the part to be set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
