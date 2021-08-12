package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Inventory class.
 * Holds the lists of parts and products as well as methods to interact with the lists.
 */
public class Inventory {


    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static final AtomicInteger partsIdGenerator = new AtomicInteger(1);
    private static final AtomicInteger productIdGenerator = new AtomicInteger(1);

    /**
     * Method to create a unique ID for parts objects.
     * @return Unique parts ID.
     */
    public static AtomicInteger getPartsIdGenerator() {
        return partsIdGenerator;
    }
    /**
     * Method to create a unique ID for product objects.
     * @return Unique product ID.
     */
    public static AtomicInteger getProductIdGenerator() {
        return productIdGenerator;
    }
    /**
     * Method to add a part to the Inventory.
     * Insures that data validation has passed along a part before attempting to save to the Inventory.
     * @param newPart Part to be added to the Inventory.
     */
    public static void addPart(Part newPart){
        if (newPart != null) allParts.add(newPart);
    }
    /**
     * Method to add a product to the Inventory.
     * Insures that data validation has passed along a product before attempting to save to the Inventory.
     * @param newProduct Product to be added to the Inventory.
     */
    public static void addProduct(Product newProduct){
        if (newProduct != null) allProducts.add(newProduct);
    }
    /**
     * Method to search all parts in Inventory for a matching part.
     * @param partId The part ID to be used in the search.
     * @return The part object that matches the search.
     */
    public static Part lookupPart (int partId) {
        for (Part temp : Inventory.getAllParts()) {
            if(temp.getId() == partId) {
                return temp;
            }
        }
        return null;
    }
    /**
     * Method to search all products in Inventory for a matching product.
     * @param productId The product ID to be used in the search.
     * @return The product object that matches the search.
     */
    public static Product lookupProduct(int productId){
        ObservableList<Product> productsList = Inventory.getAllProducts();
        for (Product temp : productsList) {
            if(temp.getId() == productId) {
                return temp;
            }
        }
        return null;
    }
    /**
     * Method to search all parts in Inventory for a matching part.
     * @param partName The part name to be used in the search.
     * @return A list containing the part object that matches the search.
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partsList = FXCollections.observableArrayList();
        for (Part temp : Inventory.getAllParts()) {
            if(temp.getName().toLowerCase().contains(partName.toLowerCase())) {
                partsList.add(temp);
            }
        }
        return partsList;
    }
    /**
     * Method to search all products in Inventory for a matching product.
     * @param productName The product name to be used in the search.
     * @return A list containing the product object that matches the search.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        for (Product temp : Inventory.getAllProducts()) {
            if(temp.getName().toLowerCase().contains(productName.toLowerCase())) {
                productsList.add(temp);
            }
        }
        return productsList;
    }
    /**
     * Method to update the attributes of the selected part in Inventory.
     * @param index The index in the parts array of the selected part.
     * @param selectedPart The updated part(a new part created with the updated attributes the user defined that will overwrite the old part).
     */
    public static void updatePart(int index,Part selectedPart){
        allParts.set(index,selectedPart);
    }
    /**
     * Method to update the attributes of the selected product in Inventory.
     * @param index The index in the products array of the selected product.
     * @param selectedProduct The updated product(a new product created with the updated attributes the user defined that will overwrite the old product).
     */
    public static void updateProduct(int index,Product selectedProduct){
        allProducts.set(index,selectedProduct);
    }
    /**
     * Method to delete a part from Inventory.
     * @param selectedPart the part to be deleted.
     * @return If the deletion was completed successfully.
     */
    public static boolean deletePart(Part selectedPart){
        try {
            allParts.remove(selectedPart);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    /**
     * Method to delete a product from Inventory.
     * @param selectedProduct the product to be deleted.
     * @return If the deletion was completed successfully.
     */
    public static boolean deleteProduct(Product selectedProduct){
        try {
            allProducts.remove(selectedProduct);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    /**
     * Method to return a list of all parts in Inventory.
     * @return List of all parts in Inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**
     * Method to return a list of all products in Inventory.
     * @return List of all products in Inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
