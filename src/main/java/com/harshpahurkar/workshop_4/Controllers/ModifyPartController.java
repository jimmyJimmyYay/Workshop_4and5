/**********************************************
 Workshop #4 & 5
 Course: APD
 Last Name: Pahurkar
 First Name: Harsh
 ID: 115587222
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Date: March 16, 2025
 **********************************************/
package com.harshpahurkar.workshop_4.Controllers;

import com.harshpahurkar.workshop_4.HelloApplication;
import com.harshpahurkar.workshop_4.Models.InHouse;
import com.harshpahurkar.workshop_4.Models.Inventory;
import com.harshpahurkar.workshop_4.Models.Outsourced;
import com.harshpahurkar.workshop_4.Models.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton inHouseRadio;

    @FXML
    private RadioButton outsourcedRadio;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField maxField;

    @FXML
    private TextField minField;

    @FXML
    private TextField machineIdField;

    @FXML
    private Label machineIdLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private Inventory inventory;
    private ToggleGroup sourceToggleGroup;
    private Part selectedPart;
    private int selectedIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inventory = HelloApplication.getInventory();

        sourceToggleGroup = new ToggleGroup();
        inHouseRadio.setToggleGroup(sourceToggleGroup);
        outsourcedRadio.setToggleGroup(sourceToggleGroup);
    }

    public void setPart(Part part, int index) {
        this.selectedPart = part;
        this.selectedIndex = index;

        idField.setText(String.valueOf(part.getId()));
        nameField.setText(part.getName());
        stockField.setText(String.valueOf(part.getStock()));
        priceField.setText(String.valueOf(part.getPrice()));
        maxField.setText(String.valueOf(part.getMax()));
        minField.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            inHouseRadio.setSelected(true);
            machineIdLabel.setText("Machine ID");
            machineIdField.setText(String.valueOf(((InHouse) part).getMachine()));
        } else {
            outsourcedRadio.setSelected(true);
            machineIdLabel.setText("Company Name");
            machineIdField.setText(((Outsourced) part).getCompanyName());
        }

        idField.setDisable(true);
    }

    @FXML
    private void handleInHouseRadio() {
        machineIdLabel.setText("Machine ID");
        machineIdField.setPromptText("Enter machine ID");

        if (selectedPart instanceof Outsourced) {
            machineIdField.clear();
        }
    }

    @FXML
    private void handleOutsourcedRadio() {
        machineIdLabel.setText("Company Name");
        machineIdField.setPromptText("Enter company name");

        if (selectedPart instanceof InHouse) {
            machineIdField.clear();
        }
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Name cannot be empty.");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceField.getText().trim());
                if (price < 0) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Price cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Price must be a valid number.");
                return;
            }

            int stock;
            try {
                stock = Integer.parseInt(stockField.getText().trim());
                if (stock < 0) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Inventory cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Inventory must be a valid integer.");
                return;
            }

            int min;
            try {
                min = Integer.parseInt(minField.getText().trim());
                if (min < 0) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Minimum value cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Minimum value must be a valid integer.");
                return;
            }

            int max;
            try {
                max = Integer.parseInt(maxField.getText().trim());
                if (max < 0) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Maximum value cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Maximum value must be a valid integer.");
                return;
            }

            if (min > max) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Minimum value cannot be greater than maximum value.");
                return;
            }

            if (stock < min || stock > max) {
                showAlert(Alert.AlertType.ERROR, "Input Error",
                        "Inventory must be between the minimum and maximum values.");
                return;
            }

            int id = Integer.parseInt(idField.getText().trim());

            if (inHouseRadio.isSelected()) {
                int machineId;
                try {
                    machineId = Integer.parseInt(machineIdField.getText().trim());
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Machine ID must be a valid integer.");
                    return;
                }

                InHouse modifiedPart = new InHouse(id, name, price, stock, min, max, machineId);
                inventory.updatePart(selectedIndex, modifiedPart);
            } else {
                String companyName = machineIdField.getText().trim();
                if (companyName.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Company Name cannot be empty.");
                    return;
                }

                Outsourced modifiedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                inventory.updatePart(selectedIndex, modifiedPart);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Cancel Operation");
        alert.setContentText("Are you sure you want to cancel? Any unsaved changes will be lost.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}