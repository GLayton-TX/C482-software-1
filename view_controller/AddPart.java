package view_controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the add part window.
 */
public class AddPart implements Initializable {

    @FXML RadioButton inHouseRadioButton;
    @FXML RadioButton outsourcedRadioButton;
    @FXML Button saveButton;
    @FXML Button cancelButton;
    @FXML TextField idField;
    @FXML TextField nameField;
    @FXML TextField stockField;
    @FXML TextField priceField;
    @FXML TextField maxField;
    @FXML TextField sourceField;
    @FXML TextField minField;
    @FXML Label sourceLabel;

    /**
     * Method called when the inHouseRadioButton is clicked.
     * Sets the sourceLabel to display Machine ID.
     */
    public void inHouseRadioButtonSelected() { sourceLabel.setText("Machine ID"); }
    /**
     * Method called when the outsourcedRadioButton id clicked.
     * sets the sourceLabel to display Company Name.
     */
    public void outsourcedRadioButtonSelected() { sourceLabel.setText("Company Name"); }
    /**
     * Method called when the saveButton is clicked.
     * Passes data from all the fields to the validate part method.
     * If part returned is valid the it adds the part to inventory, increments the number generator and returns to the main stage.
     */
    public void saveButtonClicked() {
       boolean inHouse = inHouseRadioButton.isSelected();
       Part validPart = Validate.validatePart(
               idField.getText(),
               nameField.getText(),
               priceField.getText(),
               stockField.getText(),
               minField.getText(),
               maxField.getText(),
               sourceField.getText(),
               inHouse);
       if (validPart != null) {
           Inventory.addPart(validPart);
           Inventory.getPartsIdGenerator().getAndIncrement();
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
        if (
                nameField.getText().isEmpty()
                && stockField.getText().isEmpty()
                && priceField.getText().isEmpty()
                && maxField.getText().isEmpty()
                && minField.getText().isEmpty()
                && sourceField.getText().isEmpty())
        {
            ((Stage) cancelButton.getScene().getWindow()).close();
        }
        else
            {
            Alert alert = (new Alert(Alert.AlertType.CONFIRMATION,"Part will not be saved: Continue?",ButtonType.OK));
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) ((Stage) cancelButton.getScene().getWindow()).close();
            }
    }

    /**
     * This initializes the ID number to be displayed.
     * Sets the ID number field to the next available number.
     * Creates the toggleGroup for the radio buttons. This sets it so only one can be selected at a time.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idField.setText(Inventory.getPartsIdGenerator().toString());

        ToggleGroup toggleGroup = new ToggleGroup();
        this.inHouseRadioButton.setToggleGroup(toggleGroup);
        this.inHouseRadioButton.setSelected(true);
        this.outsourcedRadioButton.setToggleGroup(toggleGroup);

    }
}
