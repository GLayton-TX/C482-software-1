package view_controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Controller class for the add part window.
 */
public class AddProduct implements Initializable {

    @FXML TextField partsSearchField;
    @FXML TextField idField;
    @FXML TextField nameField;
    @FXML TextField stockField;
    @FXML TextField priceField;
    @FXML TextField maxField;
    @FXML TextField minField;
    @FXML Button saveButton;
    @FXML Button cancelButton;

    @FXML TableView<Part> partsTable;
    @FXML TableColumn<Part, Integer> partIdColumn;
    @FXML TableColumn<Part, String> partNameColumn;
    @FXML TableColumn<Part, Integer> partQuantityColumn;
    @FXML TableColumn<Part, Double> partPriceColumn;

    @FXML TableView<Part> associatedPartTable;
    @FXML Button addAssociatedPartButton;
    @FXML Button removeAssociatedPartButton;
    @FXML TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML TableColumn<Part, String> associatedPartNameColumn;
    @FXML TableColumn<Part, Integer> associatedPartQuantityColumn;
    @FXML TableColumn<Part, Double> associatedPartPriceColumn;
    private final ObservableList<Part> toBeAddedList = FXCollections.observableArrayList();
    private double sum = 0.0;

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
     * Method called when the saveButton is clicked.
     * Gets the sum of all associated parts for the product.
     * Passes data from all the fields to the validate product method.
     * If product returned is valid the it adds the associated parts to the product.
     * Then adds the product to inventory, increments the number generator and returns to the main stage.
     */
    public void saveButtonClicked() {
        for (Part part : toBeAddedList) {
            double temp = part.getPrice();
            sum += temp;
        }
        Product validProduct = Validate.validateProduct(
                        idField.getText(),
                        nameField.getText(),
                        priceField.getText(),
                        stockField.getText(),
                        minField.getText(),
                        maxField.getText(),
                        sum);
        if (validProduct != null) {
            for (Part part : toBeAddedList) validProduct.addAssociatedPart(part);
            Inventory.addProduct(validProduct);
            Inventory.getProductIdGenerator().getAndIncrement();
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }
    /**
     * Method called when the cancel button is clicked.
     * If all the fields are blank this closes the window and returns to the main stage.
     * If they are not all blank then this has a popup to alert the user their data will not be saved.
     * Confirmation closes the window and returns to the main stage.
     */
    public void cancelButtonClicked() {
        if (nameField.getText().isEmpty()
                && stockField.getText().isEmpty()
                && priceField.getText().isEmpty()
                && maxField.getText().isEmpty()
                && minField.getText().isEmpty())
        {
            ((Stage) cancelButton.getScene().getWindow()).close();
        }
        else
            {
            Alert alert = (new Alert(Alert.AlertType.CONFIRMATION,"Product will not be saved: Continue?"));
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) ((Stage) cancelButton.getScene().getWindow()).close();
            }
    }
    /**
     * Method called when the addAssociatedPartsButton is clicked.
     * Takes the Part that has been selected to be added, and adds it to the toBeAddedList.
     * The toBeAddedList is displayed as a table at the bottom of the addProduct stage.
     */
    public void addAssociatedPartButtonClicked() {
        toBeAddedList.add(partsTable.getSelectionModel().getSelectedItem());
    }
    /**
     * Method called when the removeAssociatedPartsButton is clicked.
     * Displays a popup to confirm removal.
     * Takes the Part that has been selected to be removed, and removes it from the toBeAddedList.
     * The toBeAddedList is displayed as a table at the bottom of the addProduct stage.
     */
    public void removeAssociatedPartButtonClicked() {
        Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you wish to remove " + selectedPart.getName() + "?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) toBeAddedList.remove(selectedPart);
    }
    /**
     * This populates the partsTable and associatedPartsTable with attributes associated with each saved part.
     * Converts the Double of the price attribute to display as a currency.
     * Sets each table column to display the attribute associated with it.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        partsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // set to use information from object
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        idField.setText(Inventory.getProductIdGenerator().toString());

        NumberFormat associatedPartCurrencyFormat = NumberFormat.getCurrencyInstance();
        associatedPartPriceColumn.setCellFactory(tc -> new TableCell<Part,Double>() {

            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(associatedPartCurrencyFormat.format(price));
                }
            }
        });

        associatedPartTable.setItems(toBeAddedList);
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // set to use information from object
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}
