package view_controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * This is the class called from the start method.
 * It includes all the required buttons, search fields as well as tables that display parts and products.
 */
public class InventoryManagementScreen implements Initializable {

    @FXML TextField partsSearchField;
    @FXML TableView<Part> partsTable;
    @FXML Button addPartsButton;
    @FXML Button modifyPartsButton;
    @FXML Button deletePartsButton;
    @FXML TableColumn<Part, Integer> partIdColumn;
    @FXML TableColumn<Part, String> partNameColumn;
    @FXML TableColumn<Part, Integer> partQuantityColumn;
    @FXML TableColumn<Part, Double> partPriceColumn;

    @FXML TextField productsSearchField;
    @FXML TableView<Product> productsTable;
    @FXML Button addProductsButton;
    @FXML Button modifyProductsButton;
    @FXML Button deleteProductsButton;
    @FXML TableColumn<Product, Integer> productIdColumn;
    @FXML TableColumn<Product, String> productNameColumn;
    @FXML TableColumn<Product, Integer> productQuantityColumn;
    @FXML TableColumn<Product, Double> productPriceColumn;
    @FXML Button exitButton;


    /**
     * Whenever the addPartsButton is clicked this method is called.
     * It opens a new stage that includes the functionality to create a new part.
     * @throws IOException This will throw an exception if the .fxml file is not found.
     */
    public void addPartsButtonClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/AddPart.fxml"));
        Stage window =  new Stage();
        window.setScene(new Scene(root));
        window.show();
    }

    /**
     * Whenever the modifyPartsButton is clicked this method is called.
     * It opens a new stage that includes functionality to modify an existing part.
     * The part selected is passed to the new stage through the controller as well as the parts index.
     * These are used to display the correct information on the modify page as well as allow the correct part to be updated.
     * @throws IOException This will throw an exception if the .fxml file is not found.
     * Will cause an error dialog to pop up if no part is selected when the modifyPartsButton is pressed.
     */
    public void modifyPartsButtonClicked() throws IOException {
        if (!partsTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view_controller/ModifyPart.fxml"));
            Parent root = loader.load();
            Stage window =  new Stage();
            window.setScene(new Scene(root));
            window.show();
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            Integer selectedIndex = partsTable.getSelectionModel().getSelectedIndex();
            ModifyPart controller = loader.getController();
            controller.displaySelectedPart(selectedIndex,selectedPart);
        }
        else {
            new Alert(Alert.AlertType.ERROR, "Please select a part to modify", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Whenever the deletePartsButton is pressed this method is called.
     * It will cause a warning popup if no part is selected when the deletePartsButton is pressed.
     * Confirms the user wishes to delete the selected part.
     * Searches the products in Inventory to verify if any products include the selected part. Prompts a warning and returns if if finds any.
     * If no products contain the selected part, deletes selected part from Inventory.
     */
    public void deletePartsButtonClicked() {
        if (partsTable.getSelectionModel().isEmpty()) new Alert(Alert.AlertType.ERROR, "Please select a part to delete", ButtonType.OK).showAndWait();
        else {
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.WARNING, "Do you wish to delete " + selectedPart.getName() + "?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                if (Validate.isAnAssociatedPart(selectedPart)) {
                    new Alert(Alert.AlertType.WARNING,
                            selectedPart.getName() + " must be removed from " +  Validate.getFoundProduct().getName() + " to be deleted.").showAndWait();
                } else {
                Inventory.deletePart(selectedPart);
                }
            }
        }
    }
    /**
     * Whenever the addProductsButton is clicked this method is called.
     * It opens a new stage that includes the functionality to create a new product.
     * @throws IOException This will throw an exception if the .fxml file is not found.
     */
    public void addProductsButtonClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/AddProduct.fxml"));
        Stage window =  new Stage();
        window.setScene(new Scene(root));
        window.show();
    }
    /**
     * Whenever the modifyProductsButton is clicked this method is called.
     * It opens a new stage that includes functionality to modify an existing product.
     * The product selected is passed to the new stage through the controller as well as the products index.
     * These are used to display the correct information on the modify page as well as allow the correct product to be updated.
     * @throws IOException This will throw an exception if the .fxml file is not found.
     * Will cause an error dialog to pop up if no product is selected when the modifyProductsButton is pressed.
     */
    public void modifyProductsButtonClicked() throws IOException {
        if (!productsTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view_controller/ModifyProduct.fxml"));
            Parent root = loader.load();
            Stage window = new Stage();
            window.setScene(new Scene(root));
            window.show();
            Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
            Integer selectedIndex = productsTable.getSelectionModel().getSelectedIndex();
            ModifyProduct controller = loader.getController();
            controller.displaySelectedProduct(selectedIndex,selectedProduct);
        }
        else {
            new Alert(Alert.AlertType.ERROR, "Please select a product to modify", ButtonType.OK).showAndWait();
        }
    }
    /**
     * Whenever the deleteProductsButton is pressed this method is called.
     * It will cause a warning popup if no product is selected when the deleteProductsButton is pressed.
     * Confirms the user wishes to delete the selected product.
     * Deletes selected product from Inventory.
     */
    public void deleteProductsButtonClicked() {
        if (!productsTable.getSelectionModel().isEmpty()) {
            Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.WARNING, "Do you wish to delete" + selectedProduct.getName() + "?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                if (Validate.hasAssociatedParts(selectedProduct)) {
                    new Alert(Alert.AlertType.WARNING, "Unable to delete a product with associated parts").showAndWait();
                } else Inventory.deleteProduct(selectedProduct);
            }
        }
        else {
            new Alert(Alert.AlertType.ERROR, "Please select a product to delete", ButtonType.OK).showAndWait();
        }
    }

    /**
     * This method is called whenever the exitButton is pressed.
     * It launches a confirmation dialog.
     * if the user confirms then this method exits the running program.
     */
    public void exitButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you wish to close the program?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) System.exit(0);
    }

    /**
     * This method is called when a user presses enter with the partsSearchField is selected.
     * If the field is blank this calls all parts to be displayed.
     * Searches the Inventory by either name or ID number.
     * Clears field after search.
     */
    public void partsSearchFieldClicked () {
        if (partsSearchField.getText().isEmpty()) partsTable.setItems(Inventory.getAllParts());
        try {
            ObservableList<Part> searchedList = FXCollections.observableArrayList();
            searchedList.add(Inventory.lookupPart(Integer.parseInt(partsSearchField.getText())));
            partsTable.setItems(searchedList);
        } catch (NumberFormatException e) {
            partsTable.setItems(Inventory.lookupPart(partsSearchField.getText()));
        }
        partsSearchField.clear();
    }
    /**
     * This method is called when a user presses enter with the productsSearchField is selected.
     * If the field is blank this calls all products to be displayed.
     * Searches the Inventory by either name or ID number.
     * Clears field after search.
     */
    public void productsSearchFieldClicked() {
        if (productsSearchField.getText().isEmpty()) productsTable.setItems(Inventory.getAllProducts());
        try {
            ObservableList<Product> searchedList = FXCollections.observableArrayList();
            searchedList.add(Inventory.lookupProduct(Integer.parseInt(productsSearchField.getText())));
            productsTable.setItems(searchedList);
        } catch (NumberFormatException e) {
            productsTable.setItems(Inventory.lookupProduct(productsSearchField.getText()));
        }
        productsSearchField.clear();
    }
    /**
     * This initializes the data displayed in the tables as well as updates them after changes.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * This populates the partsTable with attributes associated with each saved part.
         * Converts the Double of the price attribute to display as a currency.
         * Sets each table column to display the attribute associated with it.
         * For future updates to this program I would make the search field dynamic. code to do so commented out below.
         */
            NumberFormat partCurrencyFormat = NumberFormat.getCurrencyInstance();
            partPriceColumn.setCellFactory(tc -> new TableCell<Part,Double>() {

                @Override
                protected void updateItem(Double price, boolean empty) {
                    super.updateItem(price, empty);
                    if (empty) setText(null);
                    else {
                        setText(partCurrencyFormat.format(price));
                    }
                }
            });

        /* dynamic parts search function that shows a sorted list based on the current search field text

        FilteredList<Part> filteredPartList = new FilteredList<>(Inventory.getAllParts(),b -> true);
        partsSearchField.textProperty().addListener((observableValue, s, searchField) -> filteredPartList.setPredicate(part -> {
            if (searchField == null || searchField.isEmpty()) {
                return true;
            }
            String stringFilter = searchField.toLowerCase();

            if (part.getName().toLowerCase().contains(stringFilter)) {
                return true;
            }
            else return String.valueOf(part.getId()).contains(stringFilter);
        }));
        */

            partsTable.setItems(Inventory.getAllParts());
            partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // set to use information from object
            partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            partQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));


        /**
         * This populates the productsTable with attributes associated with each saved product.
         * Converts the Double of the price attribute to display as a currency.
         * Sets each table column to display the attribute associated with it.
         * For future updates to this program I would make the search field dynamic. code to do so commented out below.
         */
            NumberFormat productCurrencyFormat = NumberFormat.getCurrencyInstance();
            productPriceColumn.setCellFactory(tc -> new TableCell<Product,Double>() {

                @Override
                protected void updateItem(Double price, boolean empty) {
                    super.updateItem(price, empty);
                    if (empty) setText(null);
                    else {
                        setText(productCurrencyFormat.format(price));
                    }
                }
            });

        /* dynamic products search function that shows a sorted list based on the current search field text

        FilteredList<Product> filteredProductList = new FilteredList<>(Inventory.getAllProducts(),b -> true);
        productsSearchField.textProperty().addListener((observableValue, s, searchField) -> filteredProductList.setPredicate(product -> {
            if (searchField == null || searchField.isEmpty()) {
                return true;
            }
            String stringFilter = searchField.toLowerCase();

            if (product.getName().toLowerCase().contains(stringFilter)) {
                return true;
            }
            else return String.valueOf(product.getId()).contains(stringFilter);
        }));

        partsTable.setItems(filteredPartList);
        productsTable.setItems(filteredProductList);

        */

            productsTable.setItems(Inventory.getAllProducts());
            productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // set to use information from object
            productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }


}
