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
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class provides the logic for the modify product form.
 *
 *
 */
public class ModifyProductController implements Initializable {

    /**
     * The declaration of stage and scene for loading controllers passing root.
     */
    Stage stage;
    Parent scene;

    /**
     * Text field for searching products.
     */
    @FXML
    private TextField modifyProductSearchTxt;

    /**
     * Table view for part.
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * Part ID column in part table view.
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    /**
     * Part name column in part table view.
     */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /**
     * Part inventory column in part table view.
     */
    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    /**
     * Part price column in part table view.
     */
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    /**
     * Table view for associated table view.
     */
    @FXML
    private TableView<Part> associatedPartTableView;

    /**
     * Associated part ID column in associated table view.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;

    /**
     * Associated part name column in associated table view.
     */
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    /**
     * Associated part inventory column in associated table view.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryCol;

    /**
     * Associated part price column in associated table view.
     */
    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;

    /**
     * Text field for modify product ID.
     */
    @FXML
    private TextField modifyProductIdTxt;

    /**
     * Text field for modify product name.
     */
    @FXML
    private TextField modifyProductNameTxt;

    /**
     * Text field for modify product inventory.
     */
    @FXML
    private TextField modifyProductInvTxt;

    /**
     * Text field for modify product price.
     */
    @FXML
    private TextField modifyProductPriceTxt;

    /**
     * Text field for modify maximum product.
     */
    @FXML
    private TextField modifyProductMaxTxt;

    /**
     * Text field for modify minimum product.
     */
    @FXML
    private TextField modifyProductMinTxt;

    /**
     * A list of associated parts.
     */
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * A list of parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * The object selected in the MainController.
     */
    public Product chosenProduct;

    /**
     * Adds associated parts to the product.
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
        }
            else {
                associatedParts.add(chosenPart);
                associatedPartTableView.setItems(associatedParts);
        }
    }

    /**
     * Removes associated parts.
     *
     * @param event Remove button.
     */
    @FXML
    void onActionRemove(ActionEvent event) {
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
     * Modifies and saves products and loads MainController if it does not violates the conditions,
     * otherwise, dialogue box will pop up.
     *
     * @param event Save button.
     *
     * @throws IOException Exception from FXMLLoader.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
      try {

          int id = Integer.parseInt(modifyProductIdTxt.getText());
          String name = modifyProductNameTxt.getText();
          int inventory = Integer.parseInt(modifyProductInvTxt.getText());
          double price = Double.parseDouble(modifyProductPriceTxt.getText());
          int max = Integer.parseInt(modifyProductMaxTxt.getText());
          int min = Integer.parseInt(modifyProductMinTxt.getText());


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
              }
              Inventory.addProduct(newProduct);
              Inventory.deleteProduct(chosenProduct);
              stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
              scene = FXMLLoader.load(getClass().getResource("/view/MainController.fxml"));
              stage.setScene(new Scene(scene));
              stage.show();

          }
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
     * Initializes controller and populate the text field from MainController.
     *
     * @param url The location to resolve the root object.
     *
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Populates the part table view.
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        // Filters table with part ID or name.
        allParts = Inventory.getAllParts();
        FilteredList<Part> partFilteredList = new FilteredList<>(allParts, b -> true);
        modifyProductSearchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
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

        //Transfers data from MainController to Modify Product form text field.
        chosenProduct = MainController.getSelectedModifyProduct();
        associatedParts = chosenProduct.getAllAssociatedParts();
        modifyProductIdTxt.setText(String.valueOf(chosenProduct.getId()));
        modifyProductNameTxt.setText(chosenProduct.getName());
        modifyProductInvTxt.setText(String.valueOf(chosenProduct.getStock()));
        modifyProductPriceTxt.setText(String.valueOf(chosenProduct.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(chosenProduct.getMax()));
        modifyProductMinTxt.setText(String.valueOf(chosenProduct.getMin()));

        //Populates the associated part table view.
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartTableView.setItems(associatedParts);
    }
}
