package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * This Inventory Management program is developed for managing the company inventory which contains products and parts.
 *
 *
 */
public class Main extends Application {

    /**
     * The start method creates the FXML stage and loads the initial scene.
     *
     * FUTURE ENHANCEMENT Login info and password UI should be implemented before entering the program. This can enhance the
     * program security. Besides, account users can have different authorities to manage the inventory.
     *
     * @param primaryStage Primary stage of the program.
     *
     * @throws Exception Exception from FXMLLoader.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainController.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 950, 500));
        primaryStage.show();
    }

    /**
     * This method fills the part and product table with test data.
     *
     * @param args Part and product data.
     */
    public static void main(String[] args) {

        //Sample parts
        int partId = Inventory.getNewPartsId();
        InHouse adaptor = new InHouse(partId, "Adaptor", 49.99, 26,10,60, 111);

        partId = Inventory.getNewPartsId();
        InHouse creviceTool = new InHouse(partId, "Crevice tool", 59.99, 20,8,40, 112);

        Inventory.addPart(adaptor);
        Inventory.addPart(creviceTool);

        // Sample products
        int id = Inventory.getNewProductsId();
        Product cordlessVacuum = new Product(id, "Cordless vacuum", 599.99, 50, 20, 100);
        cordlessVacuum.addAssociatedPart(adaptor);
        cordlessVacuum.addAssociatedPart(creviceTool);

        id = Inventory.getNewProductsId();
        Product uprightVacuum = new Product(id, "Upright vacuum", 399.99, 45, 25, 90 );
        uprightVacuum.addAssociatedPart(creviceTool);

        Inventory.addProduct(cordlessVacuum);
        Inventory.addProduct(uprightVacuum);
        launch(args);

    }
}
