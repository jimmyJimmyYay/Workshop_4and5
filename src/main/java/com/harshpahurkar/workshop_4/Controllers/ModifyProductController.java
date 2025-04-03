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
import com.harshpahurkar.workshop_4.Models.Inventory;
import com.harshpahurkar.workshop_4.Models.Part;
import com.harshpahurkar.workshop_4.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

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
    private TextField partSearchField;

    @FXML
    private TableView<Part> availablePartsTable;

    @FXML
    private TableColumn<Part, Integer> availablePartIdColumn;

    @FXML
    private TableColumn<Part, String> availablePartNameColumn;

    @FXML
    private TableColumn<Part, Integer> availablePartStockColumn;

    @FXML
    private TableColumn<Part, Double> availablePartPriceColumn;

    @FXML
    private TableView<Part> associatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> associatedPartIdColumn;

    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> associatedPartStockColumn;

    @FXML
    private TableColumn<Part, Double> associatedPartPriceColumn;

    @FXML
    private Button addPartButton;

    @FXML
    private Button removePartButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private Inventory inventory;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private Product selectedProduct;
    private int selectedIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inventory = HelloApplication.getInventory();

        availablePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        availablePartsTable.setItems(inventory.getAllParts());
    }

    public void setProduct(Product product, int index) {
        this.selectedProduct = product;
        this.selectedIndex = index;

        idField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        stockField.setText(String.valueOf(product.getStock()));
        priceField.setText(String.valueOf(product.getPrice()));
        maxField.setText(String.valueOf(product.getMax()));
        minField.setText(String.valueOf(product.getMin()));

        associatedParts.clear();
        associatedParts.addAll(product.getAllAssociatedParts());
        associatedPartsTable.setItems(associatedParts);

        idField.setDisable(true);
    }

    @FXML
    private void handleAddPartButton() {
        Part selectedPart = availablePartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.add(selectedPart);
            associatedPartsTable.setItems(associatedParts);
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a part to add.");
        }
    }

    @FXML
    private void handleRemovePartButton() {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Remove");
            alert.setHeaderText("Remove Associated Part");
            alert.setContentText("Are you sure you want to remove this part from the product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                associatedPartsTable.setItems(associatedParts);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a part to remove.");
        }
    }

    @FXML
    private void handleSearchPartButton() {
        String searchText = partSearchField.getText().trim();
        if (searchText.isEmpty()) {
            availablePartsTable.setItems(inventory.getAllParts());
        } else {
            try {
                int id = Integer.parseInt(searchText);
                Part part = inventory.searchPartByID(id);
                if (part != null) {
                    ObservableList<Part> searchResult = FXCollections.observableArrayList();
                    searchResult.add(part);
                    availablePartsTable.setItems(searchResult);
                } else {
                    availablePartsTable.setItems(FXCollections.observableArrayList());
                }
            } catch (NumberFormatException e) {
                availablePartsTable.setItems(inventory.searchPartByName(searchText));
            }
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

            if (associatedParts.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Product must have at least one associated part.");
                return;
            }

            double totalPartsCost = 0.0;
            for (Part part : associatedParts) {
                totalPartsCost += part.getPrice();
            }

            if (price < totalPartsCost) {
                showAlert(Alert.AlertType.ERROR, "Input Error",
                        "Product price cannot be less than the cost of its parts ($" + totalPartsCost + ")");
                return;
            }

            int id = Integer.parseInt(idField.getText().trim());

            Product modifiedProduct = new Product(id, name, price, stock, min, max);
            for (Part part : associatedParts) {
                modifiedProduct.addAssociatedPart(part);
            }

            inventory.updateProduct(selectedIndex, modifiedProduct);

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