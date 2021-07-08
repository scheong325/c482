package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
 * The controller class provides the logic for the main form.
 *
 *
 */
public class MainController implements Initializable {

    /**
     * The declaration of stage and scene for loading controllers passing root.
     */
    Stage stage;
    Parent scene;

    /**
     * Table view for the part.
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
     * Part inventory level column in part table view.
     */
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelCol;

    /**
     * Part price column in part table view.
     */
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    /**
     * Text field for searching part.
     */
    @FXML
    private TextField partSearchTxt;

    /**
     * Table view for product.
     */
    @FXML
    private TableView<Product> productTableView;

    /**
     * Product ID column in product table view.
     */
    @FXML
    private TableColumn<Product, Integer> productIdCol;

    /**
     * Product name column in product table view.
     */
    @FXML
    private TableColumn<Product, String> productNameCol;

    /**
     * Product inventory level column in product table view.
     */
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelCol;

    /**
     * Product price column in product table view.
     */
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    /**
     * Text field for searching product.
     */
    @FXML
    private TextField productSearchTxt;

    /**
     * Object selected by the user in the tableview.
     */
    private static Part selectedModifyPart;

    /**
     * The getter of modifyPart.
     *
     * @return Returns modifyPart.
     */
    public static Part getSelectedModifyPart() {
        return selectedModifyPart;
    }

    /**
     * Object selected by the user in the tableview.
     */
    private static Product selectedModifyProduct;

    /**
     * The getter of modifyProduct.
     *
     * @return Returns modifyProduct.
     */
    public static Product getSelectedModifyProduct() {
        return selectedModifyProduct;
    }

    /**
     * A list of parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * A list of products.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add parts to the main screen.
     *
     * @param event Add part button.
     *
     * @throws IOException Exception from FXMLLoader.
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartController.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Add product to the main screen.
     *
     * @param event Add product button.
     *
     * @throws IOException Exception from FXMLLoader.
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductController.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Delete part selected from the table.
     *
     * An error message will be displayed if no part is selected and confirmation message before an item is deleted.
     *
     * @param event Part delete button action.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Parts");
            errorAlert.setHeaderText("Warning");
            errorAlert.setContentText("Part not selected");
            errorAlert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Parts");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();


            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }

    }

    /**
     * Delete product selected from the table.
     *
     * An error message will be displayed if no product is selected and confirmation message before an item is deleted.
     *
     * An error message will be displayed that a product cannot be deleted if it has associated parts.
     *
     * @param event Product delete button action.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) throws IOException {

        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Products");
            errorAlert.setHeaderText("Warning");
            errorAlert.setContentText("Product not selected");
            errorAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Products");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
                if (associatedParts.size() >= 1) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Delete");
                    errorAlert.setContentText("You cannot delete a product with associated parts");
                    errorAlert.showAndWait();
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }

    /**
     * Modify part in the table.
     *
     * An error message will display if no part is selected.
     *
     * @param event Modify part button action
     *
     * @throws IOException Exception from FXMLLoader.
     */
    @FXML
    void onActionModifyPart (ActionEvent event) throws IOException {

        selectedModifyPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedModifyPart == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Parts");
            errorAlert.setHeaderText("Warning");
            errorAlert.setContentText("Part not selected");
            errorAlert.showAndWait();
            return;
        }

        else {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartController.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * Modify product in the table.
     *
     * An error message will display if no part is selected.
     *
     * @param event Modify product button action
     *
     * @throws IOException Exception from FXMLLoader.
     */
    @FXML
    void onActionModifyProduct (ActionEvent event) throws IOException {

        selectedModifyProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedModifyProduct == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Products");
            errorAlert.setHeaderText("Warning");
            errorAlert.setContentText("Product not selected");
            errorAlert.showAndWait();
            return;

        }

        else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductController.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * Exits program.
     *
     * A confirmation dialogue box will pop up before exit the program.
     *
     * @param event Exit button.
     */
    @FXML
    void onActionExit (ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Do you want to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Initializes controller and populate the table views.
     *
     * Filters the table with part ID or name.
     *
     * @param url The location to resolve the root object.
     *
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){

        //Populate the part tableview.
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Populate the product tableview.
        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

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
                if (part.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;

            });
        });
        SortedList<Part> sortedPartData = new SortedList<>(partFilteredList);
        sortedPartData.comparatorProperty().bind(partTableView.comparatorProperty());
        partTableView.setItems(sortedPartData);

        /**
         * Search product based on the product ID or product name.
         */
        allProducts = Inventory.getAllProducts();
        FilteredList<Product> productFilteredList = new FilteredList<>(allProducts, b -> true);
        productSearchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            productFilteredList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(product.getId()).contains(newValue)) {
                    return true;
                }


                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });
        SortedList<Product> sortedProductData = new SortedList<>(productFilteredList);
        sortedProductData.comparatorProperty().bind(productTableView.comparatorProperty());
        productTableView.setItems(sortedProductData);

    }
}





