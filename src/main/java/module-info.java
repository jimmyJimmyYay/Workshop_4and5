/**********************************************
 Workshop #4 & 5
 Course: APD
 Last Name: Singh
 First Name: Amrinder
 ID: 101825230
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Amrinder Singh
 Date: March 16, 2025
 **********************************************/
module com.example.workshop4_apd {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.harshpahurkar.workshop_4 to javafx.fxml;
    exports com.harshpahurkar.workshop_4;
    exports com.harshpahurkar.workshop_4.Controllers;
    opens com.harshpahurkar.workshop_4.Controllers to javafx.fxml;
    exports com.harshpahurkar.workshop_4.Models;
    opens com.harshpahurkar.workshop_4.Models to javafx.fxml;
}