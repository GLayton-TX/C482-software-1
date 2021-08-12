package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Product class.
 * It contains all the attributes and methods for creating and updating products.
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private final ObservableList<Part> associatedParts;

    /**
     * Constructor for the Product class.
     * @param id Unique ID number for each product.
     * @param name Name of the product.
     * @param price The price as a Double for the product.
     * @param stock The quantity in stock/available of the product.
     * @param min The minimum quantity of the product to be kept.
     * @param max The maximum quantity of the product to be kept.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.associatedParts = FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Method to get a products ID number.
     * @return The products ID number.
     */
    public int getId() {
        return id;
    }
    /**
     * Method to set a products ID number.
     * @param id The ID number that the user wishes to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method to get a products name.
     * @return The products name.
     */
    public String getName() {
        return name;
    }
    /**
     * Method to set a products name.
     * @param name The name that the user wishes to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Method to get a products price.
     * @return The products price.
     */
    public double getPrice() {
        return price;
    }
    /**
     * Method to set a products price.
     * @param price The price that the user wishes to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Method to get a products stock quantity.
     * @return The products stock quantity.
     */
    public int getStock() {
        return stock;
    }
    /**
     * Method to set a products stock quantity.
     * @param stock The stock quantity that the user wishes to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * Method to get a products minimum stock quantity.
     * @return The products minimum stock quantity.
     */
    public int getMin() {
        return min;
    }
    /**
     * Method to set a products minimum stock quantity.
     * @param min The minimum stock quantity that the user wishes to set.
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * Method to get a products maximum stock quantity.
     * @return The products maximum stock quantity.
     */
    public int getMax() {
        return max;
    }
    /**
     * Method to set a products maximum stock quantity.
     * @param max The maximum stock quantity that the user wishes to set.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Method to add/associate parts to the product.
     * @param part The part the user wishes to add/associate with the product.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /**
     * Method to remove a parts association with a product.
     * @param selectedAssociatedPart The part to remove from the product.
     * @return If part is found and successfully removed.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        try {
            associatedParts.remove(selectedAssociatedPart);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    /**
     * Method to get a products list of associated parts.
     * @return The products list of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
