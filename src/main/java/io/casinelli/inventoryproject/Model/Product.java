package io.casinelli.inventoryproject.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product to store data used by inventory application
 * @author Louis Casinelli Jr
 * @version 1.00
 */
public class Product{
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(ObservableList<Part> associatedParts, int id, String name, double price, int stock, int min, int max) {
        this.associatedParts = associatedParts;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * <p> Set Product ID</p>
     * @param id
     */
    public void setId(int id) { this.id = id; }

    /**
     * <p> Set Product name</p>
     * @param name
     */
    public void setName(String name) { this.name = name; }

    /**
     * <p> Set Product price</p>
     * @param price
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * <p> Set Product stock amount</p>
     * @param stock
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * <p> Set Product minimum quantity</p>
     * @param min
     */
    public void setMin(int min) { this.min = min; }

    /**
     * <p> Set maximum quntity</p>
     * @param max
     */
    public void setMax(int max) { this.max = max; }

    /**
     * @return product ID
     */
    public int getId() { return id; }

    /**
     * @return product name
     */
    public String getName() { return name; }

    /**
     * @return on-hand stock quantity
     */
    public int getStock() { return stock; }

    /**
     * @return product price
     */
    public double getPrice() { return price; }

    /**
     * @return minimum quantity
     */
    public int getMin() { return min; }

    /**
     * @return maximum quantity
     */
    public int getMax() { return max; }

    public void addAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.add(selectedAssociatedPart);
    }

    /**
     * @param selectedAssociatedPart part to be deleted
     * @return true if deleted part false if not found
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }


    /**
     * @return observable list of parts contained within this product
     */
    public ObservableList<Part> getAssociatedParts() { return associatedParts; }
}