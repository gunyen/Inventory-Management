package controller;
/**
 * @author John Nguyen 2/28/2022
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static main.Main.navigate;

public class ModifyPartScreenController implements Initializable {

    public RadioButton radioInHouse;
    public RadioButton radioOutsourced;
    public Label exceptionLabel;
    @FXML
    private TextField addPartID;
    @FXML
    private TextField addPartInventory;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartMin;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartSource;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartID.setText(String.valueOf(MainScreenController.partData.getId()));
        addPartName.setText(MainScreenController.partData.getName());
        addPartPrice.setText(String.valueOf(MainScreenController.partData.getPrice()));
        addPartInventory.setText(String.valueOf(MainScreenController.partData.getStock()));
        addPartMin.setText(String.valueOf(MainScreenController.partData.getMin()));
        addPartMax.setText(String.valueOf(MainScreenController.partData.getMax()));
        if (MainScreenController.partData instanceof InHouse) {
            radioInHouse.setSelected(true);
            partOrigin.setText("Machine ID");
            addPartSource.setText(String.valueOf(((InHouse) MainScreenController.partData).getMachineID()));
        }
        if (MainScreenController.partData instanceof Outsourced) {
            radioOutsourced.setSelected(true);
            partOrigin.setText("Customer Name");
            addPartSource.setText(((Outsourced) MainScreenController.partData).getCompanyName());
        }
    }

    /**
     * @param actionEvent the actionEvent to navigate to MainScreen.fxml
     * @throws IOException
     */
    public void onClickSave(ActionEvent actionEvent) throws IOException {
        exceptionLabel.setText("");
        String name = addPartName.getText();
        double price = 0d;
        int inventory = 0;
        int min = 0;
        int max = 0;
        String machineID = addPartSource.getText();
        if (name.isEmpty()) {
            String ex = exceptionLabel.getText();
            if (exceptionLabel.getText() == "") {
                exceptionLabel.setText("Name: enter a part name");
                name = MainScreenController.partData.getName();
            } else {
                exceptionLabel.setText(ex + "\nName: enter a part name");
                name = MainScreenController.partData.getName();
            }
        }

        try {
            price = Double.parseDouble(addPartPrice.getText());
        } catch (NumberFormatException e) {
            String ex = exceptionLabel.getText();
            if (exceptionLabel.getText() == "") {
                exceptionLabel.setText("Price: enter a double value");
            } else {
                exceptionLabel.setText(ex + "\nPrice: enter a double value");
            }
        }

        try {
            inventory = Integer.parseInt(addPartInventory.getText());
        } catch (NumberFormatException e) {
            String ex = exceptionLabel.getText();
            if (exceptionLabel.getText() == "") {
                exceptionLabel.setText("Inv: enter an integer value");
            } else {
                exceptionLabel.setText(ex + "\nInv: enter an integer value");
            }
        }

        try {
            min = Integer.parseInt(addPartMin.getText());
        } catch (NumberFormatException e) {
            String ex = exceptionLabel.getText();
            if (exceptionLabel.getText() == "") {
                exceptionLabel.setText("Min: enter an integer value");
            } else {
                exceptionLabel.setText(ex + "\nMin: enter an integer value");
            }
        }

        try {
            max = Integer.parseInt(addPartMax.getText());
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
            if (inventory > max || inventory < min) {
                String ex = exceptionLabel.getText();
                if (exceptionLabel.getText() == "") {
                    exceptionLabel.setText("Inventory is out of bounds");
                } else {
                    exceptionLabel.setText(ex + "\nInventory is out of bounds");
                }
            }
        }

        if (radioInHouse.isSelected()) {
            try {
                Integer.parseInt(addPartSource.getText());
            } catch (NumberFormatException e) {
                String ex = exceptionLabel.getText();
                if (exceptionLabel.getText() == "") {
                    exceptionLabel.setText("Machine ID: enter an integer value");
                } else {
                    exceptionLabel.setText(ex + "\nMachine ID: enter an integer value");
                }
            }
        } else if (radioOutsourced.isSelected()) {
            if (machineID.isEmpty()) {
                String ex = exceptionLabel.getText();
                if (exceptionLabel.getText() == "") {
                    exceptionLabel.setText("Customer Name: enter the customer's name");
                } else {
                    exceptionLabel.setText(ex + "\nCustomer Name: enter the customer's name");
                }
            }
        }

        if (exceptionLabel.getText().isEmpty()) {
            if (radioInHouse.isSelected()) {
                InHouse inhouse = new InHouse(MainScreenController.partData.getId(), name, price, inventory, min, max, Integer.parseInt(addPartSource.getText()));
                Inventory.updatePart(MainScreenController.partData.getId(), inhouse);
                navigate(actionEvent, "MainScreen.fxml");
            } else if (radioOutsourced.isSelected()) {
                Outsourced outsourced = new Outsourced(MainScreenController.partData.getId(), name, price, inventory, min, max, machineID);
                Inventory.updatePart(MainScreenController.partData.getId(), outsourced);
                navigate(actionEvent, "MainScreen.fxml");
            }
        }
    }

    @FXML
    private Label partOrigin;

    /**
     * @param event the event to select InHouse RadioButton
     */
    @FXML
    void onClickInHouse(ActionEvent event) {
        if (((RadioButton) event.getSource()).isSelected()) {
            partOrigin.setText("Machine ID");
        }
    }

    /**
     * @param event  the event to select Outsource RadioButton
     */
    @FXML
    void onClickOutsourced(ActionEvent event) {
        if (((RadioButton) event.getSource()).isSelected()) {
            partOrigin.setText("Customer Name");
        }
    }

    /**
     * @param actionEvent the actionEvent to navigate to MainScreen.fxml
     * @throws IOException
     */
    public void onClickMainMenu(ActionEvent actionEvent) throws IOException {
        navigate(actionEvent, "MainScreen.fxml");
    }
}
