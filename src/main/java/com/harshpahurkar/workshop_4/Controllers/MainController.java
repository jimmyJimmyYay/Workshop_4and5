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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TextField partSearchField;

    @FXML
    private Button addPartButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productIdColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private TextField productSearchField;

    @FXML
    private Button addProductButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button exitButton;

    private Inventory inventory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inventory = HelloApplication.getInventory();

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(inventory.getAllParts());
        productsTable.setItems(inventory.getAllProducts());
    }

    @FXML
    private void handleAddPartButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add-part-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Part");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            partsTable.setItems(inventory.getAllParts());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load add part screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleModifyPartButton(ActionEvent event) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modify-part-view.fxml"));
                Parent root = loader.load();

                ModifyPartController controller = loader.getController();
                controller.setPart(selectedPart, partsTable.getSelectionModel().getSelectedIndex());

                Stage stage = new Stage();
                stage.setTitle("Modify Part");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                partsTable.setItems(inventory.getAllParts());
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load modify part screen: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a part to modify.");
        }
    }

    @FXML
    private void handleDeletePartButton(ActionEvent event) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Delete Part");
            alert.setContentText("Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean isAssociated = false;
                for (Product product : inventory.getAllProducts()) {
                    for (Part part : product.getAllAssociatedParts()) {
                        if (part.getId() == selectedPart.getId()) {
                            isAssociated = true;
                            break;
                        }
                    }
                    if (isAssociated) break;
                }

                if (isAssociated) {
                    showAlert(Alert.AlertType.ERROR, "Delete Error", "Cannot delete part that is associated with a product.");
                } else {
                    inventory.deletePart(selectedPart);
                    partsTable.setItems(inventory.getAllParts());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a part to delete.");
        }
    }

    @FXML
    private void handleSearchPartButton(ActionEvent event) {
        String searchText = partSearchField.getText().trim();
        if (searchText.isEmpty()) {
            partsTable.setItems(inventory.getAllParts());
        } else {
            try {
                int id = Integer.parseInt(searchText);
                Part part = inventory.searchPartByID(id);
                if (part != null) {
                    ObservableList<Part> searchResult = javafx.collections.FXCollections.observableArrayList();
                    searchResult.add(part);
                    partsTable.setItems(searchResult);
                } else {
                    partsTable.setItems(javafx.collections.FXCollections.observableArrayList());
                }
            } catch (NumberFormatException e) {
                partsTable.setItems(inventory.searchPartByName(searchText));
            }
        }
    }

    @FXML
    private void handleAddProductButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add-product-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Product");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            productsTable.setItems(inventory.getAllProducts());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load add product screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleModifyProductButton(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modify-product-view.fxml"));
                Parent root = loader.load();

                ModifyProductController controller = loader.getController();
                controller.setProduct(selectedProduct, productsTable.getSelectionModel().getSelectedIndex());

                Stage stage = new Stage();
                stage.setTitle("Modify Product");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                productsTable.setItems(inventory.getAllProducts());
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load modify product screen: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a product to modify.");
        }
    }

    @FXML
    private void handleDeleteProductButton(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Delete Product");
            alert.setContentText("Are you sure you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (selectedProduct.getAllAssociatedParts().size() > 0) {
                    showAlert(Alert.AlertType.ERROR, "Delete Error", "Cannot delete a product that has associated parts.");
                } else {
                    inventory.deleteProduct(selectedProduct);
                    productsTable.setItems(inventory.getAllProducts());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a product to delete.");}}
    @FXML
    private void handleSearchProductButton(ActionEvent event) {
        String searchText = productSearchField.getText().trim();
        if (searchText.isEmpty()) {
            productsTable.setItems(inventory.getAllProducts());
        } else {
            try {
                int id = Integer.parseInt(searchText);
                Product product = inventory.searchProductByID(id);
                if (product != null) {
                    ObservableList<Product> searchResult = javafx.collections.FXCollections.observableArrayList();
                    searchResult.add(product);
                    productsTable.setItems(searchResult);
                } else {
                    productsTable.setItems(javafx.collections.FXCollections.observableArrayList());
                }
            } catch (NumberFormatException e) {
                productsTable.setItems(inventory.searchProductByName(searchText));
            }
        }
    }

    @FXML
    private void handleExitButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Exit Application");
        alert.setContentText("Are you sure you want to exit the application?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
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