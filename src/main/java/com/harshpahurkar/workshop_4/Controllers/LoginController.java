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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    private final String VALID_USERNAME = "name";
    private final String VALID_PASSWORD = "password";

    @FXML
    private void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main-view.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load main screen: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}