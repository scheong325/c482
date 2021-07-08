package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This displays the inventory data of parts and products.
 *
 *
 */
public class Inventory {

    /**
     * A list of all parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * A list of all products.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Object for part ID.
     */
    private static int partId = 0;

    /**
     * Object for product ID.
     */
    private static int productId = 0;

    /**
     * Generates part ID.
     *
     * @return Returns part ID.
     */
    public static int getNewPartsId() {
        return ++partId;
    }

    /**
     * Generates product ID.
     *
     * @return Returns product ID.
     */
    public static int getNewProductsId() {
        return ++productId;
    }

    /**
     * It adds a new part to the inventory.
     *
     * @param newPart The new part to be added.
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * It adds a new product to the inventory.
     *
     * @param newProduct The new product to be added.
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * Searches the parts through partID.
     *
     * @param partId The part ID.
     *
     * @return Returns part if part ID is matched, otherwise, it will return null.
     */
    public static Part lookupPart(int partId) {

        Part partFound = null;
        for (Part part : allParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }
        return partFound;

    }

    /**
     * Searches the products through product ID.
     *
     * @param productId The product ID.
     *
     * @return Returns product if product ID is matched, otherwise, it will return null.
     */
    public static Product lookupProduct(int productId) {

        Product productFound = null;
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                productFound = product;
            }
        }
        return productFound;

    }

    /**
     * Searches the part list by the part name.
     *
     * @param partName The part name.
     *
     * @return Returns the matched part.
     */
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> partFound = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partFound.add(part);
            }
        }
        return partFound;
    }

    /**
     * Searches the product list by the product name.
     *
     * @param productName The product name.
     *
     * @return Returns the matched product.
     */
    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> productFound = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().equals(productName))
                productFound.add(product);
        }
        return productFound;
    }

    /**
     * Updates part.
     *
     * @param index The index of the part.
     *
     * @param selectedPart The part to be updated.
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * Updates product.
     *
     * @param index The index of the product.
     *
     * @param newProduct The product to be updated.
     */
    public static void updateProduct(int index, Product newProduct) {

        allProducts.set(index, newProduct);
    }

    /**
     * Deletes the part.
     *
     * @param selectedPart The chosen part to be removed.
     *
     * @return Returns boolean.
     */
    public static boolean deletePart(Part selectedPart) {

        for (Part part : allParts) {
            if (allParts.contains(selectedPart)) {
                return allParts.remove(selectedPart);
            }
        }
        return false;
    }

    /**
     * Deletes the product.
     *
     * @param selectedProduct The chosen product to be removed.
     *
     * @return Returns boolean.
     */
    public static boolean deleteProduct(Product selectedProduct) {

        for (Product product : allProducts) {
            if (allProducts.contains(selectedProduct)) {
                return allProducts.remove(selectedProduct);
            }
        }
        return false;
    }


    /**
     * Gets the list of part.
     *
     * @return Returns the list of part.
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * Gets the list of product.
     *
     * @return Returns the list of product.
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }
}



