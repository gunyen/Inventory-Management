package model;
/**
 * @author John Nguyen 2/28/2022
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart the newPart to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct the newProduct to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partId the partId to lookup
     * @return the part
     */
    public static Part lookupPart(int partId) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * @param productId the productId to lookup
     * @return the product
     */
    public static Product lookupProduct(int productId) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * @param partName the partName to lookup
     * @return the filteredParts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().contains(partName)) {
                filteredParts.add(part);
            }
        }

        return filteredParts;
    }

    /**
     * @param productName the productName to lookup
     * @return the filteredProducts
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().contains(productName)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    /**
     * @param index the index to update
     * @param selectedPart the selectedPart to update
     */
    public static void updatePart(int index, Part selectedPart) {
        int partId = -1;

        for (Part part : Inventory.getAllParts()) {
            partId++;
            if (part.getId() == index) {
                Inventory.getAllParts().set(partId, selectedPart);
            }
        }
    }

    /**
     * @param index the index to update
     * @param newProduct the newProduct to update
     */
    public static void updateProduct(int index, Product newProduct) {
        int productId = -1;

        for (Product product : Inventory.getAllProducts()) {
            productId++;
            if (product.getId() == index) {
                Inventory.getAllProducts().set(productId, newProduct);
            }
        }
    }

    /**
     * @param selectedPart the selectPart to delete
     * @return boolean to deletePart
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part part : Inventory.getAllParts()) {
            if (part == selectedPart) {
                allParts.remove(selectedPart);
                return true;
            }
        }
        return false;
    }

    /**
     * @param selectedProduct the selectedProduct to delete
     * @return boolean to deleteProduct
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : Inventory.getAllProducts()) {
            if (product == selectedProduct) {
                allProducts.remove(selectedProduct);
                return true;
            }
        }
        return false;
    }

    /**
     * @return the allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return the allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
