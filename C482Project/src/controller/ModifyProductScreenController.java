package controller;
/**
 * @author John Nguyen 2/28/2022
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Inventory;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static main.Main.navigate;

public class ModifyProductScreenController implements Initializable {

    public TextField productSearchField;
    public Label exceptionLabel;
    @FXML
    private TableView<Part> partAddTableView;

    @FXML
    private TableColumn<Part, Integer> addInventoryLevel;
    @FXML
    private TableColumn<Part, Integer> addPartID;
    @FXML
    private TableColumn<Part, String> addPartName;
    @FXML
    private TableColumn<Part, Double> addPricePerUnit;

    @FXML
    private TableView<Part> partDeleteTableView;

    @FXML
    private TableColumn<Part, Integer> deleteInventoryLevel;
    @FXML
    private TableColumn<Part, Integer> deletePartID;
    @FXML
    private TableColumn<Part, String> deletePartName;
    @FXML
    private TableColumn<Part, Double> deletePricePerUnit;

    @FXML
    private TextField productIDText;
    @FXML
    private TextField productInventoryText;
    @FXML
    private TextField productMax;
    @FXML
    private TextField productMin;
    @FXML
    private TextField productNameText;
    @FXML
    private TextField productPriceText;

    /**
     *
     */
    public void onEnterPartsSearch() {
        try {
            partAddTableView.setItems(Inventory.lookupPart(productSearchField.getText()));
            if (Inventory.lookupPart(productSearchField.getText()).isEmpty()) {
                partAddTableView.setItems(Inventory.getAllParts());
                partAddTableView.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(productSearchField.getText())));
            }
        } catch (NumberFormatException e) {
            partAddTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
     *
     */
    public void onClickPartSearch() {
        try {
            partAddTableView.setItems(Inventory.lookupPart(productSearchField.getText()));
            if (Inventory.lookupPart(productSearchField.getText()).isEmpty()) {
                partAddTableView.setItems(Inventory.getAllParts());
                partAddTableView.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(productSearchField.getText())));
            }
        } catch (NumberFormatException e) {
            partAddTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
     *
     */
    public void onClickAddPart() {
        if (partAddTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Add Part Warning");
            alert.setContentText("Part must be selected to be added");
            alert.showAndWait();
        } else {
            MainScreenController.productData.addAssociatedPart(partAddTableView.getSelectionModel().getSelectedItem());
            partDeleteTableView.setItems(MainScreenController.productData.getAllAssociatedParts());
        }
    }

    /**
     *
     */
    public void onClickDeletePart() {
        if (partAddTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Part Warning");
            alert.setContentText("Part must be selected to be deleted");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                MainScreenController.productData.deleteAssociatedPart(partDeleteTableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    /**
     * @param actionEvent the actionEvent to navigate to MainScreen.fxml
     * @throws IOException
     */
    public void onClickAddProduct(ActionEvent actionEvent) throws IOException {
        exceptionLabel.setText("");
        String name = productNameText.getText();
        double price = 0d;
        int stock = 0;
        int max = 0;
        int min = 0;

        if (name.isEmpty()) {
            String ex = exceptionLabel.getText();
            if (exceptionLabel.getText() == "") {
                exceptionLabel.setText("Name: enter a product name");
            } else {
                exceptionLabel.setText(ex + "\nName: enter a product name");
            }
        } else {
            MainScreenController.productData.setName(name);
        }

        try {
            price = Double.parseDouble(productPriceText.getText());
        } catch (NumberFormatException e) {
            String ex = exceptionLabel.getText();
            if (exceptionLabel.getText() == "") {
                exceptionLabel.setText("Price: enter a double value");
            } else {
                exceptionLabel.setText(ex + "\nPrice: enter a double value");
            }
        }

        try {
            stock = Integer.parseInt(productInventoryText.getText());
        } catch (NumberFormatException e) {
            String ex = exceptionLabel.getText();
            if (exceptionLabel.getText() == "") {
                exceptionLabel.setText("Inv: enter an integer value");
            } else {
                exceptionLabel.setText(ex + "\nInv: enter an integer value");
            }
        }

        try {
            min = Integer.parseInt(productMin.getText());
        } catch (NumberFormatException e) {
            String ex = exceptionLabel.getText();
            if (exceptionLabel.getText() == "") {
                exceptionLabel.setText("Min: enter an integer value");
            } else {
                exceptionLabel.setText(ex + "\nMin: enter an integer value");
            }
        }

        try {
            max = Integer.parseInt(productMax.getText());
        } catch (NumberFormatException e) {
            String ex = exceptionLabel.getText();
            if (exceptionLabel.getText() == "") {
                exceptionLabel.setText("Max: enter an integer value");
            } else {
                exceptionLabel.setText(ex + "\nMax: enter an integer value");
            }
        }

        if (max < min) {
            String ex = exceptionLabel.getText();
            if (exceptionLabel.getText() == "") {
                exceptionLabel.setText("Max value cannot be less than Min value");
            } else {
                exceptionLabel.setText(ex + "\nMax value cannot be less than Min value");
            }
        } else {
            if (stock > max || stock < min) {
                String ex = exceptionLabel.getText();
                if (exceptionLabel.getText() == "") {
                    exceptionLabel.setText("Inventory is out of bounds");
                } else {
                    exceptionLabel.setText(ex + "\nInventory is out of bounds");
                }
            }
        }

        MainScreenController.productData.setStock(stock);
        MainScreenController.productData.setMax(max);
        MainScreenController.productData.setMin(min);
        MainScreenController.productData.setPrice(price);


        if (exceptionLabel.getText().isEmpty()) {
            Inventory.updateProduct(MainScreenController.productData.getId(), MainScreenController.productData);
            navigate(actionEvent, "MainScreen.fxml");
        }

    }

    /**
     * @param actionEvent the actionEvent to navigate to MainScreen.fxml
     * @throws IOException
     */
    public void onClickMainMenu(ActionEvent actionEvent) throws IOException {
        navigate(actionEvent, "MainScreen.fxml");
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productIDText.setText(String.valueOf(MainScreenController.productData.getId()));
        productNameText.setText(MainScreenController.productData.getName());
        productPriceText.setText(String.valueOf(MainScreenController.productData.getPrice()));
        productInventoryText.setText(String.valueOf(MainScreenController.productData.getStock()));
        productMin.setText(String.valueOf(MainScreenController.productData.getMin()));
        productMax.setText(String.valueOf(MainScreenController.productData.getMax()));

        partAddTableView.setItems(Inventory.getAllParts());

        addPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        partDeleteTableView.setItems(MainScreenController.productData.getAllAssociatedParts());

        deletePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        deletePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        deleteInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        deletePricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
