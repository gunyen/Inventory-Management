package controller;
/**
 * @author John Nguyen 2/28/2022
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static main.Main.navigate;

public class MainScreenController implements Initializable {

    public static Part partData;
    public static Product productData;
    public TextField partsSearchTextField;
    public TextField productSearchTextField;
    public Label productPartLabel;

    /**
     * @param actionEvent the actionEvent to navigate to AddPartScreen.fxml
     * @throws IOException
     */
    public void onClickAddPart(ActionEvent actionEvent) throws IOException {
        navigate(actionEvent, "AddPartScreen.fxml");
    }

    /**
     * @param actionEvent the actionEvent to navigate to ModifyPartScreen.fxml
     * @throws IOException
     */
    public void onClickModifyPart(ActionEvent actionEvent) throws IOException {
        if (partsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Part Modify Warning");
            alert.setContentText("Part must be selected to be able to modify.");
            alert.showAndWait();
        } else {
            getPartData(partsTableView.getSelectionModel().getSelectedItem());
            navigate(actionEvent, "ModifyPartScreen.fxml");
        }
    }

    /**
     *
     */
    public void onClickDeletePart() {
        if (partsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Part Deletion Warning");
            alert.setContentText("Part must be selected to be able to delete.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
            }
        }

    }

    /**
     *
     */
    public void onEnterPartsSearch() {
        try {
            partsTableView.setItems(Inventory.lookupPart(partsSearchTextField.getText()));
            if (Inventory.lookupPart(partsSearchTextField.getText()).isEmpty()) {
                partsTableView.setItems(Inventory.getAllParts());
                partsTableView.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(partsSearchTextField.getText())));
            }
        } catch (NumberFormatException e) {
            partsTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
     *
     */
    public void onClickPartSearch() {
        try {
            partsTableView.setItems(Inventory.lookupPart(partsSearchTextField.getText()));
            if (Inventory.lookupPart(partsSearchTextField.getText()).isEmpty()) {
                partsTableView.setItems(Inventory.getAllParts());
                partsTableView.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(partsSearchTextField.getText())));
            }
        } catch (NumberFormatException e) {
            partsTableView.setItems(Inventory.getAllParts());
        }
    }


    /**
     * @param part the part to extract data
     * @return the part
     */
    public Part getPartData(Part part) {
        partData = part;
        return part;
    }

    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partsInventoryLevel;
    @FXML
    private TableColumn<Part, Integer> partsPartID;
    @FXML
    private TableColumn<Part, String> partsPartName;
    @FXML
    private TableColumn<Part, String> partsUnit;


    /**
     * @param actionEvent the actionEvent to navigate to AddProductScreen.fxml
     * @throws IOException
     */
    public void onClickAddProducts(ActionEvent actionEvent) throws IOException {
        navigate(actionEvent, "AddProductScreen.fxml");
    }

    /**
     * @param actionEvent the actionEvent to navigate to ModifyProductScreen.fxml
     * @throws IOException
     */
    public void onClickModifyProduct(ActionEvent actionEvent) throws IOException {
        if (productsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Product Modify Warning");
            alert.setContentText("Product must be selected to be able to modify.");
            alert.showAndWait();
        } else {
            getProductData(productsTableView.getSelectionModel().getSelectedItem());
            navigate(actionEvent, "ModifyProductScreen.fxml");
        }
    }

    /**
     *
     */
    public void onClickDeleteProduct() {
        if (productsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Product Deletion Warning");
            alert.setContentText("Product must be selected to be able to delete.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean restriction = false;
                Product product = productsTableView.getSelectionModel().getSelectedItem();
                for (Part part : Inventory.getAllParts()) {
                    if (product.getAllAssociatedParts().contains(part)) {
                        productPartLabel.setText("This product contains parts.");
                        restriction = true;
                    }
                }
                if (!restriction) {
                    productPartLabel.setText("");
                    Inventory.deleteProduct(productsTableView.getSelectionModel().getSelectedItem());
                }
            }
        }
    }

    /**
     *
     */
    public void onEnterSearchProduct() {
        try {
            productsTableView.setItems(Inventory.lookupProduct(productSearchTextField.getText()));
            if (Inventory.lookupProduct(productSearchTextField.getText()).isEmpty()) {
                productsTableView.setItems(Inventory.getAllProducts());
                productsTableView.getSelectionModel().select(Inventory.lookupProduct(Integer.parseInt(productSearchTextField.getText())));
            }
        } catch (NumberFormatException e) {
            productsTableView.setItems(Inventory.getAllProducts());
        }
    }

    /**
     *
     */
    public void onClickProductSearch() {
        try {
            productsTableView.setItems(Inventory.lookupProduct(productSearchTextField.getText()));
            if (Inventory.lookupProduct(productSearchTextField.getText()).isEmpty()) {
                productsTableView.setItems(Inventory.getAllProducts());
                productsTableView.getSelectionModel().select(Inventory.lookupProduct(Integer.parseInt(productSearchTextField.getText())));
            }
        } catch (NumberFormatException e) {
            productsTableView.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * @param product the product to extract data
     * @return the product
     */
    public Product getProductData(Product product) {
        productData = product;
        return product;
    }

    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productsInventoryLevel;
    @FXML
    private TableColumn<Product, Integer> productsPartID;
    @FXML
    private TableColumn<Product, String> productsPartName;
    @FXML
    private TableColumn<Product, Double> productsUnit;

    /**
     *
     */
    public void onClickExit() {
        System.exit(0);
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());

        partsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));

        productsTableView.setItems(Inventory.getAllProducts());

        productsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));

    }
}
