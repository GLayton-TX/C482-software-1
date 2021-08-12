package view_controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class for the modify parts window.
 */
public class ModifyPart implements Initializable {

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
    private Integer modifyIndex;

    /**
     * Method to return the index of the part in the parts array.
     * The index is needed to update the part instead of adding a new part to the array.
     * @return The index of the part selected to be modified.
     */
    public Integer getModifyIndex() {
        return modifyIndex;
    }
    /**
     * Method to properly display all the attributes from the part that was selected to be modified.
     * Sets all fields data with those pulled from the part object.
     * Sets the In House or Outsourced toggle based on the type of part.
     * @param passedIndex The index of the part selected to be modified.
     * @param passedPart The part object selected to be modified.
     */
    public void displaySelectedPart(Integer passedIndex, Part passedPart) {
        modifyIndex = passedIndex;
        idField.setText(Integer.toString(passedPart.getId()));
        nameField.setText(passedPart.getName());
        stockField.setText(Integer.toString(passedPart.getStock()));
        priceField.setText(NumberFormat.getCurrencyInstance().format(passedPart.getPrice()));
        maxField.setText(Integer.toString(passedPart.getMax()));
        minField.setText(Integer.toString(passedPart.getMin()));
        if (passedPart instanceof InHouse) {
            sourceField.setText(Integer.toString(((InHouse) passedPart).getMachineId()));
            outsourcedRadioButton.setDisable(true);
        }
        else {
            outsourcedRadioButton.setSelected(true);
            outsourcedRadioButtonSelected();
            sourceField.setText(((Outsourced) passedPart).getCompanyName());
            inHouseRadioButton.setDisable(true);
        }
    }
    /**
     * Method called when the inHouseRadioButton is clicked.
     * Sets the sourceLabel to display Machine ID.
     */
    public void inHouseRadioButtonSelected() {
        sourceLabel.setText("Machine ID");
    }
    /**
     * Method called when the outsourcedRadioButton id clicked.
     * sets the sourceLabel to display Company Name.
     */
    public void outsourcedRadioButtonSelected() {
        sourceLabel.setText("Company Name");
    }
    /**
     * Method called when the saveButton is clicked.
     * Passes data from all the fields to the validate part method.
     * If part returned is valid the it updates the part in inventory and returns to the main stage.
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
            Inventory.updatePart(getModifyIndex(),validPart);
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
                && minField.getText().isEmpty()
                && sourceField.getText().isEmpty())
        {
            ((Stage) cancelButton.getScene().getWindow()).close();
        }
        else
            {
            Alert alert = (new Alert(Alert.AlertType.CONFIRMATION,"Part changes will not be saved: Continue?",ButtonType.OK));
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) ((Stage) cancelButton.getScene().getWindow()).close();
            }
    }
    /**
     * Creates the toggleGroup for the radio buttons. This sets it so only one can be selected at a time.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ToggleGroup toggleGroup = new ToggleGroup();
        this.inHouseRadioButton.setToggleGroup(toggleGroup);
        this.outsourcedRadioButton.setToggleGroup(toggleGroup);
        this.inHouseRadioButton.setSelected(true);

    }


}
