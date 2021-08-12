package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * This is the main class that includes the entire program.
 * The program is a basic Inventory management system, it does not save anything to a permanent database.
 * @author Greg Layton
 */
public class InventoryProgram extends Application {

    /**
     * This is start method that calls the beginning of the program
     * @param primaryStage This is the main stage that will be called whenever the program is executed.
     * @throws Exception Throws an exception in case the .fxml is not found.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view_controller/InventoryManagementScreen.fxml"));
        primaryStage.setTitle("C482 Greg Layton");
        primaryStage.setOnCloseRequest(e-> {
            e.consume();
            closeProgram();
        });
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * This calls a custom confirmation dialog if the program is requested to be closed from the window exit instead of the "Exit" button.
     */
    private void closeProgram() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you wish to close the program?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) System.exit(0);
    }

    /**
     * This is the Main method.
     * Sample parts, products made of parts have been included.
     */
    public static void main(String[] args) {

        Part partOne = new InHouse(Inventory.getPartsIdGenerator().getAndIncrement(),"Thing I",1.23,12,5,20,42);
        Inventory.addPart(partOne);
        Part partTwo = new InHouse(Inventory.getPartsIdGenerator().getAndIncrement(),"Thing II",2.45,10,5,15,43);
        Inventory.addPart(partTwo);
        Inventory.addPart(new InHouse(Inventory.getPartsIdGenerator().getAndIncrement(),"Thing III",6.78,8,5,10,44));
        Part outsourceOne = new Outsourced(Inventory.getPartsIdGenerator().getAndIncrement(),"Widget I",1.23,12,5,20,"Acme");
        Inventory.addPart(outsourceOne);
        Part outsourceTwo = new Outsourced(Inventory.getPartsIdGenerator().getAndIncrement(),"Widget II",2.45,10,5,15,"MegaCorp");
        Inventory.addPart(outsourceTwo);
        Inventory.addPart(new Outsourced(Inventory.getPartsIdGenerator().getAndIncrement(),"Widget III",6.78,8,5,10,"Amazon"));
        Product productOne = new Product(Inventory.getProductIdGenerator().getAndIncrement(),"Thing-a-ma-jig",19.99,6,1,12);
        Inventory.addProduct(productOne);
        productOne.addAssociatedPart(partOne);
        productOne.addAssociatedPart(partTwo);
        Product productTwo = new Product(Inventory.getProductIdGenerator().getAndIncrement(),"Wild wild widgets",24.99,2,0,5);
        Inventory.addProduct(productTwo);
        productTwo.addAssociatedPart(outsourceOne);
        productTwo.addAssociatedPart(outsourceTwo);

        launch(args);
    }
}
