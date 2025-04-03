/**********************************************
 Workshop #4 & 5
 Course: APD
 Last Name: Pahurkar
 First Name: Harsh
 ID: 115587222
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Date: March 16, 2025
 **********************************************/
package com.harshpahurkar.workshop_4;

import com.harshpahurkar.workshop_4.Models.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Inventory inventory;

    @Override
    public void start(Stage stage) throws IOException {
        inventory = new Inventory();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Inventory Management System - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static void main(String[] args) {
        launch();
    }
}