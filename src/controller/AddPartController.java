package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class provides the logic for the add part form.
 *
 *
 */
public class AddPartController implements Initializable {

    /**
     * The declaration of stage and scene for loading controllers passing root.
     */
    Stage stage;
    Parent scene;

    /**
     * InHouse ratio button.
     */
    @FXML
    private RadioButton inHouseRBtn;

    /**
     * Outsourced ratio button.
     */
    @FXML
    private RadioButton outsourcedRBtn;

    /**
     * The toggle group for radio buttons.
     */
    @FXML
    private ToggleGroup addPartTG;

    /**
     * Text field for adding part ID.
     */
    @FXML
    private TextField addPartIdTxt;

    /**
     * Text field for adding part name.
     */
    @FXML
    private TextField addPartNameTxt;

    /**
     * Text field for adding part inventory.
     */
    @FXML
    private TextField addPartInventoryTxt;

    /**
     * Text field for adding part price.
     */
    @FXML
    private TextField addPartPriceTxt;

    /**
     * Text field for adding maximum parts.
     */
    @FXML
    private TextField addPartMaxTxt;

    /**
     * Text field for adding part machine ID.
     */
    @FXML
    private TextField addPartMachineIdTxt;

    /**
     * Label for machine ID or company name.
     */
    @FXML
    private Label machineCompLabel;

    /**
     * Text field for adding minimum parts.
     */
    @FXML
    private TextField addPartMinTxt;

    /**
     * Set machineCompLabel to machine id.
     *
     * @param event In-house radio button.
     */
    @FXML
    void inHouseRBtnOnAction(ActionEvent event) {
        machineCompLabel.setText("Machine ID");
    }

    /**
     * Set machineCompLabel to company name.
     *
     * @param event Outsourced radio button.
     */
    @FXML
    void outsourcedRBtnOnAction(ActionEvent event) {
        machineCompLabel.setText("Company name");
    }

    /**
     *Returns to the main screen.
     *
     * @param event Cancel button.
     *
     * @throws IOException Exception from FXMLLoader.
     */
    @FXML
    void onActionDisplayMain(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Warning");
        alert.setContentText("Do you want to return to the main screen? Your information will not be saved.");
        Optional<ButtonType> result = alert.showAndWait();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainController.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Adds and saves new parts and loads MainController if it does not violates the conditions,
     * otherwise, dialogue box will pop up.
     *
     * @param event Save button.
     *
     * @throws IOException Exception from FXMLLoader.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

       try {

           int id = 0;
           String name = addPartNameTxt.getText();
           int inventory = Integer.parseInt(addPartInventoryTxt.getText());
           double price = Double.parseDouble(addPartPriceTxt.getText());
           int max = Integer.parseInt(addPartMaxTxt.getText());
           int min = Integer.parseInt(addPartMinTxt.getText());
           int machineId;
           String companyName;

           if(!(inHouseRBtn.isSelected()) && !(outsourcedRBtn.isSelected())) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText("Error");
               alert.setContentText("No button is selected.");
               alert.showAndWait();
               return;
           }

           if (min > max) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText("Error");
               alert.setContentText("Min cannot be larger than max.");
               alert.showAndWait();
               return;
           }

           if (inventory > max) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText("Error");
               alert.setContentText("Inventory cannot be more than max.");
               alert.showAndWait();
               return;
           }

           if (min > inventory) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText("Error");
               alert.setContentText("Inventory cannot be less than min.");
               alert.showAndWait();
               return;

           } else {
               if (inHouseRBtn.isSelected()) {
                   machineId = Integer.parseInt(addPartMachineIdTxt.getText());
                   InHouse inHousePart = new InHouse(id, name, price, inventory, max, min, machineId);
                   inHousePart.setId(Inventory.getNewPartsId());
                   Inventory.addPart(inHousePart);
               }
               if (outsourcedRBtn.isSelected()) {
                   companyName = addPartMachineIdTxt.getText();
                   Outsourced  outsourcedPart = new Outsourced(id, name, price, inventory, max, min, companyName);
                   outsourcedPart.setId(Inventory.getNewPartsId());
                   Inventory.addPart(outsourcedPart);
                   }
               }
           stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
           scene = FXMLLoader.load(getClass().getResource("/view/MainController.fxml"));
           stage.setScene(new Scene(scene));
           stage.show();

       }

        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("The input value is invalid or empty.");
            alert.showAndWait();
            return;
        }

    }

    /**
     * Initializes controller.
     *
     * @param url The location to resolve the root object.
     *
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
