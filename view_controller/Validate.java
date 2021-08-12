package view_controller;

import Model.*;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.text.NumberFormat;

/**
 * Class to handle input validations.
 */
public class Validate {

    private static Product foundProduct;

    /**
     * Method used to validate data before creating a product object.
     * @param id Unique ID number, created programmatically so no input validation needed.
     * @param name Name of product object, string.
     * @param price Cost of the product, must be greater than the sum of all its associated parts. Must be a positive number.
     * @param stock The quantity on hand being entered for a product. Must be between or equal to the min and max values. Must be a positive number.
     * @param min The minimum quantity on hand. Must be less then or equal to the maximum and stock. Must be a positive number.
     * @param max The maximum quantity on hand. Must be greater than or equal to the minimum and stock. Must be a positive number.
     * @param sumOfParts The total value of all the associated parts for this product.
     * @return a product object.
     */
    public static Product validateProduct (String id, String name, String price, String stock, String min, String max,double sumOfParts) {
        Product product = null;
        double validPrice = 0;
        int validStock = 0, validMax = 0, validMin = 0;
        boolean quantityIsValid = false;
        boolean sumIsValid = false;
        boolean priceNonNegative = false;
        boolean minNonNegative = false;
        boolean maxNonNegative = false;
        boolean stockNonNegative = false;

        if (name.isEmpty() ||
                stock.isEmpty() ||
                price.isEmpty() ||
                max.isEmpty() ||
                min.isEmpty())
        {
            new Alert(Alert.AlertType.ERROR,"Please fill out all Product fields").showAndWait();
        } else {
            try {
                String justPrice = price.replace('$',' ').trim();
                validPrice = Double.parseDouble(justPrice);
                if (validPrice >= 0) priceNonNegative = true;
                else new Alert(Alert.AlertType.ERROR,"Price should be a positive number").showAndWait();
            } catch (NumberFormatException exception) {
                new Alert(Alert.AlertType.ERROR,"Price should be a number").showAndWait();
            }
            try {
                validStock = Integer.parseInt(stock);
                if (validStock >= 0) stockNonNegative = true;
                else new Alert(Alert.AlertType.ERROR,"Inventory quantity should be a positive number").showAndWait();
            } catch (NumberFormatException exception) {
                new Alert(Alert.AlertType.ERROR,"Inventory quantity should be a number").showAndWait();
            }
            try {
                validMin = Integer.parseInt(min);
                if (validMin >= 0) minNonNegative = true;
                else new Alert(Alert.AlertType.ERROR,"Minimum must be a positive number").showAndWait();
            } catch (NumberFormatException exception) {
                new Alert(Alert.AlertType.ERROR,"Minimum quantity should be a number").showAndWait();
            }
            try {
                validMax = Integer.parseInt(max);
                if (validMax >= 0) maxNonNegative = true;
                else new Alert(Alert.AlertType.ERROR,"Maximum quantity should be a positive number").showAndWait();
            } catch (NumberFormatException exception) {
                new Alert(Alert.AlertType.ERROR,"Maximum quantity should be a number").showAndWait();
            }
            if (validMin <= validStock && validStock <= validMax && validMin <= validMax) {
                quantityIsValid = true;
            }else {
                new Alert(Alert.AlertType.ERROR, "Inventory quantity should be between Maximum quantity and Minimum quantity").showAndWait();
            }
            if (sumOfParts > validPrice) {
                    new Alert(Alert.AlertType.ERROR, "Price can not be less than the Total of all associated parts").showAndWait();
            }else {
                sumIsValid = true;
            }
            if (quantityIsValid && sumIsValid && priceNonNegative && stockNonNegative && minNonNegative && maxNonNegative)
                    product = new Product(Integer.parseInt(id), name, validPrice, validStock, validMin, validMax);
        }
        return product;
    }
    /**
     * Method used to validate data before creating a part object.
     * @param id Unique ID number, created programmatically so no input validation needed.
     * @param name Name of part object, string.
     * @param price Cost of the part. Must be a positive number.
     * @param stock The quantity on hand being entered for a part. Must be between or equal to the min and max values. Must be a positive number.
     * @param min The minimum quantity on hand. Must be less then or equal to the maximum and stock. Must be a positive number.
     * @param max The maximum quantity on hand. Must be greater than or equal to the minimum and stock. Must be a positive number.
     * @param source The machine ID number (Must be positive) or the company that the part was purchased from.
     * @param inHouse boolean set to true whenever the part is to be an In-House part.
     * @return a product object.
     *
     * I kept getting strange errors as I built this method.
     * There was an issue with the validation setting the error notification but still creating the part.
     * It was not breaking out of the inner loop and continuing to the end.
     * I solved this by creating booleans that had to be set to true before part creation occurred.
     */
    public static Part validatePart (String id, String name, String price, String stock, String min, String max,String source, boolean inHouse) {
        Part part = null;
        double validPrice = 0;
        int validStock = 0, validMax = 0, validMin = 0, validMachineId = 0;
        boolean quantityIsValid = false;
        boolean priceNonNegative = false;
        boolean minNonNegative = false;
        boolean maxNonNegative = false;
        boolean stockNonNegative = false;
        boolean machineIdNonNegative = false;

        if (name.isEmpty() ||
                stock.isEmpty() ||
                price.isEmpty() ||
                max.isEmpty() ||
                min.isEmpty())
        {
            new Alert(Alert.AlertType.ERROR,"Please fill out all Part fields").showAndWait();
        } else {
            try {
                String justPrice = price.replace('$',' ').trim();
                validPrice = Double.parseDouble(justPrice);
                if (validPrice >= 0) priceNonNegative = true;
                else new Alert(Alert.AlertType.ERROR,"Price should be a positive number").showAndWait();
            } catch (NumberFormatException exception) {
                new Alert(Alert.AlertType.ERROR,"Price should be a number").showAndWait();
            }
            try {
                validStock = Integer.parseInt(stock);
                if (validStock >= 0) stockNonNegative = true;
                else new Alert(Alert.AlertType.ERROR,"Inventory quantity should be a positive number").showAndWait();
            } catch (NumberFormatException exception) {
                new Alert(Alert.AlertType.ERROR,"Inventory quantity should be a number").showAndWait();
            }
            try {
                validMin = Integer.parseInt(min);
                if (validMin >= 0) minNonNegative = true;
                else new Alert(Alert.AlertType.ERROR,"Minimum must be a positive number").showAndWait();
            } catch (NumberFormatException exception) {
                new Alert(Alert.AlertType.ERROR,"Minimum quantity should be a number").showAndWait();
            }
            try {
                validMax = Integer.parseInt(max);
                if (validMax >= 0) maxNonNegative = true;
                else new Alert(Alert.AlertType.ERROR,"Maximum quantity should be a positive number").showAndWait();
            } catch (NumberFormatException exception) {
                new Alert(Alert.AlertType.ERROR,"Maximum quantity should be a number").showAndWait();
            }
            if (validMin <= validStock && validStock <= validMax && validMin <= validMax) {
                quantityIsValid = true;
            } else {
                new Alert(Alert.AlertType.ERROR, "Inventory quantity should be between Maximum quantity and Minimum quantity").showAndWait();
            }
            if (inHouse) {
                try {
                    validMachineId = Integer.parseInt(source);
                    if (validMachineId >= 0) machineIdNonNegative = true;
                    else
                        new Alert(Alert.AlertType.ERROR, "Machine ID should be a positive number", ButtonType.OK).showAndWait();
                } catch (NumberFormatException exception) {
                    new Alert(Alert.AlertType.ERROR, "Machine ID should be a number", ButtonType.OK).showAndWait();
                }
            }
            if (quantityIsValid && priceNonNegative && stockNonNegative && minNonNegative && maxNonNegative && !inHouse) {
                    part = new Outsourced(Integer.parseInt(id), name, validPrice, validStock, validMin, validMax, source);
                }
            }
            if (quantityIsValid && priceNonNegative && stockNonNegative && minNonNegative && maxNonNegative && machineIdNonNegative) {
                if (inHouse) part = new InHouse(Integer.parseInt(id), name, validPrice, validStock, validMin, validMax, validMachineId);
            }

        return part;
    }
    /**
     * Method to determine if a part selected for deletion is associated with any products.
     * Sets product info so that it can be displayed to the user.
     * @param checkedPart The part selected for deletion.
     * @return boolean set to true if the part is associated with a product.
     */
    public static boolean isAnAssociatedPart (Part checkedPart) {
        boolean isPart = false;
        for (Product product : Inventory.getAllProducts()) {
            for (Part part : product.getAllAssociatedParts()) {
                if (part == checkedPart) {
                    foundProduct = product;
                    isPart = true;
                    break;
                }
            }
        }
        return isPart;
    }
    /**
     * Method to determine if a product selected for deletion has associated parts.
     * @param checkedProduct The product selected for deletion.
     * @return boolean set to true if the product has associated parts.
     */
    public static boolean hasAssociatedParts (Product checkedProduct) {
        boolean hasPart = false;
        if (!checkedProduct.getAllAssociatedParts().isEmpty()) hasPart = true;
        return hasPart;
    }
    /**
     * Method to return product name so th user dose not have to hunt through all the products to find where the part is associated.
     * @return The product with an associated part selected for deletion.
     */
    public static Product getFoundProduct() {
        return foundProduct;
    }


}