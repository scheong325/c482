package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * It displays Product class and associated parts.
 *
 *
 */
public class Product {

    /**
     * A list of associated parts.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * This is the constructor of the product.
     */
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * The getter for the ID.
     *
     * @return Returns ID.
     */
    public int getId() {
        return id;
    }

    /**
     * The setter of the ID.
     *
     * @param id The ID of the product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getter of the product name.
     *
     * @return Returns product name.
     */
    public String getName() {
        return name;
    }

    /**
     * The setter of the product name.
     *
     * @param name The product name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getter of the product price.
     *
     * @return Returns product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * The setter of the product price.
     *
     * @param price The product price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * The getter of the stock.
     *
     * @return Returns the inventory level.
     */
    public int getStock() {
        return stock;
    }

    /**
     * The setter of the stock.
     *
     * @param stock The inventory level.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * The getter of the minimum stock.
     *
     * @return Returns the minimum amount of stock.
     */
    public int getMin() {
        return min;
    }

    /**
     * The setter of the minimum stock.
     *
     * @param min The minimum stock.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * The getter of the maximum stock.
     *
     * @return Returns the maximum amount of stock.
     */
    public int getMax() {
        return max;
    }

    /**
     * The setter of the maximum stock.
     *
     * @param max The maximum stock.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds part to the associated part list.
     *
     * @param part The part to be added.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes the chosen part from the associated parts list.
     *
     * @param selectedAssociatedPart The part to be removed.
     *
     * @return returns true if the selected part is removed; otherwise, returns false.
     */
    public boolean deleteAssociatedParts(Part selectedAssociatedPart) {

        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }

    /**
     *Gets the list of associated parts.
     *
     * @return The associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }

}

