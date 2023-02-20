package io.casinelli.inventoryproject.Model;

/**
 *  Inventory Object to contain all parts and products on hand and
 *  provide access to appropriate data
 *  @author Louis Casinelli Jr
 *  @version 1.00
 */



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {
    /**
     * Variable used to keep track of next new port ID
     */
    private static int nextPartID;
    /**
     * Variable used to keep track of next new productID
     */
    private static int nextProductID;
    /**
     * Initialize array for all parts
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Initialize array for all products
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * @return the next part ID number
     */
    public static int getNextPartID() { return ++nextPartID; }

    /**
     * @return the next product ID number
     */
    public static int getNextProductID() { return ++nextProductID; }

    /**
     * @param newPart part ot add to allParts ArrayList
     * Adds part to allParts ArrayList
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct part ot add to allProducts ArrayList
     * Adds product to allProducts ArrayList
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Find part in ArrayList by ID
     * @param partID ID of part to find in ArrayList of Parts
     * @return found part or null
     */
    public static Part lookupPart(int partID) {
        Part returnPart = null;
        for (Part p : allParts) {
            if (p.getId() == partID) {
                returnPart = p;
            }
        }
        return returnPart;
    }

    /**
     * Find Product in ArrayList by ID
     * @param productID ID of product to find in ArrayList of Products
     * @return found Product or null
     */
    public static Product lookupProduct(int productID) {
        Product returnProduct = null;
        for(Product prod : allProducts) {
            if(prod.getId() == productID) {
                returnProduct = prod;
            }
        }
        return returnProduct;
    }

    /**
     * Find all Parts with names containing the provided string of characters
     * @param partName String to match part names against
     * @return ObservableList of parts watching the provided string
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> returnList = FXCollections.observableArrayList();
        for(Part p : allParts) {
            if (p.getName().contains(partName)){
                returnList.add(p);
            }
        }
        return returnList;
    }

    /**
     * Find all Products with names containing the provided string of characters
     * @param productName String to match Product names against
     * @return ObservableList of Products matching the provided string
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> returnList = FXCollections.observableArrayList();
        for(Product prod : allProducts) {
            if(prod.getName().contains(productName)){
                returnList.add(prod);
            }
        }
        return returnList;
    }

    /**
     * Replace Part at index provided with Part provided
     * @param index of Part to update
     * @param selectedPart Part to replace part at provided index
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart); }

    /**
     * Replace Product at index provided with Product provided
     * @param index of Product to Update
     * @param selectedProduct Product to replace Product at provided index
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct); }

    /**
     * Remove Part provided from ArrayList
     * @param selectedPart Part to remove
     * @return boolean indicating success or failure of removal
     */
    public static boolean deletePart(Part selectedPart) {
       if(allParts.contains(selectedPart)){
           allParts.remove(selectedPart);
           return true;
        } else return false;
    }

    /**
     * Remove provided Product from ArrayList
     * @param selectedProduct Product to remove
     * @return boolean indicating success or failure of removal
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else return false;
    }

    /**
     * @return allParts ArrayList
     */
    public static ObservableList<Part> getAllParts() { return allParts; }

    /**
     * @return allProducts ArrayList
     */
    public static ObservableList<Product> getAllProducts() { return allProducts; }
}

