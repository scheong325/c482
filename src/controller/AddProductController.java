package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class provides the logic for the add product form.
 *
 *
 */
public class AddProductController implements Initializable {

    /**
     * The declaration of stage and scene for loading controllers passing root.
     */
    Stage stage;
    Parent scene;

    /**
     * Text field for searching parts through ID or name.
     */
    @FXML
    private TextField partSearchTxt;

    /**
     * Tableview for part.
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * Part ID column in the part table view.
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    /**
     * Part name column in the part table view.
     */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /**
     * Part inventory column in part table view.
     */
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelCol;

    /**
     * Part price column in part table view.
     */
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    /**
     * Table view for associated part.
     */
    @FXML
    private TableView<Part> associatedPartTableView;

    /**
     * Part ID column in associated part table view.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;

    /**
     * Part name column in associated part table view.
     */
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    /**
     * Part inventory column in associated part table view.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryCol;

    /**
     * Part price column in associated part table view.
     */
    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;

    /**
     * Text field for adding product name.
     */
    @FXML
    private TextField addProductNameTxt;

    /**
     * Text field for adding product inventory.
     */
    @FXML
    private TextField addProductInvTxt;

    /**
     * Text field for adding product price.
     */
    @FXML
    private TextField addProductPriceTxt;

    /**
     * Text field for adding maximum product.
     */
    @FXML
    private TextField addProductMaxTxt;

    /**
     * Text field for adding minimum product.
     */
    @FXML
    private TextField addProductMinTxt;

    /**
     * A list of associated parts.
     */
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * A list of parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Removes associated parts.
     *
     * @param event Remove button.
     */
    @FXML
    void OnActionRemoveAssociatedPart(ActionEvent event) {

        Part chosenPart = associatedPartTableView.getSelectionModel().getSelectedItem();
        if (chosenPart == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Parts");
            errorAlert.setHeaderText("Warning");
            errorAlert.setContentText("Part not selected");
            errorAlert.showAndWait();
            return;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Notice");
            alert.setContentText("Are you sure you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(chosenPart);
                associatedPartTableView.setItems(associatedParts);
            }
        }
    }

    /**
     * Add associated parts to the product.
     *
     * @param event Add button.
     */
    @FXML
    void onActionAddParts(ActionEvent event) {

        Part chosenPart = partTableView.getSelectionModel().getSelectedItem();
        if (chosenPart == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Parts");
            errorAlert.setHeaderText("Warning");
            errorAlert.setContentText("Part not selected");
            errorAlert.showAndWait();
            return;
        } else {
            associatedParts.add(chosenPart);
            associatedPartTableView.setItems(associatedParts);

        }
    }

    /**
     * Returns to the main screen with confirmation dialogue box.
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
     * Adds and saves new products and loads MainController if it does not violates the conditions,
     * otherwise, dialogue box will pop up.
     *
     * @param event Save button.
     *
     * @throws IOException Exception from FXMLLoader.
     */
    @FXML
    void onActionSave(ActionEvent event) {

        try {

            int id = 0;
            String name = addProductNameTxt.getText();
            int inventory = Integer.parseInt(addProductInvTxt.getText());
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());

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
                Product newProduct = new Product(id, name, price, inventory, min, max);
                for(Part part:associatedParts) {
                    newProduct.addAssociatedPart(part);
                    newProduct.setId(Inventory.getNewProductsId());
                }
                Inventory.addProduct(newProduct);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainController.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }

        catch(NumberFormatException | IOException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("The input value is invalid or empty.");
            alert.showAndWait();
            return;

        }
    }

    /**
     * Initializes controller and populate the table view.
     *
     * Filters the table with part ID or name.
     *
     * @param url The location to resolve the root object.
     *
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Populate the part tableview.
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        /**
         * Search part based on the part ID or part name.
         */
        allParts = Inventory.getAllParts();
        FilteredList<Part> partFilteredList = new FilteredList<>(allParts, b -> true);
        partSearchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            partFilteredList.setPredicate(part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(part.getId()).contains(newValue)) {
                    return true;
                }
                return part.getName().toLowerCase().contains(lowerCaseFilter);

            });
        });
        SortedList<Part> sortedPartData = new SortedList<>(partFilteredList);
        sortedPartData.comparatorProperty().bind(partTableView.comparatorProperty());
        partTableView.setItems(sortedPartData);

        //Populate the associated part tableview.
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartTableView.setItems(associatedParts);

    }
}
