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
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class provides the logic for the modify part form.
 *
 *
 */
public class ModifyPartController implements Initializable {

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
     * Text field for modify part ID.
     */
    @FXML
    private TextField modifyPartIdTxt;

    /**
     * Text field for modify part name.
     */
    @FXML
    private TextField modifyPartNameTxt;

    /**
     * Text field for modify part inventory.
     */
    @FXML
    private TextField modifyPartInvTxt;

    /**
     * Text field for modify part price.
     */
    @FXML
    private TextField modifyPartPriceTxt;

    /**
     * Text field for modify maximum part.
     */
    @FXML
    private TextField modifyPartMaxTxt;

    /**
     * Text field for modify machine ID.
     */
    @FXML
    private TextField ModifyPartMachineIdTxt;

    /**
     * Text field for modify minimum part.
     */
    @FXML
    private TextField modifyPartMinTxt;

    /**
     * Label for machine ID or company name.
     */
    @FXML
    private Label machineCompLabel;

    /**
     * The object selected in the MainController.
     */
    public Part chosenPart;

    /**
     * Set machineCompLabel as Machine ID when inHouse button is selected.
     *
     * @param event Radio button.
     */
    @FXML
    void inHouseRBtnOnAction(ActionEvent event) {

        machineCompLabel.setText("Machine ID");
    }

    /**
     * Set machineCompLabel as Company name when outsourced button is selected.
     *
     * @param event Radio button.
     */
    @FXML
    void outsourcedRBtnOnAction(ActionEvent event) {

        machineCompLabel.setText("Company name");
    }

    /**
     * Display a confirmation dialogue before returns to main screen.
     *
     * @param event Cancel button action.
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
     * Modifies and saves parts and loads MainController if it does not violates the conditions,
     * otherwise, dialogue box will pop up.
     *
     * @param event Save button.
     *
     * @throws IOException Exception from FXMLLoader.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {

            int id = Integer.parseInt(modifyPartIdTxt.getText());
            String name = modifyPartNameTxt.getText();
            int inventory = Integer.parseInt(modifyPartInvTxt.getText());
            double price = Double.parseDouble(modifyPartPriceTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());
            int machineId;
            String companyName;

            if (!inHouseRBtn.isSelected() && !outsourcedRBtn.isSelected()) {
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

                    machineId = Integer.parseInt(ModifyPartMachineIdTxt.getText());
                    InHouse newInHouse = new InHouse(id, name, price, inventory, max, min, machineId);
                    Inventory.addPart(newInHouse);
                    Inventory.deletePart(chosenPart);
                }

                if (outsourcedRBtn.isSelected()) {
                    companyName = ModifyPartMachineIdTxt.getText();
                    Outsourced newOutsourced = new Outsourced(id, name, price, inventory, max, min, companyName);
                    Inventory.addPart(newOutsourced);
                    Inventory.deletePart(chosenPart);
                }
            }
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainController.fxml"));
            stage.setScene(new Scene(scene));
            stage.showAndWait();

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
     * Initializes controller and populate the text field from MainController.
     *
     * LOGICAL ERROR: I linked the AddPartController to the modifyPartController.fxml.
     * Therefore, the selected part in the main controller was unable to transfer to
     * the modifyPartController. This can be simply fixed by altering the controller
     * in the fxml file.
     *
     * @param url The location to resolve the root object.
     *
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        chosenPart = MainController.getSelectedModifyPart();

        if (chosenPart instanceof InHouse) {
            inHouseRBtn.setSelected(true);
            machineCompLabel.setText("Machine ID");
            ModifyPartMachineIdTxt.setText(String.valueOf(((InHouse) chosenPart).getMachineId()));
        }

        if (chosenPart instanceof Outsourced) {
            outsourcedRBtn.setSelected(true);
            machineCompLabel.setText("Company Name");
            ModifyPartMachineIdTxt.setText (((Outsourced) chosenPart).getCompanyName());
        }

        modifyPartIdTxt.setText(String.valueOf(chosenPart.getId()));
        modifyPartNameTxt.setText(chosenPart.getName());
        modifyPartInvTxt.setText(String.valueOf(chosenPart.getStock()));
        modifyPartPriceTxt.setText(String.valueOf(chosenPart.getPrice()));
        modifyPartMaxTxt.setText(String.valueOf(chosenPart.getMax()));
        modifyPartMinTxt.setText(String.valueOf(chosenPart.getMin()));
    }
}
